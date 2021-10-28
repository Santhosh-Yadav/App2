package in.santhosh.config;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms
public class JMSConfig {

	@Autowired
	private Environment env;

	@Bean
	public ConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
		connectionFactory.setBrokerURL(env.getProperty("spring.activemq.broker.url")); 
	    connectionFactory.setPassword(env.getProperty("spring.activemq.user"));
	    connectionFactory.setUserName(env.getProperty("spring.activemq.password"));	
	   // connectionFactory.setUseAsyncSend(true);
	//	connectionFactory.setTrustAllPackages(true);
		//PooledConnectionFactory pooledConnectionFactory = new PooledConnectionFactory(connectionFactory);
		//pooledConnectionFactory.setMaxConnections(Integer.parseInt(env.getProperty("spring.activemq.maxConnections")));
		//pooledConnectionFactory.setIdleTimeout(Integer.parseInt(env.getProperty("spring.activemq.idleTimeout")));
		return connectionFactory;
	}
	@Bean
	public JmsTemplate jmsTemplate() {
		JmsTemplate template = new JmsTemplate();
		template.setConnectionFactory(connectionFactory());
	//	template.setSessionAcknowledgeMode(Integer.parseInt(env.getProperty("spring.activemq.sessionAcknowledgeMode")));
	//	template.setDeliveryMode(Integer.parseInt(env.getProperty("spring.activemq.deliverymode")));
		return template;
	}

	/*
	 * @Bean public Prime360Request prime360Request() { return new
	 * Prime360Request(); }
	 * 
	 * @Bean public Prime360DGRequestData prime360DGRequestData() { return new
	 * Prime360DGRequestData(); }
	 * 
	 * @Bean public Prime360ACERequestData prime360ACERequestData() { return new
	 * Prime360ACERequestData(); }
	 * 
	 * @Bean public Prime360RequestInfo prime360RequestInfo() { return new
	 * Prime360RequestInfo(); }
	 */
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
	    DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
	    factory.setConnectionFactory(connectionFactory());
	 //   factory.setConcurrency(env.getProperty("spring.activemq.concurrent"));
	    return factory;
	}
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}