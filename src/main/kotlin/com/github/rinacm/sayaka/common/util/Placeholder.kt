package com.github.rinacm.sayaka.common.util

import java.util.*

enum class PlaceholderOption {
    OPTIONAL, MUTUALLY_EXCLUSIVE, REQUIRED, ARRAY, EMPTY
}

class Placeholder(private vararg val names: String, private val option: PlaceholderOption = PlaceholderOption.REQUIRED, private val repeatable: Boolean = false) {
    companion object {
        val EMPTY: Placeholder = Placeholder(String.EMPTY, option = PlaceholderOption.EMPTY, repeatable = false)
    }

    private var next: LinkedList<Placeholder> = LinkedList(listOf(this))

    override fun toString(): String {
        return buildString {
            for (n in next) {
                val curr = when (option) {
                    PlaceholderOption.OPTIONAL -> "[${names[0]}${ellipsisIfRepeatable()}]"
                    PlaceholderOption.REQUIRED -> "<${names[0]}${ellipsisIfRepeatable()}>"
                    PlaceholderOption.MUTUALLY_EXCLUSIVE -> {
                        requires<IllegalArgumentException>(names.size == 2 && !repeatable, "size of placeholder names must be 2 and repeatable must be false when ${PlaceholderOption.MUTUALLY_EXCLUSIVE} is set")
                        "{${names[0]}|${names[1]}}"
                    }
                    PlaceholderOption.ARRAY -> {
                        requires<java.lang.IllegalArgumentException>(names.size > 1 && !repeatable, "size of placeholder names must be greater than 1 and repeatable must be false when ${PlaceholderOption.ARRAY} is set")
                        names.joinToString(",", "{", "}")
                    }
                    PlaceholderOption.EMPTY -> String.EMPTY
                }
                append(curr)
                if (n != next.last) append(" ")
            }
        }
    }

    operator fun plusAssign(another: Placeholder) {
        next.add(another)
    }

    private fun ellipsisIfRepeatable(): String {
        return if (repeatable) "..." else String.EMPTY
    }
}

@DslMarker
annotation class PlaceholderDsl

class PlaceholderBuilder {
    private var placeHolder: Placeholder = Placeholder.EMPTY

    fun require(name: String) {
        placeHolder += Placeholder(name)
    }

    fun requireRepeatable(name: String) {
        placeHolder += Placeholder(name, repeatable = true)
    }

    fun optional(name: String) {
        placeHolder += Placeholder(name, option = PlaceholderOption.OPTIONAL)
    }

    fun optionalRepeatable(name: String) {
        placeHolder += Placeholder(name, option = PlaceholderOption.OPTIONAL, repeatable = true)
    }

    fun mutually(first: String, second: String) {
        placeHolder += Placeholder(first, second, option = PlaceholderOption.MUTUALLY_EXCLUSIVE)
    }

    fun array(vararg name: String) {
        placeHolder += Placeholder(*name, option = PlaceholderOption.ARRAY)
    }

    fun build(): Placeholder {
        return placeHolder
    }
}