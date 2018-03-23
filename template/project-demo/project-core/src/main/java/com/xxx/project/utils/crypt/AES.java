package #{packagePrefix}#.utils.crypt;

import java.nio.charset.Charset;
import java.security.Key;
import java.util.Arrays;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class AES {

	private static final Base64 base64 = new Base64();
	private static final Charset CHARSET = Charset.forName("utf-8");

	/**
	 * SHA1 Signature加密
	 * 
	 * @param msg
	 * @return
	 */
	public static String generateSHA1(String... params) {
		Arrays.sort(params);
		StringBuilder sb = new StringBuilder();
		for (String s : params) {
			sb.append(s);
		}

		return DigestUtils.sha1Hex(sb.toString());
	}

	/**
	 * 对明文进行加密.
	 *
	 * @param key 必须为43位
	 * @param plainText 需要加密的明文
	 * @param appidOrCorpid 密钥附加验证
	 * @return 加密后base64编码的字符串
	 */
	public static String encryptAES(String plainText, String key, String appidOrCorpid) {
		ByteGroup byteCollector = new ByteGroup();
		String randomStr = genRandomStr();
		byte[] aesKey = Base64.decodeBase64(key + "=");
		byte[] randomStringBytes = randomStr.getBytes(CHARSET);
		byte[] plainTextBytes = plainText.getBytes(CHARSET);
		byte[] bytesOfSizeInNetworkOrder = number2BytesInNetworkOrder(plainTextBytes.length);
		byte[] appIdBytes = appidOrCorpid.getBytes(CHARSET);

		// randomStr + networkBytesOrder + text + appid
		byteCollector.addBytes(randomStringBytes);
		byteCollector.addBytes(bytesOfSizeInNetworkOrder);
		byteCollector.addBytes(plainTextBytes);
		byteCollector.addBytes(appIdBytes);

		// ... + pad: 使用自定义的填充方式对明文进行补位填充
		byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
		byteCollector.addBytes(padBytes);

		// 获得最终的字节流, 未加密
		byte[] unencrypted = byteCollector.toBytes();

		try {
			// 设置加密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

			// 加密
			byte[] encrypted = cipher.doFinal(unencrypted);

			// 使用BASE64对加密后的字符串进行编码
			String base64Encrypted = base64.encodeToString(encrypted);

			return base64Encrypted;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 对密文进行解密.
	 *
	 * @param key 必须为43位
	 * @param cipherText 需要解密的密文
	 * @param appidOrCorpid 密钥附加验证
	 * @return 解密得到的明文
	 */
	public static String decryptAES(String cipherText, String key, String appidOrCorpid) {
		byte[] original;
		try {
			byte[] aesKey = Base64.decodeBase64(key + "=");
			// 设置解密模式为AES的CBC模式
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
			IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
			cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

			// 使用BASE64对密文进行解码
			byte[] encrypted = Base64.decodeBase64(cipherText);

			// 解密
			original = cipher.doFinal(encrypted);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		String xmlContent, from_appid;
		try {
			// 去除补位字符
			byte[] bytes = PKCS7Encoder.decode(original);

			// 分离16位随机字符串,网络字节序和AppId
			byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

			int xmlLength = bytesNetworkOrder2Number(networkOrder);

			xmlContent = new String(Arrays.copyOfRange(bytes, 20, 20 + xmlLength), CHARSET);
			from_appid = new String(Arrays.copyOfRange(bytes, 20 + xmlLength, bytes.length), CHARSET);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		// appid不相同的情况
		if (!from_appid.equals(appidOrCorpid)) {
			throw new RuntimeException("AppID不正确");
		}

		return xmlContent;

	}

	/**
	 * 3DES加密
	 * 
	 * @param plainText 普通文本
	 * @param iv 向量，8位
	 * @param secretkey 必须是24位
	 * @return
	 */
	public static String encryptDES(String plainText, String secretkey, String iv) {
		Key deskey = null;
		StringBuilder stringBuilder = new StringBuilder("");
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretkey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);

			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
			byte[] encryptData = cipher.doFinal(plainText.getBytes(CHARSET));

			if (encryptData == null || encryptData.length <= 0) {
				return null;
			}
			for (int i = 0; i < encryptData.length; i++) {
				int v = encryptData[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					stringBuilder.append(0);
				}
				stringBuilder.append(hv);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return stringBuilder.toString();
	}

	/**
	 * 3DES解密
	 * 
	 * @param encryptText 加密文本
	 * @param iv 向量，8位
	 * @param secretkey 必须是24位
	 * @return
	 */
	public static String decryptDES(String encryptText, String secretkey, String iv) {
		if (encryptText == null || encryptText.equals("")) {
			return null;
		}
		encryptText = encryptText.toUpperCase();
		int length = encryptText.length() / 2;
		char[] hexChars = encryptText.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) ((byte) "0123456789ABCDEF".indexOf((hexChars[pos])) << 4 | (byte) "0123456789ABCDEF".indexOf((hexChars[pos + 1])));
		}
		byte[] str = d;
		Key deskey = null;
		try {
			DESedeKeySpec spec = new DESedeKeySpec(secretkey.getBytes());
			SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
			deskey = keyfactory.generateSecret(spec);
			Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
			IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, deskey, ips);

			byte[] decryptData = cipher.doFinal(str);
			return new String(decryptData, CHARSET);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/**
	 * 随机生成16位字符串
	 *
	 * @return
	 */
	public static String genRandomStr() {
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 将一个数字转换成生成4个字节的网络字节序bytes数组
	 *
	 * @param number
	 */
	private static byte[] number2BytesInNetworkOrder(int number) {
		byte[] orderBytes = new byte[4];
		orderBytes[3] = (byte) (number & 0xFF);
		orderBytes[2] = (byte) (number >> 8 & 0xFF);
		orderBytes[1] = (byte) (number >> 16 & 0xFF);
		orderBytes[0] = (byte) (number >> 24 & 0xFF);
		return orderBytes;
	}

	/**
	 * 4个字节的网络字节序bytes数组还原成一个数字
	 *
	 * @param bytesInNetworkOrder
	 */
	private static int bytesNetworkOrder2Number(byte[] bytesInNetworkOrder) {
		int sourceNumber = 0;
		for (int i = 0; i < 4; i++) {
			sourceNumber <<= 8;
			sourceNumber |= bytesInNetworkOrder[i] & 0xff;
		}
		return sourceNumber;
	}
}
