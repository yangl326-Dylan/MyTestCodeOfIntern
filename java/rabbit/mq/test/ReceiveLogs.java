package rabbit.mq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.ConsumerCancelledException;
import com.rabbitmq.client.QueueingConsumer;
import com.rabbitmq.client.ShutdownSignalException;

public class ReceiveLogs {
	private static final String Exchange_NAME = "logs";
	public static void main(String[] argv) throws java.io.IOException, ShutdownSignalException, ConsumerCancelledException, InterruptedException{
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("211.155.86.102");
	    factory.setUsername("guest");
	    factory.setPassword("echo");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    channel.exchangeDeclare(Exchange_NAME, "fanout");
	    String queueName = channel.queueDeclare().getQueue();
	    channel.queueBind(queueName, Exchange_NAME, "");
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName, true, consumer);

        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());

            System.out.println(" [x] Received '" + message + "'");
        }
	    
	}
}
