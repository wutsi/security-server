package com.wutsi.security.endpoint

import com.wutsi.security.dao.ApiKeyRepository
import com.wutsi.security.dto.GetApiKeyResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.HttpStatus
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.fail

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/GetController.sql"])
public class GetControllerTest {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var dao: ApiKeyRepository

    private val rest = RestTemplate()

    @Test
    public fun `get key`() {
        val url = "http://localhost:$port/v1/api-keys/1"

        val response = rest.getForEntity(url, GetApiKeyResponse::class.java)

        assertEquals(HttpStatus.OK, response.statusCode)

        val apiKey = response.body.apiKey
        assertNotNull("test", apiKey.name)
        assertEquals(listOf("foo", "bar"), apiKey.scopes)
    }

    @Test
    public fun `get key expired`() {
        val url = "http://localhost:$port/v1/api-keys/2"
        try {
            rest.getForEntity(url, GetApiKeyResponse::class.java)
            fail()
        } catch (ex: HttpClientErrorException) {
            assertEquals(HttpStatus.NOT_FOUND, ex.statusCode)
        } catch (ex: Exception) {
            fail()
        }
    }

    @Test
    public fun `get key not found`() {
        val url = "http://localhost:$port/v1/api-keys/999"
        try {
            rest.getForEntity(url, GetApiKeyResponse::class.java)
            fail()
        } catch (ex: HttpClientErrorException) {
            assertEquals(HttpStatus.NOT_FOUND, ex.statusCode)
        } catch (ex: Exception) {
            fail()
        }
    }
}
