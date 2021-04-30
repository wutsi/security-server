package com.wutsi.security.endpoint

import com.wutsi.security.`delegate`.GetDelegate
import com.wutsi.security.dto.GetApiKeyResponse
import org.springframework.web.bind.`annotation`.GetMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.String

@RestController
public class GetController(
    private val `delegate`: GetDelegate
) {
    @GetMapping("/v1/api-keys/{id}")
    public fun invoke(@PathVariable(name = "id") id: String): GetApiKeyResponse = delegate.invoke(id)
}
