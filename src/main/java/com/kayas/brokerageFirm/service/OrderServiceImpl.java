package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.dto.response.AdminValidationResponse;
import com.kayas.brokerageFirm.dto.request.OrderDeleteRequest;
import com.kayas.brokerageFirm.dto.request.OrderRequest;
import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.entity.Order;
import com.kayas.brokerageFirm.entity.User;
import com.kayas.brokerageFirm.exception.InvalidOrderStatusException;
import com.kayas.brokerageFirm.exception.UnauthorizedAccessException;
import com.kayas.brokerageFirm.service.helper.OrderHelper;
import com.kayas.brokerageFirm.utility.enums.OrderSide;
import com.kayas.brokerageFirm.utility.enums.Status;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AssetService assetService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderHelper orderHelper;


    @Override
    public List<Order> getOrdersByFilters(Long userId, LocalDate startDate, LocalDate endDate) {

        LocalDateTime startDateTime = (startDate != null) ? startDate.atStartOfDay() : null;
        LocalDateTime endDateTime = (endDate != null) ? endDate.atTime(23, 59, 59) : null;

        User currentUser = userService.getCurrentUser();

        if (!userService.isAdmin(currentUser)) {
            userId = currentUser.getId();
        }

        return orderHelper.getOrdersByFilters(userId, startDateTime, endDateTime);
    }


    @Override
    public String createOrder(OrderRequest request) {
        final AdminValidationResponse response = userService.validateUserAccess(request.getUserId());
        Double requestedOrderAmount = request.getSize() * request.getPrice();
        
        if (OrderSide.BUY.getDisplayName().equals(request.getOrderSide())) {
            handleBuyOrder(response.getId(), requestedOrderAmount);
        } else {
            handleSellOrder(response.getId(), request.getAssetName(), request.getSize());
        }
        
        Order order = orderHelper.createNewOrder(request, response.getId());
        return "Order with ID " + order.getId() + " has been created.";
    }

    @Override
    public String deleteOrder(OrderDeleteRequest request) {
        AdminValidationResponse response = userService.validateUserAccess(request.getUserId());
        Order order = orderHelper.getOrderById(request.getOrderId());
        
        if (!response.isAdmin() && !order.getUserId().equals(response.getId())) {
            throw new UnauthorizedAccessException("You can only cancel your own orders.");
        }
        if (!order.getStatus().equals(Status.PENDING.getDisplayName())) {
            throw new InvalidOrderStatusException("You can only cancel orders with 'PENDING' status.");
        }

        if (OrderSide.BUY.getDisplayName().equals(order.getOrderSide())) {
            handleBuyOrderCancellation(response.getId(), order.getSize(), order.getPrice());
        } else if (OrderSide.SELL.getDisplayName().equals(order.getOrderSide())) {
            handleSellOrderCancellation(response.getId(), order.getAssetName(), order.getSize());
        }

        orderHelper.updateOrderStatus(order, Status.CANCELLED.getDisplayName());
        return "Order with ID " + order.getId() + " has been cancelled.";
    }

    private void handleBuyOrder(Long userId, Double requestedOrderAmount) {
        Asset tryAsset = assetService.getAssetByUserIdAndName(userId, "TRY");
        assetService.validateBuyOrder(tryAsset, requestedOrderAmount);
        assetService.updateAssetSize(tryAsset, -requestedOrderAmount);
    }

    private void handleSellOrder(Long userId, String assetName, Double requestedSize) {
        Asset requestedAsset = assetService.getAssetByUserIdAndName(userId, assetName);
        assetService.validateSellOrder(requestedAsset, requestedSize);
        assetService.updateAssetSize(requestedAsset, -requestedSize);
    }

    private void handleBuyOrderCancellation(Long userId, Double size, Double price) {
        Asset tryAsset = assetService.getAssetByUserIdAndName(userId, "TRY");
        Double refundAmount = size * price;
        assetService.updateAssetSize(tryAsset, refundAmount);
    }

    private void handleSellOrderCancellation(Long userId, String assetName, Double size) {
        Asset requestedAsset = assetService.getAssetByUserIdAndName(userId, assetName);
        assetService.updateAssetSize(requestedAsset, size);
    }

}

