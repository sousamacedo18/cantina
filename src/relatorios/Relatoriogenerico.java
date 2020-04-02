/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorios;
import Controller.AlunoController;
import Controller.RefeitorioController;
import Dao.AlunoDao;
import Dao.CreditoDao;
import Dao.CursoDao;
import Dao.TurmaDao;
import Dao.RefeitorioDao;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.ICC_Profile;

import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfDate;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.toedter.calendar.JDateChooser;


import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import javax.swing.table.DefaultTableCellRenderer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import javax.swing.text.StyleConstants.FontConstants;
import static javax.swing.text.StyleConstants.FontFamily;

/**
 *
 * @author Home
 */

public class Relatoriogenerico extends PdfPageEventHelper {
     private static final Locale BRAZIL = new Locale("pt","BR");
     private static final DecimalFormatSymbols REAL = new DecimalFormatSymbols(BRAZIL);
     public static final DecimalFormat DINHEIRO_REAL = new DecimalFormat("¤ ###,###,##0.00",REAL);
     public static String mascaraDinheiro(double valor, DecimalFormat moeda){
        return moeda.format(valor);
    }
   public void onStartPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), 30, 800, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), 550, 800, 0);
    }

    public void onEndPage(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("http://www.xxxx-your_example.com/"), 110, 30, 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("page " + document.getPageNumber()), 550, 30, 0);
    }
     protected String horahoje(java.util.Date dtData){
       SimpleDateFormat formatBra;   
       //formatBra = new SimpleDateFormat("dd/MM/yyyy");
       formatBra = new SimpleDateFormat("HH:mm:ss");
       try {
          Date newData = formatBra.parse(dtData.toString());
          return (formatBra.format(newData));
       } catch (ParseException Ex) {
          return "Erro na conversão da data";
       }
    }
       public String formatarData(JDateChooser componente ){
        java.util.Date pega = componente.getDate();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(pega);
  }
   public String datahoje(){
	//DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); 
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); 
	Date date = new Date(); 
	return dateFormat.format(date);
   }
   public String dataBanco(String data){
       String dia = data.substring(8, 10);
       String mes = data.substring(5, 7);
       String ano = data.substring(0, 4);
       String databanco = dia+"/"+mes+"/"+ano;
       return databanco;
   }
 public String retirarFormatacaoCpf(String CPF){
          return(CPF.replaceAll("[.-]", ""));
    }
     public String imprimeCPF(String CPF) {
         if(CPF.length()==11){
    return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
      CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
         }else{
             return null;
         }
  }
     
 public String formatoMoeda(String valor){

        DecimalFormat formatoDois = new DecimalFormat("##,###,###,##0.00", new DecimalFormatSymbols (new Locale ("pt", "BR")));
        formatoDois.setMinimumFractionDigits(2); 
        formatoDois.setParseBigDecimal (true);
        return formatoDois.format(valor);
 }
     
  public void gerarPdfRefeicao(String titulo,String subtitulo,int tipo,String datainicial, String  datafinal,int idaluno,int idbolsa,int idcurso,int idturma,String campo,String ordem, int tipodetalhadoresumido) throws DocumentException, BadElementException, SQLException, FileNotFoundException {
        SimpleDateFormat out = new SimpleDateFormat("HH:mm");
        PdfTemplate t;
        String result = out.format(new Date());
        int b1=0,b2=0,total=0;
        double soma100=0,soma50=0,valorbolsa100=0,valorbolsa50=0,totalpagar=0;
        String nomebolsa="";
        String dataformatada="";
        
        
        RefeitorioDao r = new RefeitorioDao();
        
        Document document = new Document(PageSize.A4,36, 36, 36, 72);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
        HeaderFooter event = new HeaderFooter();
        writer.setPageEvent(event);       
        //document.setMargins(36, 72, 108, 180);
        //document.setMargins(36, 72, 72, 72);
        document.setMarginMirroring(true);
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
        com.itextpdf.text.Font conteudoFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
        com.itextpdf.text.Font conteudoFontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
        
  
        //            try {
//                PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
//            } catch (DocumentException ex) {
//                Logger.getLogger(Relatoriogenerico.class.getName()).log(Level.SEVERE, null, ex);
//            }
document.open();
Image img = null;
try {
    img = Image.getInstance("brasaoruplica.gif");
    img.setAlignment(Element.ALIGN_CENTER);
    img.scaleAbsolute(70,70);
} catch (IOException ex) {
    Logger.getLogger(Relatoriogenerico.class.getName()).log(Level.SEVERE, null, ex);
}
Paragraph t1 = new Paragraph("Ministério da Educação",conteudoFontBold);
Paragraph t2 = new Paragraph("Secretaria de Educação Profissional e Tecnológica",conteudoFontBold);
Paragraph t3 = new Paragraph("Instituto Federal de Educação, Ciência e Tecnologia do Tocantins",conteudoFontBold);
Paragraph t4 = new Paragraph("Campus Araguaína",conteudoFontBold);
Paragraph t5 = new Paragraph("Gerência de Ensino",conteudoFontBold);
Paragraph t6 = new Paragraph("Coordenação de Assistência Estudantil",conteudoFontBold);
Paragraph p1 = new Paragraph(titulo,FontFactory.getFont(FontFactory.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK));
Paragraph sub = new Paragraph(subtitulo,FontFactory.getFont(FontFactory.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK));
Paragraph impresso = new Paragraph("Impresso em: "+datahoje()+" às "+result,FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD,BaseColor.BLACK));
document.add(img);
document.add(new Paragraph(" "));
t1.setAlignment(Element.ALIGN_CENTER);
document.add(new Paragraph(t1));
t2.setAlignment(Element.ALIGN_CENTER);
document.add(new Paragraph(t2));
t3.setAlignment(Element.ALIGN_CENTER);
document.add(new Paragraph(t3));
t4.setAlignment(Element.ALIGN_CENTER);
document.add(new Paragraph(t4));
t5.setAlignment(Element.ALIGN_CENTER);
document.add(new Paragraph(t5));
t6.setAlignment(Element.ALIGN_CENTER);
document.add(new Paragraph(t6));
document.add(new Paragraph(p1));
document.add(new Paragraph(sub));
document.add(new Paragraph(impresso));
document.add(new Paragraph(" "));
//TABELA
PdfPTable table = new PdfPTable(6);
table.setTotalWidth(new float[]{ 170, 70, 50, 50, 50,170});//540
table.setLockedWidth(true);
//CABEÇALHO DA TABELA

PdfPCell cel1 = new PdfPCell(new Paragraph("NOME ESTUDANTE",titleFont));
PdfPCell cel2 = new PdfPCell(new Paragraph("CPF",titleFont));
PdfPCell cel3 = new PdfPCell(new Paragraph("AUXÍLIO",titleFont));
PdfPCell cel4 = new PdfPCell(new Paragraph("DATA",titleFont));
PdfPCell cel5 = new PdfPCell(new Paragraph("HORA",titleFont));
PdfPCell cel6 = new PdfPCell(new Paragraph("CURSO / TURMA",titleFont));
PdfPCell cel7 = new PdfPCell(new Paragraph());

//ADICIONAR CABEÇALHO NA TABELA
table.addCell(cel1);
table.addCell(cel2);
table.addCell(cel3);
table.addCell(cel4);
table.addCell(cel5);
table.addCell(cel6);
for (RefeitorioController u : r.Carregarrefeicao(tipo, datainicial, datafinal, idaluno, idbolsa, idcurso, idturma, campo, ordem)) {
    dataformatada=dataBanco(u.getDatarefeitorio());
    
    
    if(u.getTipobolsa()==0)
    {
        nomebolsa="100%";
        b1++;
        soma100=soma100+u.getValor();
        total++;
        
    }
    else if (u.getTipobolsa()==1)
    {
        nomebolsa="50%";
        b2++;
        soma50=soma50+u.getValor();
        total++;
    }
    
    
    cel1 = new PdfPCell(new Paragraph(u.getNomealuno(),conteudoFont));
    cel2 = new PdfPCell(new Paragraph(imprimeCPF(u.getCpf()),conteudoFont));
    cel3 = new PdfPCell(new Paragraph(nomebolsa,conteudoFont));
    cel4 = new PdfPCell(new Paragraph(dataformatada,conteudoFont));
    cel5 = new PdfPCell(new Paragraph(u.getHorariorefeicao(),conteudoFont));
    cel6 = new PdfPCell(new Paragraph(u.getNomecurso()+" - "+u.getNometurma(),conteudoFont));
    
    
    //ADICIONAR CABEÇALHO NA TABELA
    table.addCell(cel1);
    table.addCell(cel2);
    table.addCell(cel3);
    table.addCell(cel4);
    table.addCell(cel5);
    table.addCell(cel6);
    
    
}
//sumário
//         table.addCell(cel1);
//         table.addCell(cel2);
table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
document.add(table);
document.add(new Paragraph(" "));
//tabelas das somas
PdfPTable table1 = new PdfPTable(4);
table1.setTotalWidth(new float[]{ 135, 135, 135, 135});//540
table1.setLockedWidth(true);
Paragraph pcell = new Paragraph("RESUMO DOS AUXILIOS",conteudoFontBold);
pcell.setAlignment(Element.ALIGN_CENTER);
//cabeçalho
cel1.setVerticalAlignment(Element.ALIGN_MIDDLE);
cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
cel1.setColspan(4);
cel1.addElement(pcell);
//cell.setBackgroundColor(BaseColor.GRAY);
cel2.addElement(new Paragraph("TOTAL 100%:",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK)));
cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
cel3.addElement(new Paragraph(" "+b1,conteudoFont));
cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
cel4.addElement(new Paragraph("TOTAL 50%: ",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK)));
cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
cel5.addElement(new Paragraph(" "+b2,conteudoFont));
cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
cel6.addElement(new Paragraph("TOTAL GERAL DE AUXILIOS: "+total,FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK)));
cel6.setColspan(4);
cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
valorbolsa100=soma100/b1;
valorbolsa50=soma50/b2;
totalpagar=soma100+soma50;
table1.addCell(cel1);
table1.addCell(cel2);
table1.addCell(cel3);
table1.addCell(cel4);
table1.addCell(cel5);
table1.addCell(cel6);
document.add(table1);
////       document.add(new Paragraph(" "));
////       document.add(new Paragraph(" "));



//tabelas das somas
//       PdfPTable table2 = new PdfPTable(4);
//       table2.setTotalWidth(new float[]{ 100, 170, 100, 170});//540
//       table2.setLockedWidth(true);
//       cel1 = new PdfPCell(new Paragraph("CALCULO PARA PAGAMENTO DAS BOLSAS",conteudoFontBold));
//       cel1.setVerticalAlignment(Element.ALIGN_MIDDLE);
//       cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel1.setColspan(4);
//       cel2 = new PdfPCell(new Paragraph("100%:",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK))); 
//       cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
//       if(soma100>0){
//       cel3 = new PdfPCell(new Paragraph(b1+"  X  "+mascaraDinheiro(valorbolsa100,DINHEIRO_REAL)+" = "+mascaraDinheiro(soma100, DINHEIRO_REAL),conteudoFont));
//       }else{
//           
//       }
//       cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel4 = new PdfPCell(new Paragraph("50%:   ",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK))); 
//       cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel5 = new PdfPCell(new Paragraph(b2+"  X  "+mascaraDinheiro(valorbolsa50,DINHEIRO_REAL)+" = "+ mascaraDinheiro(soma50, DINHEIRO_REAL),conteudoFont));
//       cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//       
//       cel6=new PdfPCell(new Paragraph("TOTAL À PAGAR:  "+mascaraDinheiro(totalpagar, DINHEIRO_REAL),FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK)));
//       cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel6.setColspan(4);
//       
//       table2.addCell(cel1);
//       table2.addCell(cel2);
//       table2.addCell(cel3);
//       table2.addCell(cel4);
//       table2.addCell(cel5);
//       table2.addCell(cel6);

//document.add(table2);
document.close();
    try {
        Desktop.getDesktop().open(new File("relatorio.pdf"));
    } catch (IOException ex) {
        Logger.getLogger(Relatoriogenerico.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 
  
  
  
  
  public void gerarPdfRefeicaoResumido(String titulo,String subtitulo,String datainicial, String  datafinal,int idbolsa) throws DocumentException, BadElementException, SQLException, FileNotFoundException {
        SimpleDateFormat out = new SimpleDateFormat("HH:mm");
        PdfTemplate t;
        String result = out.format(new Date());
        int contAlunos = 0,totalbolsas=0;
        double soma100=0,soma50=0,valorbolsa100=0,valorbolsa50=0,totalpagar=0;
        String nomebolsa="";
        String dataformatada="";
        
        
        RefeitorioDao r = new RefeitorioDao();
        Document document = new Document(PageSize.A4,36, 36, 36, 72);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
        HeaderFooter event = new HeaderFooter();
        writer.setPageEvent(event); 
        //document.setMargins(36, 72, 108, 180);
        //document.setMargins(36, 72, 72, 72);
        document.setMarginMirroring(true);
        com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
        com.itextpdf.text.Font conteudoFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
        com.itextpdf.text.Font conteudoFontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, BaseColor.BLACK);
        
  
        document.open();
        Image img = null;
        try {
            img = Image.getInstance("brasaoruplica.gif");
            img.setAlignment(Element.ALIGN_CENTER);
            img.scaleAbsolute(70,70);
        } catch (IOException ex) {
            Logger.getLogger(Relatoriogenerico.class.getName()).log(Level.SEVERE, null, ex);
        }
        Paragraph t1 = new Paragraph(12,"Ministério da Educação",conteudoFontBold);
        Paragraph t2 = new Paragraph(12,"Secretaria de Educação Profissional e Tecnológica",conteudoFontBold);
        Paragraph t3 = new Paragraph(12,"Instituto Federal de Educação, Ciência e Tecnologia do Tocantins",conteudoFontBold);
        Paragraph t4 = new Paragraph(12,"Campus Araguaína",conteudoFontBold);
        Paragraph t5 = new Paragraph(12,"Gerência de Ensino",conteudoFontBold);
        Paragraph t6 = new Paragraph(12,"Coordenação de Assistência Estudantil",conteudoFontBold);
        Paragraph p1 = new Paragraph(12,titulo,FontFactory.getFont(FontFactory.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK));
        Paragraph sub = new Paragraph(12,subtitulo,FontFactory.getFont(FontFactory.TIMES_ROMAN,12,Font.BOLD,BaseColor.BLACK));
       // Paragraph impresso = new Paragraph("Impresso em: "+datahoje()+" às "+result,FontFactory.getFont(FontFactory.TIMES_ROMAN,9,Font.BOLD,BaseColor.BLACK));
        document.add(img);
        //document.add(new Paragraph(" "));
        t1.setAlignment(Element.ALIGN_CENTER);
//        t1.setIndentationLeft(9.5f);//Movimenta horizontal
         t1.setLeading(10f);//Movimenta vertical
//        t1.setFirstLineIndent(20f);
        document.add(new Paragraph(t1));
        t2.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(t2));
        t3.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(t3));
        t4.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(t4));
        t5.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(t5));
        t6.setAlignment(Element.ALIGN_CENTER);
        document.add(new Paragraph(t6));
        document.add(new Paragraph(" "));
        document.add(new Paragraph(p1));
        document.add(new Paragraph(sub));
       // document.add(new Paragraph(impresso));
        document.add(new Paragraph(" "));
        //TABELA
        PdfPTable table = new PdfPTable(3);
        table.setTotalWidth(new float[]{300, 70, 90});//540
        table.setLockedWidth(true);
        //CABEÇALHO DA TABELA
        
        PdfPCell cel1 = new PdfPCell(new Paragraph("Nome",titleFont));
        PdfPCell cel2 = new PdfPCell(new Paragraph("CPF",titleFont));
        //PdfPCell cel3 = new PdfPCell(new Paragraph("BOLSA",titleFont));
        PdfPCell cel3 = new PdfPCell(new Paragraph("Quantidade",titleFont));
        // PdfPCell cel4 = new PdfPCell(new Paragraph("DATA",titleFont));
        PdfPCell cel5 = new PdfPCell(new Paragraph("Hora",titleFont));
        PdfPCell cel6 = new PdfPCell(new Paragraph("Curso / Turma",titleFont));
        PdfPCell cel4 = new PdfPCell(new Paragraph(" "));
      
        cel1.setVerticalAlignment(Element.ALIGN_MIDDLE);
       
        cel2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel2.setHorizontalAlignment(Element.ALIGN_CENTER);        
        cel3.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel3.setVerticalAlignment(Element.ALIGN_CENTER);
        cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
        cel5.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel6.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cel4.setVerticalAlignment(Element.ALIGN_MIDDLE);
        //ADICIONAR CABEÇALHO NA TABELA
        table.addCell(cel1);
        table.addCell(cel2);
        table.addCell(cel3);
        //table.addCell(cel4);
        //table.addCell(cel5);
        //table.addCell(cel6);
        for (RefeitorioController u : r.CarregarrefeicaoResumido(datainicial, datafinal, idbolsa)) {
            //dataformatada=dataBanco(u.getDatarefeitorio());    
            cel1 = new PdfPCell(new Paragraph(u.getNomealuno(),conteudoFont));
            cel2 = new PdfPCell(new Paragraph(imprimeCPF(u.getCpf()),conteudoFont));
            cel3 = new PdfPCell(new Paragraph("  "+Integer.toString(u.getQtd()),conteudoFont));
            Paragraph pcelll = new Paragraph("  "+Integer.toString(u.getQtd()),conteudoFont);
            pcelll.setAlignment(Element.ALIGN_CENTER);
            
            //cabeçalho
            cel3.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
            //cel1.setColspan(4);
            //cel3.addElement(pcelll);
            contAlunos=contAlunos+1;
            totalbolsas=totalbolsas+u.getQtd();
            
            
            //ADICIONAR CABEÇALHO NA TABELA
            table.addCell(cel1);
            table.addCell(cel2);
            table.addCell(cel3);
            //table.addCell(cel4);
            // table.addCell(cel5);
            //table.addCell(cel6);
            
            
        }
        //sumário
        //         table.addCell(cel1);
//         table.addCell(cel2);
table.getDefaultCell().setBorder(PdfPCell.NO_BORDER);
document.add(table);
cel4 = new PdfPCell(new Paragraph(""));
cel5 = new PdfPCell(new Paragraph(""));
cel6 = new PdfPCell(new Paragraph(""));

document.add(new Paragraph(" "));
//tabelas das somas
PdfPTable table1 = new PdfPTable(2);
table1.setTotalWidth(new float[]{ 270, 270});//540
table1.setLockedWidth(true);

Paragraph pcell = new Paragraph("Total de Estudantes Beneficiados ",conteudoFont);
Paragraph pcel2 = new Paragraph("Total Auxílios ",conteudoFont);
Paragraph pcel3 = new Paragraph("  "+contAlunos,conteudoFont);
Paragraph pcel4 = new Paragraph("  "+totalbolsas,conteudoFont);


pcell.setAlignment(Element.ALIGN_CENTER);
pcel2.setAlignment(Element.ALIGN_CENTER);
pcel3.setAlignment(Element.ALIGN_CENTER);
pcel4.setAlignment(Element.ALIGN_CENTER);
//cabeçalho
cel1.setVerticalAlignment(Element.ALIGN_MIDDLE);
cel1.setHorizontalAlignment(Element.ALIGN_CENTER);

cel2.setVerticalAlignment(Element.ALIGN_MIDDLE);
cel2.setHorizontalAlignment(Element.ALIGN_CENTER);


cel3.setVerticalAlignment(Element.ALIGN_MIDDLE);
cel3.setHorizontalAlignment(Element.ALIGN_CENTER);


cel4.setVerticalAlignment(Element.ALIGN_MIDDLE);
cel4.setHorizontalAlignment(Element.ALIGN_CENTER);


//cel1.setColspan(4);
cel1.addElement(pcell);
cel2.addElement(pcel2);
cel3.addElement(pcel3);
cel4.addElement(pcel4);



table1.addCell(cel1);
table1.addCell(cel2);
table1.addCell(cel3);
table1.addCell(cel4);

document.add(table1);
////       document.add(new Paragraph(" "));
////       document.add(new Paragraph(" "));



//tabelas das somas
//       PdfPTable table2 = new PdfPTable(4);
//       table2.setTotalWidth(new float[]{ 100, 170, 100, 170});//540
//       table2.setLockedWidth(true);
//       cel1 = new PdfPCell(new Paragraph("CALCULO PARA PAGAMENTO DAS BOLSAS",conteudoFontBold));
//       cel1.setVerticalAlignment(Element.ALIGN_MIDDLE);
//       cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel1.setColspan(4);
//       cel2 = new PdfPCell(new Paragraph("100%:",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK))); 
//       cel2.setHorizontalAlignment(Element.ALIGN_CENTER);
//       if(soma100>0){
//       cel3 = new PdfPCell(new Paragraph(b1+"  X  "+mascaraDinheiro(valorbolsa100,DINHEIRO_REAL)+" = "+mascaraDinheiro(soma100, DINHEIRO_REAL),conteudoFont));
//       }else{
//           
//       }
//       cel3.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel4 = new PdfPCell(new Paragraph("50%:   ",FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK))); 
//       cel4.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel5 = new PdfPCell(new Paragraph(b2+"  X  "+mascaraDinheiro(valorbolsa50,DINHEIRO_REAL)+" = "+ mascaraDinheiro(soma50, DINHEIRO_REAL),conteudoFont));
//       cel5.setHorizontalAlignment(Element.ALIGN_CENTER);
//
//       
//       cel6=new PdfPCell(new Paragraph("TOTAL À PAGAR:  "+mascaraDinheiro(totalpagar, DINHEIRO_REAL),FontFactory.getFont(FontFactory.TIMES_ROMAN,10,Font.BOLD,BaseColor.BLACK)));
//       cel6.setHorizontalAlignment(Element.ALIGN_CENTER);
//       cel6.setColspan(4);
//       
//       table2.addCell(cel1);
//       table2.addCell(cel2);
//       table2.addCell(cel3);
//       table2.addCell(cel4);
//       table2.addCell(cel5);
//       table2.addCell(cel6);

//document.add(table2);
document.close();
    try {
        Desktop.getDesktop().open(new File("relatorio.pdf"));
    } catch (IOException ex) {
        Logger.getLogger(Relatoriogenerico.class.getName()).log(Level.SEVERE, null, ex);
    }
    } 

}
