package com.example.kafka.operate;

import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {
	"listeners=PLAINTEXT://localhost:9092",
})
public class KafkaOperateTest {

	private static final Map<String, Object> props = Map.of(
		"bootstrap.servers", "localhost:9092",
		"key.serializer", "org.apache.kafka.common.serialization.StringSerializer",
		"value.serializer", "org.apache.kafka.common.serialization.StringSerializer"
	);

	KafkaProducer<String, String> producer = new KafkaProducer<>(props);

	@Test
	void sample() {
		// message produce to broker
		ProducerRecord<String, String> record = new ProducerRecord<>("a-topic", "hi~");

		producer.send(record);
	}
}
