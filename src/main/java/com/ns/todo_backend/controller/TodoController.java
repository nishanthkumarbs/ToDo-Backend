package com.ns.todo_backend.controller;

import org.springframework.web.bind.annotation.*;

import com.ns.todo_backend.entity.Todo;
import com.ns.todo_backend.repository.TodoRepository;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoRepository todoRepository;

    public TodoController(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @GetMapping("/user/{userId}")
    public List<Todo> getUserTodos(
            @PathVariable Long userId,
            HttpServletRequest request
    ) {

        String email = (String) request.getAttribute("email");

        if(email == null){
            throw new RuntimeException("Unauthorized");
        }

        return todoRepository.findByUserId(userId);
    }

    // CREATE todo
    @PostMapping
    public Todo createTodo(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }

    // UPDATE todo
    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {

        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Todo not found"));

        todo.setTitle(updatedTodo.getTitle());
        todo.setCompleted(updatedTodo.isCompleted());
        todo.setPriority(updatedTodo.getPriority());
        todo.setCategory(updatedTodo.getCategory());
        todo.setRepeat(updatedTodo.getRepeat());
        todo.setReminder(updatedTodo.getReminder());
        todo.setDueDate(updatedTodo.getDueDate());
        todo.setCompletedAt(updatedTodo.getCompletedAt());

        return todoRepository.save(todo);
    }

    // DELETE todo
    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        todoRepository.deleteById(id);
    }

}