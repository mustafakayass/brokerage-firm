package com.kayas.brokerageFirm.api;

import com.kayas.brokerageFirm.dto.request.OrderDeleteRequest;
import com.kayas.brokerageFirm.dto.request.OrderRequest;
import com.kayas.brokerageFirm.dto.common.BaseResponse;
import com.kayas.brokerageFirm.entity.Order;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

public interface OrderApi {

    @GetMapping
    ResponseEntity<List<Order>> listOrders(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    );

    ResponseEntity<BaseResponse<String>> createOrder(OrderRequest orderRequest);

    ResponseEntity<BaseResponse<String>> deleteOrder(OrderDeleteRequest orderRequest);

}
