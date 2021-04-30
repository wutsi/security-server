package com.wutsi.security.endpoint

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.dto.CreateApiKeyRequest
import com.wutsi.security.dto.CreateApiKeyResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql"])
public class CreateControllerTest {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var dao: ApiKeyRepository

    private val rest = RestTemplate()

    @Test
    public fun invoke() {
        val url = "http://localhost:$port/v1/api-keys"
        val request = CreateApiKeyRequest(
            name = "test",
            scopes = listOf("foo", "bar")
        )
        val response = rest.postForEntity(url, request, CreateApiKeyResponse::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)

        val apiKey = dao.findById(response.body.id).get()
        assertEquals(request.name, apiKey.name)
        assertEquals("foo,bar", apiKey.scopes)
        assertNotNull(apiKey.creationDateTime)
        assertNull(apiKey.expiryDateTime)
    }
}
