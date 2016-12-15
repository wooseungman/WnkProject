package co.wnk.framework.core.common.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.xerces.impl.dv.util.Base64;

import co.wnk.framework.core.common.encryption.SeedCipher;
import co.wnk.framework.core.common.util.config.ApplicationProperty;

public class WnkEncryptionUtil {
	
	private static final Logger log = LoggerFactory.getLogger(WnkEncryptionUtil.class);
	private final String SEED_KEY = ApplicationProperty.get("encryption.key.seed");
	private SeedCipher seed = new SeedCipher();
	
	/**
	 * SHA-256를 이용하여 패스워드를 암호화한다
	 * @param planPwd 평문 패스워드
	 * @return 암호화된 패스워드
	 */
	public String encryptionPassword(String planPwd) {

		String encPwd = null;
		try {
			MessageDigest sh = MessageDigest.getInstance("SHA-256");
			sh.update(planPwd.getBytes());
			byte byteData[] = sh.digest();
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; i++) {
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			}
			encPwd = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return encPwd;
	}

	/**
	 * SEED-128을 이용하여 평문을 암호화한다.
	 * @param planPwd 평문
	 * @return 암호화된 택스트
	 */
	public String encryption(String planPwd) {

		String encPwd = null;
		try {
			encPwd = Base64.encode(seed.encrypt(planPwd, SEED_KEY.getBytes(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return encPwd;
	}

	/**
	 * SEED-128을 이용하여 평문을 복호화한다.
	 * @param planPwd 암호화된 택스트
	 * @return 평문
	 */
	public String decryption(String encTxt) {

		String planTxt = null;
		try {
			// TODO : GET방식을 통해 전달되는 경우, '+'가 ' '로 넘어오는 경우가 있음
			encTxt = StringUtils.replace(encTxt, " ", "+");

			byte[] encryptbytes = Base64.decode(encTxt);
			planTxt = seed.decryptAsString(encryptbytes, SEED_KEY.getBytes(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return planTxt;
	}
}
