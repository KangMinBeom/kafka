package com.example.kafka.producer.callback;

import static java.util.Objects.nonNull;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleProduceCallback implements Callback {
	public static SimpleProduceCallback newOne(){
		return new SimpleProduceCallback();
	}

	@Override
	public void onCompletion(RecordMetadata metadata, Exception exception){
		if (nonNull(metadata)){
			log.info("메세지 발행 성공!, topic: {}, partition: {}, offset: {}, timestamp: {}",
				metadata.topic(),
				metadata.partition(),
				metadata.offset(),
				metadata.timestamp());
		} else{
			log.error("메세지 발행 실패!", exception);
		}
	}
}
