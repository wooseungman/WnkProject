package co.wnk.framework.core.common.util.config;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationProperty {
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationProperty.class);
	
	static Properties props = new Properties();

	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/props/app.properties"));
		} catch (Exception e) {
			if (log.isWarnEnabled())
				log.warn(e.toString());
			else
				e.printStackTrace();
		}
	}
	
	public static Properties getInstance(){
		return props;
	}

	/**
	 * Return the string value matching to given key.
	 * If given key is not found , return null.
	 *
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return props.getProperty(key);
	}

	/**
	 * Return the integer value matching to given key.
	 * If given key is not found or value is not number format, throw exception.
	 *
	 * @param key
	 * @return
	 */
	public static int getInt(String key) {
		return Integer.parseInt(props.getProperty(key));
	}

	/**
	 * Return the boolean value matching to given key.
	 * If given key is not found or value is not boolean format, throw exception.
	 *
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key) {
		return Boolean.valueOf(props.getProperty(key)).booleanValue();
	}
}
