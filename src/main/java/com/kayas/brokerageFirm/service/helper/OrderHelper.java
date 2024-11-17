package com.kayas.brokerageFirm.service.helper;

import com.kayas.brokerageFirm.dto.request.OrderRequest;
import com.kayas.brokerageFirm.entity.Order;
import com.kayas.brokerageFirm.exception.OrderNotFoundException;
import com.kayas.brokerageFirm.repository.OrderRepository;
import com.kayas.brokerageFirm.utility.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderHelper {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getOrdersByFilters(Long userId, LocalDateTime startDate, LocalDateTime endDate) {
        List<Order> orderList = orderRepository.findOrdersByFilters(userId,startDate, endDate);
        if (orderList.isEmpty())
            throw new OrderNotFoundException("Order not found");
        return orderList;
    }

    public Order createNewOrder(OrderRequest request, Long userId) {
        Order order = new Order();
        order.setSize(request.getSize());
        order.setAssetName(request.getAssetName());
        order.setCreateDate(new Date());
        order.setPrice(request.getPrice());
        order.setUserId(userId);
        order.setStatus(Status.PENDING.getDisplayName());
        order.setOrderSide(request.getOrderSide());
        return orderRepository.save(order);
    }

    public void updateOrderStatus(Order order, String status) {
        order.setStatus(status);
        orderRepository.save(order);
    }

    public Order getOrderById(Long orderId) {
        return orderRepository.getOrderById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
    }

}
