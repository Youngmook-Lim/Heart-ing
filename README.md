## 프로젝트 개요

SSAFY 8기 2학기 자율 프로젝트

2023.04.10 ~ 2023.05.19

## 프로젝트 기획 의도

- 사랑을 가볍게, 자주 표현할 수 있는 서비스
- 마음 전달 프로젝트
- 익명의 메세지 서비스
- url 공유를 통한 프라이빗 메세지함 전달

## 프로젝트 목표

- 애자일한 개발
- 많은 사용자 받기
- 피드백 기반 개선

## 서비스명 & 로고

![로고](etc/assets/hearting_logo.png)

## 주요기능

- **메시지 보내기**
    - 하트판은 나만의 공간이자 URL을 통해 접속한 사람만이 볼 수 있는 공간으로 최근 받은 하트를 확인하고 메시지를 작성 ****할 수 있습니다.
    - 메시지 작성 시 기본 하트 5개가 주어지고 주고 받은 메시지 개수에 따라 풍부한 감정을 표현하는 스페셜 하트가 주어집니다.
- **받은 메시지**
    - 받은 메시지는 기본적으로 **24시간** 이후에 사라집니다.
    - 내가 받은 메시지에 대해 **반응**을 남길 수 있고 상대방이 확인할 수 있습니다.
- **영구 보관함**
    - 메시지가 사라지기 전에 저장한 경우 영구 보관함에 보관할 수 있습니다.
- **하트 도감**
    - 도감에서 내가 사용 가능한 **다양한 하트들과 획득 조건**을 확인할 수 있습니다.
- **실시간 알림**
    - 메시지 수신 시 및 새로운 스페셜 하트 획득 시 받을 수 있습니다.
- **하트테스트**
    - 자신의 심볼 하트를 확인할 수 있습니다.

## 서비스 화면

![화면1](etc/assets/screen1.png)

## **기술 차별점**

- **유저 경험 향상**
    - WebSocket 전용 서버 사용
        - 하트를 받은 유저의 메인 페이지에 실시간 알람을 구현함으로써 유저경험을 향상시켰습니다.
        - 웹소켓 실시간 통신의 원활한 구동을 위해 node.js로 별도의 웹소켓 백엔드 서버를 구현했습니다.
        - 특히 향후 사용자 피드백을 통한 추가 실시간 기능 추가 필요 시 메인 백엔드 서버의 부하를 덜 수 있습니다.
        - Kubernetes Deployment로 다수 node.js 서버를 운영하기 위해 redis cluster를 통해 각 node.js 서버 상태공유를 구현하였습니다.
- **대규모 트래픽 대응**
    - Kubernetes Cluster 사용
        - 대규모 트래픽 발생에 대비하여 백엔드 서버(Spring Boot, node.js)를 Kubernetes Deployment로 관리하여 다수의 백엔드 서버를 구동하고 요청에 대한 Load Balancing을 구현하였습니다. 이로써 각각의 서버에 대한 부담을 줄여 백엔드 안정성을 향상시킵니다.
        - Kubernetes를 활용함으로써 관리하는 서버의 항상성을 보장합니다. 몇 개의 서버가 다운되어도, 다른 서버가 살아있기 때문에 전체 서비스가 다운될 가능성을 줄이고, 다운된 서버를 쿠버네티스가 다시 구동시켜 안정적인 시스템 구축을 지원합니다.
        - Kubernetes를 활용하여 Rolling Update를 구현했습니다. Agile하게 개발하여 배포 횟수가 잦은 만큼 서비스에 지장 없이 신규 버전을 배포하는 것이 중요하다고 생각하였습니다.
        - 또한, 다수의 node.js 서버를 두어도 웹소켓 통신이 원활하게 하기 위해 Redis Cluster로 node.js 서버들의 상태관리를 할 계획입니다. 이 Redis Cluster는 Kubernetes StatefulSet로 관리합니다.
    - Redis 캐싱 및 Replication
        - redis는 인메모리 구조 데이터베이스로, redis 캐싱을 이용하면 MySQL 보다 빠르게 데이터에 접근할 수 있습니다.
        - redis Hash 자료구조를 사용하여 기존의 하트 획득 로직 성능을 O(M*N) 에서 O(1)로 최적화하였습니다.
        - read가 많은 데이터를 레디스에 저장하여 API 응답 속도를 최적화했습니다.
        - 장애를 대비하여 replication을 구축해 사용성을 높였습니다.
- 반응형 웹
    - 독자적 환산단위 적용한 반응형 웹 구현
    - 기기 상관 없는 안정적인 뷰 제공
- 데이터 기반 의사결정
    - 구글 애널리틱스, 핫자(hot jar)
    - 구글 태그 매니저를 활용하여 컴포넌트 단위로 이벤트 전환율 측정
    - KPI 달성 여부 확인
    - 아토믹 디자인, page단에서만의 api 통신으로 컴포넌트 간 의존성 낮춤
- 확장가능성
    - 자동 언어 번역
        - 유저풀 확장
    - 어플리케이션 출시
        - 장기 유저 유도

## 개발환경

### Frontend

- Node JS 18.13.0 (LTS)
- React 18.2.0
    - Recoil 0.7.7
- Typescript 4.9.5
- Axios 1.3.6
- Tailwind CSS 3.3.1

### Backend

- Java
    - Java OpenJDK 11
    - Spring Boot 2.7.10
        - Spring Data JPA 2.7.10
        - Spring Security 2.7.10
        - JUnit 5.8.2
        - Lombok 1.18.26
    - Gradle 7.6
- Node JS 18.13.0 (LTS)
- Socket IO 4.6.1\

### Server

- Ubuntu 20.04 LTS
- Nginx 1.18.0
- Docker 23.0.4
- Docker Compose 2.17.2
- MicroK8s (Kubernetes) 1.26.4
- Sonarqube 3.4.0
- Jenkins 2.387.3

### Database

- MySQL 8.0.30
- Redis 7.0.11

### UI / UX

- Figma 93.4.0

### IDE

- Visual Studio Code 1.78.2
- IntelliJ IDEA 2023.1

### 형상 / 이슈관리

- Gitlab
- Jira

### 웹사이트 분석/관리

- Google Analytics 4
- Google Tag Manager
- Hotjar

### 기타 툴

- Postman 10.14.2
- Termius 7.58.7

## 프로젝트 구조

### Frontend (React)

```yaml
Hearting
├── assets
│   └── images
│       ├── logo
│       ├── pixel
│       │   ├── button
│       │   ├── emoji
│       │   └── heart
│       ├── png
│       ├── sharing
│       └── social
├── atoms
├── components
│   ├── Home
│   ├── common
│   ├── heartBoard
│   ├── heartBox
│   ├── heartGuide
│   ├── heartResponse
│   ├── heartTest
│   ├── heartwrting
│   ├── manual
│   ├── modal
│   ├── navbar
│   ├── popUp
│   └── reporting
├── features
│   ├── api
│   └── hook
├── pages
├── styles
└── types
```

### Backend (Spring Boot)

```yaml
Hearting
├── api
│   ├── controller
│   ├── data
│   ├── request
│   ├── response
│   └── service
├── config
├── db
│   ├── domain
│   └── repository
├── exception
├── jwt
├── oauth
│   ├── domain
│   └── info
└── util
```

### Backend (NodeJS)

```yaml
Hearting
├── Dockerfile
├── app.js
├── config.env
├── package-lock.json
├── package.json
├── public
│   ├── index.html
│   └── script.js
└── server.js
```

## 서비스 아키텍쳐

![아키텍처](etc/assets/architecture.png)

## 팀원

- FrontEnd
    - 권오연
    - 서현경
    - 이가은

- BackEnd
    - 손민혁
    - 임영묵
    - 황정주

- Infra
    - 임영묵
