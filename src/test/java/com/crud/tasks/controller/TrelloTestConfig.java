package com.crud.tasks.controller;

import com.crud.tasks.trello.facade.TrelloFacade;
import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class TrelloTestConfig {
    @Bean
    public TrelloFacade trelloFacade() {
        return Mockito.mock(TrelloFacade.class);
    }
}
