package com.example.repository

import com.example.model.Task

object TaskRepository {
    private val tasks = mutableListOf<Task>(
        Task(1, "Изучить Kotlin"),
        Task(2, "Разобраться с Ktor"),
        Task(3, "Создать REST API")
    )

    @Synchronized
    fun getAll(): List<Task> = tasks.toList()

    @Synchronized
    fun create(title: String): Task {
        val id = (this.tasks.maxOfOrNull { it.id } ?: 0) + 1

        val task = Task(id, title)

        this.tasks.add(task)

        return task
    }

    @Synchronized
    fun getById(id: Int): Task? = tasks.find { it.id == id }

    @Synchronized
    fun update(id: Int, title: String?, completed: Boolean?): Task? {
        val index = tasks.indexOfFirst { it.id == id }

        if (index == -1) {
            return null;
        }

        val oldTask = tasks[index]

        val updatedTasks = oldTask.copy(
            title = title ?: oldTask.title,
            completed = completed ?: oldTask.completed
        )

        tasks[index] = updatedTasks

        return updatedTasks
    }

    @Synchronized
    fun delete(id: Int): Boolean {
        return tasks.removeIf { it.id == id }
    }
}
