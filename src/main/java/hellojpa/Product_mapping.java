package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product_mapping {

    @Id @GeneratedValue
    @Column(name = "PRODUCT_ID")
    private Long id;

    private String name;
    /*
    // 다대다 양방향
    @ManyToMany(mappedBy = "products_N_M")
    private List<Member_mapping> members_N_M_two_way = new ArrayList<>();

    // 다대다 한계 극복
    @OneToMany(mappedBy = "product_N_M_limit")
    private List<MemberProduct_mapping> memberProduct_N_M_limit = new ArrayList<>();
     */
}
