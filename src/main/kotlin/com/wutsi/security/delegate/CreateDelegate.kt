package com.wutsi.security.`delegate`

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.dto.CreateApiKeyRequest
import com.wutsi.security.dto.CreateApiKeyResponse
import com.wutsi.security.entity.ApiKeyEntity
import org.springframework.stereotype.Service
import java.util.UUID
import javax.transaction.Transactional

@Service
public class CreateDelegate(private val dao: ApiKeyRepository) {
    @Transactional
    public fun invoke(request: CreateApiKeyRequest): CreateApiKeyResponse {
        val key = dao.save(
            ApiKeyEntity(
                id = UUID.randomUUID().toString(),
                name = request.name,
                scopes = request.scopes
                    .map { it.trim() }
                    .joinToString(separator = ",")
            )
        )
        return CreateApiKeyResponse(
            id = key.id
        )
    }
}
