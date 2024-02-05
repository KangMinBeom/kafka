package com.example.kafka.producer.produce;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import com.example.kafka.support.helper.KafkaProducerTestHelper;
import com.example.kafka.support.junit.KafkaNetworkTimeoutExtension;

@ExtendWith(KafkaNetworkTimeoutExtension.class)
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {
	"listeners=PLAINTEXT://localhost:9092",
	"prot=9092",
	"auto.create.topics.enable=false"
})
public class Disable_TopicAutoCreationOption_KafkaProducingTest {

	KafkaProducer<String, String> producer;

	@BeforeEach
	void setup(){
		producer = KafkaProducerTestHelper.getSimpleProducer();
	}

	@Test
	@DisplayName("토픽 생성이 자동으로 생성되지 않아 예외 발생")
	@Timeout(value = 2)
	void name(){
		ProducerRecord<String, String> message = new ProducerRecord<>("topic","hello");

		producer.send(message);
	}
}
