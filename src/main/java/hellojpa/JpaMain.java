package hellojpa;

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

        /**
         * 영속성 관리 - 내부 동작 방식
         */
        Jpa2_PersistenceContext jpa2 = new Jpa2_PersistenceContext(emf);
        jpa2.persistenceExample();



    }

}