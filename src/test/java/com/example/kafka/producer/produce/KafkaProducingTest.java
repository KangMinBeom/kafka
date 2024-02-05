package com.example.kafka.producer.produce;

import static org.assertj.core.api.Assertions.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.kafka.support.annotation.KafkaTest;
import com.example.kafka.support.helper.KafkaProducerTestHelper;

@KafkaTest
public class KafkaProducingTest {
	KafkaProducer<String, String> producer;

	@BeforeEach
	void setUp(){
		producer = KafkaProducerTestHelper.getSimpleProducer();
	}

	@Test
	@DisplayName("동기, 비동기 전송 메세지 보내고 future로 성공,실패 확인")
	void name() throws ExecutionException, InterruptedException{
		ProducerRecord<String, String> message = new ProducerRecord<>("topic1", "hello");

		Future<RecordMetadata> future = producer.send(message);

		RecordMetadata actual = future.get();

		assertThat(actual.topic()).isEqualTo("topic1");
	}
}
