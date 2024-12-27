package com.kayas.brokerageFirm.dto.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WalletRequest {

    @Positive(message = "userId must be greater than zero.")
    @NotNull(message = "userId field cannot be null.")
    private Long userId;

    @NotNull(message = "Amount field cannot be null.")
    @Positive(message = "Amount must be greater than zero.")
    private BigDecimal amount;
}
