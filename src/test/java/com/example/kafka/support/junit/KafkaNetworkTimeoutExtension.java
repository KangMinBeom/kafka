package com.example.kafka.support.junit;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class KafkaNetworkTimeoutExtension implements TestExecutionExceptionHandler {
	@Override
	public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
		if (throwable instanceof TimeoutException) {
			return;
		}
		throw throwable;
	}
}