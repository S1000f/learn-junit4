# JUnit로 배우는 TDD 기초
참고서적

자바와 JUnit을 활용한 실용주의 단위 테스트 -길벗, 2019

Junit in Action -인사이트,2011

## 학습환경
- Windows 10, macOS Catalina
- IDE: IntelliJ IDEA CE, eclipse 2019-12
- jdk 1.8
- JUnit 4.13

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
  + `equalTo()` --> 비교 기준은 자바 Object의 equals()
  + `startsWith()`
  + `is()` --> 단순히 넘겨받은 매처를 다시 반환하는 역활. 데코레이터.
  + `not()` --> 다른 매처를 부정할때 사용
  + `nullValue()` `notNullValue()` --> null인지 아닌지 검사
  
### 200320
- 부동소수점 실수 두 개의 비교 --> `closeTo()` 매처를 사용하면 편리하다 `org.hamcrest.number.IsCloseTo.*;`
- 단, junit에 기본 포함된 hamcrest매처에 없을 수 도 있음 --> 별도로 필요한 hamcrest 라이브러리를 다운받아 프로젝트에 넣어줘야 함
- 단언에는 마치 주석처럼, 해당 단언을 부연 설명해줄 수 있는 message 라는 선택적 인자가 있다. 순서는 항상 첫 번째
  + ex) `assertThat("message you want", actual, matcher);`
  + 잘 사용하면, 테스트 실패시 그 원인을 파악하는데 도움이 될 수 있으므로 간결하고 명확하게 작성하는 노력을 하자
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
- `@Before/@After`, `@BeforeClass/@AfterClass` 모두 반드시 public 이어야 하며 `@BeforeCalss/@AfterClass`는 동시에 static이어야 함

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
  
### 200330

- '도메인 객체' : 단위 테스트에서 도메인 객체란 실제 애플리케이션에 포함된 객체를 말함
- '테스트 객체' : 테스트 시 도메인 객체와 상호작용할 객체
- 하나의 테스트(`@Test`)에서는 하나의 객체만 테스트해야 한다. 만약 한 번에 두 개 이상을 테스트 한다면 그 중 하나의 객체만 수정되어도 서로 간에 어떤 영향을 줄지 예측하기 어렵다. 만약 테스트 대상 객체(도메인객체)가 다른 객체와 복잡한 상호작용을 한다면, 예측가능한 테스트용 도우미 객체로 감싸서 테스트를 수행한다
  
### 200331

- 테스트 클래스가 복잡하지 않다면, 기존의 테스트 클래스의 내부 클래스(nested class)로 선언하는 방법도 있다.
- 하나의 테스트 메소드(`@Test`)안에는 반드시 하나 이상의 assert문을 사용해야 한다. 단언문이 없으면 해당 테스트는 무조건 성공한다.
- `assertEquals()` 메소드는, `equalTo()`매처 처럼 비교기반을 자바의 `Object.equals()`사용하므로, 필요에 따라 적절히 오버라이딩 하여 사용하자

### 200411

- 타임아웃 테스트
  + 주어진 시간안에 동작을 완료할 수 있는지 검사하는 테스트
  + `@Test(timeout = 밀리세컨드)` --> 주어진 시간안에 완료되지 못한 테스트는 실패함
  + 소수의 타임아웃 테스트가 전체 테스트 빌드를 실패하게 할 수도 있음. 따라서 명확한 제한시간이 정해지지 않았다면 일부 테스트들은 건너뛰는 것이 좋을때도 있다 --> `@Ignore` 를 사용하여 해당 테스트를 건너뛸 수 있다.
  + `@Ignore(value = 이그노어를 설정한 이유 명시 )` value 파라미터에 해당 테스트를 건너뛴 이유를 명시하면, 전체 테스트 빌드 실행시 해당 내용이 테스트 결과창에 출력됨
  
- Hamcrest의 특징
  + 다양한 매처를 조합하여 사용할 수 있다
  + assert가 실패했을때 실패의 원인을 직관적이고 상세히 출력해준다
  ```java
  public class HamcrestTest {
    private List<String> list;

    @Before
    public void setList() {
        list = new ArrayList<>();
        list.add("x");
        list.add("y");
        list.add("z");
    }

    @Test // test1
    public void testWithoutHamcrest() {
        assertTrue(list.contains("one") || list.contains("two") || list.contains("three"));
    }

    @Test // test2
    public void testWithHamcrest() {
        assertThat(list, hasItem(anyOf(equalTo("one"), equalTo("two"), equalTo("Three"))));
    }
  }
  ```
  + Hamcrest 미사용시(test1) 실패 메시지
  ```java
  java.lang.AssertionError
	at org.junit.Assert.fail(Assert.java:86)
	at org.junit.Assert.assertTrue(Assert.java:41)
	at org.junit.Assert.assertTrue(Assert.java:52)
	at my.junit.ch03.HamcrestTest.testWithoutHamcrest(HamcrestTest.java:30)
  ...
  ```
  
  + Hamcrest 사용시(test2) 실패 메시지
  ```java
  java.lang.AssertionError: 
  Expected: a collection containing ("one" or "two" or "Three")
     but: was "x", was "y", was "z"
  Expected :a collection containing ("one" or "two" or "Three")
  Actual   :"x", was "y", was "z"
  <Click to see difference>
  ...
  ```
  
## 스텁을 활용한 테스트
  
### 스텁(stub)이란?
- 호출자를 실제 구현물로부터 격리시킬 목적으로 런타임에 실제 코드 대신 삽입되는 코드조각이다.

- 다음의 예들 처럼, 런타임시 외부 클래스에 의존되는 애플리케이션(혹은 모듈)을 테스트 할때 사용되는 더미 객체이다.
	+ 파일 시스템과의 연결
	+ 서버와의 연결
	+ 데이터베이스와의 연결 등
	
- 스텁 사용시의 장점
	+ 시스템의 특정부분(내부 혹은 외부)이 미처 준비되지 못했더라도 다른 부분들을 테스트 할 수 있다
	+ 대부분의 경우 테스트 코드를 수정할 필요가 없다(스텁을 위해 특별히 다른 방식으로 테스트 코드를 작성하지 않아도 된다)
	+ 하부 시스템간의 통합 테스트(하향식)처럼 포괄적인 테스트 수행시 유용
	
- 스텁 사용시 유의사항(단점)
	+ 제작이 복잡한 스텁의-흉내내는 원래 대상 객체가 복잡한-경우, 스텁 자체를 디버깅해야 하는 상황이 발생할 수 도 있다
	+ 상세한 테스트에는 적합하지 못하다
	
### 스텁 제작 방식

- 스텁 제작의 두 가지 유형
	+ 테스트용 서버를 설치하고 스텁용 웹 페이지를 구현하는 방법
	+ 임베디드 웹 서버를 사용하는 방법
	
- 스텁 테스트용 웹 서버 구현시 단점
	+ 테스트 시작 전에 스텁용 서버가 잘 작동되는지 검증해야지만, 테스트 수행의 신뢰성이 생긴다. (테스트 실패시, 실패원인이 테스트 서버가 아님을 확신해야함)
	+ 웹 서버를 가동시킨 후에야 테스트 수행이 가능하므로, 테스트 자동화가 어렵다
	+ 하나의 테스트 케이스를 위한 테스트 로직이 두 군데 이상 흩어져 있다(Junit 테스트케이스와 테스트용 웹 서버)
	
- 임베디드 웹 서버 사용하기
	+ Jetty 웹 서버 : Servlet/JSP Container
	+ 임베디드 웹 서버 사용시 이점
		* 테스트케이스 내에서 프로그램적으로 제어 가능
		* 스텁으로 대체할 부분이 jetty 핸들러 뿐이므로, 서버 자체에 대해 익힐 필요가 없음
		
- Jetty Handler 예제 (특정 리소스를 반환하는 Jetty 핸들러: 여기서는 It works 문자열)

	```java
	private class TestGetContentOkHandler extends AbstractHandler { // Jetty의 AbstractHandler 추상클래스를 상속
		@Override
		public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch)
			throws IOException {
		    OutputStream out = response.getOutputStream();
		    ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
		    writer.write("It works"); // 이 핸들러 요청시 It works 문자열을 반환
		    writer.flush();
			// 출력스트림에 쓰일 컨텐츠의 길이 설정(jetty 요구사항)
		    response.setIntHeader(HttpHeaders.CONTENT_LENGTH, writer.size()); 
		    writer.writeTo(out);
		    out.flush();
		}
	}
	```
	
- Jetty 서버 사용하기
	```java
	@BeforeClass // 본 서버를 사용하는 테스트가 여러개일 경우, 매 테스트 마다 서버를 시작/종료 하지 않도록 BeforeClass 를 사용
    public static void setUp() throws Exception {
        Server server = new Server(8080);

        TestWebClient t = new TestWebClient();
        Context contextOkContent = new Context(server, "/testGetContentOk"); // url 맵핑
        contextOkContent.setHandler(t.new TestGetContentOkHandler()); // 만들어둔 핸들러에게 http 요청을 전달

        server.setStopAtShutdown(true); // 셧다운 시 서버를 자동 종료되도록 함 -> AfterClass 메서드를 구현할 필요 없음
        server.start();
    }
	```
