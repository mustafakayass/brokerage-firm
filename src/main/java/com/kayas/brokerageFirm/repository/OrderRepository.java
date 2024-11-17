package com.kayas.brokerageFirm.repository;

import com.kayas.brokerageFirm.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> getOrderById(Long id);

    @Query("SELECT o FROM Order o WHERE (:userId IS NULL OR o.userId = :userId) AND (:startDate IS NULL OR o.createDate >= :startDate) AND (:endDate IS NULL OR o.createDate <= :endDate)")
    List<Order> findOrdersByFilters(@Param("userId") Long userId,@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);
}
