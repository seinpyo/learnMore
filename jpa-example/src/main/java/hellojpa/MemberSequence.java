package hellojpa;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1,
        allocationSize = 50  //next 콜 한번에 50개씩 땡겨서 미리 메모리에 올려놓음
)
public class MemberSequence {

    @Id //직접 할당하고 싶을 때는 @Id만 단독으로 사용
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ_GENERATOR")
    //자동생성 (strategy = IDENTITY, SEQUENCE(@SequenceGenerator 필요), TABLE, AUTO)
    //IDENTITY 전략은 em.persist()가 실행되는 시점에 db insert가 실행되고 식별자(생성된 id 값) 를 불러옴
    private Long id;

    @Column(name = "name", nullable = false)
    private String username;

    public MemberSequence() {}

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
}
