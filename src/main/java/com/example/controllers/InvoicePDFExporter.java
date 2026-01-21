package com.example.controllers;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.model.Invoice;
import com.example.model.OrderItems;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import jakarta.servlet.http.HttpServletResponse;

public class InvoicePDFExporter {

    private final Invoice invoice;
    private final List<OrderItems> items;

   
    public InvoicePDFExporter(Invoice invoice, List<OrderItems> items) {
        this.invoice = invoice;
        this.items = items;
    }

   
    public byte[] generatePdfBytes() {

        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);
            document.open();

            writeContent(document);

            document.close();
            return baos.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }
    }

    
    public void export(HttpServletResponse response)
            throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        writeContent(document);

        document.close();
    }

    
    private void writeContent(Document document) throws DocumentException {

        Font title = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Font header = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font text = FontFactory.getFont(FontFactory.HELVETICA, 11);

        Paragraph p = new Paragraph("EMART INVOICE\n\n", title);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);

        addLine(document, "Invoice ID", String.valueOf(invoice.getInvoiceId()), header, text);
        addLine(document, "Order ID", String.valueOf(invoice.getOrder().getOrderId()), header, text);
        addLine(document, "Order Date", invoice.getOrderDate().toString(), header, text);
        addLine(document, "Customer", invoice.getCustomer().getFullName(), header, text);
        addLine(document, "Email", invoice.getCustomer().getEmail(), header, text);

        document.add(new Paragraph("\n"));

        
        var a = invoice.getOrder().getAddress();
        String address =
                (a.getHouseNumber() != null ? a.getHouseNumber() + ", " : "") +
                a.getTown() + ", " +
                a.getCity() + " - " +
                a.getPincode() + ", " +
                a.getState();

        addLine(document, "Billing Address", address, header, text);
        addLine(document, "Shipping Address", address, header, text);

        document.add(new Paragraph("\n"));

        
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{4, 1, 2, 2});

        addHeader(table, "Product");
        addHeader(table, "Qty");
        addHeader(table, "Price");
        addHeader(table, "Subtotal");

        double subtotal = 0;

        for (OrderItems i : items) {
            table.addCell(i.getProduct().getName());
            table.addCell(String.valueOf(i.getQuantity()));
            table.addCell(String.valueOf(i.getPrice()));
            table.addCell(String.valueOf(i.getSubtotal()));
            subtotal += i.getSubtotal();
        }

        document.add(table);
        document.add(new Paragraph("\n"));

        
        addLine(document, "Subtotal", String.valueOf(subtotal), header, text);
        addLine(document, "Discount", String.valueOf(invoice.getDiscountAmount()), header, text);
        addLine(document, "Tax", String.valueOf(invoice.getTaxAmount()), header, text);
        addLine(document, "Total Amount", String.valueOf(invoice.getTotalAmount()), header, text);

        document.add(new Paragraph("\n"));

        
        addLine(document, "E-Points Used", String.valueOf(invoice.getEpointsUsed()), header, text);
        addLine(document, "E-Points Earned", String.valueOf(invoice.getEpointsEarned()), header, text);
        addLine(document, "E-Points Balance", String.valueOf(invoice.getEpointsBalance()), header, text);
    }

    private void addHeader(PdfPTable table, String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setBackgroundColor(Color.LIGHT_GRAY);
        table.addCell(cell);
    }

    private void addLine(Document doc, String key, String value,
                         Font keyFont, Font valFont) throws DocumentException {
        Paragraph p = new Paragraph();
        p.add(new Chunk(key + ": ", keyFont));
        p.add(new Chunk(value, valFont));
        doc.add(p);
    }
}
