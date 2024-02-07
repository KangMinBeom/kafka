package com.example.kafka.consumer.consume;

import static com.example.kafka.support.assertions.KafkaAssertions.*;
import static com.example.kafka.support.helper.KafkaConsumerTestHelper.*;
import static kafka.tools.StateChangeLogMerger.*;

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
public class KafkaConsumerTest {
	KafkaConsumer<String, String> consumer;

	@BeforeEach
	void setUp(){
		consumer = KafkaConsumerTestHelper.simpleConsumer();
	}

	@Test
	@DisplayName("topic에 message를 발행하면 consume")
	void name(){
		produce("topic1","hello");

		consumer.subscribe(List.of("topic1"));

		ConsumerRecords<String, String> actual = consumer.poll(Duration.ofSeconds(2));

		assertConsumedThat(actual,Topic.topic("topic1")).isEqualTo("hello");

	}
}
