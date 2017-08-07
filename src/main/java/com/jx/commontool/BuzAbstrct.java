package com.jx.commontool;

import com.alibaba.fastjson.JSONArray;
import com.jx.commontool.commontool.CommonTool;
import com.jx.commontool.httpparser.HttpCommonParser;
import com.jx.commontool.spider.HttpCommonSpider;
import org.dom4j.Element;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by xiaowei on 17/1/18.
 */

/**
 * 第一步，获取指定的url的html内容
 * 第二步，对html内容进行解析。
 *
 */
public abstract class BuzAbstrct {
    private static HttpCommonSpider spider = new HttpCommonSpider();


    /**
     * 模拟登录返回cookie ，jsessionid
     * @param loginurl
     * @param namekv
     * @param pwdkv
     * @return
     * @throws Exception
     */
    public String loginHanderNocheckcode(String loginurl,String namekv,String pwdkv)throws Exception{
       // HttpCommonSpider hcs = new HttpCommonSpider();
        return spider.loginNocheckCodeHander(loginurl,namekv,pwdkv);
    }

    /**
     * 模拟form表单提交 post
     * @param url
     * @param cookie
     * @param parmes
     * @return
     * @throws Exception
     */
    public String initHmtlFomesubmit(String url,String cookie,String parmes)throws Exception{
        return spider.submitRequestBypost(url,cookie,parmes);
    }
    public String initHTMLstr(String url,String cookie)throws Exception{
        return spider.getUrlContentBycookie(url,cookie);
    }
    //获取一个url的html内容
    public String initHTMLstr(String url)throws Exception{
        if(spider == null){
            spider = new HttpCommonSpider();
        }
        String htmlSTR = spider.getUrlContent(url);
        return htmlSTR;
    }


    public abstract JSONArray getPageListByurl(String url)throws Exception;

    public String getHtmlstr(String htmlstr,String attrname,String attrvalue)throws Exception{
        String ret = "";
        if(null != htmlstr && !"".equals(htmlstr)) {
            String reg = attrname + "=\"" + attrvalue + "\"";
            try {
                ret = spider.getTagByStr(htmlstr, reg);//"http://fatianshi.cn/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    //根据属性值解析html，
    public List<Element> getPageHtmlstr(String htmlstr,String attrname,String attrvalue)throws Exception{

        List<Element> list = null;
        StringBuffer sb = new StringBuffer();
        if(null != htmlstr && !"".equals(htmlstr)){
            String reg = attrname+"=\""+attrvalue+"\"";
            String ret = "";
            try {
                ret = spider.getTagByStr(htmlstr,reg);//"http://fatianshi.cn/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            list = new HttpCommonParser().parseHtmlFormstr(ret);
        }
        return list;
    }
    public abstract String getFenyeurl(Element element)throws Exception;

    public String downloadPic(String url,String filename)throws Exception{
        return spider.downloadpic(url,filename);
    }
    public boolean writeToFile(String filepath,String resous)throws Exception{
        boolean f = false;
        if(CommonTool.makeFile(filepath)){
            OutputStream ous = new BufferedOutputStream(new FileOutputStream(filepath));
            ous.write(resous.getBytes());
            ous.flush();
            ous.close();
        }
        return f;
    }
}
