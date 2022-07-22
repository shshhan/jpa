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
}