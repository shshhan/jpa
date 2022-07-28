package hellojpa.jpa5_VariousRelation.jpa4_RelationMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member_jpa5 {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    /**
     * 일대다 양방향이 공식적으로 존재하지는 않음.
     * 읽기 전용 필드를 사용해서 양방향 처럼 사용은 가능.
     */
    @ManyToOne
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)    //읽기 전용으로 맵핑
    private Team_jpa5 team;

    /**
     * 일대일 맵핑
     */
    @OneToOne
    @JoinColumn(name = "LOCKER_ID")
    private Locker_jpa5 locker;

    /**
     * 다대다 맵핑
     */
    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT")
    private List<Product_jpa5> products = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<MemberProduct_jpa5> memberProducts = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}