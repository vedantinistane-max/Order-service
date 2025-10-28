package FoodDelivery.Order_service.repository;

import FoodDelivery.Order_service.entity.Order;
import FoodDelivery.Order_service.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * Find all orders for a specific user
     */
    List<Order> findByUserIdOrderByOrderDateDesc(Long userId);
    /**
     * Find orders by status
     */
    List<Order> findByStatus(OrderStatus status);
    /**
     * Find orders for a user with specific status
     */
    List<Order> findByUserIdAndStatus(Long userId, OrderStatus status);
    /**
     * Find orders within a date range
     */
    @Query("SELECT o FROM Order o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Order> findOrdersBetweenDates(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate);
    /**
     * Find orders for a specific restaurant
     */
    List<Order> findByRestaurantIdOrderByOrderDateDesc(Long restaurantId);
    /**
     * Count orders by status for a user
     */
    long countByUserIdAndStatus(Long userId, OrderStatus status);
}