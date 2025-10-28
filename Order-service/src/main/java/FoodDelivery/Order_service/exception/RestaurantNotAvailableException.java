package FoodDelivery.Order_service.exception;

public class RestaurantNotAvailableException extends RuntimeException {
    public RestaurantNotAvailableException(String message) {
        super(message);
    }
}