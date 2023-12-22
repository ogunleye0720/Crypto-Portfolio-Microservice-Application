-- MySQL dump 10.13  Distrib 8.0.32, for Linux (x86_64)
--
-- Host: localhost    Database: crypto_price
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cryptocurrency`
--

DROP TABLE IF EXISTS `cryptocurrency`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cryptocurrency` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `coin_market_id` bigint NOT NULL,
  `last_update` datetime(6) NOT NULL,
  `name` varchar(100) NOT NULL,
  `platform` varchar(100) DEFAULT NULL,
  `symbol` varchar(20) NOT NULL,
  `token_address` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueCoinMarketCapId` (`coin_market_id`),
  UNIQUE KEY `UniqueName` (`name`),
  UNIQUE KEY `UniqueSymbol` (`symbol`),
  UNIQUE KEY `UniqueSmartContractAddress` (`token_address`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cryptocurrency`
--

LOCK TABLES `cryptocurrency` WRITE;
/*!40000 ALTER TABLE `cryptocurrency` DISABLE KEYS */;
INSERT INTO `cryptocurrency` VALUES (1,1027,'2023-03-11 05:11:53.847803','Ethereum',NULL,'ETH',NULL),(2,1,'2023-03-11 05:12:16.834722','Bitcoin',NULL,'BTC',NULL),(3,3890,'2023-03-11 05:12:16.834822','Polygon',NULL,'MATIC',NULL),(4,5805,'2023-03-11 05:12:18.834822','Avalanche',NULL,'AVAX',NULL);
/*!40000 ALTER TABLE `cryptocurrency` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price`
--

DROP TABLE IF EXISTS `price`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `price` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `last_update` datetime(6) NOT NULL,
  `percent_change_1h` decimal(24,12) DEFAULT NULL,
  `percent_change_24h` decimal(24,12) DEFAULT NULL,
  `percent_change_30d` decimal(24,12) DEFAULT NULL,
  `percent_change_60d` decimal(24,12) DEFAULT NULL,
  `percent_change_7d` decimal(24,12) DEFAULT NULL,
  `percent_change_90d` decimal(24,12) DEFAULT NULL,
  `price_current` decimal(24,12) DEFAULT NULL,
  `cryptocurrency_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UniqueCryptocurrency` (`cryptocurrency_id`),
  CONSTRAINT `FKpenp1sn7wuoeh83qvswty0y06` FOREIGN KEY (`cryptocurrency_id`) REFERENCES `cryptocurrency` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price`
--

LOCK TABLES `price` WRITE;
/*!40000 ALTER TABLE `price` DISABLE KEYS */;
INSERT INTO `price` VALUES (1,'2023-03-11 05:11:53.847762',NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(2,'2023-03-11 05:12:16.834710',NULL,NULL,NULL,NULL,NULL,NULL,NULL,2),(3,'2023-03-11 05:13:17.834710',NULL,NULL,NULL,NULL,NULL,NULL,NULL,3),(4,'2023-03-11 05:13:18.834710',NULL,NULL,NULL,NULL,NULL,NULL,NULL,4);
/*!40000 ALTER TABLE `price` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-11  5:13:27
