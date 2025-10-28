package FoodDelivery.Order_service.client;

import FoodDelivery.Order_service.dto.RestaurantDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "restaurant-service", url = "${restaurant.service.url:http://localhost:8081}")
public interface RestaurantServiceClient {
    @GetMapping("/restaurants/{restaurantId}")
    RestaurantDto getRestaurantById(@PathVariable("restaurantId") Long restaurantId);

    @GetMapping("/restaurants/{restaurantId}/availability")
    boolean isRestaurantAvailable(@PathVariable("restaurantId") Long restaurantId);
}