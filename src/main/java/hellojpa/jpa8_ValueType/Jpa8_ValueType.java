package hellojpa.jpa8_ValueType;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Jpa8_ValueType {

    public EntityManagerFactory emf;

    public Jpa8_ValueType(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void useEmbeddedType() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member_jpa8 member = new Member_jpa8();
            member.setUsername("hello");
            member.setHomeAddress(new Address("city", "street", "10001"));
            member.setWorkPeriod(new Period());

            em.persist(member);
            /**
             * Embeded 타입을 사용하기 전과 후에 매핑하는 테이블은 같다.
             * 주소, 좌표, 기간 등을 클래스로 뽑아두면 내부적으로 메서드를 구현하는 등 이점이 많음.
             * 객체와 테이블을 아주 세밀하게 매핑하는 것이 가능.
             */

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }


}
