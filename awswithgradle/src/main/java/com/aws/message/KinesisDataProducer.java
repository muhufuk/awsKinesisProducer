package com.aws.message;

import com.amazonaws.services.kinesis.producer.KinesisProducer;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;

import com.amazonaws.services.kinesis.producer.UserRecordResult;
import com.aws.util.Json;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.gson.JsonObject;

public class KinesisDataProducer implements DataProducer
{
    private String streamName;
    private KinesisProducer kinesisProducer;


    public KinesisDataProducer(String streamName, KinesisProducer kinesisProducer)
    {
        this.streamName = streamName;
        System.out.println("Stream name is {}" + streamName);
        this.kinesisProducer = kinesisProducer;
    }

    @Override
    public void sendData(String id, String name, String surname)
    {
        JsonObject metaJson = new JsonObject();
        metaJson.addProperty("id", id);
        metaJson.addProperty("name", name);
        metaJson.addProperty("surname", surname);
        JsonObject message = Json.of()
                .prop("meta", metaJson)
                .prop("payload", metaJson)
                .build();


        ByteBuffer data = ByteBuffer.wrap(message.toString().getBytes(StandardCharsets.UTF_8));
        ListenableFuture<UserRecordResult> userRecordResultListenableFuture = kinesisProducer.addUserRecord(streamName,  composePartitionKey("ms13", "assetId") ,data);
        try
        {
            System.out.println("Data Sending Result "+userRecordResultListenableFuture.get().getShardId());
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            System.out.println("Data Sending Result fail inter");
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            System.out.println("Data Sending Result fail inter");
        }
    }

    private String composePartitionKey(final String tenantId, final String assetId) {
        return tenantId + "." + assetId;
    }
}
