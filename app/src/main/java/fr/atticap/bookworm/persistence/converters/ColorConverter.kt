package fr.atticap.bookworm.persistence.converters

import androidx.compose.ui.graphics.Color
import androidx.room.TypeConverter

@OptIn(ExperimentalStdlibApi::class)
class ColorConverter {
    val format = HexFormat {
        number.removeLeadingZeros = true
        number.minLength = 6
    }

    @TypeConverter
    fun fromColor(uuid: Color): String {
        return uuid.value.toHexString(format = format)
    }

    @TypeConverter
    fun colorFromString(string: String): Color {
        return Color(string.hexToULong(format = format))
    }
}