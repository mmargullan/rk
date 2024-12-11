package endterm.repository

import endterm.model.Photo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PhotoRepository: JpaRepository<Photo, Long> {
    fun findByUser_Username(userUsername: String): Photo?
//    fun findById(id: Long)
}