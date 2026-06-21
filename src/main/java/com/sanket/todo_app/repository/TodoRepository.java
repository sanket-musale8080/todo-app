package com.sanket.todo_app.repository;

import com.sanket.todo_app.entity.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long> {

    // Find all todos that are completed or not completed
    List<TodoList> findByCompleted(boolean completed);

    // Find todos whose name contains a keyword (like a search)
    List<TodoList> findByNameContaining(String keyword);

}