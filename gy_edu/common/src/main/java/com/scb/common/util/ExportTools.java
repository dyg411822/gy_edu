package com.scb.common.util;
 
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.scb.common.BaseEntity.BaseExcelModel;
 
 
 /**
  * 导出excel工具类
  * Project Name:md_omp_business
  * File Name:ExportTools.java
  * Package Name:com.md.omp.business.util
  * @author 
  * @param <T>
  */
public class ExportTools{
 
	private StringBuffer content = new StringBuffer();
	 
	/**
	 * 导出excel(利用反射机制)
	 * <br>
	 * String[] title = {"订单号","商品名称","商品价格","创建时间"};<br>
	 * String[] field = {"orderNum","productName","productPrice","createTime"};<br>
	 * ExportTools.export("sellJoin", title, field, list, respone);<br>
	 * <br>
	 * 支持字段规则转换
	 * <br>
	 * 后追加 buyNum[&个]
	 * <br>
	 * 除法 investMoney[/100]
	 * <br>
	 * 乘法 investMoney[*100]
	 * <br>
	 * 类型转换 capitalType[1_金豆,2_钱包,3_其他]
	 * <br>
	 * @param filename 导出文件名称
	 * @param title 列标题
	 * @param field 要导出的对象属性字段，（与列标题要 一一对应）
	 * @param results 导出数据list集合
	 * @param pesponse 
	 */
	public static void export(String filename, String[] title,String[] field,List<? extends Object> list, 
    		HttpServletResponse pesponse){
		  new ExportTools().exportExcel(filename, title, field, list, pesponse);
	}
	public static void main(String[] args) {
		String[] title = {"订单号","商品名称","商品价格","创建时间"};
		String[] field = {"orderNum","productName","productPrice","createTime"};
		BaseExcelModel vo=new BaseExcelModel();
		vo.setCreateTime("123456");
		vo.setOrderNum("jgdsj");
		vo.setProductName("你好");
		vo.setProductPrice("343");
		List<BaseExcelModel> list=new ArrayList<BaseExcelModel>();
		list.add(vo);
//		ExportTools.export("你好", title, field, list, pesponse);
	}
	/**
	 * 导出excel(利用反射机制)例：
	 * <br>
	 * String[] title = {"订单号","商品名称","商品价格","创建时间"};<br>
	 * String[] field = {"orderNum","productName","productPrice","createTime"};<br>
	 * ExportTools.export("sellJoin", title, field, list, respone);<br>
	 * <br>
	 * 支持字段规则转换
	 * <br>
	 * 后追加 buyNum[&个]
	 * <br>
	 * 除法 investMoney[/100]
	 * <br>
	 * 乘法 investMoney[*100]
	 * <br>
	 * 类型转换 capitalType[1_金豆,2_钱包,3_其他]
	 * <br>
	 * @param filename 导出文件名称
	 * @param title 列标题
	 * @param field 要导出的对象属性字段，（与列标题要 一一对应）
	 * @param results 导出数据list集合
	 * @param pesponse 
	 */
	public void exportExcel(String filename, String[] title,String[] field,List<? extends Object> list, 
    		HttpServletResponse pesponse){
		setTitle(title);
		try{ 
	    	 for (int i = 0; i < list.size(); i++) {
	    		 for (int j = 0; j < field.length; j++) {
	    		 Object t = list.get(i);
	    		 Field[] fields = t.getClass().getDeclaredFields();
	    		 for ( Field fd : fields ){
	    	            fd.setAccessible( true );
	    	            String fieldStr = field[j];
	    	            String fieldName = "";
	    	            //转换规则
	    	            Integer typeCode = 0;
	    	            Map<Integer, String> ruleMap = new HashMap<Integer, String>();//类型转换规则
	    	            if(fieldStr.indexOf("[") > 1){
	    	            	  fieldName = fieldStr.substring(0, fieldStr.indexOf("["));
	    	            	  String splits = fieldStr.substring(fieldStr.indexOf("[")+1,fieldStr.indexOf("]"));
	    	            	  if(splits.indexOf(",") > -1){//替换
	    	            		  typeCode = 1;
	    	            		  String[] split = splits.split(",");
	    	            		  for(String s : split){
	    	            			  String[] types = s.split("_");
	    	            			  ruleMap.put(Integer.parseInt(types[0]), types[1]);
	    	            		  }
	    	            	  }else if(splits.indexOf("&") > -1){//连接
	    	            		  typeCode = 2;
	    	            		  ruleMap.put(1, splits.substring(splits.indexOf("&")+1,splits.length()));
	    	            	  }else if(splits.indexOf("/") > -1){//除法
	    	            		  typeCode = 3;
	    	            		  ruleMap.put(1, splits.substring(splits.indexOf("/")+1,splits.length()));
	    	            	  }else if(splits.indexOf("*") > -1){//乘法
	    	            		  typeCode = 4;
	    	            		  ruleMap.put(1, splits.substring(splits.indexOf("*")+1,splits.length()));
	    	            	  }
	    	            }else{
	    	            	fieldName = fieldStr;
	    	            };
	    	            
	    	            //取值
	    	              if(fieldName.equals(fd.getName())){
	    	            	  Object value = new Object(); 
	    	            	  if("java.util.Date".equals(fd.getType().getName())){
	    	            		  value = formatDate(fd.get(t));
	    	            	  }else if("java.math.BigDecimal".equals(fd.getType().getName())){
	    	            		  value = fd.get(t);
	    	            	  }else{
	    	            		  value = fd.get(t);
	    	            	  }
	    	            	  //需要做转换
	    	            	  if(typeCode != 0){
	    	            		switch (typeCode) {
	  							case 1:
	  								value = ruleMap.get(value);
	  								break;
	  							case 2:
	  								value = value + ruleMap.get(1);
	  								break;
	  							case 3:
	  								if(null == value || "".equals(value.toString())) value = 0;
	  								value = new BigDecimal(value.toString()).divide(new BigDecimal(ruleMap.get(1)),2,BigDecimal.ROUND_HALF_UP);
	  								break;
	  							case 4:
	  								if(null == value || "".equals(value.toString())) value = 0;
	  								value = new BigDecimal(value.toString()).multiply(new BigDecimal(ruleMap.get(1)));
	  								break;
	  							default:
	  								break;
	  							}
	    	            	  }
	    	            	  setField(value);
	    	            	  break;
	    	              }
	    	            }
	    	    }  
	    		 content.append("\n");
			}
	    	 downloadFileCsv(getFileName(filename), content.toString(), pesponse);
		}catch ( Exception e ){
			System.err.println("导出文件:"+filename+" 出错");
        	e.printStackTrace();
        }  
    }
	
	/**
	 * 生成excel
	 * @param filename
	 * @param content
	 * @param response
	 * @param contentType
	 * @throws IOException
	 */
	public void downloadFileCsv(String filename, String content, HttpServletResponse response) throws IOException {
        response.addHeader("Content-Disposition", "attachment; filename=\"" + new String(filename.getBytes(), "UTF-8") + "\"");
        OutputStreamWriter ow = new OutputStreamWriter(response.getOutputStream(), "UTF-16LE");
        ow.write(0xFEFF);
        ow.write(content);
        if (null != ow) {
            ow.flush();
            ow.close();
        }
    }
	
	/**
	 * 设置标题	
	 */
	private void setTitle(String[] title){
		for (int i = 0; i < title.length; i++) {
			content.append((title[i] == null || "null".equals(title[i].toString()) 
					? "" : title[i].toString()) + "\t");
		}
		content.append("\n");
	}
	
	/**
	 * 设置列表数据
	 * @param value
	 */
	private void setField(Object value){
		content.append((value == null || "null".equals(value.toString()) 
				? "" : value.toString()) + "\t");
	}
	
	/**
	 * 生成当前时间的后缀文件名
	 * @param fileName
	 * @return
	 */
	private String getFileName(String fileName){
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmm");
        return fileName + dateFormat.format(new Date()) +".csv";
    }
	
	/**
	 * 时间类型转化
	 * @param date
	 * @return
	 */
	private String formatDate(Object obj){
    	if(obj == null){
    		return "";
    	}
    	DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    	return format.format((Date)obj);
    }
	
}