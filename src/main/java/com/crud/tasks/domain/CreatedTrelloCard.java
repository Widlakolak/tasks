package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedTrelloCard {

    @JsonProperty("id")
    private String id;

    @JsonProperty("badges")
    private Badges badges;

    public CreatedTrelloCard() {}

    public CreatedTrelloCard(String id, Badges badges) {
        this.id = id;
        this.badges = badges;
    }

    public String getId() {
        return id;
    }

    public Badges getBadges() {
        return badges;
    }
}
