package hellojpa.jpa7_ProxyAndRelation;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

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

    public void compareTypeOfProxyAndEntity() {
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
            System.out.println("m1.getClass() == m2.getClass() ? " + (m1.getClass() == m2.getClass())); //false
            System.out.println("m2.getClass() == m3.getClass() ? " + (m1.getClass() == m3.getClass())); //false

            compareType(m1, m2);
            compareType(m2, m3);

            /**
             * 따라서 객체의 타입을 비교할 때는 instanceof를 써야한다.
             */
            System.out.println("m1 instance of Member_jpa7 ? " + (m1 instanceof Member_jpa7)); //true
            System.out.println("m2 instance of Member_jpa7 ? " + (m2 instanceof Member_jpa7)); //true
            System.out.println("m3 instance of Member_jpa7 ? " + (m3 instanceof Member_jpa7)); //true


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

    public void getProxyAfterEntity() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member_jpa7 member1 = new Member_jpa7();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            //================================================

            Member_jpa7 findMember = em.find(Member_jpa7.class, member1.getId());
            System.out.println("findMember = " + findMember.getClass());

            /**
             * em.getReference로 프록시 객체를 호출했으나 이미 영속성 컨텍스트에 찾는 엔티티가 있으면 실제 객체를 return
             * jpa는 같은 트랜잭션 내 동일성을 보장하기 때문.
             */

            Member_jpa7 refMember = em.getReference(Member_jpa7.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());  //프록시가 아닌 실제 객체의 클래스

            System.out.println("findMember == refMember ? " + (findMember==refMember)); //true
            System.out.println("findMember.getClass() == refMember.getClass() ? " + (findMember.getClass()==refMember.getClass())); //true

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void getEntityAfterProxy() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member_jpa7 member1 = new Member_jpa7();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            //================================================

            Member_jpa7 refMember = em.getReference(Member_jpa7.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());  //프록시가 아닌 실제 객체의 클래스

            /**
             * 반대로 em.find로 실제 객체를 호출했는데 동일 트랜잭션 내에 프록시 객체가 있다면 프록시 객체를 return
             * jpa는 같은 트랜잭션 내 동일성을 보장하기 때문.
             */
            Member_jpa7 findMember = em.find(Member_jpa7.class, member1.getId());
            System.out.println("findMember = " + findMember.getClass());

            System.out.println("findMember == refMember ? " + (findMember==refMember)); //true
            System.out.println("findMember.getClass() == refMember.getClass() ? " + (findMember.getClass()==refMember.getClass())); //true

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void exceptionAfterDetach() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member_jpa7 member1 = new Member_jpa7();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            //================================================

            Member_jpa7 refMember = em.getReference(Member_jpa7.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());  //프록시가 아닌 실제 객체의 클래스

            System.out.println("isLoaded_1 : " + emf.getPersistenceUnitUtil().isLoaded(refMember));   //프록시 객체 초기화 여부 확인
            Hibernate.initialize(refMember); //하이버네이트가 지원하는 강제 초기화.
            System.out.println("isLoaded_2 : " + emf.getPersistenceUnitUtil().isLoaded(refMember));   //프록시 객체 초기화 여부 확인

//            em.detach(refMember);
            em.clear();

            refMember.getUsername();    //프록시 초기화 시점

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void fetchTypeLazyAndEager() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team_jpa7 team = new Team_jpa7();
            team.setName("teamA");
            em.persist(team);

            Member_jpa7 member1 = new Member_jpa7();
            member1.setUsername("member1");
            team.addMember(member1);
            em.persist(member1);


            em.flush();
            em.clear();

            //================================================

            Member_jpa7 m = em.find(Member_jpa7.class, member1.getId());
            System.out.println("m : " + m.getClass());
            /**
             * LAZY : Join 없이 Member의 값만 조회하기 때문에 프록시 객체로 조회된다.
             * EAGER : Join 하여 조회하기 때문에 엔티티 객체로 조회된다.
             */
            System.out.println("m.team : " + m.getTeam().getClass());

            System.out.println("========");
            /**
             * LAZY : 지연로딩 된 객체를 프록시로 가져온 뒤 값을 사용하는 시점에 프록시 초기화
             * --> Select로 Team 객체를 가져온 뒤 System.out.println 수행
             * EAGER : 이미 값을 갖고 있기 때문에 바로 System.out.println 수행
             */
            System.out.println("m.team.name : " + m.getTeam().getName());
            System.out.println("========");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

    }

    public void cautionOfFetchTypeEager() {
        /**
         * 즉시로딩 사용시 주의
         * 1. 여러 테이블이 Join 되어 내가 예상치 못한 SQL이 수행될 수 있다.
         * --> 성능 저하 이슈 발생 가능.
         * 2. JPQL 사용시 N+1 이슈 발생.
         */

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team_jpa7 team = new Team_jpa7();
            team.setName("teamA");
            em.persist(team);

            Team_jpa7 team2 = new Team_jpa7();
            team2.setName("teamB");
            em.persist(team2);

            Member_jpa7 member1 = new Member_jpa7();
            member1.setUsername("member1");
            team.addMember(member1);
            em.persist(member1);

            Member_jpa7 member2 = new Member_jpa7();
            member2.setUsername("member2");
            team2.addMember(member2);
            em.persist(member2);

            em.flush();
            em.clear();

            //================================================

//            Member_jpa7 m = em.find(Member_jpa7.class, member2.getId());
            List<Member_jpa7> members = em.createQuery("select m from Member_jpa7 m", Member_jpa7.class)
                    .getResultList();
            /**
             * JPQL의 동작 과정
             * SQL : SELECT * FROM Member_jpa7
             * SQL : SELECT * FROM Team_jpa7 where TEAM_ID = xxx
             * --> N+1 이슈 발생
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
