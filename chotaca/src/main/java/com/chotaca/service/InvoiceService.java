package com.chotaca.service;

import com.chotaca.dto.InvoiceDTO;
import com.chotaca.entity.Invoice;
import com.chotaca.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Invoice saveInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public Invoice updateInvoice(Long id, Invoice updatedInvoice) {

        Invoice invoice = invoiceRepository.findById(id).orElse(null);

        if (invoice != null) {

            invoice.setInvoiceNo(updatedInvoice.getInvoiceNo());
            invoice.setInvoiceDate(updatedInvoice.getInvoiceDate());
            invoice.setTotalAmount(updatedInvoice.getTotalAmount());
            invoice.setGstAmount(updatedInvoice.getGstAmount());
            invoice.setStatus(updatedInvoice.getStatus());

            return invoiceRepository.save(invoice);
        }

        return null;
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    public List<Invoice> searchInvoices(String invoiceNo) {
        return invoiceRepository
                .findByInvoiceNoContainingIgnoreCase(invoiceNo);
    }

    public InvoiceDTO convertToDTO(Invoice invoice) {

        InvoiceDTO dto = new InvoiceDTO();

        dto.setId(invoice.getId());
        dto.setInvoiceNo(invoice.getInvoiceNo());
        dto.setInvoiceDate(invoice.getInvoiceDate());
        dto.setTotalAmount(invoice.getTotalAmount());
        dto.setGstAmount(invoice.getGstAmount());
        dto.setStatus(invoice.getStatus());

        return dto;
    }

    public List<InvoiceDTO> getAllInvoiceDTOs() {

        return invoiceRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }
    public Invoice updateStatus(Long id, String status) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice Not Found"));

        invoice.setStatus(status);

        return invoiceRepository.save(invoice);
    }

    public String generateInvoiceCsv() {

        StringBuilder csv = new StringBuilder();

        csv.append("Id,InvoiceNo,Date,TotalAmount,GstAmount,Status\n");

        List<Invoice> invoices = invoiceRepository.findAll();

        for (Invoice invoice : invoices) {

            csv.append(invoice.getId()).append(",");
            csv.append(invoice.getInvoiceNo()).append(",");
            csv.append(invoice.getInvoiceDate()).append(",");
            csv.append(invoice.getTotalAmount()).append(",");
            csv.append(invoice.getGstAmount()).append(",");
            csv.append(invoice.getStatus()).append("\n");
        }

        return csv.toString();
    }


}