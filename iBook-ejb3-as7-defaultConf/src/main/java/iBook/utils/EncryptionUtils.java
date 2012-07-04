package iBook.utils;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.binary.Base64;

/**
 * Encryption utility class for encrypting/decrypting using base64.
 */
public class EncryptionUtils {
	public static final String DEFAULT_ENCODING = "UTF-8";

    /**
     * Encodes the given text according to base64.
     * @param text      the encoding text.
     * @return          the encoded text.
     */
	public static String base64encode(String text) {
		String rez = text;
		try {
			rez = Base64.encodeBase64String(text.getBytes(DEFAULT_ENCODING));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return rez;
	}

    /**
     * Decodes the given text according to base64.
     * @param text      the text to decode.
     * @return          the decoded text.
     */
	public static String base64decode(String text) {
		String decodedStr = null;
		try {
			decodedStr = new String(Base64.decodeBase64(text), DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return decodedStr;
	}
}

