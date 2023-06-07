package com.example.Mile2;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

public class kafkaListeners {
    @Component
    public class KafkaListeners {

        @KafkaListener(
                topics = "wallet",
                groupId = "groupid"
        )
        void listener(String data) {
            System.out.println("Listener received: " + data);
        }
    }
}
