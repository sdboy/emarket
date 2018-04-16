package com.emarket.util;

import java.security.MessageDigest;

/**
 * @author jiaoguang
 * @version V1.0.0
 * @project emarket
 * @package com.emarket.util
 * @class MD5Util
 * @create 2018/4/8 22:29
 * @Copyright
 * @review
 */
public class MD5Util {
  /**
   *
   * @method byteArrayToHexString
   * @author jiaoguang
   * @create 2018/4/8 22:30
   * @modifier jiaoguang
   * $modify 2018/4/8 22:30
   * @param b
   * @return java.lang.String
   * @since V1.0.0
   * @version V1.0.0
   */
  private static String byteArrayToHexString(byte b[]) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
      resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
  }

  private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
      n += 256;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
  }

  /**
   * 返回大写MD5
   * @param origin
   * @param charsetname
   * @return
   */
  private static String MD5Encode(String origin, String charsetname) {
    String resultString = null;
    try {
      resultString = new String(origin);
      MessageDigest md = MessageDigest.getInstance("MD5");
      if (charsetname == null || "".equals(charsetname))
        resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
      else
        resultString = byteArrayToHexString(md.digest(resultString.getBytes(charsetname)));
    } catch (Exception exception) {
    }
    return resultString.toUpperCase();
  }

  public static String MD5EncodeUtf8(String origin) {
    origin = origin + PropertiesUtil.getPorperty("password.salt", "");
    return MD5Encode(origin, "utf-8");
  }


  private static final String hexDigits[] = {"0", "1", "2", "3", "4", "5",
    "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
}
