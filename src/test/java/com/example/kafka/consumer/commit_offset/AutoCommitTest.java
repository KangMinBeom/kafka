package com.example.kafka.consumer.commit_offset;

import static com.example.kafka.support.helper.ConsumerRecordsHelper.*;
import static com.example.kafka.support.helper.KafkaConsumerTestHelper.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AutoCommitTest {

	KafkaConsumer<String, String> consumer;

	@BeforeEach
	void setUp(){
		consumer = consumerOf(Map.of(
			"max.poll.records","2",
			"enable.auto.commit","true"
		));
		consumer.subscribe(List.of("topic"));
	}

	@Test
	@DisplayName("auto commit 모드이기때문에 poll()이 호출될 때 마다 commit")
	void name(){
		produce("topic","a","b","c","d");

		List<String> first = messagesFrom(consumer.poll(Duration.ofSeconds(2)));
		assertThat(first).isEqualTo(List.of("a","b"));

		List<String> second = messagesFrom(consumer.poll(Duration.ofSeconds(2)));
		assertThat(second).isEqualTo(List.of("c","d"));

	}

}
