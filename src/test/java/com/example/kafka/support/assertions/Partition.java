package com.example.kafka.support.assertions;

import lombok.Value;

@Value(staticConstructor = "partition")
public class Partition {
	int value;
}