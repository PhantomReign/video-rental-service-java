package com.videorentalservice.view;

/**
 * Created by Ladislav on 01.05.2017.
 */
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;


public class FooterPageEvent extends PdfPageEventHelper {

    private static final String COMPANY_LOGO = "src/main/resources/static/images/logo.png";

    public void onStartPage(PdfWriter writer, Document document) {
        /* Place for header */
    }

    public void onEndPage(PdfWriter writer, Document document) {

        try {
            Image img = Image.getInstance(COMPANY_LOGO);
            img.scaleToFit(515,100);
            img.setAbsolutePosition(30, 10);
            img.setAlignment(Element.ALIGN_CENTER);
            writer.getDirectContent().addImage(img);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}