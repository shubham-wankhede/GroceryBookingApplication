package com.gb.store.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.gb.store.model.request.GroceryItemRequest;
import com.gb.store.model.response.GroceryItemResponse;
import com.gb.store.repo.entity.GroceryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Grocery Mapper utility class
 */
@Component
public class GroceryItemMapper {

    private final ObjectMapper objectMapper;

    @Autowired
    public GroceryItemMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Convert Grocery Item to GroceryItemResponse by copying Grocery Item values to GroceryItemResponse
     * @param groceryItem GroceryItem to be converted
     * @return converted GroceryItemResponse object by copying groceryItem values
     */
    public GroceryItemResponse mapToGroceryItemResponse(GroceryItem groceryItem){
        return objectMapper.convertValue(groceryItem,GroceryItemResponse.class);
    }

    /**
     * Convert GroceryItemRequest to GroceryItem by copying groceryItemRequest values to GroceryItem
     * @param groceryItemRequest GroceryItemRequest to be converted
     * @return converted GroceryItem object by copying groceryItemRequest values
     */
    public GroceryItem mapToGroceryItem(GroceryItemRequest groceryItemRequest){
        return objectMapper.convertValue(groceryItemRequest,GroceryItem.class);
    }

}
