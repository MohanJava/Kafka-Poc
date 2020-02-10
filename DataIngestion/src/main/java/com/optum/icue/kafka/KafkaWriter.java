package com.optum.icue.kafka;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;


public class KafkaWriter {

	private KafkaProducer<String, String> producer;
	private String KAFKA_TOPIC_TO_PRODUCE;
	//private String KAFKA_KEYSTORE_LOCATION;
	//private String KAFKA_KEYSTORE_PASSWORD;
	//private String KAFKA_TRUSTSTORE_LOCATION;
	//private String KAFKA_TRUSTSTORE_PASSWORD;
	//private String KAFKA_KEY_PASSWORD;

	public KafkaWriter() {
		try {
			final String BOOTSTRAP_SERVERS = "localhost:9092"; // ExternalizedConfigsReader.getPropertyValueFromCache("/kafka.broker.list");
			KAFKA_TOPIC_TO_PRODUCE = "mohan2";// ExternalizedConfigsReader.getPropertyValueFromCache("/kafka.produce.topic");
			
			//KAFKA_KEYSTORE_LOCATION = "src/main/resources/kafka/icue-alpha/icue-alpha.keystore.jks";
			//KAFKA_KEYSTORE_PASSWORD = "l5fRlSiD6ytRtf6gJK++CQ";
			//KAFKA_TRUSTSTORE_LOCATION = "src/main/resources/kafka/icue-alpha/kaas-truststore.jks";
			//KAFKA_TRUSTSTORE_PASSWORD = "wYVores5brZWLfwO/VlcqQ";
			//KAFKA_KEY_PASSWORD = "l5fRlSiD6ytRtf6gJK++CQ";

			Properties props = new Properties();
			props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
			props.put("acks", "all");
			props.put("enable.idempotence", "true"); // new
			props.put("retries", 5);
			props.put("batch.size", 32768);
			// Adding this to increaze the file size limit to send large file
			props.put("max.request.size", 5000000);
			props.put("linger.ms", 2); // changed from 1 to 2
			props.put("buffer.memory", 33554432);
			props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

			producer = new KafkaProducer<>(props);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Future<RecordMetadata> write(String message) {
		try {
			ProducerRecord<String, String> precord = new ProducerRecord<>(KAFKA_TOPIC_TO_PRODUCE,
					UUID.randomUUID().toString().replaceAll("-", ""), message);
			Future<RecordMetadata> returnFuture = producer.send(precord, new KafkaCallBack() {
			});
			return returnFuture;
		} catch (Exception e) {
			throw e;
		}
	}

	public void close() {
		producer.close();
	}

	/**
	 * Check all the messages are written into Kafka or wait till it completes.
	 * 
	 * @param kfrmdList
	 * @return
	 */
	public boolean isAllCompleted(List<Future<RecordMetadata>> kfrmdList) {
		Set<String> completedList = new HashSet<String>();
		int totalRecords = kfrmdList.size();

		while (completedList.size() != totalRecords) {
			List<Future<RecordMetadata>> tempList = new ArrayList<Future<RecordMetadata>>();
			for (Future<RecordMetadata> fkrmd : kfrmdList) {
				if (!fkrmd.isDone()) {
					tempList.add(fkrmd);
				} else {
					RecordMetadata rmd;
					try {
						rmd = fkrmd.get();
						completedList.add(rmd.partition() + "-" + rmd.offset());
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
					}

				}
			}
			if (!tempList.isEmpty()) {
				try {
					// wait for 100 mills to get the status.
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			kfrmdList = tempList;
		}
		return true;
	}

}

class KafkaCallBack implements Callback {
	Exception e;
	boolean exception = false;

	public boolean isException() {
		return exception;
	}

	@Override
	public void onCompletion(RecordMetadata recordMetadata, Exception excep) {
		// TODO Auto-generated method stub
		if (e != null) {
			e.printStackTrace();
			e = excep;
			exception = true;
		} else {
			//System.out.println("The offset of the record we just sent is: " + recordMetadata.offset());
		}

	}

}
