package com.kuppuch.web;

import java.io.*;

import com.kuppuch.interseptor.RequestInterceptor;
import com.lowagie.text.pdf.BaseFont;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.xhtmlrenderer.layout.SharedContext;
import org.xhtmlrenderer.pdf.ITextRenderer;

import static org.jsoup.nodes.Entities.EscapeMode.xhtml;

@Controller
public class GenFileController {

    public static Logger log = LoggerFactory.getLogger(RequestInterceptor.class);

    @GetMapping("/get_file")
    public String getFile() {
        File inputHTML = new File("./src/main/resources/doc_sample/report.html");
        try {
            Document document = Jsoup.parse(inputHTML, "UTF-8");
            document.outputSettings().syntax(Document.OutputSettings.Syntax.xml);
            Element table = document.body().getElementById("timespent");

            table.append("<tr>\n" +
                    "            <td>Задача</td>\n" +
                    "            <td>Ответственная компетенция</td>\n" +
                    "            <td>Трудозатраты</td>\n" +
                    "        </tr>");

            OutputStream outputStream = new FileOutputStream("./src/main/resources/static/outputPdf.pdf");
            ITextRenderer renderer = new ITextRenderer();
            renderer.getFontResolver().addFont("./src/main/resources/static/Roboto-Thin.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);


            SharedContext sharedContext = renderer.getSharedContext();
            sharedContext.setPrint(true);
            sharedContext.setInteractive(false);
            renderer.setDocumentFromString(document.toString());
            renderer.layout();
            renderer.createPDF(outputStream);

        } catch (IOException e) {

        }


        return "index";
    }
}
