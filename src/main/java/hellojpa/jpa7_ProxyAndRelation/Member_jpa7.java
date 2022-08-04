package hellojpa.jpa7_ProxyAndRelation;

import javax.persistence.*;

@Entity
public class Member_jpa7 {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    /**
     * 일대다 양방향이 공식적으로 존재하지는 않음.
     * 읽기 전용 필드를 사용해서 양방향 처럼 사용은 가능.
     */
//    @ManyToOne(fetch = FetchType.LAZY)  //지연로딩
    @ManyToOne(fetch = FetchType.EAGER)  //즉시로딩
    @JoinColumn(name = "TEAM_ID", insertable = false, updatable = false)    //읽기 전용으로 맵핑
    private Team_jpa7 team;

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

    public Team_jpa7 getTeam() {
        return team;
    }

    public void setTeam(Team_jpa7 team) {
        this.team = team;
        team.getMembers().add(this);
    }
}