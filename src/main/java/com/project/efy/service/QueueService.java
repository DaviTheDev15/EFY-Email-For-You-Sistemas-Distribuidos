package com.project.efy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Service
public class QueueService {

    private static final String QUEUE_URL =
            "https://sqs.us-east-2.amazonaws.com/939244404419/efyemail";

    @Autowired
    private SqsClient sqsClient;

    public void sendMessage(String message) {

        SendMessageRequest request =
                SendMessageRequest.builder()
                        .queueUrl(QUEUE_URL)
                        .messageBody(message)
                        .build();

        sqsClient.sendMessage(request);
    }
}