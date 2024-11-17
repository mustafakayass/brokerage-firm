package com.kayas.brokerageFirm.controller;

import com.kayas.brokerageFirm.api.OrderApi;
import com.kayas.brokerageFirm.dto.request.OrderDeleteRequest;
import com.kayas.brokerageFirm.dto.request.OrderRequest;
import com.kayas.brokerageFirm.dto.common.BaseResponse;
import com.kayas.brokerageFirm.entity.Order;
import com.kayas.brokerageFirm.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController implements OrderApi {

    @Autowired
    private OrderService orderService;

    @Override
    @GetMapping
    public ResponseEntity<List<Order>> listOrders(
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "yyyyMMdd") LocalDate endDate
    ) {
        List<Order> filteredOrders = orderService.getOrdersByFilters(userId, startDate, endDate);
        return ResponseEntity.ok(filteredOrders);
    }

    @Override
    @PostMapping("/create")
    public ResponseEntity<BaseResponse<String>> createOrder(@RequestBody @Valid OrderRequest request) {
        String result = orderService.createOrder(request);
        return ResponseEntity.ok(BaseResponse.success(result));
    }

    @Override
    @PostMapping("/delete")
    public ResponseEntity<BaseResponse<String>> deleteOrder(@RequestBody @Valid OrderDeleteRequest request) {
        String result = orderService.deleteOrder(request);
        return ResponseEntity.ok(BaseResponse.success(result));
    }
}

