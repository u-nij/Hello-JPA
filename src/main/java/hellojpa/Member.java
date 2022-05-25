package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//@Entity
@SequenceGenerator( // SEQUENCE 전략
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
//@TableGenerator(    // TABLE 전략
//        name = "MEMBER_SEQ_GENERATOR",
//        table = "MY_SEQUENCES", // 테이블 이름
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 1)
public class Member {


    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY 전략
    @GeneratedValue(strategy = GenerationType.SEQUENCE, // SEQUENCE 전략
                    generator = "MEMBER_SEQ_GENERATOR")
//    @GeneratedValue(strategy = GenerationType.TABLE, // TABLE 전략
//                    generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    private Integer age;
//
//    @Enumerated
//    private RoleType roleType;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;
//
//    private LocalDate testLocalDate;
//    private LocalDateTime testLocalDateTime;
//
//    @Lob
//    private String description;

    public Member() {}

}
