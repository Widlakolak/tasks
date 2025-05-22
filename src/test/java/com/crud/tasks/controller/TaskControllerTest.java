package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.service.DbService;
import com.crud.tasks.controller.TaskNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private DbService dbService;

    @Mock
    private TaskMapper taskMapper;

    @Test
    void shouldFetchAllTasks() {
        // Given
        List<Task> tasks = List.of(new Task(1L, "Test title", "Test content"));
        List<TaskDto> taskDtos = List.of(new TaskDto(1L, "Test title", "Test content"));

        when(dbService.getAllTasks()).thenReturn(tasks);
        when(taskMapper.mapToTaskDtoList(tasks)).thenReturn(taskDtos);

        // When
        ResponseEntity<List<TaskDto>> response = taskController.getTasks();

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0).getTitle()).isEqualTo("Test title");
    }

    @Test
    void shouldFetchSingleTask() throws TaskNotFoundException {
        // Given
        Task task = new Task(1L, "Single", "Content");
        TaskDto taskDto = new TaskDto(1L, "Single", "Content");

        when(dbService.getTask(1L)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When
        ResponseEntity<TaskDto> response = taskController.getTask(1L);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getId()).isEqualTo(1L);
        assertThat(response.getBody().getTitle()).isEqualTo("Single");
    }

    @Test
    void shouldDeleteTask() throws TaskNotFoundException {
        // When
        ResponseEntity<Void> response = taskController.deleteTask(1L);

        // Then
        verify(dbService, times(1)).deleteTask(1L);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void shouldUpdateTask() {
        // Given
        TaskDto taskDto = new TaskDto(1L, "Updated", "Updated content");
        Task task = new Task(1L, "Updated", "Updated content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);
        when(dbService.saveTask(task)).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);

        // When
        ResponseEntity<TaskDto> response = taskController.updateTask(taskDto);

        // Then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(response.getBody().getTitle()).isEqualTo("Updated");
    }

    @Test
    void shouldCreateTask() {
        // Given
        TaskDto taskDto = new TaskDto(null, "New", "Content");
        Task task = new Task(null, "New", "Content");

        when(taskMapper.mapToTask(taskDto)).thenReturn(task);

        // When
        ResponseEntity<Void> response = taskController.createTask(taskDto);

        // Then
        verify(dbService, times(1)).saveTask(task);
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    void shouldThrowWhenTaskNotFound() throws TaskNotFoundException {
        // Given
        when(dbService.getTask(42L)).thenThrow(new TaskNotFoundException());

        // When / Then
        assertThatThrownBy(() -> taskController.getTask(42L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage(null);
    }
}
