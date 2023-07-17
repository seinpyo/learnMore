package jpabook.review.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable //값타입 => 변경 불가능하도록 설계할 것
@Getter
public class Address {

    protected Address() {}  //기본 생성자

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }

    private String city;
    private String street;
    private String zipcode;

}

