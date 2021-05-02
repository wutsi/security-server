package com.wutsi.security.endpoint

import com.wutsi.security.`delegate`.SearchDelegate
import com.wutsi.security.dto.SearchApiKeyResponse
import org.springframework.web.bind.`annotation`.GetMapping
import org.springframework.web.bind.`annotation`.RequestParam
import org.springframework.web.bind.`annotation`.RestController
import kotlin.Int

@RestController
public class SearchController(
    private val `delegate`: SearchDelegate
) {
    @GetMapping("/v1/api-keys")
    public fun invoke(
        @RequestParam(name = "limit", required = false, defaultValue = "20") limit: Int = 20,
        @RequestParam(name = "offset", required = false, defaultValue = "0") offset: Int = 0
    ):
        SearchApiKeyResponse = delegate.invoke(limit, offset)
}
