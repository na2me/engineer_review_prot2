package com.spring_boot.model.book

import com.google.gson.Gson
import com.spring_boot.base.AbstractEntity
import com.spring_boot.model.book.value_objects.BookCategory
import com.spring_boot.model.book.value_objects.BookScore
import com.spring_boot.model.book.value_objects.BookTitle
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table


@Entity
@Table(name = "book")
class Book(
        @Embedded var title: BookTitle,
        @Embedded var category: BookCategory,
        @Embedded var score: BookScore,
        var url: String) : AbstractEntity() {

    override fun isSatisfied() {
        //// 必須フィールド存在確認
    }

    fun id() = this.id

    override fun toString(): String = Gson().toJson(this)
}