//package com.memorynotfound.pdf.itext;
package relatorios;
import Dao.UsuarioDao;
import View.Arquivo;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;

import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static jdk.nashorn.internal.objects.NativeString.trim;

public class HeaderFooter extends PdfPageEventHelper {

    private PdfTemplate t;
    private Image total;
    
           SimpleDateFormat out = new SimpleDateFormat("HH:mm");
           String horario = out.format(new Date());
           String usuario = null;
           
   public String datahoje(){
	//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	Date date = new Date(); 
	return dateFormat.format(date);
   }
   public String lerLogin() throws SQLException{
       String arq = "logar.txt";
       if(!new File(arq).exists()) {
           JOptionPane.showMessageDialog(null, "O arquivo que guarda o login, não foi encontrado");
       }else{
       String texto=trim(Arquivo.Read(arq));
       if(texto.isEmpty()){
           JOptionPane.showMessageDialog(null, "Não foi possível ler o login guardado");
       }else{
           UsuarioDao usudao = new UsuarioDao();
           usuario=usudao.pegarnome(texto);
           return usuario;
       }
       }
       return null;
   }   

    public void onOpenDocument(PdfWriter writer, Document document) {
        t = writer.getDirectContent().createTemplate(30, 16);
        try {
            total = Image.getInstance(t);
            total.setRole(PdfName.ARTIFACT);
        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        try {
            //addHeader(writer);
            addFooter(writer);
        } catch (SQLException ex) {
            Logger.getLogger(HeaderFooter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addHeader(PdfWriter writer){
        PdfPTable header = new PdfPTable(1);
        try {
            // set defaults
            header.setWidths(new int[]{24});
            header.setTotalWidth(527);
            //header.setLockedWidth(true);
            header.getDefaultCell().setFixedHeight(60);
            //header.getDefaultCell().setBorder(Rectangle.BOTTOM);
            //header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);


            // add image
            //Image logo = Image.getInstance(testeRelatorio.class.getResource("brasaoruplica.gif"));
            Image img = Image.getInstance("brasaoruplica.gif");
            header.addCell(img);

             //add text
            PdfPCell text = new PdfPCell();
            text.setPaddingBottom(15);
            text.setPaddingLeft(10);
            text.setBorder(Rectangle.BOTTOM);
            text.setBorderColor(BaseColor.LIGHT_GRAY);
            
            text.addElement(new Phrase("Ministério da Educação", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("Secretária de Educação Profissional e Tecnológia", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("Instituto Federal de Educação, Ciência e Tecnológia do Tocantins", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("Campus Araguaína", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("Gerência de Ensino", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("Coordenação de Assitência Estudantil", new Font(Font.FontFamily.HELVETICA, 12)));
            text.addElement(new Phrase("http://araguaina.ifto.edu.br/portal/"+" "+datahoje()+" horário: "+horario, new Font(Font.FontFamily.HELVETICA, 8)));
            header.addCell(text);

            // write content
            header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (MalformedURLException e) {
            throw new ExceptionConverter(e);
        } catch (IOException e) {
            throw new ExceptionConverter(e);
        }
    }

    private void addFooter(PdfWriter writer) throws SQLException{
        PdfPTable footer = new PdfPTable(3); 
        try {
            // set defaults
            footer.setWidths(new int[]{22, 4, 1});
            footer.setTotalWidth(527);
            footer.setLockedWidth(true);
            footer.getDefaultCell().setFixedHeight(20);
            footer.getDefaultCell().setBorder(Rectangle.TOP);
            footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);

            // add copyright
            //footer.addCell(new Phrase("\u00A9 Memorynotfound.com", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
            footer.addCell(new Phrase("Impresso em: "+datahoje() + " às "+horario+"  Por: "+lerLogin(), new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
//            footer.addCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
//            footer.addCell(new Phrase("", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
//            footer.getDefaultCell().setBorder(Rectangle.TOP);
//            footer.getDefaultCell().setBorderColor(BaseColor.WHITE);
//            footer.addCell(new Phrase("Desenvolvido por Carlos Henrique Sousa de Macêdo", new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD)));
//                        // add current page count
            footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            footer.addCell(new Phrase(String.format("Página %d de", writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)));
                      
            // add placeholder for total page count
            PdfPCell totalPageCount = new PdfPCell(total);
            totalPageCount.setBorder(Rectangle.TOP);
            totalPageCount.setBorderColor(BaseColor.WHITE);
            footer.addCell(totalPageCount);

            // write page
            PdfContentByte canvas = writer.getDirectContent();
            canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
            footer.writeSelectedRows(0, -1, 34, 50, canvas);
            canvas.endMarkedContentSequence();
        } catch(DocumentException de) {
            throw new ExceptionConverter(de);
        }
    }

    public void onCloseDocument(PdfWriter writer, Document document) {
        int totalLength = String.valueOf(writer.getPageNumber()).length();
        int totalWidth = totalLength * 5;
        ColumnText.showTextAligned(t, Element.ALIGN_RIGHT,
                new Phrase(String.valueOf(writer.getPageNumber()), new Font(Font.FontFamily.HELVETICA, 8)),
                totalWidth, 6, 0);
    }
}
