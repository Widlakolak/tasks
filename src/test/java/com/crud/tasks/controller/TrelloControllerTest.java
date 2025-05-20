package com.crud.tasks.controller;

import com.crud.tasks.domain.Badges;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TrelloControllerTest {

    @InjectMocks
    private TrelloController trelloController;

    @Mock
    private TrelloFacade trelloFacade;

    @Test
    void shouldFetchEmptyTrelloBoards() {
        // Given
        when(trelloFacade.fetchTrelloBoards()).thenReturn(List.of());

        // When
        var result = trelloController.getTrelloBoards();

        // Then
        assertThat(result.getBody()).isEmpty();
    }

    @Test
    void shouldCreateTrelloCard() {
        // Given
        TrelloCardDto cardDto = new TrelloCardDto("Test Card", "Card Description", "top", "1");
        Badges badges = new Badges();
        CreatedTrelloCardDto createdCardDto = new CreatedTrelloCardDto("1", "Test Card", badges, "http://test.com");

        when(trelloFacade.createCard(cardDto)).thenReturn(createdCardDto);

        // When
        ResponseEntity<CreatedTrelloCardDto> response = trelloController.createTrelloCard(cardDto);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getId()).isEqualTo("1");
        assertThat(response.getBody().getName()).isEqualTo("Test Card");
        assertThat(response.getBody().getShortUrl()).isEqualTo("http://test.com");
    }
}