package com.wutsi.security.endpoint

import com.wutsi.security.`delegate`.DeleteDelegate
import org.springframework.web.bind.`annotation`.DeleteMapping
import org.springframework.web.bind.`annotation`.PathVariable
import org.springframework.web.bind.`annotation`.RestController
import kotlin.String

@RestController
public class DeleteController(
    private val `delegate`: DeleteDelegate
) {
    @DeleteMapping("/v1/api-keys/{id}")
    public fun invoke(@PathVariable(name = "id") id: String) {
        delegate.invoke(id)
    }
}
