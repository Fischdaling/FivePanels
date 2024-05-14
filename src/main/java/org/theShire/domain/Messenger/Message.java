package org.theShire.domain.Messenger;

import org.theShire.domain.Media.Content;
import org.theShire.domain.BaseEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.theShire.domain.Messenger.Message.Stage.*;

public class Message extends BaseEntity {

    private UUID senderId;
    private Stage stage;
    private List<Content> contents;

    public Message() {
        super(Instant.now());
    }

    public Message(Instant createdAt, UUID senderId, List<Content> contents) {
        super(createdAt);
        this.senderId = senderId;
        this.stage = SENT;
        this.contents = contents;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public void addContent(Content content){
        //TODO ASSERTIONS
        this.contents.add(content);
    }

    enum Stage{
        SENT,
        ARRIVED,
        READ
    }
}
