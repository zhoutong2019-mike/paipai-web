package com.qin.util;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ConnectionUtil {
	/**
	 * 	获取MQ的连接
	 * @return
	 * @throws TimeoutException 
	 * @throws IOException 
	 */
	public static Connection getConnection() throws IOException, TimeoutException {
		//定义一个连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		factory.setPort(5672);
		factory.setVirtualHost("/vhost");
		factory.setUsername("qin");
		factory.setPassword("qin");
		return factory.newConnection();
	}

}
