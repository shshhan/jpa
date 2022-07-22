package hellojpa.jpa4_RelationMapping;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.List;

public class Jpa4_RelationMapping {

    public EntityManagerFactory emf;

    public Jpa4_RelationMapping(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void notGoodRelationMapping() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Team_jpa4 team = new Team_jpa4();
            team.setName("TeamA");
            em.persist(team);

            Member_jpa4_badEx member = new Member_jpa4_badEx();
            member.setUsername("memberA");
            member.setTeamId(team.getId());
            em.persist(member);

            Member_jpa4_badEx findMember = em.find(Member_jpa4_badEx.class, member.getId());
            Long findTeamId = findMember.getTeamId();
            Team_jpa4 findTeam = em.find(Team_jpa4.class, findTeamId);

            /**
             * 테이블에 맞추어 데이터 중심으로 모델링하면 객체간 연관관계를 만들 수 없다.
             */

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

    public void goodRelationMapping() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{

            Team_jpa4 team = new Team_jpa4();
            team.setName("TeamA");
            em.persist(team);

            Member_jpa4 member = new Member_jpa4();
            member.setUsername("memberA");
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            Member_jpa4 findMember = em.find(Member_jpa4.class, member.getId());
            List<Member_jpa4> members = findMember.getTeam().getMembers();  //양방향 연관관계

            for(Member_jpa4 m : members){
                System.out.println("m = " + m.getUsername());
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

}
