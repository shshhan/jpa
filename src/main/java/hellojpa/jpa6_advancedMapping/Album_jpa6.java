package hellojpa.jpa6_advancedMapping;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("a")    //DTYPE에 들어가는 value를 설정. 기본값은 클래스이름
public class Album_jpa6 extends Item_jpa6{

    private String artist;

}
