package com.kjt.PulseRoom.common.redis.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.connection.stream.MapRecord
import org.springframework.data.redis.connection.stream.StreamOffset
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.StringRedisSerializer
import org.springframework.data.redis.stream.StreamListener
import org.springframework.data.redis.stream.StreamMessageListenerContainer


@Configuration
class RedisConfig {

    @Value("\${spring.redis.host}")
    private lateinit var  host: String

    @Value("\${spring.redis.port}")
    private val port : Int = 0

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {
        val template = RedisTemplate<String, Any>()
        template.connectionFactory = redisConnectionFactory()
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()
        template.hashKeySerializer = StringRedisSerializer()
        template.hashValueSerializer = StringRedisSerializer()
        return template
    }

    @Bean
    fun streamMessageListenerContainer(
        //chatStreamListener 빈을 주입.
        @Qualifier("chatStreamListener")
        redisStreamListener: StreamListener<String,MapRecord<String,String,String>>
    ) : StreamMessageListenerContainer<String, MapRecord<String, String, String>>? {
        val option : StreamMessageListenerContainer.StreamMessageListenerContainerOptions<String, MapRecord<String, String, String>>?
        =  StreamMessageListenerContainer.StreamMessageListenerContainerOptions.builder()
            .pollTimeout(java.time.Duration.ofMillis(100))
            .build()
        val container = StreamMessageListenerContainer.create(redisConnectionFactory(),option)
        container.receive(
            StreamOffset.latest("chat"),
            redisStreamListener
        )
        container.start()
        return container
    }


}