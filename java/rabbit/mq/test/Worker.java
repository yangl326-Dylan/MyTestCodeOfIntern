package rabbit.mq.test;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Worker {
	 private final static String QUEUE_NAME = "task_queue";

	    public static void main(String[] argv) throws Exception {
	    //same to the sender
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("211.155.86.102");
	    factory.setUsername("guest");
	    factory.setPassword("echo");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();
	    
	    boolean durable = true;
	    channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
	    System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
	    //we are about to tell the server to deliver us the messages from the queue.
	    //since it will push us messages asynchronously, we provide a callback in the form of an object
	    //that will buffer the messages until we're ready to use them.That is what QueueingConsumer does.
	    QueueingConsumer consumer = new QueueingConsumer(channel);
	    // open the massage acknowledgement
	    boolean autoAck = false;
	    //consumer中需要的
	    channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	    //This tells RabbitMQ not to give more than one message to a worker at a time. 
	    //Or, in other words, don't dispatch a new message to a worker 
	    //until it has processed and acknowledged the previous one. 
	    //Instead, it will dispatch it to the next worker that is not still busy.
	    int prefetchCount = 1;
	    channel.basicQos(prefetchCount);
	    while (true) {
	      QueueingConsumer.Delivery delivery = consumer.nextDelivery();
	      String message = new String(delivery.getBody());
	      System.out.println(" [x] Received '" + message + "'");
	      doWork(message);
	      System.out.println(" [x] Done");
	      channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
	    }
	  }
	    private static void doWork(String task) throws InterruptedException {
	        for (char ch: task.toCharArray()) {
	            if (ch == '.'){
	            	System.out.println("doing ... ");
	            	Thread.sleep(1000);
	            }
	        }
	    }
}
