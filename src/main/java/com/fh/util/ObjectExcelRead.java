package com.fh.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 从EXCEL导入到数据库 创建人：WZX Q149156999 创建时间：2014年12月23日
 * 
 * @version
 */
public class ObjectExcelRead {

	/**
	 * @param filepath
	 *            //文件路径
	 * @param filename
	 *            //文件名
	 * @param startrow
	 *            //开始行号
	 * @param startcol
	 *            //开始列号
	 * @param sheetnum
	 *            //sheet
	 * @return list
	 */
	@SuppressWarnings({ "deprecation" })
	public static List<PageData> readExcel(String filepath, String filename, int startrow, int startcol, int sheetnum) {
		List<PageData> varList = new ArrayList<PageData>();
		InputStream inp = null;
		Workbook workbook = null;
		try {
			File target = new File(filepath, filename);
			inp = new FileInputStream(target);
			workbook = WorkbookFactory.create(inp);
			FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
			Sheet sheet = workbook.getSheetAt(sheetnum);
			PageData varpd = new PageData();
			System.out.println("----------" + sheet.getSheetName() + "----------");
			int rowNum = sheet.getLastRowNum() + 1;
			for (int i = startrow; i < rowNum; i++) {// 行循环开始
				Row row = sheet.getRow(i);
				int cellNum = row.getLastCellNum() + 1;
				for (int j = startcol; j < cellNum; j++) {// 列循环开始
					Cell cell = row.getCell(j);
					String cellValue = null;
					if (null != cell) {
						switch (cell.getCellTypeEnum()) {
						case _NONE: // 为空
							cellValue = "";
							break;
						case NUMERIC: // 数字
							cellValue = String.valueOf((int) cell.getNumericCellValue());
							break;

						case STRING: // 字符串
							cellValue = cell.getStringCellValue();
							break;

						case BOOLEAN: // Boolean
							cellValue = String.valueOf(cell.getBooleanCellValue());
							break;

						case FORMULA: // 公式
							// cellValue = cell.getNumericCellValue() + "";
							// 会打印出原本单元格的公式
							// System.out.print(cell.getCellFormula() +
							// "\t");
							// NumberFormat nf = new DecimalFormat("#.#");
							// String value =
							// nf.format(cell.getNumericCellValue());
							CellValue cellValue1 = evaluator.evaluate(cell);
							switch (cellValue1.getCellTypeEnum()) {
							case _NONE:
								// System.out.print("_NONE" + "\t");
								cellValue = "";
								break;
							case BLANK:
								cellValue = "";
								break;
							case BOOLEAN:
								cellValue = String.valueOf(cell.getBooleanCellValue());
								break;
							case ERROR:
								cellValue = String.valueOf(cell.getErrorCellValue());
								break;
							case NUMERIC:
								cellValue = String.valueOf((int) cell.getNumericCellValue());
								break;
							case STRING:
								cellValue = cell.getStringCellValue();
								// System.out.print(cell.getRichStringCellValue()
								// + "\t");
								break;
							default:
								// System.out.print("default" + "\t");
								break;
							}
							break;
						case BLANK: // 空值
							cellValue = "";
							break;

						case ERROR: // 故障
							// cellValue = "非法字符";
							cellValue = String.valueOf(cell.getErrorCellValue());
							break;
						default:
							cellValue = "";
							// System.out.print("default未知类型" + "\t");
							break;
						}

					} else {
						cellValue = "";
						// System.out.print("空值未知类型" + "\t");
					}
					varpd.put("var" + j, cellValue);
				}
				varList.add(varpd);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return varList;
	}

	public static void main(String[] args) {
		readExcel("E:/Program Files (x86)/Tencent/Tencent Files/149156999/FileRecv/", "t_commodity.xls", 0, 0, 0);
	}
}
