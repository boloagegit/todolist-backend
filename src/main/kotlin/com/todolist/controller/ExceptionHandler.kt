package com.todolist.controller

import com.todolist.AppException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class ExceptionHandler {

    data class ExceptionResponse(
            val status: Int,
            val error: String,
            val message: String,
            val path: String,
            val timestamp: Date = Date(),
    )

    @ExceptionHandler(AppException::class)
    fun handleAppException(
            exception: AppException,
            request: HttpServletRequest
    ): ResponseEntity<ExceptionResponse> {
        return ResponseEntity(
                ExceptionResponse(
                        exception.code.value(),
                        "Bad Request",
                        exception.message ?: "",
                        request.requestURI,
                ),
                HttpStatus.BAD_REQUEST,
        )
    }
}