package com.hnvas.wexchagellenge.infrastructure.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseCommand;
import com.hnvas.wexchagellenge.application.purchase.create.CreatePurchaseOutput;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Purchases", description = "API for managing purchases")
@RequestMapping("/purchases")
public interface PurchaseApi {

  @Operation(
      summary = "Create a new purchase",
      description = "Creates a new purchase record with the provided details",
      tags = {"Purchases"})
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Purchase created successfully",
            content =
                @Content(
                    schema = @Schema(implementation = CreatePurchaseOutput.class),
                    examples =
                        @ExampleObject(
                            name = "Example Response",
                            summary = "Example of a successful response",
                            value = "{ \"id\": 12345 }"))),
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
                                "{ \"message\": \"Invalid purchase information\", \"violations\": { \"purchaseDate\": \"Purchase date must be in the past or present\", \"description\": \"Description cannot be empty\" } }"))),
          @ApiResponse(
              responseCode = "500",
              description = "Internal server error",
              content = @Content(examples = @ExampleObject("Internal server error")))
      })
  @PostMapping
  ResponseEntity<CreatePurchaseOutput> createPurchase(
      @RequestBody
          @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Details of the purchase to be created",
              required = true,
              content =
                  @Content(
                      schema = @Schema(implementation = CreatePurchaseCommand.class),
                      examples =
                          @ExampleObject(
                              name = "Example Purchase",
                              summary = "Example of a purchase request",
                              value =
                                  "{ \"description\": \"Laptop\", \"purchaseDate\": \"2024-10-30\", \"amount\": 1200.00 }")))
          CreatePurchaseCommand input);
}
