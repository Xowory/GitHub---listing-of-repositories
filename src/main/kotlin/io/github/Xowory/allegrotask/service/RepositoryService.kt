package io.github.Xowory.allegrotask.service

import io.github.Xowory.allegrotask.exception.UserNotFoundException
import io.github.Xowory.allegrotask.model.Repo
import io.github.Xowory.allegrotask.model.User
import mu.KotlinLogging
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.exchange
import java.util.*
import kotlin.math.ceil


@Service
class RepositoryService {
    companion object {
        const val url = "https://api.github.com/users/"
        val logger = KotlinLogging.logger {}
    }

    val reposPerPage = 100
    private val restTemplate = RestTemplate()

    fun listRepositories(username: String): MutableList<Repo> {
        val repos = getUserRepos(username)
        logger.info("User $username has following repositories: {}", repos)

        return repos
    }

    fun countStars(username: String): Int {
        var starCount = 0
        val repos: List<Repo> = getUserRepos(username)
        for (repo in repos) {
            starCount += repo.stargazers_count
        }
        logger.info("User $username has $starCount stars in total")
        return starCount
    }

    private fun getUserRepos(username: String): MutableList<Repo> {
        try {
            val userResponseEntity : ResponseEntity<User> = restTemplate.exchange("$url$username", HttpMethod.GET)
            val repositoryResponseEntity : ResponseEntity<Array<Repo>> = restTemplate.exchange("$url$username/repos?per_page=$reposPerPage", HttpMethod.GET)

            return getReposFromGithub(userResponseEntity, repositoryResponseEntity, username)
        }
        catch (exception : HttpClientErrorException){
            throw UserNotFoundException("User $username was not found!", exception)
        }

    }

    private fun getReposFromGithub(userResponseEntity: ResponseEntity<User>, repositoryResponseEntity: ResponseEntity<Array<Repo>>, username: String): MutableList<Repo> {
        val numberOfRepos = userResponseEntity.body!!.getPublicRepos()

        if(numberOfRepos <= reposPerPage){
            logger.info("User $username has $numberOfRepos repositories")

            val repoArray : Array<Repo> = repositoryResponseEntity.body!!

            return repoArray.toList() as MutableList<Repo>
        }
        else{
            val numberOfPages : Int = ceil(numberOfRepos.toDouble() / reposPerPage).toInt()
            logger.info("User $username has $numberOfRepos repositories on $numberOfPages pages")

            val repoList : MutableList<Repo> = ArrayList()

            for (i in 1..numberOfPages) {
                val repositoryResponseEntityCopy: ResponseEntity<Array<Repo>> =
                    restTemplate.exchange("$url$username/repos?per_page=$reposPerPage&page=$i", HttpMethod.GET)
                    Collections.addAll(repoList, *repositoryResponseEntityCopy.body!!)
            }
            return repoList
        }
    }
}