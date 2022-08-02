package study;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.BinaryOperator;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;
import java.util.function.IntPredicate;
import java.util.function.IntSupplier;
import java.util.function.IntUnaryOperator;
import java.util.function.ObjIntConsumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

/* 함수형 인터페이스
 * 자바 8부터 표준 API로 제공되는 함수형 인터페이스
 * java.util.function 패키지에 포함되어 있다.
 * 매개타입으로 사용되어 람다식을 매개값으로 대입할 수 있도록 해준다.
 * Consumer, Supplier, Function, Operation, Predicate 계열이 있다.
 * 매개변수가 2개인 경우 접두사 Bi를 붙인다.
 * 참고: https://docs.oracle.com/javase/8/docs/api/java/util/function/Predicate.html
 * */
public interface FunctionalInterface {

	public static void main(String[] args) {
		/* Predicate 계열
		 * 논리 판단을 해주는 함수형 인터페이스이다.
		 * 입력을 받아서 boolean 타입 출력을 반환한다.
		 * */
		System.out.println("################### Predicate ######################");
		Predicate<Integer> predicate = (s) -> s%2 == 0; // 짝수일 경우 true를 반환한다.
		System.out.println(predicate.test(4));
		
		Predicate<Integer> predicateAnd = (s) -> s > 10; // 짝수이면서 10보다 큰 경우 true를 반환한다.
        System.out.println(predicate.and(predicateAnd).test(100));
        System.out.println(predicate.and(predicateAnd).test(10));

        BiPredicate<String, Integer> pred2 = (s,v) -> s.length() == v; // 문자열의 길이가 같을 경우 true를 반환한다.
        System.out.println(pred2.test("abcd", 5));
        System.out.println(pred2.test("abc", 3));

        IntPredicate pred3 = x -> x > 10; // 10보다 큰 경우 true를 반환한다.
        System.out.println(pred3.test(4));
		
        /* Consumer 계열
         * 파라미터 입력을 받아서 그것을 소비하는 함수형 인터페이스이다.
         * 인자를 받고 아무것도 리턴하지 않는다.
         * */
        System.out.println("################### Consumer ######################");
		Consumer<String> consumer = (s)-> System.out.println(s);
        consumer.accept("Hello"); // 파라미터 입력

        BiConsumer<String, String> biConsumer = (t, u) -> System.out.println(t + " To " + u);
        biConsumer.accept("Nice","Meet You.");
        
        ObjIntConsumer<String> objIntConsumer = (t, x) -> System.out.println(t + " am "+ x + " years old.");
        objIntConsumer.accept("I", 10);
        
        /* Supplier 계열
         * 아무런 입력을 받지 않고, 값을 하나 반환하는 함수형 인터페이스이다.
         * 자료를 '공급'(외부로 데이터를 리턴)하는 공급자 역할을 한다.
         * */
        System.out.println("################### Supplier ######################");
        Supplier<String> supplier = () -> "Good Morning";
        System.out.println(supplier.get()); // get()으로 출력

        BooleanSupplier boolsup = () -> true;
        System.out.println(boolsup.getAsBoolean());

        IntSupplier rollDice = () -> (int)(Math.random() * 6);
        for (int i = 0; i < 3; i++) {
            System.out.println(rollDice.getAsInt());
        }

        int x = 123;
        IntSupplier intSupp = () -> x; //지역변수에도 접근할 수 있다.
        // 고정되어있는 값뿐만아니라 동적으로도 주변 값들을 공급할 수 있다.
        System.out.println(intSupp.getAsInt());

    
        /* Function 계열
         * Mapping : 입력 -> 출력을 연결하는 함수형 인터페이스이다.
         * 입력 타입과 출력 타입은 다를 수도, 같을 수도 있다.
         * 매개값을 리턴값으로 매핑(타입변환)하는 역할을 한다.
         * */
        System.out.println("################### Function ######################");
        Function<String,Integer> func = (s) -> s.length(); // s 는 String타입, s.length() 는 Integer
        System.out.println(func.apply("HaHaHaHaHaHaHaHaHaHaHa")); //apply로 출력한다

        // Bi가 붙으면 2개의 값 입력
        BiFunction<String,String,Integer> biFunction = (s,u) -> s.length() + u.length();
        System.out.println(biFunction.apply("Orange","Water Melon")); //6

        // IntFunction<R>은 리턴 자료형
        IntFunction<String> intFunction = (value) -> String.valueOf("입력한 값은 : " + value);// "" + value도 가능.
        System.out.println(intFunction.apply(1024));

        //ToP Type Function은 출력을 P타입으로 한다.
        ToIntFunction<String> funcFour = (s) -> s.length(); // 4:21
        System.out.println(funcFour.applyAsInt("abcde"));
        //ToIntBiFunction<String,String>// int 출력을 하는 Bi 함수

        /* Operator 계열
         * 입력받은 타입과 동일한 타입의 출력을 하는 함수형 인터페이스이다.
         * Funtion 계열과 달리 입출력 타입이 다를 수 없다.
         * applyXXX() 메소드를 가지고 있고 매개값을 리턴값으로 매핑하는 역할보다는 매개값을 이용해서 연산을 수행한 후 동일한 타입으로 리턴값을 제공하는 역할을 한다.
         */
        System.out.println("################### Operator ######################");
        // 입력이 1개 인 것을 Unary를 붙여서 표현
        UnaryOperator<String> operator = s -> s + "하세요."; 
        // 리턴타입을 따로 입력받지 않는다 입출력이 같으니깐
        System.out.println(operator.apply("안녕")); // apply() 사용.

        BinaryOperator<String> operator1 = (s1,s2) -> s1 + s2;
        System.out.println(operator1.apply("인사","하세요."));

        IntUnaryOperator op = value -> value * 10; 
        // 동일한 타입을 출력하기 때문에 int를 쓰지 않는다.
        System.out.println(op.applyAsInt(55));

        IntBinaryOperator ibo = (v1,v2) -> v1 * v2;
        System.out.println(ibo.applyAsInt(12,11));
        
    }
	
}
