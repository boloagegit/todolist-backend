package com.todolist.todolist

import com.ninjasquad.springmockk.MockkBean
import com.todolist.entity.Task
import com.todolist.service.TaskService
import io.mockk.every
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class TodolistControllerTests(@Autowired val mockMvc: MockMvc) {

    @MockkBean
    private lateinit var taskService: TaskService

    @Test
    fun `List tasks`() {
        val tasks = listOf(Task(id = 1L, title = "test todo task title", description = "test todo task description"))
        every { taskService.findAll() } returns tasks

        mockMvc.perform(get("/api/v1/tasks").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.length()", `is`(1)))
    }
}