package com.wutsi.security.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import kotlin.String
import kotlin.collections.List

public data class CreateApiKeyRequest(
    @get:NotBlank
    public val name: String = "",
    @get:NotNull
    @get:NotEmpty
    public val scopes: List<String> = emptyList()
)
