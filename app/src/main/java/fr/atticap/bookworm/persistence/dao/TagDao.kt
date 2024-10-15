package fr.atticap.bookworm.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.MapColumn
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import fr.atticap.bookworm.model.PositionedTag
import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.model.VolumeTag
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

@Dao
interface TagDao {
    @Query("SELECT * FROM tag")
    fun getAllTags(): Flow<List<Tag>>

    @Query("SELECT * FROM tag WHERE id = :id")
    fun getTagById(id: Uuid): Flow<Tag>

    @Query("SELECT tag.*, volume_tag.pos FROM volume_tag INNER JOIN tag ON volume_tag.tagId = tag.id WHERE volumeId = :volumeId")
    fun getAllTagsOfVolume(volumeId: Uuid): Flow<List<@MapColumn("pos") PositionedTag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(tag: Tag)

    @Transaction
    @Insert
    suspend fun insertVolumeTag(volumeTag: VolumeTag)
}