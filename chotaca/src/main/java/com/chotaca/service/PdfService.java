package com.chotaca.service;

public interface PdfService {

    byte[] generateInvoicePdf(Long invoiceId);

}