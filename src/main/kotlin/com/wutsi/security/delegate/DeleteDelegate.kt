package com.wutsi.security.`delegate`

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.event.ApiKeyEventPayload
import com.wutsi.security.event.SecurityEventType
import com.wutsi.stream.EventStream
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
public class DeleteDelegate(
    private val dao: ApiKeyRepository,
    private val eventStream: EventStream
) {
    @Transactional
    public fun invoke(id: String) {
        val opt = dao.findById(id)
        if (opt.isEmpty)
            return

        val apiKey = opt.get()
        if (apiKey.expiryDateTime == null || apiKey.expired()) {
            apiKey.expiryDateTime = OffsetDateTime.now()
            dao.save(apiKey)
        }

        eventStream.publish(
            type = SecurityEventType.APIKEY_DELETED.urn,
            payload = ApiKeyEventPayload(id = apiKey.id)
        )
    }
}
