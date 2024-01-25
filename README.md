# SIGNAL

은하계의 시각적 요소를 활용한 네트워킹 서비스

---

<img src="https://github.com/lha0/signal/assets/78598160/a7343a74-b7e6-423b-95ab-9b5d56639c5e" alt="galaxy"/>

> 우리는 현재 대면하지 않고도, SNS 만으로 새로운 관계를 형성하는 시대에 살고 있습니다. 하지만, 혹시 닿을 수 있는 관계를 놓치고 있지 않으신가요? SIGNAL은 기존 SNS 서비스에 “우주” 라는 요소를 입혀, 사람들 간의 관계를 시각적으로 볼 수 있도록 하는 네트워크 서비스입니다. 그러면 우리의 은하계로 떠나볼까요?

---

## TEAM

-   전윤혁

[cypsyco - Overview](https://github.com/cypsyco)

-   이하영

[lha0 - Overview](https://github.com/lha0)

---

## PROJECT DESCRIPTION

1. **MAIN PAGE**

    > **웹사이트의 메인 화면**
    >
    > ![Untitled](https://github.com/lha0/signal/assets/78598160/537f6e3e-50e1-4bfb-93fe-9099f8822ad9)

    웹사이트에 처음으로 접속하면, 다음과 같이 로그인 페이지가 포함된 메인 화면이 나오게 됩니다. 현재 은하계에 있는 수많은 별들이 보이시나요? 저를 표현하는 새로운 별을 만들기 위해, 회원 가입 페이지로 이동하도록 하겠습니다.

    ***

2. **로그인, 회원 가입**

    > **Server와 DB 기반 로그인, 회원 가입 기능**

    ![스크린샷 2024-01-25 오후 6 57 13](https://github.com/lha0/signal/assets/78598160/61770670-11c3-4c69-972c-20fa755c6c5e)

    회원 가입 화면에서는 사용자의 정보를 입력한 후, 새로운 별을 생성할 수 있습니다.

    이 때, 새로운 별의 색상과 좌표는 입력하지 않아도, 범위 내의 무작위 값으로 설정되게 됩니다.

    ![로그인](https://github.com/lha0/signal/assets/78598160/6aaa8095-7c88-490c-8ce3-8753c7a3eab6)

    회원 가입 후 로그인에 성공하면, 위와 같이 새로 생성된 별의 좌표로 자동으로 이동하게 됩니다.

    - 회원 관리 추가 기능
        - 확장성을 고려하여 세션 기반 인증이 아닌, JWT Token 기반의 인증을 수행
        - Spring Security을 통해, JWT Token과 SecurityFilterChain을 동시에 사용하여 보안성 향상 (Hashing)

    ***

3. **프로필**

    > **사용자의 별 정보**

    ![스크린샷 2024-01-25 오후 7 07 43](https://github.com/lha0/signal/assets/78598160/08bb1af9-d874-4444-a67f-e29a922ffd0f)

    프로필 화면에서는, 현재 사용자가 다른 사람들과 맺은 관계의 현황을 보여주게 됩니다.

    - Signal

    <프로필의 받은 signal>

    ![스크린샷 2024-01-25 오후 7 22 20](https://github.com/lha0/signal/assets/78598160/c4cdc6f3-9ffc-458d-a528-d4f92a04529d)

    <프로필의 보낸 signal>

    ![스크린샷 2024-01-25 오후 7 23 14](https://github.com/lha0/signal/assets/78598160/9aadca01-8da7-4f22-96ab-eb1fa14c10b5)

    SIGNAL에서는 SNS의 팔로워, 팔로잉 개념으로 “signal” 이라는 개념을 사용합니다.

    마치 우주에서 신호를 보내는 것처럼, 사용자는 다른 사람에게 signal을 보낼 수도 있고, 받을 수도 있습니다.

    ***

4. **다른 사용자 검색**

    > **다른 사용자의 별로 이동**

    ![다른프로필](https://github.com/lha0/signal/assets/78598160/ec1864f9-351f-414b-ac30-47c6f5a4630a)

    상단의 검색 바에 다른 사람의 id를 입력하면, 해당 사용자의 별로 이동하게 됩니다.

    ![스크린샷 2024-01-25 오후 7 29 35](https://github.com/lha0/signal/assets/78598160/4b8dafec-baff-4f03-80ff-63452c6828fc)

    다른 사용자의 프로필 화면에서는 채팅 전송, 시그널 보내기 기능을 이용할 수 있습니다.

---

5. **채팅**

    > **실시간 채팅 시작**

![채팅 (2) (1)](https://github.com/lha0/signal/assets/78598160/6b56302c-f083-4e8d-8f44-dfa480cb6531)

채팅 전송 버튼을 누르면 다른 사용자와 실시간 채팅을 이용할 수 있습니다.

채팅 방 정보와 내용은 모두 DB에 식별 되어 저장되기 때문에, 이전 대화 내용을 불러올 수 있습니다.

---

6. **Signal 전송**

    > **다른 사용자에게 signal 전송**

![스크린샷 2024-01-25 오후 7 31 33](https://github.com/lha0/signal/assets/78598160/a44df6e5-4d76-42ec-8499-d8da061bae1c)

Signal 보내기 버튼을 누르면, 해당 사용자에게 signal이 전송되게 됩니다. 사용자들의 signal 현황은 실시간으로 확인되는데, 서로 signal을 보낸 사용자는 별자리로 연결됩니다.

—

7. **기술 스택**

    ![기술스택](https://github.com/lha0/signal/assets/78598160/5173ebcc-4bc0-433e-8e5c-f3672701bb2f)

    **Frontend**

    - three.js
    - react
    - redux
    - styled components
    - javascript
    - axios

    **Backend**
    
    -   spring boot
    -   java
    -   spring security
    -   github actions
    -   aws ec2
    -   mysql
    -   docker
