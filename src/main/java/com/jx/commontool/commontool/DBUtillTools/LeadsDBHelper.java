package com.jx.commontool.commontool.DBUtillTools;

import com.bj58.sfft.utility.dao.basedao.DAOHelper;
import com.jx.commontool.db.LeadsEntity;

/**
 * Created by xiaowei on 17/7/24.
 */
public class LeadsDBHelper extends BaseDBhandler {
    private static String db_file = "/opt/gaea/mysql.properties";

    public static LeadsDBHelper stance = new LeadsDBHelper();

    public synchronized long addLeads(LeadsEntity entity)throws Exception{
        long rid = 0;

        if(null != entity && !checkphone(entity.getUserphone())){
            long leads_id = super.getUniqueID();

            boolean f = LeadsDBHelper.stance.checkphone(entity.getUserphone(),entity.getUsername());
            if(f){
                System.out.println("got one ,break "+entity.getUserphone()+" "+entity.getUsername());
                return rid;
            }


            entity.setId(leads_id);

            //重复电话的录入,把电话注释掉
            entity.setUserphone("");

            DAOHelper daoHelper = initDB(db_file);
            daoHelper.sql.insert(entity);
            rid = leads_id;
        }
        return rid;
    }
    public boolean checkphone(String phonenumber,String username){
        boolean f = false;
        String condition = "userphone = '"+phonenumber+"' and username = '"+username+"'";
        DAOHelper daoHelper = initDB(db_file);
        int count = 0;
        try {
            count = daoHelper.sql.getCount(LeadsEntity.class,condition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(count > 0){
            f = true;
        }
        return f;
    }

    public boolean checkphone(String phonenumber){
        boolean f = false;
        String condition = "userphone = '"+phonenumber+"'";
        DAOHelper daoHelper = initDB(db_file);
        int count = 0;
        try {
            count = daoHelper.sql.getCount(LeadsEntity.class,condition);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(count > 0){
            f = true;
        }
        return f;
    }


}
