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

        Jpa2_PersistenceContext jpa2 = new Jpa2_PersistenceContext(emf);
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
        jpa2.detached();


        emf.close();


    }

}