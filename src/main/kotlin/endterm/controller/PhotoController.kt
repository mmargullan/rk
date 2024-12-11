package endterm.controller

import endterm.model.Photo
import endterm.repository.PhotoRepository
import endterm.service.PhotoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*

@Controller
class PhotoController(
    private val photoService: PhotoService,
    private val photoRepository: PhotoRepository
) {

    private val uploadDir = "uploads/"

    @PostMapping("/upload")
    fun uploadPhoto(@RequestParam file: MultipartFile, model: Model): String {
        val uploadPath = Paths.get(uploadDir)
        Files.createDirectories(uploadPath)

        val filename = UUID.randomUUID().toString() + "_" + file.originalFilename
        val filePath = uploadPath.resolve(filename)

        Files.copy(file.inputStream, filePath, StandardCopyOption.REPLACE_EXISTING)

        model.addAttribute("imagePath", "/images/$filename")
        val photo = Photo().apply {
            this.name = filename
            this.photo = file.bytes
        }
        photoService.savePhoto(photo)
        return "index"
    }

//    @GetMapping("/view/{filename}")
//    @ResponseBody
//    fun viewPhoto(@PathVariable filename: String): ResponseEntity<Any> {
//        val file = Paths.get(uploadDir).resolve(filename)
//        val resource = UrlResource(file.toUri())
//
//        return ResponseEntity.ok()
//            .contentType(MediaType.IMAGE_JPEG)
//            .body(resource)
//    }

    @GetMapping("/test")
    fun test(id: Long, model: Model): String{

        val photo = photoRepository.findById(id)

        val fileName = photo.get().name
        println(fileName)
        model.addAttribute("image" +
                "", "/images/$fileName")

        model.addAttribute("photo_byId", photo)

        return "index"
    }

//    @GetMapping("/photos")
//    fun listPhotos(
//        @RequestParam(defaultValue = "0") page: Int,
//        @RequestParam(defaultValue = "5") size: Int,
//        model: Model
//    ): String {
//        val photos = photoService.getPhotosPaginated(page, size)
//        model.addAttribute("photos", photos.content)
//        model.addAttribute("currentPage", page)
//        model.addAttribute("totalPages", photos.totalPages)
//        return "photo-list"
//    }

}