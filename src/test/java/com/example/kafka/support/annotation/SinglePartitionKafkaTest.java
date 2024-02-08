package com.example.kafka.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest
@EmbeddedKafka(partitions = 1, brokerProperties = {
	"listeners=PLAINTEXT://localhost:9092",
	"port=9092",
})
public @interface SinglePartitionKafkaTest {
	String testDescriptions() default "";
}