package com.jx.commontool.commontool.FileUtilTools;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by xiaowei on 17/7/19.
 */
public class BigExcepHandler extends DefaultHandler {

    //共享字符串表
    private SharedStringsTable sst;
    //上一次的内容
    private String lastcontent;
    private boolean nextIsString;
    private boolean dateFlag;
    private boolean numberFlag;
    private boolean isTElement;

    public BigExcepHandler(SharedStringsTable sst){
        this.sst = sst;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        if(localName.equals("c")){
            //sst索引
            String cellType = attributes.getValue("t");
            if(sst.equals(cellType)){
                nextIsString = true;
            }else {
                nextIsString = false;
            }

            String cellDateType = attributes.getValue("s");
            if("1".equals(cellDateType)){
                dateFlag = true;
            }else {
                dateFlag = false;
                if("2".equals(cellDateType)){
                    numberFlag = true;
                }else{
                    numberFlag = false;
                }
            }

        }

        if("t".equals(localName)){
            isTElement = true;
        }else {
            isTElement = false;
        }
        if(localName.equals("row")){
            System.out.println("------value"+attributes.getValue(0));


        }
        if(localName.equals("c")){
            System.out.println("========="+attributes.getValue(0));
        }
        if(localName.equals("v")){

            System.out.println("===--------"+attributes.getValue(0)+"====>");
        }
        lastcontent = "";
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);
        if(nextIsString){
            int inx = Integer.parseInt(lastcontent);
            lastcontent = new XSSFRichTextString(sst.getEntryAt(inx)).toString();
            System.out.println("========>"+lastcontent+"<=============");
        }

        if(isTElement){

        }else if("v".equals(localName)){
            String value = lastcontent.trim();

            if(dateFlag){
                Date date = HSSFDateUtil.getJavaDate(Double.valueOf(value));
            }
            if(numberFlag){
                BigDecimal bd = new BigDecimal(value);
                value = bd.setScale(0,BigDecimal.ROUND_UP).toString();
            }
            System.out.println("vvvvvvvv========>"+value+"<=============");
        }

    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
        lastcontent += new String(ch,start,length);
        System.out.println("77777=====>"+lastcontent);
    }

    public BigExcepHandler() {
        super();
    }

    @Override
    public InputSource resolveEntity(String publicId, String systemId) throws IOException, SAXException {
        return super.resolveEntity(publicId, systemId);
    }

    @Override
    public void notationDecl(String name, String publicId, String systemId) throws SAXException {
        super.notationDecl(name, publicId, systemId);
    }

    @Override
    public void unparsedEntityDecl(String name, String publicId, String systemId, String notationName) throws SAXException {
        super.unparsedEntityDecl(name, publicId, systemId, notationName);
    }

    @Override
    public void setDocumentLocator(Locator locator) {
        super.setDocumentLocator(locator);
    }

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        super.startPrefixMapping(prefix, uri);
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
        super.endPrefixMapping(prefix);
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        super.ignorableWhitespace(ch, start, length);
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
        super.processingInstruction(target, data);
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
        super.skippedEntity(name);
    }

    @Override
    public void warning(SAXParseException e) throws SAXException {
        super.warning(e);
    }

    @Override
    public void error(SAXParseException e) throws SAXException {
        super.error(e);
    }

    @Override
    public void fatalError(SAXParseException e) throws SAXException {
        super.fatalError(e);
    }
}
