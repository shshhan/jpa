package hellojpa.jpa5_VariousRelation.jpa4_RelationMapping;

import javax.persistence.*;

@Entity
public class MemberProduct_jpa5 {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member_jpa5 member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product_jpa5 product;
}
