package com.todolist.controller

import com.todolist.entity.Task
import com.todolist.model.TaskModel
import com.todolist.model.toTask
import com.todolist.service.TaskService
import com.todolist.service.TaskServiceError
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/tasks")
@Api(value = "task", description = "Rest Api for task operations", tags = ["Task API"])
class TaskController(@Autowired val taskService: TaskService) {

    @GetMapping
    @ApiOperation(value = "Display all tasks", response = Task::class)
    fun findAllTask(): List<Task> = taskService.findAll()

    @PostMapping
    @ApiOperation(value = "Create new task")
    @ApiResponses(value = [ApiResponse(code = 201, message = "Created")])
    fun createTask(@Valid @RequestBody taskModel: TaskModel) {
        taskService.createTask(taskModel.toTask())
    }

    /**
     * this is an example of using arrow-kt
     */
    @PutMapping("/{id}")
    @ApiResponses(value = [
        ApiResponse(code = 200, message = "OK"),
        ApiResponse(code = 400, message = "Errors related to database operations", response = ExceptionHandler.ExceptionResponse::class),
        ApiResponse(code = 400, message = "The input parameter is wrong, unable to find the data by id", response = ExceptionHandler.ExceptionResponse::class)
    ])
    fun updateTask(@Valid @RequestBody taskModel: TaskModel, @PathVariable id: Long, request: HttpServletRequest) =
            taskService.updateTask(id, taskModel.toTask(id)).fold(
                    ifLeft = {
                        when (it) {
                            is TaskServiceError.DatabaseError -> ResponseEntity(
                                    ExceptionHandler.ExceptionResponse(
                                            40001,
                                            "Bad Request",
                                            it.msg,
                                            request.requestURI,
                                    ),
                                    HttpStatus.BAD_REQUEST,
                            )
                            is TaskServiceError.DataNotFoundByIdError -> ResponseEntity(
                                    ExceptionHandler.ExceptionResponse(
                                            40002,
                                            "Bad Request",
                                            it.msg,
                                            request.requestURI,
                                    ),
                                    HttpStatus.BAD_REQUEST,
                            )
                        }
                    },
                    ifRight = { ResponseEntity(HttpStatus.OK) }
            )


    @DeleteMapping("/{id}")
    @ApiResponses(value = [ApiResponse(code = 200, message = "OK")])
    fun deleteTask(@PathVariable id: Long) {
        taskService.deleteTask(id)
    }

}