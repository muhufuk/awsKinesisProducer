package com.aws.config;

import com.amazonaws.services.kinesis.producer.KinesisProducer;
import com.amazonaws.services.kinesis.producer.KinesisProducerConfiguration;
import com.aws.message.DataProducer;
import com.aws.message.KinesisDataProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataProducerConfig
{
    @Value("${my.stream.name}")
    private String streamName;

    @Bean
    public DataProducer dataProducer() {
        return new KinesisDataProducer(streamName, kinesisProducer());
    }

    private KinesisProducer kinesisProducer() {
        KinesisProducerConfiguration config = new KinesisProducerConfiguration()
                .setRegion("eu-central-1");

        return new KinesisProducer(config);
    }
}
