# 뉴스사이트&키워드 자동수집

## 개요
- 버전 1.0에서는 Google 로그인만 가능합니다.
- 뉴스 수집 사이트는 Google, Naver, Daum 뉴스로만 구성되어 있습니다.
- 수집 라이브러리로는 Selenium을 사용했습니다.
- Chrome 드라이버 버전은 121을 사용합니다.

## 개선 사항 (Next Version)
- 다중 스레딩, 병렬 처리, 동기화를 통해 성능을 향상시킬 수 있을 것으로 기대됩니다.
- Selenium 관련 예외 처리 추가 .
- oauth 로그인 naver,kakao 추가
- jwt 토큰 및 시큐리티 성공,실패,예외 핸들링 추가
- 뷰 화면 개선 

## 배포 시나리오
1. Git에서 변경사항이 발생하면 Travis를 통해 자동 배포됩니다.
2. Travis는 Git의 main 브랜치로 푸시될 때만 변경사항을 받습니다.
3. Travis.yml 파일에서 AWS S3 및 CodeDeploy 설정을 합니다.
4. AWS에서 CodeDeploy를 설정한 후 EC2와 연결합니다.
5. EC2에서 CLI 명령어를 사용하여 저장된 프로젝트를 빌드하는 명령 파일을 실행합니다.

## 배포 순서
1. Git → Travis → S3 → CodeDeploy → EC2
2. Nginx를 사용하여 끊임없는 배포를 운영할 계획입니다.
