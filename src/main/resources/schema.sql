-- 기존 테이블이 있다면 삭제하여 테스트 환경을 초기화합니다.
DROP TABLE IF EXISTS posts;
DROP TABLE IF EXISTS users;

-- 게시글 테이블
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(225) NOT NULL,
    content TEXT,
    writer_id VARCHAR(50) NOT NULL, -- 작성자 ID 추가
    hits INT DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);