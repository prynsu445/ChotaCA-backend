package com.chotaca.controller;

import com.chotaca.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/clients")
    public Long totalClients() {
        return dashboardService.getTotalClients();
    }

    @GetMapping("/invoices")
    public Long totalInvoices() {
        return dashboardService.getTotalInvoices();
    }

    @GetMapping("/revenue")
    public Double revenue() {
        return dashboardService.getRevenue();
    }

    @GetMapping("/paid-invoices")
    public Long paidInvoices() {
        return dashboardService.getPaidInvoices();
    }
    @GetMapping("/unpaid-invoices")
    public Long unpaidInvoices() {
        return dashboardService.getUnpaidInvoices();
    }
    @GetMapping("/total-clients")
    public Long getTotalClients() {
        return dashboardService.getTotalClients();
    }

    @GetMapping("/total-invoices")
    public Long getTotalInvoices() {
        return dashboardService.getTotalInvoices();
    }

    @GetMapping("/total-revenue")
    public Double getTotalRevenue() {
        return dashboardService.getRevenue();
    }

    @GetMapping("/pending-revenue")
    public Double getPendingRevenue() {
        return dashboardService.getPendingRevenue();
    }

    @GetMapping("/summary")
    public Map<String, Object> dashboardSummary() {

        Map<String, Object> dashboard = new HashMap<>();

        dashboard.put("totalClients", dashboardService.getTotalClients());
        dashboard.put("totalInvoices", dashboardService.getTotalInvoices());
        dashboard.put("totalRevenue", dashboardService.getRevenue());
        dashboard.put("pendingRevenue", dashboardService.getPendingRevenue());

        return dashboard;
    }


}