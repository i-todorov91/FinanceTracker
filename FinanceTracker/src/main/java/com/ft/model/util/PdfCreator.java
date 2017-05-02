package com.ft.model.util;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

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
	
	private PdfCreator() {}
	
	public synchronized static PdfCreator getInstance(){
		if(instance == null){
			instance = new PdfCreator();
		}
		return instance;
	}
	
	private static String sep = File.separator;
	
	public static final String DESTINATION = System.getProperty("os.name").startsWith("Linux") ? 
			sep + "home"+sep+"streetzaki"+sep+"Programming"+sep+"github"+sep+"FinanceTracker"+sep+"FinanceTracker"+sep+"src"+sep+"main"+sep+"webapp"+sep+"static"+sep+"pdf" + sep
				: "D:"+sep+"Programming"+sep+"ITTalents_s7"+sep+"ITtalents_finalProject"+sep+"FinanceTracker"+sep+"src"+sep+"main"+sep+"webapp"+sep+"static"+sep+"pdf" + sep;

	
	public File createCashFlowPdf(User user, String description, List<CashFlow> cashFlow) throws IOException{
		
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
        
        //Create CashFlow table
        addTableToDocument(document, cashFlow);
 
        //Close document
        document.close();
        writer.close();
        pdf.close();
        
        return new File(dest);
        
	}
	
	public File createBudgetPdf(User user, String description, Budget budget) throws IOException{
		
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
     
        //Income table
        addTableToDocument(document, budget.getIncomes());
        
        //Expense table
        addTableToDocument(document, budget.getExpenses());
 
        //Close document
        document.close();
        writer.close();
        pdf.close();
		
        return new File(dest);
	}
	
	public File CreateAccountInfoPdf(User user) throws IOException{

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
        String description = user.getFirstName() + " " + user.getLastName() + " account information.";
        document.add(new Paragraph(description));
        document.add(new Paragraph(" "));
        
        //Create CashFlow table
        document.add(new Paragraph(" "));
        Table table = generateAccountTable(user);
        document.add(table);
 
        //Close document
        document.close();
        writer.close();
        pdf.close();
		
        return new File(dest);
	}
	
	private HashMap<String, HashMap<Double, Table>> generateCashFlowTable(List<CashFlow> cashFlow) throws IOException{ //Type -> Double - table
		
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
	    PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
	    
		Table table = new Table(new float[]{1,1,1,1});
        table.setWidthPercent(100);
        
        table.addHeaderCell(new Cell().add(new Paragraph("Category").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Quantity(lv)").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Date").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Description").setFont(bold).setBackgroundColor(Color.GRAY)));   

        ArrayList<String> data = new ArrayList<>();
		DateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
		TreeSet<CashFlow> orderedCF = new TreeSet<>();
		orderedCF.addAll(cashFlow);
		
		double sum = 0;
		String type = null;
		
		if (cashFlow.isEmpty()) {
			return null;
		}
		if (cashFlow.get(0).getType().equals(CashFlow.TYPES.INCOME)) {
			type = "Income";
		} else {
			type = "Expense";
		}
        for (CashFlow cf : cashFlow) {
			data.add(cf.getCategory().getName());
			data.add(String.valueOf(cf.getQuantity()));
			data.add(formater.format(cf.getDate()));
			data.add(cf.getDescription());
			sum += cf.getQuantity();
		}
        
        for (String string : data) {
            table.addCell(new Cell().add(new Paragraph(string).setFont(font)));
		}
        
        HashMap<String, HashMap<Double, Table>> hashMap = new HashMap<>();
        hashMap.put(type, new HashMap<>());
        hashMap.get(type).put(sum, table);
        
        return hashMap;
	}
	
	private void addTableToDocument(Document document, List<CashFlow> cashFlow) throws IOException{
		
		HashMap<String, HashMap<Double, Table>> info = new HashMap<>();
        double sum = 0;
        String type = null;
        Table table = null;
		
		document.add(new Paragraph(" "));
        info = generateCashFlowTable(cashFlow);
        
        if (info != null) {
        	type = info.keySet().iterator().next();
            sum = info.get(type).keySet().iterator().next();
            table = info.get(type).get(sum);
            document.add(new Paragraph(type + ": " + sum + " lv."));

            document.add(table);
            document.add(new Paragraph(" "));
		}
	}
	
	private Table generateAccountTable(User user) throws IOException{
		
		PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
	    PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
	    
		Table table = new Table(new float[]{1,1,1,1});
        table.setWidthPercent(100);
        
        table.addHeaderCell(new Cell().add(new Paragraph("Name").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Incomes(lv)").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Expenses(lv)").setFont(bold).setBackgroundColor(Color.GRAY)));
        table.addHeaderCell(new Cell().add(new Paragraph("Balance(lv)").setFont(bold).setBackgroundColor(Color.GRAY)));   

        ArrayList<String> data = new ArrayList<>();
		HashMap<String, Budget> budgets = user.getBudgets();
        for (Budget budget : budgets.values()) {
			data.add(budget.getName());
			data.add(String.valueOf(budget.getTotalIncome()));
			data.add(String.valueOf(budget.getTotalExpense()));
			data.add(String.valueOf(budget.getBalance()));
		}
        
        for (String string : data) {
            table.addCell(new Cell().add(new Paragraph(string).setFont(font)));
		}
        
        return table;
	}
	
	public static String generateFileName(User user){
		DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String date = formater.format(new Date());
		String name = user.getFirstName() + "_" + user.getLastName() + "_" + date;
		return name;
	}
}
