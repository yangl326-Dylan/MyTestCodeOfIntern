package myhttpClient;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import org.jsoup.Jsoup;

public class noProxcyJsop {
	public String innerDownload(String url,Charset charset
	        ) throws Exception {
		String host = getHost(url);
		try {
			 String UA = "Mozilla/5.0 (Windows NT 6.1; rv:20.0) Gecko/20100101 Firefox/20.0";
			return Jsoup.connect(url).timeout(30000).userAgent(UA).get().html();
		} catch (Exception e) {
				throw e;
		}
	}
	protected String getHost(String url) {
		URL u;
		try {
			u = new URL(url);
			return u.getHost();
		} catch (MalformedURLException e) {
			return "";
		}
	}
}
