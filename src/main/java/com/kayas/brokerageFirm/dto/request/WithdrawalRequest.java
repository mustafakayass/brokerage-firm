package com.kayas.brokerageFirm.dto.request;

import com.kayas.brokerageFirm.dto.common.WalletRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class WithdrawalRequest extends WalletRequest {

    @NotBlank(message = "IBAN cannot be null.")
    @Pattern(regexp = "^TR\\d{2}[0-9]{5}[A-Z0-9]{17}$", message = "Invalid IBAN format.")
    private String iban;

}
