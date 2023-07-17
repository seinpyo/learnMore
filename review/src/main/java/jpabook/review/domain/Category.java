package jpabook.review.domain;

import jpabook.review.domain.Item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="category_item",        //rdbms에서 다대다 관계를 표현해 주기 위한 중간 테이블 설정
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns = @JoinColumn(name="item_id")
    )
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")       //셀프 다대일관계
    private Category parent;

    @OneToMany(mappedBy="parent")
    private List<Category> child = new ArrayList<>();

    //연관 관계 편의 매서드
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
