package com.spring_boot.domain.review.controller

import com.spring_boot.base.util.http.RequestParams
import com.spring_boot.domain.review.Review
import com.spring_boot.domain.review.repository.ReviewRepository
import com.spring_boot.domain.review.value_object.ReviewId
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/review")
@Api(description = "Review Controller")
class ReviewController {

    @ApiOperation(value = "Get All Reviews", notes = "get all Reviews from db", response = Review::class)
    @GetMapping("")
    fun index() = ReviewRepository.findAll()

    @ApiOperation(value = "Create New Review", notes = "create new Review entity", response = Review::class)
    @PostMapping("")
    fun create(@RequestBody params: Map<String, String>): Review {
        val requestParams = RequestParams(params)
        return Review.new(requestParams)
    }

    @ApiOperation(value = "Get a Specific Review", notes = "get a specific Review by ID", response = Review::class)
    @GetMapping("{id}")
    fun read(@PathVariable id: Long) = ReviewRepository.findById(ReviewId(id))

    @ApiOperation(value = "Update a Specific Review", notes = "update a specific Review by request params", response = Review::class)
    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody params: Map<String, String>): Review {
        val requestParams = RequestParams(params)
        return Review.new(requestParams, false, ReviewId(id))
    }

    @ApiOperation(value = "Delete a Specific Review", notes = "delete a specific Review passed", response = Review::class)
    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) {
        val targetEntity = ReviewRepository.findById(ReviewId(id))
        return ReviewRepository.delete(targetEntity)
    }
}