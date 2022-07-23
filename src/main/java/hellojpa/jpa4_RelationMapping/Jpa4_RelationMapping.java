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
            List<Member_jpa4> members = findMember.getTeam().getMembers();  //양방향 연관관

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

    public void setValueOnOwner() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Member_jpa4 member = new Member_jpa4();
            member.setUsername("memberA");
            em.persist(member);

            Team_jpa4 team = new Team_jpa4();
            team.setName("TeamA");
//            team.getMembers().add(member);    //연관관계의 주인이 아닌 쪽에 값을 입력하면 정상 작동 X
            em.persist(team);

            member.setTeam(team);   //연관관계의 주인에 값을 입력해야 DB에 정상적으로 값이 들어감

            System.out.println("----------------------");
            /**
             * 영속성 컨텍스트의 1차 캐시에서 값을 가져옴
             * --> Team에 Member 값을 넣어주지 않았으므로 조회 불가능
             */
            Team_jpa4 team_jpa4 = em.find(Team_jpa4.class, team.getId());    //팀 Select
            team_jpa4.getMembers().forEach(members -> {  //Team에 Member가 지연로딩이기 때문에 이 시점에서 Member를 Select
                System.out.println("mebmer name : " + members.getUsername());
            });
            System.out.println("----------------------");

            em.flush();
            em.clear();

            /**
             * em.flush()로 DB와 동기화, em.clear()로 영속성 컨텍스트를 비운 뒤에 select
             * --> Team객체에 넣지 값을 넣지 않았지만 연관관계의 주인인 Member에서 값을 넣고 DB와 동기화 된 이후이므로 값이 제대로 출력된다.
             */
            Team_jpa4 findTeam = em.find(Team_jpa4.class, team.getId());    //팀 Select
            findTeam.getMembers().forEach(members -> {  //Team에 Member가 지연로딩이기 때문에 이 시점에서 Member를 Select
                System.out.println("mebmer name : " + members.getUsername());
            });


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

}
