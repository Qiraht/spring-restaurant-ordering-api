package com.qiraht.food_order.controller;

import com.qiraht.food_order.dto.ApiResponse;
import com.qiraht.food_order.dto.response.SalesReportResponse;
import com.qiraht.food_order.service.ReportService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/report")
@Validated
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/sales")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<SalesReportResponse>> getReportSales() {
        SalesReportResponse data = reportService.getSalesReport();

        return ResponseEntity.ok(
                ApiResponse.success("Sales report fetched successfully", data)
        );
    }
}
