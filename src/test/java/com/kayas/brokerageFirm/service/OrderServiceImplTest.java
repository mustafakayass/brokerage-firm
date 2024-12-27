package com.kayas.brokerageFirm.service;

import com.kayas.brokerageFirm.dto.request.OrderDeleteRequest;
import com.kayas.brokerageFirm.dto.request.OrderRequest;
import com.kayas.brokerageFirm.dto.response.AdminValidationResponse;
import com.kayas.brokerageFirm.entity.Asset;
import com.kayas.brokerageFirm.entity.Order;
import com.kayas.brokerageFirm.exception.InvalidOrderStatusException;
import com.kayas.brokerageFirm.exception.UnauthorizedAccessException;
import com.kayas.brokerageFirm.service.helper.OrderHelper;
import com.kayas.brokerageFirm.utility.enums.OrderSide;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private AssetService assetService;
    
    @Mock
    private UserService userService;
    
    @Mock
    private OrderHelper orderHelper;
    
    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    void createOrder_whenBuyOrder_shouldSucceed() {
        // Arrange
        OrderRequest request = new OrderRequest();
        request.setOrderSide("B");
        request.setSize(BigDecimal.valueOf(1.0));
        request.setPrice(BigDecimal.valueOf(50000.0));
        request.setAssetName("BTC");
        
        AdminValidationResponse validationResponse = new AdminValidationResponse();
        validationResponse.setId(1L);
        validationResponse.setAdmin(false);
        
        Asset tryAsset = new Asset(1L, "TRY", BigDecimal.valueOf(100000.0), BigDecimal.valueOf(100000.0));
        Order createdOrder = new Order(1L, "BTC", "B", BigDecimal.valueOf(1.0), BigDecimal.valueOf(50000.0), "P", new Date());
        
        when(userService.validateUserAccess(any())).thenReturn(validationResponse);
        when(assetService.getAssetByUserIdAndName(1L, "TRY")).thenReturn(tryAsset);
        when(orderHelper.createNewOrder(any(), any())).thenReturn(createdOrder);
        
        // Act
        String result = orderService.createOrder(request);
        
        // Assert
        verify(assetService).validateBuyOrder(tryAsset, BigDecimal.valueOf(50000.0));
        verify(assetService).updateAssetSize(tryAsset, BigDecimal.valueOf(-50000.0));
        assertTrue(result.contains("has been created"));
    }

    @Test
    void createOrder_whenSellOrder_shouldSucceed() {
        // Arrange
        OrderRequest request = new OrderRequest();
        request.setOrderSide("S");
        request.setSize(BigDecimal.valueOf(1.0));
        request.setPrice(BigDecimal.valueOf(50000.0));
        request.setAssetName("BTC");
        
        AdminValidationResponse validationResponse = new AdminValidationResponse();
        validationResponse.setId(1L);
        
        Asset btcAsset = new Asset(1L, "BTC", BigDecimal.valueOf(2.0), BigDecimal.valueOf(2.0));
        Order createdOrder = new Order(1L, "BTC", "S", BigDecimal.valueOf(1.0), BigDecimal.valueOf(50000.0), "P", new Date());
        
        when(userService.validateUserAccess(any())).thenReturn(validationResponse);
        when(assetService.getAssetByUserIdAndName(1L, "BTC")).thenReturn(btcAsset);
        when(orderHelper.createNewOrder(any(), any())).thenReturn(createdOrder);
        
        // Act
        String result = orderService.createOrder(request);
        
        // Assert
        verify(assetService).validateSellOrder(btcAsset, BigDecimal.valueOf(1.0));
        verify(assetService).updateAssetSize(btcAsset, BigDecimal.valueOf(1.0).negate());
        assertTrue(result.contains("has been created"));
    }

    @Test
    void deleteOrder_whenValidPendingOrder_shouldSucceed() {
        // Arrange
        OrderDeleteRequest request = new OrderDeleteRequest();
        request.setOrderId(1L);
        
        AdminValidationResponse validationResponse = new AdminValidationResponse();
        validationResponse.setId(1L);
        
        Order pendingOrder = new Order(1L, "BTC", "B", BigDecimal.valueOf(1.0), BigDecimal.valueOf(50000.0), "P", new Date());
        Asset tryAsset = new Asset(1L, "TRY", BigDecimal.valueOf(100000.0), BigDecimal.valueOf(100000.0));
        
        when(userService.validateUserAccess(any())).thenReturn(validationResponse);
        when(orderHelper.getOrderById(1L)).thenReturn(pendingOrder);
        when(assetService.getAssetByUserIdAndName(1L, "TRY")).thenReturn(tryAsset);
        
        // Act
        String result = orderService.deleteOrder(request);
        
        // Assert
        verify(orderHelper).updateOrderStatus(pendingOrder, "C");
        assertTrue(result.contains("has been cancelled"));
    }

    @Test
    void createOrder_whenUserNotAdmin_andUserIdProvided_shouldThrowException() {
        // Arrange
        OrderRequest request = new OrderRequest();
        request.setUserId(2L); // Different user id than current user
        request.setOrderSide(OrderSide.BUY.getDisplayName());
        request.setSize(BigDecimal.valueOf(10.0));
        request.setPrice(BigDecimal.valueOf(100.0));

        when(userService.validateUserAccess(request.getUserId()))
                .thenThrow(new UnauthorizedAccessException("You can only access your own resources"));

        // Act & Assert
        assertThrows(UnauthorizedAccessException.class, () ->
                orderService.createOrder(request)
        );

        verify(userService).validateUserAccess(request.getUserId());
        verify(orderHelper, never()).createNewOrder(any(), any());
    }


    @Test
    void deleteOrder_whenOrderNotPending_shouldThrowException() {
        // Arrange
        OrderDeleteRequest request = new OrderDeleteRequest();
        request.setOrderId(1L);
        request.setUserId(1L);

        // AdminValidationResponse mock'u
        AdminValidationResponse response = new AdminValidationResponse();
        response.setAdmin(false); // Kullanıcı admin değil
        response.setId(1L); // Kullanıcının ID'si

        // Order mock'u
        Order matchedOrder = new Order();
        matchedOrder.setStatus("M"); // Sipariş PENDING değil
        matchedOrder.setUserId(1L); // Kullanıcı kendi siparişi

        // Mock davranışları
        when(userService.validateUserAccess(1L)).thenReturn(response);
        when(orderHelper.getOrderById(1L)).thenReturn(matchedOrder);

        // Act & Assert
        assertThrows(InvalidOrderStatusException.class, () ->
            orderService.deleteOrder(request)
        );
    }
}