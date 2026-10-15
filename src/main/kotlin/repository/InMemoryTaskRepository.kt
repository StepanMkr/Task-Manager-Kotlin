package com.example.repository

import com.example.model.Task

class InMemoryTaskRepository : TaskRepository {
    private val tasks = mutableListOf<Task>(
        Task(1, "Изучить Kotlin"),
        Task(2, "Разобраться с Ktor"),
        Task(3, "Создать REST API")
    )

    @Synchronized
    override fun getAll(): List<Task> {
        return tasks.toList()
    }

    @Synchronized
    override fun getById(id: Int): Task? {
        return tasks.find { it.id == id }
    }

    @Synchronized
    override fun create(title: String): Task {
        val id = (tasks.maxOfOrNull { it.id } ?: 0) + 1

        val task = Task(
            id = id,
            title = title
        )

        tasks.add(task)
        return task
    }

    @Synchronized
    override fun update(
        id: Int,
        title: String?,
        completed: Boolean?
    ): Task? {
        val index = tasks.indexOfFirst { it.id == id }

        if (index == -1) return null

        val oldTask = tasks[index]

        val updatedTask = oldTask.copy(
            title = title ?: oldTask.title,
            completed = completed ?: oldTask.completed
        )

        tasks[index] = updatedTask
        return updatedTask
    }

    @Synchronized
    override fun delete(id: Int): Boolean {
        return tasks.removeIf { it.id == id }
    }
}