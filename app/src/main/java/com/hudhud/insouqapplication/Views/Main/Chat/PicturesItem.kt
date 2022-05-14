package com.hudhud.insouqapplication.Views.Main.Chat

import com.google.gson.annotations.SerializedName

data class PicturesItem(@SerializedName("imageURL")
                        val imageURL: String = "",
                        @SerializedName("mainPicture")
                        val mainPicture: Boolean = false,
                        @SerializedName("id")
                        val id: Int = 0)