package com.capitalbiotech.drugdeafness;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.lowagie.text.Cell;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;
import com.lowagie.text.pdf.PdfWriter;

public class PDFUtil {

	public static void main(String[] args) throws Exception {
		HashMap map = new LinkedHashMap<String,String>();
		map.put("医院", "北医三院");
		map.put("姓名","zhangsan");
		map.put("性别","zhangsan");
		map.put("年龄","zhangsan");
		map.put("样本编号","zhangsan");
		map.put("门诊号/住院号","zhangsan");
		map.put("病房/床位","zhangsan");
		map.put("送检科室","zhangsan");
		map.put("送检医生","zhangsan");
		map.put("送检样本","zhangsan");
		map.put("送检时间","zhangsan");
		map.put("备注","zhangsan");
		map.put("famCt","zhangsan");
		map.put("vicCt","zhangsan");
		map.put("nedCt","zhangsan");
		map.put("famCtResult","阴性");
		map.put("vicCtResult","阴性");
		map.put("nedCtResult","阴性");
		map.put("resultcomment","zhangsan");
		map.put("resultpictureUrl","web-app\\images\\detected_pic\\0001-检测异常.bmp");
		map.put("checker","zhangsan");
		map.put("assessor","zhangsan");
		map.put("dateCreated","zhangsan");
		map.put("pdfcomment","* 本报告仅对本次送检样品负责，如有疑问，请与您的医生联系。");
		map.put("outDir","C:\\Users\\Administrator\\Desktop\\drugdeafness\\generatepdf//test.pdf");
		//createPDF(map);
	}

	/**
	 * 创建PDF文档
	 * 
	 * @return
	 * @throws Exception
	 * @throws docException
	 */
	public static String createPDF(HashMap<String,String> map) throws Exception {
		// 输出路径
		String outPath = map.get("outDir");//"C:\\Users\\Administrator\\Desktop\\drugdeafness\\generatepdf//test.pdf";// DataUtil.createTempPath(".pdf");

		// 设置纸张
		Rectangle rect = new Rectangle(PageSize.A4);

		// 创建文档实例
		Document doc = new Document(rect);

		// 添加中文字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

		// 设置字体样式
		Font textFont = new Font(bfChinese, 11, Font.NORMAL); // 正常
		Font redTextFont = new Font(bfChinese, 11, Font.NORMAL, Color.RED); // 正常,红色
		Font boldFont = new Font(bfChinese, 11, Font.BOLD); // 加粗
		Font redBoldFont = new Font(bfChinese, 11, Font.BOLD, Color.RED); // 加粗,红色
		Font firsetTitleFont = new Font(bfChinese, 22, Font.BOLD); // 一级标题
		Font secondTitleFont = new Font(bfChinese, 15, Font.BOLD); // 二级标题
		Font thirdTitleFont = new Font(bfChinese, 12, Font.BOLD); // 
		Font fourthTitleFont = new Font(bfChinese, 9, Font.NORMAL); // 
		Font underlineFont = new Font(bfChinese, 11, Font.UNDERLINE); // 下划线斜体
		Font headFont = new Font(bfChinese, 25, Font.BOLD);

		// 手指图片
		//Image hand = Image.getInstance("C:\\Users\\Administrator\\Desktop\\drugdeafness\\generatepdf\\title.jpg");

		// 创建输出流
		PdfWriter.getInstance(doc, new FileOutputStream(new File(outPath)));

		doc.open();
		doc.newPage();

		// 段落
		Paragraph p1 = new Paragraph();
		// 短语
		Phrase ph1 = new Phrase();

		p1 = new Paragraph(map.get("医院"), firsetTitleFont);
		//p1.setLeading(50);
		p1.setAlignment(Element.ALIGN_CENTER);
		doc.add(p1);
		
		p1 = new Paragraph("药物性耳聋核酸检测报告", secondTitleFont);
		//p1.setLeading(50);
		p1.setAlignment(Element.ALIGN_CENTER);
		doc.add(p1);
		
		//直线  
		p1 = new Paragraph("———————————————————————————————————————————");  
		p1.add(new Chunk());  
		doc.add(p1);  
		
		p1 = new Paragraph();
		p1.setSpacingBefore(10);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c15 = new Chunk(leftPad("病人基本信息", 1), thirdTitleFont);
		ph1.add(c15);
		p1.add(ph1);
		doc.add(p1);

		// 创建一个有6列的表格
		PdfPTable table = new PdfPTable(6);
		table.setTotalWidth(new float[] { 80, 80, 80, 80,80,80 }); // 设置列宽
		table.setLockedWidth(true); // 锁定列宽
		table.getDefaultCell().setBorder(Cell.TABLE);

		PdfPCell cell;
		cell = new PdfPCell(new Phrase("姓名", boldFont));
		//cell.setBorderWidthLeft(3);
		cell.setMinimumHeight(20); // 设置单元格高度
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("姓名"), textFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("性别", boldFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("性别"), textFont));
		//cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("年龄", boldFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("年龄"), textFont));
		//cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("样本编号", boldFont));
		//cell.setBorderWidthLeft(3);
		cell.setMinimumHeight(20); // 设置单元格高度
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("样本编号"), textFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("门诊号/住院号", boldFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("门诊号/住院号"), textFont));
		//cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("病房/床位", boldFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("病房/床位"), textFont));
		//cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("送检科室", boldFont));
		//cell.setBorderWidthLeft(3);
		cell.setMinimumHeight(20); // 设置单元格高度
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("送检科室"), textFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("送检医生", boldFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("送检医生"), textFont));
		//cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("送检样本", boldFont));
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("送检样本"), textFont));
		//cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		
		cell = new PdfPCell(new Phrase("送检时间", boldFont));
		//cell.setBorderWidthLeft(3);
		//cell.setBorderWidthTop(3);
		cell.setMinimumHeight(20); // 设置单元格高度
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("送检时间"), textFont));
		//cell.setBorderWidthRight(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase("备注", boldFont));
		//cell.setBorderWidthLeft(3);
		//cell.setBorderWidthTop(3);
		cell.setMinimumHeight(20); // 设置单元格高度
		cell.setUseAscender(true); // 设置可以居中
		cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		table.addCell(cell);
		cell = new PdfPCell(new Phrase(" "+map.get("备注"), textFont));
		//cell.setBorderWidthRight(3);
		//cell.setBorderWidthTop(3);
		cell.setUseAscender(true); // 设置可以居中
		cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		cell.setColspan(3);
		table.addCell(cell);
		doc.add(table);
		
		p1 = new Paragraph();
		p1.setSpacingBefore(10);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		Chunk c16 = new Chunk(leftPad("检测结果", 1), thirdTitleFont);
		ph1.add(c16);
		p1.add(ph1);
		doc.add(p1);
		
		PdfPTable t1 = new PdfPTable(4);
		// t1.getDefaultCell().setHorizontalAlignment(Cell.ALIGN_CENTER);
		t1.setTotalWidth(new float[]{120, 120, 120, 120});
		t1.setLockedWidth(true); // 锁定列宽
		
		PdfPCell c2 ;
		c2 = new PdfPCell(new Phrase("序号", boldFont));
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph("检测项目", boldFont));
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph("检测值", boldFont));
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph("阴阳性判断", boldFont));
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		
		c2 = new PdfPCell(new Phrase("1", fourthTitleFont));
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph("FAM（1494 C > T）", fourthTitleFont));
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph(map.get("famCt"), fourthTitleFont));
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph(map.get("famCtResult"), fourthTitleFont));
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		
		c2 = new PdfPCell(new Phrase("2", fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph("VIC（1555 A > G）", fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph(map.get("vicCt"), fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph(map.get("vicCtResult"), fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(2);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		
		c2 = new PdfPCell(new Phrase("3", fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph("NED（质控）", fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph(map.get("nedCt"), fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph(map.get("nedCtResult"), fourthTitleFont));
		c2.disableBorderSide(1);
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);

		c2 = new PdfPCell(new Phrase("备注", fourthTitleFont));
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		c2 = new PdfPCell(new Paragraph(""+map.get("resultcomment"), textFont));
		c2.disableBorderSide(4);
		c2.disableBorderSide(8);
		c2.setMinimumHeight(20); // 设置单元格高度
		c2.setUseAscender(true); // 设置可以居中
		c2.setColspan(3);
		c2.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
		c2.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中
		t1.addCell(c2);
		doc.add(t1);
		
		Image img = Image.getInstance(map.get("resultpictureUrl"));  
		img.setAlignment(Image.ALIGN_CENTER | Image.TEXTWRAP);  
		img.setBorder(Image.BOX);  
		img.setBorderWidth(2);  
		img.setBorderColor(Color.WHITE);  
		img.scaleToFit(1300, 260);//大小  
		doc.add(img);  
		
		p1 = new Paragraph();
		ph1 = new Phrase();
		//Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c71 = new Chunk(printBlank(115)+leftPad("药物性耳聋核酸扩增检测示意图",1), fourthTitleFont);
		//ph1.add(c65);
		ph1.add(c71);
		p1.add(ph1);
		doc.add(p1);
		
		p1 = new Paragraph();
		//p1.setSpacingBefore(20);
		//p1.setSpacingAfter(10);
		ph1 = new Phrase();
		//Chunk c62 = new Chunk(hand, 0, 0);
		Chunk c63 = new Chunk(leftPad("结果提示", 1), thirdTitleFont);
		//ph1.add(c62);
		ph1.add(c63);
		p1.add(ph1);
		doc.add(p1);

		p1 = new Paragraph();
		ph1 = new Phrase();
		//Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c66 = new Chunk(leftPad("   本次送检样本为1494 C > T突变型。", 1), fourthTitleFont);
		//ph1.add(c65);
		ph1.add(c66);
		p1.add(ph1);
		doc.add(p1);
		
		p1 = new Paragraph();
		ph1 = new Phrase();
		//Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c67 = new Chunk(leftPad("   药物性耳聋敏感个体。", 1), fourthTitleFont);
		//ph1.add(c65);
		ph1.add(c67);
		p1.add(ph1);
		doc.add(p1);
		
		p1 = new Paragraph();
		ph1 = new Phrase();
		//Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c68 = new Chunk(leftPad("   提示服用耳毒性药物会导致耳聋，应终生禁用耳毒性药物。", 1), fourthTitleFont);
		//ph1.add(c65);
		ph1.add(c68);
		p1.add(ph1);
		doc.add(p1);
		
		p1 = new Paragraph();
		ph1 = new Phrase();
		//Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c69 = new Chunk(leftPad("   建议被检者亲属进行基因检测，以确认其是否为遗传性耳聋或遗传性耳聋基因突变携带者。", 1), fourthTitleFont);
		//ph1.add(c65);
		ph1.add(c69);
		p1.add(ph1);
		doc.add(p1);
		
		p1 = new Paragraph();
		p1.setSpacingBefore(20);
		p1.setSpacingAfter(10);
		ph1 = new Phrase();
		//Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c70 = new Chunk(printBlank(150)+leftPad("检验员  "+map.get("checker")+" 审核员  "+map.get("assessor")+"  检验日期 "+map.get("dateCreated")+" ",1), fourthTitleFont);
		//ph1.add(c65);
		ph1.add(c70);
		p1.add(ph1);
		doc.add(p1);
		
		p1 = new Paragraph();
		ph1 = new Phrase();
		//Chunk c65 = new Chunk(hand, 0, 0);
		Chunk c72 = new Chunk(leftPad(map.get("pdfcomment"),1), fourthTitleFont);
		//ph1.add(c65);
		ph1.add(c72);
		p1.add(ph1);
		doc.add(p1);

		doc.close();
		return outPath;
	}

	/**
	 * 创建单元格
	 * 
	 * @param table
	 * @param row
	 * @param cols
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	private static PdfPTable createCell(PdfPTable table, String[] title, int row, int cols)
			throws DocumentException, IOException {
		// 添加中文字体
		BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		Font font = new Font(bfChinese, 11, Font.BOLD);

		for (int i = 0; i < row; i++) {

			for (int j = 0; j < cols; j++) {

				PdfPCell cell = new PdfPCell();

				if (i == 0 && title != null) {// 设置表头
					cell = new PdfPCell(new Phrase(title[j], font)); // 这样表头才能居中
					if (table.getRows().size() == 0) {
						cell.setBorderWidthTop(3);
					}
				}

				if (row == 1 && cols == 1) { // 只有一行一列
					cell.setBorderWidthTop(3);
				}

				if (j == 0) {// 设置左边的边框宽度
					cell.setBorderWidthLeft(3);
				}

				if (j == (cols - 1)) {// 设置右边的边框宽度
					cell.setBorderWidthRight(3);
				}

				if (i == (row - 1)) {// 设置底部的边框宽度
					cell.setBorderWidthBottom(3);
				}

				cell.setMinimumHeight(40); // 设置单元格高度
				cell.setUseAscender(true); // 设置可以居中
				cell.setHorizontalAlignment(Cell.ALIGN_CENTER); // 设置水平居中
				cell.setVerticalAlignment(Cell.ALIGN_MIDDLE); // 设置垂直居中

				table.addCell(cell);
			}
		}

		return table;
	}

	/**
	 * 加水印（字符串）
	 * 
	 * @param inputFile
	 *            需要加水印的PDF路径
	 * @param outputFile
	 *            输出生成PDF的路径
	 * @param waterMarkName
	 *            水印字符
	 */
	public static void stringWaterMark(String inputFile, String waterMarkName) {
		try {
			String[] spe = DataUtil.separatePath(inputFile);
			String outputFile = spe[0] + "_WM." + spe[1];

			PdfReader reader = new PdfReader(inputFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

			// 添加中文字体
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);

			int total = reader.getNumberOfPages() + 1;

			PdfContentByte under;
			int j = waterMarkName.length();
			char c = 0;
			int rise = 0;
			// 给每一页加水印
			for (int i = 1; i < total; i++) {
				rise = 400;
				under = stamper.getUnderContent(i);
				under.beginText();
				under.setFontAndSize(bfChinese, 30);
				under.setTextMatrix(200, 120);
				for (int k = 0; k < j; k++) {
					under.setTextRise(rise);
					c = waterMarkName.charAt(k);
					under.showText(c + "");
				}

				// 添加水印文字
				under.endText();
			}
			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加水印（图片）
	 * 
	 * @param inputFile
	 *            需要加水印的PDF路径
	 * @param outputFile
	 *            输出生成PDF的路径
	 * @param imageFile
	 *            水印图片路径
	 */
	public static void imageWaterMark(String inputFile, String imageFile) {
		try {
			String[] spe = DataUtil.separatePath(inputFile);
			String outputFile = spe[0] + "_WM." + spe[1];

			PdfReader reader = new PdfReader(inputFile);
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));

			int total = reader.getNumberOfPages() + 1;

			Image image = Image.getInstance(imageFile);
			image.setAbsolutePosition(-100, 0);// 坐标
			image.scaleAbsolute(800, 1000);// 自定义大小
			// image.setRotation(-20);//旋转 弧度
			// image.setRotationDegrees(-45);//旋转 角度
			// image.scalePercent(50);//依照比例缩放

			PdfGState gs = new PdfGState();
			gs.setFillOpacity(0.2f);// 设置透明度为0.2

			PdfContentByte under;
			// 给每一页加水印
			for (int i = 1; i < total; i++) {
				under = stamper.getUnderContent(i);
				under.beginText();
				// 添加水印图片
				under.addImage(image);
				under.setGState(gs);
			}
			stamper.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 设置左边距
	 * 
	 * @param str
	 * @param i
	 * @return
	 */
	public static String leftPad(String str, int i) {
		int addSpaceNo = i - str.length();
		String space = "";
		for (int k = 0; k < addSpaceNo; k++) {
			space = " " + space;
		}
		;
		String result = space + str;
		return result;
	}

	/**
	 * 设置模拟数据
	 * 
	 * @param list
	 * @param num
	 */
	public static void add(List<String> list, int num) {
		for (int i = 0; i < num; i++) {
			list.add("test" + i);
		}
	}

	/**
     * 设置间距
     * @param tmp
     * @return
     */
    public static String printBlank(int tmp){
          String space="";
          for(int m=0;m<tmp;m++){
              space=space+" ";
          }
          return space;
    }
}