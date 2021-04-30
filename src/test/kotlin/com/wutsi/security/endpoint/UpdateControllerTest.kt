package com.wutsi.security.endpoint

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.dto.UpdateApiKeyRequest
import com.wutsi.security.dto.UpdateApiKeyResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals
import kotlin.test.fail

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/UpdateController.sql"])
public class UpdateControllerTest {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var dao: ApiKeyRepository

    private val rest = RestTemplate()

    @Test
    public fun `update key`() {
        val url = "http://localhost:$port/v1/api-keys/1"
        val request = UpdateApiKeyRequest(
            scopes = listOf("yo", "man", "how"),
            name = "TxT"
        )

        val response = rest.postForEntity(url, request, UpdateApiKeyResponse::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)

        val apiKey = dao.findById("1").get()
        assertEquals("yo,man,how", apiKey.scopes)
        assertEquals("TxT", apiKey.name)
    }

    @Test
    public fun `update expired key`() {
        val url = "http://localhost:$port/v1/api-keys/2"
        val request = UpdateApiKeyRequest(
            scopes = listOf("yo", "man", "how"),
            name = "yo"
        )

        try {
            rest.postForEntity(url, request, UpdateApiKeyResponse::class.java)
            fail()
        } catch (ex: HttpClientErrorException) {
            assertEquals(HttpStatus.NOT_FOUND, ex.statusCode)
        } catch (ex: Exception) {
            fail()
        }
    }

    @Test
    public fun `update invalid key`() {
        val url = "http://localhost:$port/v1/api-keys/999"
        val request = UpdateApiKeyRequest(
            scopes = listOf("yo", "man", "how"),
            name = "yo"
        )

        try {
            rest.postForEntity(url, request, UpdateApiKeyResponse::class.java)
            fail()
        } catch (ex: HttpClientErrorException) {
            assertEquals(HttpStatus.NOT_FOUND, ex.statusCode)
        } catch (ex: Exception) {
            fail()
        }
    }
}
