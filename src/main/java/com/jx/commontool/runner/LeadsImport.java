package com.jx.commontool.runner;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jx.commontool.ConfigFileUtils;
import com.jx.commontool.commontool.DBUtillTools.LeadsDBHelper;
import com.jx.commontool.db.LeadsEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xiaowei on 17/7/24.
 */
public class LeadsImport {
    private static ExecutorService es = Executors.newFixedThreadPool(6);
    public static void main(String[] args){
        LeadsImport leadsImport = new LeadsImport();
        leadsImport.runNophoneorrepeat();
    }

    /**
     * 导入没有电话，或者电话重复的数据
     */
    public void runNophoneorrepeat(){
        List<LeadsEntity> list = getNophoneLeadsList();
        int i = 0;
        if(list != null && list.size() > 0){
            for(final LeadsEntity led : list){
                System.out.println("total "+list.size()+",current "+(i++)+" "+new Date());
                es.execute(new Runnable() {
                    public void run(){
                        try {
                            System.out.println(LeadsDBHelper.stance.addLeads(led));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }
    }

    /**
     * 导入标准的线索
     */
    public void runstandleads(){
        List<LeadsEntity> list = getLeadsList();
        int i = 0;
        if(list != null && list.size() > 0){
            for (final LeadsEntity led : list){
                System.out.println("total "+list.size()+",current "+(i++)+" "+new Date());
                es.execute(new Runnable() {

                    public void run() {
                        try {
                            Thread.sleep(300l);
                            System.out.println(LeadsDBHelper.stance.addLeads(led));//.stance.addl(led);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

    }
    public List<LeadsEntity> getNophoneLeadsList(){
        List<LeadsEntity> list = new ArrayList<LeadsEntity>();
        JSONArray array = loadJsonarry("yichangtable.json.2017-07-27.json");
        if(array != null && array.size() > 0){
            for(int i=0,c=array.size();i<c;i++){
                LeadsEntity led = tranceNophoneJsonToentit(array.getJSONObject(i));
                if(null != led){
                    list.add(led);
                }
            }
        }
        return list;
    }
    public List<LeadsEntity> getLeadsList(){
        List<LeadsEntity> list = new ArrayList<LeadsEntity>();
        JSONArray array = loadJsonarry();
        if(array != null && array.size() > 0){
            for(int i=0,c=array.size();i<c;i++){
                LeadsEntity led = tranceJsonToentit(array.getJSONObject(i));

                if(null != led && !(LeadsDBHelper.stance.checkphone(led.getUserphone()))){
                    list.add(led);
                }
            }
        }

        return list;
    }
    public LeadsEntity tranceNophoneJsonToentit(JSONObject jsonObject){
        LeadsEntity leadsEntity = new LeadsEntity();
        if(null != jsonObject){
            leadsEntity.setComment("2017-08-07导入,无手机号导入");
            leadsEntity.setIntentbusiness(jsonObject.getString("yc_reason"));
            leadsEntity.setLibrary_type(0);
            leadsEntity.setIntention_code(0);
            leadsEntity.setLocation_city_id(1);
            leadsEntity.setOld_level(3);
            leadsEntity.setLevel(3);
            leadsEntity.setStatus(0);
            leadsEntity.setCity_id(1);
            leadsEntity.setUsername(jsonObject.getString("enter_name"));

            int area_id = 101;

            String reg_org = jsonObject.getString("reg_org");
            if(reg_org == null || "".equals(reg_org)){

            }else if(reg_org.contains("海淀")){
                area_id = 102;
            }else if(reg_org.contains("丰台")){
                area_id = 105;
            }else if(reg_org.contains("东城")){
                area_id = 103;
            }else if(reg_org.contains("西城")){
                area_id = 104;
            }else if(reg_org.contains("通州")){
                area_id = 107;
            }else if(reg_org.contains("房山")){
                area_id = 111;
            }else if(reg_org.contains("昌平")){
                area_id = 108;
            }else if(reg_org.contains("大兴")){
                area_id = 115;
            }else if(reg_org.contains("怀柔")){
                area_id = 116;
            }else if(reg_org.contains("平谷")){
                area_id = 117;
            }
            leadsEntity.setArea_id(area_id);
            leadsEntity.setUserphone(jsonObject.getString("phone"));
            leadsEntity.setSource(99l);
            leadsEntity.setCreate_time(new Date());
        }
        return leadsEntity;
    }
    public LeadsEntity tranceJsonToentit(JSONObject jsonObject){
        LeadsEntity leadsEntity = new LeadsEntity();
        if(null != jsonObject && null != jsonObject.getString("enter_name") && !"".equals(jsonObject.getString("enter_name"))){
            leadsEntity.setComment("2017-07-28导入");
            leadsEntity.setIntentbusiness(jsonObject.getString("yc_reason"));
            leadsEntity.setLibrary_type(0);
            leadsEntity.setIntention_code(0);
            leadsEntity.setLocation_city_id(3);
            leadsEntity.setOld_level(3);
            leadsEntity.setLevel(3);
            leadsEntity.setStatus(0);
            leadsEntity.setCity_id(1);

            leadsEntity.setUsername(jsonObject.getString("enter_name"));
            leadsEntity.setSale_attribute(2);

            int area_id = 101;

            String reg_org = jsonObject.getString("reg_org");
            if(reg_org == null || "".equals(reg_org)){

            }else if(reg_org.contains("海淀")){
                area_id = 102;
            }else if(reg_org.contains("丰台")){
                area_id = 105;
            }else if(reg_org.contains("东城")){
                area_id = 103;
            }else if(reg_org.contains("西城")){
                area_id = 104;
            }else if(reg_org.contains("通州")){
                area_id = 107;
            }else if(reg_org.contains("房山")){
                area_id = 111;
            }else if(reg_org.contains("昌平")){
                area_id = 108;
            }else if(reg_org.contains("大兴")){
                area_id = 115;
            }else if(reg_org.contains("怀柔")){
                area_id = 116;
            }else if(reg_org.contains("平谷")){
                area_id = 117;
            }
            leadsEntity.setArea_id(area_id);
            leadsEntity.setUserphone(jsonObject.getString("phone"));
            leadsEntity.setSource(99l);
            leadsEntity.setCreate_time(new Date());

        }
        return leadsEntity;
    }
    public JSONArray loadJsonarry(String jsonname){
        JSONArray arry = new JSONArray();
        String str = ConfigFileUtils.readFileByUrl("/opt/mnt/"+jsonname);
        if(null != str && !"".equals(str)){
            arry = JSON.parseArray(str);
        }
        return arry;
    }
    public JSONArray loadJsonarry(){
        JSONArray arry = new JSONArray();
        String str = ConfigFileUtils.readFileByUrl("/opt/mnt/yichangtable.json");
        if(null != str && !"".equals(str)){
            arry = JSON.parseArray(str);
        }
        return arry;
    }
}
