package com.jx.commontool.commontool;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by xiaowei on 17/1/17.
 */
public class CommonTool {
    public static void main(String[] args){
        String regx = "class=";
    }
    public static boolean makeFile(String filepath)throws Exception{
        boolean f = false;
        String[] arry = filepath.split("/");
        if(arry.length > 0){
            String strtemp = "";
            for(int i=0,c=arry.length;i<c;i++){
                String str = arry[i];
                if(null != str && !"".endsWith(str)){
                    strtemp = strtemp+"/"+str;
                    File ff = new File(strtemp);
                    if(i == (c-1)){
                        if(ff.exists()){
                            File bakfile = new File(strtemp);
                            Date date = new Date();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                           ;
                            bakfile.renameTo(new File(strtemp+"."+sdf.format(date)+".bak"));
                        }
                        f = ff.createNewFile();
                    }else {
                        f = ff.exists()?true:ff.mkdir();
                        if(f){
                            continue;
                        }else {
                            break;
                        }
                    }
                }
            }
        }
       // File file = new File(filepath);
        return f;
    }
}
