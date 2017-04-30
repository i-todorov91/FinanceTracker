package com.ft.model.util;

import java.io.File;
import java.io.IOException;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;

import com.ft.model.budget.Budget;
import com.ft.model.budget.flows.CashFlow;
import com.ft.model.user.User;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

public class PdfCreator {

	private static PdfCreator instance = null;
	
	private PdfCreator() { }
	
	public synchronized static PdfCreator getInstance(){
		if(instance == null){
			instance = new PdfCreator();
		}
		return instance;
	}
	
	private static String sep = File.separator;
	public static final String DESTINATION = System.getProperty("os.name").startsWith("Linux") ? "/home/streetzaki/Programming/github/FinanceTracker/" : "D:"+sep+"Programming"+sep+"ITTalents_s7"+sep+"ITtalents_finalProject"+sep+"pdfs"+sep;
	
	public void createCashFlowPdf(User user, String description, List<CashFlow> cashFlow) throws IOException{
		
		String fileName = generateFileName(user);
		String dest = PdfCreator.DESTINATION + fileName + ".pdf";
		File file = new File(dest);
		file.createNewFile();
		
		//Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
 
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
 
        // Initialize document
        Document document = new Document(pdf);
        document.setMargins(35, 35, 35, 50);
        document.add(new Paragraph(description));
        document.add(new Paragraph(" "));
 
        PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
        
        Table table = generateTable(cashFlow);
        
        document.add(table);
 
        //Close document
        document.close();
        writer.close();
        pdf.close();
		
	}
	
	public void createBudgetPdf(User user, String description, Budget budget) throws IOException{
		
		String fileName = getInstance().generateFileName(user, budget);
		String dest = PdfCreator.DESTINATION + fileName + ".pdf";
		File file = new File(dest);
		
		file.createNewFile();
		
		//Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);
 
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
 
        // Initialize document
        Document document = new Document(pdf);
        document.setMargins(35, 35, 35, 50);
        document.add(new Paragraph(description));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Incomes: "));
        
        Table table = null;
        
        //Income table
        table = generateTable(budget.getIncomes());
        document.add(table);
        
        //Expense table
        document.add(new Paragraph("Expenses: "));
        table = generateTable(budget.getExpenses());
        document.add(table);
 
        //Close document
        document.close();
        writer.close();
        pdf.close();
		
	}
	
	public Table generateTable(List<CashFlow> cashFlow) throws IOException{
		
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
	    PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
	    
		Table table = new Table(new float[]{1,1,1,1});
        table.setWidthPercent(100);
        
        table.addHeaderCell(new Cell().add(new Paragraph("Category").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Quantity").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Date").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Description").setFont(bold).setBackgroundColor(Color.GRAY)));   

        ArrayList<String> data = new ArrayList<>();
		DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		TreeSet<CashFlow> orderedCF = new TreeSet<>();
		orderedCF.addAll(cashFlow);
        for (CashFlow cf : orderedCF) {
			data.add(cf.getCategory().getName());
			data.add(String.valueOf(cf.getQuantity()));
			data.add(formater.format(cf.getDate()));
			data.add(cf.getDescription());
		}
        
        for (String string : data) {
            table.addCell(new Cell().add(new Paragraph(string).setFont(font)));
		}
        
        return table;
	}
	
	public String generateFileName(User user, Budget budget){
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String date = formater.format(new Date());
		String name = user.getFirstName() + " " + user.getLastName() + " " + budget.getName() + "_" + date;
		return name;
	}
}
