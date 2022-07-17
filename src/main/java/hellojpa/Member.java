package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity //기본 생성자 필수
//@Table(name = "USER") //테이블명이 다를 경우 테이블명 직접 맵핑
public class Member {

    @Id
    private Long id;

    @Column(name = "name", unique = true, length = 10)    //컬럼명이 다를 경우 컬럼명 직접 맵핑, unique, length 등 제약조건 가능
    private String name;

    public Member() {
    }

    public Member(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
}
