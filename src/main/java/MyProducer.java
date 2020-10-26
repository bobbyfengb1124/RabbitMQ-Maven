import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author bobbyfeng
 */
public class MyProducer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(Constants.BROKER_IP);
        factory.setPort(Constants.PORT);
        factory.setVirtualHost(Constants.VIRTUAL_HOST);
        factory.setUsername(Constants.USER_NAME);
        factory.setPassword(Constants.PASSWORD);

        Connection connection =factory.newConnection();

        Channel channel = connection.createChannel();

        String msg = "Hello world, Rabbit MQ";

        channel.basicPublish(Constants.EXCHANGE_NAME, "bobby.best",null,msg.getBytes());
        channel.close();
        connection.close();
    }
}
