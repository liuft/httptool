package com.jx.commontool;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;


/**
 * simple introduction
 *
 * <p>detailed comment</p>
 * @author chuxuebao 2015年9月16日
 * @see
 * @since 1.0
 */

public class ConfigFileUtils {
	
	private static volatile Map<String, Properties> configFiles = new ConcurrentHashMap<String, Properties>();
	
	private static volatile Map<String, List<String>> txtFiles = new ConcurrentHashMap<String, List<String>>();

	private static Object lock = new Object();
	public static void main(String[] args){
		JSONObject job = JSONObject.parseObject(ConfigFileUtils.readFileByUrl("/opt/ord_files/110101019105864.html"));
		job.getString("detail");
	}

	public static void wirteString2file(String str,String filepath){
		if(null == str || "".equals(str) || null == filepath || "".equals(filepath))
			return;

		File file = new File(filepath);
		if(!file.exists()){
			makefiledir(filepath);
		}

		FileOutputStream writer = null;

		try {
			writer = new FileOutputStream(file);
			writer.write(str.getBytes());
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != writer){
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 检查一个文件是不是存在本地目录
	 * @param filename
	 * @return
	 */
	public static boolean checkFileExists(String filename){
		boolean f = false;
		if(null != filename && !"".equals(filename)){
			File file = new File(filename);
			f = file.exists();
		}
		return f;
	}

	/**
	 * 循环简历目录
	 * @param filepath
	 */
	private static void makefiledir(String filepath) {
		String[] filarry = filepath.split("/");
		String temp = "/";
		for(int i = 1;i<(filarry.length-1);i++){
			File f = new File(temp+""+filarry[i]);
			if(!f.exists()){
				f.mkdir();
			}
			temp+=filarry[i]+"/";
		}
	}

	public static String readFileByUrl(String filepath){
		return readFileByUrl(filepath,"UTF-8");
	}
	/**
	 * 读取文件内容
	 * @param filepath
	 * @param charset
	 * @return
	 */
	public static String readFileByUrl(String filepath, String charset){
		String ret = null;
		File file = new File(filepath);
		if(file.exists() && !file.isDirectory()){
			StringBuffer sb = new StringBuffer();
			try {
				FileInputStream inputStream = new FileInputStream(file);

				byte[] bytes = null;
				try {
					bytes = new byte[inputStream.available()];
				} catch (IOException e) {
					e.printStackTrace();
				}
				int z;
				try {
					while ((z = inputStream.read(bytes,0,bytes.length)) != -1){
						String t_str = new String(bytes,charset);
						sb.append(t_str);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			if(null != sb){
				ret = sb.toString();
			}
		}

		return ret;
	}
	public static String replaceHtmlTitle(String ord_str,String excepttag){
		if(null != excepttag && !excepttag.equals("")){
			ord_str = ord_str.replaceAll("<"+excepttag+">",excepttag);
		}
		String ret = "";
		//先去掉注释
		int begin_zs = ord_str.indexOf("<!--");
		int end_zs = ord_str.indexOf("-->");
		if(end_zs >= begin_zs && end_zs > -1){
			ord_str = ord_str.replace(ord_str.substring((begin_zs),(end_zs+3)),"");
		}


		int begin = ord_str.indexOf("<");
		int end = ord_str.indexOf(">");

		if(begin < end){
			ret = ord_str.replace(ord_str.substring(ord_str.indexOf("<"),(ord_str.indexOf(">")+1)),"");
		}else{
			ret = ord_str.replace(">","");
		}
		if(ret.contains("<")){
			ret = replaceHtmlTitle(ret,excepttag);
		}
		return ret;
	}

}
