package com.example.pogoda2.dataanswer

import com.google.gson.annotations.SerializedName
import kotlin.collections.List

data class Base (

	@SerializedName("city") val city : City,
	@SerializedName("cod") val cod : Int,
	@SerializedName("message") val message : Double,
	@SerializedName("cnt") val cnt : Int,
	@SerializedName("list") val list : List<com.example.pogoda2.dataanswer.List>,
)