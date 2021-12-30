package com.todolist.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.time.OffsetDateTime
import java.util.*

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider", dateTimeProviderRef = "auditingDateTimeProvider")
class AuditConfiguration {

    @Bean
    fun auditorProvider(): AuditorAware<String> {
        return AuditorAware { Optional.of("") }
    }

    @Bean
    fun auditingDateTimeProvider(): DateTimeProvider{
        return DateTimeProvider { Optional.of(OffsetDateTime.now()) }
    }
}