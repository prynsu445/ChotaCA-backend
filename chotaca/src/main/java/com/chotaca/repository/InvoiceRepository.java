package com.chotaca.repository;

import com.chotaca.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    @Query("SELECT COALESCE(SUM(i.totalAmount),0) FROM Invoice i")
    Double getTotalRevenue();

    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.status='PAID'")
    Long countPaidInvoices();

    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.status = 'PENDING'")
    Long countUnpaidInvoices();

    List<Invoice> findByInvoiceNoContainingIgnoreCase(String invoiceNo);

    @Query("""
       SELECT COALESCE(SUM(i.totalAmount),0)
       FROM Invoice i
       WHERE i.status = 'UNPAID'
       """)
    Double getPendingRevenue();
}