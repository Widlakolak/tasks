package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AttachmentsByType {

    @JsonProperty("trello")
    private Trello trello;

    public AttachmentsByType() {}

    public AttachmentsByType(Trello trello) {
        this.trello = trello;
    }

    public Trello getTrello() {
        return trello;
    }
}