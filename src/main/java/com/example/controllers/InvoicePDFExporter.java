package com.example.controllers;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import com.example.model.Invoice;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import jakarta.servlet.http.HttpServletResponse;

public class InvoicePDFExporter {
	
	private List<Invoice> invoiceList;

    public InvoicePDFExporter(List<Invoice> invoiceList) {
        this.invoiceList = invoiceList;
    }

    private void writeTableHeader(PdfPTable table) {

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        cell.setBackgroundColor(Color.BLUE);

        Font font = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);

        cell.setPhrase(new Phrase("Invoice ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Customer ID", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Order Date", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Payment Method", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Discount Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Tax Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Total Amount", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Delivery Type", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Billing Address", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Shipping Address", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Epoints Used", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Epoints Balance", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Epoints Earned", font));
        table.addCell(cell);
    }

    private void writeTableData(PdfPTable table) {

        for (Invoice invo : invoiceList) {

            table.addCell(String.valueOf(invo.getInvoice_id()));

            table.addCell(invo.getOrder() != null
                    ? String.valueOf(invo.getOrder().getOrderId())
                    : "N/A");

            table.addCell(invo.getCustomer() != null
                    ? String.valueOf(invo.getCustomer().getUserId())
                    : "N/A");

            table.addCell(invo.getOrder_date() != null
                    ? invo.getOrder_date().toString()
                    : "N/A");

            table.addCell(invo.getPaymentMethod() != null
                    ? invo.getPaymentMethod().toString()
                    : "N/A");

            table.addCell(invo.getDiscount_amount() != null
                    ? invo.getDiscount_amount().toString()
                    : "0.00");

            table.addCell(invo.getTax_amount() != null
                    ? invo.getTax_amount().toString()
                    : "0.00");

            table.addCell(invo.getTotal_amount() != null
                    ? invo.getTotal_amount().toString()
                    : "0.00");

            table.addCell(invo.getDelivery_type() != null
                    ? invo.getDelivery_type().toString()
                    : "N/A");

            table.addCell(invo.getBilling_address() != null
                    ? invo.getBilling_address()
                    : "N/A");

            table.addCell(invo.getShipping_address() != null
                    ? invo.getShipping_address()
                    : "N/A");

            table.addCell(String.valueOf(invo.getEpoints_used()));

            table.addCell(String.valueOf(invo.getEpoints_balance()));

            table.addCell(String.valueOf(invo.getEpoints_earned()));
        }
    }
     
    public void export(HttpServletResponse response) throws DocumentException, IOException {

        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p = new Paragraph("Invoice Report", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(p);

        PdfPTable table = new PdfPTable(14);
        table.setWidthPercentage(100f);
        table.setSpacingBefore(10);

        table.setWidths(new float[]{
                2.5f, 3.0f, 3.0f, 4.0f, 3.0f,
                3.0f, 3.0f, 3.0f, 3.5f, 5.0f,
                5.0f, 3.0f, 3.0f, 3.0f
        });

        writeTableHeader(table);
        writeTableData(table);

        document.add(table);
        document.close();
    }

}
