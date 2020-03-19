# learn-junit4
실습- 자바와 JUnit을 활용한 실용주의 단위 테스트 -제프 랭어,앤디 헌트,데이브 토바스,유동환 역, 길벗, 2019


## 실행환경
- IDE: Eclipse 2019-12
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
- '단언(assert)'은 어떤 조건이 참인지 검증하는 정적 메소드
- `assertTrue` Junit 에서 기본 제공하는 단언 --> `import static org.junit.Assert.*;`
- `assertThat` hamcrest 에서 제공하는 단언(hamcrest는 matchers의 애너그램...) --> `import static org.hamcrest.CoreMatchers.*;`
  + `assertThat(actual, matcher);` actual은 검증하고자 하는 값, matcher는 검증하고자 하는 대상의 (같다고)기대하는 값
- 주요 매처(matcher; 단언 메소드의 두번째 인자)
  + `equalTo()` --> 비교 기준은 자바의 equal() 메소드
  + `startsWith()`
  + `is()` --> 단순히 넘겨받은 매처를 다시 반환하는 역활. 데코레이터.
  + `not()` --> 다른 매처를 부정할때 사용
  + `nullValue()` `notNullValue()` --> null인지 아닌지 검사
