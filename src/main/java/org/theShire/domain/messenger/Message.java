package org.theShire.domain.messenger;

import org.theShire.domain.media.Content;
import org.theShire.domain.BaseEntity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.theShire.domain.messenger.Message.Stage.*;

public class Message extends BaseEntity {//TODO assertions

    //The ID from the Sender
    private UUID senderId;
    //The stage the Message is in (SENT,ARRIVED,READ)
    private Stage stage;
    //The Content of the message (Text or Media)
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
