package example.com.fan.base.sign;

import java.io.IOException;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class DesUtil {
	private final static String DES = "DES";
	private final static char[] COMPLEMENT = "Complement".toCharArray();
	private static byte[] iv = { 2, 0, 1, 5, 1, 1, 2, 6 };
	private static final String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWZYX";

	/**
	 * Description 根据键值进行加密 密文固定
	 * 
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encryptFixed(String data, String key) {
		for (int i = 0; key.length() < 8 && key.length() % 8 != 0; i++) {
			key += COMPLEMENT[i];
		}
		try {

			byte[] bt = encrypt(data.getBytes("utf-8"), key.getBytes("utf-8"));
			data = new BASE64Encoder().encode(bt);
			StringBuffer sb = new StringBuffer();
			StringBuffer base = new StringBuffer(data);
			int index = data.lastIndexOf("=");
			// 伪装密文防止穷举
			if (index > 0) {
				base.deleteCharAt(index);
				sb.append((char) ((bt[1] + 128) % 26 + 65));
				sb.append((char) ((bt[4] + 128) % 26 + 65));
				sb.append((char) ((bt[3] + 128) % 26 + 65));
				sb.append((char) ((bt[5] + 128) % 26 + 65));
				sb.append("!");
				sb.append(base);
				sb.append((char) ((bt[7] + 128) % 26 + 65));
				sb.append((char) ((bt[0] + 128) % 26 + 65));
				sb.append((char) ((bt[2] + 128) % 26 + 65));
				sb.append((char) ((bt[6] + 128) % 26 + 65));
				if (sb.length() > 6) {
					// 换位
					char c = sb.charAt(3);
					sb.replace(3, 4, sb.charAt(5) + "");
					sb.replace(5, 6, c + "");
				}
			} else {
				sb = new StringBuffer(data);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return data;
	}

	/**
	 * Description 根据键值进行解密 密文固定
	 * 
	 * @param data
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decryptFixed(String data, String key) {
		for (int i = 0; key.length() < 8 && key.length() % 8 != 0; i++) {
			key += COMPLEMENT[i];
		}

		if (data == null) {
			return null;
		}

		StringBuffer sb = new StringBuffer(data);

		// 判断伪装
		if ((sb.charAt(4) + "").equals("!")) {
			if (sb.length() > 6) {
				// 换位
				char c = sb.charAt(3);
				sb.replace(3, 4, sb.charAt(5) + "");
				sb.replace(5, 6, c + "");
			}
			data = sb.substring(5, sb.length() - 4) + "=";
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] buf = decoder.decodeBuffer(data);
			byte[] bt = decrypt(buf, key.getBytes("utf-8"));
			return new String(bt);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(data);
	}

	/**
	 * Description 根据键值进行加密 加入随机
	 *
	 * @param data
	 * @param key
	 *            加密键byte数组
	 * @return
	 * @throws Exception
	 */
	public static String encryptRandom(String data, String key) {
		for (int i = 0; key.length() < 8 && key.length() % 8 != 0; i++) {
			key += COMPLEMENT[i];
		}
		try {

			StringBuffer sb = new StringBuffer();

			// 插入垃圾字符
			sb.append(chars.charAt((int) (Math.random() * chars.length())));
			sb.append(data);
			sb.append(chars.charAt((int) (Math.random() * chars.length())));

			if (sb.length() > 6) {
				// 换位
				char c = sb.charAt(3);
				sb.replace(3, 4, sb.charAt(5) + "");
				sb.replace(5, 6, c + "");
				System.out.println(sb);
			}

			byte[] bt = encrypt(sb.toString().getBytes("utf-8"),
					key.getBytes("utf-8"));
			data = new BASE64Encoder().encode(bt);
			StringBuffer base = new StringBuffer(data);
			int index = data.lastIndexOf("=");
			// 清空
			sb = new StringBuffer();
			// 伪装密文防止穷举
			if (index > 0) {
				base.deleteCharAt(index);
				sb.append(UUID.randomUUID().toString().substring(0, 4));
				sb.append("!");
				sb.append(base);
				sb.append("!");
				sb.append(chars.charAt((int) (Math.random() * chars.length())));
				sb.append(iv[(int) (Math.random() * iv.length)]);
				sb.append(chars.charAt((int) (Math.random() * chars.length())));
			} else {
				sb.append(chars.charAt((int) (Math.random() * chars.length())));
				sb.append(data);
				sb.append(chars.charAt((int) (Math.random() * chars.length())));
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return data;
	}

	/**
	 * Description 根据键值进行解密
	 *
	 * @param data
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return
	 * @throws IOException
	 * @throws Exception
	 */
	public static String decryptRandom(String data, String key) {
		for (int i = 0; key.length() < 8 && key.length() % 8 != 0; i++) {
			key += COMPLEMENT[i];
		}
		if (data == null) {
			return null;
		}

		// 判断伪装
		if ((data.toCharArray()[4] + "").equals("!")) {
			data = data.substring(5, data.length() - 4) + "=";
		} else {
			data = data.substring(1, data.length() - 1);
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] buf = decoder.decodeBuffer(data);
			byte[] bt = decrypt(buf, key.getBytes("utf-8"));

			StringBuffer sb = new StringBuffer(new String(bt));
			if (sb.length() > 6) {
				// 换位
				char c = sb.charAt(3);
				sb.replace(3, 4, sb.charAt(5) + "");
				sb.replace(5, 6, c + "");
			}
			return sb.substring(1, sb.length() - 1).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new String(data);
	}

	/**
	 * Description 根据键值进行加密
	 * 
	 * @param data
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return
	 * @throws Exception
	 */
	static byte[] encrypt(byte[] data, byte[] key) throws Exception {

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, zeroIv);

		return cipher.doFinal(data);
	}

	/**
	 * Description 根据键值进行解密
	 * 
	 * @param data
	 * @param key
	 *            加密私钥，长度不能够小于8位
	 * @return
	 * @throws Exception
	 */
	static byte[] decrypt(byte[] data, byte[] key) throws Exception {

		// 从原始密钥数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);

		IvParameterSpec zeroIv = new IvParameterSpec(iv);

		// 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey securekey = keyFactory.generateSecret(dks);

		// Cipher对象实际完成解密操作
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		// 用密钥初始化Cipher对象
		cipher.init(Cipher.DECRYPT_MODE, securekey, zeroIv);

		return cipher.doFinal(data);
	}

}