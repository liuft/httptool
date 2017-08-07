package com.jx.commontool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jx.commontool.commontool.CommonTool;
import com.jx.commontool.httpparser.HttpCommonParser;
import com.jx.commontool.spider.HttpCommonSpider;
import org.dom4j.Element;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.*;
import org.htmlparser.util.NodeList;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by xiaowei on 17/1/3.
 */
public class HttpBuz {
    String hoststr = "http://fatianshi.cn";
    public static void main(String[] args){

        try {
            new HttpBuz().fatianshi();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void fatianshi()throws Exception{

        HttpCommonSpider hcs = new HttpCommonSpider(hoststr);
        HttpCommonParser hcp = new HttpCommonParser();
        //首页类目
        List<Element> catelist = hcs.getTagByAttributes("class","complex-box");
        if(null != catelist && catelist.size() > 0){
            Element rootcate = catelist.get(0);
            List<Element> alist = new ArrayList<Element>();
            hcp.parseElementBytagname(alist,rootcate,"a");
            JSONArray ja = new JSONArray();
            if(alist != null){
                for(Element e : alist){
                    JSONObject jo = new JSONObject();
                    JSONArray jarry = new JSONArray();
                    jo.put(e.getText().toString(),checkftslist(hoststr+e.attributeValue("href"),jarry));
                    ja.add(jo);
                    break;
                   // Thread.sleep(3000);
                }
            }
            if(ja.size() > 0){
                System.out.println(ja.toString());
                String filepath = "/opt/fatshi/lawer.txt";
                if(CommonTool.makeFile(filepath)){
                    OutputStream ous = new BufferedOutputStream(new FileOutputStream(filepath));
                    ous.write(ja.toString().getBytes());
                    ous.flush();
                    ous.close();
                }

            }
        }

        //首页内容律师列表
       // List<Element> list = hcs.getTagByAttributes("","");
    }
    public JSONArray checkftslist(String cateur,JSONArray ja){
        HttpCommonSpider hcates = new HttpCommonSpider(cateur);
        HttpCommonParser hcp = new HttpCommonParser();
        String fenyeurl = "";
        //解析下一个分页的url
        int currentpage = 1;
        try {
            List<Element> elist = hcates.getTagByAttributes("id","ajaxPage");
            if(null == elist || elist.size() == 0){
                return ja;
            }
            Iterator<Element> pa =  hcates.getTagByAttributes("id","ajaxPage").get(0).elementIterator();

            while (pa.hasNext()){
                Element epage = pa.next();
                if(epage.attribute("class") != null && epage.attribute("class").getValue() != null && epage.attribute("class").getValue().equals("on")){
                    currentpage = Integer.parseInt(epage.getTextTrim());
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

            if(cateur.contains("PageIndex=")){
                cateur = cateur.replaceAll("PageIndex=","");
            }
            if(cateur.contains("?")){
                fenyeurl = cateur+"&PageIndex="+(currentpage+1);
            }else{
                fenyeurl = cateur+"?PageIndex="+(currentpage+1);
            }


        List<Element> elist = null;
        try {
            elist = hcates.getTagByAttributes("class","li-left");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(null == ja){
            ja = new JSONArray();
        }
        if(null != elist && elist.size() > 0){
            for(Element e : elist){
                String detailurl = hoststr+((Element)e.elements().get(0)).attributeValue("href");
                try {
                    ja.add(checkDetail(detailurl));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }

            }
        }
        if(!"".equals(fenyeurl)){
            ja = checkftslist(fenyeurl,ja);
        }

        return ja;
    }
    public String checkDetail(String urlstr)throws Exception{
        return new HttpCommonSpider().getFtsDetail(urlstr);
    }
    public void getParser()throws Exception{
        for(int i =1,c=8;i<c;i++){
            String urlstr = "http://lawyer.110.com/lawyer/region/rid/"+i+"/";
            URL url = new URL(urlstr);
            URLConnection urlConnection =  url.openConnection();
            Parser parser = new Parser(urlConnection);

            NodeFilter filter = new HasAttributeFilter("class","p01");

            NodeList nlist = parser.parse(filter);
            if(nlist != null && nlist.size() > 0){
                Node[] narry = nlist.toNodeArray();//页面律师数量
                for(Node nn : narry){
                    String phone = getTextbychild(nn,"class","tell");
                    String namestr = getnamestr(nn,"class","s01");
                    System.out.println(namestr+"----"+phone);
                }
            }
            System.out.println("======"+nlist.size());
            Thread.sleep(1000);
        }

    }
    public String getTextbychild(Node node,String attrname,String attrvalue)throws Exception{
        String ret = "";
        if(node != null){
            String htmlstr = node.toHtml();
            Parser p = new Parser(htmlstr);
            NodeList nlist = p.parse(new HasAttributeFilter(attrname,attrvalue));
            ret = nlist.toNodeArray()[0].getFirstChild().getText();
        }
        return ret;
    }
    public String getnamestr(Node node,String attrname,String attrvalue)throws Exception{
        String ret = "";
        if(node != null){
            Parser p = new Parser(node.toHtml());
            NodeList nlist = p.parse(new HasAttributeFilter(attrname,attrvalue));
            ret = nlist.toNodeArray()[0].getFirstChild().getNextSibling().getFirstChild().getText();

        }
        return ret;
    }
}
