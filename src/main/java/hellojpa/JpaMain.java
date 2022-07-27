package hellojpa;

import hellojpa.jpa4_RelationMapping.Jpa4_RelationMapping;
import hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Jpa5_VariousRelation;

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

        Jpa5_VariousRelation jpa5 = new Jpa5_VariousRelation(emf);
        /**
         * 일대다 관계
         */
        jpa5.oneToManyEx();

        emf.close();


    }

}