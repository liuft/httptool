package com.jx.commontool;

import com.alibaba.fastjson.JSONArray;
import com.jx.commontool.httpparser.HttpCommonParser;
import com.jx.commontool.spider.HttpCommonSpider;
import org.dom4j.Element;

import java.util.List;

/**
 * Created by xiaowei on 17/1/18.
 */
public class ZhaofaWang extends BuzAbstrct{
    public static void main(String[] args){
        BuzAbstrct bac = new ZhaofaWang();
        boolean ischeck = true;
        String urlhost = "http://china.findlaw.cn/beijing/page_1/";
        JSONArray retarray = new JSONArray();
        while (ischeck){
            try {
                JSONArray pagearry = bac.getPageListByurl(urlhost);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public JSONArray getPageListByurl(String url) throws Exception {
        JSONArray jarry = new JSONArray();
        String htmlstr = initHTMLstr(url);
//       List<Element> elements = getPageHtmlstr(htmlstr,);
        return jarry;
    }

    public String getFenyeurl(Element element) throws Exception {
        return null;
    }
}
