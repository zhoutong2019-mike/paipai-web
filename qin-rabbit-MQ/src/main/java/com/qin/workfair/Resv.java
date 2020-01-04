package com.qin.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.qin.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class Resv {
	private static final String QUEUE_NAME = "test_work_queue";
	public static void main(String[] args) throws IOException, TimeoutException {
		Connection conn = ConnectionUtil.getConnection();
		final Channel channel = conn.createChannel();
		boolean durable = false;
		channel.queueDeclare(QUEUE_NAME, durable , false, false, null);
		channel.basicQos(1);//保证每次只分发一个
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "utf-8");
				System.out.println("消费者[1] 接收消息 = " + msg);
				channel.basicAck(envelope.getDeliveryTag(), false);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		boolean autoAck = true ;
		channel.basicConsume(QUEUE_NAME,autoAck , consumer);
	}

}
