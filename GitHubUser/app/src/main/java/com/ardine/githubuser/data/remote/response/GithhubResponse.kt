package com.ardine.githubuser.data.remote.response

import com.ardine.githubuser.data.local.entity.User
import com.google.gson.annotations.SerializedName

data class GithhubResponse(

	@field:SerializedName("total_count")
	val totalCount: Int?,

	@field:SerializedName("incomplete_results")
	val incompleteResults: Boolean?,

	@field:SerializedName("items")
	val items: List<User?>?
)