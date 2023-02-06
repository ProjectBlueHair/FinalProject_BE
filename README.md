# oncounter_BE
<img src="https://user-images.githubusercontent.com/117705848/216882220-2e3e844e-c30a-40ce-9474-817a41338240.jpg" width=500px height=500px ></img>
* 발표 영상 (준비중)
* 시연 영상 (준비중)


## 🎙프로젝트 소개
[**oncounter.co.kr**](https://oncounter.co.kr/)
* 하나의 웹 서비스를 위해 프론트엔드, 백엔드, 디자이너, 데브옵스 등 많은 분야의 사람들이 필요한 것처럼, 하나의 음악을 만드는 것 역시 다양한 분야의 사람들을 필요로 합니다. 문제는 그 다양한 분야의 사람들을 모으거나, 어떤 프로젝트에 참여할 수 있는 수단이 마땅치 않다는 점에 있습니다.
* **Oncounter**는 음악 관련 협업을 진행하고자 하는 사람, 혹은 본인의 음악을 어필하고 싶은 사람들을 타겟으로, 다양한 음악적 분야에 종사하는 사람들이 서로의 음악을 듣고, 협업하고, 작품을 출시할 수 있는 서비스를 제공하고자 합니다.
## 👥팀 멤버
### Front End
* [조무결](https://github.com/mugyeol), [김동현](https://github.com/kdh8615)
### Back End
* [이상직](https://github.com/JeekLee), [이진홍](https://github.com/sooni2), [윤진국](https://github.com/GGONG1956)
### Design
* 김지민


## 🛠기술 스택

<p align=justify>

<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/redis-DC382D?style=for-the-badge&logo=redis&logoColor=white">
<img src="https://img.shields.io/badge/githubactions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white">
<img src="https://img.shields.io/badge/Spring boot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white">
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Gradle&logoColor=white">
<img src="https://img.shields.io/badge/GRADLE-02303A?style=for-the-badge&logo=Spring Security&logoColor=white">
<img src="https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON Web Tokens&logoColor=white">
<img src="https://img.shields.io/badge/QueryDSL-0769AD?style=for-the-badge&logo=jQuery&logoColor=white">
<img src="https://img.shields.io/badge/Elastic Search-005571?style=for-the-badge&logo=Elasticsearch&logoColor=white">
<img src="https://img.shields.io/badge/Socket.io-010101?style=for-the-badge&logo=Socket.io&logoColor=white">
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white">
<img src="https://img.shields.io/badge/aws-232F3E?style=for-the-badge&logo=Amazon AWS&logoColor=white">
<img src="https://img.shields.io/badge/RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white">
<img src="https://img.shields.io/badge/S3-569A31?style=for-the-badge&logo=Amazon S3&logoColor=white">
<img src="https://img.shields.io/badge/EC2-FF9900?style=for-the-badge&logo=Amazon EC2&logoColor=white">
<img src="https://img.shields.io/badge/ngnix-009639?style=for-the-badge&logo=nginx&logoColor=white">
<img src="https://img.shields.io/badge/Swagger-85EA2D?style=for-the-badge&logo=Swagger&logoColor=white">
</p>

## 📋서비스 아키텍쳐
![아키텍쳐](https://user-images.githubusercontent.com/117705848/216890320-9c6807b3-5e94-4ed6-974a-fd3b1ab2627e.png)

## ⚙ERD
* 추가 예정


   
   
## 📌API 명세서

* [oncounter Swagger UI](http://3.38.16.236:8080/swagger-ui/index.html)

<img width="966" alt="스크린샷 2023-02-06 오전 11 55 42" src="https://user-images.githubusercontent.com/117705848/216873264-61b82913-06d5-44af-9e20-c2624410f6be.png">
<img width="962" alt="스크린샷 2023-02-06 오전 11 56 02" src="https://user-images.githubusercontent.com/117705848/216873274-300df40c-0662-4ff8-89ac-6fe3660a0a77.png">
<img width="963" alt="스크린샷 2023-02-06 오전 11 56 47" src="https://user-images.githubusercontent.com/117705848/216873289-ca2d5d9a-4e75-4da6-bdad-ac0ac5e1955c.png">

## 주요 기능
### 메인페이지

* 메인 페이지에서는 사람들이 만든 다양한 음악들을 들어볼 수 있습니다. 마음에 드는 곡의 플레이 버튼을 눌러 재생하고, 하단 플레이어를 통해 탐색, 볼륨 조절 등의 기능을 사용해보세요!

<img width="1508" alt="스크린샷 2023-02-06 오후 12 43 36" src="https://user-images.githubusercontent.com/117705848/216879543-254585d9-75eb-4bfc-8076-d3a386db765e.png">


### 상세페이지

* 곡의 썸네일을 클릭하면 **상세 페이지**로 이동할 수 있습니다. 상단 플레이어의 `🔈버튼` , `**oncounter** 로고 버튼`, `볼륨 컨트롤러` 통해 각 음원들을 조작해보세요.

* 마음에 드는 곡이라면, 왼쪽 `❤️버튼`을 통해 좋아요 하는 것도 잊지 말아주세요!

* 곡을 어딘가에 담아두고 싶으시다면, `보관함 추가 버튼` 을 통해  저장하거나 `공유 버튼`을 통해 SNS에 공유해보세요! 마음에 드는 뮤지션을 발견했다면 `팔로우 버튼`으로 팔로우 해보세요!

<img width="1487" alt="스크린샷 2023-02-06 오후 12 44 39" src="https://user-images.githubusercontent.com/117705848/216879574-c7dcac3c-c5e3-4c13-b2bc-6e3fec2d6f9a.png">

### 마이페이지/계정설정

* 관심있는 유저의 프로필 사진이나, 닉네임을 클릭하면 유저 페이지로 이동할 수 있습니다. 왼쪽 유저 정보를 통해 그 유저의 `SNS에 방문`하거나, `다이렉트 메세지`를 보내보세요. 해당 유저가 보관한 게시글이나, 작성한 게시글도 확인해볼 수 있습니다!

<img width="1510" alt="스크린샷 2023-02-06 오후 12 54 35" src="https://user-images.githubusercontent.com/117705848/216880452-c3d52df7-799e-4a19-b753-a356f0e6dc82.png">

* 계정 설정을 통해 `닉네임과 비밀번호, 프로필 사진을 수정`할 수 있습니다. 내 `SNS 계정을 추가`하고, 나에 대한 소개를 작성해보세요! 각 기능 별 `알림 설정`도 할 수 있답니다!

<img width="1302" alt="스크린샷 2023-02-06 오후 12 55 10" src="https://user-images.githubusercontent.com/117705848/216880622-8f952d0c-10f1-4278-8b07-7d0ecee31650.png">

### 다이렉트 메세지

* 함께 작업을 하고 싶은 유저나, 응원하고 싶은 유저에게 1:1 다이렉트 메세지를 보내보세요! 다이렉트 메세지는 상단 `🗨️ 버튼`을 통해 언제든지 사용할 수 있습니다!

<img width="1487" alt="스크린샷 2023-02-06 오후 1 04 32" src="https://user-images.githubusercontent.com/117705848/216881391-94615c36-3f34-4096-8107-787953f11722.png">

* oncounter의 서비스 기능들에 대한 더 자세한 내용은 아래 `사용자 가이드`를 참조해주시길 바랍니다.

## 📒사용자 가이드

* [oncounter 사용자가이드](https://protective-whale-78f.notion.site/bb305c9f0a75495290c2ed47348aff6f)  


