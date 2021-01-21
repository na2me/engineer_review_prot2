package com.spring_boot.book

import com.google.gson.Gson
import com.spring_boot.base.AbstractEntity
import com.spring_boot.book.value_objects.BookTitle
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "book")
class Book(
        var title: BookTitle,
        var category: Int,
        var score: Double,
        var url: String) : AbstractEntity() {

    override fun isSatisfied() {
        TODO("Not yet implemented")
    }

    fun id() = this.id

    override fun toString(): String = Gson().toJson(this)
}