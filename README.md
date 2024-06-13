<img width="627" alt="image" src="https://github.com/hacomarch/RandomWriting/assets/64404604/b821de15-e358-470f-bb76-88dc97d9764f">

# Random Writing - 랜덤 글쓰기 주제 추천 웹 사이트

## 개요
사용자에게 랜덤으로 글쓰기 주제를 추천하고 글을 쓰며 댓글 및 좋아요를 통해 다른 사용자와 소통할 수 있는 서비스입니다.

<br>

## 진행 일정
2022.11 - 2022.12

<br>

## 사용 스택
<img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F?style=for-the-badge&logo=Spring&logoColor=white"> <img src="https://img.shields.io/badge/Spring%20WebFlux-FFA500?style=for-the-badge&logo=WebFlux&logoColor=white">
<br>
<img src="https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=HTML5&logoColor=white"> <img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=CSS3&logoColor=white"> <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white">
<br>
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white">
<br>
<img src="https://img.shields.io/badge/Naver%20Clova%20API-03C75A?style=for-the-badge&logo=Naver&logoColor=white">

<br>

## ERD
<img width="613" alt="image" src="https://github.com/hacomarch/RandomWriting/assets/64404604/e633453d-a741-4422-a4aa-f5ce5d7ca86d">

## 주요 기능

### 1. 로그인 및 회원 관리 기능 
회원 가입, 회원 탈퇴, 회원 정보 수정 기능을 구현했습니다.

### 2. 게시글 및 댓글 및 좋아요 기능
게시글 작성, 수정, 삭제, 다른 사용자의 글 조회, 글 목록 조회 기능을 구현했습니다.

### 3. 랜덤 주제 추천 기능 
난수를 생성해 주제가 저장되어 있는 테이블에서 난수 인덱스를 인수로 전달해 그 인수에 해당하는 주제를 가져옵니다.

### 4. 글 감정 분석 후 동영상 추천 기능
사용자가 글을 쓰면 그 글을 Naver Clova API를 통해 감정을 분석합니다. 긍정, 부정, 중립으로 나누어 결과를 받은 후, 부정의 감정을 띈 글이 5개 넘으면 유튜브의 힐링 영상 리스트로 이동합니다.

<br>

## 시연 동영상

https://github.com/hacomarch/RandomWriting/assets/64404604/03874891-5256-48c0-9ad3-40087480b9d0



