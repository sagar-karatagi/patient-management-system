package com.pm.patientservice.kafka;


import com.pm.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducer {

    private final KafkaTemplate<String,byte[]> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(Patient patient){

        //Create PatientEvent using proto builder
//        PatientEvent event = PatientEvent.new_builder().set.....

        //Use try catch to send message to kafka
        try{
            kafkaTemplate.send("patient-events",patient.getId().toString().getBytes( ));
        }catch (Exception e){
            log.error("Error sending message to kafka",e);
        }
    }
}
