package com.chotaca.service;

import com.chotaca.repository.ClientRepository;
import com.chotaca.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Long getTotalClients() {
        return clientRepository.count();
    }

    public Long getTotalInvoices() {
        return invoiceRepository.count();
    }

    public Double getRevenue() {
        return invoiceRepository.getTotalRevenue();
    }

    public Long getPaidInvoices() {
        return invoiceRepository.countPaidInvoices();
    }

    public Long getUnpaidInvoices() {
        return invoiceRepository.countUnpaidInvoices();
    }

    public Double getPendingRevenue() {
        return invoiceRepository.getPendingRevenue();
    }


}