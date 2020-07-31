package com.qin.config;

import java.util.HashSet;

import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.qin.utils.RedisCache;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig<E> {
	// 1.连接池
	@Bean
	public JedisPoolConfig getJedisPoolConfig() {
		JedisPoolConfig jedisConfig = new JedisPoolConfig();
		jedisConfig.setMaxIdle(400);// 资源池允许的最大空闲连接数
		jedisConfig.setMaxTotal(6000);// 资源池中的最大连接数
		jedisConfig.setMaxWaitMillis(1000);// 当资源池连接用尽后，调用者的最大等待时间（单位为毫秒）
		jedisConfig.setTestOnBorrow(true);// 向资源池借用连接时是否做连接有效性检测（ping）。检测到的无效连接将会被移除。
		return jedisConfig;
	}

	// 2.配置连接工厂
	@Bean
	public JedisConnectionFactory getJFactory(JedisPoolConfig pool) {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setPoolConfig(pool);// 设置连接池
		factory.setHostName("127.0.0.1");
		factory.setPort(6379);
		factory.setTimeout(100000);
		factory.setUsePool(true);
		return factory;
	}

	// 3.
	@SuppressWarnings("all")
	@Bean
	public RedisTemplate getRedisTemplate(JedisConnectionFactory jConnectionFactory) {
		RedisTemplate tpl = new RedisTemplate();
		tpl.setConnectionFactory(jConnectionFactory);
		tpl.setEnableTransactionSupport(true);
		return tpl;

	}

	@SuppressWarnings("all")
	@Bean
	public SimpleCacheManager getSCacheManager(RedisTemplate redisTemplate) {
		RedisCache cache = new RedisCache();
		cache.setRedisTemplate(redisTemplate);
		cache.setName("cache01");
		HashSet<RedisCache> set = new HashSet<RedisCache>();
		set.add(cache);
		SimpleCacheManager manager = new SimpleCacheManager();
		manager.setCaches(set);
		return manager;
	}
	// jieba分词

	/*
	 * @Bean public JiebaSegmenter jiebaSegmenter() { return new JiebaSegmenter();
	 * 
	 * }
	 */

}
