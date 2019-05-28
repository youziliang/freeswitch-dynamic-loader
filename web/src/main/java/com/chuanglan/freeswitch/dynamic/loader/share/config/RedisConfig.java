package com.chuanglan.freeswitch.dynamic.loader.share.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;


//TODO @Configuration
public class RedisConfig {

    @Value("${spring.redis.cluster.nodes}")
    private String nodes;

    @Value("${spring.redis.commandTimeout}")
    private String commandTimeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.maxTotal}")
    private String maxTotal;

    @Value("${spring.redis.maxIdle}")
    private String maxIdle;

    @Value("${spring.redis.minIdle}")
    private String minIdle;

    @Value("${spring.redis.maxWaitMillis}")
    private String maxWaitMillis;

    @Value("${spring.redis.minEvictableIdleTimeMillis}")
    private String minEvictableIdleTimeMillis;

    @Value("${spring.redis.numTestsPerEvictionRun}")
    private String numTestsPerEvictionRun;

    @Value("${spring.redis.softMinEvictableIdleTimeMillis}")
    private String softMinEvictableIdleTimeMillis;

    //TODO @Bean
    public JedisCluster getJedisCluster() {
        String[] serverArray = nodes.split(",");
        Set<HostAndPort> nodes = new HashSet<>();
        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }
        JedisPoolConfig config = getJedisPoolConfig();
        //参数依次是：集群地址、链接超时时间、返回值的超时时间、链接尝试次数、密码、配置文件
        return new JedisCluster(nodes, Integer.valueOf(commandTimeout), 10000, 3, password, config);
    }

    private JedisPoolConfig getJedisPoolConfig(){
        JedisPoolConfig config = new JedisPoolConfig();
        //连接耗尽时是否阻塞，false报异常，ture阻塞直到超时，默认true
        config.setBlockWhenExhausted(false);
        //在空闲时检查有效性，默认false
        config.setTestWhileIdle(false);
        //在获取连接的时候检查有效性，默认false
        config.setTestOnBorrow(false);
        //在将连接放回池中前自动检验连接是否有效
        config.setTestOnReturn(true);
        //是否启用pool的jmx管理功能，默认true
        config.setJmxEnabled(true);
        //是否启用后进先出，默认true
        config.setLifo(true);
        //最大连接数，默认8个
        config.setMaxTotal(Integer.valueOf(maxTotal));
        //最大空闲连接数，默认8个
        config.setMaxIdle(Integer.valueOf(maxIdle));
        //最小空闲连接数，默认0
        config.setMinIdle(Integer.valueOf(minIdle));
        //获取连接时的最大等待毫秒数。设置为阻塞时BlockWhenExhausted，超时抛异常；小于零时阻塞不确定时间，默认-1
        config.setMaxWaitMillis(Long.valueOf(maxWaitMillis));
        //逐出连接的最小空闲时间，默认1800000毫秒(30分钟)
        config.setMinEvictableIdleTimeMillis(Long.valueOf(minEvictableIdleTimeMillis));
        //每次逐出检查时逐出的最大数目，为负数就是 1/abs(n)，默认3
        config.setNumTestsPerEvictionRun(Integer.valueOf(numTestsPerEvictionRun));
        //对象空闲多久后逐出，当空闲时间大于该值且空闲连接大于最大空闲数时直接逐出，不再根据MinEvictableIdleTimeMillis判断（默认逐出策略）
        config.setSoftMinEvictableIdleTimeMillis(Long.valueOf(softMinEvictableIdleTimeMillis));
        //逐出扫描的时间间隔（毫秒），为负数则不运行逐出线程，默认-1
        config.setTimeBetweenEvictionRunsMillis(-1);
        //设置的逐出策略类名，默认DefaultEvictionPolicy（当连接超过最大空闲时间或连接数超过最大空闲连接数）
        config.setEvictionPolicyClassName("org.apache.commons.pool2.impl.DefaultEvictionPolicy");
        //默认为"pool"
        config.setJmxNamePrefix("pool");
        return config;
    }

}
