package com.wutsi.security.endpoint

import com.wutsi.security.dto.SearchApiKeyResponse
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus.OK
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/SearchController.sql"])
public class SearchControllerTest {
    @LocalServerPort
    public val port: Int = 0

    @Test
    public fun invoke() {
        val url = "http://localhost:$port/v1/api-keys"
        val rest = RestTemplate()
        val response = rest.getForEntity(url, SearchApiKeyResponse::class.java)

        assertEquals(OK, response.statusCode)

        val apiKeys = response.body.apiKeys
        assertEquals(2, apiKeys.size)

        assertNotNull("1", apiKeys[0].id)
        assertNotNull("test", apiKeys[0].name)
        assertEquals(listOf("foo", "bar"), apiKeys[0].scopes)

        assertNotNull("3", apiKeys[1].id)
        assertNotNull("me", apiKeys[1].name)
        assertEquals(listOf("bar"), apiKeys[1].scopes)
    }
}
