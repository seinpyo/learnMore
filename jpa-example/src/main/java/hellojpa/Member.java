package hellojpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
//JPA를 사용해서 테이블과 매핑할 클래스는 @Entity 필수
//기본생성자 필수
public class Member {
    @Id
    private Long id;
    private String name;

    public Member() {}
    public Member(Long id, String name) {
        this.id = id;
        this.name = name ;
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
