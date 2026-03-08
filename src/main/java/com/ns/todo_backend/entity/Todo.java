package com.ns.todo_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private boolean completed;

    private String priority;

    private String category;

    private String repeat;

    private String reminder;

    private String dueDate;

    private String createdAt;

    private String completedAt;

    private Long userId;

}