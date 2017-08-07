package com.jx.commontool;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jx.commontool.httpparser.HttpCommonParser;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaowei on 17/2/7.
 */
public class GongJinBuz extends BuzAbstrct {
    private String loginurl = "http://www.kungeek.com/hszcrm-portal/j_spring_security_check";
    private String hetongurl = "http://www.kungeek.com/hszcrm-portal/khContract/khContractPage.do";
    private String namekv = "j_username=jscs_xiaoshou@kungeek.com";
    private String pwdkv = "j_password=123456";
    public static void main(String[] args){
        BuzAbstrct buz = new GongJinBuz();

        try {
            buz.getPageListByurl("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public JSONArray getPageListByurl(String url) throws Exception {
        System.out.println("task begin at >>>>>>>>>"+System.currentTimeMillis());
        String jseeionid = loginHanderNocheckcode(loginurl,namekv,pwdkv);//模拟登录
        JSONArray retArry = new JSONArray();

        for(int pageindex=111;pageindex <= 140;pageindex++){
            String htmlstr = initHTMLstr(hetongurl+"?pageNum="+pageindex,jseeionid);

            String tablestr = getHtmlstr(htmlstr,"id","dataTable");
            String[] tablearry = tablestr.split("tbody");
            if(tablearry.length == 3){
                String orgstrlist = tablearry[1];
                String trliststr = orgstrlist.substring(1,orgstrlist.length() - 3);

                //先把</tr>切开
                String[] trarry = trliststr.split("</tr>");
                for(String trcontent : trarry){

                    if(trcontent == null || trcontent.equals("")){
                        continue;
                    }
                    String[] tdarry = trcontent.split("<td class=\"td_tc\">");
                    if(tdarry == null || tdarry.length <= 3){
                        continue;
                    }
                    String contrid = tdarry[1].substring(0,tdarry[1].indexOf("<"));//合同编号
                    String custid = tdarry[2].substring(tdarry[2].indexOf(">"),tdarry[2].indexOf("</a>"));//用户编号
                    String imgreq = "http://www.kungeek.com/hszcrm-portal/khContract/ajaxShowImage.do";

                    String imgret = initHmtlFomesubmit(imgreq,jseeionid,"contractId="+contrid+"&custId="+custid);
                    //{"status":"success","msg":[{"id":"13407","gsId":"2016022633814","custId":"14767040709589141454","contractId":"XS20163563","jjsxDm":"001","fileName":null,"mxId":0,"pageNum":1,"filePath":null,"fpath":"http://182.92.232.117:7500/dev1/0/000/190/0000190797.fid"}]}
                    JSONObject job = JSONObject.parseObject(imgret);//.parse(imgret);
                    JSONArray imgarry = job.getJSONArray("msg");
                    for(int i=0,c=imgarry.size();i<c;i++){
                        try{
                            JSONObject imgobj = imgarry.getJSONObject(i);
                            String imgurl = imgobj.getString("fpath");
                            JSONObject jret = new JSONObject();
                            jret.put("imgurl",imgurl);
                            jret.put("contrid",contrid);
                            jret.put("imgname",contrid+"_"+i);
                            retArry.add(jret);
                            System.out.println("get last image is >>>> imgurl "+imgurl+",and contrid"+contrid+"-"+i);
                        }catch (Exception e){
                            System.out.println("contrid"+contrid+"-"+i);
                            e.printStackTrace();

                        }

                    }



                }



            }
            Thread.sleep(1000);
        }
        writeToFile("/opt/gongjin/contridlist.txt",retArry.toString());
        if(retArry != null && retArry.size() > 0){
            for(int i=0,c=retArry.size();i<c;i++){
                JSONObject job = retArry.getJSONObject(i);
                String imgurl = job.getString("imgurl");
                String imgname = job.getString("imgname");

                ExecutorService threadpool = Executors.newFixedThreadPool(3);

                threadpool.execute(new Downimgurl(imgurl,imgname));
            }
        }
        System.out.println("task end at >>>>>>>>>"+System.currentTimeMillis());
        return retArry;
    }

    public String getFenyeurl(Element element) throws Exception {
        return null;
    }
    class Downimgurl implements Runnable {

        String imgname = "";
        String imgurl = "";


        public Downimgurl(String imgurl,String imgname){
            this.imgname = imgname;
            this.imgurl = imgurl;
        }
        public void run() {
            int c = (new Random().nextInt(2)+2)*1000;
            try {
                downloadPic(imgurl,imgname);
                System.out.println("download pic "+imgname+" at >>>>>>>>>"+System.currentTimeMillis());
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(c);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
