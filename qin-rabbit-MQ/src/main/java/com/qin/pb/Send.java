package com.qin.pb;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.qin.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String EXCHAGE_NAME = "test_exchange_fanout";

	public static void main(String[] args) throws IOException, TimeoutException {
		Connection conn = ConnectionUtil.getConnection();
		Channel channel = conn.createChannel();
		// 声明交换机
		channel.exchangeDeclare(EXCHAGE_NAME, "fanout");
		// 发送消息
		String msg = "hello qin ! ";
		String routingKey="error";
		channel.basicPublish(EXCHAGE_NAME, routingKey, null, msg.getBytes());
		System.out.println("发送消息=" + msg);
		channel.close();
		conn.close();
	}
}
