package com.jx.commontool.commontool.FileUtilTools;

import com.jx.commontool.db.EnterEntites;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaowei on 17/7/18.
 */
public class ExcelHandler {
    public static ExcelHandler handler = new ExcelHandler();
    /*****
     ***** 行列范围参数中均采用“,”作为不连续值的分割符，采用“-”作为两个连续值的连接符，这样简化了用户的参数配置，同时也保留了配置的灵活性，例如：
     *****（1）12-        表示查询范围为从第十二行(列)到EXCEL中有记录的最后一行(列)；
     *****（2）12-24      表示查询范围为从第十二行(列)到第二十四行(列)；
     *****（3）12-24，30  表示查询范围为从第十二行(列)到第二十四行(列)、第三十行(列)等；
     *********/
    public static final String SEPARATOR = ",";
    public static final String CONNECTOR = "-";
    public static void main(String[] args){
        ArrayList<ArrayList<String>> table_list= handler.readExcel("/mnt/data_2.csv",1,"2-","1-");//.readExcelBysax("/opt/enter-table.csv");
        System.out.println(table_list.size());

    }
    public List<EnterEntites> readExcelBysax(String filename){
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = null;
        try {
            saxParser = factory.newSAXParser();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        if(saxParser != null){
            try {
                OPCPackage pkg = OPCPackage.open(filename);
                XSSFReader r = new XSSFReader(pkg);
                XMLReader xr  = XMLReaderFactory.createXMLReader();

                SharedStringsTable sst = r.getSharedStringsTable();
                xr.setContentHandler(new BigExcepHandler(sst));
                InputStream stream1 = r.getSheet("rId1");
                xr.parse(new InputSource(stream1));
                stream1.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return null;
    }
    /** 读取Excel文件内容 */
    public ArrayList<ArrayList<String>> readExcel(String filePath, int sheetIndex, String rows, String columns) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>> ();
        try {
            XSSFWorkbook wb = new XSSFWorkbook(new BufferedInputStream(new FileInputStream(filePath)));
            XSSFSheet sheet = wb.getSheetAt(sheetIndex);

            dataList = readExcel(sheet, rows, getColumnNumber(sheet, columns));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
    /** 获取连续行、不连续列数据 */
    private ArrayList<ArrayList<String>> getRowsValue(Sheet sheet, int startRow, int endRow, int[] cols) {
        if (endRow < startRow) {
            return null;
        }

        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        for (int i = startRow; i <= endRow; i++) {
            data.add(getRowValue(sheet, i, cols));
        }
        return data;
    }

    /** 获取行不连续列数据 */
    private ArrayList<String> getRowValue(Sheet sheet, int rowIndex, int[] cols) {
        Row row = sheet.getRow(rowIndex);
        ArrayList<String> rowData = new ArrayList<String>();
        for (int colIndex : cols) {
            rowData.add(getCellValue(row, colIndex));
        }
        return rowData;
    }
    /**
     * 获取单元格内容
     *
     * @param row
     * @param col
     *            a excel column index from 0 to 65535
     * @return
     */
    private String getCellValue(Row row, int col) {
        if (row == null) {
            return "";
        }
        Cell cell = row.getCell(col);
        return getCellValue(cell);
    }
    /**
     * 获取单元格内容
     *
     * @param cell
     * @return
     */
    private String getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        String value = cell.toString().trim();
        if(cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
            if(DateUtil.isCellDateFormatted(cell)){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
                value = simpleDateFormat.format(cell.getDateCellValue());
            }
        }
        //String value = cell.getStringCellValue();
        try {
            // This step is used to prevent Integer string being output with
            // '.0'.
            Float.parseFloat(value);
            value = value.replaceAll("\\.0$", "");
            value = value.replaceAll("\\.0+$", "");
            return value;
        } catch (NumberFormatException ex) {
            return value;
        }
    }

    /** 获取行连续列数据 */
    private ArrayList<String> getRowValue(Sheet sheet, int rowIndex, int startCol, int endCol) {
        if (endCol < startCol) {
            return null;
        }

        Row row = sheet.getRow(rowIndex);
        ArrayList<String> rowData = new ArrayList<String>();
        for (int i = startCol; i <= endCol; i++) {
            rowData.add(getCellValue(row, i));
        }
        return rowData;
    }
    /** 读取Excel文件内容 */
    protected ArrayList<ArrayList<String>> readExcel(Sheet sheet, String rows, int[] cols) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
        // 处理行信息，并逐行列块读取数据
        String[] rowList = rows.split(SEPARATOR);
        for (String rowStr : rowList) {
            if (rowStr.contains(CONNECTOR)) {
                String[] rowArr = rowStr.trim().split(CONNECTOR);
                int start = Integer.parseInt(rowArr[0]) - 1;
                int end;
                if (rowArr.length == 1) {
                    end = sheet.getLastRowNum();
                } else {
                    end = Integer.parseInt(rowArr[1].trim()) - 1;
                }
                dataList.addAll(getRowsValue(sheet, start, end, cols));
            } else {
                dataList.add(getRowValue(sheet, Integer.parseInt(rowStr) - 1, cols));
            }
        }
        return dataList;
    }
    /**
     * Change excel column string to integer number array
     *
     * @param sheet
     *            excel sheet
     * @param columns
     *            column letter of excel file, like A,B,AA,AB
     * @return
     */
    protected int[] getColumnNumber(Sheet sheet, String columns) {
        // 拆分后的列为动态，采用List暂存
        ArrayList<Integer> result = new ArrayList<Integer>();
        String[] colList = columns.split(SEPARATOR);
        for (String colStr : colList) {
            if (colStr.contains(CONNECTOR)) {
                String[] colArr = colStr.trim().split(CONNECTOR);
                int start = Integer.parseInt(colArr[0]) - 1;
                int end;
                if (colArr.length == 1) {
                    end = sheet.getRow(sheet.getFirstRowNum()).getLastCellNum() - 1;
                } else {
                    end = Integer.parseInt(colArr[1].trim()) - 1;
                }
                for (int i = start; i <= end; i++) {
                    result.add(i);
                }
            } else {
                result.add(Integer.parseInt(colStr) - 1);
            }
        }

        // 将List转换为数组
        int len = result.size();
        int[] cols = new int[len];
        for (int i = 0; i < len; i++) {
            cols[i] = result.get(i).intValue();
        }

        return cols;
    }
}
