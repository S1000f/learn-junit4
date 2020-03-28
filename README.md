# JUnit로 배우는 TDD 기초
참고서적

자바와 JUnit을 활용한 실용주의 단위 테스트 -길벗, 2019

Junit in Action -인사이트,2011

## 학습환경
- Windows 10, macOS Catalina
- IDE: IntelliJ IDEA CE 2019.3.4, eclipse 2019-12
- jdk 8
---
### 200317
- `@Test` 어노테이션이 붙은 메서드를 테스트로 실행 `import org.junit.*;`
- Arrange -> Act -> Assert
- `equalTo` `import static org.hamcrest.CoreMatchers.*;`

### 200318
- Junit은 `@Test`마다 새로운 인스턴스를 생성, 단위 테스트를 독립적으로 실행
- 각 테스트 실행 전에 `@Before` 어노테이션이 붙은 메서드를 먼저 실행함
- 여러 테스트에 중복된 로직이 있다면 `@Before` 메서드에 정의할 것

### 200319
- '단언(assert)'은 어떤 조건이 참인지 검증하는 메소드
- `assertTrue` Junit 에서 기본 제공하는 단언 --> `import static org.junit.Assert.*;`
- `assertThat` hamcrest 에서 제공하는 단언(hamcrest는 matchers의 애너그램...) --> `import static org.hamcrest.CoreMatchers.*;`
  + `assertThat(actual, matcher);` actual은 검증하고자 하는 값, matcher는 검증하고자 하는 대상의 (같다고)기대하는 값
- 주요 매처(matcher; 단언 메소드의 두번째 인자)
  + `equalTo()` --> 비교 기준은 자바의 equal() 메소드
  + `startsWith()`
  + `is()` --> 단순히 넘겨받은 매처를 다시 반환하는 역활. 데코레이터.
  + `not()` --> 다른 매처를 부정할때 사용
  + `nullValue()` `notNullValue()` --> null인지 아닌지 검사
  
### 200320
- 부동소수점 실수 두 개의 비교 --> `closeTo()` 매처를 사용하면 편리하다 `org.hamcrest.number.IsCloseTo.*;`
- 단, junit에 기본 포함된 hamcrest매처에 없을 수 도 있음 --> 별도로 필요한 hamcrest 라이브러리를 다운받아 프로젝트에 넣어줘야 함
- 단언에는 마치 주석처럼, 해당 단언을 부연 설명해줄 수 있는 message 라는 선택적 인자가 있다. 순서는 첫 번째
  + ex) `assertThat("message you want", actual, matcher);`
  + 무작정 남용하지 말고, 부연 설명이 없더라도 단언문 그 자체로 해당 목적을 명확하게 인지할 수 있도록 작성하는 노력을 해야함
  + 명확한 테스트 이름, 의미 있는 상수, 의미 있는 변수이름, 가독성 있게 매처를 사용 등등...
  
- 예외처리 테스트
  + 예외가 발생될 상황에서 예외가 잘 던져지는지를 테스트 하는 것. 즉, '기대한' 예외가 발생되면 테스트는 성공이며, '기대한' 예외가 발생되지 않으면 테스트 오류
  + *테스트 코드 그 자체에서 발생하는 예외를 처리 하려는 것이 아님*
  + 1. 테스트 어노테이션 사용--> `@Test(expected = 예외클래스이름.class)`
  + 2. try/catch 사용--> try문 안에 fail(); 삽입(catch문에서 예외를 못 잡을 시 테스트 실패)
  + 3. ExpectedException 사용-->
    ```java
    @Rule
    public ExpectedException ex = ExpectedException.none();
      ...
    @Test
    public void test() {
      ex.expect(예외클래스이름.class);
      ex.expectMessage(...);
        ...
      기대한 예외발생 테스트 코드;
        ...
    };
    ```
  
### 200321
- 테스트 코드와 프로덕션 코드 분리하기
  + 테스트를 프로덕션 코드와 같은 디렉토리, 같은 패키지에 넣기 --> 비추
  + 테스트를 별도 디렉토리, 같은 패키지에 넣기 --> 이클립스, 메이븐, 등등 많이 사용
    > 테스트는 검증하고자 하는 대상 클래스에 패키지 수준의 접근권한을 가지게 됨(default 접근지시자 까지 접근)
  + 테스트를 별도 디렉토리, 유사한 패키지에 넣기
    > 프로덕션 코드의 공개 인터페이스만 테스트 할 수 있음. 프로덕션 코드의 작은 변화가 테스트 코드에 영향을 미치고(반대방향은 안됨) 테스트가 많이 실패할 수록 테스트 작성이 어려워지고 생산성이 되려 저하될 수도 있기 때문...
- 테스트 메소드 이름은 '어떤 동작을 하면 어떤 기대된 결과가 나온다'라는 사실을 명시하는게 좋음

### 200322
- 다수의 `@Before` 메소드가 있을 경우, 실행 순서는 코드상의 순서와 무관하다. 즉 실행될 순서를 가늠하기 어렵다(`@Test`도 마찬가지)
- 따라서 여러 초기화가 일정한 순서로 실행되기 원한다면, 하나의 `@Before`메소드에 구현할 것
- `@After` 어노테이션은 '각각'의 테스트가 '끝날 때 마다' 한번씩 실행된다. 그리고 테스트가 실패해도 실행 됨. 테스트 실행 후 발생하는 부산물들을 처리할때 사용
- 다음은 `@After`가 정의되어 있는 테스트 클래스의 실행 예 (소스 상 테스트 순서인 01, 02 는 테스트 실행 순서와는 무관)
  + ```java
    @Before before01
    @Test test02
    @After after01
    @Before before01
    @Test test01
    @After after01
    ```
- 테스트 클래스 단위의 before와 after도 있다. `@BeforeClass` `@AfterClass`

### 200328

- 테스트 클래스의 조건
  + 접근제어자가 `public`
  + 파라미터를 받지않는 생성자를 제공해야함 --> 자바가 암시적으로 만들어주는 default constructor가 이 조건에 부합됨
  > 테스트 클래스의 이름은 Test로 끝나도록 한다. 반드시 지킬 필요는 없지만 웬만하면...
  
- 테스트 메서드의 조건
  + 접근제어자 `public`
  + 반환형 `void`
  + `@Test` 어노테이션이 부여되어 있어야 함
  
- 파라미터화 테스트 러너
  + 동일한 테스트를 다양한 인자값을 대입하여 반복 수행하는 테스트
  + JUnit 런타임 작동방식
    1. 정적 메소드를 호출해 컬렉션 객체를 얻는다
    2. 컬렉션에 저장된 배열의 수만큼 순환한다
    3. 한번의 루프당 유일한 public 생성자를 찾는다
    4. 생성자에 배열 원소를 넣은 후 생성자를 호출한다
    5. 이제 @Test 메서드를 찾아 호출한다
  
```java
@RunWith(value = Parameterized.class) // 파라미터화 테스트 클래스 어노테이션
public class ParameterizedTest {
    private double expected;  // 테스트에 사용될 변수 선언
    private double valueOne;
    private double valueTwo;

    @Parameterized.Parameters // 테스트에 사용될 파라미터를 저장하기 위한 메소드 필요
    public static Collection<Integer[]> getParameters() {  // 반드시 static 으로 선언, 메소드가 받는 인자 없음
        return Arrays.asList(new Integer[][] {  // 반드시 java.util.Collection 사용, 컬렉션의 원소는 배열
                {2, 1, 1},
                {3, 2, 1},
                {4, 3, 1},
        });
    }
    
    // 유일한 public 생성자가 필요하며, 인자의 타입과 개수는 선언된 맴버변수 즉, 파라미터 변수와 동일해야함
    public ParameterizedTest(double expected, double valueOne, double valueTwo) {
        this.expected = expected;
        this.valueOne = valueOne;
        this.valueTwo = valueTwo;
    }

    @Test
    public void sum() {
        Calculator calc = new Calculator();
        assertEquals(expected, calc.add(valueOne, valueTwo), 0);
    }
}
```
  
  

