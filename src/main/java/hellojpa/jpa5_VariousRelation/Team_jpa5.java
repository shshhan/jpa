package hellojpa.jpa5_VariousRelation.jpa4_RelationMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team_jpa5 {

    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinColumn(name = "TEAM_ID")   //@JoinColumn 없으면 JoinTable 방식을 사용함.
    private List<hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Member_jpa5> members = new ArrayList<>();

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

    public List<hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Member_jpa5> getMembers() {
        return members;
    }

    public void setMembers(List<hellojpa.jpa5_VariousRelation.jpa4_RelationMapping.Member_jpa5> members) {
        this.members = members;
    }
}