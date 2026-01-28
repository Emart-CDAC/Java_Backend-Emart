package com.example.controllers;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import com.example.model.Address;
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

        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20);
        Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        Font normal = FontFactory.getFont(FontFactory.HELVETICA, 11);
        Font small = FontFactory.getFont(FontFactory.HELVETICA, 9);

        /* ================= HEADER ================= */
        PdfPTable header = new PdfPTable(2);
        header.setWidthPercentage(100);
        header.setWidths(new float[] { 3, 1 });

        PdfPCell left = new PdfPCell(new Phrase("EMART INVOICE", titleFont));
        left.setBorder(0);

        PdfPCell right = new PdfPCell(new Phrase(
                "TOTAL\n₹ " + invoice.getTotalAmount(),
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        right.setHorizontalAlignment(Element.ALIGN_CENTER);
        right.setVerticalAlignment(Element.ALIGN_MIDDLE);
        right.setBackgroundColor(new Color(255, 165, 0));
        right.setBorder(0);

        header.addCell(left);
        header.addCell(right);
        document.add(header);

        document.add(new Paragraph("\n"));

        /* ================= SELLER & BUYER ================= */
        PdfPTable parties = new PdfPTable(2);
        parties.setWidthPercentage(100);

        parties.addCell(infoCell(
                "Sold By:\nEMART Pvt Ltd\nIndia",
                bold));

        parties.addCell(infoCell(
                "Bill To:\n" +
                        invoice.getCustomer().getFullName() + "\n" +
                        invoice.getCustomer().getEmail(),
                bold));

        document.add(parties);
        document.add(new Paragraph("\n"));

        /* ================= INVOICE META ================= */
        PdfPTable meta = new PdfPTable(3);
        meta.setWidthPercentage(100);

        meta.addCell(metaCell("Invoice No", invoice.getInvoiceId() + ""));
        meta.addCell(metaCell("Order ID", invoice.getOrder().getOrderId() + ""));
        meta.addCell(metaCell("Invoice Date", invoice.getOrderDate().toString()));

        document.add(meta);
        document.add(new Paragraph("\n"));

        /* ================= PRODUCT TABLE ================= */
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setWidths(new float[] { 4, 1, 2, 2 });

        addHeader(table, "Product");
        addHeader(table, "Qty");
        addHeader(table, "Price");
        addHeader(table, "Amount");

        java.math.BigDecimal subtotal = java.math.BigDecimal.ZERO;

        for (OrderItems i : items) {
            table.addCell(new PdfPCell(new Phrase(i.getProduct().getName(), normal)));
            table.addCell(centerCell(i.getQuantity() + ""));
            table.addCell(centerCell("Rs." + i.getPrice()));
            table.addCell(centerCell("Rs." + i.getSubtotal()));
            subtotal = subtotal.add(i.getSubtotal());
        }

        document.add(table);
        document.add(new Paragraph("\n"));

        /* ================= SUMMARY ================= */
        PdfPTable summary = new PdfPTable(2);
        summary.setWidthPercentage(40);
        summary.setHorizontalAlignment(Element.ALIGN_RIGHT);

        summary.addCell(sumCell("Subtotal"));
        summary.addCell(sumCell("₹ " + subtotal));

        summary.addCell(sumCell("Discount"));
        summary.addCell(sumCell("- ₹ " + invoice.getDiscountAmount()));

        summary.addCell(sumCell("Tax"));
        summary.addCell(sumCell("₹ " + invoice.getTaxAmount()));

        PdfPCell totalLabel = sumCell("TOTAL");
        totalLabel.setBackgroundColor(Color.LIGHT_GRAY);

        PdfPCell totalVal = sumCell("₹ " + invoice.getTotalAmount());
        totalVal.setBackgroundColor(Color.LIGHT_GRAY);

        summary.addCell(totalLabel);
        summary.addCell(totalVal);

        document.add(summary);
        document.add(new Paragraph("\n"));

        /* ================= E-POINTS ================= */
        PdfPTable points = new PdfPTable(2);
        points.setWidthPercentage(50);

        points.addCell(metaCell("E-Points Used", invoice.getEpointsUsed() + ""));
        points.addCell(metaCell("E-Points Earned", invoice.getEpointsEarned() + ""));
        points.addCell(metaCell("E-Points Balance", invoice.getEpointsBalance() + ""));

        document.add(points);

        document.add(new Paragraph("\n"));

        /* ================= NOTES ================= */
        document.add(new Paragraph(
                "NOTES:\n" +
                        "1. Amount includes applicable taxes.\n" +
                        "2. This is a system generated invoice.",
                small));
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

    private PdfPCell infoCell(String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setPadding(8);
        return cell;
    }

    private PdfPCell metaCell(String key, String value) {
        PdfPCell cell = new PdfPCell(new Phrase(key + "\n" + value));
        cell.setPadding(6);
        return cell;
    }

    private PdfPCell centerCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private PdfPCell sumCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11)));
        cell.setPadding(6);
        return cell;
    }

}
