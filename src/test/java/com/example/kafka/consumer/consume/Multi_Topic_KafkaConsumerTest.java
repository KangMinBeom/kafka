package com.example.kafka.consumer.consume;

import static com.example.kafka.support.assertions.KafkaAssertions.*;
import static com.example.kafka.support.helper.KafkaConsumerTestHelper.*;

import java.time.Duration;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.kafka.support.annotation.KafkaTest;
import com.example.kafka.support.assertions.Topic;
import com.example.kafka.support.helper.KafkaConsumerTestHelper;

@KafkaTest
public class Multi_Topic_KafkaConsumerTest {
	KafkaConsumer<String, String>consumer;

	@BeforeEach
	void setUp(){
		consumer = KafkaConsumerTestHelper.simpleConsumer();
	}

	@Test
	@DisplayName("kafka 의 consumer 는 두 개 이상의 토픽을 consume 가능")
	void name() {
		produce("topic1", "1번째 토픽입니다.");
		produce("topic2", "2번째 토픽입니다.");

		consumer.subscribe(List.of("topic1", "topic2"));

		ConsumerRecords<String, String> actual = consumer.poll(Duration.ofSeconds(10));

		assertConsumedThat(actual, Topic.topic("topic1")).isEqualTo("1번째 토픽입니다.");
		assertConsumedThat(actual, Topic.topic("topic2")).isEqualTo("2번째 토픽입니다.");
	}
}
