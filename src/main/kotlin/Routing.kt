package com.example

import com.example.dto.CreateTaskRequest
import com.example.dto.UpdateTaskRequest
import com.example.repository.InMemoryTaskRepository
import com.example.repository.TaskRepository
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.plugins.swagger.*

import com.example.service.TaskService
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive

fun Application.configureRouting() {
    val repository = InMemoryTaskRepository()
    val service = TaskService(repository)

    routing {
        swaggerUI(
            path = "swagger",
            swaggerFile = "openapi.yaml"
        )
        get("/tasks") {
            val tasks = service.getAll()
            call.respond(tasks)
        }
        get("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null || id <= 0) {
                call.respond(HttpStatusCode.BadRequest, "Некорректный id задачи: $id")

                return@get
            }

            val taskById = service.getById(id)

            if (taskById == null) {
                call.respond(HttpStatusCode.NotFound, "Задача не найдена")

                return@get
            }

            call.respond(HttpStatusCode.OK, taskById)
        }
        post("/tasks") {
            val request = call.receive<CreateTaskRequest>()

            if (request.title.isBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Название задачи не может быть пустым")
                return@post
            }

            val task = service.create(request.title)

            call.respond(HttpStatusCode.Created, task)
        }
        patch("tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null || id <= 0) {
                call.respond(HttpStatusCode.BadRequest, "Некорректный id задачи: $id")

                return@patch
            }

            val request = call.receive<UpdateTaskRequest>()

            if (request.title !== null && request.title.isBlank()) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    "Название задачи не может быть пустым"
                )
                return@patch
            }

            val updatedTask = service.update(
                id,
                title = request.title,
                completed = request.completed
            )

            if (updatedTask == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Задача не найдена"
                )
                return@patch
            }

            call.respond(HttpStatusCode.OK, updatedTask)
        }
        delete("/tasks/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null || id <= 0) {
                call.respond(HttpStatusCode.BadRequest, "Некорректный id задачи: $id")

                return@delete
            }

            val deletedTask = service.delete(id)

            if (!deletedTask) {
                call.respond(
                    HttpStatusCode.NotFound,
                    "Задача не найдена"
                )
                return@delete
            }

            call.respond(HttpStatusCode.NoContent)
        }
    }
}