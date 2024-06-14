-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: eland_web
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `tag_info`
--

DROP TABLE IF EXISTS `tag_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tag_info` (
  `tag_id` int(11) NOT NULL,
  `tag_name` varchar(10) CHARACTER SET utf8 NOT NULL,
  `type` int(11) NOT NULL,
  PRIMARY KEY (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tag_info`
--

LOCK TABLES `tag_info` WRITE;
/*!40000 ALTER TABLE `tag_info` DISABLE KEYS */;
INSERT INTO `tag_info` VALUES (1,'品牌',1),(2,'人物',1),(3,'地點',1),(4,'組織團體',1),(5,'政府',1),(6,'活動',1),(7,'媒體',1),(8,'其他',1),(9,'旅遊住宿',2),(10,'美食',2),(11,'料理烹飪',2),(12,'美妝保養',2),(13,'時尚穿搭',2),(14,'髮型設計',2),(15,'電玩遊戲',2),(16,'3C資訊',2),(17,'電器設備',2),(18,'親子互動',2),(19,'親子教育',2),(20,'媽咪寶寶',2),(21,'繪畫插圖',2),(22,'攝影平面',2),(23,'文字創作',2),(24,'空間設計',2),(25,'語言翻譯',2),(26,'金融理財',2),(27,'兩性議題',2),(28,'知識分享',2),(29,'才藝學習',2),(30,'書評/書籍介紹',2),(31,'房屋資訊',2),(32,'學校系所',2),(33,'法律知識',2),(34,'氣象地理',2),(35,'醫療健康',2),(36,'運動健身',2),(37,'汽機車',2),(38,'寵物',2),(39,'生活小物',2),(40,'植物盆栽',2),(41,'菸酒雪茄',2),(42,'玩具模型',2),(43,'趣味分享',2),(44,'動漫',2),(45,'命理宗教',2),(46,'潛水釣魚',2),(47,'軍事',2),(48,'結婚婚禮',2),(49,'自行車',2),(50,'露營登山',2),(51,'居家家具',2),(52,'交通工具',2),(53,'政治社會',2),(54,'影劇藝文',2),(55,'音樂評論',2),(56,'消費',2),(57,'新聞',2),(58,'社會公益',2),(59,'影音創作',2),(60,'音樂創作',2),(61,'舞蹈',2),(62,'樂器演奏',2),(63,'生活分享',2),(64,'魔術師',2),(65,'工作職業',2),(66,'論壇',1);
/*!40000 ALTER TABLE `tag_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-14  9:47:27
