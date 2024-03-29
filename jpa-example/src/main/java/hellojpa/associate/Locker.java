package hellojpa.associate;

import hellojpa.BaseEntity;

import javax.persistence.*;

@Entity
public class Locker  extends BaseEntity {
    @Id @GeneratedValue
    private Long id;
    private String name;

    @OneToOne(mappedBy = "locker")
    private AssociateMember member;

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
