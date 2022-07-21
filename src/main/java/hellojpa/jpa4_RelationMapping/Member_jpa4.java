package hellojpa.jpa4_RelationMapping;

import javax.persistence.*;

@Entity
public class Member_jpa4 {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)  //지연로딩 전략
    @JoinColumn(name = "TEAM_ID")
    private Team_jpa4 team;

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

    public Team_jpa4 getTeam() {
        return team;
    }

    public void setTeam(Team_jpa4 team) {
        this.team = team;
    }
}