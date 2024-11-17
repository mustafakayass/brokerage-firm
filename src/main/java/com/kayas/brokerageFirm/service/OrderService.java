package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.dto.request.OrderDeleteRequest;
import com.kayas.brokerageFirm.dto.request.OrderRequest;
import com.kayas.brokerageFirm.entity.Order;

import java.time.LocalDate;
import java.util.List;

public interface OrderService {
    List<Order> getOrdersByFilters(Long userId, LocalDate startDate , LocalDate endDate);
    String createOrder(OrderRequest request);
    String deleteOrder(OrderDeleteRequest request);
}
