package com.shkubel.project.service;

import com.shkubel.project.service.impl.InvoiceServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Service
public class PdfService {

    private static final String PDF_RESOURCES = "/pdf-resources/";
    private InvoiceServiceImpl invoiceService;
    private SpringTemplateEngine templateEngine;

    @Autowired
    public PdfService(InvoiceServiceImpl invoiceService, SpringTemplateEngine templateEngine) {
        this.invoiceService = invoiceService;
        this.templateEngine = templateEngine;
    }

    public File generatePdf() throws IOException, com.lowagie.text.DocumentException {
        Context context = getContext();
        String html = loadAndFillTemplate(context);
        return renderPdf(html);
    }


    private File renderPdf(String html) throws IOException, com.lowagie.text.DocumentException {
        File file = File.createTempFile("students", ".pdf");
        OutputStream outputStream = new FileOutputStream(file);
        ITextRenderer renderer = new ITextRenderer(20f * 4f / 3f, 20);
        renderer.setDocumentFromString(html, new ClassPathResource(PDF_RESOURCES).getURL().toExternalForm());
        renderer.layout();
        renderer.createPDF(outputStream);
        outputStream.close();
        file.deleteOnExit();
        return file;
    }

    private Context getContext() {
        Context context = new Context();
        context.setVariable("invoices", invoiceService.findInvoiceById(23L));
        return context;
    }

    private String loadAndFillTemplate(Context context) {
        return templateEngine.process("invoice/new", context);
    }


}