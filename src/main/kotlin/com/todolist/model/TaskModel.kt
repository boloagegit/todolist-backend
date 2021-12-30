package com.todolist.model

import com.todolist.entity.Task
import javax.validation.constraints.NotBlank

data class TaskModel(
        @NotBlank(message = "Title can not be null or empty")
        val title: String,
        @NotBlank(message = "Description can not be null or empty")
        val description: String,

        val star: Boolean = false,
        val finish: Boolean = false,
)

fun TaskModel.toTask(id: Long? = null) = Task(
        id = id,
        title = title,
        description = description,
        star = star,
        finish = finish
)