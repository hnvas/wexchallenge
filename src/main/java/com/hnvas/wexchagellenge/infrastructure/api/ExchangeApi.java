package com.hnvas.wexchagellenge.infrastructure.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hnvas.wexchagellenge.application.purchase.exchange.get.GetExchangesOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Exchanges", description = "API for managing purchase exchanges")
@RequestMapping("/purchases/{id}/exchanges")
public interface ExchangeApi {

  @Operation(
      summary = "Get exchanges for a purchase",
      description = "Retrieves exchange information for a specific purchase",
      tags = {"Exchanges"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Exchanges retrieved successfully",
            content =
                @Content(
                    schema = @Schema(implementation = GetExchangesOutput.class),
                    examples =
                        @ExampleObject(
                            name = "Example Response",
                            summary = "Example of a successful response",
                            value =
                                "{ \"id\": 67890, \"description\": \"Laptop\", \"purchaseDate\": \"2024-10-30\", \"amount\": 1200.00, \"conversions\": [ { \"country\": \"Australia\", \"currency\": \"Dollar\", \"recordDate\": \"2024-10-30\", \"exchangeRate\": 1.2, \"convertedAmount\": 1440.00 }, { \"country\": \"Canada\", \"currency\": \"Dollar\", \"recordDate\": \"2024-10-30\", \"exchangeRate\": 1.5, \"convertedAmount\": 1800.00 } ] }"))),
        @ApiResponse(
            responseCode = "422",
            description = "Invalid input data",
            content =
                @Content(
                    mediaType = "application/json",
                    examples =
                        @ExampleObject(
                            name = "Validation Errors",
                            summary = "Example of validation errors",
                            value =
                                "[ { \"field\": \"country\", \"message\": \"Country must not be empty\" }, { \"field\": \"currency\", \"message\": \"Currency must not be empty\" } ]"))),
        @ApiResponse(responseCode = "404", description = "Purchase not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
      })
  @GetMapping
  ResponseEntity<GetExchangesOutput> getExchanges(
      @Parameter(description = "ID of the purchase", required = true) @PathVariable(name = "id")
          String id,
      @Parameter(description = "Country name, required if currency is not provided")
          @RequestParam(name = "country")
          String countryCurrency,
      @Parameter(description = "Currency name, required if country is not provided")
          @RequestParam(name = "currency")
          String currency);
}
