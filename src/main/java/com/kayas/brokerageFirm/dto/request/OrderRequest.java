package com.kayas.brokerageFirm.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {

    @Positive(message = "userId must be greater than zero.")
    @NotNull(message = "userId field cannot be null.")
    private Long userId;

    @Pattern(regexp = "^[A-Z]{3}$", message = "assetName must be 3 uppercase letters.")
    @NotBlank(message = "assetName field cannot be null.")
    private String assetName;

    @Pattern(regexp = "^(B|S)$", message = "orderSide must be either 'B' (buy) or 'S' (sell) only.")
    @NotBlank(message = "orderSide field cannot be null or blank.")
    private String orderSide;

    @Positive(message = "Order size must be greater than zero.")
    @NotNull(message = "size field cannot be null.")
    private Double size;

    @Positive(message = "price field must be greater than zero.")
    @NotNull(message = "price field cannot be null.")
    private Double price;
}
