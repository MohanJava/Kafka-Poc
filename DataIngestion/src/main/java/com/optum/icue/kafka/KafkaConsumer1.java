package com.optum.icue.kafka;

import java.util.Collection;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;


public class KafkaConsumer1 {

	public static Consumer<String, String> createConsumer() {
		final Properties props = new Properties();
		
		props.put("bootstrap.servers", IKafkaConstants.BOOTSTRAP_SERVERS);
		props.put("acks", "all");
		props.put("enable.idempotence", "true"); // new
		props.put("retries", 5);
		props.put("batch.size", 32768);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, IKafkaConstants.GROUP_ID_CONFIG);
		// Adding this to increaze the file size limit to send large file
		props.put("max.request.size", 5000000);
		props.put("linger.ms", 2); // changed from 1 to 2
		props.put("buffer.memory", 33554432);
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		final Consumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe((Collection)Collections.singletonList(IKafkaConstants.TOPIC_NAME));
		
		return consumer;
	}
	
}
