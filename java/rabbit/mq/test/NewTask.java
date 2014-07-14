package rabbit.mq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import com.rabbitmq.client.QueueingConsumer;

public class NewTask {
	private final static String QUEUE_NAME = "task_queue";

	  public static void main(String[] argv) throws Exception {
	      	      
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("211.155.86.102");
	    factory.setUsername("guest");
	    factory.setPassword("echo");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    boolean durable = true;
	    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
	    
	    String message = getMessage(argv);
	    //producer����Ҫ�� default exchange
	    channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
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
