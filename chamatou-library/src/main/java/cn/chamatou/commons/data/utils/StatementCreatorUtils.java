package cn.chamatou.commons.data.utils;
/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.sql.Types;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
public abstract class StatementCreatorUtils {
	//private static final Logger logger = Logger.getLogger(StatementCreatorUtils.class);
	private static final Map<Class<?>, Integer> javaTypeToSqlTypeMap = new HashMap<Class<?>, Integer>(32);
	static {
		javaTypeToSqlTypeMap.put(boolean.class, Types.BOOLEAN);
		javaTypeToSqlTypeMap.put(Boolean.class, Types.BOOLEAN);
		javaTypeToSqlTypeMap.put(String.class, Types.VARCHAR);
		javaTypeToSqlTypeMap.put(byte.class, Types.TINYINT);
		javaTypeToSqlTypeMap.put(Byte.class, Types.TINYINT);
		javaTypeToSqlTypeMap.put(short.class, Types.SMALLINT);
		javaTypeToSqlTypeMap.put(Short.class, Types.SMALLINT);
		javaTypeToSqlTypeMap.put(int.class, Types.INTEGER);
		javaTypeToSqlTypeMap.put(Integer.class, Types.INTEGER);
		javaTypeToSqlTypeMap.put(long.class, Types.BIGINT);
		javaTypeToSqlTypeMap.put(Long.class, Types.BIGINT);
		javaTypeToSqlTypeMap.put(BigInteger.class, Types.BIGINT);
		javaTypeToSqlTypeMap.put(float.class, Types.FLOAT);
		javaTypeToSqlTypeMap.put(Float.class, Types.FLOAT);
		javaTypeToSqlTypeMap.put(double.class, Types.DOUBLE);
		javaTypeToSqlTypeMap.put(Double.class, Types.DOUBLE);
		javaTypeToSqlTypeMap.put(BigDecimal.class, Types.DECIMAL);
		javaTypeToSqlTypeMap.put(java.sql.Date.class, Types.DATE);
		javaTypeToSqlTypeMap.put(java.sql.Time.class, Types.TIME);
		javaTypeToSqlTypeMap.put(java.sql.Timestamp.class, Types.TIMESTAMP);
		javaTypeToSqlTypeMap.put(Blob.class, Types.BLOB);
		javaTypeToSqlTypeMap.put(Clob.class, Types.CLOB);
	}
	private static boolean isStringValue(Class<?> inValueType) {
		return (CharSequence.class.isAssignableFrom(inValueType) ||
				StringWriter.class.isAssignableFrom(inValueType));
	}
	/**
	 * 设置PreparedStatement参数值
	 */
	public static void setValue(PreparedStatement ps, int paramIndex,Object inValue) throws SQLException{
		setValue(ps, paramIndex, 2, inValue);
	}
	
	/**
	 * 设置PreparedStatement参数值
	 * @param ps
	 * @param paramIndex
	 * @param scale
	 * @param inValue
	 * @throws SQLException
	 */
	public static void setValue(PreparedStatement ps, int paramIndex, Integer scale,
			Object inValue) throws SQLException {
		int sqlType = javaTypeToSqlTypeMap.get(inValue.getClass());
		if (sqlType == Types.VARCHAR || sqlType == Types.NVARCHAR
				|| sqlType == Types.LONGVARCHAR
				|| sqlType == Types.LONGNVARCHAR) {
			ps.setString(paramIndex, inValue.toString());
		} else if ((sqlType == Types.CLOB || sqlType == Types.NCLOB)
				&& isStringValue(inValue.getClass())) {
			String strVal = inValue.toString();
			if (strVal.length() > 4000) {
				// Necessary for older Oracle drivers, in particular when
				// running against an Oracle 10 database.
				// Should also work fine against other drivers/databases since
				// it uses standard JDBC 4.0 API.
				try {
					if (sqlType == Types.NCLOB) {
						ps.setNClob(paramIndex, new StringReader(strVal),
								strVal.length());
					} else {
						ps.setClob(paramIndex, new StringReader(strVal),
								strVal.length());
					}
					return;
				} catch (AbstractMethodError err) {
					err.printStackTrace();
					//logger.debug(
					//		"JDBC driver does not implement JDBC 4.0 'setClob(int, Reader, long)' method",err);
				} catch (SQLFeatureNotSupportedException ex) {
					ex.printStackTrace();
					//logger.debug("JDBC driver does not support JDBC 4.0 'setClob(int, Reader, long)' method",ex);
				}
			}
			ps.setString(paramIndex, strVal);
		} else if (sqlType == Types.DECIMAL || sqlType == Types.NUMERIC) {
			if (inValue instanceof BigDecimal) {
				ps.setBigDecimal(paramIndex, (BigDecimal) inValue);
			} else if (scale != null) {
				ps.setObject(paramIndex, inValue, sqlType, scale);
			} else {
				ps.setObject(paramIndex, inValue, sqlType);
			}
		} else if (sqlType == Types.DATE) {
			if (inValue instanceof java.util.Date) {
				if (inValue instanceof java.sql.Date) {
					ps.setDate(paramIndex, (java.sql.Date) inValue);
				} else {
					ps.setDate(paramIndex, new java.sql.Date(
							((java.util.Date) inValue).getTime()));
				}
			} else if (inValue instanceof Calendar) {
				Calendar cal = (Calendar) inValue;
				ps.setDate(paramIndex, new java.sql.Date(cal.getTime()
						.getTime()), cal);
			} else {
				ps.setObject(paramIndex, inValue, Types.DATE);
			}
		} else if (sqlType == Types.TIME) {
			if (inValue instanceof java.util.Date) {
				if (inValue instanceof java.sql.Time) {
					ps.setTime(paramIndex, (java.sql.Time) inValue);
				} else {
					ps.setTime(paramIndex, new java.sql.Time(
							((java.util.Date) inValue).getTime()));
				}
			} else if (inValue instanceof Calendar) {
				Calendar cal = (Calendar) inValue;
				ps.setTime(paramIndex, new java.sql.Time(cal.getTime()
						.getTime()), cal);
			} else {
				ps.setObject(paramIndex, inValue, Types.TIME);
			}
		} else if (sqlType == Types.TIMESTAMP) {
			if (inValue instanceof java.util.Date) {
				if (inValue instanceof java.sql.Timestamp) {
					ps.setTimestamp(paramIndex, (java.sql.Timestamp) inValue);
				} else {
					ps.setTimestamp(paramIndex, new java.sql.Timestamp(
							((java.util.Date) inValue).getTime()));
				}
			} else if (inValue instanceof Calendar) {
				Calendar cal = (Calendar) inValue;
				ps.setTimestamp(paramIndex, new java.sql.Timestamp(cal
						.getTime().getTime()), cal);
			} else {
				ps.setObject(paramIndex, inValue, Types.TIMESTAMP);
			}
		}else if(sqlType==Types.INTEGER){
			ps.setInt(paramIndex, Integer.parseInt(inValue.toString()));
		}
	}
}
