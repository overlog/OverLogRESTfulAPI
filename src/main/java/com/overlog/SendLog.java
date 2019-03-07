package com.overlog;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class SendLog {
    private static final String QUEUE_NAME = "overlogQueue";

    public static void sendMessage(String logPayload) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false ,null);
        channel.basicPublish("", QUEUE_NAME, null, logPayload.getBytes());
        System.out.println(" [x] Sent '" + logPayload + "'");
        channel.close();
        connection.close();
    }
}
