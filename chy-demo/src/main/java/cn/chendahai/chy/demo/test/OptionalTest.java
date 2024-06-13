package cn.chendahai.chy.demo.test;

import lombok.Data;

import java.util.Optional;

public class OptionalTest {
    public static void main(String[] args) {
        Person person = new Person();
        Optional.ofNullable(person)
                .map(p -> p.getHobby())
                .map(h -> h.getScope())
                .ifPresent(scope -> System.out.println(scope));

        System.out.println(person.getHobby());
        System.out.println(person.getHobby().getScope());
    }
}


@Data
class Person {

    private Hobby hobby;

}

@Data
class Hobby {

    private String scope;
}

