package com.sms.diaganal

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posters")
data class PosterImagesData(
    @PrimaryKey val page: Page
)