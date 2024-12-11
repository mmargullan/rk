package endterm.model

import javax.persistence.*

@Entity
@Table(name = "users")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null

    @Column(unique = true)
    var username: String? = null

    @Column
    var password: String? = null

    @Column
    var role: String? = null

}