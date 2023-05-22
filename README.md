# 하팅!

## 배너필요!!

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
  - 하트판은 나만의 공간이자 URL을 통해 접속한 사람만이 볼 수 있는 공간으로 최근 받은 하트를 확인하고 메시지를 작성할 수 있습니다.
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
- **신고하기**
  - 비방·비하·욕설 메시지는 신고할 수 있습니다.

## 하팅! 서비스 화면

<img src="etc/assets/screen1.png" width="500px">

### 홈 화면

- 체험 해보기를 누르거나 스크롤을 내리면 간단히 서비스를 소개합니다.
- 개발팀의 하트 수신함 보러가기를 누르면 개발팀의 `하트 수신함`으로 이동합니다.

로그인 상태

<img src="etc/assets/home_login.gif" width="200px" >

- `나의 하트판 가기` 버튼을 누르면 `나의 하트판(하트 수신함)`으로 이동합니다.

로그아웃 상태

<img src="etc/assets/home_Logout.gif" width="200px" >

- 카카오, 트위터, 구글 소셜 로그인을 지원합니다.

### 나의 하트 수신함 (받은 하트)

<img src="etc/assets/heartboard.gif" width="200px" >

- 새로운 메시지는 빨간 점으로 표시됩니다.
- 나의 `하트 수신함` 버튼을 눌러 링크, 페이스북, 트위터, 카카오톡으로 공유 가능합니다.
  - 카카오톡 공유에 최적화되어 있습니다.
    [카카오공유]
- 각 `하트`를 클릭하면 해당 메시지를 모달과 함께 확인할 수 있습니다.
  - 남은 시간(타이머), 남긴 반응을 확인할 수 있습니다.
  - 반응, 삭제, 신고할 수 있습니다.

### 하트 수신함

<img src="etc/assets/heartboard_other.gif" width="200px" >

- 다른 사용자의 하트 수신함에서 `하트 보내기` 버튼을 메시지 작성 페이지로 이동합니다.
- 로그인 되어있지 않고, 자신의 수신함 아니면 메시지를 읽을 수 없습니다.
  - 이 경우, `하트`를 클릭하면 부르르 애니메이션으로 상호작용 합니다.

### 메시지 작성

<img src="etc/assets/write.gif" width="200px" >

- 획득한 하트 중에서 하나를 선택합니다.
- 제목은 필수로 작성하고, 내용은 선택적으로 작성합니다.
- 모두 작성했으면 전달하기를 누릅니다.

### 영구 보관함

<img src="etc/assets/heartbox.gif" width="200px" >

- `하트`가 사라지기 전에 저장한 경우 영구 보관함에 보관할 수 있습니다.
- 각 버튼을 클릭하면 해당 메시지를 모달과 함께 확인할 수 있습니다.
  - 받은 날짜, 남긴 반응을 확인할 수 있습니다.
  - 반응, 삭제, 신고할 수 있습니다.

### 하트 도감

<img src="etc/assets/heartguide.gif" width="200px" >

- 메시지 작성이 가능한 다양한 `하트`들을 확인할 수 있습니다.
- 획득하지 못한 `하트`는 자물쇠로 잠겨있습니다.
- 획득 조건을 달성하면 빨간 점으로 표시됩니다.
- 각 `하트`를 클릭하면 모달과 함께 스토리, 획득 조건을 확인할 수 있습니다.

### 실시간 알림

<img src="etc/assets/alarm.gif" width="200px" >

- 메시지, 반응 수신 및 새로운 스페셜 하트 획득 시 실시간으로 받을 수 있습니다.
- 읽은 알림과 안읽은 알림은 구분됩니다.
- 받은 시점으로부터 시간 경과를 알 수 있습니다.
- 안읽은 알림이 있으면 빨간 점으로 표시됩니다.

### 사용설명서

<img src="etc/assets/manual.gif" width="200px" >

- 하팅의 기능을 간단한 설명과 사진으로 설명합니다.

### 하트 테스트

<img src="etc/assets/hearttest.gif" width="200px" >

- 자신의 심볼 하트를 확인할 수 있습니다.
- 링크, 페이스북, 트위터, 카카오톡으로 결과 페이지를 공유할 수 있습니다.
  - 카카오톡 공유에 최적화되어 있습니다.

### 신고하기

<img src="etc/assets/report.gif" width="200px" >

- 메시지 우측 상단에 위치한 `신고하기` 버튼을 눌러 진행합니다.
- 메시지별로 한 번만 신고할 수 있고, 누적 신고 당한 횟수가 3회인 유저는 3일간 계정 일시 정지가 됩니다.
- <img src="etc/assets/pause.png">
- 누적 신고 당한 횟수가 5회에 도달하면 해당 유저는 계정 영구 정지 처리가 됩니다.

## 기술 차별점

### 유저 경험 향상

- WebSocket 전용 서버 사용
  - 하트를 받은 유저의 메인 페이지에 실시간 알람을 구현함으로써 유저경험을 향상시켰습니다.
  - 웹소켓 실시간 통신의 원활한 구동을 위해 node.js로 별도의 웹소켓 백엔드 서버를 구현했습니다.
  - 특히 향후 사용자 피드백을 통한 추가 실시간 기능 추가 필요 시 메인 백엔드 서버의 부하를 덜 수 있습니다.
  - Kubernetes Deployment로 다수 node.js 서버를 운영하기 위해 redis cluster를 통해 각 node.js 서버 상태공유를 구현하였습니다.

### 대규모 트래픽 대응

- Kubernetes Cluster 사용
  - 대규모 트래픽 발생에 대비하여 백엔드 서버(Spring Boot, node.js)를 Kubernetes Deployment로 관리하여 다수의 백엔드 서버를 구동하고 요청에 대한 Load Balancing을 구현하였습니다. 이로써 각각의 서버에 대한 부담을 줄여 백엔드 안정성을 향상시킵니다.
  - Kubernetes를 활용함으로써 관리하는 서버의 항상성을 보장합니다. 몇 개의 서버가 다운되어도, 다른 서버가 살아있기 때문에 전체 서비스가 다운될 가능성을 줄이고, 다운된 서버를 쿠버네티스가 다시 구동시켜 안정적인 시스템 구축을 지원합니다.
  - Kubernetes를 활용하여 Rolling Update를 구현했습니다. Agile하게 개발하여 배포 횟수가 잦은 만큼 서비스에 지장 없이 신규 버전을 배포하는 것이 중요하다고 생각하였습니다.
  - 또한, 다수의 node.js 서버를 두어도 웹소켓 통신이 원활하게 하기 위해 Redis Cluster로 node.js 서버들의 상태관리를 할 계획입니다. 이 Redis Cluster는 Kubernetes StatefulSet로 관리합니다.
- Redis 캐싱 및 Replication
  - redis는 인메모리 구조 데이터베이스로, redis 캐싱을 이용하면 MySQL 보다 빠르게 데이터에 접근할 수 있습니다.
  - redis Hash 자료구조를 사용하여 기존의 하트 획득 로직 성능을 O(M\*N) 에서 O(1)로 최적화하였습니다.
  - read가 많은 데이터를 레디스에 저장하여 API 응답 속도를 최적화했습니다.
  - 장애를 대비하여 replication을 구축해 사용성을 높였습니다.

### 반응형 웹

- 독자적 환산단위 적용한 반응형 웹 구현
  - 모바일 환경에서 url바와 하단의 네비게이션 바로 윗부분 혹은 아랫부분이 잘리는 현상이 발생했습니다.
  - 이를 해결하기 위해, 자바스크립트의 가장 상단에서 innerHeight의 1%의 값을 구해 --vh라는 속성으로 새로 정의하는 함수를 만들어 호출했습니다. 이를 기준으로 레이아웃을 배치해 호환성을 높였습니다.
- 기기 상관 없는 안정적인 뷰 제공
  - 데스크탑 환경에서 핸드폰 화면처럼 좁게 보이게 적용하거나, 사이드바는 환경마다 열리는 위치가 달라지게 적용하는 등 다양한 이벤트에서 최적화된 화면을 제공하려 노력했습니다.

### 데이터 기반 의사결정

- 웹사이트 분석 툴
  - Google Analytics
    - <img src="etc/assets/ga.png" >
  - Hotjar
    - recording
    - <img src="etc/assets/hotjar1.gif" >
    - heatmap
    - <img src="etc/assets/hotjar2.png" >
  - Google Tag Manager
    - <img src="etc/assets/gtm.png" >
    - GA4, Hotjar에서 기본으로 제공하는 데이터로 사용자 흐름을 분석하기에 어려움이 있었습니다. 또한 저희 서비스가 한페이지에서 다양한 이벤트가 일어나는 특성상 정확한 이벤트 별 지표가 필요하다고 판단했습니다.
    - Google Tag Manager를 사용해 트리거-변수를 묶은 태그 단위로 이벤트 전환율을 측정하여 KPI(성과 달성 지표)로 사용할 수 있었습니다.

### 아토믹 디자인

- page단에서만의 api 통신으로 컴포넌트 간 의존성을 낮췄습니다.

### 확장가능성

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

## 와이어프레임

## ERD

![ERD](docs/hearting_erd.png)

## 서비스 아키텍쳐

![아키텍처](docs/hearting_architecture.png)

## 협업 환경

### Git으로 협업하기

Git을 통한 협업 방식은 [우아한 형제들 Git Flow](https://techblog.woowahan.com/2553/)를 기본 베이스로 삼았습니다.

브랜치는 master, develop, feature 총 3가지를 사용했으며 전략은 다음과 같습니다.

- `master`: 서비스가 출시될 수 있는 브랜치입니다. master 브랜치에 올라온 기능들은 에러 없이 작동하는 상태입니다.

- `develop`: 다음 서비스 출시를 위해 실제 개발이 이루어지는 브랜치입니다.

- `feature`: 기능 단위 개발을 위한 브랜치로 develop에서 분기하여 개발이 끝나면 각각 베이스 브랜치로 병합됩니다.

매주 한 번 이상 모든 feature 브랜치를 develop 브랜치로 병합 후 배포하여 실제 배포 환경에서 잘 동작하는지 여부를 확인했습니다.

### Jira로 협업하기

매주 월요일 스프린트 회의를 통해 그 주의 목표를 세우고 목표 달성을 위한 구체적인 작업들을 정리했습니다.

유튜브 라이브와 같이 공통적인 일정부터 팀 회의, 파트별 회의, 개인 개발 작업까지 회의를 통해 구체적으로 계획했습니다.

이를 위해 사용된 요소들은 다음과 같습니다.

`에픽`: 어떤 작업이 속하는 최상위 레벨로 학습, 설계, 회의, 개발, 공통 총 5가지 에픽을 만들었습니다.

`스토리`: 에픽에 속하는 작업의 단위입니다. 구체적인 작업 내용을 작성하고 스토리 포인트로 예상 소요 시간을 산정할 수 있습니다. 한 스토리 당 최대 4시간을 넘지 않게 하였고 개인별로 매주 40시간 이상 할당했습니다.

`번다운 차트`: 스프린트의 목표를 달성하기 위해 남은 시간과 남은 스토리 포인트를 확인해 프로젝트의 진척도를 파악할 수 있는 지표입니다.

### Notion으로 협업하기

회의록, 스크럼 회의, 발표 정리, 문서 정리, 기획서 등 자료들을 Notion을 통해 작성 및 관리하였습니다.

- `회의록`: 매일 회의한 내용을 회의록으로 기록하였습니다.
- `프로젝트 일지`: 개인별로 프로젝트 일지(problem-cause-solution)를 작성하였습니다. 프로젝트 일지는 개발하면서 만난 오류와 문제 상황을 정리하고 원인과 해결방법을 적습니다.
- `컨벤션`: 프로젝트의 모든 컨벤션들을 문서화하여 모두가 공유 가능하도록 하였습니다. 기록 및 정리한 컨벤션들에는 Git 컨벤션, Jira 컨벤션, FE 컨벤션, BE 컨벤션이 있습니다.
- `프로젝트 문서 관리`: 요구사항 정의서, 기능명세서, 일정관리 등 공유 문서 관리를 노션에 기록하여 모두가 동일한 목표를 가지고 개발 할 수 있도록 하였습니다.

## 팀원

### **Chillin'** 팀 소개

|                                          권오연                                           |                                          손민혁                                           |                                          서현경                                           |                                          이가은                                           |                                          임영묵                                           |                                          황정주                                           |
| :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: | :---------------------------------------------------------------------------------------: |
|                                      [Ohyeon Kwon]()                                      |                        [Minhyeok Son](https://github.com/sonmh79)                         |                                    [Hyeonkyeong Seo]()                                    |                          [Gaeun Lee](https://github.com/haegomm)                          |                     [Youngmook Lim](https://github.com/Youngmook-Lim)                     |                                     [Jeongju Hwang]()                                     |
| <img src="https://avatars.githubusercontent.com/u/94703258?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/78152114?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/94703258?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/95625643?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/94703258?v=4" width="100" height="100"> | <img src="https://avatars.githubusercontent.com/u/94703258?v=4" width="100" height="100"> |

### 팀원 역할

- FrontEnd

  - 권오연
  - 서현경
  - 이가은

- BackEnd

  - 손민혁
  - 임영묵
    - Spring Boot 서버 REST API 개발 (받은메시지 및 메시지 공통기능 API 개발)
    - TDD(Test Driven Development) 기반 테스트 코드 작성
    - Node.js 웹소켓 서버 개발
  - 황정주

- Infra
  - 임영묵
    - 전체 아키텍쳐 설계
    - Jenkins CI/CD Pipeline를 통한 빌드/배포 자동화 시스템 구축
    - Kubernetes 클러스터 구축
    - Node.js 서버 인스턴스 간 상태 공유를 위한 Redis 클러스터 구축
    - AWS EC2 환경설정 (Nginx, Docker, MySQL, 방화벽 설정 등)
    - DockerHub를 활용한 서버 이미지 버전 관리

## 프로젝트 산출물

- [기능명세서](docs/hearting_%EA%B8%B0%EB%8A%A5%EB%AA%85%EC%84%B8%EC%84%9C.pdf)
- [2차 배포 반영사항](docs/hearting_2%EC%B0%A8%EB%B0%B0%ED%8F%AC%EB%B0%98%EC%98%81%EC%82%AC%ED%95%AD.pdf)
- [3차 배포 반영사항](docs/hearting_3%EC%B0%A8%EB%B0%B0%ED%8F%AC%EB%B0%98%EC%98%81%EC%82%AC%ED%95%AD.pdf)
- [와이어프레임](docs/hearting_wireframe.png)
- [ERD](docs/hearting_erd.png)
- [아키텍쳐](docs/hearting_architecture.png)
- [API 문서](docs/hearting_api_docs.pdf)
- [Git Convention](docs/hearting_git_convention.pdf)
- [포팅매뉴얼](exec/hearting_%ED%8F%AC%ED%8C%85%EB%A7%A4%EB%89%B4%EC%96%BC.pdf)

## 프로젝트 발표자료

- [중간발표 Presentation](docs/hearting_%EC%A4%91%EA%B0%84%EB%B0%9C%ED%91%9C%EC%9E%90%EB%A3%8C.pdf)
- [최종발표 Presentation](docs/hearting_%EC%B5%9C%EC%A2%85%EB%B0%9C%ED%91%9C%EC%9E%90%EB%A3%8C.pdf)

## 회고
