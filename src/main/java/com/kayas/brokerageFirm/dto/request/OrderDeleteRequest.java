package com.kayas.brokerageFirm.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDeleteRequest {

    @NotNull(message = "userId field cannot be null.")
    @Positive(message = "userId must be greater than zero.")
    private Long userId;

    @Positive(message = "Order id must be greater than zero.")
    @NotNull(message = "size field cannot be null.")
    private Long orderId;
}
