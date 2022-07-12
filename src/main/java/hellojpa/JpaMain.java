package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        //factory는 애플리케이션 로딩 시점에 하나만 만들고
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //트랜잭션 단위마다 EntityManager를 생성해서 로직 처리
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Member member = new Member();
        member.setId(1L);
        member.setName("Hello A");

        em.persist(member);

        tx.commit();

        em.close();

        emf.close();
    }

}