package io.github.Xowory.allegrotask.model

class Repo {
    var name: String? = null
    var stargazers_count : Int = 0

    override fun toString(): String {
        return "Repo(name=$name, stargazers_count=$stargazers_count)"
    }

}