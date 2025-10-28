package FoodDelivery.Order_service.config;

import FoodDelivery.Order_service.entity.Order;
import FoodDelivery.Order_service.entity.OrderItem;
import FoodDelivery.Order_service.entity.OrderStatus;
import FoodDelivery.Order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final OrderRepository orderRepository;

    @Autowired
    public DataInitializer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Create sample orders for testing
        if (orderRepository.count() == 0) {
            createSampleOrders();
        }
    }

    private void createSampleOrders() {
        // Sample Order 1
        Order order1 = new Order();
        order1.setUserId(1L);
        order1.setRestaurantId(1L);
        order1.setTotalAmount(new BigDecimal("29.99"));
        order1.setStatus(OrderStatus.DELIVERED);
        order1.setDeliveryAddress("123 Main St, City, State 12345");
        order1.setSpecialInstructions("Leave at door");
        order1.setOrderDate(LocalDateTime.now().minusDays(1));
        order1.setDeliveryDate(LocalDateTime.now().minusDays(1).plusHours(1));
        order1.setOrderItems(Arrays.asList(
                new OrderItem(101L, "Margherita Pizza", 1, new BigDecimal("24.99")),
                new OrderItem(102L, "Garlic Bread", 1, new BigDecimal("4.99"))
        ));

        // Sample Order 2
        Order order2 = new Order();
        order2.setUserId(1L);
        order2.setRestaurantId(2L);
        order2.setTotalAmount(new BigDecimal("18.50"));
        order2.setStatus(OrderStatus.OUT_FOR_DELIVERY);
        order2.setDeliveryAddress("456 Oak Ave, City, State 12345");
        order2.setSpecialInstructions("Ring doorbell");
        order2.setOrderDate(LocalDateTime.now().minusHours(2));
        order2.setOrderItems(Arrays.asList(
                new OrderItem(201L, "Chicken Burger", 1, new BigDecimal("12.99")),
                new OrderItem(202L, "French Fries", 1, new BigDecimal("5.49"))
        ));

        // Sample Order 3
        Order order3 = new Order();
        order3.setUserId(2L);
        order3.setRestaurantId(1L);
        order3.setTotalAmount(new BigDecimal("35.75"));
        order3.setStatus(OrderStatus.PREPARING);
        order3.setDeliveryAddress("789 Pine St, City, State 12345");
        order3.setSpecialInstructions("No onions");
        order3.setOrderDate(LocalDateTime.now().minusMinutes(30));
        order3.setOrderItems(Arrays.asList(
                new OrderItem(103L, "Pepperoni Pizza", 1, new BigDecimal("27.99")),
                new OrderItem(104L, "Caesar Salad", 1, new BigDecimal("7.75"))
        ));

        orderRepository.saveAll(Arrays.asList(order1, order2, order3));
        System.out.println("Sample orders created successfully!");
    }
}