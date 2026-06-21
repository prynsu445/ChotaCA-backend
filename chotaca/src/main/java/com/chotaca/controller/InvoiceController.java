package com.chotaca.controller;

import com.chotaca.dto.StatusUpdateDTO;
import com.chotaca.entity.Invoice;
import com.chotaca.service.InvoiceService;
import com.chotaca.service.PdfService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public Invoice saveInvoice(@Valid @RequestBody Invoice invoice) {
        return invoiceService.saveInvoice(invoice);
    }

    @GetMapping
    public List<Invoice> getAllInvoices() {

        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {

        return invoiceService.getInvoiceById(id);
    }

    @PutMapping("/{id}")
    public Invoice updateInvoice(@PathVariable Long id,
                                 @RequestBody Invoice invoice) {
        return invoiceService.updateInvoice(id, invoice);
    }

    @DeleteMapping("/{id}")
    public String deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return "Invoice Deleted Successfully";
    }


    @Autowired
    private PdfService pdfService;

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable Long id) {

        byte[] pdf = pdfService.generateInvoicePdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoice.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
    @GetMapping("/search")
    public List<Invoice> searchInvoices(
            @RequestParam String invoiceNo) {

        return invoiceService.searchInvoices(invoiceNo);
    }



    @PatchMapping("/{id}/status")
    public Invoice updateStatus(
            @PathVariable Long id,
            @RequestBody StatusUpdateDTO request) {

        return invoiceService.updateStatus(id, request.getStatus());
    }

    @GetMapping("/export/csv")
    public ResponseEntity<String> exportInvoicesCsv() {

        String csvData = invoiceService.generateInvoiceCsv();

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=invoices.csv")
                .body(csvData);
    }


}