# learn-junit4
실습- 자바와 JUnit을 활용한 실용주의 단위 테스트 -제프 랭어,앤디 헌트,데이브 토바스,유동환 역, 길벗, 2019
## 실행환경
- IDE: Eclipse 2019-12
- jdk 8

### 200317
- `@Test` 어노테이션이 붙은 메서드를 테스트로 실행 `import org.junit.*;`
- Arrange -> Act -> Assert
- `equalTo` `import static org.hamcrest.CoreMatchers.*;`

### 200318
- Junit은 `@Test`마다 새로운 인스턴스를 생성, 단위 테스트를 독립적으로 실행
- 각 테스트 실행 전에 `@Before` 어노테이션이 붙은 메서드를 먼저 실행함
- 여러 테스트에 중복된 로직이 있다면 `@Before` 메서드에 정의할 것
