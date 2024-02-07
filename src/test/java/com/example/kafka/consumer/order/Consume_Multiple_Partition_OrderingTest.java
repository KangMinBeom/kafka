package com.example.kafka.consumer.order;

import static com.example.kafka.support.helper.ConsumerRecordsHelper.*;
import static com.example.kafka.support.helper.KafkaConsumerTestHelper.*;
import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.time.Duration;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.kafka.support.annotation.TriplePartitionKafkaTest;
import com.example.kafka.support.helper.KafkaConsumerTestHelper;

@TriplePartitionKafkaTest(testDescriptions = "파티션이 3개인 카프카 브로커 테스트")
public class Consume_Multiple_Partition_OrderingTest {
	KafkaConsumer<String, String> consumer;

	@BeforeEach
	void setUp(){
		consumer = KafkaConsumerTestHelper.simpleConsumer();
	}

	@Test
	@DisplayName("partition이 여러개라면 순서대로 consume하지 않는다.")
	void name(){
		produce("topicA","a","b","c");
		produce("topicB","d","e","f");

		consumer.subscribe(List.of("topicA","topicB"));

		ConsumerRecords<String, String> actual = consumer.poll(Duration.ofSeconds(2));

		List<String> messages = messagesFrom(actual);

		assertThat(messages).isNotEqualTo(List.of("a","b","c","d","e","f"));

	}

}
