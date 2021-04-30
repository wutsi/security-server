package com.wutsi.security.endpoint

import com.wutsi.security.dao.ApiKeyRepository
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.jdbc.Sql
import org.springframework.web.client.RestTemplate
import java.time.OffsetDateTime
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = ["/db/clean.sql", "/db/DeleteController.sql"])
public class DeleteControllerTest {
    @LocalServerPort
    public val port: Int = 0

    @Autowired
    private lateinit var dao: ApiKeyRepository

    private val rest = RestTemplate()

    @Test
    public fun `delete key`() {
        val now = OffsetDateTime.now()
        val url = "http://localhost:$port/v1/api-keys/1"

        rest.delete(url)

        val apiKey = dao.findById("1").get()
        assertNotNull(apiKey.expiryDateTime)
        assertTrue(OffsetDateTime.now().toEpochSecond() - now.toEpochSecond() < 5)
    }

    @Test
    public fun `delete key with expiration-date in future`() {
        val now = OffsetDateTime.now()
        val url = "http://localhost:$port/v1/api-keys/2"

        rest.delete(url)

        val apiKey = dao.findById("2").get()
        assertNotNull(apiKey.expiryDateTime)
        assertTrue(OffsetDateTime.now().toEpochSecond() - now.toEpochSecond() < 5)
    }

    @Test
    public fun `delete key already expired`() {
        val url = "http://localhost:$port/v1/api-keys/3"

        rest.delete(url)

        val apiKey = dao.findById("3").get()
        assertNotNull(apiKey.expiryDateTime)
    }

    @Test
    public fun `nothing when invalid key`() {
        val url = "http://localhost:$port/v1/api-keys/9999"
        rest.delete(url)
    }
}
