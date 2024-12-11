package endterm.service

import endterm.model.Photo
import endterm.repository.PhotoRepository
import endterm.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PhotoService(
    private val userRepository: UserRepository,
    private val photoRepository: PhotoRepository
) {

    fun savePhoto(photo: Photo) {
        photoRepository.save(photo)
    }


}