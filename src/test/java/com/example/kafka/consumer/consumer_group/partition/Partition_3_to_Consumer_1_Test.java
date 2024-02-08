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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.kafka.support.annotation.TriplePartitionKafkaTest;
import com.example.kafka.support.helper.KafkaConsumerTestHelper;

@TriplePartitionKafkaTest()
public class Partition_3_to_Consumer_1_Test {
	ExecutorService executorService = Executors.newFixedThreadPool(2);

	@AfterEach
	void tearDown() throws InterruptedException{
		executorService.shutdown();
		executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
	}

	@Test
	@DisplayName("(파티션-3):(컨슈머-1) 이라면 해당 인스턴스가 모든 파티션을 점유한다.")
	void name(){
		produce("topic","a","b","c","d","e","f");

		KafkaConsumer<String, String> consumer = KafkaConsumerTestHelper.simpleConsumer();

		consumer.subscribe(List.of("topic"));

		executorService.submit(() -> {
			List<ConsumerRecord<String, String>> records = recordListFrom(consumer.poll(Duration.ofSeconds(2)));
			records.forEach(it ->
				System.out.printf("partition:[%s], offset:[%s], value:[%s]\n", it.partition(), it.offset(), it.value()));
		});
	}
}
