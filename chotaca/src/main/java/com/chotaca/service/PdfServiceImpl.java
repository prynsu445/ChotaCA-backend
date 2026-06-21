package com.chotaca.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPCell;
import com.chotaca.entity.Invoice;
import com.chotaca.entity.InvoiceItem;
import com.chotaca.repository.InvoiceRepository;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Override
    public byte[] generateInvoicePdf(Long invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        try {

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter.getInstance(document, out);

            document.open();

            Font titleFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD,
                            20
                    );

            Paragraph title =
                    new Paragraph(
                            "ChotaCA INVOICE",
                            titleFont
                    );

            title.setAlignment(Element.ALIGN_CENTER);

            document.add(title);
            document.add(new Paragraph(" "));

            document.add(new Paragraph("CHOTACA INVOICE"));
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Invoice No : " + invoice.getInvoiceNo()));
            document.add(new Paragraph("Date : " + invoice.getInvoiceDate()));
            document.add(new Paragraph("Total Amount : ₹" + invoice.getTotalAmount()));

            document.add(new Paragraph(" "));

            document.add(new Paragraph("Item\t\tQty\t\tPrice"));


            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);

            table.setWidthPercentage(100);

            Font headerFont =
                    FontFactory.getFont(
                            FontFactory.HELVETICA_BOLD
                    );

            PdfPCell itemHeader =
                    new PdfPCell(
                            new Phrase("Item", headerFont)
                    );

            PdfPCell qtyHeader =
                    new PdfPCell(
                            new Phrase("Qty", headerFont)
                    );

            PdfPCell priceHeader =
                    new PdfPCell(
                            new Phrase("Price", headerFont)
                    );

            table.addCell(itemHeader);
            table.addCell(qtyHeader);
            table.addCell(priceHeader);

            for (InvoiceItem item : invoice.getItems()) {

                table.addCell(item.getDescription());

                table.addCell(
                        String.valueOf(item.getQuantity())
                );

                table.addCell(
                        String.valueOf(item.getPrice())
                );
            }

            document.add(table);
            document.add(new Paragraph(" "));

            document.add(
                    new Paragraph(
                            "GST : ₹" + invoice.getGstAmount()
                    )
            );

            document.add(
                    new Paragraph(
                            "Grand Total : ₹"
                                    + (invoice.getTotalAmount()
                                    + invoice.getGstAmount())
                    )
            );

            document.close();

            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}