package hellojpa.jpa7_ProxyAndRelation;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Jpa7_ProxyAndRelation {

    public EntityManagerFactory emf;

    public Jpa7_ProxyAndRelation(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void getReferenceForProxy() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member_jpa7 member = new Member_jpa7();
            member.setUsername("member1");
            em.persist(member);

            em.flush();
            em.clear();

            //================================================
            /**
             * 프록시 객체
             * 1. 실제 클래스를 상속 받아서 만들어지기 때문에 사용하는 입장에서는 실제 객체인지 프록시 객체인지 구분하지 않고 사용해도 된다.(이론상)
             * 2. 프록시 객체는 실제 객체의 참조를 보관하고 있다.
             * 3. 프록시 객체를 호출하면 프록시 초기화 후 실제 객체의 메서드를 호출한다.
             */

//            Member_jpa7 findMember = em.find(Member_jpa7.class, member.getId());
            Member_jpa7 findMember = em.getReference(Member_jpa7.class, member.getId());//프록시 객체
            System.out.println("findMember: " + findMember.getClass());
            System.out.println("findMember.id : " + findMember.getId());    //이미 알고있는 값에 대해서는 초기화 하지 않음.
            System.out.println("findMember.username : " + findMember.getUsername());    //프록시 객체 초기화

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void compareTypeOfProxyAndReal() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member_jpa7 member1 = new Member_jpa7();
            member1.setUsername("member1");
            em.persist(member1);

            Member_jpa7 member2 = new Member_jpa7();
            member2.setUsername("member2");
            em.persist(member2);

            Member_jpa7 member3 = new Member_jpa7();
            member3.setUsername("member3");
            em.persist(member3);

            em.flush();
            em.clear();

            //================================================

            Member_jpa7 m1 = em.find(Member_jpa7.class, member1.getId());
            Member_jpa7 m2 = em.find(Member_jpa7.class, member2.getId());
            Member_jpa7 m3 = em.getReference(Member_jpa7.class, member3.getId());

            /**
             * 프록시 객체는 실제 객체를 상속 받았기 때문에 타입을 == 으로 비교하면 다른 타입으로 판단된다.
             */
            System.out.println("m1.getClass() == m2.getClass() ? " + (m1.getClass() == m2.getClass()));
            System.out.println("m2.getClass() == m3.getClass() ? " + (m1.getClass() == m3.getClass()));

            compareType(m1, m2);
            compareType(m2, m3);

            /**
             * 따라서 객체의 타입을 비교할 때는 instanceof를 써야한다.
             */
            System.out.println("m1 instance of Member_jpa7 ? " + (m1 instanceof Member_jpa7));
            System.out.println("m2 instance of Member_jpa7 ? " + (m2 instanceof Member_jpa7));
            System.out.println("m3 instance of Member_jpa7 ? " + (m3 instanceof Member_jpa7));


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    private void compareType(Member_jpa7 mA, Member_jpa7 mB) {
        System.out.println("mA.getClass() == mB.getClass() ? " + (mA.getClass() == mB.getClass()));
    }

}
