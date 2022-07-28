package hellojpa.jpa5_VariousRelation.jpa4_RelationMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product_jpa5 {

    @Id @GeneratedValue
    private Long id;

    private String name;


    /**
     * 다대다 관계가 편해보이지만 실무에서 사용하기에는 한계가 있다.
     * 1. 다대다 관계 해소를 위한 연결 테이블이 자동으로 생성되는데 이 테이블에 추가적인 컬럼을 생성할 수 없다.
     * 2. 보이지 않는 테이블이기 때문에 쿼리가 복잡하게 생성된다.
     * ==> 연결 테이블을 엔티티로 승격시켜 다대다 관계를 일대다, 다대일 관계로 해소시킨다.
     */
    @ManyToMany(mappedBy = "products")
    private List<Member_jpa5> members = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<MemberProduct_jpa5> memberProducts = new ArrayList<>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
