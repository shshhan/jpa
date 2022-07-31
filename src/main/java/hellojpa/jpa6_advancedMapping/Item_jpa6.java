package hellojpa.jpa6_advancedMapping;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) //Joined전략
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //SingleTable전략
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS) //TablePerClass전략
@DiscriminatorColumn(name = "DIS_TYPE")    //DTYPE 컬럼을 추가해주는 어노테이션. SingleTable 전략에서는 DTYPE이 필수이기 때문에 어노테이션을 추가하지 않아도 자동 생성됨.
public abstract class Item_jpa6 {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private int price;

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
