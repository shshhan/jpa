package hellojpa.jpa_before4;

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
    }

    public void flush() {
        /**
         * 수정된 엔티티 쓰기 지연 SQL(영속성 컨텍스트의 변경내용)들을 DB와 동기화
         * 플러시가 발생하는 경우
         * 1. em.flush();
         * 2. tx.commit;
         * 3. em.createQuery() - JPQL은 무조건 SQL로 번역되어 DB에 쿼리를 날리게 되는데 flush가 되지 않는다면 동일 트랜잭션내 쓰기 지연 SQL 저장소에 담긴 데이터를 확인 불가능 하기 때문
         * Flush가 수행되어도 1차 캐시가 날아가지는 않는다. 쓰기 지연과 변경 감지로 쓰기 지연 SQL 저장소에 담겨있던 쿼리를 DB로 날림
         */
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member(200L, "member200");
            em.persist(member);

//            em.flush();
            TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
            List<Member> members = query.getResultList();

            System.out.println("members : " + members);

            members.forEach(m -> System.out.println(m.getName()));

            System.out.println("=================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

    public void detached() {
        /**
         *
         */
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            //영속
            Member member = em.find(Member.class, 150L);
            member.setName("AAAA");

            //준영속 - 더이상 JPA에서 관리하지 않음
            em.detach(member);
            em.clear(); //영속성 컨텍스트 전체를 초기화

            System.out.println("=================");

            Member member2 = em.find(Member.class, 150L);   //em.clear()로 영속성 컨텍스트가 초기화되었기 때문에 DB에서 select

            tx.commit();    //member를 detach했기 때문에 dirtychecking이 일어나지 않아 update SQL X
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

}
