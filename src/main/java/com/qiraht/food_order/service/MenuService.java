package com.qiraht.food_order.service;

import com.qiraht.food_order.dto.request.MenuRequest;
import com.qiraht.food_order.dto.response.MenuResponse;
import com.qiraht.food_order.entity.Menu;
import com.qiraht.food_order.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class MenuService {
    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public String createMenu(MenuRequest request) {
        Menu menu = Menu.builder()
                .name(request.name())
                .description(request.description())
                .stock(request.stock())
                .price(request.price())
                .isAvailable(true)
                .build();

        menuRepository.save(menu);
        log.info("Menu with id {} created successfully", menu.getId());

        return menu.getId().toString();
    }

    public List<MenuResponse> getAllMenus() {
        List<Menu> menus = menuRepository.findAll();

        return menus.stream().map(m ->  new MenuResponse(
                m.getId().toString(),
                m.getName(),
                m.getDescription(),
                m.getPrice(),
                m.getStock(),
                m.getIsAvailable()
        )).toList();
    }

    public MenuResponse getMenuById(String id) {
        UUID menuId = UUID.fromString(id);

        Menu menu = menuRepository.findById(menuId).orElse(() -> log.error("menu with id {} not found" , id));

        return new MenuResponse(
                        menu.getId().toString(),
                        menu.getName(),
                        menu.getDescription(),
                        menu.getPrice(),
                        menu.getStock(),
                        menu.getIsAvailable());
    }
}
