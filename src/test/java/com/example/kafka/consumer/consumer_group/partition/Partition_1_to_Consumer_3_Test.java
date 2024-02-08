package com.example.kafka.consumer.consumer_group.partition;

import static com.example.kafka.support.helper.ConsumerRecordsHelper.*;
import static com.example.kafka.support.helper.KafkaConsumerTestHelper.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.KafkaConsumerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;

import com.example.kafka.support.annotation.SinglePartitionKafkaTest;
import com.example.kafka.support.helper.KafkaConsumerTestHelper;

@SinglePartitionKafkaTest(testDescriptions = "하나의 파티션에 3개의 컨슈머가 붙음")
public class Partition_1_to_Consumer_3_Test {
	ExecutorService executorService = Executors.newFixedThreadPool(2);

	@AfterEach
	void tearDown() throws InterruptedException{
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
	}

	@Test
	@DisplayName("파티션-1 컨슈머-3이라면 오로지 하나의 consumer만 consume한다")
	void name(){
		produce("topiac-A","a","b","c","d","e","f");

		KafkaConsumer<String, String> consumer1 = KafkaConsumerTestHelper.simpleConsumer();
		KafkaConsumer<String, String> consumer2 = KafkaConsumerTestHelper.simpleConsumer();
		KafkaConsumer<String, String> consumer3 = KafkaConsumerTestHelper.simpleConsumer();


		consumer1.subscribe(List.of("topic-A"));
		consumer2.subscribe(List.of("topic-A"));
		consumer3.subscribe(List.of("topic-A"));

		pollAndPrint(consumer1, "consumer1");
		pollAndPrint(consumer2, "consumer2");
		pollAndPrint(consumer3, "consumer3");

	}

	private void pollAndPrint(KafkaConsumer<String, String> consumer, String consumerName){
		executorService.submit(() -> {
			List<ConsumerRecord<String, String>> records = recordListFrom(consumer.poll(Duration.ofSeconds(2)));
			records.forEach(it ->
				System.out.printf("consumer[%s] partition:[%s], offset:[%s], value:[%s]\n", consumerName, it.partition(), it.offset(), it.value()));
		});

	}
}
