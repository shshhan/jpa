package hellojpa.jpa_before4;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class Jpa1_GetStart {

    public EntityManagerFactory emf;

    public Jpa1_GetStart(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void basicCRUD() {
        //트랜잭션 단위마다 EntityManager를 생성해서 로직 처리(쓰레드간 공유X)
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            /** 등록 */
            //            Member member = new Member();
            //            member.setId(2L);
            //            member.setName("Hello B");
            //            em.persist(member);

            /** 조회 */
            Member findMember = em.find(Member.class, 1L);
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.id = " + findMember.getName());

            /** JPQL
             * 조회 시 테이블이 아닌 엔티티 객체를 대상으로 검색하는 객체 지향 쿼리
             * */
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(10)
                    .getResultList();

            result.forEach(m -> System.out.println("member.name = " + m.getName()));


            /** 삭제 */
            //            em.remove(findMember);

            /** 수정 */
            findMember.setName("HelloJPA"); //JPA가 트랜잭션을 커밋하는 시점에 엔티티에 변경이 생기면 자동으로 update 쿼리를 생성


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }

}
