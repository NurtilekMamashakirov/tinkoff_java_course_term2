package edu.java.services.kafka;

import edu.java.dto.request.LinkUpdate;

public interface QueueProducer {
    void send(LinkUpdate linkUpdate);
}
