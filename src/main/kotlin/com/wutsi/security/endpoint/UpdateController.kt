package com.wutsi.security.endpoint

import com.wutsi.security.`delegate`.UpdateDelegate
import com.wutsi.security.dto.UpdateApiKeyRequest
import com.wutsi.security.dto.UpdateApiKeyResponse
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid
import kotlin.String

@RestController
public class UpdateController(
    private val `delegate`: UpdateDelegate
) {
    @PostMapping("/v1/api-keys/{id}")
    public fun invoke(
        @PathVariable(name = "id") id: String,
        @Valid @RequestBody
        request: UpdateApiKeyRequest
    ): UpdateApiKeyResponse = delegate.invoke(id, request)
}
