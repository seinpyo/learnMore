package hellojpa;

import javax.persistence.*;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

//@Entity
//JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
//기본생성자 필수
public class Member {
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    private Integer age;

    @Enumerated(EnumType.STRING) //EnumType.ORDINAL 옵션(*디폴트옵션임)은 사용하지 말 것 (integer로 저장됨)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    //LocalDate, LocalDateTime 사용하면 애노테이션 없어도 알아서 매핑 됨
    private LocalDate testLocalDate1;
    private LocalDateTime testLocalDate2;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob    //varchar 범위를 넘는 큰 data content //BLOB(문자 외), CLOB(문자) 매핑
    private String description;

    @Transient //db에 추가 없이 메모리에서만 사용할 변수 => 특정 필드를 컬럼에 매핑하지 않음(매핑 무시)
    public int temp;

    public Member() {}


}
