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
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'auto-increment',
  `user_id` varchar(100) NOT NULL COMMENT '알림을 받은 유저 아이디',
  `content` varchar(500) NOT NULL COMMENT '알림 내용',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '알림 생성 일시',
  `expired_date` datetime NOT NULL COMMENT '알림 만료 일시 (생성일시 + 24시간)',
  `type` varchar(15) NOT NULL COMMENT '알림종류 / R : 받은하트, E : 보낸하트, H : 도감',
  `is_checked` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0 : 안 읽음, 1 : 읽음',
  `is_active` tinyint(1) NOT NULL DEFAULT '1' COMMENT '1 : 활성화, 0 : 삭제',
  `message_id` bigint DEFAULT NULL,
  `heart_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_TO_notification_1` (`user_id`),
  KEY `FK_message_TO_notification` (`message_id`),
  KEY `FK_heart_TO_notification` (`heart_id`),
  CONSTRAINT `FK_heart_TO_notification` FOREIGN KEY (`heart_id`) REFERENCES `heart` (`id`),
  CONSTRAINT `FK_message_TO_notification` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FK_user_TO_notification_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2579 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-05-18 15:03:43
