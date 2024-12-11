//import endterm.model.User
//import javafx.concurrent.Task
//import org.springframework.data.domain.Page
//import org.springframework.data.domain.Pageable
//import org.springframework.data.jpa.repository.JpaRepository
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.query.Param
//
//interface TaskRepository : JpaRepository<User, Long> {
//
//    @Query(
//        """
//        SELECT t FROM User t
//        WHERE (:name IS NULL OR t.name LIKE %:name%)
//        AND (:minPriority IS NULL OR t.priority >= :minPriority)
//        AND (:maxPriority IS NULL OR t.priority <= :maxPriority)
//        AND (:categories IS NULL OR t.category IN :categories)
//        """
//    )
//    fun findByFilters(
//        @Param("name") name: String?,
//        @Param("minPriority") minPriority: Int?,
//        @Param("maxPriority") maxPriority: Int?,
//        @Param("categories") categories: List<String>?,
//        pageable: Pageable
//    ): Page<User>
//}
