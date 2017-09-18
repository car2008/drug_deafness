package com.capitalbiotech.health

import grails.transaction.Transactional
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.CellStyle
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.hssf.util.HSSFColor

@Transactional
class DictionaryService {
	
    def serviceMethod() {
	
    }
	
	/**
	 * export dictionary from mysql
	 */
	def exportDictionary(response) {
		def pubmedList = Dictionary.list();
		Workbook workbook = new HSSFWorkbook();

		CellStyle contentStyle = workbook.createCellStyle();
		org.apache.poi.ss.usermodel.Font contentFont = workbook.createFont();
		contentFont.setColor(HSSFColor.GREY_50_PERCENT.index);
		contentStyle.setFont(contentFont);
		contentStyle.setWrapText(false);
		contentStyle.setAlignment(CellStyle.ALIGN_LEFT);
		contentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		CellStyle titleStyle = workbook.createCellStyle();
		org.apache.poi.ss.usermodel.Font titleFont = workbook.createFont();
		titleFont.setColor(HSSFColor.BLACK.index);
		titleStyle.setFont(titleFont);
		titleStyle.setWrapText(false);
		titleStyle.setAlignment(CellStyle.ALIGN_LEFT);
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

		Sheet sheet = workbook.createSheet("Dictionary");
		int currentRow = 0;
		int currentCol = 0;
		org.apache.poi.ss.usermodel.Row row = null;
		org.apache.poi.ss.usermodel.Cell cell = null;
		
		//title line
		row = sheet.createRow(currentRow++);
		//recordname	recordmodel	genename	rsid	geno	catagrory	reference	recordlabel recordeffect  description diseaseCatagrory
		cell = row.createCell(currentCol++);
		cell.setCellValue("recordname");
		cell.setCellStyle(titleStyle);

		cell = row.createCell(currentCol++);
		cell.setCellValue("recordmodel");
		cell.setCellStyle(titleStyle);

		cell = row.createCell(currentCol++);
		cell.setCellValue("genename");
		cell.setCellStyle(titleStyle);

		cell = row.createCell(currentCol++);
		cell.setCellValue("rsid");
		cell.setCellStyle(titleStyle);

		cell = row.createCell(currentCol++);
		cell.setCellValue("geno");
		cell.setCellStyle(titleStyle);

		cell = row.createCell(currentCol++);
		cell.setCellValue("catagrory");
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(currentCol++);
		cell.setCellValue("reference");
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(currentCol++);
		cell.setCellValue("recordlabel");
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(currentCol++);
		cell.setCellValue("recordeffect");
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(currentCol++);
		cell.setCellValue("description");
		cell.setCellStyle(titleStyle);
		
		cell = row.createCell(currentCol++);
		cell.setCellValue("diseaseCatagrory");
		cell.setCellStyle(titleStyle);

		pubmedList?.each {pubmed ->
			currentCol = 0
			row = sheet.createRow(currentRow++);
			//recordname	recordmodel	genename	rsid	geno	catagrory	reference	recordlabel recordeffect  description diseaseCatagrory
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.recordname);
			cell.setCellStyle(contentStyle);

			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.recordmodel ? pubmed.recordmodel : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.genename ? pubmed.genename : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.rsid ? pubmed.rsid : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.geno ? pubmed.geno : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.catagrory ? pubmed.catagrory : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.reference ? pubmed.reference : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.recordlabel ? pubmed.recordlabel : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.recordeffect ? pubmed.recordeffect : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.description ? pubmed.description : "");
			cell.setCellStyle(contentStyle);
			
			cell = row.createCell(currentCol++);
			cell.setCellValue(pubmed.diseaseCatagrory ? pubmed.diseaseCatagrory : "");
			cell.setCellStyle(contentStyle);
			
		}

		response.setHeader("Content-disposition", "attachment; filename=dictionary.xls")
		workbook.write(response.outputStream);
		response.outputStream.flush()
		response.outputStream.close()
	}
}
