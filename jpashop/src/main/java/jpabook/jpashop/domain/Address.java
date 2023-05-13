package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Embeddable
@Getter
//주소 값 타입: 변경 불가능하게 설계 (생성 시에만 값 설정 가능)
public class Address {

    private  String city;
    private String street;
    private String zipcode;

    protected Address() {} //JPA 스펙 상, 기본 생성자 필요 -> 그나마 new로 생성하기 어렵도록 protected 설정

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
