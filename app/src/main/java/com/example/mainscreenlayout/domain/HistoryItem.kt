package com.example.mainscreenlayout.domain

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.*

@Entity(tableName = "history",
foreignKeys = [
    ForeignKey(entity = Record::class,
        parentColumns = ["id"],
        childColumns = ["record_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE),
ForeignKey(entity = Answer::class,
parentColumns = ["id"],
childColumns = ["answer_id"],
    onUpdate = CASCADE,
    onDelete = CASCADE)])
data class HistoryItem(
    val record_id : String?,
    val answer_id : String?,
    val description : String?,
    val date : Long,
    @PrimaryKey
    val id: String = UUID.randomUUID().toString()
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(record_id)
        parcel.writeString(answer_id)
        parcel.writeString(description)
        parcel.writeLong(date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<HistoryItem> {
        override fun createFromParcel(parcel: Parcel): HistoryItem {
            return HistoryItem(parcel)
        }

        override fun newArray(size: Int): Array<HistoryItem?> {
            return arrayOfNulls(size)
        }
    }
}