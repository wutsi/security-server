package com.wutsi.security.`delegate`

import com.wutsi.security.dao.ApiKeyRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import javax.transaction.Transactional

@Service
public class DeleteDelegate(private val dao: ApiKeyRepository) {
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
    }
}
