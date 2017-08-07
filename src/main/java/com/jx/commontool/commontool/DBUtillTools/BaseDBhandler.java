package com.jx.commontool.commontool.DBUtillTools;

import com.bj58.sfft.utility.dao.basedao.DAOBase;
import com.bj58.sfft.utility.dao.basedao.DAOHelper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiaowei on 17/7/21.
 */
public class BaseDBhandler {
    static long begin_time = 1309449600000L;
    private static Object lock = new Object();


    static Map<String,DAOHelper> db_pool = new HashMap<String,DAOHelper>();
    public DAOHelper initDB(String url){
        if(null == url || "".equals(url))
            return null;
        DAOHelper daoHelper = null;
        if(!db_pool.containsKey(url)){
            synchronized (lock){
                if(!db_pool.containsKey(url)){
                    String myFile = url;
                    File file = new File(myFile);
                    System.out.println("----------daoHelpers初始化-----"+myFile);
                    try {
                        daoHelper = DAOBase.createIntrance(myFile);
                        if(null != daoHelper){
                            db_pool.put(url,daoHelper);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }else {
            daoHelper = db_pool.get(url);
        }
        return daoHelper;
    }
    /**
     * 获取主键ID
     *
     * @return
     * @throws Exception
     */
    public  synchronized long getUniqueID() throws Exception {


        long destID = System.currentTimeMillis() - begin_time;
        destID = (destID << 8) | 1;
        Thread.sleep(1);
        return destID;
    }

}
