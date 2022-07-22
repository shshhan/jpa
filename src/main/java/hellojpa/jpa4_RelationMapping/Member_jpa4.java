package hellojpa.jpa4_RelationMapping;

import javax.persistence.*;

@Entity
public class Member_jpa4 {

    @Id @GeneratedValue
    @Column(name = "MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    /**
     * 양방향 매핑에서는 객체의 두 관계 중 하나를 연관관계의 주인으로 지정해야한다.
     * 연관관계의 주인만 FK를 등록 및 수정할 수 있고, 주인이 아닌 쪽은 읽기만 가능하다.
     * FK가 있는 곳을 주인으로 정하는 것이 좋고, 주인이 아닌 쪽에서 mappedBy 속성을 사용한다.
     */
    @ManyToOne(fetch = FetchType.LAZY)  //지연로딩 전략
    @JoinColumn(name = "TEAM_ID")
    private Team_jpa4 team;     //연관관계의 주인

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