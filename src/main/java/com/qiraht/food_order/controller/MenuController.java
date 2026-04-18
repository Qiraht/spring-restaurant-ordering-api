package com.qiraht.food_order.controller;

import com.qiraht.food_order.dto.ApiResponse;
import com.qiraht.food_order.dto.request.MenuRequest;
import com.qiraht.food_order.dto.response.MenuResponse;
import com.qiraht.food_order.service.MenuService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Validated
public class MenuController {
    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<String>> postMenu(@Valid @RequestBody MenuRequest request) {
        String data = menuService.createMenu(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(
                ApiResponse.success("menu successfully created", data)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<MenuResponse>>> getMenus() {
        List<MenuResponse> data = menuService.getAllMenus();

        return ResponseEntity.ok(
                ApiResponse.success("success", data)
        );
    }

    @GetMapping("/{menuId}")
    public ResponseEntity<ApiResponse<MenuResponse>> getMenuById(@PathVariable("menuId") String id) {
        MenuResponse data = menuService.getMenuById(id);

        return ResponseEntity.ok(
                ApiResponse.success("success", data)
        );
    }

    @PutMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Void>> putMenuById(@PathVariable("menuId") String id, @Valid @RequestBody MenuRequest request) {
        menuService.editMenuById(id, request);

        return ResponseEntity.ok(
                ApiResponse.success("Menu edited successfully")
        );
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<ApiResponse<Void>> deleteMenuById(@PathVariable("menuId") String id) {
        menuService.deleteMenuById(id);

        return ResponseEntity.noContent().build();
    }
}
