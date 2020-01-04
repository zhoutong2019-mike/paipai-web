package com.qin.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.qin.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.AMQP.BasicProperties;

public class resv2 {
	private static final String QUEUE_NAME = "test_queue_topic2";
	private static final String EXCHAGE_NAME = "test_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection conn = ConnectionUtil.getConnection();
		final Channel channel = conn.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		channel.queueBind(QUEUE_NAME, EXCHAGE_NAME, "goods.add");
		channel.queueBind(QUEUE_NAME, EXCHAGE_NAME, "goods.delete");
		channel.basicQos(1);// 保证每次只分发一个
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, BasicProperties properties, byte[] body)
					throws IOException {
				String msg = new String(body, "GBK");
				System.out.println("消费者[2] 接收消息 = " + msg);

				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					System.out.println("[2] done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		boolean autoAck = false;
		channel.basicConsume(QUEUE_NAME, autoAck, consumer);
	}
}
