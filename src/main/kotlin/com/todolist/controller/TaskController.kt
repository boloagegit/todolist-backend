package com.todolist.controller

import com.todolist.entity.Task
import com.todolist.model.TaskModel
import com.todolist.model.toTask
import com.todolist.service.TaskService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/tasks")
@Api(value = "task", description = "Rest Api for task operations", tags = ["Task API"])
class TaskController(@Autowired val taskService: TaskService) {

    @GetMapping
    @ApiOperation(value = "Display all tasks", response = Task::class)
    fun findAllTask(): List<Task> {
        return taskService.findAll()
    }

    @PostMapping
    @ApiOperation(value = "Create new task")
    @ApiResponses(value = [ApiResponse(code = 200, message = "OK")])
    fun createTask(@Valid @RequestBody taskModel: TaskModel) {
        taskService.createTask(taskModel.toTask())
    }

    @PutMapping("/{id}")
    fun updateTask(@Valid @RequestBody taskModel: TaskModel, @PathVariable id: Long) {
        taskService.updateTask(id, taskModel.toTask(id))
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long) {
        taskService.deleteTask(id)
    }

}