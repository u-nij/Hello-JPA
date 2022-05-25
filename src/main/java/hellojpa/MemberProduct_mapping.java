package hellojpa;

import javax.persistence.*;

@Entity
public class MemberProduct_mapping {

    @Id @GeneratedValue
    private Long Id;
    /*
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member_mapping member_N_M_limit;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product_mapping product_N_M_limit;
     */
}
