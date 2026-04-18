package com.soccergrlstudios.wannmensieren.data.api

import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TumNatApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: TumNatApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TumNatApi::class.java)
    }

    @After
    fun teardown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCourses returns response with hits on 200 OK`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""
                {
                  "total_count": 2,
                  "count": 2,
                  "offset": 0,
                  "hits": [
                    {
                      "course_id": 1,
                      "course_name": "Test Course 1"
                    },
                    {
                      "course_id": 2,
                      "course_name": "Test Course 2"
                    }
                  ]
                }
            """.trimIndent())
        mockWebServer.enqueue(mockResponse)

        val response = api.getCourses(emptyMap())

        assertNotNull(response)
        assertEquals(2, response.hits.size)
        assertEquals(1L, response.hits[0].courseId)
        assertEquals("Test Course 1", response.hits[0].courseName)
        assertEquals(2L, response.hits[1].courseId)
        assertEquals("Test Course 2", response.hits[1].courseName)

        val recordedRequest = mockWebServer.takeRequest()
        assertEquals("/api/v1/course", recordedRequest.path)
    }

    @Test
    fun `getCourses with params sends correct query strings`() = runBlocking {
        val mockResponse = MockResponse()
            .setResponseCode(200)
            .setBody("""{"total_count": 0, "count": 0, "offset": 0, "hits": []}""")
        mockWebServer.enqueue(mockResponse)

        val params = mapOf("filter" to "cs", "limit" to "10")
        api.getCourses(params)

        val recordedRequest = mockWebServer.takeRequest()
        val path = recordedRequest.path ?: ""
        assert(path.contains("filter=cs"))
        assert(path.contains("limit=10"))
    }
}
