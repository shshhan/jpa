package hellojpa.jpa7_ProxyAndRelation;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team_jpa7 {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")   //@JoinColumn 없으면 JoinTable 방식을 사용함.
    private List<Member_jpa7> members = new ArrayList<>();

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

    public List<Member_jpa7> getMembers() {
        return members;
    }

    public void setMembers(List<Member_jpa7> members) {
        this.members = members;
    }

    public void addMember(Member_jpa7 member) {
        member.setTeam(this);
        this.members.add(member);
    }
}