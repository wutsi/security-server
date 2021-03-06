package com.wutsi.security

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cache.`annotation`.EnableCaching
import org.springframework.scheduling.`annotation`.EnableAsync
import org.springframework.scheduling.`annotation`.EnableScheduling
import org.springframework.transaction.`annotation`.EnableTransactionManagement
import kotlin.String

@SpringBootApplication
@EnableAsync
@EnableTransactionManagement
@EnableCaching
@EnableScheduling
public class Application

public fun main(vararg args: String) {
    org.springframework.boot.runApplication<Application>(*args)
}
