package com.example.repository

import com.example.model.Task

interface TaskRepository {

    fun getAll(): List<Task>

    fun create(title: String): Task

    fun getById(id: Int): Task?

    fun update(id: Int, title: String?, completed: Boolean?): Task?

    fun delete(id: Int): Boolean
}
