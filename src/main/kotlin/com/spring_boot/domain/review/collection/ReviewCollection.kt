package com.spring_boot.domain.review.collection

import com.spring_boot.base.collection.AbstractEntityCollection
import com.spring_boot.domain.review.Review

/**
 * @return [ReviewCollection] converted from [List<Review>]
 */
fun List<Review>.toCollection(): ReviewCollection {
    return ReviewCollection(this)
}

class ReviewCollection(list: List<Review>) : AbstractEntityCollection<ReviewCollection, Review>(list) {

}