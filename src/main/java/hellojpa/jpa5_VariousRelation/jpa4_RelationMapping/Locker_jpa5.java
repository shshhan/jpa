package hellojpa.jpa5_VariousRelation.jpa4_RelationMapping;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Locker_jpa5 {

    @Id @GeneratedValue
    private Long id;

    private String name;

    /**
     * 일대일 맵핑 양방향
     */
    @OneToOne(mappedBy = "locker")
    private Member_jpa5 member;

    /**
     * 일대일 맵핑에서 키의 위치
     * 1. 주 테이블(사용빈도가 잦은 테이블)
     *  + 객체지향 개발자 선호, 주 테이블 하나만 조회해도 대상 테이블 데이터 조회 가능
     *  - 대상 테이블 값이 없으면 FK에 null 허용
     * 2. 대상 테이블
     *  + DBA 선호, 추후 테이블 관계 변경 시 테이블 구조 유지 가능
     *  - 지연 로딩으로 설정해도 항상 즉시 로딩(주 테이블 객체의 필드에 대상 필드 값을 넣기 위해서는 어차피 쿼리를 날려야하기 때문)
     */
}
