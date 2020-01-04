package com.qin.work;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.qin.util.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Send {
	private static final String QUEUE_NAME = "test_work_queue";
	public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
		Connection conn = ConnectionUtil.getConnection();
		Channel channel = conn.createChannel();
		channel.queueDeclare(QUEUE_NAME, false, false, false, null);
		for (int i = 0; i < 50; i++) {
			String msg = "hello msg=" + i;
			channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
			Thread.sleep(i*20);
			System.out.println("发送者发送消息:="+msg);
		}
		channel.close();
		conn.close();
	}
}
