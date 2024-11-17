package com.kayas.brokerageFirm.service.helper;

import com.kayas.brokerageFirm.dto.request.OrderRequest;
import com.kayas.brokerageFirm.entity.Order;
import com.kayas.brokerageFirm.exception.OrderNotFoundException;
import com.kayas.brokerageFirm.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderHelperTest {

    @Mock
    private OrderRepository orderRepository;
    
    @InjectMocks
    private OrderHelper orderHelper;

    @Test
    void createNewOrder_shouldCreateSuccessfully() {
        // Arrange
        OrderRequest request = new OrderRequest();
        request.setAssetName("BTC");
        request.setOrderSide("B");
        request.setSize(1.0);
        request.setPrice(50000.0);
        
        Order expectedOrder = new Order(1L, "BTC", "B", 1.0, 50000.0, "P", new Date());
        when(orderRepository.save(any(Order.class))).thenReturn(expectedOrder);
        
        // Act
        Order result = orderHelper.createNewOrder(request, 1L);
        
        // Assert
        assertEquals("P", result.getStatus());
        assertEquals("BTC", result.getAssetName());
        verify(orderRepository).save(any(Order.class));
    }

    @Test
    void getOrderById_whenOrderNotFound_shouldThrowException() {
        // Arrange
        when(orderRepository.getOrderById(any())).thenReturn(Optional.empty());
        
        // Act & Assert
        assertThrows(OrderNotFoundException.class, () ->
            orderHelper.getOrderById(1L)
        );
    }

    @Test
    void updateOrderStatus_shouldUpdateSuccessfully() {
        // Arrange
        Order order = new Order(1L, "BTC", "B", 1.0, 50000.0, "P", new Date());
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        
        // Act
        orderHelper.updateOrderStatus(order, "C");
        
        // Assert
        assertEquals("C", order.getStatus());
        verify(orderRepository).save(order);
    }
} 