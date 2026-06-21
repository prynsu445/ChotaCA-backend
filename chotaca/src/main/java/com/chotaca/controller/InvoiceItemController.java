package com.chotaca.controller;

import com.chotaca.entity.InvoiceItem;
import com.chotaca.service.InvoiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class InvoiceItemController {

    @Autowired
    private InvoiceItemService invoiceItemService;

    @PostMapping
    public InvoiceItem createItem(@RequestBody InvoiceItem item) {
        return invoiceItemService.createItem(item);
    }

    @GetMapping
    public List<InvoiceItem> getAllItems() {
        return invoiceItemService.getAllItems();
    }

    @GetMapping("/{id}")
    public InvoiceItem getItemById(@PathVariable Long id) {
        return invoiceItemService.getItemById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        invoiceItemService.deleteItem(id);
    }

    @PutMapping("/{id}")
    public InvoiceItem updateItem(
            @PathVariable Long id,
            @RequestBody InvoiceItem item) {

        return invoiceItemService.updateItem(id, item);
    }


}