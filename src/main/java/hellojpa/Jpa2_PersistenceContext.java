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
            Member member1 = em.find(Member.class, 100L);
            System.out.println(member1.getId());
            System.out.println(member1.getName());

            //같은 트랜잭션 내 동일성 보장
            Member member2 = em.find(Member.class, 100L);
            System.out.println(member1 == member2);
            //준영속 - 영속성 컨텍스트에서 분리
//            em.detach(member);

            tx.commit();    //커밋 시점에 insert sql 생성
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public void writeBehind() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //영속
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            //이때 insert sql을 수행하는 것이 아니라 커밋 시점에 sql
            em.persist(member1);
            em.persist(member2);

            System.out.println("=================");

            tx.commit();    //커밋 시점에 insert sql 생성
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

    public void dirtyChecking() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = em.find(Member.class, 150L);
            member.setName("name11");

            System.out.println("=================");

            tx.commit();    //커밋 시점에 update sql 생성
            /**
             * 커밋 시점에 1차캐시에 등록되어있는 스냅샷과 엔티티가 다를 경우 변경을 감지하여 update
             */
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
