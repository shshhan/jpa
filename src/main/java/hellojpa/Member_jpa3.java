package hellojpa;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Member")
public class Member_jpa3 {

    @Id
    private Long id;

    @Column(name = "name", updatable = false, nullable = false)  //DB 컬럼명이 다를 경우, 업데이트 금지, notnull 제약조건,
    private String username;

    @Column(precision = 10, scale = 3)  //BigDecimal, BigInteger에서 사용. precision : 소수점 포함 자릿 수, scale : 소수점 이하 자릿 수
    private Integer age;

    @Enumerated(EnumType.STRING)    //EnumType.ORDINAL이 기본값. 하지만 ORDINAL로 쓰면 순서값이 들어가서 추후 Enum 수정 시 정확한 데이터를 알 수 없음.
    private RoleType roleType;

//    @Temporal(TemporalType.TIMESTAMP)
//    private Date createdDate;
//
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date lastModifiedDate;

    // java8 이상에서는 LocalDate를 지원하고, 최신 하이버네이트에서는 LocalDate 사용 시 별도의 어노테이션 맵핑 없이 처리
    private LocalDate testLocalDate;
    private LocalDateTime testLocalDateTime;

    @Lob    //맵핑하는 필드 타입이 문자면 CLOB, 나머지는 BLOB으로 맵핑됨.
    private String description;

    @Transient  //맵핑에서 제외
    private int temp;

    public Member_jpa3() {
    }
}