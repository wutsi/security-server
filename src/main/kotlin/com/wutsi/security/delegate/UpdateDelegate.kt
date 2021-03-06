package com.wutsi.security.`delegate`

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.dto.UpdateApiKeyRequest
import com.wutsi.security.dto.UpdateApiKeyResponse
import com.wutsi.security.event.ApiKeyEventPayload
import com.wutsi.security.event.SecurityEventType
import com.wutsi.security.exception.NotFoundException
import com.wutsi.stream.EventStream
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
public class UpdateDelegate(
    private val dao: ApiKeyRepository,
    private val eventStream: EventStream
) {
    @Transactional
    public fun invoke(id: String, request: UpdateApiKeyRequest): UpdateApiKeyResponse {
        val apiKey = dao.findById(id).orElseThrow { NotFoundException("not_found") }
        if (apiKey.expired())
            throw NotFoundException("expired")

        apiKey.name = request.name
        apiKey.scopes = request.scopes
            .map { it.trim() }
            .joinToString(separator = ",")
        dao.save(apiKey)

        eventStream.publish(
            type = SecurityEventType.APIKEY_UPDATED.urn,
            payload = ApiKeyEventPayload(id)
        )
        return UpdateApiKeyResponse(
            id = id
        )
    }
}
