package com.qiraht.food_order.helper;

import com.qiraht.food_order.dto.response.MenuResponse;
import com.qiraht.food_order.entity.Menu;

public class MenuMapper {
    public static MenuResponse toResponse(Menu menu) {
        return new MenuResponse(
                menu.getId().toString(),
                menu.getName(),
                menu.getDescription(),
                menu.getPrice(),
                menu.getStock());
    }
}
