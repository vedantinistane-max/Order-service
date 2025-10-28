package FoodDelivery.Order_service.dto;

import java.math.BigDecimal;

public class RestaurantDto {
    private Long id;
    private String name;
    private String address;
    private String cuisine;
    private BigDecimal deliveryFee;
    private Integer deliveryTimeMinutes;
    private boolean isActive;
    private Double rating;
    public RestaurantDto() {}
    public RestaurantDto(Long id, String name, String address, String cuisine,
                         BigDecimal deliveryFee, Integer deliveryTimeMinutes,
                         boolean isActive, Double rating) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.cuisine = cuisine;
        this.deliveryFee = deliveryFee;
        this.deliveryTimeMinutes = deliveryTimeMinutes;
        this.isActive = isActive;
        this.rating = rating;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getCuisine() {
        return cuisine;
    }
    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }
    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }
    public void setDeliveryFee(BigDecimal deliveryFee) {
        this.deliveryFee = deliveryFee;
    }
    public Integer getDeliveryTimeMinutes() {
        return deliveryTimeMinutes;
    }
    public void setDeliveryTimeMinutes(Integer deliveryTimeMinutes) {
        this.deliveryTimeMinutes = deliveryTimeMinutes;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean active) {
        isActive = active;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
}