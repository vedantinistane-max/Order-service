package FoodDelivery.Order_service.dto;

import FoodDelivery.Order_service.entity.OrderStatus;
import java.time.LocalDateTime;

public class DeliveryStatusResponse {
    private Long orderId;
    private OrderStatus status;
    private String statusDescription;
    private LocalDateTime lastUpdated;
    private String deliveryAddress;
    private LocalDateTime estimatedDeliveryTime;
    private String deliveryPersonName;
    private String deliveryPersonContact;
    public DeliveryStatusResponse() {}
    public DeliveryStatusResponse(Long orderId, OrderStatus status, String statusDescription,
                                  LocalDateTime lastUpdated, String deliveryAddress) {
        this.orderId = orderId;
        this.status = status;
        this.statusDescription = statusDescription;
        this.lastUpdated = lastUpdated;
        this.deliveryAddress = deliveryAddress;
    }
    // Getters and Setters
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public void setStatus(OrderStatus status) {
        this.status = status;
    }
    public String getStatusDescription() {
        return statusDescription;
    }
    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    public String getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public LocalDateTime getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }
    public void setEstimatedDeliveryTime(LocalDateTime estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }
    public String getDeliveryPersonName() {
        return deliveryPersonName;
    }
    public void setDeliveryPersonName(String deliveryPersonName) {
        this.deliveryPersonName = deliveryPersonName;
    }
    public String getDeliveryPersonContact() {
        return deliveryPersonContact;
    }
    public void setDeliveryPersonContact(String deliveryPersonContact) {
        this.deliveryPersonContact = deliveryPersonContact;
    }
}