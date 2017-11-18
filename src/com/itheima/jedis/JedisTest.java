package com.itheima.jedis;

import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisTest {
	// 通过java程序访问redis数据库
	@Test
	// 获得单一的jedis对象操作数据库
	public void test1() {
		// 1.获得连接对象
		Jedis jedis = new Jedis("192.168.177.128", 6379);
		// 2.获得数据
		String username = jedis.get("username");
		System.out.println(username);
		// 设置数据
		// jedis.set("username", "zhangsan");
		// jedis.set("addr", "沈阳");
		String addr = jedis.get("addr");
		System.out.println(addr);
	}

	@Test
	// 通过Jedis的pool获得jedis连接对象
	public void test2() {
		// 0.创建池子的配置对象
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		// 最大闲置个数
		poolConfig.setMaxIdle(30);
		// 最小闲置个数
		poolConfig.setMinIdle(10);
		// 最大连接数
		poolConfig.setMaxTotal(50);
		// 1.创建一个redis的连接池
		JedisPool pool = new JedisPool(poolConfig, "192.168.177.128");
		// 2.从连接中获取redis的连接资源
		Jedis jedis = pool.getResource();

		// 3.操作redis数据库
		String returnStr = jedis.set("xxx", "yyy");
		System.out.println("jedis设置值后的返回值为：" + returnStr);
		jedis.get("xxx");
		// 4.关闭资源，
		jedis.close();
		// 在实际开发中，本次用完不会关闭pool，因为其它程序还会用
		pool.close();
	}

}
