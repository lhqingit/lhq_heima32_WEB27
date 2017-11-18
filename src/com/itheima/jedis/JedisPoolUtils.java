package com.itheima.jedis;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

//jedis自己写的工具类
public class JedisPoolUtils {
	private static JedisPool pool = null;
	static {
		// 加载配置文件
		InputStream inputStream = JedisPoolUtils.class.getClassLoader().getResourceAsStream("redis.properties");
		Properties pro = new Properties();
		try {
			pro.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//获取配置文件的值
		int maxIdle = Integer.parseInt(pro.get("redis.maxIdle").toString());
		int minIdle = Integer.parseInt(pro.get("redis.minIdle").toString());
		int maxTotal = Integer.parseInt(pro.get("redis.maxTotal").toString());
		String url = pro.get("redis.url").toString();
		int port = Integer.parseInt(pro.get("redis.port").toString());
		//创建jedis连接池
			//连接池配置文件
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(maxIdle);
		poolConfig.setMinIdle(minIdle);
		poolConfig.setMaxTotal(maxTotal);
		pool = new JedisPool(poolConfig, url, port);
	}

	// 获取jedis资源的方法
	public static Jedis getJedis() {
		return pool.getResource();
	}

	// 测试工具类
	public static void main(String[] args) {
		Jedis jedis = getJedis();
		System.out.println(jedis.get("xxx"));
	}
}
