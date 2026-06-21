package com.chotaca.service;

import com.chotaca.dto.InvoiceItemDTO;
import com.chotaca.entity.Invoice;
import com.chotaca.entity.InvoiceItem;
import com.chotaca.repository.InvoiceItemRepository;
import com.chotaca.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvoiceItemService {

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public InvoiceItem createItem(InvoiceItem item) {

        item.setAmount(
                item.getQuantity() * item.getPrice()
        );

        InvoiceItem savedItem = invoiceItemRepository.save(item);

        Invoice invoice = savedItem.getInvoice();

        List<InvoiceItem> items =
                invoiceItemRepository.findByInvoice(invoice);

        Double total = items.stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();

        double gst = total * 0.18;

        invoice.setTotalAmount(total);
        invoice.setGstAmount(gst);

        invoiceRepository.save(invoice);

        return savedItem;
    }

    public List<InvoiceItem> getAllItems() {
        return invoiceItemRepository.findAll();
    }

    public InvoiceItem getItemById(Long id) {
        return invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item not found"));
    }

    public void deleteItem(Long id) {

        InvoiceItem item = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item not found"));

        Invoice invoice = item.getInvoice();

        invoiceItemRepository.delete(item);

        List<InvoiceItem> items =
                invoiceItemRepository.findByInvoice(invoice);

        Double total = items.stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();

        double gst = total * 0.18;

        invoice.setTotalAmount(total);
        invoice.setGstAmount(gst);

        invoiceRepository.save(invoice);
    }


    public InvoiceItem updateItem(Long id, InvoiceItem updatedItem) {

        InvoiceItem item = invoiceItemRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Item not found"));

        item.setDescription(updatedItem.getDescription());
        item.setQuantity(updatedItem.getQuantity());
        item.setPrice(updatedItem.getPrice());

        item.setAmount(
                updatedItem.getQuantity() * updatedItem.getPrice()
        );

        InvoiceItem updated = invoiceItemRepository.save(item);

        Invoice invoice = updated.getInvoice();

        List<InvoiceItem> items =
                invoiceItemRepository.findByInvoice(invoice);

        Double total = items.stream()
                .mapToDouble(InvoiceItem::getAmount)
                .sum();

        double gst = total * 0.18;

        invoice.setTotalAmount(total);
        invoice.setGstAmount(gst);

        invoiceRepository.save(invoice);

        return updated;
    }
    public InvoiceItemDTO convertToDTO(InvoiceItem item) {

        InvoiceItemDTO dto = new InvoiceItemDTO();

        dto.setId(item.getId());
        dto.setDescription(item.getDescription());
        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());
        dto.setAmount(item.getAmount());

        return dto;
    }

    public List<InvoiceItemDTO> getAllInvoiceItemDTOs() {

        return invoiceItemRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

}