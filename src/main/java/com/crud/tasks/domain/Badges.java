package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Badges {

    @JsonProperty("votes")
    private int votes;

    @JsonProperty("attachmentsByType")
    private AttachmentsByType attachmentsByType;

    public Badges() {}

    public Badges(int votes, AttachmentsByType attachmentsByType) {
        this.votes = votes;
        this.attachmentsByType = attachmentsByType;
    }

    public int getVotes() {
        return votes;
    }

    public AttachmentsByType getAttachmentsByType() {
        return attachmentsByType;
    }
}

