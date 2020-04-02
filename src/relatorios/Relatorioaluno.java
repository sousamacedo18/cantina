/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package relatorios;

import Controller.AlunoController;
import Dao.AlunoDao;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Home
 */
public class Relatorioaluno {
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
   public void gerarPdfTodosAlunos(String campo, String  ordem) throws DocumentException, BadElementException, SQLException {
       SimpleDateFormat out = new SimpleDateFormat("HH:mm");
 
     
       String result = out.format(new Date());
        int b1=0,b2=0,b3=0,total=0;
        String nomebolsa="";
        String dataformatada="";
        String nomecurso="";
        String nometurma="";

        
                    AlunoDao dao = new AlunoDao();
                    Document document = new Document(PageSize.A4,36, 36, 36, 72);
                    //document.setMargins(36, 72, 108, 180);
                    //document.setMargins(36, 72, 72, 72);
                    document.setMarginMirroring(true);
                    com.itextpdf.text.Font titleFont = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
                    com.itextpdf.text.Font conteudoFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 10, BaseColor.BLACK);
                    com.itextpdf.text.Font conteudoFontBold = FontFactory.getFont(FontFactory.TIMES_BOLD, 10, BaseColor.BLACK);
                   
  
                    try {
                            try {
                                PdfWriter.getInstance(document, new FileOutputStream("relatorio.pdf"));
                            } catch (DocumentException ex) {
                                Logger.getLogger(Relatorioaluno.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        document.open();
                         Image img = null;
                           try {
                               img = Image.getInstance("brasaoruplica.gif");
                               img.setAlignment(Element.ALIGN_CENTER);
                               img.scaleAbsolute(70,70);
                           } catch (IOException ex) {
                               Logger.getLogger(Relatorioaluno.class.getName()).log(Level.SEVERE, null, ex);
                           }
                            Paragraph t1 = new Paragraph("MINISTÉRIO DA EDUCAÇÃO",conteudoFontBold);
                            Paragraph t2 = new Paragraph("SECRETARIA DE EDUCAÇÃO PROFISSIONAL E TECNOLÓGICA",conteudoFontBold);
                            Paragraph t3 = new Paragraph("INSTITUTO FEDERAL DE EDUCAÇÃO, CIÊNCIA E TECNOLOGIA DO TOCANTINS",conteudoFontBold);
                            Paragraph t4 = new Paragraph("CAMPUS ARAGUAÍNA",conteudoFontBold);
                            Paragraph t5 = new Paragraph("GERÊNCIA DE ENSINO",conteudoFontBold);
                            Paragraph t6 = new Paragraph("COORDENAÇÃO DE ASSISTÊNCIA ESTUDANTIL",conteudoFontBold);

                            document.add(new Paragraph(" "));
                            Paragraph p = new Paragraph("Lista de Estudantes com Auxilios",FontFactory.getFont(FontFactory.TIMES_ROMAN,15,Font.BOLD,BaseColor.GREEN));
        
                           Paragraph p1 = new Paragraph("Entre Datas: ",FontFactory.getFont(FontFactory.TIMES_ROMAN,16,Font.BOLD,BaseColor.BLACK));
        
       
        document.add(img);
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
//        p.setAlignment(Element.ALIGN_CENTER);
//        document.add(new Paragraph(p));
        document.add(new Paragraph(" "));
        //p1.setAlignment(Element.ALIGN_CENTER);;
        //document.add(new Paragraph(p1));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Impresso em: "+datahoje()+" às "+result));
        document.add(new Paragraph(" "));
        PdfPTable table = new PdfPTable(4);

        table.setTotalWidth(new float[]{230,50,70,190});//540
        table.setLockedWidth(true); 
        //CABEÇALHO DA TABELA
        
        PdfPCell cel1 = new PdfPCell(new Paragraph("NOME ESTUDANTE",titleFont));
        PdfPCell cel2 = new PdfPCell(new Paragraph("AUXÍLIO",titleFont));
        PdfPCell cel3 = new PdfPCell(new Paragraph("CPF",titleFont));
        PdfPCell cel4 = new PdfPCell(new Paragraph("CURSO / TURMA",titleFont));
        //PdfPCell cel7 = new PdfPCell(new Paragraph("QUANT.",titleFont));
        //PdfPCell cel5 = new PdfPCell(new Paragraph("TURMA",titleFont));
        //PdfPCell cel6 = new PdfPCell(new Paragraph("ID",titleFont));
                //ADICIONAR CABEÇALHO NA TABELA
                table.addCell(cel1);
                table.addCell(cel2);
                table.addCell(cel3);
                table.addCell(cel4);
                //table.addCell(cel5);
               // table.addCell(cel6);
                //table.addCell(cel7);
                //table.addCell(cel7);
        for (AlunoController u : dao.CarregarTodosAlunos(campo, ordem)) {
         //dataformatada=dataBanco(u.get());
         if(u.getBolsa()==0){nomebolsa="100%";b1++;total++;}else if (u.getBolsa()==1){nomebolsa="50%";b2++;total++;}else if (u.getBolsa()==2){nomebolsa="Inativa";b3++;}
         
         
         cel1 = new PdfPCell(new Paragraph(u.getNomealuno().toString(),conteudoFont));
         cel2 = new PdfPCell(new Paragraph(nomebolsa,conteudoFont));
         cel3 = new PdfPCell(new Paragraph(imprimeCPF(u.getCpfaluno()),conteudoFont));
         cel4 = new PdfPCell(new Paragraph(u.getNomecurso()+" - "+u.getNometurma(),conteudoFont));
         //cel5 = new PdfPCell(new Paragraph(u.getNometurma(),conteudoFont));
        // cel6 = new PdfPCell(new Paragraph(u.getIdaluno().toString(),conteudoFont));
         //cel7 = new PdfPCell(new Paragraph(u.getQuantidaderefeitorio().toString(),conteudoFont));
         //cel7 = new PdfPCell(new Paragraph(u.getNomeusuario(),conteudoFont));
                //ADICIONAR CABEÇALHO NA TABELA
                table.addCell(cel1);
                table.addCell(cel2);
                table.addCell(cel3);
                table.addCell(cel4);
                //table.addCell(cel5);
                //table.addCell(cel6);
                //table.addCell(cel7);
                //table.addCell(cel8);            
            
        }
        //sumário
         
         table.addCell(cel1);
         table.addCell(cel2);
        document.add(table);
        
        document.add(new Paragraph(" "));
        
        //tabelas das somas
       PdfPTable table1 = new PdfPTable(4);
       table1.setTotalWidth(new float[]{ 135, 135, 135, 135});//540
       table1.setLockedWidth(true);
   
       
       //cabeçalho
       PdfPCell cel5 = new PdfPCell();
       cel1.setVerticalAlignment(Element.ALIGN_MIDDLE);
       cel1.setHorizontalAlignment(Element.ALIGN_CENTER);
       Paragraph pcell = new Paragraph("RESUMO DOD AUXILIOS",conteudoFontBold);
       pcell.setAlignment(Element.ALIGN_CENTER);
       cel1.addElement(pcell);
       cel1.setColspan(4);
       //cell.setBackgroundColor(BaseColor.GRAY);       
       cel2.addElement(new Paragraph("SOMA DAS AUXILIOS / 100%: "+b1,conteudoFont));
       cel2.setColspan(2);
       cel3.addElement(new Paragraph("SOMA DAS AUXILIOS / 50%: "+b2,conteudoFont));
       cel3.setColspan(2);
       cel4.addElement(new Paragraph("SOMA DAS AUXILIOS / INATIVAS:"+b3,conteudoFont));
       cel4.setColspan(2);
       cel5.addElement(new Paragraph("TOTAL GERAL DE AUXILIOS: "+total,conteudoFontBold));
       cel5.setColspan(4);
       table1.addCell(cel1);
       table1.addCell(cel2);
       table1.addCell(cel3);
       table1.addCell(cel4);
       table1.addCell(cel5);
       document.add(table1);
    
        
       
    } catch (FileNotFoundException ex) {
        Logger.getLogger(Relatorioaluno.class.getName()).log(Level.SEVERE, null, ex);
    }finally{
        document.close();
    }
    try {
        Desktop.getDesktop().open(new File("relatorio.pdf"));
    } catch (IOException ex) {
        Logger.getLogger(Relatorioaluno.class.getName()).log(Level.SEVERE, null, ex);
    }
    }    
}
