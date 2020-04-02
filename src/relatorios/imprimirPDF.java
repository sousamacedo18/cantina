/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorios;

/**
 *
 * @author Home
 */

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import relatorios.HeaderFooter;


public class imprimirPDF {
    
    public static void main(String... args) throws FileNotFoundException, DocumentException, BadElementException, IOException {

        // create document
        //Document document = new Document(PageSize.A4, 36, 36, 90, 36);
        Document document = new Document(PageSize.A4,36, 36, 170, 72);
      
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("HeaderFooter.pdf"));

    
        
        HeaderFooter event = new HeaderFooter();
        
        writer.setPageEvent(event);

        // write to document
        document.open();
     
        // add header and footer
      
        int i;
        for(i=0;i<1000;i++){
        document.add(new Paragraph("DADOS DOS ESTUDANTES AQUI."));
        }
        //document.newPage();
        //document.add(new Paragraph("Adding a footer to PDF Document using iText."));
        document.close();
    }
}