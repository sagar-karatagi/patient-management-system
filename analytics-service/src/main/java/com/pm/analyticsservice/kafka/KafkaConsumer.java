package com.pm.analyticsservice.kafka;

import com.pm.patient.event.PatientEvent;
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

        try{
             PatientEvent patientEvent = PatientEvent.parseFrom(event);
             System.out.println("Received event from kafka: \n "+patientEvent.toString());
         }catch (Exception e){
             System.out.println("Error parsing event from kafka: " + e.getMessage());
         }

    }
}
