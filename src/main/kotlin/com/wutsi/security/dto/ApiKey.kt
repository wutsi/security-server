package com.wutsi.security.dto

import kotlin.String
import kotlin.collections.List

public data class ApiKey(
    public val id: String = "",
    public val name: String = "",
    public val scopes: List<String> = emptyList()
)
