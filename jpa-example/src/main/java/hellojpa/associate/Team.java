package hellojpa.associate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {
    @Id @GeneratedValue
    @Column(name = "TEAM_ID")
    private Long id;
    private String name;

    @OneToMany(mappedBy = "team") //일대다(내가 일), AssociateMember에 team 으로 매핑이 되어있다고 명시
    //AssociateMember는 team으로써, Team은 members로써 상호 단방향 연관관계를 맺고 있음 (이걸 양방향이라고 하기로 함)
    //(DB의 연관관계는 FK로 연결된 방향이 없는 연관관계)
    //AssociateMember와 Team 이 변경될 때 team, members 둘 중 어떤 값을 기준으로 FK를 update 할 것인지 ?
    // == 연관관계의 주인을 정해야 함
    // 연관관계의 주인만이 외래 키를 등록, 수정할 수 있고 주인이 아닌쪽은 읽기만 가능하다.
    //(mappedBy = "연관관계의 주인 컬럼") 속성을 사용해 지정한다
    //이번 경우에는 AssociateMember의 team이 연관관계의 주인이며 *** Team의 members는 읽기만 가능 ***
    //team에만 값을 넣어도 members에도 적용됨
    //  -> 근데?~ team에만 값을 넣으면 em.flush 되기 전에 members 값을 조회할 수 없다
    //  (flush 된 후에 members를 조회하면 DB에서 select한 결과를 가져와서 보여줌)
    //  그냥 둘 다 넣어주는게 좋다
    //권장 : 외래 키가 있는 곳을 주인으로 정하라 (1:N 관계에서 N)
    private List<AssociateMember> members = new ArrayList<>();

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

    public List<AssociateMember> getMembers() {
        return members;
    }

    public void setMembers(List<AssociateMember> member) {
        this.members = member;
    }
}
