package endterm.model

import javax.persistence.*

@Entity
@Table(name = "tasks")
class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null

    @Column(unique = true)
    var title: String? = null

    @Column
    var description: String? = null

    @Column
    var completed: Boolean? = null

}