package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Jpa2_PersistenceContext {

    public EntityManagerFactory emf;

    public Jpa2_PersistenceContext(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void persistenceExample() {
        //트랜잭션 단위마다 EntityManager를 생성해서 로직 처리(쓰레드간 공유X)
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            //영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");

            //영속 컨텍스트에 추가한 뒤 커밋 전 조회해도 1차 캐시에 저장된 값을 불러오기 때문에 select문을 사용하지 않음
            System.out.println(em.find(Member.class, 100L).getName());

            //준영속 - 영속성 컨텍스트에서 분리
//            em.detach(member);

            tx.commit();    //커밋 시점에 sql 생성
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
