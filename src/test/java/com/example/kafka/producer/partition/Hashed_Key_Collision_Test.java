package com.example.kafka.producer.partition;

import static org.assertj.core.api.Assertions.*;

import org.apache.kafka.common.utils.Utils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class Hashed_Key_Collision_Test {

	@Test
	@DisplayName("파티션 수가 적으면 해시 충돌이 일어날 확률이 높음")
	void name(){
		int key1 = Utils.murmur2("key-1".getBytes()) % 2;
		int key2 = Utils.murmur2("key-2".getBytes()) % 2;

		assertThat(key1).isNotEqualTo(key2);
	}
}
