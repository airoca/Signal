# SIGNAL

은하계의 시각적 요소를 활용한 네트워킹 서비스

---

![갤럭시.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/2710357c-bc36-48a7-bf0c-17dcf68d439a/%E1%84%80%E1%85%A2%E1%86%AF%E1%84%85%E1%85%A5%E1%86%A8%E1%84%89%E1%85%B5.gif)

> 우리는 현재 대면하지 않고도, SNS 만으로 새로운 관계를 형성하는 시대에 살고 있습니다. 하지만, 혹시 닿을 수 있는 관계를 놓치고 있지 않으신가요? SIGNAL은 기존 SNS 서비스에 “우주” 라는 요소를 입혀, 사람들 간의 관계를 시각적으로 볼 수 있도록 하는 네트워크 서비스입니다. 그러면 우리의 은하계로 떠나볼까요?
> 

---

## TEAM

- 전윤혁

[cypsyco - Overview](https://github.com/cypsyco)

- 이하영

[lha0 - Overview](https://github.com/lha0)

---

## PROJECT DESCRIPTION

1. **MAIN PAGE**
    
    > **웹사이트의 메인 화면**
    > 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/22d51d3a-da97-401c-b82e-c36d23e35e3f/Untitled.png)
    
    웹사이트에 처음으로 접속하면, 다음과 같이 로그인 페이지가 포함된 메인 화면이 나오게 됩니다. 현재 은하계에 있는 수많은 별들이 보이시나요? 저를 표현하는 새로운 별을 만들기 위해, 회원 가입 페이지로 이동하도록 하겠습니다.
    
    ---
    
2. **로그인, 회원 가입**
    
    > **Server와 DB 기반 로그인, 회원 가입 기능**
    > 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/a052f3dd-4cac-4f8b-b222-5a0398e85cf8/Untitled.png)
    
    회원 가입 화면에서는 사용자의 정보를 입력한 후, 새로운 별을 생성할 수 있습니다.
    
    이 때, 새로운 별의 색상과 좌표는 입력하지 않아도, 범위 내의 무작위 값으로 설정되게 됩니다.
    
    ![로그인.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/871b3f95-2d4c-41c7-86e8-4ca3c1036f8a/%E1%84%85%E1%85%A9%E1%84%80%E1%85%B3%E1%84%8B%E1%85%B5%E1%86%AB.gif)
    
    회원 가입 후 로그인에 성공하면, 위와 같이 새로 생성된 별의 좌표로 자동으로 이동하게 됩니다.
    
    - 회원 관리 추가 기능
        - 확장성을 고려하여 세션 기반 인증이 아닌, JWT Token 기반의 인증을 수행
        - Spring Security을 통해, JWT Token과 SecurityFilterChain을 동시에 사용하여 보안성 향상 (Hashing)
    
    ---
    

1. **프로필**
    
    > **사용자의 별 정보**
    > 
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/8172506b-d03a-48d4-8d57-b5836255dcf5/Untitled.png)
    
    프로필 화면에서는, 현재 사용자가 다른 사람들과 맺은 관계의 현황을 보여주게 됩니다.
    
    - Signal
    
    <프로필의 받은 signal>
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/d0f0eb23-7017-463e-8541-6c40eceb98ab/Untitled.png)
    
    <프로필의 보낸 signal>
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/082a6e87-bcfa-4426-a995-7a5f45b1f7d7/Untitled.png)
    
    SIGNAL에서는 SNS의 팔로워, 팔로잉 개념으로 “signal” 이라는 개념을 사용합니다.
    
    마치 우주에서 신호를 보내는 것처럼, 사용자는 다른 사람에게 signal을 보낼 수도 있고, 받을 수도 있습니다.
    
    ---
    

1. **다른 사용자 검색**
    
    > **다른 사용자의 별로 이동**
    > 
    
    ![다른프로필.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/2a273835-169a-482b-b424-67b3c96a6c3f/%E1%84%83%E1%85%A1%E1%84%85%E1%85%B3%E1%86%AB%E1%84%91%E1%85%B3%E1%84%85%E1%85%A9%E1%84%91%E1%85%B5%E1%86%AF.gif)
    
    상단의 검색 바에 다른 사람의 id를 입력하면, 해당 사용자의 별로 이동하게 됩니다.
    
    ![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/4870facb-ef27-4186-934f-c95fd2e3b231/Untitled.png)
    
    다른 사용자의 프로필 화면에서는 채팅 전송, 시그널 보내기 기능을 이용할 수 있습니다.
    

---

1. **채팅**
    
    > **실시간 채팅 시작**
    > 
    

![채팅.gif](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/6035a56e-64d5-4402-a9f4-f975c87c2bb2/%E1%84%8E%E1%85%A2%E1%84%90%E1%85%B5%E1%86%BC.gif)

채팅 전송 버튼을 누르면 다른 사용자와 실시간 채팅을 이용할 수 있습니다.

채팅 방 정보와 내용은 모두 DB에 식별 되어 저장되기 때문에, 이전 대화 내용을 불러올 수 있습니다.

---

1. **Signal 전송**
    
    > **다른 사용자에게 signal 전송**
    > 

![Untitled](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/64cfa22e-6304-4e64-98e8-84ba35e35cde/Untitled.png)

Signal 보내기 버튼을 누르면, 해당 사용자에게 signal이 전송되게 됩니다. 사용자들의 signal 현황은 실시간으로 확인되는데, 서로 signal을 보낸 사용자는 별자리로 연결됩니다.

---

1. **기술 스택**
    
    ![기술스택.png](https://prod-files-secure.s3.us-west-2.amazonaws.com/f6cb388f-3934-47d6-9928-26d2e10eb0fc/1b5d4ef1-de21-46db-9d81-f6dafaef6aca/%EA%B8%B0%EC%88%A0%EC%8A%A4%ED%83%9D.png)
    
    **Frontend**
    
    - three.js
    - react
    - redux
    - styled components
    - javascript
    - axios

**Backend**

- spring boot
- java
- spring security
- github actions
- aws ec2
- mysql
- docker
