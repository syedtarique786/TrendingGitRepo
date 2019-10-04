package com.android.syed.gitrepo.model

import android.os.Parcel
import android.os.Parcelable

data class RepoModel(
    val author: String,
    val name: String,
    val avatar: String,
    val description: String,
    val stars: Int,
    val forks: Int,
    var language: String? = null,
    var languageColor: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(author)
        parcel.writeString(name)
        parcel.writeString(avatar)
        parcel.writeString(description)
        parcel.writeInt(stars)
        parcel.writeInt(forks)
        parcel.writeString(language)
        parcel.writeString(languageColor)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RepoModel> {
        override fun createFromParcel(parcel: Parcel): RepoModel {
            return RepoModel(parcel)
        }

        override fun newArray(size: Int): Array<RepoModel?> {
            return arrayOfNulls(size)
        }
    }
}