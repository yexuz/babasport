package com.zlj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import redis.clients.jedis.Jedis;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class RedisTest {

	@Autowired
	private Jedis jedis;

	@Test
	public void springRedisTest() {

		jedis.set("ooo", "aaa");
	}

	@Test
	public void redisTest() {
		Jedis jedis = new Jedis("192.168.200.128", 6379);
		Long pno = jedis.incr("pno");
		System.err.println(pno);
		jedis.close();
	}
}
