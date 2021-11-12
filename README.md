## 🏃 Running Mate 🏃

![https://user-images.githubusercontent.com/55127012/138738203-9a06b53f-d502-43ca-a999-5a82e5f811e0.png](https://user-images.githubusercontent.com/55127012/138738203-9a06b53f-d502-43ca-a999-5a82e5f811e0.png)

주니어 개발자, 기획자, 디자이너의 포트폴리오 제작을 위한 사이드 프로젝트 팀빌딩 서비스 입니다.

문제해결에 관한 이야기, 개인 테크블로그 주소, 화면설계, Usecase, API에 대한 상세 설명은 Wiki에 포함되어있습니다.

[Go Wiki !](https://github.com/f-lab-edu/running-mate/wiki)

## 🏃‍♂️ 프로젝트 목표

- 객체지향 원리를 이해하고 원칙을 지켜 유지보수에 용이하고 확장성이 높은 코드 작성
- 개발한 기능에 대한 검증과 코드 변화(요구사항)를(을) 대비하여 테스트 코드 작성
- 대용량 트래픽 상황에 맞는 서버성능 개선 및 최적화

## 🏃‍♂️ 사용기술

- Java 11
- Spring Boot
- MyBatis
- MySQL
- Maven

## 🏃‍♂️ 프로토타입

아래 프로토타입은 '카카오 오븐'을 사용해 작성되었습니다.

![prototype](https://user-images.githubusercontent.com/55127012/141405470-7f1910fa-d68b-4130-aa3a-f3bc44704425.png)

## 🏃‍♂️ 브랜치 전략

**Git-Flow**

![https://user-images.githubusercontent.com/55127012/138739316-8bca609d-b3b9-4a1e-a80f-91e2e5e42bd3.png](https://user-images.githubusercontent.com/55127012/138739316-8bca609d-b3b9-4a1e-a80f-91e2e5e42bd3.png)

- main(master) : 제품으로 출시될 수 있는 브랜치 입니다.
- develop : 다음 출시 버전을 개발하는 브랜치 입니다.
- feature : 기능을 개발을 진행하는 브랜치 입니다.
- release : 이번 출시 버전을 준비하는 브랜치 입니다.
- hotfix : 출시 버전에서 발생한 버그를 수정하는 브랜치 입니다.

**PR Rule**

- 새로운 개발건은 develop 브랜치를 베이스로 feature/#이슈번호 의 브랜치명으로 개발을 진행합니다.
- 새로운 개발건에 대한 작업이 완료되면 develop 브랜치를 대상으로 PR을 요청합니다.
- 지정한 리뷰어에게 코드 리뷰를 받고 Approve를 받아야 Merge pull request를 할 수 있습니다.
