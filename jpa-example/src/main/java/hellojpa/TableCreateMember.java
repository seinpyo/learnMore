package hellojpa;

import javax.persistence.*;

//테이블 매핑 전략 : 모든 DB 에서 사용 가능하다는 장점이 있으나 성능이 떨어진다

//** 기본키 제약조건 : NOT NULL, 유일성, 불변성
// 이 조건을 만족하는 자연키(주민번호, 전화번호 등)를 찾기 어려우므로 대리키(대체키) 사용 권장
// Long 형 + 대체키 + 키 생성전략 을 사용 하는 것이 가장 권장되는 식별자 전략이다

//@Entity
@TableGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        table = "MY_SEQUENCES",     //테이블 이름
        pkColumnValue = "MEMBER_SEQ",   //pk지정
        allocationSize = 1
        )
public class TableCreateMember {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
