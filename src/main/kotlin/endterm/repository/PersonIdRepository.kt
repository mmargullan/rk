package endterm.repository

import endterm.model.PersonId
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonIdRepository: JpaRepository<PersonId, Long> {
}