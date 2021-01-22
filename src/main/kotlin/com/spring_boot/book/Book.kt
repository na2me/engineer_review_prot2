package com.spring_boot.book

import com.google.gson.Gson
import com.spring_boot.base.AbstractEntity
import com.spring_boot.book.value_objects.BookTitle
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "book")
class Book(
        @Embedded
        var title: BookTitle,
        var category: Int,
        var score: Double,
        var url: String) : AbstractEntity() {

    override fun isSatisfied() {
        //// 必須フィールド存在確認
    }

    fun id() = this.id

    override fun toString(): String = Gson().toJson(this)
}