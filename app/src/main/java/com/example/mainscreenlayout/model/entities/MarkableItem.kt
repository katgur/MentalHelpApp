package com.example.mainscreenlayout.model

import android.os.Parcel
import android.os.Parcelable

data class MarkableItem(
    val id: String?,
    val name: String?,
    var isMarked: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeByte(if (isMarked) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MarkableItem> {
        override fun createFromParcel(parcel: Parcel): MarkableItem {
            return MarkableItem(parcel)
        }

        override fun newArray(size: Int): Array<MarkableItem?> {
            return arrayOfNulls(size)
        }
    }

}