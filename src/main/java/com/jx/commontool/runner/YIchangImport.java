package com.jx.commontool.runner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jx.commontool.ConfigFileUtils;
import com.jx.commontool.commontool.DBUtillTools.EnterExcepDBHander;
import com.jx.commontool.db.EnterEntites;

import java.io.File;
import java.util.*;

/**
 * Created by xiaowei on 17/7/21.
 */
public class YIchangImport {

    public static void main(String[] argo) {
        System.out.println("begin=========>"+System.currentTimeMillis());
        YIchangImport yi = new YIchangImport();
//        System.out.println(yi.getPhoneNumberbyZhuceid("110101018795293"));
        List<String> s_list = yi.loadTYCurl();
        if (null == s_list || s_list.size() < 1) {
            System.out.println("get nothing");
            return;
        }
        int page_little = 100;
        JSONArray no_phone_arry = new JSONArray();
        JSONArray phone_arry = new JSONArray();
        JSONArray r_arry = yi.getYIChangarry(s_list);
        StringBuffer con_buffer = new StringBuffer();
        Map<String,JSONObject> j_hash = new HashMap<String, JSONObject>();
        if(r_arry != null && r_arry.size() > 0){
            int code = 0;
            for(int i = 0,c = r_arry.size();i<c;i++){
                System.out.println("small begin "+new Date()+" total "+r_arry.size()+" current "+i);
                JSONObject job = r_arry.getJSONObject(i);



                if(code < page_little){//每10次做一次查询
                    con_buffer.append("'"+job.getString("zhuceid")+"',");
                    j_hash.put(job.getString("zhuceid"),job);
                    code++;
                }else{

                    if(con_buffer.length() < 1)
                        continue;
                    try{
                        String ids = con_buffer.substring(0,(con_buffer.length() - 1));
                        List<EnterEntites> rlist = yi.getPhoneNumbyZhuceidArry(ids,j_hash.size());
                        if(null != rlist && rlist.size() > 0){
                            for(EnterEntites enterEntites : rlist){

                                JSONObject joo = j_hash.get(enterEntites.getReg_code());
                                String phone_number = enterEntites.getPhone_number();
                                System.out.println(phone_number);
                                String bigreson = joo.getString("yc_reason");
                                if(null != bigreson ){
                                    String[] res_arr = bigreson.split(" 列入原因：");
                                    if(res_arr.length > 1){
                                        String ret_reson = res_arr[1].trim();
                                        ret_reson = ret_reson.substring(0,ret_reson.indexOf(" "));
                                        joo.put("yc_reason",ret_reson);
                                    }

                                }
                                joo.put("reg_org",enterEntites.getReg_org());
                                joo.put("phone",phone_number);
                                if(phone_number == null || "".equals(phone_number)){
                                    no_phone_arry.add(joo);
                                }else {
                                    phone_arry.add(joo);
                                }

                            }
                        }else {
                            System.out.println("null message ");
                        }
                        j_hash.clear();
                        code = 0;
                        System.out.println("small end "+new Date());
                        con_buffer = new StringBuffer();
                        try {
                            Thread.sleep(100l);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }



                }

            }

        }

        System.out.println("phone size "+phone_arry.size());
        System.out.println("no phone size "+no_phone_arry.size());
        System.out.println("end=========>"+System.currentTimeMillis());
        ConfigFileUtils.wirteString2file(phone_arry.toString(),"/opt/mnt/yichangtable.json");
        ConfigFileUtils.wirteString2file(no_phone_arry.toString(),"/opt/mnt/yichangtable_nophone.json");
    }
    public List<EnterEntites> getPhoneNumbyZhuceidArry(String zhuce_ids,int pagesize){
        List<EnterEntites> rlist = null;
        String condition = "reg_code in ("+zhuce_ids+")";
        try {
            rlist = EnterExcepDBHander.stance.getEntersListbycondition(condition,"phone_number,reg_code,reg_org",1,pagesize,"");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rlist;
    }
    public String getPhoneNumberbyZhuceid(String zhuceid){
        String ret = "";
        String condition = "reg_code='"+zhuceid+"'";
        List<EnterEntites> e_list = null;
        try {
            e_list = EnterExcepDBHander.stance.getEntersListbycondition(condition,1,1,"");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JSONObject ret_j = new JSONObject();
        if(null != e_list && e_list.size() > 0){
            ret_j.put("ret","ok");
            ret_j.put("phonenumber",e_list.get(0).getPhone_number());
            ret = e_list.get(0).getPhone_number();
        }else {
            ret_j.put("ret","fail");
            ret_j.put("msg","tyk does not get phone ,zhuceid is "+zhuceid);
        }
//        ret = ret_j.toString();
        return ret;

    }

    /**
     * 获取异常列表
     * @param s_list
     * @return
     */
    public JSONArray getYIChangarry(List<String> s_list){
        JSONArray r_arry = new JSONArray();
        for (String str : s_list) {
            JSONArray arry = JSONArray.parseArray(str);
            if (arry != null && arry.size() > 0) {
                for (int i = 0, c = arry.size(); i < c; i++) {
                    JSONObject job = new JSONObject();
                    JSONObject jsonObject = arry.getJSONObject(i);
                    String enterinfo = jsonObject.getString("info");
                    if (enterinfo != null && !enterinfo.equals("")) {
                        enterinfo = enterinfo.replaceAll("\\r", "").
                                replaceAll("\\n", "").replaceAll("<tr>", "").replaceAll("</tr>", "").replaceAll("<td width=\\\"4%\\\"/>", "")
                                .replaceAll("</td>", "").replaceAll("<td style=\\\"text-align:left;\\\">", "").replaceAll("<td style=\\\"text-align:left;width:50%;\\\"> ", "").replaceAll("注册号：", "").replaceAll("列入日期：", "");
                        String[] info_arry = enterinfo.split(" ");
                        for (String str_info : info_arry) {
                            if (str_info.contains("-")) {
                                job.put("date", str_info);
                            } else if (!str_info.equals("") && !str_info.equals(" ")) {
                                job.put("zhuceid", str_info);
                            }
                        }
                    }
                    String reson = jsonObject.getString("reason");
                    if(null != reson && !"".equals(reson)){
                        String yc_reacon = ConfigFileUtils.replaceHtmlTitle(reson,"tr").replaceAll("\r","").replaceAll("\\t","").replaceAll("\\n","").replaceAll("\\u00A0","");
                        if(null != yc_reacon){
                            String[] yc_arry = yc_reacon.split("tr");
                            String[] name_arry = yc_arry[1].split("：");
                            if(null != name_arry && name_arry.length > 1){
                                job.put("enter_name",name_arry[1].trim());
                            }
                            job.put("yc_reason", yc_reacon);
                        }

                    }
                    r_arry.add(job);
                }
            }
        }
        return r_arry;

    }


    public List<String> loadTYCurl(){

        List<String> url_list = new ArrayList<String>();
        File file = new File("/opt/skyyichang");
        File[] file_list = file.listFiles();
        for(File file_html : file_list){
            StringBuffer sb = new StringBuffer();
            if(file_html.getName().endsWith("_list.html")){
                url_list.add(ConfigFileUtils.readFileByUrl("/opt/skyyichang/"+file_html.getName(),"utf-8"));
            }
        }

        return url_list;
    }
}
