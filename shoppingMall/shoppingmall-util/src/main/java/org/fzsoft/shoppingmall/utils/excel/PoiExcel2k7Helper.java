package org.fzsoft.shoppingmall.utils.excel;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Excel 读取（2007+新格式）
 * @author	laughing Sir
 * @date	2013-4-10 下午03:39:01
 * @note	PoiExcel2k7Helper
 */
public class PoiExcel2k7Helper extends PoiExcelHelper {
	/** 获取sheet列表 */
	public ArrayList<String> getSheetList(File file) {
		ArrayList<String> sheetList = new ArrayList<String>(0);
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			Iterator<XSSFSheet> iterator = wb.iterator();
			while (iterator.hasNext()) {
				sheetList.add(iterator.next().getSheetName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sheetList;
	}

	/** 读取Excel文件内容 */
	public ArrayList<ArrayList<String>> readExcel(File file, int sheetIndex, String rows, String columns) {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			
			dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	/** 读取Excel文件内容 */
	public ArrayList<ArrayList<String>> readExcel(File file, int sheetIndex, String rows, int[] cols) {
		ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
			XSSFSheet sheet = wb.getSheetAt(sheetIndex);
			
			dataList = readExcel(sheet, rows, cols);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dataList;
	}
}