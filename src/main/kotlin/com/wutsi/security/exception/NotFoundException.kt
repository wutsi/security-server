package com.wutsi.security.exception

import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.Exception
import kotlin.String

@ResponseStatus(NOT_FOUND)
class NotFoundException(message: String) : Exception(message)
