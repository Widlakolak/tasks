package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TrelloMapperTest {
    @Autowired
    TrelloMapper trelloMapper;

    private TrelloListDto trelloListDto;
    private TrelloList trelloList;
    TrelloCardDto trelloCardDto;
    TrelloCard trelloCard;
    TrelloBoardDto trelloBoardDto;
    TrelloBoard trelloBoard;

    @BeforeEach
    void setUp() {
        trelloListDto = new TrelloListDto("1", "ToDo", false);
        trelloList = new TrelloList("1", "ToDo", false);

        trelloBoardDto = new TrelloBoardDto("1", "Board", Collections.singletonList(trelloListDto));
        trelloBoard = new TrelloBoard("1", "Board", Collections.singletonList(trelloList));

        trelloCardDto = new TrelloCardDto("Card name", "Card desc", "top", "1");
        trelloCard = new TrelloCard("Card name", "Card desc", "top", "1");
    }

    @Test
    void shouldMapToBoards() {
        //When
        List<TrelloBoard> boards = trelloMapper.mapToBoards(List.of(trelloBoardDto));

        //Then
        assertThat(boards).hasSize(1);
        assertThat(boards.get(0).getId()).isEqualTo("1");
        assertThat(boards.get(0).getName()).isEqualTo("Board");
        assertThat(boards.get(0).getLists()).hasSize(1);
    }

    @Test
    void shouldMapToBoardsDto() {
        //When
        List<TrelloBoardDto> boardDtos = trelloMapper.mapToBoardsDto(List.of(trelloBoard));

        //Then
        assertThat(boardDtos).hasSize(1);
        assertThat(boardDtos.get(0).getId()).isEqualTo("1");
        assertThat(boardDtos.get(0).getName()).isEqualTo("Board");
        assertThat(boardDtos.get(0).getLists()).hasSize(1);
    }

    @Test
    void shouldMapToList() {
        //When
        List<TrelloList> result = trelloMapper.mapToList(List.of(trelloListDto));

        //Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(0).getName()).isEqualTo("ToDo");
        assertThat(result.get(0).isClosed()).isFalse();
    }

    @Test
    void shouldMapToListDto() {
        //When
        List<TrelloListDto> result = trelloMapper.mapToListDto(List.of(trelloList));

        //Then
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("1");
        assertThat(result.get(0).getName()).isEqualTo("ToDo");
        assertThat(result.get(0).isClosed()).isFalse();
    }

    @Test
    void shouldMapToCard() {
        //When
        TrelloCard result = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertThat(result.getName()).isEqualTo("Card name");
        assertThat(result.getDescription()).isEqualTo("Card desc");
        assertThat(result.getPos()).isEqualTo("top");
        assertThat(result.getListId()).isEqualTo("1");
    }

    @Test
    void shouldMapToCardDto() {
        //When
        TrelloCardDto result = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertThat(result.getName()).isEqualTo("Card name");
        assertThat(result.getDescription()).isEqualTo("Card desc");
        assertThat(result.getPos()).isEqualTo("top");
        assertThat(result.getListId()).isEqualTo("1");
    }
}