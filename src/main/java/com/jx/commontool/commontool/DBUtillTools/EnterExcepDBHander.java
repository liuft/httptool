package com.jx.commontool.commontool.DBUtillTools;

import com.alibaba.fastjson.JSONArray;
import com.bj58.sfft.utility.dao.basedao.DAOBase;
import com.bj58.sfft.utility.dao.basedao.DAOHelper;
import com.jx.commontool.db.EnterEntites;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.util.Date;
import java.util.List;

import static com.jx.commontool.commontool.DBUtillTools.BaseDBhandler.begin_time;

/**
 * Created by xiaowei on 17/7/18.
 */
public class EnterExcepDBHander extends BaseDBhandler{

    private static String db_file = "/opt/gaea/mysql.properties";

    public static EnterExcepDBHander stance = new EnterExcepDBHander();

    public List<EnterEntites> getEntersListbycondition (String condition,String column,int pageindex,int pagesize,String sortby)throws Exception{
        List<EnterEntites> e_list = initDB(db_file).sql.getListByPage(EnterEntites.class,condition,column,pageindex,pagesize,sortby);
        return e_list;
    }
    public List<EnterEntites> getEntersListbycondition(String condition,int pageindex,int pagesize,String sortby)throws Exception{
        List<EnterEntites> e_list = initDB(db_file).sql.getListByPage(EnterEntites.class,condition,"phone_number",pageindex,pagesize,sortby);
        return e_list;
    }

    public long addUser(EnterEntites employEntity) throws Exception {
        if(employEntity == null){
            return 0L;
        }
        long eid = super.getUniqueID();
        employEntity.setRecord_id(eid);
        initDB(db_file).sql.insert(employEntity);
        return eid;
    }

}
