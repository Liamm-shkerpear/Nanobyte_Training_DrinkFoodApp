package com.example.drinkfoodapp.main.data.domain.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "wines")
@Parcelize
data class WineItem(
    @PrimaryKey val id: Int,
    val wine: String?,
    val winery: String?,
    val location: String?,
    val image: String?,
    @Embedded val rating: Rating?,
    @ColumnInfo(name = "is_saved")
    val isSaved: Boolean = false
) : Parcelable {
    @Parcelize
    data class Rating(
        val average: String?,
        val reviews: String?
    ) : Parcelable
}
