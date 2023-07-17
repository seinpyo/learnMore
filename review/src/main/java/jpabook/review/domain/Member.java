package jpabook.review.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "memberId")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy= "member")  //order 테이블에 있는 member 필드에 의해 mapping 된 것임을 명시
    private List<Order> orders = new ArrayList<>();

}
