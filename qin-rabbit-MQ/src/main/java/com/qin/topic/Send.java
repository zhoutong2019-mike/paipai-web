package com.qin.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.qin.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String EXCHANGE_NAME = "test_exchange_topic";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection conn = ConnectionUtil.getConnection();
		Channel channel = conn.createChannel();

		channel.exchangeDeclare(EXCHANGE_NAME, "topic");

		String msg = "…Ã∆∑...";
		String routingKey = "goods.delete";
		channel.basicPublish(EXCHANGE_NAME, routingKey, null, msg.getBytes());
		System.out.println("sendmsg ok :" + msg);
		channel.close();
		conn.close();
	}

}
