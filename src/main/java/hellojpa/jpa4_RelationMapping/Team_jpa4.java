package hellojpa.jpa4_RelationMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team_jpa4 {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team")
    private List<Member_jpa4> members = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Member_jpa4> getMembers() {
        return members;
    }

    public void setMembers(List<Member_jpa4> members) {
        this.members = members;
    }


    /**
     * 양방향 연관관계의 편의를 위한 메서드.
     * 이 메서드 혹은 Member의 changeTeam 둘 중 하나만 작성해서 한쪽에서 추가시 반대쪽도 추가되도록 설정
     * @param member
     */
    public void addMember(Member_jpa4 member) {
        member.setTeam(this);
        members.add(member);
    }
}