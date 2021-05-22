package com.hiekn.meta.conf;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Configuration("meta")  
public class ConfigBeans {  
	
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
    public RestHighLevelClient esClient(){
		return new RestHighLevelClient(builder(es_host.stream().map(h -> {
			String[] split = h.split(":");
			return new HttpHost(split[0],Integer.parseInt(split[1]));
		}).toArray(HttpHost[]::new)));
	}

	private RestClientBuilder builder(HttpHost... httpHosts) {
		RestClientBuilder builder = RestClient.builder(httpHosts);
		Header[] defaultHeaders = new Header[]{new BasicHeader("x", "y")};
		builder.setDefaultHeaders(defaultHeaders);
		builder.setRequestConfigCallback((RequestConfig.Builder requestConfigBuilder) -> requestConfigBuilder.setConnectTimeout(5000).setSocketTimeout(60000));
		final CredentialsProvider credentialsProvider =
				new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials("user", "password"));
		builder.setHttpClientConfigCallback((HttpAsyncClientBuilder httpAsyncClientBuilder) ->
				httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
//		builder.setNodeSelector((Iterable<Node> nodes) -> {
//			boolean foundOne = false;
//			for (Node node : nodes) {
//                String rackId = node.getAttributes().get("rack_id").get(0);
//                if ("rack_one".equals(rackId)) {
//                    foundOne = true;
//                    break;
//                }
//			}
//			if (foundOne) {
//				Iterator<Node> nodesIt = nodes.iterator();
//				while (nodesIt.hasNext()) {
//					Node node = nodesIt.next();
//					String rackId = node.getAttributes().get("rack_id").get(0);
//					if (!"rack_one".equals(rackId)) {
//						nodesIt.remove();
//					}
//				}
//			}
//		});
		return builder;
	}
    
    @Bean
    public MongoClient mongoClient(){
		return MongoClients.create(
				MongoClientSettings.builder()
						.applyToSocketSettings(builder -> builder.connectTimeout(5, TimeUnit.SECONDS))
						.applyToConnectionPoolSettings(builder -> builder.maxWaitTime(120, TimeUnit.SECONDS))
						.writeConcern(WriteConcern.UNACKNOWLEDGED)
//						.credential(MongoCredential.createCredential(username,"admin",password.toCharArray()))
						.applyToClusterSettings(builder -> builder.hosts(mongo_host.stream().map(ServerAddress::new).collect(Collectors.toList())))
						.build());
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
