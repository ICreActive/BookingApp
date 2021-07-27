package com.shkubel.project.service.impl;

import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.shkubel.project.models.entity.Invoice;
import com.shkubel.project.service.PdfService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfServiceImpl implements PdfService {

    @Value("${pdf.path}")
    private String fileName;

    private Invoice invoice;

    public PdfServiceImpl(Invoice invoice) {
        this.invoice = invoice;
    }

    public PdfServiceImpl() {

    }

    public void exportToPdf(HttpServletResponse response) {

        PdfReader reader = null;

        try {
            reader = new PdfReader(fileName);
            PdfStamper stamper = new PdfStamper(reader, response.getOutputStream());

            AcroFields form = stamper.getAcroFields();
            form.setField("sName", invoice.getSeller().getName());
            form.setField("sBankAccount", invoice.getSeller().getBankAccount());
            form.setField("sAddress", invoice.getSeller().getAddress());

            form.setField("uName", invoice.getUser().getUserFirstname() + ' ' + invoice.getUser().getUserLastname());
            form.setField("uEmail", invoice.getUser().getEmail());

            form.setField("invoice", "Invoice № " + invoice.getId().toString());
            form.setField("iCrDate", invoice.getCreatingDate());
            form.setField("iDateDue", invoice.getCreatingDate() + 3);

            form.setField("oCheckIn", invoice.getOrderUser().getLocalDateStart().format(DateTimeFormatter.ISO_DATE));
            form.setField("oCheckOut", invoice.getOrderUser().getLocalDateFinish().format(DateTimeFormatter.ISO_DATE));
            form.setField("oKlass", invoice.getRoom().getKlassApartment().getName());
            form.setField("oNumSeat", String.valueOf(invoice.getRoom().getNumberOfSeats()));

            form.setField("hName", invoice.getRoom().getTitle());
            form.setField("hDescription", invoice.getRoom().getDescription());
            form.setField("hPrice", String.valueOf(invoice.getRoom().getPrice()));
            form.setField("hTotal", String.valueOf((invoice.getRoom().getPrice()) * invoice.getPeriod()));

            form.setField("hPeriod", String.valueOf(invoice.getPeriod()));

            stamper.setFormFlattening(true);
            stamper.close();
            reader.close();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            assert reader != null;
            reader.close();
        }
    }

    @Override
    public Invoice setInvoice(Invoice invoice) {
        return this.invoice = invoice;
    }


}