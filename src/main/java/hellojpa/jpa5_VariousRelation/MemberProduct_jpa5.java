package hellojpa.jpa5_VariousRelation.jpa4_RelationMapping;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberProduct_jpa5 {

    /**
     * 다대다 해소를 위해 승격시킨 엔티티일지라도 PK는 인조키를 사용하는 것이 추후 비즈니스 로직 변경 등에 대처가 유연하다.
     */
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Member_jpa5 member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Product_jpa5 product;

    private int count;
    private int price;

    private LocalDateTime orderDateTime;
}
