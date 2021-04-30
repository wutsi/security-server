package com.wutsi.security.endpoint

import com.wutsi.security.`delegate`.CreateDelegate
import com.wutsi.security.dto.CreateApiKeyRequest
import com.wutsi.security.dto.CreateApiKeyResponse
import org.springframework.web.bind.`annotation`.PostMapping
import org.springframework.web.bind.`annotation`.RequestBody
import org.springframework.web.bind.`annotation`.RestController
import javax.validation.Valid

@RestController
public class CreateController(
    private val `delegate`: CreateDelegate
) {
    @PostMapping("/v1/api-keys")
    public fun invoke(@Valid @RequestBody request: CreateApiKeyRequest): CreateApiKeyResponse =
        delegate.invoke(request)
}
