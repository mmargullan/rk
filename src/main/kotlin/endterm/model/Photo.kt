package endterm.model

import javax.persistence.*

@Entity
@Table(name = "photos")
class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    var id: Long? = null

    @OneToOne
    @JoinColumn(name = "user_id")
    var user: User? = null

    @Basic(fetch = FetchType.LAZY)
    @Column
    @Lob
    var photo: ByteArray? = null

    @Column
    var name: String? = null

}