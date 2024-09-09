# 뉴스피드 프로젝트
프로그램 역할 : 영화 리뷰를 보는 프로그램
프로젝트 기간 : 2024.06.04 ~ 2024.06.10 (6일)
<br>

## 프로젝트 소개
소개 : 사용자에게 뉴스피드를 보여주는 프로젝트
<details>
<summary>기능</summary>
  <ul>
  <details>
    <summary>프로필 관리</summary>
      <ul>
        <li>프로필 조회 기능</li>
        <li>프로필 수정 기능</li>
      </ul>
  </details>
  <details>
    <summary>뉴스피드 게시물 관리</summary>
      <ul>
        <li>게시물 작성, 조회, 수정, 삭제 기능</li>
        <li>뉴스피드 조회 기능</li>
      </ul>
  </details>
  <details>
    <summary>사용자 인증</summary>
      <ul>
        <li>회원 가입 기능</li>
        <li>회원 탈퇴 기능</li>
  </details>
  <details>
    <summary>친구 관리</summary>
      <ul>
        <li>친구 추가/삭제 기능</li>
        <li>친구 상태라면, 친구의 게시물을 최신으로 조회</li>
      </ul>
  </details>
  </ul>
</details>
<details>
  <summary>도전 기능: 업그레이드 뉴스피드</summary>
  <ul>
    <details>
      <summary>업그레이드 뉴스피드</summary>
      <ul>
        <li>페이지네이션</li>
        <li>정렬 기능</li>
        <li>기간별 검색 기능</li>
      </ul>
    </details>
    <details>
      <summary>댓글</summary>
        <ul>
          <li>댓글 작성, 조회, 수정, 삭제</li>
          <li>댓글 수정,삭는 댓글의 작성자 혹은 게시글의 작성자만 가능</li>
        </ul>
    </details>
    <details>
      <summary>좋아요</summary>
        <ul>
          <li>게시물 및 댓글 좋아요/ 좋아요 취소 기능</li>
        </ul>
    </details>
  </ul>
</details>

## Goals
* 개인 역량 최대한 발휘하기
* 팀원 간의 원활한 소통과 협업을 위해 질문과 논의사항 발생시 적극적인 토론
* Spring Boot에 대한 이해를 바탕으로 프로젝트 완수하기


## Project Rules
<details>
  <summary>개발 환경</summary>
  <ul>
    <li>Java (JDK version 17)</li>
    <li>Spring</li>
    <li>Intellij</li>
  </ul>
</details>
<details>
  <summary>기술 스택</summary>
  <ul>
    <li>Spring JPA</li>
    <li>JWT</li>
    <li>MySQL</li>
  </ul>
</details>

## Github Rules
https://teamsparta.notion.site/Github-Rules-2264168a0ea04e9ea26be4283391045e


## 팀원 구성
![1231](https://github.com/user-attachments/assets/329d49cf-cf2f-4f39-908d-9e5bff26982a)

| 조성래 | 조준호 | 서순균 | 김동규 |
|:---:|:---:|:---:|:---:|
| [@Sungrae-kogi](https://github.com/Sungrae-kogi) | [@juno0432](https://github.com/juno0432) | [@seosg98](https://github.com/seosg98) | [@bronbe](https://github.com/bronbe) |

<br>


## 🤝 역할 분담
* 조성래 : 댓글, 좋아요 관리 기능
* 조준호 : 프로필 관리, 사용자 인증 관리, 친구관리(팔로우)
* 서순균 : 프로필 조회
* 김동규 : 뉴스피드 게시물 관, 업그레이드 뉴스피드
<br>

## ☁ 와이어프레임
https://www.figma.com/file/UewEpKu6CDeGF191zrsYLS?type=design%27&node-id=0:1

## ☁ ERD 다이어그램
![image](https://github.com/user-attachments/assets/711fc3c8-f7d9-4139-8862-5c804f0aa7f6)

<br>

## 📑 API 명세서
![명세1](https://github.com/user-attachments/assets/f2bcc8b4-b5de-4557-a3da-ba1b33b9f6d4)
![명세2](https://github.com/user-attachments/assets/7f4d2023-e759-46e0-a1d6-c81eabc66f78)
![명세3](https://github.com/user-attachments/assets/c506d124-c263-4259-8ab1-b08cbc0bdaa3)
![명세4](https://github.com/user-attachments/assets/39416d41-41cb-4224-b635-35595ad944a1)
![명세5](https://github.com/user-attachments/assets/2724a85b-cc97-4c09-b78e-4b2692fa3de4)
![명세6](https://github.com/user-attachments/assets/37d73c38-b1df-478c-99fd-f75370c7e397)
![명세7](https://github.com/user-attachments/assets/65c1d0ff-0836-4f84-a516-996822247428)
![명세8](https://github.com/user-attachments/assets/0925dc7e-6ef4-4eee-a176-b522f83f04eb)
![명세9](https://github.com/user-attachments/assets/f55e56b0-3e42-4347-bfae-b86d5e3c7665)

<br>

## 📑 프로젝트 후기

#### 조성래
협업 진행간에 git issue 들을 사용해보지 못하고, PR comment 에 적극적으로 참여하지 못한 부분이 아쉽고.
Spring 인증, 인가에 대한 학습이 온전히 이루어지지 못한 상태에서 진행하였기 때문에 어려운 과정을 겪었지만, 진행하며 
팀원에 대한 적극적인 질문과 논의를 수행하여 극복할 수 있는 좋은 경험이었습니다.

#### 조준호
팀원들간의 코드 리뷰가 매우 유익했습니다, 다른분들의 코드를 살펴보며 새로운 관점에서 문제를 바라볼 수 있었고,
이를 통해 개발에 대한 시야를 넓히는 소중한 기회가 되었습니다.
또한 팀원들과의 협업이 원활하게 이루어졌고, 덕분에 더욱 효과적인 결과를 도출할 수 있었습니다.
다만 N+1문제는 아직 완벽하게 해결하지 못했지만, 과제가 끝난 이후에도 깊이 학습할수 있는 기회가 된것 같습니다!

#### 서순균
아직 개발공부가 많이 부족하다는걸 알게 되었고, 팀원분들이 많은 도움을 주셨고 협업이란게 왜 중요한지 다시 알게되었습니다.
아직 많이 부족하고 그러지만 서로 커뮤니케이션이 잘 되었고 적극적으로 다들 참여하여 좋은 경험이 될 수 있었던 프로젝트였던거
같습니다.
#### 김동규
많이 배워갈 수 있었던 뜻깊은 시간이었습니다.
