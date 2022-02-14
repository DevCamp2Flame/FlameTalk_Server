-- Keyspace 생성
CREATE KEYSPACE IF NOT EXISTS flametalk WITH replication ={'class':'NetworkTopologyStrategy','datacenter1':1};

-- Keyspace 접속
USE flametalk;

-- 테이블 생성
CREATE TABLE IF NOT EXISTS message
(
    message_id   text,
    message_type text,
    sender_id    text,
    nickname     text,
    room_id      text,
    contents     text,
    file_url     blob,
    created_at   timestamp,
    PRIMARY KEY (message_id)
);

-- 데이터 생성 예시
INSERT INTO message (message_id, message_type, sender_id, nickname, room_id, contents, created_at)
VALUES ('1', 'TALK', '1', 'darom', '1', 'hi', '2022-02-14 13:30:54.234');
INSERT INTO message (message_id, message_type, sender_id, nickname, room_id, file_url, created_at)
VALUES ('1', 'TALK', '1', 'darom', '1', 'url', '2022-02-14 13:30:54.234');