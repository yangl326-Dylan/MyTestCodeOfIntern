package myhttpClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetLocalIpAddress {
	private static String localIpAddress = null;
	public static String getLocalIpAddr() {
		InetAddress inet;
		try {
			inet = InetAddress.getLocalHost();
			localIpAddress = inet.getHostAddress();
			return localIpAddress;
		} catch (UnknownHostException e) {
			return "";
		}
	}
}
