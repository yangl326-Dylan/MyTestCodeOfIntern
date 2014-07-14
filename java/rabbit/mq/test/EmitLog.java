package rabbit.mq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EmitLog {
	private static final String Exchange_NAME = "logs";
	public static void main(String[] argv) throws java.io.IOException{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("211.155.86.102");
	    factory.setUsername("guest");
	    factory.setPassword("echo");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    channel.exchangeDeclare(Exchange_NAME, "fanout");
	    
	    String message = getMessage(argv);
	    
	    channel.basicPublish(Exchange_NAME, "", null, message.getBytes());
	    
	    System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
	}
	
	 private static String getMessage(String[] strings){
		    if (strings.length < 1)
		        return "Hello .........!";
		    return joinStrings(strings, " ");
	}
	 private static String joinStrings(String[] strings, String delimiter) {
		    int length = strings.length;
		    if (length == 0) return "";
		    StringBuilder words = new StringBuilder(strings[0]);
		    for (int i = 1; i < length; i++) {
		        words.append(delimiter).append(strings[i]);
		    }
		    return words.toString();
		}
}
