package org.theShire.domain.Messenger;

import org.theShire.domain.BaseEntity;

import java.time.Instant;

public class Message extends BaseEntity {
    public Message() {
        super(Instant.now());
    }
}
