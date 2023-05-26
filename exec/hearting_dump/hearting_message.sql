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
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'auto-increment',
  `heart_id` bigint NOT NULL COMMENT 'heart 테이블 PK',
  `emoji_id` bigint DEFAULT NULL COMMENT 'emoji 테이블 PK',
  `sender_id` varchar(100) DEFAULT NULL COMMENT '송(발)신자 (user 테이블 PK - 비로그인 시에도 보낼 수 있기 때문에 NULL)',
  `receiver_id` varchar(100) NOT NULL COMMENT '수신자(user 테이블 PK)',
  `title` varchar(50) NOT NULL COMMENT '메시지  제목',
  `content` varchar(500) DEFAULT NULL COMMENT '메시지 내용',
  `is_read` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 : 안 읽음, 1 : 읽음',
  `is_stored` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 : 미보관, 1 : 보관',
  `sender_ip` varchar(20) DEFAULT NULL COMMENT '송신자의 public IP 주소',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 : 활성화, 0 : 삭제',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '전송 일시',
  `expired_date` datetime NOT NULL COMMENT '메시지 만료 일시',
  `is_reported` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 : 신고 안 됨, 1 : 신고 됨',
  PRIMARY KEY (`id`),
  KEY `FK_heart_TO_message_1` (`heart_id`),
  KEY `FK_emoji_TO_message_1` (`emoji_id`),
  KEY `FK_user_TO_message_1` (`sender_id`),
  KEY `FK_user_TO_message_2` (`receiver_id`),
  CONSTRAINT `FK_emoji_TO_message_1` FOREIGN KEY (`emoji_id`) REFERENCES `emoji` (`id`),
  CONSTRAINT `FK_heart_TO_message_1` FOREIGN KEY (`heart_id`) REFERENCES `heart` (`id`),
  CONSTRAINT `FK_user_TO_message_1` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_user_TO_message_2` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5182 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
