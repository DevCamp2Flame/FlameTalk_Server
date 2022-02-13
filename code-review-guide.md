# Code Review Guide
SGS 데브캠프 2기 flame 팀의 김다롬입니다.

전체적인 프로젝트 설명이나, 아키텍처 구조는 [README.md](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/README.md) 에서 확인하실 수 있습니다.

## 폴더 구조
```shell
├─auth        # 인증 서버
├─chat        # 채팅 서버 (채팅 메시지 송수신)
├─chat-api    # 채팅 API 서버 (채팅방 생성 및 사용자 목록 불러오기)
├─common      # 여러 서버에서 공통적으로 사용하는 공통 모듈 - 현재 사용하지 않습니다.
├─file        # 파일 서버 (프로필 이미지 등 각종 파일 관리)
├─infra       # Spring Cloud 서버 (서비스 디스커버리, 게이트웨이)
│  ├─eureka
│  └─gateway
├─membership  # 멤버십 서버
└─presence    # 접속 상태 서버 (채팅방 입/퇴장 기록)
```

## 역할
저는 크게 4가지 서버를 맡아 구현했습니다.
1. 인증 서버 (auth)
2. Spring Cloud 서버 (eureka, gateway)
3. 채팅 서버 (chat)
4. 접속 상태 서버 (presence)

## 코드 설명
### 1. 인증 서버
인증 서버에서는 Spring Security 와 JWT 를 활용하여 사용자 인증 처리를 하고 있습니다.
특히 로그인시 발급되는 JWT 토큰은 추후 다른 서버에 접근 할 때 사용하게 됩니다.

토큰 탈취 이슈에 대응하기 위하여 사용자가 request 로 보낸 토큰과, DB 에 저장된 토큰을 비교하여 불일치할 경우 토큰이 탈취된 것으로 간주하여 다시 로그인하도록 유도했습니다.
매 요청마다 DB 정보를 확인해야 하므로 Key-Value 형식의 빠른 redis 를 DB로 사용했습니다.

**관련 코드**

- [JWT 토큰 생성 및 검증](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/auth/src/main/java/com/devcamp/flametalk/global/util/JwtTokenProvider.java)
- [request 의 토큰과 DB 토큰 일치 여부 판단](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/auth/src/main/java/com/devcamp/flametalk/domain/token/service/TokenService.java)
- [사용자 컨트롤러](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/auth/src/main/java/com/devcamp/flametalk/domain/user/controller/UserController.java)
- [사용자 서비스](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/auth/src/main/java/com/devcamp/flametalk/domain/user/service/UserService.java)

### 2. API Gateway
`Spring Cloud 서버 (eureka, gateway)` 중 API Gateway 의 역할을 중점으로 구성했습니다.

![FlameTalk_Architecture-API Gateway 플로우](https://user-images.githubusercontent.com/44438366/153760233-42a9e73f-a789-4378-80ef-80303f1c836a.png)

모든 사용자 요청은 Gateway 를 먼저 만나게 되는데, 인증 서버일 경우, 인증 서버의 자체적인 검증 로직을 거칩니다.
반면, 그 이외의 모든 서버는 Gateway 의 AddRequestFilter 를 거치게 됩니다.

AddRequestFilter 에서는 인증 서버에 JWT 토큰을 전달하여 아래 3가지를 확인합니다.
1. 유효한 토큰인지
2. token DB 에 저장된 토큰과 일치하는지
3. 토큰 정보와 일치하는 사용자가 있는지 

세 가지 모두 성공적으로 확인했다면, 내부 서버로 라우팅될 때 요청 헤더에 기존에 있던 JWT 토큰 정보를 제거하고,
USER-ID, DEVICE-ID 를 추가하여 전달합니다.

> JWT 토큰이 flametalk 서버에서 생성한 토큰인지만 확인하고 라우팅 시키고 싶었지만,
> 현재 common 모듈이 제대로 동작하지 않아 공통 코드를 제대로 활용하지 못하여 매번 인증 서버를 거쳐 검증하는 로직으로 구현했습니다.
> 대신 인증 서버를 여러대로 구성하여 부하를 분산시키는 방법을 고민해봤습니다.
> 
> 반면에 이런 검증 과정을 거침으로써 내부의 서버에서는 안전하게 헤더의 정보를 믿고, 활용할 수 있습니다.
> 
> 리팩토링을 한다면 common 모듈의 버그를 해결하여 JWT 토큰 관련 코드를 common 으로 옮겨
> gateway 에서도 토큰 검증을 코드 중복 없이 사용할 수 있도록 할 것입니다.
> 이렇게 된다면 현재 인증 서버의 JWT filter 부분의 미완성 부분도 gateway 에서 처리가능할 것으로 예상합니다.


**관련 코드**

- [게이트웨이 필터: AddRequestFilter](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/infra/gateway/src/main/java/com/devcamp/flametalk/gateway/filter/AddRequestHeaderFilter.java)
- `인증 서버에서 사용자 검증` 링크의 getUserIfPresent 메소드
  - [/auth/domain/user/controller/UserController.java](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/auth/src/main/java/com/devcamp/flametalk/domain/user/controller/UserController.java)
  - [/auth/domain/user/service/UserService.java](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/auth/src/main/java/com/devcamp/flametalk/domain/user/service/UserService.java)
- [인증 서버의 JWT filter](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/auth/src/main/java/com/devcamp/flametalk/global/util/JwtAuthenticationFilter.java)


### 3. 채팅 서버
채팅 서버에서는 STOMP 를 활용하여 웹 소켓 연결을 맺고, kafka 브로커를 활용하여 구독/발행 구조로 메시지를 송수신하고 있습니다.
또한 docker 에 cassandra 를 올려 사용하여 채팅 메시지를 저장 및 관리하고 있습니다.

특징은 요청이 웹 소켓 요청인지 HTTP 요청인지에 따라 컨트롤러와 Service 를 분리했다는 점을 꼽을 수 있습니다.

관련 코드
- 웹 소켓
  - [채팅 컨트롤러](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/chat/src/main/java/com/devcamp/flametalk/contoller/StompChatController.java)
  - [채팅 Receiver](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/chat/src/main/java/com/devcamp/flametalk/service/Receiver.java)
  - [채팅 Sender](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/chat/src/main/java/com/devcamp/flametalk/service/Sender.java)
- HTTP
  - [채팅 메시지 컨트롤러](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/chat/src/main/java/com/devcamp/flametalk/contoller/ChatMessageController.java)
  - [채팅 메시지 서비스](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/chat/src/main/java/com/devcamp/flametalk/service/ChatService.java)

### 4. 접속 상태 서버
접속 상태 서버에서는 기본 WebSocket 으로 웹 소켓 연결을 맺고, 사용자가 채팅방에 접속한 상태인지 아닌지를 기록합니다.
채팅방 입장과 퇴장은 빈번하게 일어나고, 해당 채팅방에 접속한 사용자의 목록을 빠르게 알아야 하기 때문에 Set 자료구조를 활용하여 redis 에 저장했습니다.

관련 코드
- [웹 소켓 메시지 분석하여 입장과 퇴장 기록](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/presence/src/main/java/com/devcamp/flametalk/presence/config/WebSockChatHandler.java)
- 채팅방에 접속한 사용자 목록
  - [컨트롤러](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/presence/src/main/java/com/devcamp/flametalk/presence/controller/PresenceController.java)
  - [서비스](https://github.com/DevCamp2Flame/FlameTalk_Server/blob/develop/presence/src/main/java/com/devcamp/flametalk/presence/service/PresenceService.java)


## 마치며
Spring Cloud, Websocket, kafka 등 새롭게 사용해본 기술과, MSA, 접속 상태 서버와 같은 낯선 개념들을 공부하면서 구현을 병행하느라 
들인 시간에 비해 코드량도 적고 아직 더 보완하고 싶은 마음이 커 아쉽기도 하지만,
다르게 생각해보면, 짧은 기간내에 몰입하고 새로운 것에 도전하여 이만큼 할 수 있구나. 하는 성취감과 자신감도 얻을 수 있었고 
앞으로 더 쉽게 많은 도전을 할 수 있는 마음가짐을 갖게 된 것 같습니다.

3개월간 데브 캠프라는 좋은 기회를 주셔서 대규모 프로젝트의 맛을 볼 수 있었다는 것에 정말 감사드리고 
앞으로는 더 노력해서 나은 모습 보여드릴 수 있도록 하겠습니다!

이상 SGS 데브캠프 2기 김다롬이었습니다.
시간내어 리뷰해주셔서 감사드립니다!