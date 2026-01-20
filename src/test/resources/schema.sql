-- 기존 테이블이 있다면 삭제하여 테스트 환경을 초기화합니다.
DROP TABLE IF EXISTS posts;

-- H2 데이터베이스 전용 테이블 생성 문법입니다.
CREATE TABLE posts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(225) NOT NULL,
    content TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);