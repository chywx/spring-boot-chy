package cn.chendahai.chy.demo.lambda;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

/**
 * JDK1.8 Lambda特性测试学习
 */
public class LambdaTest {

    @Test
    public void testOptional(){
        Optional<String> optional = Optional.of("bam");
        optional.isPresent();                  // true
        optional.get();                        // "bam"
        optional.orElse("fallback");    // "bam"
        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
        Optional<Person> optionalPerson = Optional.of(new Person());
        optionalPerson.ifPresent(s -> System.out.println(s.firstName));
    }


    @Test
    public void testComparator(){
        Comparator<Person> comparator01 = (p1, p2) -> p1.firstName.compareTo(p2.firstName);
        Comparator<Person> comparator02 = Comparator.comparing(p -> p.firstName);           //等同于上面的方式
        Person p1 = new Person("John", "Doe");
        Person p2 = new Person("Alice", "Wonderland");
        System.out.println(comparator01.compare(p1, p2));;             // > 0
        System.out.println(comparator02.reversed().compare(p1, p2));;  // < 0
    }

    class Person {
        String firstName;
        String lastName;

        Person() {
        }

        Person(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    @Test
    public void testConsumer() {
        // 参照物，方便知道下面的Lamdba表达式写法
        Consumer<Person> greeter01 = new Consumer<Person>() {
            @Override
            public void accept(Person p) {
                System.out.println("Hello, " + p.firstName);
            }
        };
        Consumer<Person> greeter02 = (p) -> System.out.println("Hello, " + p.firstName);
        greeter02.accept(new Person("Luke", "Skywalker"));  //Hello, Luke
    }


    class Something {
        public String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    @Test
    public void testFunctions() {
        Function<String, Integer> toInteger = Integer::valueOf;                                         //转Integer
        Function<String, String> backToString = toInteger.andThen(String::valueOf);                     //转String
        Function<String, String> afterToStartsWith = backToString.andThen(new Something()::startsWith); //截取第一位
        String apply = afterToStartsWith.apply("123");// "123"
        System.out.println(apply);
    }


    @Test
    public void testPredicate() {
        Predicate<String> predicate = (s) -> s.length() > 0;

        boolean foo0 = predicate.test("foo");           // true
        boolean foo1 = predicate.negate().test("foo");  // negate否定相当于!true

        System.out.println(foo0);
        System.out.println(foo1);

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        System.out.println(nonNull.test(null));
        System.out.println(isNull.test(null));

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        System.out.println(isNotEmpty.test(""));
    }

}
