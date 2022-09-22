package hellojpa.jpa8_ValueType;

import javax.persistence.*;

@Entity
public class Member_jpa8 {

    @Id
    @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    //기간 Period
    @Embedded
    private Period workPeriod;

    //주소
    @Embedded
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({   //Embedded Type이 한 엔티티에서 중복된다면 컬럼명 속성을 재정의해야한다.
            @AttributeOverride(name="city", column = @Column(name = "WORK_CITY"))
            , @AttributeOverride(name="street", column = @Column(name = "WORK_STREET"))
            , @AttributeOverride(name="zipcode", column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress = null; //관련 컬럼들을 모두 null로 저장

    /**
     * Embedded 타입의 장점
     * 높은 응집도, 재사용성
     * 해당 값 타입만 사용하는 의미있는 메서드 생성 가능
     * 임베디드 타입을 포함한 모든 값 타입은 값 타입을 소유한 엔티티 생명주에 의존적
     */

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
