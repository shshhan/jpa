package hellojpa;

import hellojpa.jpa7_ProxyAndRelation.Jpa7_ProxyAndRelation;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class JpaMain {

    public static void main(String[] args) {
        //factory는 애플리케이션 로딩 시점에 하나만 만들고
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        /**
         * JPA 시작하기
         */
//        Jpa1_GetStart jpa1 = new Jpa1_GetStart(emf);
//        jpa1.basicCRUD();

//        Jpa2_PersistenceContext jpa2 = new Jpa2_PersistenceContext(emf);
        /**
         * 영속성 관리 - 내부 동작 방식
         */
//        jpa2.persistenceExample();

        /**
         * 쓰기 지연
         */
//        jpa2.writeBehind();

        /**
         * dirty checking
         */
//        jpa2.dirtyChecking();

        /**
         * flush
         */
//        jpa2.flush();

        /**
         * detach
         */
//        jpa2.detached();

//        Jpa3_EntityMapping jpa3 = new Jpa3_EntityMapping(emf);
        /**
         * @Id, @GeneratedValue
         */
//        jpa3.idMappingEx();

//        Jpa4_RelationMapping jpa4 = new Jpa4_RelationMapping(emf);
        /**
         * 양방향 맵핑의 좋은, 안좋은 예
         */
//        jpa4.notGoodRelationMapping();
//        jpa4.goodRelationMapping();

        /**
         * 양방향 맵핑시 연관관계의 주인에 값을 입력해야한다.
         */
//        jpa4.setValueOnOwner();

//        Jpa5_VariousRelation jpa5 = new Jpa5_VariousRelation(emf);
        /**
         * 일대다 관계
         */
//        jpa5.oneToManyEx();

//        Jpa6_AdvancedMaping jpa6 = new Jpa6_AdvancedMaping(emf);
        /**
         * 조인 전략 -> 각각 테이블로 변환
         */
//        jpa6.inheritanceStrategy();

        Jpa7_ProxyAndRelation jpa7 = new Jpa7_ProxyAndRelation(emf);
        /**
         * 프록시 객체 호출
         */
//        jpa7.getReferenceForProxy();

        /**
         * 프록시 객체와 실제 객체 사이의 비교에는 instanceof 활용
         */
//        jpa7.compareTypeOfProxyAndEntity();

        /**
         * 프록시 객체 호출시 실제 객체가 영속성 컨텍스트에 존재하고 있으면 실제 객체를 반환
         */
//        jpa7.getProxyAfterEntity();

        /**
         * 실제 객체 호출 시에도 프록시 객체가 영속성 컨텍스트에 존재하고 있으면 프록시 객체를 반환
         */
//        jpa7.getEntityAfterProxy();

        /**
         * 준영속 상태 시 프록시를 초기화 하면 org.hibernate.LazyInitializationException 예외 발생
         */
//        jpa7.exceptionAfterDetach();

        /**
         * 즉시로딩과 지연로딩
         */
//        jpa7.fetchTypeLazyAndEager();

        /**
         * 즉시로딩 사용시 주의점
         */
//        jpa7.cautionOfFetchTypeEager();

        /**
         * 영속성 전이 & 고아 객체
         */
        jpa7.cascadeTypeAndOrpahnRemoval();


        emf.close();


    }

}