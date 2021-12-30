package com.todolist

import org.springframework.http.HttpStatus

open class AppException(val code: HttpStatus, message: String? = null, cause: Throwable? = null): Exception(message, cause)