package com.sanket.todo_app.service;

import com.sanket.todo_app.dto.TodoRequestDTO;
import com.sanket.todo_app.dto.TodoResponseDTO;
import com.sanket.todo_app.entity.TodoList;
import com.sanket.todo_app.exception.TodoNotFoundException;
import com.sanket.todo_app.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    // ─── CREATE ────────────────────────────────────────────────
    public TodoResponseDTO createTodo(TodoRequestDTO requestDTO) {
        TodoList todo = new TodoList();
        todo.setName(requestDTO.getName());
        todo.setDescription(requestDTO.getDescription());
        TodoList savedTodo = todoRepository.save(todo);
        return convertToResponseDTO(savedTodo);
    }

    // ─── GET ALL ───────────────────────────────────────────────
    public List<TodoResponseDTO> getAllTodos() {
        List<TodoList> todos = todoRepository.findAll();
        return todos.stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // ─── GET BY ID ─────────────────────────────────────────────
    public TodoResponseDTO getTodoById(Long id) {
        TodoList todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        return convertToResponseDTO(todo);
    }

    // ─── UPDATE ────────────────────────────────────────────────
    public TodoResponseDTO updateTodo(Long id, TodoRequestDTO requestDTO) {
        TodoList todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todo.setName(requestDTO.getName());
        todo.setDescription(requestDTO.getDescription());
        TodoList updatedTodo = todoRepository.save(todo);
        return convertToResponseDTO(updatedTodo);
    }

    // ─── MARK COMPLETE ─────────────────────────────────────────
    public TodoResponseDTO markComplete(Long id) {
        TodoList todo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todo.setCompleted(true);
        return convertToResponseDTO(todoRepository.save(todo));
    }

    // ─── DELETE ────────────────────────────────────────────────
    public void deleteTodo(Long id) {
        if (!todoRepository.existsById(id)) {
            throw new TodoNotFoundException(id);
        }
        todoRepository.deleteById(id);
    }

    // ─── HELPER ────────────────────────────────────────────────
    private TodoResponseDTO convertToResponseDTO(TodoList todo) {
        TodoResponseDTO response = new TodoResponseDTO();
        response.setId(todo.getId());
        response.setName(todo.getName());
        response.setDescription(todo.getDescription());
        response.setCompleted(todo.isCompleted());
        response.setCreatedAt(todo.getCreatedAt());
        return response;
    }
}