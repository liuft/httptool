package com.jx.commontool.commontool.FileUtilTools;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by xiaowei on 17/7/18.
 */
public class JXLHander {
    public static void main(String[] argo){
        try {
            Workbook book = Workbook.getWorkbook(new FileInputStream(new File("/opt/copy_base.xls")));
            Sheet sheet = book.getSheet(0);

            for(int i = 1;i< 10;i++){
                Cell[] cells = sheet.getRow(i);
                for(Cell c : cells){
                    String str = c.getContents();
                    byte[] str_byte = str.getBytes("gbk");

                    System.out.println(new String(str_byte,"UTF-8"));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        Cell cell = null;


    }
}
