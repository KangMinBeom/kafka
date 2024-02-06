package com.example.kafka.support.assertions;

import lombok.Value;

@Value(staticConstructor = "topic")
public class Topic {
	String value;
}