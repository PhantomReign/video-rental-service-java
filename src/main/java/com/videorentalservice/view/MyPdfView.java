package com.videorentalservice.view;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.videorentalservice.models.Disc;
import com.videorentalservice.models.Order;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Ladislav on 30.04.2017.
 */
public class MyPdfView extends AbstractPdfView {

    private static final String FONT_REGULAR = "src/main/resources/static/fonts/OpenSans-Regular.ttf";
    private static final String FONT_BOLD = "src/main/resources/static/fonts/OpenSans-Bold.ttf";
    private static final String FONT_ITALIC_BOLD = "src/main/resources/static/fonts/OpenSans-BoldItalic.ttf";

    @Override
    protected void buildPdfDocument(Map<String, Object> model,
                                    Document document, PdfWriter writer, HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {

        Order order = (Order) model.get("order");

        BaseFont courier = BaseFont.createFont(FONT_REGULAR,  "Cp1250", BaseFont.EMBEDDED);
        BaseFont courierBold = BaseFont.createFont(FONT_BOLD,  "Cp1250", BaseFont.EMBEDDED);
        BaseFont courierItalicBold = BaseFont.createFont(FONT_ITALIC_BOLD,  "Cp1250", BaseFont.EMBEDDED);

        Font fontBigBold = new Font(courierBold, 14);
        Font fontRegular = new Font(courier, 12);
        Font fontItalicBold = new Font(courierItalicBold, 12);


        Paragraph pMainTit = new Paragraph();
        pMainTit.setFont(fontBigBold);
        pMainTit.add("Informácie o objednávke\n\n");
        pMainTit.setAlignment(Element.ALIGN_CENTER);
        document.add(pMainTit);

        Paragraph pUserTit = new Paragraph();
        pUserTit.setFont(fontItalicBold);
        pUserTit.add("Užívateľ:");
        pUserTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pUserTit);

        Paragraph pUser = new Paragraph();
        pUser.setFont(fontRegular);
        pUser.add(order.getUser().getUserName());
        pUser.setAlignment(Element.ALIGN_CENTER);
        document.add(pUser);


        Paragraph pEmailTit = new Paragraph();
        pEmailTit.setFont(fontItalicBold);
        pEmailTit.add("Email:");
        pEmailTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pEmailTit);

        Paragraph pEmail = new Paragraph();
        pEmail.setFont(fontRegular);
        pEmail.add(order.getUser().getEmail());
        pEmail.setAlignment(Element.ALIGN_CENTER);
        document.add(pEmail);


        Paragraph pPhoneTit = new Paragraph();
        pPhoneTit.setFont(fontItalicBold);
        pPhoneTit.add("Telefón:");
        pPhoneTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pPhoneTit);

        Paragraph pPhone = new Paragraph();
        pPhone.setFont(fontRegular);
        pPhone.add(order.getUser().getPhone());
        pPhone.setAlignment(Element.ALIGN_CENTER);
        document.add(pPhone);


        Paragraph pAdressTit = new Paragraph();
        pAdressTit.setFont(fontItalicBold);
        pAdressTit.add("Adresa:");
        pAdressTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pAdressTit);

        Paragraph pAdress = new Paragraph();
        pAdress.setFont(fontRegular);
        pAdress.add(order.getUser().getAddress());
        pAdress.setAlignment(Element.ALIGN_CENTER);
        document.add(pAdress);

        Paragraph pDivider = new Paragraph();
        pDivider.add("\n\n----------------------------------------------------------------------------------------------------------------------------------\n\n");
        pDivider.setAlignment(Element.ALIGN_LEFT);
        document.add(pDivider);

        Paragraph pOrdNumTit = new Paragraph();
        pOrdNumTit.setFont(fontItalicBold);
        pOrdNumTit.add("Číslo objednávky:");
        pOrdNumTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pOrdNumTit);

        Paragraph pOrdNum = new Paragraph();
        pOrdNum.setFont(fontRegular);
        pOrdNum.add(order.getOrderNumber());
        pOrdNum.setAlignment(Element.ALIGN_CENTER);
        document.add(pOrdNum);


        Paragraph pPriceTit = new Paragraph();
        pPriceTit.setFont(fontItalicBold);
        pPriceTit.add("Cena:");
        pPriceTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pPriceTit);

        Paragraph pPrice = new Paragraph();
        pPrice.setFont(fontRegular);
        pPrice.add(order.getPrice().toString() + " €");
        pPrice.setAlignment(Element.ALIGN_CENTER);
        document.add(pPrice);


        Paragraph pFromTit = new Paragraph();
        pFromTit.setFont(fontItalicBold);
        pFromTit.add("Od:");
        pFromTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pFromTit);

        Paragraph pFrom = new Paragraph();
        pFrom.setFont(fontRegular);
        pFrom.add(order.getFromDate());
        pFrom.setAlignment(Element.ALIGN_CENTER);
        document.add(pFrom);


        Paragraph pToTit = new Paragraph();
        pToTit.setFont(fontItalicBold);
        pToTit.add("Do:");
        pToTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pToTit);

        Paragraph pTo = new Paragraph();
        pTo.setFont(fontRegular);
        pTo.add(order.getToDate());
        pTo.setAlignment(Element.ALIGN_CENTER);
        document.add(pTo);


        Paragraph pStatusTit = new Paragraph();
        pStatusTit.setFont(fontItalicBold);
        pStatusTit.add("Stav:");
        pStatusTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pStatusTit);

        Paragraph pStatus = new Paragraph();
        pStatus.setFont(fontRegular);
        pStatus.add(order.getStatus());
        pStatus.setAlignment(Element.ALIGN_CENTER);
        document.add(pStatus);


        Paragraph pDiscTit = new Paragraph();
        pDiscTit.setFont(fontItalicBold);
        pDiscTit.add("Filmy:");
        pDiscTit.setAlignment(Element.ALIGN_LEFT);
        document.add(pDiscTit);

        for (Disc disc: order.getDiscs()) {
            Paragraph pDisc = new Paragraph();
            pDisc.setFont(fontRegular);
            pDisc.add(disc.getTitle());
            pDisc.setAlignment(Element.ALIGN_CENTER);
            document.add(pDisc);
        }

        FooterPageEvent event = new FooterPageEvent();
        writer.setPageEvent(event);
    }
}