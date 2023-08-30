package jpabook.review.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
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

    @JsonIgnore
    @OneToMany(mappedBy= "member")  //order 테이블에 있는 member 필드에 의해 mapping 된 것임을 명시
    private List<Order> orders = new ArrayList<>();

}
