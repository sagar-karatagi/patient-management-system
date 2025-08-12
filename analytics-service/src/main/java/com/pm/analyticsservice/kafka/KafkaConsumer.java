package com.pm.analyticsservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    /*
    What is this?
    -@groupId is used to identify the consumer group (Advanced topic).
    -@topic is the topic to listen to.
    -@message/@event is the message received from kafka.
    */
    @KafkaListener(topics = "patient-events",groupId = "patient-events")
    public void consumeMessage(byte[] event){
        /*
        try{
            PatientEvent event = PatientEvent.parseFrom(event);
            //Perform any business logic related to analytics-service here
            log.info("Event received from patient-events topic: {}",event);
        }catch (Exception e){
            log.error("Error parsing event in analytics-service",e);
        }
        */
    }
}
