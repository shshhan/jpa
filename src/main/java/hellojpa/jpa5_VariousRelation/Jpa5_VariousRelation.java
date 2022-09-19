package hellojpa.jpa5_VariousRelation.jpa4_RelationMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Jpa5_VariousRelation {

    public EntityManagerFactory emf;

    public Jpa5_VariousRelation(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void oneToManyEx() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Member_jpa5 member = new hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Member_jpa5();
            member.setUsername("member1");

            em.persist(member);

            hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Team_jpa5 team = new hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Team_jpa5();
            team.setName("teamA");

            /**
             * 연관관계의 주인은 Team. DB테이블의 FK(TEAM_ID)는 Member테이블에 존재.
             * --> team에서 update를 했는데 왜 member 테이블에 update 쿼리가 발생.
             * --> 일대다 관계에서 연관관계의 주인인 1에서 데이터 수정 시 FK를 갖고있는 반대편 테이블에서 쿼리 수행.
             * ==> 실제 조회할 필요가 없더라도 객체지향을 조금 포기하고 연관관계의 주인을 다 쪽으로 객체를 설계하여 다대일 단방향(혹은 양방향) 방식으로 구현하는 것이 유지보수 및 성능에 용이.
             */
            team.getMembers().add(member);

            em.persist(team);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

}
