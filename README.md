# [숨겨진 화백]

예술 문화 가치 증진과 교육 불평등 해소를 위한 유저맞춤형 초기작가, 작품 큐레이션 및 멘토링 중개 서비스

```text
숨겨진 작가와 작품이 빛을 발하는 그날까지
```

## 🎯서비스 배경 및 목표

홍보성과 정보 접근성이 부족한 초기 작가와 작품들을 대중들에게 소개함으로써 예술의 대중화 및 문화가치 증진을 주 목적으로합니다.
<br/>
최종적으로 대중들의 문화 수준을 향상시키고, 예술 교육 격차를 해소함으로써 사회의 지속가능한 발전을 목표로 합니다.

## 👥 팀 구성

- 오한영: 프로젝트 관리 및 어드민페이지 개발
- 김기홍: 백엔드 API 개발 및 로컬 테스트환경, CI/CD 파이프라인 구축
- 김현주: React 기반 프론트엔드 UI/UX 개발

## 🛠️ 기술 스택

| 분류       | 기술 명                                                                                  |
|----------|---------------------------------------------------------------------------------------|
| FrontEnd | JavaScript, axios, React, Figma                                                       |
| BackEnd  | Java 17, Spring(Boot 3.3.2, Security, JPA), QueryDsl, JUnit, Test Containers, Mockito |
| DataBase | MySQL 8, Redis                                                                        |
| DevOps   | Docker, NGINX, (AWS) ECS, ECR, RDS, ELB, CodeDeploy, GithubActions                    |
| Tools    | IntelliJ, WebStorm, nGrinder, Notion, Slack                                           |

## 📀 아키텍처

![HiddenArtist_Architecture](readme/images/HiddenArtist_Architecture.png)

## 🗃️ ERD

- 추가예정

## 📂 패키지 구조

```text
HiddenArtist
    ├─.github
    │    ├─workflows
    │    └─ISSUE_TEMPLATE
    │
    ├─docker
    │    └─docker-compose.yml
    │
    ├─frontend
    │     ├─dist/...
    │     ├─public/...
    │     └─src
    │        └─components/...
    │
    ├─backend
    │    └─src
    │       ├─main/java/com/hiddenartist/backend
    │       │             ├─domain
    │       │             │    └─api
    │       │             │       ├─controller
    │       │             │       │     ├─request
    │       │             │       │     └─response
    │       │             │       ├─persistence
    │       │             │       │     ├─entity...
    │       │             │       │     ├─type
    │       │             │       │     └─repository
    │       │             │       └─service
    │       │             │
    │       │             └─global
    │       │                 ├─config
    │       │                 ├─exception
    │       │                 ├─security
    │       │                 ├─redis
    │       │                 ├─aop
    │       │                 ├─utils
    │       │                 └─ ...
    │       └─test/...
    └─README.md
```

## 🚀 기술적 경험

- 추가예정
