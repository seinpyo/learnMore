package jpql.domain;

import javax.persistence.*;

@Entity
public class Member {
    @Id @GeneratedValue
    private  Long id;
    private String username;
    private int age;

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
//                ", team=" + team +    //양방향 요소가 toString() 에 서로 들어가면 서로 참조되어 stackoverflow 발생 위험
                '}';
    }
}
