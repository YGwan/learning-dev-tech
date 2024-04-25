# Server Monitoring이란

<br>

- 컴퓨터 네트워크에서 서버의 성능, 상태 및 작동 여부를 지속적으로 추적하고 검사하는 프로세스
- 성능, 장애 대비, 자원 사용량 확인 등의 목적으로 모니터링 진행
- 기업의 IT 인프라를 안정적으로 유지하고 문제를 신속하게 해결하기 위해 필수적인 활동
- CPU, Memory, Connection, Request 수 등 많은 지표들을 확인 & 관리

<br>

- 여러 모니터링 툴이 존재하지만, 스프링 부트가 제공하는 Actuator의 경우 이러한 모니터링 기능을 편리하게 사용할 수 있는 다양한 편의성 제공
- 모니터링 사용 툴 : Spring Actuator, Prometheus, Grafana

<br>

---

### Spring Actuator
- 스프링 프레임워크에서 제공되는 라이브러리 중 하나
- 애플리케이션 상태를 모니터링 할 수 있는 지표, 메트릭 수집을 위한 HTTP Endpoint 제공(health, metrics, prometheus 등)

<br>

#### Spring Actuator에서 제공하는 Endpoint 목록
- beans : 컨테이너에 등록된 스프링 빈 조회
- env : Environment 정보 조회
- health : 서버 health 정보 조회
- info : 애플리케이션 정보 조회
- metrics : 애플리케이션의 메트릭 정보 조회
- logger : 애플리케이션 로거 설정 조회 & 변경
- prometheus : 애플리케이션의 메트릭 데이터를 Prometheus 형식으로 노출
...

<br>

#### metrics 엔드포인트 사용 시 조회 가능한 메트릭 정보
- application.ready.time
- application.started.time
- disk.free
- disk.total
- hikaricp.connections
- process.cpu.usage
...

<br>

#### 지표(메트릭)이란?
- 시스템이나 소프트웨어 애플리케이션의 상태나 동작을 측정하고 기록하는 데 사용되는 수치적인 값
- 일반적으로 시간에 따른 변화를 추적하거나 시스템의 특정 부분에 대한 정보를 제공하기 위해 수집
- 유형
  - 카운터(Counter) : 순차적으로 증가하는 값 (값을 증가하거나 0으로 초기화 하는 것만 가능)
  - 타이머(Timer) : 정 이벤트의 지속 시간을 측정한 값 (카운터와 유사)
  - 게이지(Gauge) : 임의로 오르내릴 수 있는 단일 숫자 값(값이 증가하거나 감소하는 것 가능)
  
<br>

```

Spring Actuator & Custom을 통해 얻은 지표 값들을 모니터링 툴에 맞도록 만들어야한다.
- 모니터링 툴이 달라지면 포맷이 달라져 코드를 변경해야됨 -> 마이크로미터 라이브러리를 통해 해결 (추상화)

```

<br>

#### 마이크로미터란?
- 자바 기반의 애플리케이션에서 메트릭 수집을 위한 오픈 소스 라이브러리
- 마이크로미터가 정한 표준 방법으로 메트릭 데이터 전달 후 모니터링 툴에 맞는 구현체 선택
  - 이후, 모니터링 툴이 변경되더라도 구현체만 변경하면 됨 -> 구현 코드의 변화 X

<br>

---

### Prometheus
- 오픈 소스 모니터링 시스템 및 경고 도구로, 다양한 작업에서 발생하는 메트릭을 수집하고 저장하는 데 사용
- 메트릭 정보를 지속적으로 수집하고, 이를 자체 DB에 저장
- spring actuator가 제공하는 /metrics 엔드포인트의 JSON 포맷을 prometheus가 사용 X -> 마이크로미터 프로메테우스 구현체 라이브러리 추가 필요
- 마이크로미터 프로메테우스 구현체 라이브러리
  ``` java
  implementation 'io.micrometer:micrometer-registry-prometheus'
  ```
 
<br>

### PromQL(Prometheus SQL)
- Prometheus에서 수집한 메트릭 데이터를 쿼리하고 분석하기 위한 질의 언어

<br>

---

### Grafana
- 다양한 데이터 소스에서 수집된 데이터를 시각화하고 모니터링하기 위한 오픈 소스 플랫폼
- Prometheus, InfluxDB, Elasticsearch 등 다양한 데이터 소스와 연동 가능
- 메트릭 데이터를 다양한 시각화 도구를 사용해 데이터를 확인할 수 있는 시각화 대시보드 제공

<br>

---

### 전제척인 FLOW

1. 스프링 엑츄에이터을 통해 필요한 메트릭 자동 생성
2. 마이크로미터 프로메테우스 구현체 라이브러리 의존성 추가 (자동으로 프로메테우스가 사용할 수 있는 메트릭 데이터를 생성해줌)
3. 프로메테우스가 지속해서 메트릭 정보 수집()
4. 프로메테우스가 수집한 메트릭 데이터를 내부 DB에 저장
5. 그라파나 대시보드 툴을 통해 그래프로 편리하게 메트릭 조회

<br>
<br>
<br>
