package com.jx.commontool.httpparser;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiaowei on 17/1/3.
 */
public class HttpCommonParser {

    public String getJosnObjectByString(String[] parms,String valuestr){

        return null;
    }
    public static void main(String[] args){


        FileReader reader = null;
        try {
            reader = new FileReader("/opt/testbook.xml");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        char[] c = new char[1024];
        try {
            reader.read(c);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String resouse = new String(c);

        List<Element> elements = new HttpCommonParser().parseHtmlFormstr(resouse);
        if(null != elements && elements.size() > 0){
            System.out.println(elements.size());
        }

//        Document document = null;
//        try {
//            document = new SAXReader().read(new ByteArrayInputStream(resouse.getBytes()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Element element = null;
//        if(null != document){
//            element = document.getRootElement();
//            elements = element.elements();
//        }



    }

    public List<Element> parseElementBytagname(List<Element> retlist,Element element,String tagname)throws Exception{
        if(null == retlist){
            retlist = new ArrayList<Element>();
        }
        if(element != null){
            Iterator it = element.elementIterator();
            while (it.hasNext()){
                Element ee = (Element) it.next();
                if(ee.getName().equals(tagname)){
                    retlist.add(ee);
                }
                if(ee.hasContent()){
                    retlist = parseElementBytagname(retlist,ee,tagname);
                }
            }
        }
        return retlist;
    }

    public List<Element> parseHtmlFormstr(String resouse){
        resouse = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><root>"+resouse+"</root>";
        List<Element> elements = null;
        Document document = null;
        try {
            document = new SAXReader().read(new ByteArrayInputStream(resouse.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element element = null;
        if(null != document){
            element = document.getRootElement();
            elements = element.elements();
        }

        return elements;
    }
    public String parseHtml(){
        return null;
    }
}
