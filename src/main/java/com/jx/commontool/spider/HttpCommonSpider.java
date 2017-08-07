package com.jx.commontool.spider;

import com.alibaba.fastjson.JSONObject;
import com.jx.commontool.httpparser.HttpCommonParser;
import org.dom4j.Element;

import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by xiaowei on 17/1/3.
 */
public class HttpCommonSpider {
    private String urlstr = "";
    public HttpCommonSpider(){

    }
    public HttpCommonSpider(String url){
        this.urlstr = url;
    }
    public String getSpiderByurlNotlogin(String url){
        String ret = "";
        return ret;
    }
    public List<Element> getTagBytagname(String tagname)throws Exception{
        return getTagBytagname(urlstr,tagname);
    }
    public List<Element> getTagBytagname(String url,String tagname)throws Exception{
        List<Element> list = null;
        String htmlstr = getUrlContent(url);
        if(null != htmlstr){
            String regx = "";
        }
        return list;
    }

    /**
     * 根据属性获取elementlist
     * @param attrname 属性名称
     * @param attrvalue 属性值
     * @return
     * @throws Exception
     */
    public List<Element> getTagByAttributes(String attrname,String attrvalue)throws Exception{
        return getTagByAttributes(this.urlstr,attrname,attrvalue);
    }

    /**
     * 根据属性获取elementlist
     * @param url
     * @param attrname
     * @param attrvalue
     * @return
     * @throws Exception
     */
    public List<Element> getTagByAttributes(String url,String attrname,String attrvalue)throws Exception{
        List<Element> list = null;
        String htmlstr = getUrlContent(url);
        StringBuffer sb = new StringBuffer();
        if(null != htmlstr && !"".equals(htmlstr)){
            String reg = attrname+"=\""+attrvalue+"\"";
            String ret = "";
            try {
                ret = getTagByStr(htmlstr,reg);//"http://fatianshi.cn/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            list = new HttpCommonParser().parseHtmlFormstr(ret);
        }
        return list;
    }

    public List<Element> getTagBycss(String cssvalue)throws Exception{
        return getTagBycss(this.urlstr,cssvalue);
    }
    public List<Element> getTagBycss(String url,String cssvalue)throws Exception{
        List<Element> list = null;
        return null;
    }
    public static void main(String[] args){
        try {
//            new HttpCommonSpider().downloadpic("",);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String getFtsDetail(String url)throws Exception{
        HttpCommonParser hcp = new HttpCommonParser();
        String ret = "";
        String htmlstr = getUrlContent(url);
        String phonereg = "id=\"UserPhone\"";
        String namereg = "id=\"UserFullName\"";
        String emailreg = "id=\"UserName\"";


        HttpCommonSpider hcs = new HttpCommonSpider();

        String phonecode = hcs.getTagByStr(htmlstr,phonereg);

        List<Element> phoneel = hcp.parseHtmlFormstr(phonecode);

        String phone = getSigleElmentText(phoneel);
        String namestr = getSigleElmentText(hcp.parseHtmlFormstr(hcs.getTagByStr(htmlstr,namereg)));
        String email = getSigleElmentText(hcp.parseHtmlFormstr(hcs.getTagByStr(htmlstr,emailreg)));
        JSONObject jo = new JSONObject();
        jo.put("name",namestr);
        jo.put("phone",phone);
        jo.put("eamil",email);
        ret = jo.toString();
        return ret;
    }
    public String getSigleElmentText(List<Element> elementList)throws Exception{
        String ret = "";
        if(null != elementList && elementList.size() > 0){
            Element element = elementList.get(0);
            ret = element.getText();
        }
        return ret;
    }

    /**
     * post方式提交
     * @param url
     * @param cookie
     * @param params
     * @return
     * @throws Exception
     */
    public String submitRequestBypost(String url,String cookie,String params)throws Exception{
        String ret = "";
        URL urlreq = new URL(url);
        HttpURLConnection sconn = (HttpURLConnection) urlreq.openConnection();
        sconn.setDoInput(true);
        sconn.setDoOutput(true);
        sconn.setRequestProperty("Cookie",cookie);
        sconn.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        sconn.setRequestProperty("Referer","http://www.kungeek.com/hszcrm-portal/login/login.jsp");
        sconn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        sconn.setRequestProperty("Accept-Encoding","gzip, deflate");
        sconn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
        sconn.setRequestProperty("Connection","keep-alive");
        sconn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

        OutputStream outputStream = sconn.getOutputStream();
        outputStream.write(params.getBytes());

        outputStream.flush();
        outputStream.close();

        BufferedReader br1 = new BufferedReader(new InputStreamReader(sconn.getInputStream()));

        String temp = "";
        StringBuffer sb = new StringBuffer();
        while ((temp = br1.readLine()) != null){
            sb.append(temp);
            sb.append("\n");
        }


        return sb.toString();
    }
    /**
     * 根据用户名和密码模拟登录
     * @param urlstr
     * @param loginnamekv
     * @param pwdkv
     * @return
     * @throws Exception
     */
    public String loginNocheckCodeHander(String urlstr,String loginnamekv,String pwdkv)throws Exception{
        URL url = new URL(urlstr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(false);
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        conn.setRequestProperty("Referer","http://www.kungeek.com/hszcrm-portal/login/login.jsp");
        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding","gzip, deflate");
        conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
        conn.setRequestProperty("Connection","keep-alive");
        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        String content = loginnamekv+"&"+pwdkv;


        OutputStream os =conn.getOutputStream();
        os.write(content.getBytes());

        os.flush();
        os.close();

        String responsecookie = conn.getHeaderField("Set-Cookie");
        String sessionid = "";
        if(responsecookie != null){
            sessionid = responsecookie.substring(0,responsecookie.indexOf(";"));
        }

        Map<String,List<String>> headermap = conn.getHeaderFields();
        for(Map.Entry<String,List<String>> header: headermap.entrySet()){
            System.out.println("header key "+header.getKey()+";header value "+ header.getValue());

        }
        return sessionid;
    }

    /**
     * 获取登录后页面内容
     * @param urlstr
     * @param cookie
     * @return
     * @throws Exception
     */
    public String getUrlContentBycookie(String urlstr,String cookie)throws Exception{
        String ret = "";
        URL urlContent = new URL(urlstr);
        HttpURLConnection connl = (HttpURLConnection) urlContent.openConnection();
        connl.setDoOutput(true);
        connl.setUseCaches(false);
        connl.setRequestMethod("GET");
        connl.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");


        connl.setRequestProperty("Cookie",cookie);
        connl.connect();
        BufferedReader br1 = new BufferedReader(new InputStreamReader(connl.getInputStream()));
        Map<String,List<String>> pagemap = connl.getHeaderFields();
        for(Map.Entry<String,List<String>> header: pagemap.entrySet()){
            System.out.println("pagemap header key "+header.getKey()+"; pagemap header value "+ header.getValue());

        }
        // String line = br1.readLine();

        String temp = "";
        StringBuffer sb = new StringBuffer();
        while ((temp = br1.readLine()) != null){
            sb.append(temp);
            sb.append("\n");
        }

        if(sb != null){
            ret = sb.toString();
            System.out.println("get content >>>"+sb.toString());
        }else{
            System.out.println("does not get content !!!");
        }


        return ret;
    }
    /**
     * 暂时测试登录后获取页面内容,以公瑾为例子
     * @param urlstr
     * @param usernamekv
     * @param pwdkv
     * @return
     * @throws Exception
     */
    public String getUrlContentbylogin(String urlstr,String usernamekv,String pwdkv)throws Exception{
        String ret = "";
        URL url = new URL("http://www.kungeek.com/hszcrm-portal/j_spring_security_check");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestMethod("POST");
        conn.setInstanceFollowRedirects(false);
       // conn.setRequestProperty("Cookie","");
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        conn.setRequestProperty("Referer","http://www.kungeek.com/hszcrm-portal/login/login.jsp");
        conn.setRequestProperty("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        conn.setRequestProperty("Accept-Encoding","gzip, deflate");
        conn.setRequestProperty("Accept-Language","zh-CN,zh;q=0.8");
        conn.setRequestProperty("Connection","keep-alive");
        conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
        String content = "j_username=jscs_xiaoshou@kungeek.com&j_password=123456";

        //conn.connect();

        OutputStream os =conn.getOutputStream();
        os.write(content.getBytes());

        os.flush();
        os.close();

        String responsecookie = conn.getHeaderField("Set-Cookie");
        String sessionid = "";
        if(responsecookie != null){
            sessionid = responsecookie.substring(0,responsecookie.indexOf(";"));
        }

        Map<String,List<String>> headermap = conn.getHeaderFields();
        for(Map.Entry<String,List<String>> header: headermap.entrySet()){
            System.out.println("header key "+header.getKey()+";header value "+ header.getValue());

        }

        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        conn.getInputStream().close();
        br.close();

        URL homeurl = new URL("http://www.kungeek.com/hszcrm-portal/home.do");
        HttpURLConnection connh = (HttpURLConnection) homeurl.openConnection();
        connh.setDoOutput(true);
        connh.setUseCaches(false);
        connh.setRequestMethod("GET");
        connh.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
        connh.setRequestProperty("Cookie",sessionid);
        connh.connect();

        String homecookie = connh.getHeaderField("Set-Cookie");

        Map<String,List<String>> homemap = conn.getHeaderFields();
        for(Map.Entry<String,List<String>> header: homemap.entrySet()){
            System.out.println("home header key "+header.getKey()+"; home header value "+ header.getValue());

        }


        URL urlContent = new URL("http://www.kungeek.com/hszcrm-portal/khContract/khContractPage.do");
        HttpURLConnection connl = (HttpURLConnection) urlContent.openConnection();
        connl.setDoOutput(true);
        connl.setUseCaches(false);
        connl.setRequestMethod("GET");
        //connl.setRequestProperty("Cookie","");
        connl.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

        if(homecookie == null){
            homecookie = sessionid;
        }
        connl.setRequestProperty("Cookie",homecookie);
        connl.connect();
        BufferedReader br1 = new BufferedReader(new InputStreamReader(connl.getInputStream()));
        Map<String,List<String>> pagemap = conn.getHeaderFields();
        for(Map.Entry<String,List<String>> header: pagemap.entrySet()){
            System.out.println("pagemap header key "+header.getKey()+"; pagemap header value "+ header.getValue());

        }
       // String line = br1.readLine();

        String temp = "";
        StringBuffer sb = new StringBuffer();
        while ((temp = br1.readLine()) != null){
            sb.append(temp);
            sb.append("\n");
        }

        if(sb != null){
            System.out.println("get content >>>"+sb.toString());
        }else{
            System.out.println("does not get content !!!");
        }


        return ret;
    }
    public String getUrlContent(String urlstr)throws Exception{
        URL url = new URL(urlstr);
        URLConnection conn = url.openConnection();
        conn.setDoOutput(true);
        conn.setUseCaches(false);
        conn.setRequestProperty("User-Agent","Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");

        conn.connect();//打开链接
        BufferedReader bufferedReader= new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String temp = "";
       StringBuffer sb = new StringBuffer();
        while ((temp = bufferedReader.readLine()) != null){
            sb.append(temp);
            sb.append("\n");
        }
        //System.out.println("sb .tostring "+sb.toString());

        bufferedReader.close();

        String content = "";
        return sb.toString();
    }

    /**
     * 根据class解析html
     * @param resouse
     * @param cssvalue
     * @return
     * @throws Exception
     */
    public String getTabBycssvalue(String resouse,String cssvalue)throws Exception{
        String ret = "";
        if(resouse != null && resouse.contains(cssvalue)){
            String[] array = resouse.split("class=\"");//把所有的class 切割。找第一个闭合的",然后以空格闭合。看是否包含所要查找的class
            if(array.length > 0){
                for(String tempstr : array){
                    //找到第一个闭合"的位置
                    int location = tempstr.indexOf("\"");
                    if(location > 0){
                        String classesstr = tempstr.substring(0,location);
                        String[] classarry = classesstr.split(" ");
                        for(String clas : classarry){
                            if(clas.equals(cssvalue)){//得到了相应的class

                            }
                        }
                    }
                }

            }
        }

        return ret;
    }

    /**
     *
     * @param res
     * @param tagname
     * @return
     * @throws Exception
     */
    public String getTagByTagname(String res,String tagname)throws Exception{
        String ret = "";

        return ret;
    }
    /**
     * 根据属性 name="value" 来切割htmlstr
     * @param res
     * @param regxstr
     * @return
     * @throws Exception
     */
    public String getTagByStr(String res,String regxstr)throws Exception{
        String ret = "";
        if(null != res && res.contains(regxstr)){
            String strtemp = "";
            String[] arry = res.split(regxstr);
            for(int i=0,c=arry.length;i<c;i++){
                String temp = arry[i];
                if(i == 0){
                    strtemp += temp.substring(temp.lastIndexOf("<"));
                    strtemp += regxstr;
                }else if(i > 0 && i < (c-1)){
                    strtemp += temp.substring(0,(temp.indexOf(">")+1));
                    if(strtemp.endsWith("/>")){

                    }else{
                        String tagname = strtemp.substring(1,strtemp.indexOf(" "));
                        strtemp += uncoveredTag(temp.substring((temp.indexOf(">")+1)),tagname);
                    }
                    strtemp += ";;";
                    strtemp += temp.substring(temp.lastIndexOf("<"));
                    strtemp += regxstr;
                }else if(i == (c-1)){
                    strtemp += temp.substring(0,(temp.indexOf(">")+1));
                    if(strtemp.endsWith("/>")){

                    }else{
                        String tagname = strtemp.substring(1,strtemp.indexOf(" "));
                        strtemp += uncoveredTag(temp.substring((temp.indexOf(">")+1)),tagname);
                    }
                }
            }
              ret =  strtemp;

        }
        return ret;
    }
    public String uncoveredTag(String res,String tagname)throws Exception{
        StringBuffer ret = new StringBuffer();
        String prefixstr = "";
        if(null != res){
//            prefixstr = res.substring(0,)
//            res = res.substring(res.indexOf("<"));

            int tagcount = (res.split("/"+tagname).length -1);//计算出一共包含多少个tagname标签

            int[] postionarray = new int[tagcount];//该数组记录了每个结束的tagname的位置

            int firstpostion = 0;
            for(int i =0;i<tagcount;i++){
                int cupostion = res.indexOf("/"+tagname,firstpostion);
                postionarray[i] = cupostion;
                firstpostion = cupostion+tagname.length();
            }


            String tempstr = res.substring(0,res.indexOf("/"+tagname+">"));//截取从头开始到第一个闭合标签的位置
            int endindex = 0;

            if(tempstr.contains(tagname)){//包含的话要查看包含几个，包含几个结束位置就往后延几个
                int beginindex = 1;
                int begincount = tempstr.split(tagname).length - 1;

                while (begincount > 0){
                    beginindex += begincount;
                    tempstr = res.substring((postionarray[beginindex-2]+tagname.length()),(postionarray[beginindex-1]));
                    begincount = tempstr.split(tagname).length  - 1;
                    if(begincount == 0){
                        endindex = postionarray[beginindex-1];
                    }
                }
                ret.append(res.substring(0,endindex-1+tagname.length()+3));


            }else{//不含说明处理结束
                endindex = postionarray[0];
                ret.append(res.substring(0,endindex-1+tagname.length()+3));
            }




        }
        //System.out.println(ret.toString());
        return ret.toString();
    }
    public String downloadpic(String urlstr,String filename)throws Exception{
        String pathstr = "";
        System.out.println("img url is ---->"+urlstr);
        URL url = new URL(urlstr);
        HttpURLConnection piconn = (HttpURLConnection) url.openConnection();
        BufferedInputStream inputStream = new BufferedInputStream(piconn.getInputStream());
        File imgfile = new File("/opt/gongjin/"+filename+".jpg");
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(imgfile));
        BufferedImage bis = ImageIO.read(inputStream);
        ImageIO.write(bis,"jpg",outputStream);

        return pathstr;
    }
}
