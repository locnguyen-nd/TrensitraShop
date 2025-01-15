-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: trendista_database
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` binary(16) NOT NULL,
  `city` varchar(255) NOT NULL,
  `district` varchar(255) NOT NULL,
  `is_default_address` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) NOT NULL,
  `spec_address` varchar(255) NOT NULL,
  `ward` varchar(255) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKda8tuywtf0gb6sedwk7la1pgi` (`user_id`),
  CONSTRAINT `FKda8tuywtf0gb6sedwk7la1pgi` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `address`
--

LOCK TABLES `address` WRITE;
/*!40000 ALTER TABLE `address` DISABLE KEYS */;
INSERT INTO `address` VALUES (_binary ' \Á\ˆ(πNàç2ã\≈\"','Da Nang','Da Nang',_binary '','nam','0123456789','Da Nang','Hai Chau',_binary '\⁄\Êá6\œD@è\ƒp\Á\Õ\∆K-'),(_binary '}´\Ë\—fCBïWs\∆¿ë','Da Nang','Da Nang',_binary '','nam','0123456789','Da Nang','Hai Chau',_binary '∂a{î\›RL¿ó	\Ô=∑cæ'),(_binary 'æ7{\‡5I˛£\”,\–«é\ﬁ','Da Nang','Da Nang',_binary '','nam1','0123456789','Da Nang','Hai Chau',_binary '∂a{î\›RL¿ó	\Ô=∑cæ');
/*!40000 ALTER TABLE `address` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `auth_user_authority`
--

DROP TABLE IF EXISTS `auth_user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auth_user_authority` (
  `user_id` binary(16) NOT NULL,
  `roles_id` binary(16) NOT NULL,
  PRIMARY KEY (`user_id`,`roles_id`),
  KEY `FKnunur1rxqo9uqop5s56shq6yp` (`roles_id`),
  CONSTRAINT `FKhnndtpgongj5lyfux2tdv8ip` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKnunur1rxqo9uqop5s56shq6yp` FOREIGN KEY (`roles_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_authority`
--

LOCK TABLES `auth_user_authority` WRITE;
/*!40000 ALTER TABLE `auth_user_authority` DISABLE KEYS */;
INSERT INTO `auth_user_authority` VALUES (_binary '\ŸIÄ\\'\ÊD…∏Ω¡\Î¯yRÆ',_binary 'C@*§%@§ÖD\v±Ö§'),(_binary '\rl\«8\ÏM òMJFª)÷ß',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary 'R\–3\"ÑD+∏\ﬂ\À\‰“é',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary 'åS\·˘§N∑µ\÷PKlä',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary ';J\≈\Á˙B\Ëû8\ÍG∑\È',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary 'wJçè\È\ÿKj±6]2€ö',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary 'ãOô¡ﬁ™Gµ∫ä{∑»â•U',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary 'Ø\Ú\ﬁ\⁄}±L3±7è+eQcU',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary '±\n\—\ƒ;¶@hÖ´ ?\‰|',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary '∂a{î\›RL¿ó	\Ô=∑cæ',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary 'π	∫\ÊrK∂~h†Wü;p',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary '\ŸIÄ\\'\ÊD…∏Ω¡\Î¯yRÆ',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary '\⁄\Êá6\œD@è\ƒp\Á\Õ\∆K-',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*'),(_binary 'ˇÏñÜ\Ò\ﬂD	í\Ó\˜®êq\∆',_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*');
/*!40000 ALTER TABLE `auth_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banners`
--

DROP TABLE IF EXISTS `banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banners` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `display_order` int DEFAULT NULL,
  `image_url` varchar(255) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) NOT NULL,
  `type` enum('CAROUSEL','CATEGORY','COUNTDOWN','DISCOUNT','FEATURED','HERO','INFO','PROMOTIONAL') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES (1,0,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736915391/BANNER/CAROUSEL/BANNER/CAROUSEL_images.jpg',_binary '','/home','Banner hompage t·∫øt 2025','CAROUSEL');
/*!40000 ALTER TABLE `banners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart` (
  `id` binary(16) NOT NULL,
  `cart_total` decimal(38,2) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK9emlp6m95v5er2bcqkjsw48he` (`user_id`),
  CONSTRAINT `FKl70asp4l4w0jmbm1tqyofho4o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (_binary 'yÃî\Ë\Ú†Fù≥˙\Á<æ5\ﬂ',0.00,NULL),(_binary '\‡\‡]Aâ\"L…πç∫<û\»\ﬁ\0',1400000.00,_binary 'π	∫\ÊrK∂~h†Wü;p');
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart-item`
--

DROP TABLE IF EXISTS `cart-item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cart-item` (
  `id` binary(16) NOT NULL,
  `cart_item_quantity` int DEFAULT NULL,
  `product_variant_id` binary(16) DEFAULT NULL,
  `cart_id` binary(16) DEFAULT NULL,
  `product_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK214dnjqbuqn6bojxg42cuytx5` (`cart_id`),
  KEY `FKi99rbtwsptu342fegnllatmcb` (`product_id`),
  CONSTRAINT `FK214dnjqbuqn6bojxg42cuytx5` FOREIGN KEY (`cart_id`) REFERENCES `cart` (`id`),
  CONSTRAINT `FKi99rbtwsptu342fegnllatmcb` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart-item`
--

LOCK TABLES `cart-item` WRITE;
/*!40000 ALTER TABLE `cart-item` DISABLE KEYS */;
INSERT INTO `cart-item` VALUES (_binary 'œ£P€§HPÖ)\ˆ4ˇ§',6,_binary '≥\\}ºçL+ª˙ö;\Ë´p(',_binary '\‡\‡]Aâ\"L…πç∫<û\»\ﬁ\0',_binary ' \˜¬å`BkÅ09ü\”]');
/*!40000 ALTER TABLE `cart-item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` binary(16) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `delete_at` datetime(6) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `gender_id` binary(16) NOT NULL,
  `parent_id` binary(16) DEFAULT NULL,
  `index_num` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2hi3wrk72s07hvnmgtpefph99` (`gender_id`),
  KEY `FK2y94svpmqttx80mshyny85wqr` (`parent_id`),
  CONSTRAINT `FK2hi3wrk72s07hvnmgtpefph99` FOREIGN KEY (`gender_id`) REFERENCES `gender` (`id`),
  CONSTRAINT `FK2y94svpmqttx80mshyny85wqr` FOREIGN KEY (`parent_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (_binary '#b6•\ E\Úóg\ˆ0+\“\ F','2024-12-19 14:33:12.437000','2024-12-19 14:33:12.437000','2024-12-19 14:33:12.437000','',NULL,'√Åo ba l·ªó - √Åo hai d√¢y n·ªØ','ao-ba-lo-ao-hai-day-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'HëÅlxLAaå\∆ukNÅG',12),(_binary 'ÅX!ùMDÉô\ÕoM\“a\ ','2024-12-19 14:26:23.893000','2024-12-19 14:26:23.893000','2024-12-19 14:26:23.893000','',NULL,'√Åo hoodie - √Åo n·ªâ n·ªØ','ao-hoodie-ao-ni-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'J§Ho°L∫à\‘;N≥\"≥>',2),(_binary '\"LÇ\ﬂ*MìÆ7\ÊU{ãs','2024-12-19 09:25:14.944000','2024-12-19 09:25:14.944000','2024-12-19 09:25:14.944000','√Åo Polo Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734600314/CATEGORIES/CATEGORIES_391x719%20polo.webp','√Åo Polo Nam','ao-polo-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary '0\‰:\n\»(Jçë¨\ÊL?\Í',1),(_binary '(≠\ﬁ*å	NÉí7\⁄K\Ù4|','2024-12-19 14:26:04.879000','2024-12-19 14:26:04.879000','2024-12-19 14:26:04.879000','',NULL,'√Åo kho√°c n·ªØ','ao-khoac-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'J§Ho°L∫à\‘;N≥\"≥>',3),(_binary '0\‰:\n\»(Jçë¨\ÊL?\Í','2024-12-19 09:16:59.479000','2024-12-19 09:16:59.479000','2024-12-19 09:16:59.479000','√Åo Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734599819/CATEGORIES/CATEGORIES_AKM6017-NAU%20SKM7003-NAV%20QKM7007-NAU%20%285%29.webp','√Åo Nam','ao-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',NULL,0),(_binary 'BBâ\‹\ÔBHqíO`…∂\ZA','2024-12-19 09:28:10.280000','2024-12-19 09:28:10.280000','2024-12-19 09:28:10.280000','Qu·∫ßn nam',NULL,'Qu·∫ßn nam','quan-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',NULL,1),(_binary 'GÃ¥^ör@jõ¢¿\≈e5\Ô\n','2024-12-19 09:35:25.521000','2024-12-19 09:35:25.521000','2024-12-19 09:35:25.521000','Qu·∫ßn kaki nam',NULL,'Qu·∫ßn kaki nam','quan-kaki-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'BBâ\‹\ÔBHqíO`…∂\ZA',5),(_binary 'HëÅlxLAaå\∆ukNÅG','2024-12-19 14:32:33.893000','2024-12-19 14:32:33.893000','2024-12-19 14:32:33.893000','',NULL,'ƒê·ªì m·∫∑c trong n·ªØ','do-mac-trong-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',NULL,7),(_binary 'H≠i\ƒ	O0¢Ç±Ç\ƒ','2024-12-19 09:29:35.535000','2024-12-19 09:29:35.535000','2024-12-19 09:29:35.535000','Ph·ª• ki·ªán nam',NULL,'Ph·ª• ki·ªán nam','phu-kien-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',NULL,3),(_binary 'J§Ho°L∫à\‘;N≥\"≥>','2024-12-19 14:22:54.521000','2024-12-19 14:22:54.521000','2024-12-19 14:22:54.521000','','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734618174/CATEGORIES/CATEGORIES_menu_woman.webp','√Åo n·ªØ','ao-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',NULL,4),(_binary 'L?ïhé\ÚB¢;\À*=;\»]','2024-12-19 14:28:20.490000','2024-12-19 14:28:20.490000','2024-12-19 14:28:20.490000','',NULL,'√Åo gi√≥ n·ªØ','ao-gio-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary '(≠\ﬁ*å	NÉí7\⁄K\Ù4|',1),(_binary 'PQLA\Î\‡H-Ø3\Ó\\\Ÿ\‘3','2024-12-19 14:25:26.376000','2024-12-19 14:25:26.376000','2024-12-19 14:25:26.376000','',NULL,'√Åo vest n·ªØ','ao-vest-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'J§Ho°L∫à\‘;N≥\"≥>',8),(_binary 'S§π¶?NF†≠\¬	\„yˇc','2024-12-19 09:39:34.916000','2024-12-19 09:39:34.916000','2024-12-19 09:39:34.916000','√Åo polo th·ªÉ thao nam',NULL,'√Åo polo th·ªÉ thao nam','ao-polo-the-thao-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'ô\›J\Ô@ùØsF˙xSs∂',7),(_binary 'S€ë`:iK¿ëP\Ò.\’`n','2024-12-19 09:40:00.225000','2024-12-19 09:40:00.225000','2024-12-19 09:40:00.225000','B·ªô th·ªÉ thao nam',NULL,'B·ªô th·ªÉ thao nam','bo-the-thao-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'ô\›J\Ô@ùØsF˙xSs∂',8),(_binary 'WZeåm\≈G°•N˛¯\Èu*','2024-12-19 14:26:14.949000','2024-12-19 14:26:14.949000','2024-12-19 14:26:14.949000','',NULL,'√Åo ch·ªëng n·∫Øng n·ªØ','ao-chong-nang-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'J§Ho°L∫à\‘;N≥\"≥>',0),(_binary 'i)\\Ã2A~ñ7≠	∂[','2024-12-19 09:34:34.923000','2024-12-19 09:34:34.923000','2024-12-19 09:34:34.923000','Qu·∫ßn d√†i nam',NULL,'Qu·∫ßn d√†i nam','quan-dai-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'BBâ\‹\ÔBHqíO`…∂\ZA',4),(_binary 'yò\«\È|¶F;°Rq\€OA','2024-12-19 14:26:54.412000','2024-12-19 14:26:54.412000','2024-12-19 14:26:54.412000','',NULL,'√Åo thun n·ªØ','ao-thun-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'J§Ho°L∫à\‘;N≥\"≥>',7),(_binary 'Ü\ÔWdSZLóå_á\Ò\n{\œ','2024-12-19 14:28:06.887000','2024-12-19 14:28:06.887000','2024-12-19 14:28:06.887000','',NULL,'√Åo phao n·ªØ','ao-phao-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary '(≠\ﬁ*å	NÉí7\⁄K\Ù4|',4),(_binary 'ë\Û#VzK√ë\Á}aÇ\‰É','2024-12-19 09:21:59.277000','2024-12-19 09:21:59.277000','2024-12-19 09:21:59.277000','√Åo Thun Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734600118/CATEGORIES/CATEGORIES_thun%20391x719.webp','√Åo Thun Nam','ao-thun-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary '0\‰:\n\»(Jçë¨\ÊL?\Í',2),(_binary 'ì©q\¬rÉK\"•\\±7}\'','2024-12-19 09:20:05.059000','2024-12-19 09:20:05.059000','2024-12-19 09:20:05.059000','√Åo Kho√°c Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734600004/CATEGORIES/CATEGORIES_ao-khoac-391x719.webp','√Åo Kho√°c Nam','ao-khoac-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary '0\‰:\n\»(Jçë¨\ÊL?\Í',0),(_binary 'ó£dêsªEí∑\Ú1\‡Yn\Î','2024-12-19 14:29:20.181000','2024-12-19 14:29:20.181000','2024-12-19 14:29:20.181000','',NULL,'Qu·∫ßn d√†i n·ªØ','quan-dai-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary '∞z¿∞rB ó»∑>\0>V',9),(_binary 'ô\›J\Ô@ùØsF˙xSs∂','2024-12-19 09:29:11.791000','2024-12-19 09:29:11.791000','2024-12-19 09:29:11.791000','ƒê·ªì th·ªÉ thao nam',NULL,'ƒê·ªì th·ªÉ thao nam','do-the-thao-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',NULL,2),(_binary '¶\ÈhÅJ“Æ∂≥5p\∆\Á','2024-12-19 14:29:35.975000','2024-12-19 14:29:35.975000','2024-12-19 14:29:35.975000','',NULL,'Qu·∫ßn kaki n·ªØ','quan-kaki-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary '∞z¿∞rB ó»∑>\0>V',10),(_binary '™¥ì\Û\–KDû]^û:d\√\œ','2024-12-19 09:38:48.011000','2024-12-19 09:38:48.011000','2024-12-19 09:38:48.011000','Qu·∫ßn th·ªÉ thao nam',NULL,'Qu·∫ßn th·ªÉ thao nam','quan-the-thao-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'ô\›J\Ô@ùØsF˙xSs∂',9),(_binary '∞z¿∞rB ó»∑>\0>V','2024-12-19 14:28:51.017000','2024-12-19 14:28:51.017000','2024-12-19 14:28:51.017000','',NULL,'Qu·∫ßn n·ªØ','quan-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',NULL,5),(_binary '≥%JU\ZKî\\ıFá\‚ñ','2024-12-19 09:35:48.791000','2024-12-19 09:35:48.791000','2024-12-19 09:35:48.791000','Qu·∫ßn short nam',NULL,'Qu·∫ßn short nam','quan-short-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'BBâ\‹\ÔBHqíO`…∂\ZA',6),(_binary 'µ)\Ó\ÌâDÆ∫/\rl≠aú','2024-12-19 09:36:11.259000','2024-12-19 09:36:11.259000','2024-12-19 09:36:11.259000','Qu·∫ßn √¢u nam',NULL,'Qu·∫ßn √¢u nam','quan-au-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'BBâ\‹\ÔBHqíO`…∂\ZA',3),(_binary '∑\‡\≈PJdùåy\ÃY\ÿM','2024-12-19 14:25:55.451000','2024-12-19 14:25:55.451000','2024-12-19 14:25:55.451000','',NULL,'√Åo polo n·ªØ','ao-polo-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'J§Ho°L∫à\‘;N≥\"≥>',5),(_binary 'æ;j\n\ÌJïû\œ\Ë\‰ò;ãh','2024-12-19 09:28:40.802000','2024-12-19 09:28:40.802000','2024-12-19 09:28:40.802000','ƒê·ªì b·ªô nam',NULL,'ƒê·ªì b·ªô nam','do-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',NULL,8),(_binary '\Õc\Ò˝†G≥\‡Ñ“ßK;','2024-12-19 09:37:18.175000','2024-12-19 09:37:18.175000','2024-12-19 09:37:18.175000','ƒê·ªì b·ªô d√†i tay nam',NULL,'ƒê·ªì b·ªô d√†i tay nam','do-bo-dai-tay-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'æ;j\n\ÌJïû\œ\Ë\‰ò;ãh',10),(_binary '—π%UÑIPª°|Œë∫M','2024-12-19 14:29:29.164000','2024-12-19 14:29:29.164000','2024-12-19 14:29:29.164000','',NULL,'Qu·∫ßn n·ªâ n·ªØ','quan-ni-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary '∞z¿∞rB ó»∑>\0>V',11),(_binary '\”\ÀxJ§EJßéü\È-sﬂπ¶','2024-12-19 14:32:58.868000','2024-12-19 14:32:58.868000','2024-12-19 14:32:58.868000','',NULL,'√Åo bra n·ªØ','ao-bra-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'HëÅlxLAaå\∆ukNÅG',13),(_binary '\’\Ù\ÿS,πG¡¨ê{É\\\ÌI\˜','2024-12-19 09:38:03.447000','2024-12-19 09:38:03.447000','2024-12-19 09:38:03.447000','ƒê·ªì b·ªô ng·∫Øn tay nam',NULL,'ƒê·ªì b·ªô ng·∫Øn tay nam','do-bo-ngan-tay-nam',_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø',_binary 'æ;j\n\ÌJïû\œ\Ë\‰ò;ãh',11),(_binary '\ÂÇbÃèA\ËÉ˚B\ÛNvù\Û','2024-12-19 14:26:37.773000','2024-12-19 14:26:37.773000','2024-12-19 14:26:37.773000','',NULL,'√Åo s∆° mi n·ªØ','ao-so-mi-nu',_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø',_binary 'J§Ho°L∫à\‘;N≥\"≥>',6);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_discount`
--

DROP TABLE IF EXISTS `category_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_discount` (
  `category_id` binary(16) NOT NULL,
  `discount_id` binary(16) NOT NULL,
  KEY `FK6xy3f4vl3gr2lfjbkfi123gdw` (`discount_id`),
  KEY `FKnyyk86d4c9gg3lik5d2ik082h` (`category_id`),
  CONSTRAINT `FK6xy3f4vl3gr2lfjbkfi123gdw` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`),
  CONSTRAINT `FKnyyk86d4c9gg3lik5d2ik082h` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_discount`
--

LOCK TABLES `category_discount` WRITE;
/*!40000 ALTER TABLE `category_discount` DISABLE KEYS */;
INSERT INTO `category_discount` VALUES (_binary '\"LÇ\ﬂ*MìÆ7\ÊU{ãs',_binary '|x∞\Û9ΩE˚¶3;dO\Ì\Òo');
/*!40000 ALTER TABLE `category_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color`
--

DROP TABLE IF EXISTS `color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color` (
  `id` binary(16) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `delete_at` datetime(6) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (_binary '5\ZêJBLFû0˚Ç©ˇ7x','2024-12-19 04:05:11.388000','2024-12-19 04:05:11.388000','2024-12-19 04:05:11.388000','White','#FFFFFF','trang'),(_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e','2024-12-19 04:05:24.870000','2024-12-19 04:05:24.870000','2024-12-19 04:05:24.870000','Gray','#808080','xam'),(_binary 'K\"\Z\—]£Eò∏\ÙàS∏\Ë}{','2024-12-19 04:05:40.381000','2024-12-19 04:05:40.381000','2024-12-19 04:05:40.381000','Pink','#FFC0CB','hong'),(_binary 'äeûv4J/Æ6Æ\œZ\‰m','2024-12-19 04:05:06.945000','2024-12-19 04:05:06.945000','2024-12-19 04:05:06.945000','Black','#000000','trang'),(_binary 'ñè∫JK@\Ó∞Hå1\∆\ÿ\…','2024-12-19 04:05:18.796000','2024-12-19 04:05:18.796000','2024-12-19 04:05:18.796000','Yellow','#FFFF00','vang'),(_binary '\–3ˇü+_OÏåù\Ï4\ﬁ\Âî','2024-12-19 04:05:01.000000','2024-12-19 04:05:01.000000','2024-12-19 04:05:01.000000','Green','#008000','xanh-la-cay'),(_binary '—û3˘OAÖM\ˆLÿ¥\‚','2024-12-19 04:05:29.185000','2024-12-19 04:05:29.185000','2024-12-19 04:05:29.185000','Orange','#FFA500','cam'),(_binary '\Ô)aˇ/\ÊD∑¶°F\Zê\€1','2024-12-19 04:04:47.673000','2024-12-19 04:04:47.673000','2024-12-19 04:04:47.673000','Red','#FF0000','do'),(_binary '¸\Ù3<\\J˝øB0<3ò´ü','2024-12-19 04:04:55.066000','2024-12-19 04:04:55.066000','2024-12-19 04:04:55.066000','Blue','#0000FF','xanh-duong');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `id` binary(16) NOT NULL,
  `description` text,
  `discount_type` enum('AMOUNT','PERCENT') NOT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `frame` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `max_discount_value` decimal(10,2) DEFAULT NULL,
  `min_order_value` decimal(10,2) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `start_date` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (_binary 'S˚õŒè\"JÊüâ˛(B\–L\Ù','√Åp d·ª•ng cho c√°c m·∫∑t h√†ng √°o polo nam m√πa ƒë√¥ng  2024','PERCENT',15.00,'2024-12-25 22:29:00.103000','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734700943/Discount%20Frame/Discount%20Frame_pngtree-product-border-orange-frame-promotion-big-sale-png-image_6134236.png',_binary '',30000.00,0.00,'Sale Noel 2024 V1 ','2024-12-20 22:29:00.103000'),(_binary '|x∞\Û9ΩE˚¶3;dO\Ì\Òo','√Åp d·ª•ng cho c√°c m·∫∑t h√†ng √°o polo nam m√πa ƒë√¥ng  2024','PERCENT',15.00,'2024-12-25 22:29:00.103000','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734700943/Discount%20Frame/Discount%20Frame_pngtree-product-border-orange-frame-promotion-big-sale-png-image_6134236.png',_binary '',30000.00,0.00,'Sale Noel 2024 ','2024-12-20 22:29:00.103000');
/*!40000 ALTER TABLE `discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gender`
--

DROP TABLE IF EXISTS `gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gender` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) NOT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (_binary '∫¸FΩ\€\Ôé¢™\Zu—Ø','Unisex',NULL,'unisex'),(_binary 'ﬂ≠2∞Ω\⁄\Ôé¢™\Zu—Ø','Nam',NULL,'nam'),(_binary 'ﬂ∏w\·Ω\⁄\Ôé¢™\Zu—Ø','N·ªØ',NULL,'nu'),(_binary '˘i\"œΩ\⁄\Ôé¢™\Zu—Ø','Tr·∫ª Em',NULL,'tre-em');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_item` (
  `id` binary(16) NOT NULL,
  `item_price` decimal(38,2) DEFAULT NULL,
  `product_variant_id` binary(16) DEFAULT NULL,
  `quantity` int NOT NULL,
  `order_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt4dc2r9nbvbujrljv3e23iibt` (`order_id`),
  KEY `FK551losx9j75ss5d6bfsqvijna` (`product_id`),
  CONSTRAINT `FK551losx9j75ss5d6bfsqvijna` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKt4dc2r9nbvbujrljv3e23iibt` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (_binary 'P\ÿ8|N\Ôó-èøPßRF',NULL,_binary '≥\\}ºçL+ª˙ö;\Ë´p(',1,_binary '¶\ {Y]Kñæ_)Äª\—q\Ã',_binary ' \˜¬å`BkÅ09ü\”]'),(_binary 'Ñ˝\nJ<-@∏ø˝Æπi¯s˚',NULL,_binary '≥\\}ºçL+ª˙ö;\Ë´p(',1,_binary 'h^AÖ÷¥HEë\∆_Ω	¢\Õ',_binary ' \˜¬å`BkÅ09ü\”]');
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` binary(16) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `delete_at` datetime(6) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `discount` double DEFAULT NULL,
  `expected_delivery_date` datetime(6) DEFAULT NULL,
  `order_status` enum('CANCELLED','DELIVERED','PENDING','PROCESSING','RETURNED','SHIPPED') NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `shipment_tracking_number` varchar(255) DEFAULT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `address_id` binary(16) NOT NULL,
  `user_id` binary(16) NOT NULL,
  `card_number` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5464gxwc32ongdvka2rtvw96` (`address_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKf5464gxwc32ongdvka2rtvw96` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (_binary 'h^AÖ÷¥HEë\∆_Ω	¢\Õ','2024-12-28 11:07:12.999000','2024-12-28 11:07:12.999000','2024-12-28 11:07:12.999000',0,'2024-12-28 10:57:31.634000','PENDING','',NULL,1.00,_binary 'æ7{\‡5I˛£\”,\–«é\ﬁ',_binary '∂a{î\›RL¿ó	\Ô=∑cæ',NULL),(_binary '¶\ {Y]Kñæ_)Äª\—q\Ã','2024-12-28 11:24:09.190000','2024-12-28 11:24:09.190000','2024-12-28 11:24:09.190000',0,'2024-12-28 10:57:31.634000','PENDING','',NULL,1.00,_binary ' \Á\ˆ(πNàç2ã\≈\"',_binary '\⁄\Êá6\œD@è\ƒp\Á\Õ\∆K-',NULL);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` binary(16) NOT NULL,
  `amount` decimal(38,2) NOT NULL,
  `payment_date` datetime(6) NOT NULL,
  `payment_method` varchar(255) NOT NULL,
  `payment_status` enum('COMPLETED','FAILED','PENDING') NOT NULL,
  `order_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKmf7n8wo2rwrxsd6f3t9ub2mep` (`order_id`),
  CONSTRAINT `FKlouu98csyullos9k25tbpk4va` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
INSERT INTO `payment` VALUES (_binary 'å±U\ˇ\ÙE\‰øR¨fT∑º]',1.00,'2024-12-28 11:07:12.998000','','PENDING',_binary 'h^AÖ÷¥HEë\∆_Ω	¢\Õ'),(_binary 'µ@C2s0N≤\Ô_V¶Éó≥',1.00,'2024-12-28 11:24:09.189000','','PENDING',_binary '¶\ {Y]Kñæ_)Äª\—q\Ã');
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permission`
--

DROP TABLE IF EXISTS `permission`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permission` (
  `id` binary(16) NOT NULL,
  `end_point` varchar(255) NOT NULL,
  `method` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK2ojme20jpga3r4r79tdso17gi` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permission`
--

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;
INSERT INTO `permission` VALUES (_binary '$\\Ÿ\¬Lsê\’*K9A\Ù','/address/**','POST','USER CREATE ADDRESS'),(_binary 'u6ÓëΩÖGìè\‰L\Î(@S','/cart-order/create','POST','USER CREATE ORDER'),(_binary '|Bòà≠\∆NwÜ3(\Ë∏\œ<','/cart-order/user','GET','USER GET ORDER'),(_binary '}1*°\Ì\Ê@∂æ7\◊ogx','/cart-order/cancel/**','PUT','USER CANCEL ORDER'),(_binary 'µ\ÊyÃûLèµÖ&ìfëu','/user/**','GET','USER READ ACCOUNT');
/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product` (
  `id` binary(16) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `delete_at` datetime(6) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `code` varchar(255) DEFAULT NULL,
  `description` text,
  `featured_image` varchar(255) DEFAULT NULL,
  `is_free_ship` bit(1) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `origin_price` decimal(38,2) DEFAULT NULL,
  `price` decimal(38,2) DEFAULT NULL,
  `rating_average` int DEFAULT NULL,
  `rating_total` int DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `status` bit(1) DEFAULT NULL,
  `summary` text,
  `tag` enum('BEST_SELLERS','HOT_DEALS','NEW_ARRIVALS','RECOMMENDED','TRENDING') NOT NULL,
  `units_sold` int DEFAULT NULL,
  `views` int DEFAULT NULL,
  `category_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (_binary ' \˜¬å`BkÅ09ü\”]','2024-12-19 14:14:59.197000','2024-12-19 14:14:59.197000','2025-01-15 09:03:09.942000','SWF205','TQu·∫ßn gi√≥ YOGUU v·∫£i nhƒÉn t·ª± nhi√™n, m·ªÅm m·∫°i t·∫°o c·∫£m gi√°c tho·∫£i m√°i t·ªëi ƒëa. Nh·∫π t√™nh, b·ªÅn m√†u, ƒë·ªãnh h√¨nh form d√°ng t·ªët mang ƒë·∫øn v·∫ª ngo√†i th·ªùi trang. Thi·∫øt k·∫ø hi·ªán ƒë·∫°i, ph·ªëi vi·ªÅn ph·∫£n quang c√° t√≠nh. Ph√π h·ª£p v·ªõi nhi·ªÅu phong c√°ch t·ª´ th·ªÉ thao ƒë·∫øn d·∫°o ph·ªë. Gi·∫∑t m√°y ti·ªán l·ª£i.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Qu·∫ßn Gi√≥ D√†i Yoguu Ph·ªëi Vi·ªÅn Ph·∫£n Quang',250000.00,250000.00,0,0,'quan-gio-dai-yoguu-phoi-vien-phan-quang',_binary '','Tho·∫£i m√°i v·∫≠n ƒë·ªông v·ªõi qu·∫ßn gi√≥ d√†i YOGUU ch·∫•t li·ªáu Guu cao c·∫•p. V·∫£i nhƒÉn t·ª± nhi√™n, b·ªÅn m√†u, form d√°ng chu·∫©n. Thi·∫øt k·∫ø t√∫i s∆∞·ªùn ti·ªán l·ª£i, ph√π h·ª£p nhi·ªÅu phong c√°ch. Gi·∫∑t m√°y d·ªÖ d√†ng, nhanh kh√¥.','HOT_DEALS',0,0,_binary 'i)\\Ã2A~ñ7≠	∂['),(_binary '¸\ÌXW3Nﬂ∑\r7w\—d','2024-12-19 13:55:02.337000','2024-12-19 13:55:02.337000','2024-12-23 09:58:22.509000','UFB450','CH·∫§T LI·ªÜU AIRY COOL - C√îNG NGH·ªÜ V·∫¢I TH·∫æ H·ªÜ M·ªöI Th·∫•u hi·ªÉu mong mu·ªën s·ª≠ d·ª•ng nh·ªØng s·∫£ ph·∫©m th·ªùi trang tho√°ng m√°t, th·∫•m h√∫t t·ªët cho m√πa h√®, ƒë·ªôi ng≈© YODY nghi√™n c·ª©u v√† cho r·ªùi ƒë·ªùi s·∫£n ph·∫©m √°o polo Airy Cool v·ªõi nh·ªØng t√≠nh nƒÉng v∆∞·ª£t tr·ªôi. C√¥ng ngh·ªá v·∫£i FREEZING ti√™n ti·∫øn, h·∫° nhi·ªát cho ng√†y h√®.S·∫£n ph·∫©m ƒë∆∞·ª£c thi·∫øt k·∫ø v·ªõi 85% Nylon v√† 15% Spandex. Trong ƒë√≥, s·ª£i Nylon l√† c·∫•u t·∫°o ch√≠nh gi√∫p t·∫°o c·∫£m gi√°c tho·∫£i m√°i, d·ªÖ ch·ªãu khi m·∫∑c.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616502/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',NULL,'√Åo Polo Th·ªÉ Thao Nam Airy Cool Basic',105000.00,89250.00,0,0,'ao-polo-the-thao-nam-airy-cool-basic',_binary '','Tho·∫£i m√°i t·∫≠p luy·ªán nh·ªù c√¥ng ngh·ªá Airy cool ti·∫øn ti·∫øn gi√∫p gi·∫£i nhi·ªát c∆° th·ªÉ cho ng√†y h√® m√°t m·∫ª. K·∫øt c·∫•u v·∫£i m·ªÅm m·ªãn, kh√¥ nhanh. Th√¥ng tho√°ng, th·∫•m h√∫t m·ªì h√¥i t·ªët. Kh·∫£ nƒÉng co gi√£n, gi·ªØ form t·ªët.','BEST_SELLERS',0,0,_binary '\"LÇ\ﬂ*MìÆ7\ÊU{ãs'),(_binary 'HéèqE∂±\nm±\ÎR;','2024-12-19 13:46:28.315000','2024-12-19 13:46:28.315000','2025-01-15 09:03:10.202000','KXG917','M√πa h√® n√≥ng b·ª©c s·∫Ω lu√¥n c·∫ßn ƒë·∫øn nh·ªØng chi·∫øc √°o thun v·ª´a nh·∫π nh√†ng v·ª´a tho√°ng m√°t ƒë·ªÉ gi·∫£m b·ªõt c√°i b√≠ b√°ch. Ngo√†i vi·ªác s·ª≠ d·ª•ng c√°c thi·∫øt b·ªã l√†m m√°t, gi·∫£i ph√°p h·ªØu hi·ªáu m√† kh√¥ng ph·∫£i ai c≈©ng ch√∫ tr·ªçng ƒë√≥ l√† t√¨m ƒë·∫øn nh·ªØng s·∫£n ph·∫©m m·∫∑c tho√°ng, c√≥ ƒë·ªô th·∫•m h√∫t cao. √Åo thun YODY ra ƒë·ªùi v·ªõi m·ª•c ti√™u n√†y. Nh·ªØng chi·∫øc √°o thun tr·∫£i qua qu√° tr√¨nh nghi√™n c·ª©u b·ªÅn b·ªâ c·ªßa ƒë·ªôi ng≈© thi·∫øt k·∫ø nay ƒë√£ ƒë∆∞·ª£c ra m·∫Øt s·ªü h·ªØu nh·ªØng ∆∞u ƒëi·ªÉm n·ªïi b·∫≠t v·ªÅ gi√° c·∫£ v√† t√≠nh nƒÉng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615988/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',NULL,'Polo Nam Cafe Bo 3 MaÃÄu',149000.00,126650.00,0,0,'polo-nam-cafe-bo-3-mau',_binary '','Thi·∫øt k·∫ø d√°ng su√¥ng basic kho·∫ª kho·∫Øn. √Åo t·∫°o ƒëi·ªÉm nh·∫•n ·ªü ph·∫ßn ph·ªëi c·ªï 3 m√†u, bo tay c√πng h√¨nh th√™u ch√∫ th·ªè YODY tr∆∞·ªõc ng·ª±c ƒë·ªôc ƒë√°o. Ch·∫•t li·ªáu th·∫•m h√∫t m·ªì h√¥i, kh√°ng khu·∫©n v√† kh·ª≠ m√πi c∆° th·ªÉ c·ª±c t·ªët v√† b·∫£o v·ªá kh·ªèi tia UV ƒë·∫øn 98%.','BEST_SELLERS',0,0,_binary '\"LÇ\ﬂ*MìÆ7\ÊU{ãs'),(_binary 'q!†\Ê\∆Go≥k\ÚáQª£','2024-12-19 14:44:29.608000','2024-12-19 14:44:29.608000','2025-01-15 09:03:10.242000','LBX195','Vest gile n·ªØ v·ªõi ch·∫•t li·ªáu double face kim c∆∞∆°ng ƒë·ªôc ƒë√°o, kh√¥ng nhƒÉn, kh√¥ng x√π. V·∫£i co gi√£n nh·∫π t·∫°o c·∫£m gi√°c tho·∫£i m√°i. Thi·∫øt k·∫ø t√∫i c∆°i ti·ªán d·ª•ng t√¥n l√™n v·∫ª ƒë·∫πp hi·ªán ƒë·∫°i, thanh l·ªãch. L√† s·ª± l·ª±a ch·ªçn ho√†n h·∫£o cho nh·ªØng qu√Ω c√¥ s√†nh ƒëi·ªáu.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619470/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',NULL,'Vest N·ªØ Gile C√∫c B·ªçc T√∫i C∆°i',590000.00,590000.00,0,0,'vest-nu-gile-cuc-boc-tui-coi',_binary '','Vest gile n·ªØ v·ªõi ch·∫•t li·ªáu double face kim c∆∞∆°ng ƒë·ªôc ƒë√°o, kh√¥ng nhƒÉn, kh√¥ng x√π. V·∫£i co gi√£n nh·∫π t·∫°o c·∫£m gi√°c tho·∫£i m√°i. Thi·∫øt k·∫ø t√∫i c∆°i ti·ªán d·ª•ng t√¥n l√™n v·∫ª ƒë·∫πp hi·ªán ƒë·∫°i, thanh l·ªãch. L√† s·ª± l·ª±a ch·ªçn ho√†n h·∫£o cho nh·ªØng qu√Ω c√¥ s√†nh ƒëi·ªáu.','TRENDING',0,0,_binary 'PQLA\Î\‡H-Ø3\Ó\\\Ÿ\‘3'),(_binary 'çuv\‹E$DYí$\ƒmû4he','2024-12-19 13:34:27.039000','2024-12-19 13:34:27.039000','2025-01-15 09:03:10.284000','ZBE174','M√πa h√® n√≥ng b·ª©c s·∫Ω lu√¥n c·∫ßn ƒë·∫øn nh·ªØng chi·∫øc √°o thun v·ª´a nh·∫π nh√†ng v·ª´a tho√°ng m√°t ƒë·ªÉ gi·∫£m b·ªõt c√°i b√≠ b√°ch. Ngo√†i vi·ªác s·ª≠ d·ª•ng c√°c thi·∫øt b·ªã l√†m m√°t, gi·∫£i ph√°p h·ªØu hi·ªáu m√† kh√¥ng ph·∫£i ai c≈©ng ch√∫ tr·ªçng ƒë√≥ l√† t√¨m ƒë·∫øn nh·ªØng s·∫£n ph·∫©m m·∫∑c tho√°ng, c√≥ ƒë·ªô th·∫•m h√∫t cao. √Åo thun YODY ra ƒë·ªùi v·ªõi m·ª•c ti√™u n√†y. Nh·ªØng chi·∫øc √°o thun tr·∫£i qua qu√° tr√¨nh nghi√™n c·ª©u b·ªÅn b·ªâ c·ªßa ƒë·ªôi ng≈© thi·∫øt k·∫ø nay ƒë√£ ƒë∆∞·ª£c ra m·∫Øt s·ªü h·ªØu nh·ªØng ∆∞u ƒëi·ªÉm n·ªïi b·∫≠t v·ªÅ gi√° c·∫£ v√† t√≠nh nƒÉng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615267/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',NULL,'T-shirt Nam Slimfit Thun Rib Cotton M·ªÅm',149000.00,149000.00,0,0,'t-shirt-nam-slimfit-thun-rib-cotton-mem',_binary '','Everyday Basics: S·∫¢N PH·∫®M T·ªêT - GI√Å TR·∫¢I NGHI·ªÜM M·ªÅm m·∫°i - Th·∫•m h√∫t - Co gi√£n hi·ªáu qu·∫£. √Åo thun nam basic YODY l√† b√≠ k√≠p cho m√πa h√® tho·∫£i m√°i c·ªßa c√°c anh. M·ªôt thi·∫øt k·∫ø ƒë∆°n gi·∫£n, b·∫£ng m√†u phong ph√∫ v√† t·ªëi ∆∞u s·ª± tho·∫£i m√°i trong th·ªùi ti·∫øt n·∫Øng n√≥ng m√πa h√®. S·ªü h·ªØu ngay nh√©!','NEW_ARRIVALS',0,0,_binary 'ë\Û#VzK√ë\Á}aÇ\‰É'),(_binary 'ºà\Œ\‹csO©ë}fkx','2024-12-19 14:50:48.144000','2024-12-19 14:50:48.144000','2025-01-15 09:03:10.326000','JGI879','Vest gile n·ªØ v·ªõi ch·∫•t li·ªáu double face kim c∆∞∆°ng ƒë·ªôc ƒë√°o, kh√¥ng nhƒÉn, kh√¥ng x√π. V·∫£i co gi√£n nh·∫π t·∫°o c·∫£m gi√°c tho·∫£i m√°i. Thi·∫øt k·∫ø t√∫i c∆°i ti·ªán d·ª•ng t√¥n l√™n v·∫ª ƒë·∫πp hi·ªán ƒë·∫°i, thanh l·ªãch. L√† s·ª± l·ª±a ch·ªçn ho√†n h·∫£o cho nh·ªØng qu√Ω c√¥ s√†nh ƒëi·ªáu.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619848/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',NULL,'Vest N·ªØ D√°ng Blazer N·∫Øp T√∫i X·∫ª Sau',899000.00,899000.00,0,0,'vest-nu-dang-blazer-nap-tui-xe-sau',_binary '','Vest gile n·ªØ v·ªõi ch·∫•t li·ªáu double face kim c∆∞∆°ng ƒë·ªôc ƒë√°o, kh√¥ng nhƒÉn, kh√¥ng x√π. V·∫£i co gi√£n nh·∫π t·∫°o c·∫£m gi√°c tho·∫£i m√°i. Thi·∫øt k·∫ø t√∫i c∆°i ti·ªán d·ª•ng t√¥n l√™n v·∫ª ƒë·∫πp hi·ªán ƒë·∫°i, thanh l·ªãch. L√† s·ª± l·ª±a ch·ªçn ho√†n h·∫£o cho nh·ªØng qu√Ω c√¥ s√†nh ƒëi·ªáu.','TRENDING',0,0,_binary 'PQLA\Î\‡H-Ø3\Ó\\\Ÿ\‘3'),(_binary '\…ZR∫¸3Bg©H\nn~h%','2024-12-19 14:10:40.495000','2024-12-19 14:10:40.495000','2025-01-15 09:03:10.363000','WPE143','Th·∫•u hi·ªÉu mong mu·ªën s·ª≠ d·ª•ng nh·ªØng s·∫£n ph·∫©m th·ªùi trang tho√°ng m√°t, th·∫•m h√∫t t·ªët cho m√πa h√®, ƒë·ªôi ng≈© YODY nghi√™n c·ª©u v√† cho r·ªùi ƒë·ªùi s·∫£n ph·∫©m √°o polo Airy Cool v·ªõi nh·ªØng t√≠nh nƒÉng v∆∞·ª£t tr·ªôi. C√¥ng ngh·ªá v·∫£i FREEZING ti√™n ti·∫øn, h·∫° nhi·ªát cho ng√†y h√®.S·∫£n ph·∫©m ƒë∆∞·ª£c thi·∫øt k·∫ø v·ªõi 85% Nylon v√† 15% Spandex. Trong ƒë√≥, s·ª£i Nylon l√† c·∫•u t·∫°o ch√≠nh gi√∫p t·∫°o c·∫£m gi√°c tho·∫£i m√°i, d·ªÖ ch·ªãu khi m·∫∑c.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617440/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',NULL,'Qu·∫ßn Gi√≥ D√†i Yoguu B·ªï Th√¢n C√≥ T√∫i S∆∞·ªùn',300000.00,300000.00,0,0,'quan-gio-dai-yoguu-bo-than-co-tui-suon',_binary '','Tho·∫£i m√°i v·∫≠n ƒë·ªông v·ªõi qu·∫ßn gi√≥ d√†i YOGUU ch·∫•t li·ªáu Guu cao c·∫•p. V·∫£i nhƒÉn t·ª± nhi√™n, b·ªÅn m√†u, form d√°ng chu·∫©n. Thi·∫øt k·∫ø t√∫i s∆∞·ªùn ti·ªán l·ª£i, ph√π h·ª£p nhi·ªÅu phong c√°ch. Gi·∫∑t m√°y d·ªÖ d√†ng, nhanh kh√¥.','HOT_DEALS',0,0,_binary 'i)\\Ã2A~ñ7≠	∂['),(_binary '◊∏dìL≥âUåÜk◊ª\◊','2024-12-19 13:17:21.647000','2024-12-19 13:17:21.647000','2025-01-15 09:03:10.390000','JPF593','<p><strong>Ch·∫•t li·ªáu ƒë·∫≥ng c·∫•p:</strong><br>ƒê∆∞·ª£c l√†m t·ª´ 100% Cotton USA cao c·∫•p, √°o T-shirt mang l·∫°i c·∫£m gi√°c m·ªÅm m·∫°i, tho√°ng m√°t v√† d·ªÖ ch·ªãu su·ªët c·∫£ ng√†y d√†i. Ch·∫•t li·ªáu cotton t·ª± nhi√™n gi√∫p th·∫•m h√∫t m·ªì h√¥i t·ªët, kh√¥ng g√¢y k√≠ch ·ª©ng da, ph√π h·ª£p cho m·ªçi lo·∫°i th·ªùi ti·∫øt.</p><figure class=\"image\"><img style=\"aspect-ratio:1676/2276;\" src=\"https://bizweb.dktcdn.net/100/438/408/files/cotton-usa-01-c6e1caef-f6f9-4116-b198-f1f2136418cb.jpg?v=1687402779123\" width=\"1676\" height=\"2276\"></figure><p><strong>Thi·∫øt k·∫ø t·ªëi gi·∫£n, sang tr·ªçng:</strong><br>Phi√™n b·∫£n Premium ƒë∆∞·ª£c thi·∫øt k·∫ø v·ªõi phong c√°ch t·ªëi gi·∫£n nh∆∞ng tinh t·∫ø, d·ªÖ d√†ng ph·ªëi h·ª£p v·ªõi nhi·ªÅu lo·∫°i trang ph·ª•c t·ª´ qu·∫ßn jeans, qu·∫ßn short ƒë·∫øn qu·∫ßn √¢u. ƒê∆∞·ªùng may t·ªâ m·ªâ, form d√°ng chu·∫©n, t√¥n l√™n s·ª± kh·ªèe kho·∫Øn v√† l·ªãch l√£m c·ªßa ph√°i m·∫°nh.</p><p><strong>ƒê·ªô b·ªÅn v∆∞·ª£t tr·ªôi:</strong><br>√Åo kh√¥ng ch·ªâ ƒë·∫πp m√† c√≤n b·ªÅn b·ªâ v·ªõi th·ªùi gian nh·ªù c√¥ng ngh·ªá x·ª≠ l√Ω v·∫£i hi·ªán ƒë·∫°i, gi√∫p ch·ªëng co r√∫t, ch·ªëng nhƒÉn v√† gi·ªØ m√†u l√¢u. B·∫°n c√≥ th·ªÉ y√™n t√¢m s·ª≠ d·ª•ng √°o trong nhi·ªÅu l·∫ßn gi·∫∑t m√† v·∫´n gi·ªØ ƒë∆∞·ª£c phom d√°ng ban ƒë·∫ßu.</p>','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614242/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',NULL,'√Åo Tshirt Nam Cotton USA Phi√™n B·∫£n Premium',80000.00,80000.00,0,0,'ao-tshirt-nam-cotton-usa-phien-ban-premium',_binary '','Thi·∫øt k·∫ø c·ªï tr√≤n c∆° b·∫£n c√πng d√°ng su√¥ng gi√∫p t·∫°o s·ª± tho·∫£i m√°i c·ª≠ ƒë·ªông cho ng∆∞·ªùi m·∫∑c. S·ª£i cotton ch·∫•t l∆∞·ª£ng cao v·ªõi ƒë·ªô m·∫£nh v√† kh·∫£ nƒÉng nhu·ªôm ∆∞u vi·ªát. ƒêa nƒÉng, m√†u s·∫Øc ƒëa d·∫°ng l·ª±a ch·ªçn.','NEW_ARRIVALS',0,0,_binary 'ë\Û#VzK√ë\Á}aÇ\‰É');
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_discount`
--

DROP TABLE IF EXISTS `product_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_discount` (
  `product_id` binary(16) NOT NULL,
  `discount_id` binary(16) NOT NULL,
  KEY `FK8q5g6698ts6uqig91bmm3ukb7` (`discount_id`),
  KEY `FKr5ttw8wovl5nkcc9ysfc16fkk` (`product_id`),
  CONSTRAINT `FK8q5g6698ts6uqig91bmm3ukb7` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`),
  CONSTRAINT `FKr5ttw8wovl5nkcc9ysfc16fkk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_discount`
--

LOCK TABLES `product_discount` WRITE;
/*!40000 ALTER TABLE `product_discount` DISABLE KEYS */;
INSERT INTO `product_discount` VALUES (_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary 'S˚õŒè\"JÊüâ˛(B\–L\Ù');
/*!40000 ALTER TABLE `product_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_image`
--

DROP TABLE IF EXISTS `product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_image` (
  `id` binary(16) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `delete_at` datetime(6) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `is_thumbnail` bit(1) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `color_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqcabopu2ex77fymxr9rhxb5uj` (`color_id`),
  KEY `FK6oo0cvcdtb6qmwsga468uuukk` (`product_id`),
  CONSTRAINT `FK6oo0cvcdtb6qmwsga468uuukk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKqcabopu2ex77fymxr9rhxb5uj` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (_binary ' jó?-@˘ÆUa\Û\Ã¯?','2024-12-19 14:50:53.040000','2024-12-19 14:50:53.040000','2024-12-19 14:50:53.040000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619852/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary 't4©LNãO\œ˛\⁄\⁄','2024-12-19 14:15:04.173000','2024-12-19 14:15:04.173000','2024-12-19 14:15:04.173000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617703/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary ' \˜¬å`BkÅ09ü\”]'),(_binary '™\Ë\⁄{Ahë©≠éê\Õg','2024-12-19 13:34:34.297000','2024-12-19 13:34:34.297000','2024-12-19 13:34:34.297000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615273/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '¸\Ù3<\\J˝øB0<3ò´ü',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary '\–v\‡âKµü:\¬eïÄ','2024-12-19 13:17:27.377000','2024-12-19 13:17:27.377000','2024-12-19 13:17:27.377000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614246/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary 'ñè∫JK@\Ó∞Hå1\∆\ÿ\…',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary 'p\ÈS\œL\n∫¯}œ†','2024-12-19 13:34:28.911000','2024-12-19 13:34:28.911000','2024-12-19 13:34:28.911000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615268/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary '~\ı1ç\ÕOTéUi\Ã\ˆw\’\◊','2024-12-19 13:17:22.388000','2024-12-19 13:17:22.388000','2024-12-19 13:17:22.388000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614242/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\Ô)aˇ/\ÊD∑¶°F\Zê\€1',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary 'jIÿ£üNvû‹≠¯O3à\Œ','2024-12-19 14:50:49.233000','2024-12-19 14:50:49.233000','2024-12-19 14:50:49.233000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619848/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary '\ı´9´ìI√∏QX\Û|ßH','2024-12-19 14:15:02.392000','2024-12-19 14:15:02.392000','2024-12-19 14:15:02.392000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617701/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary ' \˜¬å`BkÅ09ü\”]'),(_binary '\"ä±˘å\‰IÉ≥/1r&%>P','2024-12-19 13:34:27.943000','2024-12-19 13:34:27.943000','2024-12-19 13:34:27.943000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615267/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary '\"ﬂóJ=VK¸ì±\»¿qóiü','2024-12-19 13:34:29.946000','2024-12-19 13:34:29.946000','2024-12-19 13:34:29.946000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615269/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary '*è9≤§}J\˜é ç6=*\Ê\À','2024-12-19 13:17:24.296000','2024-12-19 13:17:24.296000','2024-12-19 13:17:24.296000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614243/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\Ô)aˇ/\ÊD∑¶°F\Zê\€1',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary '-åøñ≠O5õ\–éÉ0\€','2024-12-19 13:55:06.481000','2024-12-19 13:55:06.481000','2024-12-19 13:55:06.481000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616505/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '¸\ÌXW3Nﬂ∑\r7w\—d'),(_binary '/\ﬂ^\À_˛AÅØ\√@¨ã\˜w','2024-12-19 13:55:07.443000','2024-12-19 13:55:07.443000','2024-12-19 13:55:07.443000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616506/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '¸\ÌXW3Nﬂ∑\r7w\—d'),(_binary '4\ıãÑdüKèá∆ÑÇz\–\‹\·','2024-12-19 13:46:31.911000','2024-12-19 13:46:31.911000','2024-12-19 13:46:31.911000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615991/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'HéèqE∂±\nm±\ÎR;'),(_binary '9\0\»£aK≥≤∂\ﬂ\˜]t','2024-12-19 14:50:52.138000','2024-12-19 14:50:52.138000','2024-12-19 14:50:52.138000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619851/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary '9q\ÀJ˛\€M0ßü\Í˙úl','2024-12-19 13:17:28.503000','2024-12-19 13:17:28.503000','2024-12-19 13:17:28.503000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614247/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary 'ñè∫JK@\Ó∞Hå1\∆\ÿ\…',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary ';¥\…l¯SOƒ∫\€\„÷∞\Èr6','2024-12-19 14:10:45.724000','2024-12-19 14:10:45.724000','2024-12-19 14:10:45.724000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617445/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '\…ZR∫¸3Bg©H\nn~h%'),(_binary '=Åô	∏aIY†ø[m%/¥','2024-12-19 14:44:37.274000','2024-12-19 14:44:37.274000','2024-12-19 14:44:37.274000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619476/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary 'q!†\Ê\∆Go≥k\ÚáQª£'),(_binary 'D\≈27.{C7®\Ô\ÕWñ\'s\Ï','2024-12-19 14:50:56.333000','2024-12-19 14:50:56.333000','2024-12-19 14:50:56.333000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619855/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary 'GoJs±DÇ~cü¥!','2024-12-19 14:44:36.089000','2024-12-19 14:44:36.089000','2024-12-19 14:44:36.089000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619475/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary 'q!†\Ê\∆Go≥k\ÚáQª£'),(_binary 'JÅÖ&>@…îs\⁄4k8(0','2024-12-19 13:46:29.370000','2024-12-19 13:46:29.370000','2024-12-19 13:46:29.370000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615988/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'HéèqE∂±\nm±\ÎR;'),(_binary 'Q&\Í\Ÿ\\IE´¨àè\ÙåLL±','2024-12-19 13:34:31.142000','2024-12-19 13:34:31.142000','2024-12-19 13:34:31.142000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615270/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary 'R\Z©ºú§L»öç]ßFøÄ','2024-12-19 13:55:05.500000','2024-12-19 13:55:05.500000','2024-12-19 13:55:05.500000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616504/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '¸\ÌXW3Nﬂ∑\r7w\—d'),(_binary 'R/®â\ \«J∆ü”ø à\Áô!','2024-12-19 13:55:03.580000','2024-12-19 13:55:03.580000','2024-12-19 13:55:03.580000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616502/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '¸\ÌXW3Nﬂ∑\r7w\—d'),(_binary 'UV\‰\À\ÎàAGÅ¸Øç\nç~∫','2024-12-19 14:10:41.476000','2024-12-19 14:10:41.476000','2024-12-19 14:10:41.476000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617440/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '\…ZR∫¸3Bg©H\nn~h%'),(_binary 'X\ˆ $Ø\…HòÄ˛ªÉ*U','2024-12-19 13:17:25.434000','2024-12-19 13:17:25.434000','2024-12-19 13:17:25.434000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614244/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\Ô)aˇ/\ÊD∑¶°F\Zê\€1',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary '^.\n,zLI†s’Ü3#','2024-12-19 13:46:30.221000','2024-12-19 13:46:30.221000','2024-12-19 13:46:30.221000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615989/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'HéèqE∂±\nm±\ÎR;'),(_binary 'b}-˙\ÀFŸé\–\◊1iORE','2024-12-19 14:15:04.971000','2024-12-19 14:15:04.971000','2024-12-19 14:15:04.971000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617704/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary ' \˜¬å`BkÅ09ü\”]'),(_binary 'e\Àï\Ë\‘Dπ•\ÎÖX\˜û¸','2024-12-19 13:17:29.714000','2024-12-19 13:17:29.714000','2024-12-19 13:17:29.714000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614248/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary 'ñè∫JK@\Ó∞Hå1\∆\ÿ\…',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary 'gB\‰$îöE\ËΩ3∞õW\Ê¡','2024-12-19 14:10:44.128000','2024-12-19 14:10:44.128000','2024-12-19 14:10:44.128000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617443/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '\…ZR∫¸3Bg©H\nn~h%'),(_binary 'g\Ô^Ø≠¸Ohæî®OuH˛ú','2024-12-19 14:10:47.037000','2024-12-19 14:10:47.037000','2024-12-19 14:10:47.037000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617446/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '\…ZR∫¸3Bg©H\nn~h%'),(_binary 'k\"âi)NFøj\Ú4I\\h`','2024-12-19 14:50:54.172000','2024-12-19 14:50:54.172000','2024-12-19 14:50:54.172000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619853/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary 'y(izAG9ç\‘O;g,X≤','2024-12-19 13:55:08.143000','2024-12-19 13:55:08.143000','2024-12-19 13:55:08.143000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616507/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '¸\ÌXW3Nﬂ∑\r7w\—d'),(_binary '\"U\Î_Kï±m\Ì.£\Ê\ı','2024-12-19 13:34:32.120000','2024-12-19 13:34:32.120000','2024-12-19 13:34:32.120000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615271/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '¸\Ù3<\\J˝øB0<3ò´ü',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary 'ã\Î\’p\ﬁ\ŸFOª\Õ3\ÁPø°','2024-12-19 14:15:01.404000','2024-12-19 14:15:01.404000','2024-12-19 14:15:01.404000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617700/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary ' \˜¬å`BkÅ09ü\”]'),(_binary 'éT\ÿ0\€*G¸Ü<≥2˙0F)','2024-12-19 13:34:33.019000','2024-12-19 13:34:33.019000','2024-12-19 13:34:33.019000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615272/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '¸\Ù3<\\J˝øB0<3ò´ü',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary 'ó?AHbæDŸ≥∫]cök\œT','2024-12-19 14:44:35.169000','2024-12-19 14:44:35.169000','2024-12-19 14:44:35.169000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619474/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary 'q!†\Ê\∆Go≥k\ÚáQª£'),(_binary '£¸R˚\ÌGöí+≤ó\◊´','2024-12-19 13:17:26.208000','2024-12-19 13:17:26.208000','2024-12-19 13:17:26.208000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614245/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary 'ñè∫JK@\Ó∞Hå1\∆\ÿ\…',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary 'ΩR\œl•	O√èIô\ÚN\€–õ','2024-12-19 14:10:42.419000','2024-12-19 14:10:42.419000','2024-12-19 14:10:42.419000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617441/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '\…ZR∫¸3Bg©H\nn~h%'),(_binary '\≈\ÏFvkáO óï\Í\'\‰¯\‘r','2024-12-19 14:50:55.035000','2024-12-19 14:50:55.035000','2024-12-19 14:50:55.035000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619854/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary '\»\Ê˘í¶\ŸM∑í	}\⁄\˜è\ÿ','2024-12-19 13:17:23.197000','2024-12-19 13:17:23.197000','2024-12-19 13:17:23.197000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614242/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\Ô)aˇ/\ÊD∑¶°F\Zê\€1',_binary '◊∏dìL≥âUåÜk◊ª\◊'),(_binary '\…\Ÿ\√.\›C_¥\Îb4J\"\¬','2024-12-19 14:10:43.316000','2024-12-19 14:10:43.316000','2024-12-19 14:10:43.316000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617442/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '\…ZR∫¸3Bg©H\nn~h%'),(_binary 'Œä˛R&@rûV[\ÿ,\»R','2024-12-19 14:15:03.256000','2024-12-19 14:15:03.256000','2024-12-19 14:15:03.256000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617702/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary ' \˜¬å`BkÅ09ü\”]'),(_binary '\—«É?j\ƒCàöƒì{ÅìèÜ','2024-12-19 13:46:31.015000','2024-12-19 13:46:31.015000','2024-12-19 13:46:31.015000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615990/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'HéèqE∂±\nm±\ÎR;'),(_binary '\‘^R?ôXF\‰ªM\ÙjJBH¶','2024-12-19 14:44:30.979000','2024-12-19 14:44:30.979000','2024-12-19 14:44:30.979000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619470/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'q!†\Ê\∆Go≥k\ÚáQª£'),(_binary '\÷}\È\ÊKÑA©≥ñ}5z˛´','2024-12-19 14:44:31.939000','2024-12-19 14:44:31.939000','2024-12-19 14:44:31.939000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619471/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'q!†\Ê\∆Go≥k\ÚáQª£'),(_binary '\Ÿ•|\‡SFl¢ö\Õ;¢GcA','2024-12-19 14:44:32.766000','2024-12-19 14:44:32.766000','2024-12-19 14:44:32.766000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619472/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'q!†\Ê\∆Go≥k\ÚáQª£'),(_binary '\ﬁ°•hFu≥¸öp˚A\Ò','2024-12-19 14:44:34.225000','2024-12-19 14:44:34.225000','2024-12-19 14:44:34.225000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619473/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'q!†\Ê\∆Go≥k\ÚáQª£'),(_binary '·∫ãˇ\“aCÆß?QÇJ','2024-12-19 13:34:35.215000','2024-12-19 13:34:35.215000','2024-12-19 13:34:35.215000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615274/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '¸\Ù3<\\J˝øB0<3ò´ü',_binary 'çuv\‹E$DYí$\ƒmû4he'),(_binary '\Í.^ˇâ\ÀJ\Zì\‚ø\n\Ô!','2024-12-19 14:15:00.286000','2024-12-19 14:15:00.286000','2024-12-19 14:15:00.286000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary ' \˜¬å`BkÅ09ü\”]'),(_binary '\Í\Ÿv|∞Goª©\›a^1M','2024-12-19 14:50:50.080000','2024-12-19 14:50:50.080000','2024-12-19 14:50:50.080000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619849/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary '\ˆ;´Q\ \ÿ@≠ëÜ)	\€a®b','2024-12-19 14:50:50.984000','2024-12-19 14:50:50.984000','2024-12-19 14:50:50.984000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619850/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'ºà\Œ\‹csO©ë}fkx'),(_binary '¯ª\€∞\ÕFú£rp™cù','2024-12-19 14:10:44.943000','2024-12-19 14:10:44.943000','2024-12-19 14:10:44.943000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617444/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Gray_.webp',_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '\…ZR∫¸3Bg©H\nn~h%');
/*!40000 ALTER TABLE `product_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_product_variants`
--

DROP TABLE IF EXISTS `product_product_variants`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_product_variants` (
  `product_id` binary(16) NOT NULL,
  `product_variants_id` binary(16) NOT NULL,
  UNIQUE KEY `UKk5p540pxm44ibxsmllfwtu72g` (`product_variants_id`),
  KEY `FK3qus8y6ras9eai3290vml4wh9` (`product_id`),
  CONSTRAINT `FK3qus8y6ras9eai3290vml4wh9` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKm8hny4ljn6w9txu9b6548go46` FOREIGN KEY (`product_variants_id`) REFERENCES `product_variant` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_product_variants`
--

LOCK TABLES `product_product_variants` WRITE;
/*!40000 ALTER TABLE `product_product_variants` DISABLE KEYS */;
INSERT INTO `product_product_variants` VALUES (_binary ' \˜¬å`BkÅ09ü\”]',_binary '≥\\}ºçL+ª˙ö;\Ë´p('),(_binary ' \˜¬å`BkÅ09ü\”]',_binary '-»ÜüöHmøˇr´\Z8uí'),(_binary ' \˜¬å`BkÅ09ü\”]',_binary 'hHo£5áMÇ©\Ùˇo4©æ~'),(_binary ' \˜¬å`BkÅ09ü\”]',_binary '≈ö¶∞VòL\Íò!\·S\‰â\Ëz'),(_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary '¶\ﬂ\‹)\›C€ö\ıP\÷6o'),(_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary 'M#ëïNáAñâ“ûTrØH'),(_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary '[\È5\Ã\ﬁ#AG•19POk è'),(_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary '©n7\◊7L\ÁΩj¨ö©>\Œ\Í'),(_binary 'HéèqE∂±\nm±\ÎR;',_binary 'uå`¶aI»¨“ö\Ëö˝\ÈI'),(_binary 'HéèqE∂±\nm±\ÎR;',_binary 'ûù£\ÓalO}´\fíL'),(_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary '\ŒfH4ÜN¨´Wä¡Ø¸'),(_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary 'qòNÑ{[J◊Åi'),(_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary 'ô¸\n-¨@°æˇG¥íj97'),(_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary '¸HÄ2\ÔM§ìlë†tî´\Ù'),(_binary 'çuv\‹E$DYí$\ƒmû4he',_binary '\n≤§A∂NÑ}µΩ\Á\∆'),(_binary 'çuv\‹E$DYí$\ƒmû4he',_binary 'O}Ö,j)Nhàj≈∫<˚\'),(_binary 'çuv\‹E$DYí$\ƒmû4he',_binary 'lá≤ó\ŒA∞âCê\‘7\Ú|+'),(_binary 'çuv\‹E$DYí$\ƒmû4he',_binary 'ö\·ó\ŸJ?JÅ\Z \Ÿ ®\'),(_binary 'ºà\Œ\‹csO©ë}fkx',_binary 'A(†ùôEnö\Ê¨wÑ\·G}'),(_binary 'ºà\Œ\‹csO©ë}fkx',_binary 'R∂çòF\'C«¨ö1…Æ\„ü@'),(_binary 'ºà\Œ\‹csO©ë}fkx',_binary 'õ¿YÆi\˜L\"ÜGsŸÉ<\\Ÿ'),(_binary 'ºà\Œ\‹csO©ë}fkx',_binary 'ƒ§\√:NEI®ì)#\ƒ¡cΩ'),(_binary '\…ZR∫¸3Bg©H\nn~h%',_binary '	;w\ﬁ&@5∂l-¯ã:ã('),(_binary '\…ZR∫¸3Bg©H\nn~h%',_binary 'zR\ÒûÉ\«O9òrwò^y5'),(_binary '\…ZR∫¸3Bg©H\nn~h%',_binary 'Ñ™\Ë&O–íL∂N\Î9©'),(_binary '\…ZR∫¸3Bg©H\nn~h%',_binary '¡ú9L•≤A‘ÜáZc™ñØ'),(_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary '5˚Mj¬≤Nxº/y~h˚'),(_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary 'ó©uGàKvÆ +wTk|'),(_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary '¢	\“\‡ºDJ´»§6Õìr1'),(_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary '±\Õu√ó9Jh•Q(rS\«');
/*!40000 ALTER TABLE `product_product_variants` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_sizes`
--

DROP TABLE IF EXISTS `product_sizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_sizes` (
  `product_id` binary(16) NOT NULL,
  `size` enum('L','M','S','XL','XXL') DEFAULT NULL,
  KEY `FK4w69qsh5hd062xv3hqkpgpdpu` (`product_id`),
  CONSTRAINT `FK4w69qsh5hd062xv3hqkpgpdpu` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sizes`
--

LOCK TABLES `product_sizes` WRITE;
/*!40000 ALTER TABLE `product_sizes` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_sizes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_variant`
--

DROP TABLE IF EXISTS `product_variant`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_variant` (
  `id` binary(16) NOT NULL,
  `code_variant` varchar(255) NOT NULL,
  `stock_quantity` int NOT NULL,
  `color_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  `size_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7ic0arew3txgoctacehy5nal9` (`color_id`),
  KEY `FKgrbbs9t374m9gg43l6tq1xwdj` (`product_id`),
  KEY `FKn1veiq5y5r3fb6qw0n030o7mh` (`size_id`),
  CONSTRAINT `FK7ic0arew3txgoctacehy5nal9` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `FKgrbbs9t374m9gg43l6tq1xwdj` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKn1veiq5y5r3fb6qw0n030o7mh` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_variant`
--

LOCK TABLES `product_variant` WRITE;
/*!40000 ALTER TABLE `product_variant` DISABLE KEYS */;
INSERT INTO `product_variant` VALUES (_binary '	;w\ﬁ&@5∂l-¯ã:ã(','WPE143-BLACK-SMALL',100,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '\…ZR∫¸3Bg©H\nn~h%',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary '\n≤§A∂NÑ}µΩ\Á\∆','ZBE174-BLUE-SMALL',10,_binary '¸\Ù3<\\J˝øB0<3ò´ü',_binary 'çuv\‹E$DYí$\ƒmû4he',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary '\ŒfH4ÜN¨´Wä¡Ø¸','LBX195-GRAY-MEDIUM',15,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary 'B˘}ìMZ±p≤»∫TOU'),(_binary 'qòNÑ{[J◊Åi','LBX195-BLACK-MEDIUM',20,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary 'B˘}ìMZ±p≤»∫TOU'),(_binary 'uå`¶aI»¨“ö\Ëö˝\ÈI','KXG917-WHITE-EXTRA LARGE',20,_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'HéèqE∂±\nm±\ÎR;',_binary 'ä\÷\Á~rE≤kp	8\È'),(_binary 'A(†ùôEnö\Ê¨wÑ\·G}','JGI879-BLACK-SMALL',20,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'ºà\Œ\‹csO©ë}fkx',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary 'ô¸\n-¨@°æˇG¥íj97','LBX195-GRAY-SMALL',20,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary '≥\\}ºçL+ª˙ö;\Ë´p(','SWF205-BLACK-EXTRA LARGE',20,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary ' \˜¬å`BkÅ09ü\”]',_binary 'ä\÷\Á~rE≤kp	8\È'),(_binary '¶\ﬂ\‹)\›C€ö\ıP\÷6o','UFB450-GRAY-LARGE',20,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary '6r=˙\€A√òΩ\râ\Ì\']'),(_binary '-»ÜüöHmøˇr´\Z8uí','SWF205-GRAY-SMALL',20,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary ' \˜¬å`BkÅ09ü\”]',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary '5˚Mj¬≤Nxº/y~h˚','JPF593-YELLOW-SMALL',10,_binary 'ñè∫JK@\Ó∞Hå1\∆\ÿ\…',_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary 'M#ëïNáAñâ“ûTrØH','UFB450-BLACK-EXTRA LARGE',20,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary 'ä\÷\Á~rE≤kp	8\È'),(_binary 'O}Ö,j)Nhàj≈∫<˚\','ZBE174-BLUE-MEDIUM',5,_binary '¸\Ù3<\\J˝øB0<3ò´ü',_binary 'çuv\‹E$DYí$\ƒmû4he',_binary 'B˘}ìMZ±p≤»∫TOU'),(_binary 'R∂çòF\'C«¨ö1…Æ\„ü@','JGI879-BLACK-MEDIUM',35,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'ºà\Œ\‹csO©ë}fkx',_binary 'B˘}ìMZ±p≤»∫TOU'),(_binary '[\È5\Ã\ﬁ#AG•19POk è','UFB450-GRAY-LARGE',20,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary '6r=˙\€A√òΩ\râ\Ì\']'),(_binary 'hHo£5áMÇ©\Ùˇo4©æ~','SWF205-BLACK-SMALL',10,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary ' \˜¬å`BkÅ09ü\”]',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary 'lá≤ó\ŒA∞âCê\‘7\Ú|+','ZBE174-BLACK-SMALL',15,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'çuv\‹E$DYí$\ƒmû4he',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary 'zR\ÒûÉ\«O9òrwò^y5','WPE143-GRAY-LARGE',20,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '\…ZR∫¸3Bg©H\nn~h%',_binary '6r=˙\€A√òΩ\râ\Ì\']'),(_binary 'Ñ™\Ë&O–íL∂N\Î9©','WPE143-BLACK-EXTRA LARGE',50,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '\…ZR∫¸3Bg©H\nn~h%',_binary 'ä\÷\Á~rE≤kp	8\È'),(_binary 'ó©uGàKvÆ +wTk|','JPF593-YELLOW-LARGE',10,_binary 'ñè∫JK@\Ó∞Hå1\∆\ÿ\…',_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary '6r=˙\€A√òΩ\râ\Ì\']'),(_binary 'ö\·ó\ŸJ?JÅ\Z \Ÿ ®\','ZBE174-BLACK-MEDIUM',5,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'çuv\‹E$DYí$\ƒmû4he',_binary 'B˘}ìMZ±p≤»∫TOU'),(_binary 'õ¿YÆi\˜L\"ÜGsŸÉ<\\Ÿ','JGI879-WHITE-SMALL',15,_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'ºà\Œ\‹csO©ë}fkx',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary 'ûù£\ÓalO}´\fíL','KXG917-WHITE-SMALL',20,_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'HéèqE∂±\nm±\ÎR;',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary '¢	\“\‡ºDJ´»§6Õìr1','JPF593-RED-LARGE',10,_binary '\Ô)aˇ/\ÊD∑¶°F\Zê\€1',_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary '6r=˙\€A√òΩ\râ\Ì\']'),(_binary '©n7\◊7L\ÁΩj¨ö©>\Œ\Í','UFB450-BLACK-EXTRA LARGE',20,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary '¸\ÌXW3Nﬂ∑\r7w\—d',_binary 'ä\÷\Á~rE≤kp	8\È'),(_binary '±\Õu√ó9Jh•Q(rS\«','JPF593-RED-SMALL',10,_binary '\Ô)aˇ/\ÊD∑¶°F\Zê\€1',_binary '◊∏dìL≥âUåÜk◊ª\◊',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary '¡ú9L•≤A‘ÜáZc™ñØ','WPE143-GRAY-SMALL',50,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary '\…ZR∫¸3Bg©H\nn~h%',_binary ' Ωt\⁄\…zM6ëVo%§\‹'),(_binary 'ƒ§\√:NEI®ì)#\ƒ¡cΩ','JGI879-WHITE-MEDIUM',30,_binary '5\ZêJBLFû0˚Ç©ˇ7x',_binary 'ºà\Œ\‹csO©ë}fkx',_binary 'B˘}ìMZ±p≤»∫TOU'),(_binary '≈ö¶∞VòL\Íò!\·S\‰â\Ëz','SWF205-GRAY-LARGE',15,_binary 'Gôöñ\ÎNƒ´?¡\ZL\\£e',_binary ' \˜¬å`BkÅ09ü\”]',_binary '6r=˙\€A√òΩ\râ\Ì\']'),(_binary '¸HÄ2\ÔM§ìlë†tî´\Ù','LBX195-BLACK-SMALL',10,_binary 'äeûv4J/Æ6Æ\œZ\‰m',_binary 'q!†\Ê\∆Go≥k\ÚáQª£',_binary ' Ωt\⁄\…zM6ëVo%§\‹');
/*!40000 ALTER TABLE `product_variant` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` binary(16) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK8sewwnpamngi6b1dwaa88askk` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (_binary 'C@*§%@§ÖD\v±Ö§','Role for regular admin with basic permissions','ADMIN'),(_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*','Role for regular users with basic permissions','USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_permissions`
--

DROP TABLE IF EXISTS `role_permissions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role_permissions` (
  `role_id` binary(16) NOT NULL,
  `permission_id` binary(16) NOT NULL,
  PRIMARY KEY (`role_id`,`permission_id`),
  KEY `FKh0v7u4w7mttcu81o8wegayr8e` (`permission_id`),
  CONSTRAINT `FKh0v7u4w7mttcu81o8wegayr8e` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FKlodb7xh4a2xjv39gc3lsop95n` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
INSERT INTO `role_permissions` VALUES (_binary 'C@*§%@§ÖD\v±Ö§',_binary '$\\Ÿ\¬Lsê\’*K9A\Ù'),(_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*',_binary '$\\Ÿ\¬Lsê\’*K9A\Ù'),(_binary 'C@*§%@§ÖD\v±Ö§',_binary 'u6ÓëΩÖGìè\‰L\Î(@S'),(_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*',_binary 'u6ÓëΩÖGìè\‰L\Î(@S'),(_binary 'C@*§%@§ÖD\v±Ö§',_binary '|Bòà≠\∆NwÜ3(\Ë∏\œ<'),(_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*',_binary '|Bòà≠\∆NwÜ3(\Ë∏\œ<'),(_binary 'C@*§%@§ÖD\v±Ö§',_binary '}1*°\Ì\Ê@∂æ7\◊ogx'),(_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*',_binary '}1*°\Ì\Ê@∂æ7\◊ogx'),(_binary 'C@*§%@§ÖD\v±Ö§',_binary 'µ\ÊyÃûLèµÖ&ìfëu'),(_binary 'Ç9;ë\ÿ˘GÍ©à p<pˇ*',_binary 'µ\ÊyÃûLèµÖ&ìfëu');
/*!40000 ALTER TABLE `role_permissions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id` binary(16) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (_binary '\0\r\ƒ\ÊJ	ã,\“¬±5\Ã','Extra Small','XS'),(_binary 'û\Ô;\«N¶\‡\ÏÖV\»','Double Extra Large','XXL'),(_binary ' Ωt\⁄\…zM6ëVo%§\‹','Small','S'),(_binary '6r=˙\€A√òΩ\râ\Ì\']','Large','L'),(_binary 'B˘}ìMZ±p≤»∫TOU','Medium','M'),(_binary 'ä\÷\Á~rE≤kp	8\È','Extra Large','XL'),(_binary 'éa\ˆØÑÖBá™åÉ\€\"£','32','32'),(_binary 'ì\◊∫\›G∂®Ñ|\«\‰¨I','30','30'),(_binary '®\‰!\≈\·B\ÚÄD\Ã\¬!0\Õ','34','34'),(_binary '©gUò?LÀê\ÌIu\÷\Ï&','28','28'),(_binary '\Í}\“0L\‚≠\rî8S`','36','36');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `delete_at` datetime(6) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `provider` enum('FACEBOOK','GOOGLE','MANUAL') NOT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  `code_expiry` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (_binary '\rl\«8\ÏM òMJFª)÷ß','2024-12-20 12:59:31.274000','2024-12-20 12:59:31.274000','2024-12-20 13:00:18.659000',NULL,'hoainamadm@gmail.com',_binary '','nam','huynh','{bcrypt}$2a$10$dqn.IP1jKFmUCWcKMepVX.KIoU8rFsUywTAbDYveV1k/9vFNbkX26','0123456789','MANUAL','717166',NULL),(_binary 'R\–3\"ÑD+∏\ﬂ\À\‰“é','2024-12-20 12:58:29.770000','2024-12-20 12:58:29.770000','2024-12-20 12:58:29.770000',NULL,'dev@gmail.com',_binary '\0','nam','huynh','{bcrypt}$2a$10$i4DT2gC0L5D/V6V.St0WEeyPuF0UsoUc96iaiYEs9aPYSna.lMfn.','0123456789','MANUAL','378903',NULL),(_binary 'åS\·˘§N∑µ\÷PKlä','2024-12-20 09:46:45.070000','2024-12-20 09:46:45.070000','2024-12-20 09:48:40.784000',NULL,'loc51936@donga.edu.vn',_binary '','Nguy·ªÖn','L·ªôc','{bcrypt}$2a$10$RrVoym./elJEczEdWg.4Nu8tuUNAEzSI81LvMZy0V7se0MVEMcv2i','0935277535','MANUAL','486064',NULL),(_binary ';J\≈\Á˙B\Ëû8\ÍG∑\È','2025-01-07 13:34:16.843000','2025-01-07 13:34:16.843000','2025-01-07 13:34:16.843000',NULL,'nam123@gmail.com',_binary '\0','Nam','Huynh','{bcrypt}$2a$10$vkOjxpFZvgpWpmL.VyoAz.M7FoFNuKc/4kJG7/pIwBw7GfaywiukO','+84773564824','MANUAL','737048',NULL),(_binary 'wJçè\È\ÿKj±6]2€ö','2024-12-20 07:16:13.973000','2024-12-20 07:16:13.973000','2024-12-20 07:16:13.973000',NULL,'nam@gmail.com',_binary '\0','nam','huynh','{bcrypt}$2a$10$nDMDoRftY8xdWyxAylpABu9PLzRhJj/tZzwXK0Sd6lmeW9NvzZVBa','0123456789','MANUAL','209368',NULL),(_binary 'ãOô¡ﬁ™Gµ∫ä{∑»â•U','2025-01-07 09:37:51.433000','2025-01-07 09:37:51.433000','2025-01-07 10:04:31.236000',NULL,'nam54529@donga.edu.vn',_binary '','dev','dev','{bcrypt}$2a$10$idvQ5w2yuazezNZB0Kn1DOycGzg3V8yqzEvEaAQZ900t2wYeDtJAa','+84342531726','MANUAL','460696',NULL),(_binary 'Ø\Ú\ﬁ\⁄}±L3±7è+eQcU','2025-01-06 09:09:19.255000','2025-01-06 09:09:19.255000','2025-01-06 09:09:19.255000',NULL,'testdev@gmai.com',_binary '\0','test','test','{bcrypt}$2a$10$HgSC28HNmjj7I6d3NXYZLOanas/iULmjs5MP1/0ihq.raH2mgCeke','0123456789','MANUAL','984027',NULL),(_binary '±\n\—\ƒ;¶@hÖ´ ?\‰|','2025-01-07 13:28:24.468000','2025-01-07 13:28:24.468000','2025-01-07 13:28:24.468000',NULL,'hoainamit@gmai.com',_binary '\0','Nam','Huynh','{bcrypt}$2a$10$l9NwaKinNzE/ynlWXEOIUe24UWlP1UtSPS0GdvOirFRbRtYlT57pe','+84773564824','MANUAL','896448',NULL),(_binary '∂a{î\›RL¿ó	\Ô=∑cæ','2024-12-28 10:13:13.689000','2024-12-28 10:13:13.689000','2024-12-28 10:29:55.904000',NULL,'hoainampp2002@gmai.com',_binary '','nam','huynh','{bcrypt}$2a$10$F0ySG/yYyF5xRO5BaYMIJ.tF2NzF.SmfGeVfPNSNYANbDspIANbKe','0123456789','MANUAL','984239',NULL),(_binary 'π	∫\ÊrK∂~h†Wü;p','2025-01-14 21:12:22.156000','2025-01-14 21:12:22.156000','2025-01-14 21:14:46.064000',NULL,'loc@gmail.com',_binary '','Nguyen','Loc','{bcrypt}$2a$10$xtynu/lk9wNZvhljxaGpDemF3RSV0ohWSi7c4TL2I2YX.o9mtQUYC','0193848343','MANUAL',NULL,NULL),(_binary '\ŸIÄ\\'\ÊD…∏Ω¡\Î¯yRÆ','2024-12-19 15:02:54.188000','2024-12-19 15:02:54.188000','2025-01-08 08:54:20.620000',NULL,'locvlog1.0@gmail.com',_binary '','Nguyen','Loc','{bcrypt}$2a$10$zJBpzb7DqmNDn8kID9GMpuKKkE/oYYPj3Vdx656RKH80qrU83tsEC','0912749215','MANUAL','279813',NULL),(_binary '\⁄\Êá6\œD@è\ƒp\Á\Õ\∆K-','2024-12-28 10:10:14.221000','2024-12-28 10:10:14.221000','2024-12-28 11:18:02.581000',NULL,'hoainamit4.0@gmai.com',_binary '','nam','huynh','{bcrypt}$2a$10$BdInjp8PrbcViRqI3cdCcerbDtmv.WCF5d5kd49MVfy./adx3ZskO','0123456789','MANUAL','780152',NULL),(_binary 'ˇÏñÜ\Ò\ﬂD	í\Ó\˜®êq\∆','2025-01-08 07:59:53.611000','2025-01-08 07:59:53.611000','2025-01-08 08:01:19.754000',NULL,'hoainamit4.0@gmail.com',_binary '','Nam','Huynh','{bcrypt}$2a$10$HfPhPeWcutfXUemGuwiHpOcczuG/RgziUAenBG9H9UwHUqsohI8nq','+84773564824','MANUAL','377103',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-15 16:20:00
