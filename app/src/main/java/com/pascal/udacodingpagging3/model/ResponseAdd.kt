package com.pascal.udacodingpagging3.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseAdd(

	@field:SerializedName("id")
	val id: Int? = null
) : Parcelable
