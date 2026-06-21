package com.chotaca.repository;

import com.chotaca.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByClientNameContainingIgnoreCase(String clientName);

    @Query("""
       SELECT DISTINCT c
       FROM Client c
       JOIN c.invoices i
       WHERE i.status = 'UNPAID'
       """)
    List<Client> findClientsWithUnpaidInvoices();

}