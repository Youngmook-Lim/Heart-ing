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
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'auto-increment',
  `message_id` bigint NOT NULL COMMENT '신고된 메시지(message 테이블 PK)',
  `reporter_id` varchar(100) NOT NULL COMMENT '신고자(user 테이블 PK)',
  `reported_id` varchar(100) DEFAULT NULL COMMENT '신고 당한 사람(user 테이블 PK)',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '신고일시',
  `content` varchar(500) NOT NULL COMMENT '신고 내용(사유)',
  PRIMARY KEY (`id`),
  KEY `FK_message_TO_report_1` (`message_id`),
  KEY `FK_user_TO_report_1` (`reporter_id`),
  KEY `FK_user_TO_report_2` (`reported_id`),
  CONSTRAINT `FK_message_TO_report_1` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FK_user_TO_report_1` FOREIGN KEY (`reporter_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_user_TO_report_2` FOREIGN KEY (`reported_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
