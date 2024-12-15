package endterm.model

import javax.persistence.*

@Entity
@Table(name = "person_id")
class PersonId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null

    @Column
    var personID: Long? = null

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null

}