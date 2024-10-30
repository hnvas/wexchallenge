package com.hnvas.wexchagellenge.application.purchase.exchange.get;

import com.hnvas.wexchagellenge.application.exception.ResourceNotFoundException;
import com.hnvas.wexchagellenge.application.exception.ValidationException;
import com.hnvas.wexchagellenge.application.validation.ValidationHandler;
import com.hnvas.wexchagellenge.domain.purchase.Purchase;
import com.hnvas.wexchagellenge.domain.purchase.PurchaseGateway;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeConverter;
import com.hnvas.wexchagellenge.domain.purchase.exchange.ExchangeRateGateway;
import com.hnvas.wexchagellenge.domain.purchase.exchange.LocalizedAmount;
import jakarta.validation.ConstraintViolation;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

import static com.hnvas.wexchagellenge.application.exception.NoExchangeRateToConvertException.noExchangeRateToConvert;

public class GetExchangesCommandHandler {

  private static final String INVALID_QUERY_MESSAGE = "Invalid query information";
  public static final long MONTH_RANGE_PERIOD = 6L;

  private final PurchaseGateway purchaseGateway;
  private final ExchangeRateGateway exchangeRateGateway;
  private final ValidationHandler<GetExchangesCommand> validationHandler;

  public GetExchangesCommandHandler(
      PurchaseGateway purchaseGateway,
      ExchangeRateGateway exchangeRateGateway,
      ValidationHandler<GetExchangesCommand> validationHandler) {
    this.purchaseGateway = purchaseGateway;
    this.exchangeRateGateway = exchangeRateGateway;
    this.validationHandler = validationHandler;
  }

  public GetExchangesOutput handle(GetExchangesCommand command) {
    if (!validationHandler.isValid(command)) {
      throw notifyInvalid(validationHandler.violations());
    }

    final var purchase =
        purchaseGateway
            .findById(command.purchaseId())
            .orElseThrow(() -> notifyNotFound(Purchase.class.getSimpleName()));

    final var localizedAmountList = fetchLocalizedAmounts(purchase, command);

    return GetExchangesOutput.of(purchase, localizedAmountList);
  }

  private List<LocalizedAmount> fetchLocalizedAmounts(
      Purchase purchase, GetExchangesCommand command) {
    final var dateTo = purchase.purchaseDate();
    final var dateFrom = dateTo.minusMonths(MONTH_RANGE_PERIOD);

    final var exchangeRates =
        switch (command.queryType()) {
          case COUNTRY -> exchangeRateGateway.findExchangeRatesByCountry(
              command.country(), dateFrom, dateTo);
          case CURRENCY -> exchangeRateGateway.findExchangeRatesByCurrency(
              command.currency(), dateFrom, dateTo);
          case COUNTRY_AND_CURRENCY -> exchangeRateGateway.findExchangeRatesByCountryAndCurrency(
              command.country(), command.currency(), dateFrom, dateTo);
        };

    if (CollectionUtils.isEmpty(exchangeRates)) {
      throw noExchangeRateToConvert();
    }

    return ExchangeConverter.convert(exchangeRates, purchase);
  }

  private ValidationException notifyInvalid(
      Set<ConstraintViolation<GetExchangesCommand>> violations) {
    return new ValidationException(
        INVALID_QUERY_MESSAGE, violations.stream().map(ConstraintViolation::getMessage).toList());
  }

  private ResourceNotFoundException notifyNotFound(String entityClassName) {
    return ResourceNotFoundException.of(entityClassName);
  }
}
