package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team_mapping {

    @Id
    @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    // 다대일 단방향
    // X

    // 다대일 양방향 (Member가 어떤 Team의 소속인지에 대한 로직이 자주 등장할 경우)
    @OneToMany(mappedBy = "team_N_1")
    private List<Member_mapping> members_N_1_two_way = new ArrayList<>();

    // 일대다 단방향
    @OneToMany
    @JoinColumn(name = "TEAM_ID") // 반대 방향의 관리해주어야 하는 외래 키
    private List<Member_mapping> members_1_N_one_way = new ArrayList<>();

}
