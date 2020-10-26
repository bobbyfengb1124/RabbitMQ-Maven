import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author bobbyfeng
 */
public class MyConsumer {


    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(Constants.BROKER_IP);
        connectionFactory.setPort(Constants.PORT);
        connectionFactory.setVirtualHost(Constants.VIRTUAL_HOST);
        connectionFactory.setUsername(Constants.USER_NAME);
        connectionFactory.setPassword(Constants.PASSWORD);
        Connection con = connectionFactory.newConnection();
        Channel channel = con.createChannel();
        // String exchange, String type, boolean durable, boolean autoDelete, Map<String, Object> arguments
        channel.exchangeDeclare(Constants.EXCHANGE_NAME, "direct", false, false,null);
        // String queue, boolean durable, boolean exclusive, boolean autoDelete, Map<String, Object> arguments
        channel.queueDeclare(Constants.QUEUE_NAME,false,false,false,null);
        System.out.println("Waiting for message...");

        channel.queueBind(Constants.QUEUE_NAME,Constants.EXCHANGE_NAME,"bobby.best");
        com.rabbitmq.client.Consumer consumer = new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
              String msg = new String(body, StandardCharsets.UTF_8);
              System.out.println("Received message : '" + msg + "'");
              System.out.println("consumerTag : " + consumerTag );
              System.out.println("deliveryTag : " + envelope.getDeliveryTag() );
          }
        };
        // String queue, boolean autoAck, Consumer callback
        channel.basicConsume(Constants.QUEUE_NAME, true,consumer);
    }
}
