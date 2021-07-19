package com.shkubel.project.service;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shkubel.project.models.entity.Invoice;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class PdfService {

    private Invoice invoice;

    public PdfService(Invoice invoice) {
        this.invoice = invoice;
    }
    public PdfService() {

    }

    private void writeTableHeader(PdfPTable table) {
        table.addCell("Hotel_ID");
        table.addCell("DESCRIPTION");
        table.addCell("PRICE PER DAY");
        table.addCell("QUANTITY (DAYS)");
        table.addCell("TOTAL");
    }

    private void writeTableData(PdfPTable table) {

        table.addCell(invoice.getSeller().getName());
        table.addCell("INVOICE TO:");

        table.addCell(invoice.getSeller().getAddress());
        table.addCell(invoice.getUser().getUserFirstname()+ ' ' + invoice.getUser().getUserLastname());

        table.addCell(invoice.getSeller().getBankAccount());
        table.addCell(invoice.getUser().getEmail());

    }

    public void export(HttpServletResponse response) throws IOException {
        Document document = new Document(PageSize.A4);

        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        PdfPTable table = new PdfPTable(2);
        table.setSpacingAfter(15);
        table.setWidthPercentage(100);

        writeTableData(table);
        document.add(table);

        document.add(new Paragraph("Invoice № " + invoice.getId()));
        document.add(new Paragraph("Date of Invoice: " + invoice.getCreatingDate()));
        document.add(new Paragraph("Due Date: " + invoice.getCreatingDate()+3));

        PdfPTable table1 = new PdfPTable(5);
        table1.setSpacingAfter(15);
        table1.setWidthPercentage(100);

        writeTableHeader(table1);

        document.add(table1);

        document.close();

    }

}