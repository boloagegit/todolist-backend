package com.todolist.service

import arrow.core.Either
import arrow.core.flatMap
import com.todolist.entity.Task
import com.todolist.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

sealed class TaskServiceError(open val msg: String) {
    data class DataNotFoundByIdError(val id: Long) : TaskServiceError("Unable to find the data, id = $id")
    data class DatabaseError(val reason: String?) : TaskServiceError("Database access error, the reason is $reason")
}

@Service
class TaskService(@Autowired val taskRepository: TaskRepository) {
    fun findAll(): List<Task> {
        return taskRepository.findAll()
    }

    fun createTask(task: Task) {
        taskRepository.save(task)
    }

    fun updateTask(id: Long, task: Task): Either<TaskServiceError, Task> =
            Either.conditionally(taskRepository.existsById(id), { TaskServiceError.DataNotFoundByIdError(id) }, {})
                    .flatMap {
                        Either.catch {
                            taskRepository.save(task)
                        }.mapLeft {
                            TaskServiceError.DatabaseError(it.message)
                        }
                    }

    fun deleteTask(id: Long) {
        if (!taskRepository.existsById(id)) {
            throw AppBadRequestException("Unable to find the data to be deleted")
        }

        taskRepository.deleteById(id)
    }
}