package rabbit.mq.test;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;

public class Recv {

    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
    //same to the sender
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost("211.155.86.102");
    factory.setUsername("guest");
    factory.setPassword("echo");
    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
    //we are about to tell the server to deliver us the messages from the queue.
    //since it will push us messages asynchronously, we provide a callback in the form of an object
    //that will buffer the messages until we're ready to use them.That is what QueueingConsumer does.
    QueueingConsumer consumer = new QueueingConsumer(channel);
    channel.basicConsume(QUEUE_NAME, true, consumer);
    
    while (true) {
      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
      String message = new String(delivery.getBody());
      System.out.println(" [x] Received '" + message + "'");
    }
  }
}