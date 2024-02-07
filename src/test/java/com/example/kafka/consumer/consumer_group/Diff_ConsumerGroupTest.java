package com.example.kafka.consumer.consumer_group;

import static com.example.kafka.support.helper.ConsumerRecordsHelper.*;
import static com.example.kafka.support.helper.KafkaConsumerTestHelper.*;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.kafka.support.annotation.TriplePartitionKafkaTest;

@TriplePartitionKafkaTest(testDescriptions = "컨슈머 그룹 테스트를 위해 파티션을 3개로 지정")
public class Diff_ConsumerGroupTest {
	ExecutorService threads = Executors.newFixedThreadPool(2);

	@AfterEach
	void tearDown() throws InterruptedException{
		threads.shutdown();
		threads.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
	}

	@Test
	@DisplayName("하나의 컨슈머 그룹에 포함된 2개의 물리적 컨슈머")
	void name(){
		produce("topic","a","b","c","d","e","f");

		KafkaConsumer<String, String> sut_consumer1 = simpleConsumer("fancy-consumer-group");
		KafkaConsumer<String, String> sut_consumer2 = simpleConsumer("not-fancy-consumer-group");
		sut_consumer1.subscribe(List.of("topic"));
		sut_consumer2.subscribe(List.of("topic"));

		threads.submit(() ->{
			List<ConsumerRecord<String, String>> records = recordListFrom(sut_consumer1.poll(Duration.ofSeconds(2)));
			records.forEach(it ->
				System.out.printf("consumer[%s] partition:[%s], offset:[%s], value:[%s]\n", "consumer1", it.partition(), it.offset(), it.value()));
		});

		threads.submit(() ->{
			List<ConsumerRecord<String, String>> records = recordListFrom(sut_consumer2.poll(Duration.ofSeconds(2)));
			records.forEach(it ->
				System.out.printf("consumer[%s] partition:[%s], offset:[%s], value:[%s]\n", "consumer1", it.partition(), it.offset(), it.value()));
		});
	}
}
