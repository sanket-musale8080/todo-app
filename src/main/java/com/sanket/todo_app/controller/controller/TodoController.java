package com.sanket.todo_app.controller;

import com.sanket.todo_app.dto.TodoRequestDTO;
import com.sanket.todo_app.dto.TodoResponseDTO;
import com.sanket.todo_app.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/todos")
public class TodoController {

    @Autowired
    private TodoService todoService;

    // ─── CREATE ────────────────────────────────────────────────
    @PostMapping
    public ResponseEntity<TodoResponseDTO> createTodo(
            @Valid @RequestBody TodoRequestDTO requestDTO) {
        TodoResponseDTO response = todoService.createTodo(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ─── GET ALL ───────────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<TodoResponseDTO>> getAllTodos() {
        List<TodoResponseDTO> todos = todoService.getAllTodos();
        return ResponseEntity.ok(todos);
    }

    // ─── GET BY ID ─────────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> getTodoById(@PathVariable Long id) {
        TodoResponseDTO todo = todoService.getTodoById(id);
        return ResponseEntity.ok(todo);
    }

    // ─── UPDATE ────────────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<TodoResponseDTO> updateTodo(
            @PathVariable Long id,
            @Valid @RequestBody TodoRequestDTO requestDTO) {
        TodoResponseDTO response = todoService.updateTodo(id, requestDTO);
        return ResponseEntity.ok(response);
    }

    // ─── MARK COMPLETE ─────────────────────────────────────────
    @PatchMapping("/{id}/complete")
    public ResponseEntity<TodoResponseDTO> markComplete(@PathVariable Long id) {
        TodoResponseDTO response = todoService.markComplete(id);
        return ResponseEntity.ok(response);
    }

    // ─── DELETE ────────────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok("Todo deleted successfully");
    }
}