package com.fm.demo.common;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class SqlUtil {
	/**
	 * 数据库类型：取值范围：oracle、sqlserver、mysql
	 */
	private static String sqlType = null;
	private static Double dbVersion = null;
	@SuppressWarnings("unused")
	private final static String ORACLE = "oracle";
	private final static String SQLSERVER = "sqlserver";
	private final static String MYSQL = "mysql";
	
	// static {
	// 	if(sqlType == null) {
	// 		try {
	// 			String jdbcDriverClassName = CommonUtil.getConfigValue(CommonUtil.getClassesPath() + "dataSource.properties", "jdbc.driverClassName");
	// 			String dbVersionStr = CommonUtil.getConfigValue(CommonUtil.getClassesPath() + "dataSource.properties", "jdbc.dbVersion");
	// 			if(dbVersionStr != null && !"".equals(dbVersionStr)) dbVersion = Double.parseDouble(dbVersionStr);
	// 			if(jdbcDriverClassName.indexOf(SQLSERVER) != -1) {
	// 				sqlType = SQLSERVER;
	// 				if(dbVersion == null) dbVersion = 2012.0;
	// 			} else if(jdbcDriverClassName.indexOf("mysql") != -1) {
	// 				sqlType = MYSQL;
	// 				if(dbVersion == null) dbVersion = 8.0;
	// 			} else {
	// 				sqlType = "";
	// 				if(dbVersion == null) dbVersion = 11.2;
	// 			}
	// 		} catch(Exception e) {
	// 			e.printStackTrace();
	// 		}
	// 	}
	// }
	
	public static String getSqlType() {
		return sqlType;
	}
	
	public static void setSqlType(String sqlType1) {
		sqlType = sqlType1;
	}

	/**
	 * 获取sql语句
	 * @param methodName
	 * @param entity
	 * @param params
	 * @param classes
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	// @SuppressWarnings("unchecked")
	// public static Map<String, Object> getSqlMap(String methodName, Object entity, Object[] params, Class[] classes, Class clazz) throws Exception {
	// 	methodName += CommonUtil.toFirstUpperCase(sqlType);
	// 	if(classes == null) {
	// 		classes = new Class[params.length];
	// 		for(int i = 0; i < params.length; i++) {
	// 			classes[i] = params[i].getClass();
	// 		}
	// 	}
	// 	if(clazz == null) clazz = entity.getClass();
	// 	Method method = clazz.getMethod(methodName, classes);
	// 	Object sql = method.invoke(entity, params);
	// 	Map<String, Object> map = new HashMap<String, Object>();
	// 	map.put(SQLSYNTAX, sql);
	// 	map.put(METHODNAME, methodName);
	// 	map.put(SQLTYPE, sqlType);
	// 	return map;
	// }
	
	/**
	 * 获取sql语句
	 * @param methodName
	 * @param entity
	 * @param params
	 * @param classes
	 * @return
	 * @throws Exception
	 */
	// @SuppressWarnings("unchecked")
	// public static Map<String, Object> getSqlMap(String methodName, Object entity, Object[] params, Class[] classes) throws Exception {
	// 	return getSqlMap(methodName, entity, params, classes, null);
	// }
	
	/**
	 * 获取sql语句
	 * @param methodName
	 * @param entity
	 * @param params
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	// @SuppressWarnings("unchecked")
	// public static Map<String, Object> getSqlMap(String methodName, Object entity, Object[] params, Class clazz) throws Exception {
	// 	return getSqlMap(methodName, entity, params, null, clazz);
	// }
	
	/**
	 * 获取sql语句
	 * @param methodName
	 * @param entity
	 * @param params
	 * @return
	 * @throws Exception
	 */
	// public static Map<String, Object> getSqlMap(String methodName, Object entity, Object[] params) throws Exception {
	// 	return getSqlMap(methodName, entity, params, null, null);
	// }
	
	/**
	 * 转换sql语句
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static String convertSql(String sql) throws Exception {
		if(SQLSERVER.equals(sqlType)) {
			sql = oracle2Sqlserver(sql);
		} else if(MYSQL.equals(sqlType)) {
			sql = oracle2Mysql(sql);
		}
		return sql;
	}

	/**
	 * 格式化sql
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static String formatSql(String sql) throws Exception {
		if(sql == null || "".equals(sql.trim()))
			return "";
		sql = sql.replaceAll("\\\"", "");
		sql = sql.replaceAll("\"", "");
		sql = sql.replaceAll("\t", " ");
		sql = sql.replaceAll("\n", " ");
		sql = sql.replaceAll("\r", " ");
		sql = sql.trim();
		Pattern p = Pattern.compile(" {2,}"); //去除多余空格
		Matcher m = p.matcher(sql);
		sql = m.replaceAll(" ");
		//select内为空没有列，多插入一个空格
		if("select from".equalsIgnoreCase(sql) || sql.toLowerCase().endsWith(" distinct from")) sql = sql.replace(" from", "  from");
		if("select".equalsIgnoreCase(sql) || "select distinct".equalsIgnoreCase(sql) || sql.toLowerCase().endsWith(" from")) sql += " ";
		return sql;
	}
	
	/**
	 * oracle转sqlserver
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static String oracle2Sqlserver(String sql) throws Exception {
		String result = sql.replace("nvl(", "isnull(").replace("||", "+").replace("chr(", "char(").replace("ceil(", "ceiling(")
						.replace("length(", "len(").replace("substr(", "substring(");
		//TODO 转换sysdate + n
		//...
		result = result.replace("sysdate", "getdate()");
		//TODO 转换to_date、to_char、to_number
		//...
		//TODO 转换rownum
		//...
		//TODO 转换wmsys.wm_concat(distinct
		//...
		//TODO 转换listagg
		//...
		return result;
	}
	
	/**
	 * oracle转sqlserver
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public static String oracle2Mysql(String sql) throws Exception {
		String result = sql.replace("nvl(", "ifnull(").replace("||", "+").replace("chr(", "char(");
		//TODO 转换sysdate + n
		//...
		result = result.replace("sysdate", "now()");
		//TODO 转换to_date、to_char、to_number
		//...
		//TODO 转换rownum
		//...
		//TODO 转换wmsys.wm_concat(distinct
		//...
		//TODO 转换listagg
		//...
		return result;
	}
	
	private static Boolean isNumber(String src) throws Exception {
		return src.matches("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,6})?$");
	}
	
	/*private static Boolean isInteger(String src) throws Exception {
		return src.matches("^[0-9]+$");
	}*/
	
	public static String instr(String src, String des) throws Exception {
		src = convertSql(src);
		des = convertSql(des);
		String sql = "instr(" + src + "," + des + ")";
		if(SQLSERVER.equals(sqlType)) {
			sql = "charindex(" + des + "," + src + ")";
		}
		return " " + sql + " ";
	}
	
	public static String nvl(String src, String des) throws Exception {
		src = convertSql(src);
		des = convertSql(des);
		String sql = "nvl(" + src + "," + des + ")";
		if(SQLSERVER.equals(sqlType)) {
			sql = "isnull(" + src + "," + des + ")";
		} else if(MYSQL.equals(sqlType)) {
			sql = "ifnull(" + src + "," + des + ")";
		}
		return " " + sql + " ";
	}
	
	public static String nvl2(String src, String des, String des1) throws Exception {
		src = convertSql(src);
		des = convertSql(des);
		des1 = convertSql(des1);
		String sql = "nvl2(" + src + "," + des + "," + des1 + ")";
		if(SQLSERVER.equals(sqlType) || MYSQL.equals(sqlType)) {
			sql = "case when " + src + " is null or " + src + "='' then " + des + " else " + des1 + " end";
		}
		return " " + sql + " ";
	}
	
	public static String concat(Object ...params) throws Exception {
		String sql = "";
		for(int i = 0; i < params.length; i++) {
			String des = "" + params[i];
			if(params[i] instanceof String[]) {
				String[] strs = (String[]) params[i];
				// strs[0]: 数据库(例:sqlserver,mysql); strs[1]: 字符串(例:t.qty); strs[2]: format(例:FM99999.9999)
				if(("," + strs[0] + ",").indexOf("," + sqlType + ",") != -1)
					des = to_char(strs[1], strs[2]);
				else 
					des = "" + strs[1];
			}
			des = convertSql(des);
			if(SQLSERVER.equals(sqlType) || MYSQL.equals(sqlType)) {
				sql += (i == 0 ? "concat(" : ",") + des + (i == params.length - 1 ? ")" : "");
			} else {
				sql += (i == 0 ? "" : "||") + des;
			}
		}
		return " " + sql + " ";
	}
	
	public static String sysdate() throws Exception {
		String sql = "sysdate";
		if(SQLSERVER.equals(sqlType)) {
			sql = "getdate()";
		} else if(MYSQL.equals(sqlType)) {
			sql = "now()";
		}
		return " " + sql + " ";
	}
	
	public static String decode(Object ...params) throws Exception {
		String sql = "";
		for(int i = 0; i < params.length; i++) {
			String des = params[i] == null ? "null" : "" + params[i]; 
			des = convertSql(des);
			if(i == 0) {
				if(SQLSERVER.equals(sqlType)) {
					sql = "case " + des;
				} else {
					sql = "decode(" + des;
				}
			} else {
				if(SQLSERVER.equals(sqlType)) {
					if(i % 2 == 0) {
						sql += " then " + des;
					} else if(i == params.length - 1) {
						sql += " else " + des;
					} else {
						sql += " when " + des;
					}
				} else {
					sql += "," + des;
				}
			}
		}
		if(SQLSERVER.equals(sqlType)) {
			sql = " end";
		} else {
			sql = ")";
		}
		return " " + sql + " ";
	}
	
	public static String add_days(String src, Object des) throws Exception {
		src = convertSql(src);
		String desStr = ("" + des).trim();
		desStr = convertSql(desStr);
		String sql = src + (desStr.startsWith("-") ? "" : "+") + desStr;
		if(isNumber(desStr)) {
			if(SQLSERVER.equals(sqlType)) {
				sql = "dateadd(dd," + desStr + "," + src + ")";
			} else if(MYSQL.equals(sqlType)) {
				sql = "date_add(" + src + ",interval " + desStr + " day)";
			}
		} else {
			if(SQLSERVER.equals(sqlType)) {
				sql = "datediff(dd," + desStr + "," + src + ")";
			} else if(MYSQL.equals(sqlType)) {
				sql = "datediff(" + src + "," + desStr + ")";
			}
		}
		return " " + sql + " ";
	}
	
	public static String add_hours(String src, int hours) throws Exception {
		src = convertSql(src);
		String sql = src + (hours < 0 ? "-" : "+") + "(" + Math.abs(hours) + "/24)";
		if(SQLSERVER.equals(sqlType)) {
			sql = "dateadd(hh," + hours + "," + src + ")";
		} else if(MYSQL.equals(sqlType)) {
			sql = "date_add(" + src + ",interval " + hours + " hour)";
		}
		return " " + sql + " ";
	}
	
	public static String add_minutes(String src, int minutes) throws Exception {
		src = convertSql(src);
		String sql = src + (minutes < 0 ? "-" : "+") + "(" + Math.abs(minutes) + "/24/60)";
		if(SQLSERVER.equals(sqlType)) {
			sql = "dateadd(mi," + minutes + "," + src + ")";
		} else if(MYSQL.equals(sqlType)) {
			sql = "date_add(" + src + ",interval " + minutes + " minute)";
		}
		return " " + sql + " ";
	}
	
	public static String add_seconds(String src, int seconds) throws Exception {
		src = convertSql(src);
		String sql = src + (seconds < 0 ? "-" : "+") + "(" + Math.abs(seconds) + "/24/60/60)";
		if(SQLSERVER.equals(sqlType)) {
			sql = "dateadd(ss," + seconds + "," + src + ")";
		} else if(MYSQL.equals(sqlType)) {
			sql = "date_add(" + src + ",interval " + seconds + " second)";
		}
		return " " + sql + " ";
	}
	
	public static String add_months(String src, int months) throws Exception {
		src = convertSql(src);
		String sql = "add_months(" + src + "," + months + ")";
		if(SQLSERVER.equals(sqlType)) {
			sql = "dateadd(mm," + months + "," + src + ")";
		} else if(MYSQL.equals(sqlType)) {
			sql = "date_add(" + src + ",interval " + months + " month)";
		}
		return " " + sql + " ";
	}
	
	private static String convertDateFormat(String format) throws Exception {
		//yyyy-mm-dd hh24:mi:ss
		String result = "".equals(format) ? null : format;
		if(result != null) {
			if(SQLSERVER.equals(sqlType)) {
				result = format.toLowerCase().replace("mm", "MM").replace("hh24", "hh").replace("mi", "mm");
			} else if(MYSQL.equals(sqlType)) {
				result = format.toLowerCase().replace("yyyy", "%Y").replace("mm", "%m").replace("dd", "%d").replace("hh24", "%H").replace("hh", "%h").replace("mi", "%i").replace("ss", "%s");
			}
		}
		return result;
	}
	
	// public static String to_date(Object src, String format) throws Exception {
	// 	String dateFormat = format.toLowerCase().replace("mm", "MM").replace("hh24", "hh").replace("mi", "mm");
	// 	String srcStr;
	// 	if(src instanceof String) {
	// 		srcStr = convertSql((String)src);
	// 		try {
	// 			DateUtils.parseDate(srcStr, dateFormat);
	// 			srcStr = "'" + srcStr + "'";
	// 			if(SQLSERVER.equals(sqlType)) format = null;
	// 		} catch(Exception e) {}
	// 	} else {
	// 		srcStr = (new SimpleDateFormat(dateFormat)).format(src instanceof Date ? (Date)src : (Timestamp)src);
	// 		srcStr = "'" + srcStr + "'";
	// 		if(SQLSERVER.equals(sqlType)) format = null;
	// 	}
	// 	format = convertDateFormat(format);
	// 	String sql = "to_date(" + srcStr + ",'" + format + "')";
	// 	if(SQLSERVER.equals(sqlType)) {
	// 		sql = format == null ? srcStr : "format(" + srcStr + ",'" + format + "')";
	// 	} else if(MYSQL.equals(sqlType)) {
	// 		sql = "str_to_date(" + srcStr + ",'" + format + "')";
	// 	}
	// 	return " " + sql + " ";
	// }
	
	public static String to_number(String src, String dataType) throws Exception {
		src = convertSql(src);
		//TODO 对oracle来讲dataType='xxx'是转十六进制
		String sql = "to_number(" + src + (dataType == null ? "" : ",'" + dataType + "'") + ")";
		if(dataType == null) dataType = "numeric";
		if(SQLSERVER.equals(sqlType)) {
			sql = "convert(" + dataType + "," + src + ")";
		} else if(MYSQL.equals(sqlType)) {
			sql = "convert(" + src + "," + dataType + ")";
		}
		return " " + sql + " ";
	}
	
	public static String to_number(String src) throws Exception {
		return to_number(src, null);
	}
	
	public static String to_char(Object src, String format) throws Exception {
		String srcStr = "" + src;
		srcStr = convertSql(srcStr);
		String sql = "to_char(" + srcStr + (format == null ? "" : ",'" + format + "'") + ")";
		if(SQLSERVER.equals(sqlType) || MYSQL.equals(sqlType)) {
			if(format == null) {
				sql = "convert(varchar," + srcStr + ")";
			} else {
				//oracle函数调用格式：to_char(12, 'FM999,990.0099')
				Boolean isFmNumber = format.toLowerCase().startsWith("fm") && isNumber(format.replace(",", "").substring(2));
				if(isFmNumber) format = format.substring(2);
				int lPad = 0;
				if(isNumber(format.replace(",", ""))) {
					if(!isFmNumber && format.startsWith("9")) {
						//计算占位空格数
						String format_l = format.substring(0, format.indexOf("."))/*.replace(",", "")*/;
						lPad = format_l.length();
					}
					format = format.replace("9", "#");
				} else {
					format = convertDateFormat(format);
				}
				sql = "format(" + srcStr + ",'" + format + "')";
				if(lPad > 0) sql = lpad(sql, lPad, " ").trim();
			}
		}
		return " " + sql + " ";
	}
	
	public static String to_char(Object src) throws Exception {
		return to_char(src, null);
	}
	
	public static String rownum() throws Exception {
		String sql = "rownum";
		if(SQLSERVER.equals(sqlType)) {
			sql = "(ROW_NUMBER() over(order by (select 0)))";
		} else if(MYSQL.equals(sqlType)) {
			sql = "(@rownum:=@rownum+1)";
		}
		return " " + sql + " ";
	}
	
	public static String nextval(String seqName) throws Exception {
		String sql = seqName + ".nextval";
		if(SQLSERVER.equals(sqlType)) {
			sql = "(next value for " + seqName + ")";
		} else if(MYSQL.equals(sqlType)) {
			//TODO 自定义数据库函数nextval
			sql = "nextval('" + seqName + "')";
		}
		return " " + sql + " ";
	}
	
	public static String listagg(String src, String orderBy, String over, String sep) throws Exception {
		src = convertSql(src);
		orderBy = convertSql(orderBy);
		if(sep == null) sep = ",";
		String sql = null;
		if(dbVersion < 11.2) {
			sql = "wmsys.wm_concat(" + src + ")";
		} else {
			sql = "(listagg(" + src.replace("distinct ", "").trim() + ",'" + sep + "') within group(order by " + orderBy + ")" + (over == null ? "" : " over(partition by " + over + ")") + ")";
		}
		if(SQLSERVER.equals(sqlType)) {
			if(dbVersion < 2017) {
				sql = "(" + src + " for xml path(''))";
			} else {
				//2017以上版本支持
				sql = "(string_agg(" + src.replace("distinct ", "").trim() + ") within group(order by " + orderBy + "))";
			}
		} else if(MYSQL.equals(sqlType)) {
			sql = "(group_concat(" + src + " order by " + orderBy + "))";
		}
		return " " + sql + " ";
	}
	
	public static String listagg(String src, String orderBy, String over) throws Exception {
		return listagg(src, orderBy, over, null);
	}
	
	public static String listagg(String src, String orderBy) throws Exception {
		return listagg(src, orderBy, null);
	}
	
	public static String lpad(String src, int length, String sep) throws Exception {
		src = convertSql(src);
		String sql = "lpad(" + src + "," + length + (sep == null ? "" : ",'" + sep + "'") + ")";
		if(SQLSERVER.equals(sqlType)) {
			sql = "dbo.lpad(" + src + "," + length + (sep == null ? "" : ",'" + sep + "'") + ")";
		}
		return " " + sql + " ";
	}
	
	public static String lpad(String src, int length) throws Exception {
		return lpad(src, length, null);
	}
	
	public static String rpad(String src, int length, String sep) throws Exception {
		src = convertSql(src);
		String sql = "rpad(" + src + "," + length + (sep == null ? "" : ",'" + sep + "'") + ")";
		if(SQLSERVER.equals(sqlType)) {
			sql = "dbo.rpad(" + src + "," + length + (sep == null ? "" : ",'" + sep + "'") + ")";
		}
		return " " + sql + " ";
	}
	
	public static String rpad(String src, int length) throws Exception {
		return rpad(src, length, null);
	}
	
	public static String trunc(String src, Object format) throws Exception {
		src = convertSql(src);
		String formatStr = "" + format;
		String sql = "trunc(" + src + (format == null || "".equals(formatStr) ? "" : "," + (isNumber(formatStr) ? formatStr : "'" + formatStr + "'")) + ")";
		if(SQLSERVER.equals(sqlType) || MYSQL.equals(sqlType)) {
			if(format == null || "".equals(formatStr)) {
				formatStr = "0";
			}
			if(isNumber(formatStr)) {
				//保留小数点后n位
				int formatNumber = Integer.parseInt(formatStr);
				if(formatNumber >= 0) {
					String newFormat = "0.";
					newFormat += String.format("%" + (formatNumber + 1) + "d", 0);
					sql = to_char(src, newFormat).trim();
					sql = "left(" + sql + ",len(" + sql + ")-" + (formatNumber == 0 ? 2 : 1) + ")";
				} else {
					//整数部分截取
					sql = to_char(src, "0.0").trim();
					sql = "left(" + sql + ",len(" + sql + ")-" + (Math.abs(formatNumber) + 2) + ")";
				}
				sql = to_number(sql).trim();
			} else {
				// sql = to_date(src, formatStr).trim();
			}
		}
		return " " + sql + " ";
	}
	
	public static String trunc(String src) throws Exception {
		return trunc(src, null);
	}
}
