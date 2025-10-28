package FoodDelivery.Order_service.service;

import FoodDelivery.Order_service.client.RestaurantServiceClient;

import FoodDelivery.Order_service.dto.*;

import FoodDelivery.Order_service.entity.Order;

import FoodDelivery.Order_service.entity.OrderStatus;

import FoodDelivery.Order_service.exception.OrderNotFoundException;

import FoodDelivery.Order_service.exception.RestaurantNotAvailableException;

import FoodDelivery.Order_service.repository.OrderRepository;

import feign.FeignException;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;

@Service

@Transactional

public class OrderService {

    private final OrderRepository orderRepository;

    private final RestaurantServiceClient restaurantServiceClient;

    @Autowired

    public OrderService(OrderRepository orderRepository, RestaurantServiceClient restaurantServiceClient) {

        this.orderRepository = orderRepository;

        this.restaurantServiceClient = restaurantServiceClient;

    }

    /**

     * Create a new order

     */

    public OrderResponse createOrder(CreateOrderRequest request) {

        // Validate restaurant availability

        try {

            RestaurantDto restaurant = restaurantServiceClient.getRestaurantById(request.getRestaurantId());

            if (!restaurant.isActive()) {

                throw new RestaurantNotAvailableException("Restaurant is currently closed");

            }

            boolean isAvailable = restaurantServiceClient.isRestaurantAvailable(request.getRestaurantId());

            if (!isAvailable) {

                throw new RestaurantNotAvailableException("Restaurant is not accepting orders at this time");

            }

        } catch (FeignException e) {

            throw new RestaurantNotAvailableException("Unable to verify restaurant availability");

        }

        // Create new order

        Order order = new Order(

                request.getUserId(),

                request.getRestaurantId(),

                request.getTotalAmount(),

                request.getDeliveryAddress(),

                request.getSpecialInstructions(),

                request.getOrderItems()

        );

        Order savedOrder = orderRepository.save(order);

        return convertToOrderResponse(savedOrder);

    }

    /**

     * Get orders for a specific user

     */

    @Transactional(readOnly = true)

    public List<OrderResponse> getOrdersByUserId(Long userId) {

        List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(userId);

        return orders.stream()

                .map(this::convertToOrderResponse)

                .collect(Collectors.toList());

    }

    /**

     * Get delivery status for a specific order

     */

    @Transactional(readOnly = true)

    public DeliveryStatusResponse getDeliveryStatus(Long orderId) {

        Order order = orderRepository.findById(orderId)

                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        return new DeliveryStatusResponse(

                order.getId(),

                order.getStatus(),

                getStatusDescription(order.getStatus()),

                order.getOrderDate(),

                order.getDeliveryAddress()

        );

    }

    /**

     * Update order status

     */

    public OrderResponse updateOrderStatus(Long orderId, OrderStatus newStatus) {

        Order order = orderRepository.findById(orderId)

                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        order.setStatus(newStatus);

        // Set delivery date if order is delivered

        if (newStatus == OrderStatus.DELIVERED) {

            order.setDeliveryDate(LocalDateTime.now());

        }

        Order updatedOrder = orderRepository.save(order);

        return convertToOrderResponse(updatedOrder);

    }

    /**

     * Get order by ID

     */

    @Transactional(readOnly = true)

    public OrderResponse getOrderById(Long orderId) {

        Order order = orderRepository.findById(orderId)

                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        return convertToOrderResponse(order);

    }

    /**

     * Cancel an order

     */

    public OrderResponse cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)

                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId));

        // Check if order can be cancelled

        if (order.getStatus() == OrderStatus.DELIVERED || order.getStatus() == OrderStatus.CANCELLED) {

            throw new IllegalStateException("Cannot cancel order with status: " + order.getStatus());

        }

        order.setStatus(OrderStatus.CANCELLED);

        Order cancelledOrder = orderRepository.save(order);

        return convertToOrderResponse(cancelledOrder);

    }

    /**

     * Get orders by status

     */

    @Transactional(readOnly = true)

    public List<OrderResponse> getOrdersByStatus(OrderStatus status) {

        List<Order> orders = orderRepository.findByStatus(status);

        return orders.stream()

                .map(this::convertToOrderResponse)

                .collect(Collectors.toList());

    }

    /**

     * Convert Order entity to OrderResponse DTO

     */

    private OrderResponse convertToOrderResponse(Order order) {

        try {

            RestaurantDto restaurant = restaurantServiceClient.getRestaurantById(order.getRestaurantId());

            return new OrderResponse(order, restaurant.getName());

        } catch (FeignException e) {

            // If restaurant service is unavailable, return response without restaurant name

            return new OrderResponse(order);

        }

    }

    /**

     * Get human-readable status description

     */

    private String getStatusDescription(OrderStatus status) {

        return switch (status) {

            case PENDING -> "Order received and is being processed";

            case CONFIRMED -> "Order confirmed by restaurant";

            case PREPARING -> "Your order is being prepared";

            case OUT_FOR_DELIVERY -> "Order is out for delivery";

            case DELIVERED -> "Order has been delivered";

            case CANCELLED -> "Order has been cancelled";

        };

    }

}
