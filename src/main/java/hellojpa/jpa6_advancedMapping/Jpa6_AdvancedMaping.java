package hellojpa.jpa6_advancedMapping;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Jpa6_AdvancedMaping {

    public EntityManagerFactory emf;

    public Jpa6_AdvancedMaping(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void inheritanceStrategy() {
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            Movie_jpa6 movie = new Movie_jpa6();
            movie.setDirector("aaa");
            movie.setActor("bbb");
            movie.setName("바람과 함께 사라지다");
            movie.setPrice(10000);

            em.persist(movie);
            em.flush();
            em.clear(); //1차 캐시 초기화

            Item_jpa6 findItem= em.find(Item_jpa6.class, movie.getId());
            System.out.println("findMovie = " + findItem);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

    }

}
