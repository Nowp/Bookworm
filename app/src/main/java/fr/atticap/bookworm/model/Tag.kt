package fr.atticap.bookworm.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.uuid.Uuid

@Entity(tableName = "tag")
data class Tag(
    @PrimaryKey
    val id: Uuid = Uuid.random(),
    val name: String,
    val color: Color,
)
