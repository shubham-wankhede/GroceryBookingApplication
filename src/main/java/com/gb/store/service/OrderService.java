package com.gb.store.service;

import com.gb.store.model.request.GroceryOrderRequest;
import com.gb.store.repo.OrderRepository;
import com.gb.store.repo.entity.GroceryItem;
import com.gb.store.repo.entity.OrderInfo;
import com.gb.store.repo.entity.OrderItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service to manage order details, transaction and manage inventory after order
 */
@Slf4j
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final GroceryService groceryService;

    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            GroceryService groceryService
    ) {
        this.orderRepository = orderRepository;
        this.groceryService = groceryService;
    }

    /**
     * Place an order by reducing the quanity of items from provided item id
     * and quantity to reduce
     * @param groceryOrderRequest grocery order request list
     */
    @Transactional
    public void placeOrder(List<GroceryOrderRequest> groceryOrderRequest) {
        Map<String, GroceryItem> processedGroceryItems = groceryOrderRequest.stream()
                .map(orderItem -> {
                    log.info("Ordered Item : {}", orderItem);
                    // Adjust inventory after successful booking (decrease quantities)
                    return groceryService.manageInventory(orderItem.getGroceryItemId(), orderItem.getQuantity());
                })
                .collect(Collectors.toMap(GroceryItem::getId, groceryItem -> groceryItem));

        //store the ordered items transaction
        if (!processedGroceryItems.isEmpty()) {
            OrderInfo orderInfo = new OrderInfo();
            List<OrderItem> orderItemList = groceryOrderRequest.stream()
                    .map(orderItem ->
                            OrderItem.builder()
                                    .groceryItem(processedGroceryItems.get(orderItem.getGroceryItemId()))
                                    .quantity(orderItem.getQuantity())
                                    .build()
                    )
                    .toList();

            orderInfo.setOrderItems(orderItemList);
            orderRepository.save(orderInfo);
        }
    }
}
