/****************************************************************
 * WnkParamMap
 * ArgumentResolver에서 사용할 Customer Map 
 * @author skycow79
 * @version 1.0
 * @date 2016.12.22
 ****************************************************************/
package co.wnk.framework.core.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WnkParamMap {
	private static final Logger logger = LoggerFactory.getLogger(WnkParamMap.class);
	private Map<String, Object> paramMap = new HashMap<String, Object>();
	
	/***********************************************************
	 * Key에 해당하는 Value(Object)를 반환한다.
	 * @param key
	 * @return Object
	 ***********************************************************/
	public Object get(String key){
		return MapUtils.getObject(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Object)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return Object
	 ***********************************************************/
	public Object get(String key, String defaultValue){
		return MapUtils.getObject(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(String)를 반환한다.
	 * @param key
	 * @return String
	 ***********************************************************/
	public String getString(String key){
		return MapUtils.getString(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(String)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return String
	 ***********************************************************/
	public String getString(String key, String defaultValue){
		return MapUtils.getString(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Boolean)를 반환한다.
	 * @param key
	 * @return Boolean
	 ***********************************************************/
	public boolean getBoolean(String key){
		return MapUtils.getBooleanValue(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Boolean)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return boolean
	 ***********************************************************/
	public boolean getBoolean(String key, boolean defaultValue){
		return MapUtils.getBooleanValue(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Integer)를 반환한다.
	 * @param key
	 * @return Integer
	 ***********************************************************/
	public Integer getInteger(String key){
		return MapUtils.getInteger(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Integer)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return Integer
	 ***********************************************************/
	public Integer getInteger(String key, Integer defaultValue){
		return MapUtils.getInteger(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(int)를 반환한다.
	 * @param key
	 * @return int
	 ***********************************************************/
	public int getInt(String key){
		return MapUtils.getIntValue(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(int)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return int
	 ***********************************************************/
	public int getInt(String key, int defaultValue){
		return MapUtils.getIntValue(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Long)를 반환한다.
	 * @param key
	 * @return Long
	 ***********************************************************/
	public Long getLong(String key){
		return MapUtils.getLong(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Long)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return Long
	 ***********************************************************/
	public Long getLong(String key, Long defaultValue){
		return MapUtils.getLong(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Double)를 반환한다.
	 * @param key
	 * @return Double
	 ***********************************************************/
	public Double getDouble(String key){
		return MapUtils.getDouble(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Double)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return Double
	 ***********************************************************/
	public Double getDouble(String key, Double defaultValue){
		return MapUtils.getDouble(paramMap, key, defaultValue); 
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Float)를 반환한다.
	 * @param key
	 * @return Float
	 ***********************************************************/
	public Float getFloat(String key){
		return MapUtils.getFloat(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Float)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return Float
	 ***********************************************************/
	public Float getFloat(String key, Float defaultValue){
		return MapUtils.getFloat(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Short)를 반환한다.
	 * @param key
	 * @return Short
	 ***********************************************************/
	public Short getShort(String key){
		return MapUtils.getShort(paramMap, key);
	}
	
	/***********************************************************
	 * Key에 해당하는 Value(Short)를 반환한다.
	 * Key에 해당하는 Value가 없으면 defaultValue를 반환한다.
	 * @param key
	 * @param defaultValue
	 * @return Short
	 ***********************************************************/
	public Short getShort(String key, Short defaultValue){
		return MapUtils.getShort(paramMap, key, defaultValue);
	}
	
	/***********************************************************
	 * 선언되어 있는 paramMap을 반환한다. 
	 * @return Map<String,Object>
	 ***********************************************************/
	public Map<String,Object> getMap(){
		return paramMap;
	}
	
	/***********************************************************
	 * 해당 key에 해당하는 value(Object)를 등록한다.
	 * @param key
	 * @param value
	 ***********************************************************/
	public void put(String key, Object value){
		paramMap.put("key", value);
	}
	
	/***********************************************************
	 * paramMap의 Empty 여부를 체크한다.
	 * @return boolean
	 ***********************************************************/
	public boolean isEmpty(){
		return paramMap.isEmpty();
	}
	
	/***********************************************************
	 * 해당 Key에 대한 포함여부를 체크한다.
	 * @param key
	 * @return boolean
	 ***********************************************************/
	public boolean containsKey(String key){
		return paramMap.containsKey(key);
	}
	
	/***********************************************************
	 * 해당 Value에 대한 포함여부를 체크한다.
	 * @param value
	 * @return boolean
	 ***********************************************************/
	public boolean containsValue(Object value){
		return paramMap.containsValue(value);
	}
	
	/***********************************************************
	 * 해당 Key에 해당하는 값을 Remove 한다.
	 * @param key
	 * @return
	 ***********************************************************/
	public Object remove(String key){
		return paramMap.remove(key);
	}
	
	/***********************************************************
	 * ParamMap을 Clear 한다.
	 ***********************************************************/
	public void clear(){
		paramMap.clear();
	}
	
	/***********************************************************
	 * ParamMap의 모든값을 가져와서 반환한다.
	 * @return Set<Entry<String, Object>>
	 ***********************************************************/
	public Set<Entry<String, Object>> entrySet(){
        return paramMap.entrySet();
    }
    
	/***********************************************************
	 * ParamMap의 key을 가져와서 반환한다.
	 * @return
	 ***********************************************************/
    public Set<String> keySet(){
        return paramMap.keySet();
    }
}
