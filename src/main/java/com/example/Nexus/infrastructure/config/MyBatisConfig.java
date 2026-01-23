package com.example.Nexus.infrastructure.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.example.Nexus.domain.**.mapper") // 모든 도메인의 mapper 패키지를 스캔하도록 수정
public class MyBatisConfig {
}
