package com.wutsi.security.`delegate`

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.dto.ApiKey
import com.wutsi.security.dto.GetApiKeyResponse
import com.wutsi.security.exception.NotFoundException
import org.springframework.stereotype.Service

@Service
public class GetDelegate(private val dao: ApiKeyRepository) {
    public fun invoke(id: String): GetApiKeyResponse {
        val apiKey = dao.findById(id).orElseThrow { NotFoundException("not_found") }
        if (apiKey.expired())
            throw NotFoundException("expired")

        return GetApiKeyResponse(
            apiKey = ApiKey(
                id = apiKey.id,
                name = apiKey.name,
                scopes = apiKey.scopes.split(",")
            )
        )
    }
}
