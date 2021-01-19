package com.spring_boot.book

import javax.persistence.*


@Entity
@Table(name = "book")
class Book(
        var title: String,
        var category: Int,
        var score: Double,
        var url: String,
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Long? = null)