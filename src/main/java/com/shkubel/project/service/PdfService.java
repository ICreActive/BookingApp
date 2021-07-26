package com.shkubel.project.service;

import com.shkubel.project.models.entity.Invoice;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface PdfService {

    void exportToPdf(HttpServletResponse response) throws IOException;

    Invoice setInvoice(Invoice invoice);

}