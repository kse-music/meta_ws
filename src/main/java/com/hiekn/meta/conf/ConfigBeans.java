package com.hiekn.meta.conf;

import com.google.common.collect.Lists;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

@Configuration("meta")  
public class ConfigBeans {  
	
	@Value("${es_name}")  
	private String es_name;
	@Value("#{'${es_host}'.split(',')}")
	private List<String> es_host;

	@Value("#{'${mongo_host}'.split(',')}")
	private List<String> mongo_host;

    
	@Value("${redis_ip}")
	private String redis_ip;
	@Value("${redis_port}")
	private Integer redis_port;
	@Value("${redis_max_total}")
	private Integer max_toal;
	@Value("${redis_min_idle}")
	private Integer min_idle;
	@Value("${redis_max_idle}")
	private Integer max_idle;
	@Value("${redis_test_on_borrow}")
	private Boolean test_on_borrow;
	@Value("${redis_max_wait_millis}")
	private Integer max_wait_millis;
	@Value("${redis_timeout}")
	private Integer timeout;
	
    @Bean
    public TransportClient esClient(){
        Settings settings = Settings.builder().put("cluster.name", es_name).build();
        TransportClient transportClient = new PreBuiltTransportClient(settings);
        try {
            for (String host : es_host) {
                String[] ipPort = host.split(":");
                transportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ipPort[0]), Integer.valueOf(ipPort[1])));
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return transportClient;
    } 
    
    @Bean
    public MongoClient mongoClient(){
    	MongoClientOptions options = MongoClientOptions.builder().connectTimeout(100000).maxWaitTime(1000000).build();
    	List<ServerAddress> seeds = Lists.newArrayList(); 
    	for (String host : mongo_host) {
            String[] ipPort = host.split(":");
            seeds.add(new ServerAddress(ipPort[0], Integer.valueOf(ipPort[1])));
		}
    	return new MongoClient(seeds, options);
    }  

    @Bean
    public JedisPool jedisPool(){
		JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(max_toal);
        config.setMinIdle(min_idle);
        config.setMaxIdle(max_idle);
        config.setMaxWaitMillis(max_wait_millis);
        config.setTestOnBorrow(test_on_borrow);
        return new JedisPool(config, redis_ip,redis_port,timeout);
	}
  
}  
