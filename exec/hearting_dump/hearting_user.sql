-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: heart-ing.com    Database: hearting
-- ------------------------------------------------------
-- Server version	8.0.30

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(100) NOT NULL COMMENT 'auto-increment',
  `type` varchar(15) NOT NULL COMMENT 'KAKAO : 카카오, GOOGLE : 구글',
  `email` varchar(100) NOT NULL COMMENT '이메일',
  `nickname` varchar(15) NOT NULL COMMENT '닉네임',
  `refresh_token` varchar(200) DEFAULT NULL COMMENT 'refresh 토큰',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '가입일시',
  `updated_date` datetime DEFAULT NULL COMMENT '수정일시',
  `status_message` varchar(100) DEFAULT NULL COMMENT '상태메시지',
  `reported_count` int NOT NULL DEFAULT '0' COMMENT '누적 신고된 횟수(3회 이상 : 일시정지, 5회 이상 : 영구정지)',
  `status` char(1) NOT NULL DEFAULT 'A' COMMENT 'A : 활성화, P : 일시정지, O : 영구정지, D : 탈퇴',
  `role` varchar(15) NOT NULL DEFAULT 'ROLE_USER' COMMENT 'Spring Security용 컬럼',
  `message_total` bigint DEFAULT '0' COMMENT '역정규화 대상 - 회원별 메시지 total',
  PRIMARY KEY (`id`),
  UNIQUE KEY `refresh_token` (`refresh_token`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 15:03:40
