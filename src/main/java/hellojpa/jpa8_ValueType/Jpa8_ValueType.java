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

    public void sideEffectOfValueType() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Address address = new Address("city", "street", "10001");

            Member_jpa8 member1 = new Member_jpa8();
            member1.setUsername("hello");
            member1.setHomeAddress(address);
            em.persist(member1);

            Member_jpa8 member2 = new Member_jpa8();
            member2.setUsername("hello");
            member2.setHomeAddress(address);
            em.persist(member2);

//            member1.getHomeAddress().setCity("newCity");
            /**
             * Side Effect 발생 : update 쿼리가 두번 발생
             * ==> Embedded Type과 같은 값 타입은 실제 인스턴스인 값을 여러 엔티티에서 공유하면 위험
             */

            member2.setHomeAddress(new Address(address.getCity(), address.getStreet(), address.getZipcode()));

//            member1.getHomeAddress().setCity("3rdCity");
            /**
             * Side Effect를 방지하기 위해서는 항상 값을 복사해서 사용해야한다.
             * --> Primitive Type은 = 연산자로 복사하면 값이 복사되지만, 직접 정의한 값 타입인 Embedded Type은 객체 타입이다.
             * --> 객체 타입은 = 연산자로 대입할 경우 값이 아닌 해당 인스턴스의 참조 값이 대입되기 때문에, Side Effect를 막을 수 없다.
             * ==> 값 타입은 생성 후 절대 값을 변경할 수 없는 불변 객체(immutable Object)로 설계해야한다.
             * Setter를 없애거나 내부적으로만 사용하도록 private setter tjsdjs
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
