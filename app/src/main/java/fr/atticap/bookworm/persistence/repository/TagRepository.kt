package fr.atticap.bookworm.persistence.repository

import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.persistence.dao.TagDao
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

class TagRepository(private val tagDao: TagDao) {
    fun getTagById(id: Uuid): Flow<Tag> = tagDao.getTagById(id)
}