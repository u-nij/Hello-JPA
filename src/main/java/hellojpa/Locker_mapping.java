package hellojpa;

import javax.persistence.*;

@Entity
public class Locker_mapping {

    @Id @GeneratedValue
    @Column(name = "LOCKER_ID")
    private Long id;

    private String name;

    // 주테이블에 외래키, 일대일 양방향
    @OneToOne(mappedBy = "locker_1_1")
    private Member_mapping member_1_1;
}
