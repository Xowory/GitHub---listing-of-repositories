package io.github.Xowory.allegrotask.controller

import io.github.Xowory.allegrotask.exception.UserNotFoundException
import io.github.Xowory.allegrotask.model.Repo
import io.github.Xowory.allegrotask.service.RepositoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/repos")
class RepositoryController @Autowired constructor(private val repositoryService: RepositoryService) {

    @GetMapping("/list/{username}")
    fun listRepositories(@PathVariable username : String) : MutableList<Repo> {
        return repositoryService.listRepositories(username)
    }

    @GetMapping("/rating/{username}")
    fun countStars(@PathVariable username: String): Int {
        return repositoryService.countStars(username)
    }

    @ExceptionHandler(UserNotFoundException::class)
    fun userNotFoundException(exception: UserNotFoundException): ResponseEntity<String> {
        return ResponseEntity
            .status(HttpStatus.NOT_FOUND)
            .body(exception.message)
    }
}