# FlameTalk_Backend

<div align="center" style="display:flex;">
	<img src="https://user-images.githubusercontent.com/43838030/153394342-37221ea4-b3cf-4dc4-81b6-4d0b9ed9e46b.png" width="200">
</div>
<div align="center">

### SGS 데브캠프 2기 flame 팀의 메신저 클론 프로젝트입니다🔥

앱 서비스의 기본적인 회원 인증, 연락처 동기화를 통한 친구 추가, 채팅 프로필 커스텀, 검색 기능과 채팅 기능을 제공합니다.
<br/>채팅은 STOMP WebSocket 기반의 실시간 통신으로 이루어지며, FCM을 통해 메세지에 대한 푸시 알림 기능이 있습니다.
</div>

<br/>

---
## 목차
1. 팀원 (Team member)
2. 프로젝트 정보 (Project Info)
3. 아키텍처 (Architecture)
4. DB 모델링 (Database Modeling)
5. API 명세서 (API Spec)
6. 협업 규칙 (Collaboration Rools)
7. 실행 방법 (How to build)


## 1. 팀원 (Team member)

| [<img src="https://avatars.githubusercontent.com/vo0a" width="100">](https://github.com/vo0a) | [<img src="https://avatars.githubusercontent.com/SuyeonChoi" width="100">](https://github.com/SuyeonChoi) |
|:---------------------------------------------------------------------------------------------:|:------------------------------------------------------------------------------------------------------:| 
|                                [김다롬](https://github.com/vo0a)                                 |                                  [최수연](https://github.com/SuyeonChoi)                                  |

[백엔드 업무 분담](https://github.com/DevCamp2Flame/FlameTalk_Server/wiki/백엔드-업무-분담)

## 2. 프로젝트 정보 (Project Info)

### 📚 0) 버전 정보 (Version Info)

|   IDE    | Version |
|:--------:|:-------:| 
| IntelliJ | 2021.3  |

| Language | Version |
|:--------:|:-------:| 
|   Java   |   11    |

|  Framework  | Version |
|:-----------:|:-------:| 
| Spring boot |  2.6.2  |
|   gradle    |  7.3.3  |

| Database  |       Version        | Description                    |
|:---------:|:--------------------:|--------------------------------|
|   MySQL   |        8.0.27        | RDBMS: 사용자 및 프로필, 피드 등 연관관계 저장 |
| cassandra |        4.0.1         | NoSQL: 채팅 메시지 저장               |
|   redis   |       3.0.504        | NoSQL: 캐시 - 사용자 탈퇴, 채팅방 입퇴장 기록 |

### 📚 1) 라이브러리 (Library)

|     Libraby      | Version  | Description             |
|:----------------:|:--------:|-------------------------|
| Spring Security  |  2.6.2   | 사용자 인증 및 보안설정           |
| Spring Data JPA  |  2.6.2   | Repository 인터페이스 제공     |
|   Spring Cloud   | 2021.0.0 | MSA 구성을 지원              |
| WebSocket, STOMP |  2.6.2   | 양방향 통신을 지원              |
|      kafka       |  2.8.1   | publish/subscribe 모델 지원 |
|      MySQL       |  2.6.2   | MySQL 연결을 제공            |
|    cassandra     |  3.3.0   | cassandra 연결을 제공        |
|      redis       |  2.6.2   | redis 연결을 제공            |

### 📚 2) 요구사항 (Requirements Spec)

- 로그인, 회원가입
- JWT 기반 사용자 인증
- 프로필 생성 및 수정
- 멀티 프로필, 오픈 프로필 생성 및 관리
- 친구 목록, 생일인 친구 목록 불러오기
- 친구 차단, 숨김 설정
- 채팅, 오픈 채팅
- 파일 송수신
- N 디바이스 지원

### 📚 3) 문서 취합 (Document Archive)
[백엔드 아카이브](https://github.com/DevCamp2Flame/FlameTalk_Server/wiki/백엔드-아카이브) </br>


## 3. 아키텍처 (Architecture)
![FlameTalk_Architecture-flametalk_architecture](https://user-images.githubusercontent.com/44438366/153760193-40b70261-91bc-452d-abd8-11b9da11b7b8.png)

## 4. DB 모델링 (Database Modeling)
![milestone2_db](https://user-images.githubusercontent.com/44438366/153452811-ab72ba00-94e9-4994-b5c4-722fcb168ba2.png)

## 5. API 명세서 (API Spec)
[API Spec](https://github.com/DevCamp2Flame/FlameTalk_Server/wiki)

## 6. 협업 규칙 (Collaboration Rools)

[백엔드 협업 규칙](https://github.com/DevCamp2Flame/FlameTalk_Server/wiki/백엔드-협업-규칙) </br>


## 7. 실행 방법 (How to build)

### 1) PORT 설정
- gateway 8080
- auth 8081
- membership 8082
- file 8083
- chat-api 8084
- chat 8085
    - MSA 로 서버 여러대 띄울 때 805X 사용
    - ex) 8051, 8052
- presence 8086
- fcm 8089

### 2)서버 실행 방법
### 프로젝트 클론 - 빌드
```shell
// 깃 클론
git clone -b develop https://github.com/DevCamp2Flame/FlameTalk_Server

// 실행하고 싶은 서버 폴더로 이동
cd/FlameTalk_Server/auth

// 쓰기 권한 부여
chmod +x gradlew

// build
./gradlew/build
```
여기까지 완료하면 /auth/build/libs 에 jar 파일이 만들어집니다. </br>
jar 파일을 실행하면 spring boot 를 실행할 수 있습니다.
    
### MySQL user 생성
```sql
# mysql 접속
mysql -u root -p

# flame 유저 생성
create user 'flame'@'%' identified by 'flame123!@#';

# 생성한 유저에게 권한 부여
GRANT ALL PRIVILEGES ON . TO 'flame'@'%' WITH GRANT OPTION;

# 변경사항 적용
FLUSH PRIVILEGES;

# mysql 나가기
quit
```
    
**참고**
    
Mac 환경에서는 ! 때문에 문제가 됨.
이때는 유저 굳이 생성하지 않고, root, admin 등 본인의 mysql 계정을 사용해도 됩니다.
    
이때는 아래 프로젝트 실행 단계에서 --MYSQL_USER={본인 계정} --MYSQL_PASSWORD={계정
    비밀번호} 로 치환하고, JWT_SECRET 부분에도 특수문자를 지우고 실행하면 됩니다.
    
### 프로젝트 실행
```shell
cd build/libs
    
java -jar flametalk-0.0.1-SNAPSHOT.jar --MYSQL_PORT=3306 --MYSQL_USER=flame --MYSQL_PASSWORD=flame123!@#
--JWT_SECRET=@dkssudgktpdy-durlsms*vmffpdla$xladlqslek!wkf(qnxkremflqslekdyd^rkflz
--REDIS_PORT=6379 --APP_NAME=flametalk1.gmail.com
--APP_PASSWORD=cnrdmzaaebhkqhgh
```
    
프로젝트 실행하면 808X 포트로 서버가 실행되고, localhost:808X 으로 서버에
    request 를 보낼 수 있습니다.

### 3) API Gateway 를 통해 서버 호출하기

eureka → 테스트할 서버 실행 (auth, membership, file, ... ) → gateway 순서로 실행시켜주세요.

테스트할 서버를 키고, gateway 를 실행시켜야 gateway 에서 서버를 spring.application.name 으로 인식할 수 있습니다.
서버 호출시 게이트웨이 포트번호 8080으로 호출해주세요.

### 4) MySQL 스키마 및 테이블 생성

**schema.sql**
```sql
-- MySQL Script generated by MySQL Workbench
-- Fri Feb 4 17:49:07 2022
-- Model: New Model Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema flametalk_db
-- -----------------------------------------------------
-- id: flame
-- password: flame123!@#

-- -----------------------------------------------------
-- Schema flametalk_db
--
-- id: flame
-- password: flame123!@#
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `flametalk_db` DEFAULT CHARACTER SET utf8 COLLATE utf8_bin;
USE `flametalk_db`;

-- -----------------------------------------------------
-- Table `flametalk_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`user`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`user`
(
    `id`           VARCHAR(20)  NOT NULL,
    `email`        VARCHAR(320) NOT NULL,
    `password`     VARCHAR(60)  NULL,
    `nickname`     VARCHAR(20)  NOT NULL,
    `phone_number` VARCHAR(13)  NOT NULL,
    `birthday`     VARCHAR(10)  NOT NULL,
    `social`       TINYINT      NOT NULL,
    `status`       TINYINT      NOT NULL,
    `region`       VARCHAR(2)   NOT NULL,
    `language`     VARCHAR(3)   NOT NULL,
    `created_at`   DATETIME     NOT NULL,
    `updated_at`   DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`device`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`device`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`device`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`            VARCHAR(20)  NOT NULL,
    `device_id`          VARCHAR(16)  NOT NULL,
    `token`              VARCHAR(163) NULL,
    `cur_max_message_id` VARCHAR(45)  NULL,
    `created_at`         DATETIME     NOT NULL,
    `updated_at`         DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `device_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`profile`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`profile`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `user_id`      VARCHAR(20) NOT NULL,
    `image_url`    BLOB        NULL,
    `bg_image_url` BLOB        NULL,
    `sticker`      JSON        NULL,
    `description`  VARCHAR(60) NULL,
    `is_default`   TINYINT     NOT NULL,
    `created_at`   DATETIME    NOT NULL,
    `updated_at`   DATETIME    NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `profile_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`feed`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`feed`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`feed`
(
    `id`            BIGINT   NOT NULL AUTO_INCREMENT,
    `profile_id`    BIGINT   NOT NULL,
    `image_url`     BLOB     NOT NULL,
    `is_background` TINYINT  NOT NULL,
    `is_lock`       TINYINT  NOT NULL,
    `created_at`    DATETIME NOT NULL,
    `updated_at`    DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `profile_id_idx` (`profile_id` ASC) VISIBLE,
    CONSTRAINT `feed_profile_id`
    FOREIGN KEY (`profile_id`)
    REFERENCES `flametalk_db`.`profile` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`friend`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`friend`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`friend`
(
    `id`                  BIGINT      NOT NULL,
    `user_id`             VARCHAR(20) NOT NULL,
    `friend_id`           VARCHAR(20) NOT NULL,
    `assigned_profile_id` BIGINT      NOT NULL,
    `preview`             JSON        NOT NULL COMMENT '(나의 멀티 프로필에 따른) 프로필 사진, 상태메세지 ',
    `is_marked`           TINYINT     NOT NULL,
    `is_hidden`           TINYINT     NOT NULL,
    `is_blocked`          TINYINT     NOT NULL,
    `created_at`          DATETIME    NOT NULL,
    `updated_at`          DATETIME    NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    INDEX `profile_idx` (`assigned_profile_id` ASC) VISIBLE,
    INDEX `friend_friend_id_idx` (`friend_id` ASC) VISIBLE,
    CONSTRAINT `friend_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `friend_assigned_profile_id`
    FOREIGN KEY (`assigned_profile_id`)
    REFERENCES `flametalk_db`.`profile` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `friend_friend_id`
    FOREIGN KEY (`friend_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`open_profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`open_profile`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`open_profile`
(
    `id`          BIGINT      NOT NULL AUTO_INCREMENT,
    `user_id`     VARCHAR(20) NOT NULL,
    `nickname`    VARCHAR(20) NOT NULL,
    `image_url`   BLOB        NULL,
    `description` VARCHAR(60) NULL,
    `created_at`  DATETIME    NOT NULL,
    `updated_at`  DATETIME    NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `open_profile_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`chatroom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`chatroom`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`chatroom`
(
    `id`         VARCHAR(36) NOT NULL,
    `host_id`    VARCHAR(20) NULL,
    `count`      INT         NOT NULL,
    `is_open`    TINYINT     NOT NULL,
    `url`        VARCHAR(7)  NULL,
    `created_at` DATETIME    NOT NULL,
    `updated_at` DATETIME    NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `host_id_idx` (`host_id` ASC) VISIBLE,
    CONSTRAINT `host_id`
    FOREIGN KEY (`host_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`user_chatroom`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`user_chatroom`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`user_chatroom`
(
    `id`                   BIGINT      NOT NULL AUTO_INCREMENT,
    `chatroom_id`          VARCHAR(36) NOT NULL,
    `user_id`              VARCHAR(20) NOT NULL,
    `open_profile_id`      BIGINT      NULL,
    `title`                VARCHAR(50) NULL,
    `last_read_message_id` VARCHAR(45) NULL,
    `image_url`            BLOB        NULL,
    `input_lock`           TINYINT     NOT NULL,
    `created_at`           DATETIME    NOT NULL,
    `updated_at`           DATETIME    NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    INDEX `user_chatroom_chatroom_id_idx` (`chatroom_id` ASC) VISIBLE,
    INDEX `user_chatroom_open_profile_id_idx` (`open_profile_id` ASC) VISIBLE,
    CONSTRAINT `user_chatroom_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `user_chatroom_chatroom_id`
    FOREIGN KEY (`chatroom_id`)
    REFERENCES `flametalk_db`.`chatroom` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `user_chatroom_open_profile_id`
    FOREIGN KEY (`open_profile_id`)
    REFERENCES `flametalk_db`.`open_profile` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`file`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`file`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`file`
(
    `id`          BIGINT       NOT NULL AUTO_INCREMENT,
    `url`         BLOB         NOT NULL,
    `title`       VARCHAR(100) NOT NULL,
    `extension`   VARCHAR(5)   NOT NULL,
    `chatroom_id` VARCHAR(36)  NULL,
    `created_at`  DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `file_chatroom_id_idx` (`chatroom_id` ASC) VISIBLE,
    CONSTRAINT `file_chatroom_id`
    FOREIGN KEY (`chatroom_id`)
    REFERENCES `flametalk_db`.`chatroom` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
ENGINE = InnoDB;


SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;
```
### 5) cassandra 스키마 및 테이블 생성
```sql
# Keyspace 생성
CREATE KEYSPACE IF NOT EXISTS flametalk WITH replication ={'class':'NetworkTopologyStrategy','datacenter1':1};

# Keyspace 접속
USE flametalk;

# 테이블 생성
CREATE TABLE IF NOT EXISTS message (message_id text, message_type text, sender_id text, nickname text, room_id text, contents text, file_url blob, created_at timestamp, PRIMARY KEY (message_id));

# 데이터 생성 예시
INSERT INTO message (message_id, message_type, sender_id, nickname, room_id, contents, created_at) VALUES ('1', 'TALK', '1', 'darom', '1', 'hi', '2022-02-14 13:30:54.234');
INSERT INTO message (message_id, message_type, sender_id, nickname, room_id, file_url, created_at) VALUES ('1', 'TALK', '1', 'darom', '1', 'url', '2022-02-14 13:30:54.234');
```