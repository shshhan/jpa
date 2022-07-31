package hellojpa.jpa6_advancedMapping;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("m")    //DTYPE에 들어가는 value를 설정. 기본값은 클래스이름
public class Movie_jpa6 extends Item_jpa6{

    private String director;
    private String actor;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActor() {
        return actor;
    }

    public void setActor(String actor) {
        this.actor = actor;
    }
}
