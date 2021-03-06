package myhttpClient;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


public class NoProxyHttpClient {
	public String download(String url, Charset charset) throws Exception{
		String host = getHost(url);

		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet getMethod = new HttpGet(url);

		try {
			HttpResponse response = httpClient.execute(getMethod);
			// 若不是200，则返回空串
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				getMethod.releaseConnection();
				// 下载时发生异常，下载器={0},url={1},异常信息={2}。
				throw new Exception("下载器返回状态码：" + statusCode);
			}
			// 读取内容
			byte[] responseBody = IOUtils.toByteArray(response.getEntity().getContent());

			getMethod.releaseConnection();

			return new String(responseBody, charset.name());
		} finally {
			try {
				httpClient.close();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
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
