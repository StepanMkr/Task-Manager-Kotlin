package com.example.service

import com.example.model.Task
import com.example.repository.TaskRepository

class TaskService(
    private val repository: TaskRepository
) {
    fun getAll(): List<Task> {
        return repository.getAll()
    }

    fun getById(id: Int): Task? {
        return repository.getById(id)
    }

    fun create(title: String): Task {
        require(title.isNotBlank()) {
            "Название задачи не может быть пустым"
        }

        return repository.create(title)
    }

    fun update(
        id: Int,
        title: String?,
        completed: Boolean?
    ): Task? {
        if (title != null) {
            require(title.isNotBlank()) {
                "Название задачи не может быть пустым"
            }
        }

        return repository.update(id, title, completed)
    }

    fun delete(id: Int): Boolean {
        return repository.delete(id)
    }
}