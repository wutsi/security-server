package com.wutsi.security.dto

import kotlin.collections.List

public data class SearchApiKeyResponse(
    public val apiKeys: List<ApiKey> = emptyList()
)
