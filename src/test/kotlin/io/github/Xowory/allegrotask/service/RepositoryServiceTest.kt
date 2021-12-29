package io.github.Xowory.allegrotask.service

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.Xowory.allegrotask.model.Repo
import io.github.Xowory.allegrotask.model.User
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.impl.client.HttpClientBuilder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.test.web.client.exchange
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get


@SpringBootTest
@AutoConfigureMockMvc
internal class RepositoryServiceTest{

    @Autowired
    private lateinit var mvc: MockMvc

    @Autowired
    var objectMapper: ObjectMapper? = null

    var restTemplate : TestRestTemplate = TestRestTemplate()

    @Test
    fun `given user does not exists when user info is retrieved then 404 is received`() {
        val username = "..."
        val request: HttpUriRequest = HttpGet("https://api.github.com/users/$username")
        val httpResponse: CloseableHttpResponse? = HttpClientBuilder.create().build().execute(request)

        assertEquals(HttpStatus.NOT_FOUND.value(), httpResponse!!.statusLine.statusCode)
    }

    @Test
    fun `should throw UserNotFoundException`(){
        val username = "..."
        val mvcResult : MvcResult = mvc.get("/repos/list/$username")
            .andDo { print() }
            .andExpect { status{ isNotFound() } }
            .andReturn()

        assertEquals("User $username was not found!", mvcResult.response.contentAsString)
    }

    @Test
    fun `get all repository status`(){
        val username = "Xowory"
        val mvcResult : MvcResult = mvc.get("/repos/list/$username")
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andReturn()

        val request: HttpUriRequest = HttpGet("https://api.github.com/users/$username")
        val httpResponse: CloseableHttpResponse? = HttpClientBuilder.create().build().execute(request)

        assertEquals(httpResponse!!.statusLine.statusCode, mvcResult.response.status)
    }

    @Test
    fun `get all repository`(){
        val username = "Xowory"
        val mvcResult : MvcResult = mvc.get("/repos/list/$username")
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andReturn()

        val repository : Array<Repo> = objectMapper!!.readValue(mvcResult.response.contentAsString, Array<Repo>::class.java)

        val result : ResponseEntity<User> = restTemplate.exchange("https://api.github.com/users/$username", HttpMethod.GET)

        assertEquals(repository.size, result.body!!.public_repos)
    }

    @Test
    fun countStargazers(){
        val username = "Xowory"
        val mvcResult : MvcResult = mvc.get("/repos/rating/$username")
            .andDo { print() }
            .andExpect { status { isOk() } }
            .andReturn()

        assertEquals(mvcResult.response.contentAsString, "0")

    }
}
