package com.todolist.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@EntityListeners(AuditingEntityListener::class)
data class Task(
        @Id @GeneratedValue val id: Long?,
        val title: String,
        val description: String,
        val star: Boolean = false,
        val finish: Boolean = false,

        @CreatedDate
        @Column(name = "created_date", nullable = false, updatable = false)
        var createdDate: ZonedDateTime? = null,

        @LastModifiedDate
        @Column(name = "last_modified_date", nullable = false)
        var lastModifiedDate: ZonedDateTime? = null
)
