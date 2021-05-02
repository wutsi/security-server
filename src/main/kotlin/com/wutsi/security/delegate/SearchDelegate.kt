package com.wutsi.security.`delegate`

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.dto.ApiKey
import com.wutsi.security.dto.SearchApiKeyResponse
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
public class SearchDelegate(private val dao: ApiKeyRepository) {
    public fun invoke(
        limit: Int = 20,
        offset: Int = 0
    ): SearchApiKeyResponse {
        val pageable = PageRequest.of(offset / limit, limit)
        val entities = dao.findAll(pageable)
        return SearchApiKeyResponse(
            apiKeys = entities.filter { !it.expired() }
                .map {
                    ApiKey(
                        id = it.id,
                        name = it.name,
                        scopes = it.scopes.split(",")
                    )
                }
        )
    }
}
