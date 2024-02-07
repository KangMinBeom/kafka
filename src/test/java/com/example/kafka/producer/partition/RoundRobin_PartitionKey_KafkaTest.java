package com.example.kafka.producer.partition;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.kafka.producer.callback.SimpleProduceCallback;
import com.example.kafka.support.annotation.KafkaTest;
import com.example.kafka.support.helper.KafkaProducerTestHelper;

@KafkaTest(testDescriptions = "partition의 개수는 2개")
public class RoundRobin_PartitionKey_KafkaTest {
	KafkaProducer<String, String> producer;

	@BeforeEach
	void setUp(){
		producer = KafkaProducerTestHelper.getSimpleProducer(1);
	}

	@Test
	@DisplayName("partition key를 입력하면 특정한 partition에 들어간다.")
	void name(){
		for(int i=0; i<6; i++){
			ProducerRecord<String, String> message =new ProducerRecord<>("topic", "hi" + i);

			producer.send(message, SimpleProduceCallback.newOne());
		}

		producer.close();
	}
}
