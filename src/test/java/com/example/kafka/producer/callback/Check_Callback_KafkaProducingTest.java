package com.example.kafka.producer.callback;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.kafka.support.annotation.KafkaTest;
import com.example.kafka.support.helper.KafkaProducerTestHelper;

@KafkaTest
public class Check_Callback_KafkaProducingTest {
	KafkaProducer<String, String> producer;

	@BeforeEach
	void setUp(){
		producer = KafkaProducerTestHelper.getSimpleProducer();
	}

	@Test
	@DisplayName("카프카 메세지 발행 후 callback 실행")
	void name(){
		ProducerRecord<String, String> message = new ProducerRecord<>("topic1", "hello");

		producer.send(message, SimpleProduceCallback.newOne());
	}
}
