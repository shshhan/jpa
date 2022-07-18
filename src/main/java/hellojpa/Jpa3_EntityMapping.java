package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * 영속성 컨텍스트의 이점
 * 1. 1차 캐시 - 쓰레드간 공유는 안되기 때문에 실질적인 성능상 이점보다는 메커니즘의 이점이 있음.
 * 2. 동일성 보장
 * 3. 트랜잭션을 지원하는 쓰기 지연
 * 4. 변경 감지
 * 5. 지연 로딩
 */
public class Jpa3_EntityMapping {

    public EntityManagerFactory emf;

    public Jpa3_EntityMapping(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void idMappingEx() {
        //트랜잭션 단위마다 EntityManager를 생성해서 로직 처리(쓰레드간 공유X)
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member_jpa3 member_jpa3 = new Member_jpa3();
            member_jpa3.setUsername("cc");

            em.persist(member_jpa3);

            tx.commit();    //커밋 시점에 insert sql 생성
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

}
