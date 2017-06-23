package test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code {

	public static void main(String[] args) {
		String url = "【妙趣水桶包 不规则包型更有大容量】，点击链接再选择浏览器打开http://c.b1wv.com/h.iI8vkW?cv=kG40Zw5QAdg&sm=5cf31f ，或复制这条信息￥kG40Zw5QAdg￥后打开手机淘宝";
		//Pattern pattern = Pattern.compile("(http|ftp|https|www)[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)[^\u4e00-\u9fa5\\s]*");
		Pattern pattern = Pattern.compile("(http|ftp|https|www)[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)([a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~])*");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			url = matcher.group(0);
		}
		System.out.println(url);

	}
	
	

}
