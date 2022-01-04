package com.todolist.service

import com.todolist.AppException
import org.springframework.http.HttpStatus

class AppBadRequestException(message: String? = null, cause: Throwable? = null) : AppException(HttpStatus.BAD_REQUEST, message, cause)