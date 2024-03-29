package com.example.kafka.support.helper;

import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;

public class KafkaProducerTestHelper {
	public static KafkaProducer<String, String> getSimpleProducer() {
		Map<String, Object> props = Map.of(
			// bootstrap server 설정
			"bootstrap.servers", "localhost:9092",
			// kafka 로 전송할 message 의 s/d 클래스 설정
			"key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
			"value.serializer", "org.apache.kafka.common.serialization.StringSerializer"
		);
		return new KafkaProducer<>(props);
	}

	public static KafkaProducer<String, String> getSimpleProducer(int ack) {
		Map<String, Object> props = Map.of(
			"bootstrap.servers", "localhost:9092",
			"key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
			"value.serializer", "org.apache.kafka.common.serialization.StringSerializer",
			"acks", String.valueOf(ack)
		);
		return new KafkaProducer<>(props);
	}
}
