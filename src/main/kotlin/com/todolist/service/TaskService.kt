package com.todolist.service

import com.todolist.entity.Task
import com.todolist.repository.TaskRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TaskService(@Autowired val taskRepository: TaskRepository) {
    fun findAll(): List<Task> {
        return taskRepository.findAll()
    }

    fun createTask(task: Task) {
        taskRepository.save(task)
    }

    fun updateTask(id: Long, task: Task) {
        if(!taskRepository.existsById(id)){
            throw AppBadRequestException("Unable to find the data to be updated")
        }

        taskRepository.save(task)
    }

    fun deleteTask(id: Long) {
        if(!taskRepository.existsById(id)){
            throw AppBadRequestException("Unable to find the data to be deleted")
        }

        taskRepository.deleteById(id)
    }


}