package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member_mapping extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    /*
    // 다대일 단방향, 양방향
//    @ManyToOne // Member 입장에서 many, Team 입장에서 one
//    @JoinColumn(name = "TEAM_ID")   // MEMBER 테이블의 'TEAM_ID' 칼럼과 매핑
//    private Team_mapping team_N_1;

    // 일대다 단방향
    // X

    // 일대다 양방향
    @ManyToOne
    @JoinColumn(name="TEAM_ID", insertable = false, updatable = false)
    private Team_mapping team_1_N_two_way;

    // 주테이블에 외래키, 일대일 단방향, 양방향
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker_mapping locker_1_1;

    // 다대다 단방향, 양방향
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product_mapping> products_N_M = new ArrayList<>();

    // 다대다 한계 극복
    @OneToMany(mappedBy = "member_N_M_limit")
    private List<MemberProduct_mapping> memberProduct_N_M_limit = new ArrayList<>();
    */

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}