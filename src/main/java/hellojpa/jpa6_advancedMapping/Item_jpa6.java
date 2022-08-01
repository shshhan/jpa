package hellojpa.jpa6_advancedMapping;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) //Joined전략
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //SingleTable전략
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //TablePerClass전략
@DiscriminatorColumn(name = "DIS_TYPE")    //DTYPE 컬럼을 추가해주는 어노테이션. SingleTable 전략에서는 DTYPE이 필수이기 때문에 어노테이션을 추가하지 않아도 자동 생성됨.
public abstract class Item_jpa6 extends BaseEntity{

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

    /**
     * Joined
     * 장점 : 데이터가 정규화 되어있다, 외래 키 참조 무결성 제약조건을 활용할 수 있다, 저장공간을 효율적으로 사용할 수 있다.
     * 단점 : Select 시 Join을 사용하게 되고 Insert시 SQL을 두번 호출하는 등 성능 저하가 있다, 쿼리문 자체가 복잡해진다.
     * => 큰 단점은 아니고, 객체 모델링과도 일치하게 되므로 정으로 보는 것이 좋다.
     *
     * SingleTable
     * 장점 : Join이 필요없어 조회 성능이 빠르다, 쿼리가 단순하다.
     * 단점 : 자식 엔티티들의 고유 컬럼은 모두 null을 허용해야 한다, 데이터가 많아지면 오히려 조회 성능이 떨어질 수 있다.
     *
     * TablePerClass
     * DB 설계자와 ORM 전문가 양 쪽 모두가 추천하지 않는 방식
     * 장점 : 서브 타입을 명확하게 구분해서 처러할 때는 효과적
     * 단점 : 여러 테이블 함께 조회시 UNION을 사용하여 성능 저하, 로직 변경이나 컬럼 추가시 작업할 코드 변경이 많다.
     */


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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


}
