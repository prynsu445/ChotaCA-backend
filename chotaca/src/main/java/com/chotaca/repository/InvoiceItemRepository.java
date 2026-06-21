package com.chotaca.repository;


import java.util.List;
import com.chotaca.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.chotaca.entity.InvoiceItem;

@Repository
public interface InvoiceItemRepository
        extends JpaRepository<InvoiceItem, Long> {
    List<InvoiceItem> findByInvoice(Invoice invoice);

}