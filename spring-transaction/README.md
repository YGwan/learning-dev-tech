# Transacation이란

<br>

- 데이터베이스 관리 시스템(DBMS)에서 실행되는 작업의 단위
- 트랜잭션은 ACID라고 알려진 속성을 준수하여 작동
    - 원자성 (Atomicity)
        - 트랜잭션 내의 모든 작업은 모두 성공하거나 모두 실패되어야 한다.
    - 일관성 (Consistency)
        - 트랜잭션 실행 전과 후의 데이터베이스 상태는 일관되어야 한다.
    - 고립성 (Isolation)
        - 한 트랜잭션의 실행이 다른 트랜잭션에 영향을 미치지 않아야 한다.
    - 지속성 (Durability)
        - 성공적으로 완료된 트랜잭션은 영구적으로 데이터베이스에 반영되어야 한다.

<br>

## JDBC & JPA를 사용한 초기 트랜잭션 구현 방법

<br>

### 예시 코드
- JPA를 기준으로 설명

<br>

``` java

public void joinAllUserFromJPA(List<User> users) throws SQLException {
    EntityManager em = emf.createEntityManager();
    EntityTransaction tx = em.getTransaction();

    try {
        tx.begin();
        for (User user : users) {
            transDao.joinUser(user, em);
        }
        tx.commit();
    } catch (Exception e) {
        tx.rollback();

    } finally {
        em.close();
    }
}

```

- JDBC와는 다르게 EntityManager를 가져와 EntityManager에서 EntityTransaction을 가져옴
    - JDBC는 DBConfig 클래스에서 DB 관련 환경 설정을 진행하고 이를 가지고 Connection 객체를 정의합니다.
-  Connection 객체를 통해 트랜잭션 시작, 커밋, 에러 발생시 롤백, 처리가 다 끝나면 해당 트랜잭션을 닫는 것까지 진행

<br>

### 한계점
- 데이터 엑세스 기술에 의존성이 강제된다. (데이터 접근 기술에 따라 코드 내용이 변경된다.)
- 트랜잭션 관리를 직접 해야되기 때문에 번거롭다.(트랜잭션 open & close)
- Service 코드 내에 비즈니스 로직 외에 트랜잭션 처리 관련 코드가 추가되기 때문에 관리가 어렵다. (try ~ catch 구문 등)

<br>

```

이러한 문제를 Spring이 해결했다.

스프링 트랜잭션의 핵심 기술

1. 트랜잭션 동기화
    - 데이터 접근 기술과 비즈니스 로직 사이의 종속성 제거
    
2. 트랜잭션 추상화
    - 트랜잭션 코드가 추상화되어 숨겨져 있다.
    - 개발자가 트랜잭션 기능을 쉽게 사용할 수 있다.

3. 선언적 트랜잭션
    - 비즈니스 로직과 트랜잭션 관련 로직을 AOP를 사용해 완전히 분리했다.

```

<br>

## Spring에서의 트랜잭션 처리 방법

<br>

### 1. 트랜잭션 동기화
- 트랜잭션 동기화란 트랜잭션을 시작하기 위한 Connection 객체를 특별한 저장소인 트랜잭션 동기화 매니저에 저장하고 필요할때마다 쓸 수 잇도록 하는 기술
- 트랜잭션 동기화 매니저는 작업 쓰레드마다 Connection 객체를 독립적으로 관리하기 때문에, 멀티 쓰레드 환경에서도 충돌이 발생하지 않음
    - 현재 실행중인 트랜잭션과 관련된 정보를 쓰레드 로컬에 저장

<br>

### 예시 코드
- JDBC를 기준으로 설명

<br>

``` java
private void joinAllUserFromSyncTranByJdbc(List<User> users) throws SQLException {
    TransactionSynchronizationManager.initSynchronization();
    Connection conn = DataSourceUtils.getConnection(dataSource);
    conn.setAutoCommit(false);
    
    try {
        for (User user : users) {
            transDao.joinUser(user);
        }
        conn.commit();
    } catch (Exception e) {
        conn.rollback();
    
    } finally {
        DataSourceUtils.releaseConnection(conn, dataSource);
        TransactionSynchronizationManager.unbindResource(dataSource);
        TransactionSynchronizationManager.clearSynchronization();
    }
}

```

- 트랜잭션 동기화 매니저를 초기화하고 커넥션을 가져옴
    - Datasource의 경우 기본적으로 제공하기 때문에 의존성 주입을 통해 가져옴
    - JDBC를 사용했기 때문에 DataSourceUtils 클래스를 사용해 Connection 획득
- DataSourceUtils.getConnection() 메서드의 경우 커넥션이 존재할 경우 가져다 쓰고, 존재하지 않을 경우 새로 등록해서 사용 (Connection Holder에서 확인)
- Connection 객체를 매개변수로 넘겨 DAO에서 처리하는 것이 아닌 트랜잭션 동기화 매니져에서 커넥션 객체를 가져와 처리
- Connection 객체를 통해 트랜잭션 시작, 커밋, 에러 발생시 롤백, 처리가 다 끝나면 해당 트랜잭션을 닫는 것까지 진행

<br>

### 이전과 달라진 점
- 커넥션을 가져오고 종료하는 방식
    - 커넥션을 관리하는 객체가 따로 존재
- DAO에 Connection 객체를 전달하지 않아도 됨

<br>

### 한계점
- 트랜잭션을 사용하는 코드는 데이터 접근 기술마다 다르기 때문에 아직 데이터 접근 기술에 의존적임
    - JDBC -> DataSourceUtils
    - JPA -> EntityManagerFactory
    - Hibernate -> SessionFactoryUtils
- 이로 인해 데이터 접근 기술이 바뀌면 서비스 계층의 코드 변경이 불가피함
- 서비스 코드 내의 비즈니스 로직 외 트랜잭션 처리 관련 코드가 추가되어 관리가 힘듬 (try ~ catch)

<br>

```

트랜잭션 처리 작업 시 공통점 발견

1. 트랜잭션을 가져오거나 생성함
2. DB 관련 작업을 진행
3. 작업이 정상적으로 끝나면 커밋 후 트랜잭션 종료
4. 문제 발생시 작업 종료 후 롤백

구현 방식에 상관 없이 동일한 작업 수행 -> 추상화 가능

```

<br>

### 2. 트랜잭션 추상화
- 트랜잭션 매니저의 기능을 인터페이스로 정의하고 JDBC, JPA 등의 데이터 접근 기술에 따른 구현체를 사용
- 서비스 계층은 구현체가 아닌 인터페이스에 의존
    - JDBC -> JPA로 변경해도 서비스 코드 변경 X

<br>

### 예시 코드

<br>

``` java
public void joinAllUserFromAbstractTran(List<User> users) {
    TransactionStatus status = platformTransactionManager.getTransaction(new DefaultTransactionDefinition());

    try {
        for (User user : users) {
            transDao.joinUser(user);
        }
        transactionManager.commit(status);

    } catch (Exception e) {
        transactionManager.rollback(status);
        System.out.println(e.getMessage());
    }
}

```

- PlatfromTransactioinManger 경우 기본적으로 제공하기 때문에 의존성 주입을 통해 가져옴
- 기본 트랜잭션 속성으로 트랜잭션을 생성
- 이로 인해 완전히 데이터 접근 기술과 의존하지 않아 데이터 접근 기술이 바뀔때 서비스 코드가 바뀌지 않음
    - Spring에서 자체적으로 연결시킴

<br>

### 한계점
- 아직까지도 비즈니스 로직 외의 다른 작업을 추가로 서비스 로직에서 해야한다. (try ~ catch)

<br>

### 3. 선언적 트랜잭션
- 스프링은 선언적 트랜잭션 기술을 통해 트랜잭션 생성 및 종료 등의 관련 내용을 비즈니스 로직과 분리함
- 스프링 AOP를 통해 프록시 객체를 만들고 해당 객체에서 트랜잭션 관련 로직을 처리
- 그 후, 트랜잭션을 시작하고 실제 서비스를 대신 호출해 서비스 코드에 구현되어 있는 비즈니스 로직을 처리
- 서비스 로직에서의 try ~ catch를 프록시 객체를 통해 구현


<br>

- 프록시 패턴이란?
    - 프록시 패턴은 어떤 객체에 대한 접근을 제어하는 용도로 대리인이나 대변인에 해당하는 객체를 제공하는 패턴
    - 스프링 AOP는 프록시 기반의 AOP 구현체이며, 스프링 Bean에만 AOP 적용 가능
    - 프록시는 클라이언트로부터 요청을 받으면 타겟 클래스의 메소드로 위임하고, 경우에 따라 부가 작업 추가
    - 프록시는 클라이언트가 타겟 클래스를 호출하는 과정에만 동작

<br>

### 예시 코드

<br>

``` java
@Transactional
public void joinAllUser(List<User> users) {
    for (User user : users) {
        try {
            userDao.joinUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

```

- @Transactional 어노테이션 사용
    - 메서드 , 클래스, 인터페이스 등에 적용 가능
    - 서비스 계층에서 주로 사용
    - propagation, isolation, timeout, readOnly, rollbackFor, noRollbackFor 속성 지정 가능

<br>

<br>

<br>
