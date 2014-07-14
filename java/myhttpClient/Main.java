package myhttpClient;

import java.nio.charset.Charset;

public class Main {

	/**
	 * @param args
	 * @author huangting
	 * @throws Exception 
	 * @date 2014-6-30
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		NoProxyHttpClient noProxy = new NoProxyHttpClient();
		String url = "http://sd.esf.sina.com.cn/detail/2680680";
		Charset charset = Charset.forName("utf-8");
		//there is no proxy
		try {
	        //noProxy.download(url, charset);
        } catch (Exception e) {
	        // TODO Auto-generated catch block
        	System.out.println("there is no proxy");
	        System.out.println(e.getMessage());
        }
		
		//there is proxy
		HasProxyHttpClient hasProxyHttpClient = new HasProxyHttpClient();
		try {
	        //System.out.println(hasProxyHttpClient.download(url, charset));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
        	System.out.println("there is proxy");
        	 System.out.println(e.getMessage());
        }
		
		//test jsoup proxy
		noProxcyJsop soup = new noProxcyJsop();
		try {
	        //System.out.println(soup.innerDownload(url, charset));
        } catch (Exception e) {
	        // TODO Auto-generated catch block
        	System.out.println("JSOP : there is no proxy");
        	System.out.println(e.getMessage());
        }
		//test jsoup has proxy
		HasProxyJsop hasSoup = new HasProxyJsop();
			try {
			    // System.out.println(hasSoup.innerDownload(url, charset));
		    } catch (Exception e) {
			       // TODO Auto-generated catch block
		        System.out.println("JSOP : there is  proxy");
		        System.out.println(e.getMessage());
		    }
			
			//there is no proxy
			try {
		        //System.out.println(noProxy.download(url, charset));
	        } catch (Exception e) {
		        // TODO Auto-generated catch block
	        	System.out.println("there is no proxy");
		        System.out.println(e.getMessage());
	        }
		//get local address
		GetLocalIpAddress getLocalAddress = new GetLocalIpAddress();
		System.out.println(getLocalAddress.getLocalIpAddr());
			
	}

}
