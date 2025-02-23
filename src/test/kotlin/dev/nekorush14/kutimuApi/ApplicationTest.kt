package dev.nekorush14.kutimuApi

import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            module()
        }
        val client = createClient { expectSuccess = false }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }

}
