package com.optum.data.pipeline.impl;

import java.util.HashMap;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import com.optum.icue.kafka.KafkaConsumer1;

public class ConsumerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		dataConsume();

	}

	private static void dataConsume() {
		Consumer<String, String> consumer = KafkaConsumer1.createConsumer();
		int noMessageToFetch = 0;
		HashMap<String, String> values = new HashMap<>();
		//consumer.seekToEnd(consumer.assignment());
		while (true) {
			final ConsumerRecords<String, String> consumerRecords = consumer.poll(1000);
			if (consumerRecords.count() == 0) {
				noMessageToFetch++;
				if (noMessageToFetch > 100)
					break;
				else
					continue;
			}
			
			consumerRecords.forEach(record -> {
				System.out.println("Record value " + record.value());
				System.out.println("Record offset " + record.offset());
				//JSONObject jsonObject = readAsJson(record.value());
				JSONArray arr = readAsJsonArray(record.value());
				System.out.println(" -------------- >>>>>>>> "+values.keySet().size() );
				//System.out.println("Record value " + record.value());
				//System.out.println("Record partition " + record.partition());
				//System.out.println("Record offset " + record.offset());
				
				}
			);
			
			
			consumer.commitAsync();
		}
		consumer.close();
	}
	
	static JSONArray readAsJsonArray(String message) {
		JSONArray jsonObject = null;
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(message);
			jsonObject = (JSONArray) obj;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
