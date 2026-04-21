package com.qiraht.food_order.service;

import com.qiraht.food_order.dto.request.MenuRequest;
import com.qiraht.food_order.entity.Menu;
import com.qiraht.food_order.exception.NotFoundException;
import com.qiraht.food_order.exception.ValidationException;
import com.qiraht.food_order.repository.MenuRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Menu getMenuById(String id) {
        UUID menuId = UUID.fromString(id);

        return menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("menu with id " + id +" not found"));
    }

    public void editMenuById(String id, MenuRequest request) {
        UUID menuId = UUID.fromString(id);

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("menu with id " + id +" not found"));

        menu.setName(request.name());
        menu.setDescription(request.description());
        menu.setPrice(request.price());
        menu.setStock(request.stock());

        menuRepository.save(menu);
    }

    public void deleteMenuById(String id) {
        UUID menuId = UUID.fromString(id);

        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new NotFoundException("menu with id " + id +" not found"));

        menu.setDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public void checkoutMenuById(String id, Integer quantity) {
        UUID menuId = UUID.fromString(id);

        Menu menu = menuRepository.findByIdForUpdate(menuId)
                .orElseThrow(() -> new ValidationException("menu not found"));

        // Stock validation
        if (quantity > menu.getStock()) {
            throw new ValidationException("Item stock not enough");
        }

        // Deleted validation
        if (menu.getDeletedAt() != null) {
            throw new ValidationException("Item is not available");
        }

        menu.setStock(menu.getStock() - quantity);

        menuRepository.save(menu);
    }
}
