package com.todolist.todolist

import com.todolist.entity.Task
import com.todolist.repository.TaskRepository
import com.todolist.service.TaskService
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TodolistServiceTests {

    @MockK
    private lateinit var taskRepository: TaskRepository

    @InjectMockKs
    private lateinit var taskService: TaskService

    private val defaultData = listOf(Task(id = 1L, title = "test todo task title", description = "test todo task description"))

    @BeforeEach
    fun init() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test get todo-list all tasks`() {
        // arrange
        every { taskRepository.findAll() } returns defaultData
        val expected = defaultData

        // act
        val result = taskService.findAll()

        // assert
        result shouldBe expected
    }
}