package fr.atticap.bookworm.service

import fr.atticap.bookworm.model.Tag
import fr.atticap.bookworm.persistence.dao.TagDao

class TagService(private val tagDao: TagDao) {
    suspend fun update(tag: Tag) {
        tagDao.update(tag)
    }
}