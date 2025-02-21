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
INSERT INTO `address` VALUES (_binary 'N*É\Ê\˜N\∏f1D¨\ﬂ\‚\n','H√† N·ªôi','Nam T·ª´ Li√™m',_binary '','Nh√†','0923893833','Ng√µ 7','Nguy√™n X√°',_binary '◊Ñ~?EÖéç h9\ÿ^');
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
INSERT INTO `auth_user_authority` VALUES (_binary '◊Ñ~?EÖéç h9\ÿ^',_binary 'C@*ÔøΩ%@ÔøΩÔøΩD'),(_binary '◊Ñ~?EÖéç h9\ÿ^',_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í'),(_binary '\Ìº¸ó˚INAì t\ÚR\\∑=',_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í');
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
  `event` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES (2,0,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739155821/BANNER/CAROUSEL/BANNER/CAROUSEL_DEV%20NGUYEN.png',_binary '','/home','slider-horizontal-01-0_mehymf','CAROUSEL','T·∫øt 2025'),(4,1,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1737101822/BANNER/CAROUSEL/BANNER/CAROUSEL_slider-horizontal-01-1_uhsxl0.jpg',_binary '','','slider-horizontal-01-1_uhsxl0','CAROUSEL','T·∫øt 2025'),(5,2,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1737101971/BANNER/CAROUSEL/BANNER/CAROUSEL_slider-horizontal-01-2_fmdeok.jpg',_binary '','','slider-horizontal-01-2_fmdeok','CAROUSEL','T·∫øt 2025'),(6,0,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1737102122/BANNER/COUNTDOWN/BANNER/COUNTDOWN_BANNER-FLASH-SALE_q8ubof.webp',_binary '','','BANNER-FLASH-SALE_q8ubof','COUNTDOWN','FLASH80'),(7,1,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1737102173/BANNER/COUNTDOWN/BANNER/COUNTDOWN_BANNER-FLASH-SALE-5-20_mkaqeq.jpg',_binary '','','BANNER-FLASH-SALE-5-20_mkaqeq','COUNTDOWN','FLASH80'),(8,0,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1737102328/BANNER/FEATURED/BANNER/FEATURED_hang-ban-chay-1000x670_bmifjt.webp',_binary '','','hang-ban-chay-1000x670_bmifjt','FEATURED','H√†ng b√°n ch·∫°y'),(9,1,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1737102365/BANNER/FEATURED/BANNER/FEATURED_hang-ban-chay-1800x600_hso4vk.webp',_binary '','','hang-ban-chay-1800x600_hso4vk','FEATURED','H√†ng b√°n ch·∫°y');
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
INSERT INTO `cart` VALUES (_binary '!¯\Ê\\DLWél¢{wó\“˘',0.00,_binary '◊Ñ~?EÖéç h9\ÿ^'),(_binary 'l[S>|Ω@¿≤ò£\'\Ù\\\rz',0.00,_binary '\Ìº¸ó˚INAì t\ÚR\\∑='),(_binary '†à\€\’BA\˜ûç\∆¿\‡◊§',0.00,NULL),(_binary '∂ÿè\ãWHÆlÑmû#ä¨',0.00,NULL);
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
  `product_image_id` binary(16) DEFAULT NULL,
  `create_at` datetime(6) DEFAULT NULL,
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
  `description` text,
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
INSERT INTO `category` VALUES (_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Khi nh·∫Øc ƒë·∫øn t·ªß ƒë·ªì c·ªßa c√°c ch·ªã em, b·∫°n s·∫Ω ho√†n to√†n b·ªã thu h√∫t b·ªüi nh·ªØng chi·ªÖc¬†√°o n·ªØ ƒëang ƒë∆∞·ª£c treo ngay ng·∫Øn. Th·ªùi trang √°o n·ªØ ki·ªÉu¬†lu√¥n ƒëa d·∫°ng v√† ph√°t tri·ªÉn kh√¥ng ng·ª´ng v·ªõi ƒëa d·∫°ng thi·∫øt k·∫ø. V·∫≠y nh·ªØng ki·ªÉu √°o n·ªØ ƒë·∫πp n√†o ƒëang ƒë∆∞·ª£c ƒë√¥ng ƒë·∫£o ph√°i n·ªØ quan t√¢m? H√£y c√πng TRENDISTA t√¨m hi·ªÉu ngay nh√©!',NULL,'√Åo n·ªØ','ao-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',NULL,0),(_binary '$\\≤\“\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn l√† m·ªôt trong nh·ªØng trang ph·ª•c kh√¥ng th·ªÉ thi·∫øu c·ªßa ph√°i ƒë·∫πp. Qu·∫ßn n·ªØ ƒë∆∞·ª£c chia l√†m nhi·ªÅu lo·∫°i nh∆∞ qu·∫ßn jeans, qu·∫ßn n·ªâ, qu·∫ßn kaki, qu·∫ßn th·ªÉ thao... T√πy thu·ªôc v√†o s·ªü th√≠ch hay m·ª•c ƒë√≠ch c·ªßa b·∫°n m√† b·∫°n c√≥ th·ªÉ l·ª±a ch·ªçn chi·∫øc qu·∫ßn ph√π h·ª£p nh·∫•t v·ªõi m√¨nh. H√£y c√πng TRENDISTA ƒëi·ªÉm l·∫°i nh·ªØng lo·∫°i qu·∫ßn n·ªïi b·∫≠t',NULL,'Qu·∫ßn n·ªØ','quan-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',NULL,1),(_binary '$\\≥\‡\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Ph√°i ƒë·∫πp lu√¥n mong mu·ªën b·∫£n th√¢n ƒë∆∞·ª£c tr∆∞ng di·ªán v√† xinh ƒë·∫πp h∆°n m·ªói ng√†y kh√¥ng ch·ªâ khi ra ngo√†i m√† ngay c·∫£ ·ªü nh√†. Nhu c·∫ßu ƒë∆∞·ª£c m·∫∑c nh·ªØng b·ªô ƒë·ªì n·ªØ h·ª£p m·ªët v√† tho·∫£i m√°i ch√≠nh l√† nhu c·∫ßu ch√≠nh ƒë√°ng.\n\n\n\nC√°c ch·ªã em khi m·∫∑c ƒë·ªì b·ªô s·∫Ω ho√†n to√†n ti·∫øt ki·ªám ƒë∆∞·ª£c t·ªëi ƒëa th·ªùi gian b·ªüi ch√∫ng kh√¥ng c·∫ßn ph·∫£i k·∫øt',NULL,'ƒê·ªì b·ªô n·ªØ','do-bo-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',NULL,2),(_binary '$\\¥Ä\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Th·ªùi trang ƒë·ªì m·∫∑c trong n·ªØ TRENDISTA v·ªõi ki·ªÉu d√°ng g·ª£i c·∫£m, ch·∫•t li·ªáu cao c·∫•p, nhi·ªÅu ∆∞u ƒë√£i h·∫•p d·∫´n ƒëang ch·ªù b·∫°n kh√°m ph√°. Mua ƒë·ªì n·ªôi y ch∆∞a bao gi·ªù l√† ƒë·ªÖ d√†ng ƒë·∫øn v·∫≠y.\n\nC√πng ƒëi·ªÉm danh m·ªôt s·ªë ki·ªÉu m·∫´u ƒë·ªì m·∫∑c trong cho n·ªØ m√† b·∫•t k·ªÉ n√†ng n√†o c≈©ng c·∫ßn nh√©.\n\n1. ƒê·ªì m·∫∑c trong cho n·ªØ kh√¥ng th·ªÉ thi·∫øu - Qu·∫ßn l√≥t',NULL,'ƒê·ªì m·∫∑c trong n·ªØ','do-mac-trong-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',NULL,3),(_binary '$\\µ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì th·ªÉ thao n·ªØ','do-the-thao-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',NULL,4),(_binary '$\\µé\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·∫ßm v√† ch√¢n v√°y n·ªØ','dam-va-chan-vay-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',NULL,5),(_binary '$\\∂\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','T·∫ßm quan tr·ªçng c·ªßa ph·ª• ki·ªán ƒë·ªëi v·ªõi th·ªùi trang n·ªØ\n\nPh·ª• ki·ªán th·ªùi trang n·ªØ kh√¥ng ch·ªâ l√† nh·ªØng m√≥n ƒë·ªì ƒëi k√®m, d√πng th√™m ƒë·ªÉ ƒë·ª±ng ƒë·ªì m√† ƒë√≥ c√≤n l√† nh·ªØng m√≥n ƒë·ªì gi√∫p n√¢ng t·∫ßm gu ƒÉn m·∫∑c c·ªßa b·∫°n.\n\nM·ªôt b·ªô trang ph·ª•c tr√¥ng r·∫•t b√¨nh th∆∞·ªùng nh∆∞ng n·∫øu b·∫°n bi·∫øt mix th√™m c√°c ph·ª• ki·ªán ƒëi k√®m nh∆∞ t√∫i x√°ch, hoa tai',NULL,'Ph·ª• ki·ªán n·ªØ','phu-kien-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',NULL,6),(_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-02-10 09:19:21.664000','√Åo nam Trendista ch·∫•t l∆∞·ª£ng cao, m·∫´u m√£ ƒëa d·∫°ng, th·ªùi trang v√† phong c√°ch.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739153959/CATEGORIES/CATEGORIES_DEV%20NGUYEN.png','√Åo nam','ao-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',NULL,0),(_binary '$\\∑\0\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'Qu·∫ßn nam','quan-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',NULL,1),(_binary '$\\∑n\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì b·ªô nam','do-bo-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',NULL,2),(_binary '$\\∑\‹\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì m·∫∑c trong nam','do-mac-trong-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',NULL,3),(_binary '$\\∏J\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì th·ªÉ thao nam','do-the-thao-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',NULL,4),(_binary '$\\∏∏\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'Ph·ª• ki·ªán nam','phu-kien-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',NULL,5),(_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo tr·∫ª em TRENDISTA - C√°c m·∫´u √°o tr·∫ª em ƒë·∫πp, cao c·∫•p nh·∫•t. C√°c m·∫´u √°o thun, √°o ph√¥ng cho b√©, √°o polo tr·∫ª em ch·∫•t t·ªët.',NULL,'√Åo Tr·∫ª em','ao-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',NULL,0),(_binary '$\\πû\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Ng√†y nay s·ª± ƒëa d·∫°ng kh√¥ng c√≤n gi·ªõi h·∫°n trong c√°c s·∫£n ph·∫©m th·ªùi trang ng∆∞·ªùi l·ªõn m√† th·ªùi trang tr·∫ª em c≈©ng v·∫≠y. Qu·∫ßn √°o tr·∫ª em ƒë·∫∑c bi·ªát qu·∫ßn tr·∫ª em c≈©ng ng√†y c√†ng ƒë∆∞·ª£c ch√∫ tr·ªçng trong thi·∫øt k·∫ø ki·ªÉu d√°ng ƒëa d·∫°ng, tinh t·∫ø v·ªõi ch·∫•t li·ªáu,... cho ƒë·∫øn m√†u s·∫Øc cho c√°c b√©.',NULL,'Qu·∫ßn Tr·∫ª em','quan-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',NULL,1),(_binary '$\\∫ \”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','ƒê·ªì b·ªô tr·∫ª em hi·ªán nay r·∫•t phong ph√∫,¬†ƒëa d·∫°ng, b·∫Øt m·∫Øt v√† t·∫°o ƒë∆∞·ª£c s·ª± h√†o h·ª©ng, t√≤ m√≤ cho¬†c√°c b√© ƒëang trong ƒë·ªô tu·ªïi¬†ph√°t tri·ªÉn.\n\nH√£y c√πng TRENDISTA t√¨m hi·ªÉu v·ªÅ b·ªô ƒë·ªì¬†tr·∫ª em, c√°c l·ª±a ch·ªçn ƒë·ªì b·ªô tr·∫ª em cho b√© theo t·ª´ng ƒë·ªô tu·ªïi, l∆∞u √Ω khi ch·ªçn ƒë·ªì b·ªô cho tr·∫ª em c≈©ng nh∆∞ ƒëi·ªÉm qua m·ªôt v√†i b·ªô ƒë·ªì tr·∫ª em b·∫Øt trend',NULL,'ƒê·ªì b·ªô Tr·∫ª em','do-bo-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',NULL,2),(_binary '$\\∫¨\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì m·∫∑c trong Tr·∫ª em','do-mac-trong-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',NULL,3),(_binary '$\\ª\Z\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì th·ªÉ thao Tr·∫ª em','do-the-thao-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',NULL,4),(_binary '$\\ªà\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','N√≥i ƒë·∫øn th·ªùi trang tr·∫ª em kh√¥ng th·ªÉ kh√¥ng k·ªÉ ƒë·∫øn nh·ªØng m·∫´u v√°y tr·∫ª em ƒë·∫πp. Nh·ªØng chi·∫øc v√°y ƒë·∫ßm tr·∫ª em cao c·∫•p kh√¥ng ch·ªâ ƒë∆∞·ª£c c√°c b√© g√°i y√™u th√≠ch v√† l·ª±a ch·ªçn khi ƒëi mua s·∫Øm c√πng b·ªë m·∫π. TRENDISTA m√°ch b·∫°n nh·ªØng ki·ªÉu v√°y c∆° b·∫£n v√† nh·ªØng tip h∆∞·ªõng d·∫´n mix trang ph·ª•c ƒë·ªÉ m·∫π tr·ªü th√†nh chuy√™n gia',NULL,'ƒê·∫ßm v√† ch√¢n v√°y b√© g√°i','dam-va-chan-vay-be-gai',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',NULL,5),(_binary '$\\º\n\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Ph·ª• ki·ªán th·ªùi trang tr·∫ª em TRENDISTA nh∆∞ m≈©/ n√≥n cho b√©, t·∫•t, kh·∫©u trang tr·∫ª em, gi√†y d√©p tr·∫ª em...ƒëa d·∫°ng m·∫´u m√£, m√†u s·∫Øc ng·ªô nghƒ©nh ƒë√°ng y√™u¬†t·∫°o phong c√°ch m·ªõi m·∫ª cho b√©. Mua ngay t·∫°i TRENDISTA.VN ƒë·ªÉ nh·∫≠n h√†ng ng√†n ∆∞u ƒë√£i h·∫•p d·∫´n.',NULL,'Ph·ª• ki·ªán Tr·∫ª em','phu-kien-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',NULL,6),(_binary '$^~\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo vest n·ªØ','ao-vest-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^ \¬\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo kho√°c n·ªØ¬†l√† trang ph·ª•c r·∫•t c·∫ßn thi·∫øt trong nh·ªØng ng√†y ƒê√¥ng l·∫°nh gi√°. H√£y c√πng TRENDISTA kh√°m ph√° ngay m·ªôt s·ªë m·∫´u √°o kho√°c n·ªØ , √°o gi√≥ n·ªØ ƒë·∫πp ƒëang ‚Äúƒë∆∞·ª£c l√≤ng‚Äù t·∫•t c·∫£ c√°c kh√°ch h√†ng n·ªØ nh√©!\n\n\n\n√Åo kho√°c n·ªØ form r·ªông, √°o kho√°c n·ªØ h√†n qu·ªëc l√† m·ªôt trong nh·ªØng item kh√¥ng th·ªÉ thi·∫øu',NULL,'√Åo kho√°c n·ªØ','ao-khoac-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^!Ä\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','M√πa ƒë√¥ng ƒë√£ ƒë·∫øn, v√† khi nhi·ªát ƒë·ªô gi·∫£m, l·ª±a ch·ªçn trang ph·ª•c c·ªßa ch√∫ng ta tr·ªü n√™n quan tr·ªçng ƒë·ªÉ gi·ªØ ·∫•m v√† phong c√°ch. Trong th·∫ø gi·ªõi th·ªùi trang ƒë√¥ng, √Åo Phao N·ªØ¬†n·ªïi b·∫≠t nh∆∞ m·ªôt trang ph·ª•c ƒëa d·∫°ng v√† quan tr·ªçng.',NULL,'√Åo phao n·ªØ','ao-phao-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo gi√≥ n·ªØ','ao-gio-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^\"z\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo ch·ªëng n·∫Øng n·ªØ TRENDISTA ƒë·∫∑t ch·ªâ s·ªë UPF 50+¬†ngƒÉn chƒÉn tia¬†UVA v√† UVB¬†l√™n t·ªõi 98%, ch·∫•t li·ªáu l·∫°i co gi√£n, m√°t m·∫ª khi m·∫∑c l√† l·ª±a ch·ªçn ho√†n h·∫£o ƒë·ªÉ ch·ªã em b·∫£o v·ªá l√†n da c·ªßa m√¨nh trong nh·ªØng ng√†y h√® n·∫Øng g·∫Øt.\n\nT·∫ßm quan tr·ªçng c·ªßa √°o ch·ªëng n·∫Øng n·ªØ\n\n√Åo ch·ªëng n·∫Øng n·ªØ l√† m·ªôt trong nh·ªØng v·∫≠t d·ª•ng kh√¥ng th·ªÉ thi·∫øu.',NULL,'√Åo ch·ªëng n·∫Øng n·ªØ','ao-chong-nang-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',4),(_binary '$^\"¸\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Mua √°o sweater n·ªØ form r·ªông online nhanh ch√≥ng v√† thu·∫≠n ti·ªán Freeship to√†n qu·ªëc c√πng ch√≠nh s√°ch ƒë·ªïi tr·∫£ d·ªÖ d√†ng. S·ªü h·ªØu ngay √°o n·ªâ n·ªØ c√πng nhi·ªÅu ∆∞u ƒë√£i t·∫°i ƒë√¢y.\n\n1. √Åo sweater n·ªØ local brand\n\nV·ªõi ƒë·ªô ph·ªï bi·∫øn r·ªông r√£i c·ªßa sweater th√¨ t·ªõi th·ªùi ƒëi·ªÉm hi·ªán t·∫°i, sweaters local brand ƒëang tr·ªü n√™n th·ªãnh h√†nh',NULL,'√Åo hoodie - √Åo n·ªâ n·ªØ','ao-hoodie-ao-ni-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',5),(_binary '$^#~\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','M·ªói khi ƒë√¥ng ƒë·∫øn, √°o len n·ªØ l·∫°i l√†m khu·∫•y ƒë·∫£o gi·ªõi m·ªô ƒëi·ªáu v·ªõi ki·ªÉu thi·∫øt k·∫ø v√† ho·∫° ti·∫øt ƒëa d·∫°ng c·ªßa m√¨nh. Trong ph·∫ßn n·ªôi dung d∆∞·ªõi ƒë√¢y, th·ªùi trang TRENDISTA s·∫Ω b·∫≠t m√≠ v·ªõi c√°c b·∫°n nh·ªØng¬†m·∫´u √°o len n·ªØ ƒë·∫πp v√† trendy nh·∫•t hi·ªán nay.',NULL,'√Åo len n·ªØ','ao-len-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',6),(_binary '$^#\ˆ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Mua √°o thun n·ªØ ƒë·∫πp gi√° r·∫ª, √°p ph√¥ng in h√¨nh nƒÉng ƒë·ªông, thi·∫øt k·∫ø tr·∫ª trung, ch·∫•t li·ªáu cao c·∫•p co gi√£n 4 chi·ªÅu, tho·∫£i m√°i trong t·ª´ng c·ª≠ ƒë·ªông.',NULL,'√Åo thun n·ªØ','ao-thun-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',7),(_binary '$^$d\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','BST 100 m·∫´u √°o s∆° mi n·ªØ ƒë·∫πp, c√°c ki·ªÉu √°o s∆° mi ƒë·∫πp, tr·∫ª trung ch√≠nh h√£ng TRENDISTA. Mua √°o s∆° mi n·ªØ cao c·∫•p, gi√° t·ªët 2024 t·∫°i ƒë√¢y.',NULL,'√Åo s∆° mi n·ªØ','ao-so-mi-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',8),(_binary '$^$\‹\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo polo n·ªØ ch√≠nh h√£ng TRENDISTA - BST √°o polo n·ªØ cao c·∫•p, tr·∫ª trung, √°o thun n·ªØ c√≥ c·ªï ƒë·∫πp, gi√° t·ªët nh·∫•t 2024. ∆Øu ƒë√£i l·ªõn, freeship to√†n qu·ªëc. Mua ngay!',NULL,'√Åo polo n·ªØ','ao-polo-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∞\”\ﬂÔïß\…\Ã\‰æ',9),(_binary '$^%J\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Mua qu·∫ßn th·ªÉ thao cho n·ªØ nƒÉng ƒë·ªông, th·ªùi trang ch√≠nh h√£ng, m·∫∑c l√† m√°t, gi√°  t·ªët, ship to√†n qu·ªëc, mi·ªÖn ph√≠ ƒë·ªïi tr·∫£ 15 ng√†y ƒë·∫ßu.',NULL,'Qu·∫ßn d√†i n·ªØ','quan-dai-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≤\“\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^%∏\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','M·ªôt trong nh·ªØng lo√†i qu·∫ßn ƒë∆∞·ª£c ∆∞a chu·ªông nh·∫•t hi·ªán nay ch·∫Øc ch·∫Øc kh√¥ng th·ªÉ kh√¥ng nh·∫Øc ƒë·∫øn qu·∫ßn n·ªâ n·ªØ. Qu·∫ßn n·ªâ n·ªØ mang d√°ng th·ªÉ thao l·∫°i kh·ªèe kho·∫Øn ph√π h·ª£p v·ªõi phong c√°ch street-style. Chi·∫øc qu·∫ßn n√†y¬†c√≥ th·ªÉ m·∫∑c trong r·∫•t nhi·ªÅu ho√†n c·∫£nh kh√°c nhau nh∆∞ ƒëi ch∆°i, ƒëi h·ªçc, ƒëi l√†m ·ªü nh·ªØng n∆°i kh√¥ng b·ªã g√≤ b√≥',NULL,'Qu·∫ßn n·ªâ n·ªØ','quan-ni-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≤\“\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^&:\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Mua qu·∫ßn kaki n·ªØ, qu·∫ßn khaki ch√≠nh h√£ng gi√° t·ªët, ƒë·∫ßy ƒë·ªß ki·ªÉu d√°ng. Mua qu·∫ßn kaki n·ªØ t·∫°i TRENDISTA v·ªõi ∆∞u ƒë√£i, giao h√†ng mi·ªÖn ph√≠, ƒë·ªïi tr·∫£ d·ªÖ d√†ng.',NULL,'Qu·∫ßn kaki n·ªØ','quan-kaki-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≤\“\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^&®\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn short n·ªØ l√† s·ª± l·ª±a ch·ªçn ƒëa phong c√°ch, ƒëa c√° t√≠nh cho m·ªçi c√¥ n√†ng. ƒê∆∞·ª£c ∆∞a chu·ªông trong m√πa h√®, m√πa thu v·ªõi ∆∞u ƒëi·ªÉm t·∫°o ƒë∆∞·ª£c s·ª± tho·∫£i m√°i, nƒÉng ƒë·ªông, v√† c·ª±c th·ªùi trang.',NULL,'Qu·∫ßn short n·ªØ','quan-short-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≤\“\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^\' \”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'Qu·∫ßn jeans n·ªØ','quan-jeans-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≤\“\”\ﬂÔïß\…\Ã\‰æ',4),(_binary '$^\'Ñ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn T√¢y n·ªØ l√† m·∫´u qu·∫ßn ‚Äúhuy·ªÅn tho·∫°i‚Äù, b·∫°n c√≥ th·ªÉ h√¥ bi·∫øn t·ª´ c√¥ n√†ng c√¥ng s·ªü trang nh√£ hay c√° t√≠nh d·∫°o ph·ªë theo nhi·ªÅu style kh√°c nhau. Thi·∫øt k·∫ø h∆∞·ªõng ƒë·∫øn s·ª± ƒë∆°n gi·∫£n, ti·ªán l·ª£i, form chu·∫©n, ph√π h·ª£p trong m·ªçi ho√†n c·∫£nh.\n\n\n\nQu·∫ßn √Çu n·ªØ l√† m·ªôt trong nh·ªØng items kh√¥ng c√≤n xa l·∫° v·ªõi c√°c qu√Ω c√¥. S·ª± tho·∫£i m√°',NULL,'Qu·∫ßn √¢u n·ªØ','quan-au-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≤\“\”\ﬂÔïß\…\Ã\‰æ',5),(_binary '$^(\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì b·ªô d√†i tay n·ªØ','do-bo-dai-tay-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≥\‡\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^(j\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì b·ªô ng·∫Øn tay n·ªØ','do-bo-ngan-tay-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\≥\‡\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^(\ÿ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo bra n·ªØ','ao-bra-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\¥Ä\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^)F\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'Qu·∫ßn l√≥t n·ªØ','quan-lot-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\¥Ä\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^)¥\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Mua √°o hai d√¢y, √°o ba l·ªó n·ªØ gi√° t·ªët, thi·∫øt k·∫ø tr·∫ª trung t√¥n d√°ng, thu h√∫t m·ªçi √°nh nh√¨n. Mua ngay ƒë·ªÉ nh·∫≠n ∆∞u ƒë√£i gi·∫£m 50%',NULL,'√Åo ba l·ªó - √Åo hai d√¢y n·ªØ','ao-ba-lo-ao-hai-day-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\¥Ä\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^*\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo gi·ªØ nhi·ªát n·ªØ ƒë∆∞·ª£c xem nh∆∞ l√† m·ªôt m√≥n ƒë·ªì ‚Äúb·∫£o b·ªëi‚Äù v√¥ c√πng c·∫ßn thi·∫øt d√†nh cho c√°c c√¥ n√†ng trong m√πa ƒë√¥ng l·∫°nh gi√°. Kh√¥ng nh·ªØng gi√∫p gi·ªØ ·∫•m c∆° th·ªÉ hi·ªáu qu·∫£ c√≤n r·∫•t d·ªÖ ph·ªëi ƒë·ªì v·ªõi nh·ªØng trang ph·ª•c kh√°c nhau m√† v·∫´n ƒë·∫£m b·∫£o ƒë∆∞·ª£c th·ªùi trang c≈©ng nh∆∞ c√° t√≠nh c·ªßa ng∆∞·ªùi m·∫∑c.',NULL,'√Åo gi·ªØ nhi·ªát n·ªØ','ao-giu-nhiet-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\¥Ä\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^*ö\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'Qu·∫ßn th·ªÉ thao n·ªØ','quan-the-thao-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\µ\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^+\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo polo th·ªÉ thao n·ªØ','ao-polo-the-thao-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\µ\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^+v\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo thun th·ªÉ thao n·ªØ','ao-thun-the-thao-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\µ\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^+\⁄\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'B·ªô th·ªÉ thao n·ªØ','bo-the-thao-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\µ\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^,>\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','ƒê·∫ßm n·ªØ - v√°y li·ªÅn th√¢n l√† item th·ªùi trang ƒëem l·∫°i n√©t d·ªãu d√†ng, n·ªØ t√≠nh cho ph√°i ƒë·∫πp. Trang ph·ª•c n√†y th∆∞·ªùng ƒë∆∞·ª£c thi·∫øt k·∫ø v·ªõi ch·∫•t v·∫£i m·ªèng, m√°t gi√∫p ch·ªã em tho·∫£i m√°i v·∫≠n ƒë·ªông. V·∫≠y, b·∫°n ƒë√£ bi·∫øt m·∫´u v√°y n·ªØ, ƒë·∫ßm body n·ªØ¬†n√†o¬†ƒëang th·ªãnh h√†nh hi·ªán nay ch∆∞a? N·∫øu ch∆∞a th√¨ ƒë·ª´ng b·ªè qua ph·∫ßn g·ª£i √Ω d∆∞·ªõi ƒë√¢y nha',NULL,'ƒê·∫ßm n·ªØ','dam-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\µé\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^,¿\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Ch√¢n v√°y n·ªØ, ch√¢n v√°y ch·ªØ A, ch√¢n v√°y c√¥ng s·ª≠ v·ªõi nhi·ªÅu thi·∫øt k·∫ø tinh t·∫ø ƒë·∫£m b·∫£o s·∫Ω l√†m b·∫°n h√†i l√≤ng. Xem ngay ƒë·ªÉ mua Ch√¢n v√°y TRENDISTA ch√≠nh h√£ng',NULL,'Ch√¢n v√°y n·ªØ','chan-vay-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\µé\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^-.\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'T·∫•t n·ªØ','tat-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^-ú\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'T√∫i x√°ch n·ªØ','tui-xach-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^.(\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','B·ªô s∆∞u t·∫≠p gi√†y c·ªßa c√°c c√¥ g√°i c√≥ gu ch·∫Øc ch·∫Øn s·∫Ω kh√¥ng th·ªÉ thi·∫øu nh·ªØng ƒë√¥i gi√†y cao g√≥t. Ch·ªã em thanh l·ªãch v·ªõi m·ªôt outfit ho√†n h·∫£o b∆∞·ªõc ƒëi t·ª± tin tr√™n m·ªôt ƒë√¥i gi√†y cao g√≥t s·∫Ω thu h√∫t ƒë∆∞·ª£c √°nh nh√¨n c·ªßa r·∫•t nhi·ªÅu ng∆∞·ªùi. V·∫≠y, nh·ªØng ƒë√¥i gi√†y cao g√≥t n√†o ƒëang ƒë∆∞·ª£c ∆∞a chu·ªông nh·∫•t hi·ªán nay? C√πng TRNEDISTA kh√°m ph√°',NULL,'Gi√†y n·ªØ','giay-nu',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^.™\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Thi√™n ƒë∆∞·ªùng ph·ª• ki·ªán th·ªùi trang n·ªØ cao c·∫•p, thi·∫øt k·∫ø ƒë·∫πp, kh√¥ng th·ªÉ thi·∫øu c·ªßa c√¥ n√†ng hi·ªán ƒë·∫°i ‚Äì ph·ª• ki·ªán th·ªùi trang n·ªØ ƒë∆°n gi·∫£n m√† kh√¥ng k√©m ph·∫ßn tinh t·∫ø.',NULL,'Ph·ª• ki·ªán n·ªØ kh√°c','phu-kien-nu-khac',_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^8˙\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo vest nam l√† m·ªôt s·∫£n ph·∫©m th·ªùi trang t·∫°o ƒë∆∞·ª£c r·∫•t nhi·ªÅu s·ª± sang tr·ªçng, kh√°c bi·ªát so v·ªõi nhi·ªÅu m√≥n ƒë·ªì th·ªùi trang kh√°c.\n\n\n\nH√£y c√πng TRENDISTA t√¨m hi·ªÉu √°o vest nam l√† g√¨, c√°ch ƒë·ªÉ ph√¢n bi·ªát √°o vest nam c≈©ng nh∆∞ tham kh·∫£o ƒë∆∞·ª£c m·ªôt v√†i m·∫´u √°o vest nam ph√π h·ª£p nh·∫•t cho b·∫£n th√¢n qua b√†i vi·∫øt d∆∞·ªõi ƒë√¢y.',NULL,'√Åo vest nam','ao-vest-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^9\‡\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo kho√°c nam TRENDISTA ch√≠nh h√£ng gi√° r·∫ª, ch·∫•t l∆∞·ª£ng cao, ch·ªëng n∆∞·ªõc c·∫£n gi√≥, mua h√†ng ch√≠nh h√£ng t·∫°i website, giao h√†ng nhanh ch√≥ng.',NULL,'√Åo kho√°c nam','ao-khoac-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^:b\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo phao nam','ao-phao-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^:\–\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo gi√≥ nam','ao-gio-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^;>\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Nh·ªØng ng√†y thu ƒë√¥ng g·∫ßn t·ªõi, ƒë·ªÉ kh·ªèi nh√†m ch√°n v·ªõi c√°c ki·ªÉu √°o nh∆∞ √°o len, √°o gi·ªØ nhi·ªát kh√¥ng c√≥ ch√∫t ph√° c√°ch n√†o th√¨ √°o hoodie nam l√† l·ª±a ch·ªçn s√°ng su·ªët. V·∫≠y √°o hoodie nam¬†ƒë∆∞·ª£c m·∫∑c nh∆∞ th·∫ø n√†o v√† mua √°o hoodie nam ·ªü ƒë√¢u ch·∫•t l∆∞·ª£ng nh·∫•t?',NULL,'√Åo hoodie - √Åo n·ªâ nam','ao-hoodie-ao-ni-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',4),(_binary '$^;¿\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo len nam c√≥ v√¥ c√πng nh·ªØng ki·ªÉu c√°ch kh√°c nhau t·ª´: √°o len c·ªï cao nam, √°o len nam c·ªï l·ªç, √°o len nam‚Ä¶ Nh·ªØng ki·ªÉu √°o n√†y th∆∞·ªùng mang form r·ªông, ch·∫•t m·ªÅm m·ªãn v√† b·∫Øt k·ªãp xu h∆∞·ªõng th·ªùi trang nam. Phong c√°ch TRENDISTA gi√∫p ch√†ng t√∫t t√°t¬†l·∫°i v·∫ª ƒë·∫πp trai ƒë√≥n m√πa ƒë√¥ng ·∫•m √°p.',NULL,'√Åo len nam','ao-len-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',5),(_binary '$^<8\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','BST m·∫´u √°o thun nam ƒë·∫πp, √°o ph√¥ng nam h√†ng hi·ªáu cao c·∫•p YODY m·ªõi nh·∫•t 2024. Mua √°o thun nam TRENDISTA v·ªõi ∆∞u ƒë√£i h·∫•p d·∫´n, freeship to√†n qu·ªëc.',NULL,'√Åo thun nam','ao-thun-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',6),(_binary '$^<¶\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','BST √°o s∆° mi nam ƒë·∫πp, cao c·∫•p, ch√≠nh h√£ng TRENDISTA, √°o s∆° mi c√¥ng s·ªü nam hi·ªán ƒë·∫°i, tr·∫ª trung, ƒëa d·∫°ng m·∫´u m√£. Mua √°o s∆° mi t·∫°i TRENDISTA ngay!',NULL,'√Åo s∆° mi nam','ao-so-mi-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',7),(_binary '$^=(\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','BST √°o polo nam ch√≠nh h√£ng, ƒëa d·∫°ng ki·ªÉu d√°ng t·∫°i TRENDISTA. Ch·∫•t li·ªáu cao c·∫•p, tho√°ng m√°t, gi√° t·ªët nh·∫•t. Mua online, giao h√†ng nhanh to√†n qu·ªëc!',NULL,'√Åo polo nam','ao-polo-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∂~\”\ﬂÔïß\…\Ã\‰æ',8),(_binary '$^=ñ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'Qu·∫ßn d√†i nam','quan-dai-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\0\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^>\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','V·∫ª thanh l·ªãch, tr·∫ª trung v√†¬†nam t√≠nh l√† t·∫•t c·∫£ nh·ªØng g√¨ b·∫°n nh·∫≠n ƒë∆∞·ª£c khi di·ªán qu·∫ßn kaki nam. D∆∞·ªõi ƒë√¢y, th·ªùi trang TRENDISTA s·∫Ω b·∫≠t m√≠ v·ªõi m·ªçi ng∆∞·ªùi nh·ªØng m·∫´u qu·∫ßn th·ªãnh h√†nh nh·∫•t v√† c√°ch ph·ªëi ƒë·ªì v·ªõi qu·∫ßn kaki nam chu·∫©n fashionista.',NULL,'Qu·∫ßn kaki nam','quan-kaki-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\0\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^>Ü\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','H√†ng ng√†n qu·∫ßn short nam tr·∫ª trung, qu·∫ßn sooc nam th·ªÉ thao gi√° r·∫ª. M·∫´u m√£ ƒëa d·∫°ng, ki·ªÉu d√°ng nƒÉng ƒë·ªông, mua qu·∫ßn ƒë√πi nam v·ªõi ∆∞u ƒë√£i kh·ªßng l√™n ƒë·∫øn 50%!',NULL,'Qu·∫ßn short nam','quan-short-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\0\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^>\Ù\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','BST qu·∫ßn jeans nam ƒë·∫πp, qu·∫ßn b√≤ nam ch√≠nh h√£ng TRENDISTA, m·∫´u m·ªõi nh·∫•t 2024. Mua online ngay ƒë·ªÉ nh·∫≠n ∆∞u ƒë√£i gi·∫£m gi√° l√™n t·ªõi 50%',NULL,'Qu·∫ßn jeans nam','quan-jeans-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\0\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^?l\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn √¢u nam c√¥ng s·ªü ƒë·∫πp, qu·∫ßn t√¢y nam cao c·∫•p ch√≠nh h√£ng TRENDISTA ƒëa d·∫°ng m·∫´u m√£, ki·ªÉu d√°ng. Mua ngay qu·∫ßn √¢u TRENDISTA v·ªõi ∆∞u ƒë√£i c·ª±c s·ªëc!',NULL,'Qu·∫ßn √¢u nam','quan-au-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\0\”\ﬂÔïß\…\Ã\‰æ',4),(_binary '$^?\⁄\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì b·ªô d√†i tay nam','do-bo-dai-tay-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑n\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^@H\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì b·ªô ng·∫Øn tay nam','do-bo-ngan-tay-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑n\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^@∂\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn l√≥t nam l√† m√≥n ƒë·ªì ƒë∆∞·ª£c m·∫∑c b√™n trong nh·∫±m b·∫£o v·ªá ‚Äúc·∫≠u nh·ªè‚Äù. ƒê√¢y ch√≠nh l√† l√Ω do c∆° b·∫£n nh·∫•t khi m·∫∑c ƒë·ªì l√≥t khi·∫øn ph√°i m·∫°nh lu√¥n t·ª± tin v√† tho·∫£i m√°i m·ªói khi s·ª≠ d·ª•ng.\n\nC√°c lo·∫°i qu·∫ßn l√≥t nam¬†ƒë∆∞·ª£c s·∫£n xu·∫•t t·ª´ nhi·ªÅu ch·∫•t li·ªáu v·∫£i kh√°c nhau, ch·ªß y·∫øu l√† nh·ªØng ch·∫•t li·ªáu t·ª± nhi√™n, kh√¥ng g√¢y k√≠ch ·ª©ng da',NULL,'Qu·∫ßn l√≥t nam','quan-lot-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\‹\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^A8\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Mua √°o ba l·ªó nam ch√≠nh h√£ng, √°o thun 3 l·ªó ch·∫•t li·ªáu cao c·∫•p, co gi√£n 4 chi·ªÅu, t·ª± tin trong c√°c ho·∫°t ƒë·ªông th·ªÉ thao nh∆∞ gym, b√≥ng r·ªï, b√≥ng ƒë√°, ch·∫°y b·ªô,..',NULL,'√Åo ba l·ªó nam','ao-ba-lo-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\‹\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^A∫\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo gi·ªØ nhi·ªát nam l√† trang ph·ª•c s·ªü h·ªØu nhi·ªÅu ∆∞u ƒëi·ªÉm v∆∞·ª£t tr·ªôi ƒë∆∞·ª£c thi·∫øt k·∫ø m·ªèng, nh·∫π gi√∫p gi·ªØ ·∫•m ƒë∆∞·ª£c s·∫£n xu·∫•t v·ªõi c·∫•u t·∫°o ƒë·∫∑c bi·ªát. V·∫≠y √°o gi·ªØ nhi·ªát nam c√≥ thi·∫øt k·∫ø nh∆∞ th·∫ø n√†o? Mua √°o gi·ªØ nhi·ªát nam ·ªü ƒë√¢u? C√¥ng d·ª•ng c·ªßa √°o gi·ªØ nhi·ªát nam l√† g√¨? C√πng t√¨m hi·ªÉu ngay v·ªõi TRENDISTA nh√©!',NULL,'√Åo gi·ªØ nhi·ªát nam','ao-giu-nhiet-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∑\‹\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^B2\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','M·ªôt trong nh·ªØng lo·∫°i qu·∫ßn kh√¥ng th·ªÉ thi·∫øu d√†nh cho ph√°i m·∫°nh l√† qu·∫ßn th·ªÉ thao nam. Qu·∫ßn th·ªÉ thao nam kh√¥ng ch·ªâ ƒë√°p ·ª©ng phong c√°ch m√† c√≤n gi√∫p b·∫°n c·∫£m th·∫•y tho·∫£i m√°i khi tham gia c√°c ho·∫°t ƒë·ªông. Ng√†y nay c√≥ nhi·ªÅu lo·∫°i qu·∫ßn th·ªÉ thao ƒë∆∞·ª£c ph√°t tri·ªÉn cho b·∫°n c√≥ th√™m nhi·ªÅu s·ª± l·ª±a ch·ªçn nh∆∞:¬†qu·∫ßn d√†i, qu·∫ßn',NULL,'Qu·∫ßn th·ªÉ thao nam','quan-the-thao-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏J\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^B¥\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo polo th·ªÉ thao nam l√† m·ªôt trong nh·ªØng m·∫´u √°o ƒë∆∞·ª£c s·ª≠ d·ª•ng ph·ªï bi·∫øn, r·ªông r√£i v√† ƒë·∫°t ƒë∆∞·ª£c c√°nh m√†y r√¢u c·ª±c k·ª≥ y√™u th√≠ch.\n\nH√£y c√πng TRENDISTA t√¨m hi·ªÉu √°o polo th·ªÉ thao cho nam l√† √°o g√¨, c√°c m·∫´u √°o polo th·ªÉ thao nam hot nh·∫•t nƒÉm 2024 c√πng c√°c ch·∫•t li·ªáu l√†m n√™n √°o polo th·ªÉ thao¬†qua b√†i vi·∫øt d∆∞·ªõi ƒë√¢y.',NULL,'√Åo polo th·ªÉ thao nam','ao-polo-the-thao-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏J\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^C,\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo thun th·ªÉ thao, √°o¬†ph√¥ng th·ªÉ thao nam th∆∞·ªùng ƒë∆∞·ª£c c√°nh m√†y r√¢u¬†s·ª≠ d·ª•ng trong nh·ªØng l√∫c t·∫≠p luy·ªán th·ªÉ d·ª•c th·ªÉ thao hay c√°c ho·∫°t ƒë·ªông¬†ngo√†i tr·ªùi c·∫ßn v·∫≠n ƒë·ªông m·∫°nh. Lo·∫°i √°o n√†y c√≥ c·∫•u t·∫°o v√† ch·∫•t li·ªáu ƒë·∫∑c bi·ªát gi√∫p cho vi·ªác ho·∫°t ƒë·ªông c·ªßa nh·ªØng ng∆∞·ªùi s·ª≠ d·ª•ng s·∫£n ph·∫©m n√†y c·∫£m th·∫•y tho·∫£i m√°i, m√°t l·∫°nh',NULL,'√Åo thun th·ªÉ thao nam','ao-thun-the-thao-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏J\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^K8\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','B·ªô ƒë·ªì th·ªÉ thao cho nam l√† m·ªôt trong nh·ªØng s·∫£n ph·∫©m th·ªùi trang ph·ª•c v·ª• hi·ªáu qu·∫£ cho ƒë·ªùi s·ªëng th∆∞·ªùng ng√†y, th·ªÉ d·ª•c th·ªÉ thao m·ªói ng√†y m√† v·∫´n r·∫•t th·ªùi trang, phong c√°ch.\n\n\n\nH√£y c√πng TRENDISTA t√¨m hi·ªÉu b·ªô ƒë·ªì th·ªÉ thao cho nam l√† g√¨, c√°c b·ªô ƒë·ªì th·ªÉ thao cho nam ƒëang hot nh·∫•t hi·ªán nay c√πng m·ªôt v√†i l∆∞u √Ω khi mua b·ªô th·ªÉ thao nam',NULL,'B·ªô th·ªÉ thao nam','bo-the-thao-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏J\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^L\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'T·∫•t nam','tat-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏∏\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^L™\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'T√∫i x√°ch nam','tui-xach-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏∏\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^M\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Mua m≈© l∆∞·ª°i trai nam, n√≥n nam ch√≠nh h√£ng gi√° t·ªët. Mua m≈© l∆∞·ª°i trai nam t·∫°i TRENDISTA v·ªõi ∆∞u ƒë√£i, giao h√†ng mi·ªÖn ph√≠, ƒë·ªïi tr·∫£ d·ªÖ d√†ng.',NULL,'M≈© nam','mu-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏∏\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^MÆ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Th·∫Øt l∆∞ng nam l√† m√≥n ph·ª• ki·ªán th·ªùi trang kh√¥ng th·ªÉ thi·∫øu cho ph√°i m·∫°nh ng√†y nay. ƒê√¢y l√† m√≥n ph·ª• ki·ªán d√πng ƒë·ªÉ k·∫øt h·ª£p v·ªõi qu·∫ßn √¢u, qu·∫ßn jeans, b·ªô vest l·ªãch l√£m,... ƒë·ªÉ tƒÉng s·ª± thanh l·ªãch v√† sang tr·ªçng c·ªßa ng∆∞·ªùi m·∫∑c. \n\nNh∆∞ng th·∫Øt l∆∞ng nam c√≥ nhi·ªÅu ki·ªÉu d√°ng v√† ch·∫•t li·ªáu, m·ªói ki·ªÉu d√°ng, ch·∫•t li·ªáu¬†l·∫°i c√≥',NULL,'Th·∫Øt l∆∞ng nam','that-lung-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏∏\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^N:\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Gi√†y nam l√† m√≥n ƒë·ªì th·ªùi trang¬†kh√¥ng l·∫° l·∫´m g√¨ v·ªõi ph√°i m·∫°nh n·ªØa, nh·∫•t l√† v√†o nƒÉm 2024 khi m√† d√©p c√≥ xu h∆∞·ªõng ƒëi xu·ªëng. M·ªói ki·ªÉu gi√†y nam l·∫°i c√≥ m·ª•c ƒë√≠ch s·ª≠ d·ª•ng kh√°c nhau n√™n ng∆∞·ªùi ƒëi s·∫Ω ph·∫£i ƒë∆∞a ra quy·∫øt ƒë·ªãnh h·ª£p l√≠ ƒë·ªÉ c√≥ b·ªô ƒë·ªì ƒë·∫πp nh·∫•t.\n\nC√≥ nhi·ªÅu ki·ªÉu gi√†y nam nh∆∞ gi√†y t√¢y, gi√†y da, gi√†y l∆∞·ªùi,...',NULL,'Gi√†y nam','giay-nam',_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∏∏\”\ﬂÔïß\…\Ã\‰æ',4),(_binary '$^N\∆\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo kho√°c tr·∫ª em th∆∞∆°ng hi·ªáu TRENDISTA lu√¥n ƒë√°p ·ª©ng ƒë∆∞·ª£c ch·∫•t l∆∞·ª£ng c≈©ng nh∆∞ ƒëa d·∫°ng v·ªÅ ch·ªßng lo·∫°i: √Åo phao tr·∫ª em, √°o gi√≥ tr·∫ª em, √°o ·∫•m,... gi√∫p c√°c b√© gi·ªØ ·∫•m hi·ªáu qu·∫£ trong th·ªùi ti·∫øt m√πa ƒë√¥ng kh·∫Øc nghi·ªát.',NULL,'√Åo kho√°c tr·∫ª em','ao-khoac-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^OR\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo phao tr·∫ª em','ao-phao-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^O\‘\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo gi√≥ tr·∫ª em','ao-gio-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^PL\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo n·ªâ tr·∫ª em¬†l√† m·ªôt m√≥n ƒë·ªì th·ªùi trang thi·∫øt y·∫øu gi√∫p b√© y√™u gi·ªØ ·∫•m trong nh·ªØng ng√†y th·ªùi ti·∫øt chuy·ªÉn l·∫°nh.¬†H√£y c√πng TRENDISTA t√¨m hi·ªÉu v·ªÅ m·ªôt v√†i m·∫´u √°o n·ªâ, √°o hoodie tr·∫ª em hot trend nh·∫•t hi·ªán nay v√† tham kh·∫£o th√™m m·ªôt v√†i c√°ch ph·ªëi ƒë·ªì v·ªõi √°o n·ªâ tr·∫ª em trong n·ªôi dung d∆∞·ªõi ƒë√¢y nh√©!',NULL,'√Åo hoodie - √Åo n·ªâ tr·∫ª em','ao-hoodie-ao-ni-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^P\‚\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','√Åo len tr·∫ª¬†em¬†lu√¥n l√† m·ªôt m√≥n ƒë·ªì th·ªùi trang thi·∫øt y·∫øu gi√∫p b√© y√™u gi·ªØ ·∫•m trong nh·ªØng ng√†y th·ªùi ti·∫øt chuy·ªÉn l·∫°nh.¬†¬†H√£y c√πng TRENDISTA t√¨m hi·ªÉu v·ªÅ m·ªôt v√†i m·∫´u¬†√°o len tr·∫ª em hot trend nh·∫•t hi·ªán nay, c≈©ng nh∆∞ c√°c l∆∞u √Ω trong kh√¢u l·ª±a ch·ªçn √°o len cho b√© v√† tham kh·∫£o th√™m m·ªôt v√†i c√°ch ph·ªëi ƒë·ªì v·ªõi¬†√°o len tr·∫ª em',NULL,'√Åo len tr·∫ª em','ao-len-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',4),(_binary '$^Qn\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','BST √°o thun tr·∫ª em, √°o ph√¥ng b√© trai, b√© g√°i cao c·∫•p, ch·∫•t li·ªáu tho√°ng m√°t. Mua √°o ph√¥ng tr·∫ª em cao c·∫•p TRENDISTA ƒë·ªÉ nh·∫≠n ∆∞u ƒë√£i h·∫•p d·∫´n!',NULL,'√Åo thun tr·∫ª em','ao-thun-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',5),(_binary '$^Q\‹\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','C·∫≠p nh·∫≠t BST √°o s∆° mi tr·∫ª em ƒë·∫πp, cao c·∫•p, √°o s∆° mi cho b√© ch√≠nh h√£ng TRENDISTA ch·∫•t li·ªáu v∆∞·ª£t tr·ªôi. Mua √°o so mi tr·∫ª em TRENDISTA gi√° ∆∞u ƒë√£i ngay!',NULL,'√Åo s∆° mi tr·∫ª em','ao-so-mi-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',6),(_binary '$^RT\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','BST √°o polo tr·∫ª em TRENDISTA ƒëa d·∫°ng m·∫´u m√£: √°o polo b√© trai, √°o polo b√© g√°i cao c·∫•p, tho√°ng m√°t. Mua √°o polo tr·∫ª em ch√≠nh h√£ng TRENDISTA ngay!',NULL,'√Åo polo tr·∫ª em','ao-polo-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\π&\”\ﬂÔïß\…\Ã\‰æ',7),(_binary '$^R\Ã\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn legging tr·∫ª em laÃÄ m√¥Ã£t trong nh∆∞ÃÉng item kh√¥ng th·ªÉ thi·∫øu c·ªßa caÃÅc beÃÅ. Qu·∫ßn legging v∆∞ÃÄa h∆°Ã£p th∆°ÃÄi trang laÃ£i v∆∞ÃÄa tho·∫£i maÃÅi cho caÃÅc beÃÅ c·∫£ ngaÃÄy hoaÃ£t ƒë√¥Ã£ng. V√¢Ã£y baÃ£n ƒëaÃÉ bi·∫øt coÃÅ nh∆∞ÃÉng laÃ£oi legging naÃÄo phuÃÄ h∆°Ã£p v∆°ÃÅi caÃÅc beÃÅ ch∆∞a? H√£y cuÃÄng TRENDISTA tiÃÄm hi·ªÉu ngay ƒë·ªÉ khaÃÅm ph√°.',NULL,'Qu·∫ßn d√†i tr·∫ª em','quan-dai-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\πû\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^SN\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn kaki tr·∫ª em ƒë∆∞·ª£c xem l√† m√≥n ƒë·ªì kh√¥ng th·ªÉ thi·∫øu trong t·ªß ƒë·ªì c·ªßa c√°c b√© b·ªüi ∆∞u ƒëi·ªÉm khi m·∫∑c ch√≠nh l√† s·ª± tho·∫£i m√°i, d·ªÖ ch·ªãu h∆°n n√™n c√°c m·∫π l·ª±a ch·ªçn mua nhi·ªÅu. Ch∆∞a d·ª´ng l·∫°i ·ªü ƒë√≥ chi·∫øc qu·∫ßn kaki cho b√© trai¬†n√†y c√≤n d·ªÖ d√†ng k·∫øt h·ª£p ƒë∆∞·ª£c v·ªõi nhi·ªÅu lo·∫°i √°o gi√∫p n√¢ng t·∫ßm di·ªán m·∫°o cho c√°c b√© y√™u.',NULL,'Qu·∫ßn kaki tr·∫ª em','quan-kaki-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\πû\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^S\∆\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn n·ªâ tr·∫ª em l√† chi·∫øc qu·∫ßn r·∫•t ho√†n h·∫£o v√† kh√¥ng th·ªÉ thi·∫øu trong t·ªß ƒë·ªì d√†nh cho c√°c b√©. H√£y c√πng TRENDISTA t√¨m hi·ªÉu qu·∫ßn n·ªâ em l√† g√¨, ƒëi·ªÉm qua nh·ªØng m·∫´u qu·∫ßn n·ªâ tr·∫ª em hot nh·∫•t hi·ªán nay c≈©ng nh∆∞ g·ª£i √Ω ph·ªëi ƒë·ªì v·ªõi qu·∫ßn n·ªâ tr·∫ª em qua b√†i vi·∫øt d∆∞·ªõi ƒë√¢y.',NULL,'Qu·∫ßn n·ªâ tr·∫ª em','quan-ni-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\πû\”\ﬂÔïß\…\Ã\‰æ',2),(_binary '$^T>\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Qu·∫ßn short tr·∫ª em kh√¥ng ch·ªâ mang t√°c d·ª•ng ph·ªëi ƒë·ªì l√†m ƒë·∫πp m√† c√≤n l√† m·ªôt m√≥n ƒë·ªì th·ªùi trang r·∫•t c·∫ßn thi·∫øt ƒë·ªëi v·ªõi m·ªçi ƒë·ªô tu·ªïi c·ªßa b√©.',NULL,'Qu·∫ßn short tr·∫ª em','quan-short-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\πû\”\ﬂÔïß\…\Ã\‰æ',3),(_binary '$^T¿\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','TRENDISTA mang ƒë·∫øn th·∫ø gi·ªõi th·ªùi trang nƒÉng ƒë·ªông v√† phong c√°ch cho c√°c b√© y√™u v·ªõi b·ªô s∆∞u t·∫≠p qu·∫ßn jean tr·∫ª em, qu·∫ßn b√≤ tr·∫ª em ƒëa d·∫°ng v√† ch·∫•t l∆∞·ª£ng. TRENDISTA hi·ªÉu r·∫±ng c√°c b√© c·∫ßn s·ª± tho·∫£i m√°i v√† t·ª± do v·∫≠n ƒë·ªông, ƒë·ªìng th·ªùi v·∫´n th·ªÉ hi·ªán c√° t√≠nh ri√™ng.',NULL,'Qu·∫ßn jeans tr·∫ª em','quan-jeans-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\πû\”\ﬂÔïß\…\Ã\‰æ',4),(_binary '$^U8\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì b·ªô d√†i tay tr·∫ª em','do-bo-dai-tay-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∫ \”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^U¶\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'ƒê·ªì b·ªô ng·∫Øn tay tr·∫ª em','do-bo-ngan-tay-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∫ \”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^V\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'√Åo gi·ªØ nhi·ªát tr·∫ª em','ao-giu-nhiet-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\∫¨\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^Vå\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','N√≥i ƒë·∫øn th·ªùi trang tr·∫ª em kh√¥ng th·ªÉ kh√¥ng k·ªÉ ƒë·∫øn nh·ªØng m·∫´u v√°y tr·∫ª em ƒë·∫πp. Nh·ªØng chi·∫øc v√°y ƒë·∫ßm tr·∫ª em cao c·∫•p kh√¥ng ch·ªâ ƒë∆∞·ª£c c√°c b√© g√°i y√™u th√≠ch v√† l·ª±a ch·ªçn khi ƒëi mua s·∫Øm c√πng b·ªë m·∫π. TRENDISTA m√°ch b·∫°n nh·ªØng ki·ªÉu v√°y c∆° b·∫£n v√† nh·ªØng tip h∆∞·ªõng d·∫´n mix trang ph·ª•c ƒë·ªÉ m·∫π tr·ªü th√†nh chuy√™n gia v√† bi·∫øn b√© tr·ªü',NULL,'ƒê·∫ßm b√© g√°i','dam-be-gai',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\ªà\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^W6\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','Di·ªán c√°c m·∫´u ch√¢n v√°y b√© g√°i ƒë·∫πp lu√¥n l√† ni·ªÅm y√™u th√≠ch c·ªßa c√°c c√¥ b√© y√™u th√≠ch s·ª± d·ªÖ th∆∞∆°ng, ƒëi·ªáu ƒë√†. ƒê√¢y c≈©ng l√† item n√†y m√† ph·ª• huynh hi·ªán nay l·ª±a ch·ªçn cho c√°c c√¥ c√¥ng ch√∫a nh·ªè c·ªßa m√¨nh. V·ªõi b√†i vi·∫øt d∆∞·ªõi ƒë√¢y, TRENDISTA mong r·∫±ng b·∫°n s·∫Ω t√¨m ƒë∆∞·ª£c cho b√© y√™u nh√† m√¨nh nh·ªØng m·∫´u ch√¢n v√°y ph√π h·ª£p nh·∫•t!',NULL,'Ch√¢n v√°y b√© g√°i','chan-vay-be-gai',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\ªà\”\ﬂÔïß\…\Ã\‰æ',1),(_binary '$^W\¬\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'T·∫•t tr·∫ª em','tat-tre-em',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\º\n\”\ﬂÔïß\…\Ã\‰æ',0),(_binary '$^X0\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',NULL,NULL,'Ph·ª• ki·ªán tr·∫ª em kh√°c','phu-kien-tre-em-khac',_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ',_binary '$\\º\n\”\ﬂÔïß\…\Ã\‰æ',1);
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
/*!40000 ALTER TABLE `category_discount` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_message`
--

DROP TABLE IF EXISTS `chat_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_message` (
  `id` binary(16) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `sender` varchar(255) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `recipient_id` binary(16) DEFAULT NULL,
  `sender_id` binary(16) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  `is_admin` bit(1) NOT NULL,
  `is_read` bit(1) NOT NULL,
  `user_info` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgodixomu1da3pi892h3xxx523` (`user_info`),
  CONSTRAINT `FKgodixomu1da3pi892h3xxx523` FOREIGN KEY (`user_info`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
INSERT INTO `chat_message` VALUES (_binary 'ü	\ÂLÅêr784®','hello','user','2025-02-06 11:14:31.260000',NULL,NULL,_binary '◊Ñ~?EÖéç h9\ÿ^',_binary '\0',_binary '\0',NULL),(_binary '\r∞\ÏsH\0ß\\\€\◊∆™å\Ÿ','hello','admin','2025-02-06 11:20:55.402000',NULL,NULL,_binary '\Ìº¸ó˚INAì t\ÚR\\∑=',_binary '\0',_binary '\0',NULL),(_binary ']5à+\ÈO–ûë¡õD\Ÿ\Ã','Ch√†o b·∫°n','admin','2025-02-06 11:12:15.564000',NULL,NULL,_binary '◊Ñ~?EÖéç h9\ÿ^',_binary '\0',_binary '\0',NULL),(_binary '~îo8\ÿ˛@sÇN‘òh∫','Hello','admin','2025-02-06 11:10:54.269000',NULL,NULL,_binary '◊Ñ~?EÖéç h9\ÿ^',_binary '\0',_binary '\0',NULL),(_binary '∫µé>sB~∂2:\0#\ﬁ3¸','Tin nh·∫Øn t·ª´ shop','admin','2025-02-06 11:33:17.160000',NULL,NULL,_binary '\Ìº¸ó˚INAì t\ÚR\\∑=',_binary '\0',_binary '\0',NULL);
/*!40000 ALTER TABLE `chat_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `collection`
--

DROP TABLE IF EXISTS `collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `collection` (
  `id` binary(16) NOT NULL,
  `create_at` datetime(6) NOT NULL,
  `delete_at` datetime(6) NOT NULL,
  `update_at` datetime(6) NOT NULL,
  `description` text,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK6ddpfjq9st4mbnjvqyi3aaqkj` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection`
--

LOCK TABLES `collection` WRITE;
/*!40000 ALTER TABLE `collection` DISABLE KEYS */;
INSERT INTO `collection` VALUES (_binary '?˚\œ]Å±Eõ°EÆ±^DJn','2025-02-11 15:14:48.582000','2025-02-11 15:14:48.582000','2025-02-11 15:14:55.256000','BST thu ƒë√¥ng','BST thu ƒë√¥ng 2024','bst-thu-ong-2024',NULL),(_binary '»ï\‘8ò#AQïÜèûSŒî','2025-02-11 15:58:05.829000','2025-02-11 15:58:05.829000','2025-02-11 15:58:13.426000','B·ªô s∆∞u t·∫≠p m√πa xu√¢n 2025','BST m√πa xu√¢n 2025','bst-mua-xuan-2025','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739264285/collections/thumbnails/collection_thumb_bst-mua-xuan-2025.jpg');
/*!40000 ALTER TABLE `collection` ENABLE KEYS */;
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
INSERT INTO `color` VALUES (_binary '$Xáz\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-02-10 10:01:25.683000','xanh l∆°','#8ABCC0','sky-blue'),(_binary '$Xâd\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh c·ªï v·ªãt','#244368','teal'),(_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh ƒë·∫≠m','#235387','dark-blue'),(_binary '$Xäh\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh mint','#CDDCDB','mint-green'),(_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh nh·∫°t','#A8D3D7','light-blue'),(_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh ƒëen','#13293D','black-blue'),(_binary '$Xã0\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh coban','#0E405F','cobalt-blue'),(_binary '$Xãl\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh l√°','#008000','green'),(_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','r√™u','#45503B','moss-green'),(_binary '$Xã\Ó\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','xanh r√™u','#747553','olive-green'),(_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','ghi','#AE9B91','ash'),(_binary '$Xåf\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','ghi x√°m','#C5C9CA','gray-ash'),(_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','n√¢u','#C09A7E','brown'),(_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','n√¢u ƒë·∫≠m','#3A2923','dark-brown'),(_binary '$Xç$\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','cam','#FE523D','orange'),(_binary '$Xç`\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','cam ƒë·∫•t','#E35125','earth-orange'),(_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','be','#F5F5DC','beige'),(_binary '$Xç\‚\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','h·ªìng','#FFC0CB','pink'),(_binary '$Xé\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','t√≠m','#DEBCC8','purple'),(_binary '$Xéd\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','t√≠m nh·∫°t','#C3BCE8','light-purple'),(_binary '$Xé†\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','t√≠m than','#204A80','indigo'),(_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','ƒëen','#000000','black'),(_binary '$Xè\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','navy','#27285C','navy'),(_binary '$XèT\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','v√†ng','#FFFF00','yellow'),(_binary '$Xèö\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','ƒë·ªè','#FF0000','red'),(_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','x√°m','#DDDDDD','gray'),(_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','x√°m ƒë·∫≠m','#9B8F91','dark-gray'),(_binary '$XêN\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','tr·∫Øng','#FFFFFF','white');
/*!40000 ALTER TABLE `color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `conversations`
--

DROP TABLE IF EXISTS `conversations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `conversations` (
  `id` binary(16) NOT NULL,
  `sender_id` binary(16) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmqamgi4ilj0qedylhrhcwffec` (`user_id`),
  CONSTRAINT `FKmqamgi4ilj0qedylhrhcwffec` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `conversations`
--

LOCK TABLES `conversations` WRITE;
/*!40000 ALTER TABLE `conversations` DISABLE KEYS */;
/*!40000 ALTER TABLE `conversations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `discount`
--

DROP TABLE IF EXISTS `discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `discount` (
  `id` binary(16) NOT NULL,
  `code` varchar(255) NOT NULL,
  `description` text,
  `discount_type` enum('AMOUNT','PERCENT') NOT NULL,
  `discount_value` decimal(10,2) NOT NULL,
  `end_date` datetime(6) NOT NULL,
  `frame` varchar(255) DEFAULT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `max_discount_value` decimal(10,2) DEFAULT NULL,
  `max_usage_per_customer` int DEFAULT NULL,
  `min_order_value` decimal(10,2) DEFAULT NULL,
  `start_date` datetime(6) NOT NULL,
  `usage_limit` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKi14w897ofrtv43vbx44rtv01u` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `discount`
--

LOCK TABLES `discount` WRITE;
/*!40000 ALTER TABLE `discount` DISABLE KEYS */;
INSERT INTO `discount` VALUES (_binary '\ıZ\Ï(ÇPGœì¯S)Z\Ï','TET25','Ch∆∞∆°ng tr√¨nh gi·∫£m gi√° ƒë·∫∑c bi·ªát cho T·∫øt Nguy√™n ƒê√°n cho ƒë∆°n h√†ng tr√™n 1tr ƒë·ªìng','PERCENT',10.00,'2025-03-01 23:59:59.999000','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1740123419/Discount%20Frame/Discount%20Frame_Screenshot%202025-02-17%20091930.png',_binary '',50000.00,NULL,5000.00,'2025-02-21 00:00:00.000000',NULL);
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
INSERT INTO `gender` VALUES (_binary '$\\SZ\”\ﬂÔïß\…\Ã\‰æ','Nam',NULL,'nam'),(_binary '$\\T˛\”\ﬂÔïß\…\Ã\‰æ','N·ªØ',NULL,'nu'),(_binary '$\\Uû\”\ﬂÔïß\…\Ã\‰æ','Tr·∫ª em',NULL,'tre-em'),(_binary '$\\U\‰\”\ﬂÔïß\…\Ã\‰æ','Unisex',NULL,'unisex');
/*!40000 ALTER TABLE `gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` binary(16) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `recipient` binary(16) DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
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
  `order_id` binary(16) DEFAULT NULL,
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
INSERT INTO `order_item` VALUES (_binary '|0D\›1I∞ísù[¥å\…',5000.00,_binary '$`8\”\ﬂÔïß\…\Ã\‰æ',1,_binary '\ÕRõoóI\ˆóÇô>\\¯',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ');
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
  `expected_delivery_date` datetime(6) DEFAULT NULL,
  `expired_at` datetime(6) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `order_coder` bigint DEFAULT NULL,
  `order_date` datetime(6) DEFAULT NULL,
  `order_status` enum('CANCELLED','CREATED','DELIVERED','PENDING','PROCESSING','RETURNED','SHIPPED') NOT NULL,
  `payment_method` enum('COD','QR') DEFAULT NULL,
  `shipment_tracking_number` varchar(255) DEFAULT NULL,
  `total_amount` decimal(38,2) NOT NULL,
  `address_id` binary(16) DEFAULT NULL,
  `discount_id` binary(16) DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf5464gxwc32ongdvka2rtvw96` (`address_id`),
  KEY `FKdovsc3u2it5yoknwgx4brjid1` (`discount_id`),
  KEY `FKel9kyl84ego2otj2accfd8mr7` (`user_id`),
  CONSTRAINT `FKdovsc3u2it5yoknwgx4brjid1` FOREIGN KEY (`discount_id`) REFERENCES `discount` (`id`),
  CONSTRAINT `FKel9kyl84ego2otj2accfd8mr7` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKf5464gxwc32ongdvka2rtvw96` FOREIGN KEY (`address_id`) REFERENCES `address` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (_binary '\ÕRõoóI\ˆóÇô>\\¯','2025-02-21 15:59:37.910000','2025-02-21 15:59:37.910000','2025-02-21 16:08:32.678000','2025-02-28 15:59:37.904000','2025-02-21 16:18:32.632215','Chuy·ªÉn nhanh',377904,'2025-02-21 15:59:37.904072','PENDING','COD',NULL,4500.00,_binary 'N*É\Ê\˜N\∏f1D¨\ﬂ\‚\n',_binary '\ıZ\Ï(ÇPGœì¯S)Z\Ï',_binary '◊Ñ~?EÖéç h9\ÿ^');
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
  `amount` int NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `deep_link` varchar(255) DEFAULT NULL,
  `paid_at` datetime(6) DEFAULT NULL,
  `payment_method` enum('COD','QR') NOT NULL,
  `payment_status` varchar(255) DEFAULT NULL,
  `qr_code` mediumtext,
  `transaction_id` bigint DEFAULT NULL,
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
INSERT INTO `payment` VALUES (_binary 'f\\í>bCUô!ÄGŸ™¶É',4500,'2025-02-21 16:08:32.633217',NULL,NULL,'COD','CREATED',NULL,377904,_binary '\ÕRõoóI\ˆóÇô>\\¯');
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
INSERT INTO `permission` VALUES (_binary '$ÔøΩÔøΩÔøΩLs\Ôø','/address/**','POST','USER CREATE ADDRESS'),(_binary 'u6ÓëΩÔøΩGÔøΩÔøΩ\Ô','/cart-order/create','POST','USER CREATE ORDER'),(_binary '|BÔøΩÔøΩÔøΩÔøΩNw','/cart-order/user','GET','USER GET ORDER'),(_binary '}1*ÔøΩÔøΩÔøΩ@ÔøΩ','/cart-order/cancel/**','PUT','USER CANCEL ORDER'),(_binary 'ÔøΩÔøΩyÃûLÔøΩ\Ôø','/user/**','GET','USER READ ACCOUNT');
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
INSERT INTO `product` VALUES (_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-17 14:19:30.687000','VEM7003','Ch·∫•t li·ªáu Nano waffle cao c·∫•p t·∫°o n√™n s·ª± kh√°c bi·ªát: m·ªÅm m·∫°i, tho√°ng m√°t, ch·ªëng nhƒÉn v√† ch·ªëng UV hi·ªáu qu·∫£. C√¥ng ngh·ªá s·ª£i nano si√™u m·∫£nh mang ƒë·∫øn tr·∫£i nghi·ªám m·∫∑c v√¥ c√πng tho·∫£i m√°i v√† sang tr·ªçng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Vest Nam M·ªôt L·ªõp',5000.00,5000.00,0,0,'vest-nam-mot-lop',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^8˙\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-17 14:20:46.576000','AKM7007','√Åo kho√°c nam ƒëa nƒÉng m≈© li·ªÅn ti·ªán l·ª£i, l√≥t l√¥ng ·∫•m √°p. C√¥ng ngh·ªá m√†ng PU si√™u m·ªèng nh·∫π, ch·ªëng th·∫•m n∆∞·ªõc hi·ªáu qu·∫£. Thi·∫øt k·∫ø hi·ªán ƒë·∫°i v·ªõi ph·ªëi kho√° tr·∫ª trung, d√¢y r√∫t ch·∫Øc ch·∫Øn, kho√° t√∫i trong an to√†n ƒë·ªÉ ƒë·ª±ng ƒë·ªì, c√∫c b·∫•m c·ªï tay tr√°nh gi√≥ l√πa. V·∫£i polyester cao c·∫•p, b·ªÅn b·ªâ, tr∆∞·ª£t n∆∞·ªõc 3k ph√π h·ª£p m·ªçi th·ªùi ti·∫øt.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'√Åo Kho√°c Nam M≈© Li·ªÅn L√≥t L√¥ng',849000.00,849000.00,0,0,'ao-khoac-nam-mu-lien-lot-long',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^:\–\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','PHM7009','Trang b·ªã cho m√πa ƒë√¥ng l·∫°nh gi√° m·ªôt chi·∫øc √°o phao ∆∞u vi·ªát: √Åo Phao Nam Tr·∫ßn Tr√°m N·∫πp Gi·∫•u Kho√°. Chi·∫øc √°o c√≥ tr·ªçng l∆∞·ª£ng nh·∫π, kh·∫£ nƒÉng gi·ªØ ·∫•m t·ªët c√πng thi·∫øt k·∫ø hi·ªán ƒë·∫°i. Thi·∫øt k·∫ø tr·∫ßn tr√°m b·ªÅn ƒë·∫πp theo th·ªùi gian, kh√¥ng s·ª£ l·ªói m·ªët. Kho√° k√©o YKK - lo·∫°i kho√° h√†ng ƒë·∫ßu th·∫ø gi·ªõi, b·ªÅn ch·∫Øc','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'√Åo Phao Nam Tr·∫ßn Tr√°m N·∫πp Gi·∫•u Kho√°',699000.00,699000.00,0,0,'ao-phao-nam-tran-tram-nep-giau-khoa',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^:b\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','STM7055','C√¥ng ngh·ªá d·ªát Jacquard hi·ªán ƒë·∫°i, t·∫°o h·ªça ti·∫øt l·ªó ƒë·ªôc ƒë√°o. Th·∫•m h√∫t t·ªët, kh√¥ nhanh, th√¥ng tho√°ng nh·ªù ki·ªÉu d·ªát - t·∫°o c·∫£m tho·∫£i m√°i cho l√†n da. Ki·ªÉu d√°ng √°o thun th·ªÉ thao basic, tr·∫ª trung, nƒÉng ƒë·ªông. C·ªï tr√≤n, √¥m s√°t c·ªï, t·∫°o c·∫£m gi√°c tho·∫£i m√°i khi v·∫≠n ƒë·ªông.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'√Åo Thun Th·ªÉ Thao Nam Si√™u Nh·∫π',279000.00,279000.00,0,0,'ao-thun-the-thao-nam-sieu-nhe',_binary '',NULL,'NEW_ARRIVALS',0,0,_binary '$^<8\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','QKM7005','Qu·∫ßn Kaki Nam C·∫°p Di ƒê·ªông Ki·ªÉu d√°ng c·∫°p di ƒë·ªông th·ªùi trang, form chu·∫©n ƒë·∫πp. Ch·∫•t li·ªáu Khaki cotton cao c·∫•p, th√†nh ph·∫ßn cotton cao gi√∫p qu·∫ßn th√¥ng tho√°ng, d·ªÖ ch·ªãu khi m·∫∑c. V·∫£i ƒëanh ch·∫Øc gi·ªØ phom l√¢u d√†i nh∆∞ng v·∫´n m·ªÅm m·∫°i, kh√¥ng th√¥ c·ª©ng, kh√¥ng b·ªã nhƒÉn nh√∫m hay bai d√£o','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Qu·∫ßn Kaki Nam C·∫°p Di ƒê·ªông',499000.00,499000.00,0,0,'quan-kaki-nam-cap-di-dong',_binary '',NULL,'NEW_ARRIVALS',0,0,_binary '$^>\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','QKM6017','Ki·ªÉu d√°ng Slim fit √¥m s√°t t√¥n l√™n ƒë∆∞·ªùng n√©t c∆° th·ªÉ, gi√∫p b·∫°n t·ª± tin v√† kho·∫ª kho·∫Øn trong m·ªçi ho·∫°t ƒë·ªông t·ª´ ƒëi l√†m, ƒëi ch∆°i ƒë·∫øn d·ª± ti·ªác. S·∫£n ph·∫©m ho√†n h·∫£o cho ph√°i m·∫°nh hi·ªán ƒë·∫°i - t·ª± tin kh·∫≥ng ƒë·ªãnh phong c√°ch.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Qu·∫ßn Kaki Nam Slimfit',499000.00,499000.00,0,0,'quan-kaki-nam-slimfit',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^>\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','QJM6041','Qu·∫ßn co gi√£n nh·∫π gi√∫p ng∆∞·ªùi m·∫∑c tho·∫£i m√°i khi v·∫≠n ƒë·ªông, ch·∫•t li·ªáu denim like ƒë∆∞·ª£c l√†m t·ª´ v·∫£i m·ªôc m√†u tr·∫Øng sau ƒë√≥ ƒë∆∞·ª£c nhu·ªôm m√†u n√™n m√†u s·∫Øc phong ph√∫ ƒëa d·∫°ng, t∆∞∆°i s√°ng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Qu·∫ßn Jeans Nam Slim Denim Like C∆° B·∫£n',599000.00,599000.00,0,0,'quan-jeans-nam-slim-denim-like-co-ban',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^>\Ù\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-02-10 10:19:16.531000','AKN7001','S·∫£n ph·∫©m ƒë∆∞·ª£c ch·ªù ƒë√≥n m·ªói m√πa thu ƒë√¥ng t·∫°i TRENDISTA: √°o mƒÉng t√¥ d√°ng d√†i d√†nh cho ph√°i ƒë·∫πp. S·ª≠ d·ª•ng s·ª£i tencel c√≥ ngu·ªìn g·ªëc t·ª± nhi√™n mang ƒë·∫øn ƒë·ªô m∆∞·ª£t b√≥ng nh·∫π cho √°o. Thi·∫øt k·∫ø c·ªï ƒëi·ªÉn ƒë∆∞·ª£c ch√∫ tr·ªçng t·ªâ m·ªâ trong t·ª´ng chi ti·∫øt ƒëem l·∫°i ƒë·ªô ho√†n thi·ªán cao.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'√Åo MƒÉng T√¥ N·ªØ ƒêai Eo Casual',1399000.00,1399000.00,0,0,'ao-mang-to-nu-dai-eo-casual',_binary '',NULL,'NEW_ARRIVALS',0,0,_binary '$^ \¬\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-02-10 10:19:17.251000','KNN7004','Ch·∫•t li·ªáu PU si√™u m·ªÅm, gi·ªëng da th·∫≠t nh∆∞ng kh√¥ng g√¢y h·∫°i cho ƒë·ªông v·∫≠t. L·ªõp l√≥t m·ªÅm m·∫°i, gi·ªØ ·∫•m tuy·ªát v·ªùi, bo g·∫•u co gi√£n tho·∫£i m√°i. Thi·∫øt k·∫ø tr·∫ª trung, nƒÉng ƒë·ªông. Th·ªùi trang, b·ªÅn ƒë·∫πp, th√¢n thi·ªán v·ªõi m√¥i tr∆∞·ªùng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Kho√°c Ng·∫Øn N·ªØ Bomber Da Ph√¥i Bo Chun',990000.00,990000.00,0,0,'khoac-ngan-nu-bomber-da-phoi-bo-chun',_binary '',NULL,'NEW_ARRIVALS',0,0,_binary '$^ \¬\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','QSN7033','Qu·∫ßn short n·ªØ d√°ng ng·∫Øn thi·∫øt k·∫ø tr·∫ª trung. Ch·∫•t li·ªáu d·∫° cao c·∫•p, b·ªÅn m√†u, kh√¥ng nhƒÉn. Th√†nh ph·∫ßn spandex gi√∫p qu·∫ßn co gi√£n tho·∫£i m√°i. ƒêai kim lo·∫°i t·∫°o ƒëi·ªÉm nh·∫•n th·ªùi trang.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Qu·∫ßn Short N·ªØ D·∫° ƒêai Kim lo·∫°i',399000.00,399000.00,0,0,'quan-sooc-nu-da-dai-kim-loai',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^&®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','QJN7067','Qu·∫ßn jeans n·ªØ thi·∫øt k·∫ø ·ªëng loe c√° t√≠nh, g·∫•u nhƒÉn ƒë·ªôc ƒë√°o t·∫°o phong c√°ch th·ªùi th∆∞·ª£ng. Ch·∫•t li·ªáu denim cotton m·ªÅm m·∫°i, th√¥ng tho√°ng, c√≥ ƒë·ªô b·ªÅn cao. Qu·∫ßn co gi√£n nh·∫π do ch·ª©a th√†nh ph·∫ßn spandex gi√∫p ng∆∞·ªùi m·∫∑c tho·∫£i m√°i khi v·∫≠n ƒë·ªông. H·∫°n ch·∫ø kh√¥ c·ª©ng sau gi·∫∑t.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Qu·∫ßn Jean N·ªØ ·ªêng Loe G·∫•u NhƒÉn',549000.00,549000.00,0,0,'quan-jean-nu-ong-loe-gau-nhan',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^\' \”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-17 13:53:08.666000','QJN6052','M·ªôt thi·∫øt k·∫ø qu·∫ßn jean n·ªØ ·ªëng su√¥ng tr·∫ª trung t√¥n d√°ng, ch·∫•t v·∫£i tho√°ng m√°t, m·ªÅm, ƒë·ªô b·ªÅn cao. S·ªü h·ªØu ngay.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Qu·∫ßn Jeans N·ªØ D√°ng Su√¥ng Cotton Tr∆°n',549000.00,549000.00,0,0,'quan-jeans-nu-dang-suong-cotton-tron',_binary '',NULL,'BEST_SELLERS',0,0,_binary '$^\' \”\ﬂÔïß\…\Ã\‰æ');
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
INSERT INTO `product_image` VALUES (_binary '$c:Ä\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952046/ao-vest-nam-VEM7003-DEN_ndii5v.jpg',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c<~\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952035/ao-vest-nam-VEM7003-DEN-1_arvuth.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c=n\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952038/ao-vest-nam-VEM7003-DEN-2_uuxai7.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c=\Ê\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952036/ao-vest-nam-VEM7003-DEN-3_xhioc9.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c>J\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952036/ao-vest-nam-VEM7003-DEN-4_mgqsew.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c>Æ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952036/ao-vest-nam-VEM7003-DEN-5_p8citu.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c?\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952037/ao-vest-nam-VEM7003-DEN-6_eohpkx.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c?Ä\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952041/ao-vest-nam-VEM7003-DEN-7_fjel6t.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c?\‰\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952064/ao-vest-nam-VEM7003-NAU_bwpwcr.jpg',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c@H\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952054/ao-vest-nam-VEM7003-NAU-1_iqf3yn.webp',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c@¨\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952055/ao-vest-nam-VEM7003-NAU-2_veptzv.webp',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cA\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952057/ao-vest-nam-VEM7003-NAU-3_k72fdj.webp',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cAt\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952058/ao-vest-nam-VEM7003-NAU-4_hkilbl.webp',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cA\ÿ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952059/ao-vest-nam-VEM7003-NAU-5_bqb5pk.webp',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cB2\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952062/ao-vest-nam-VEM7003-NAU-6_egzpgf.webp',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cBñ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952063/ao-vest-nam-VEM7003-NAU-7_aed7da.webp',_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cB\\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952053/ao-vest-nam-VEM7003-GHI_jm2iwg.jpg',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cC^\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952043/ao-vest-nam-VEM7003-GHI-1_az7hdc.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cC\¬\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952044/ao-vest-nam-VEM7003-GHI-2_xsiope.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cD&\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952045/ao-vest-nam-VEM7003-GHI-3_or9cuj.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cDî\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952046/ao-vest-nam-VEM7003-GHI-4_l1dn13.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cD¯\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952048/ao-vest-nam-VEM7003-GHI-5_kqbcjr.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cEf\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952050/ao-vest-nam-VEM7003-GHI-6_r4yiy1.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cE\ \”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952051/ao-vest-nam-VEM7003-GHI-7_wg8nsh.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cF.\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-1_dlw5o5.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cFú\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-2_flmfac.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cG\0\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945685/ao-khoac-nam-AKM7007-DEN-3_lwyulf.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cGd\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-4_qbnzzw.webp_flmfac.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cG\»\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-5_q8real.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cH,\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-6_xv8rl5.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cHÜ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945685/ao-khoac-nam-AKM7007-DEN-7_rgwihy.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cH\‡\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945686/ao-khoac-nam-AKM7007-GHI-1_iax9ht.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cID\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945686/ao-khoac-nam-AKM7007-GHI-2_ocqcrd.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cI®\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945686/ao-khoac-nam-AKM7007-GHI-3_geiggd.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cJ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945689/ao-khoac-nam-AKM7007-GHI-4_wpdtqy.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cJp\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945689/ao-khoac-nam-AKM7007-GHI-5_sbq7x2.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cJ\ﬁ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945689/ao-khoac-nam-AKM7007-GHI-6_af2m2m.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cK8\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945690/ao-khoac-nam-AKM7007-GHI-7_strbfp.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cKú\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953566/ao-phao-nam-PHM7009-XDE-1_v4ifxk.webp',_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cL\0\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953567/ao-phao-nam-PHM7009-XDE-2_clxzqi.webp',_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cLd\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953568/ao-phao-nam-PHM7009-XDE-3_zw822y.webp',_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cLæ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953570/ao-phao-nam-PHM7009-XDE-4_efoohd.webp',_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cM\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953570/ao-phao-nam-PHM7009-XDE-5_bz8uc7.webp',_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cMÜ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953572/ao-phao-nam-PHM7009-XDE-6_hfy2ne.webp',_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cM\‡\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953573/ao-phao-nam-PHM7009-XDE-7_bwnsrm.webp',_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cND\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953560/ao-phao-nam-PHM7009-REU-1_xja9nz.webp',_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cN\∆\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953560/ao-phao-nam-PHM7009-REU-2_lpxfeu.webp',_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cO4\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953561/ao-phao-nam-PHM7009-REU-3_bbau3g.webp',_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cO¨\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953560/ao-phao-nam-PHM7009-REU-4_a8qvou.webp',_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cP\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953561/ao-phao-nam-PHM7009-REU-5_t7zpuw.webp',_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cPj\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953561/ao-phao-nam-PHM7009-REU-6_wg7wfj.webp',_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cP\Œ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953564/ao-phao-nam-PHM7009-REU-7_hvrgjl.jpg',_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cQ2\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953773/ao-thun-STM7055-XAM-1_yfid3s.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cQñ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953775/ao-thun-STM7055-XAM-2_p4fwel.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cQ˙\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953776/ao-thun-STM7055-XAM-3_fc4ffb.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cRT\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953777/ao-thun-STM7055-XAM-4_xhumck.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cR∏\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953779/ao-thun-STM7055-XAM-5_jgigdx.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cS&\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953781/ao-thun-STM7055-XAM-6_wodhrg.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cSä\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953766/ao-thun-STM7055-DEN-1_zcdq3l.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cS¯\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953767/ao-thun-STM7055-DEN-2_ighc7z.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cT\\\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953768/ao-thun-STM7055-DEN-3_lyrrsa.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cT\ \”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953769/ao-thun-STM7055-DEN-4_nsxqkx.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cU.\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953770/ao-thun-STM7055-DEN-5_r3g3ny.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cUú\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953772/ao-thun-STM7055-DEN-6_gswzbd.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cV\0\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954062/quan-kaki-nam-QKM7005-GHI-1_gba4eg.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cVZ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954065/quan-kaki-nam-QKM7005-GHI-2_hrrfej.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cVæ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954066/quan-kaki-nam-QKM7005-GHI-3_cbxu42.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cW\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954069/quan-kaki-nam-QKM7005-GHI-4_vdvyjx.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cW|\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954070/quan-kaki-nam-QKM7005-GHI-5_ewyryb.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cW\‡\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954071/quan-kaki-nam-QKM7005-GHI-6_ldvjdp.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cXD\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954073/quan-kaki-nam-QKM7005-GHI-7_mxzkfe.webp',_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cX®\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954052/quan-kaki-nam-QKM7005-DEN-1_lxxkwx.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cY\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954053/quan-kaki-nam-QKM7005-DEN-2_yq9032.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cYf\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954055/quan-kaki-nam-QKM7005-DEN-3_f63waj.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cY\ \”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954056/quan-kaki-nam-QKM7005-DEN-4_o6lapx.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cZ.\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954058/quan-kaki-nam-QKM7005-DEN-5_xdezr0.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cZà\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954059/quan-kaki-nam-QKM7005-DEN-6_ajzt5l.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cZ\Ï\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954061/quan-kaki-nam-QKM7005-DEN-7_wvtqaz.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c[F\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954286/quan-kaki-nam-QKM6017-XAM-1_ejvx0p.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$c[™\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954288/quan-kaki-nam-QKM6017-XAM-2_dwxyts.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ch\‘\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954290/quan-kaki-nam-QKM6017-XAM-3_utabal.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cit\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954292/quan-kaki-nam-QKM6017-XAM-4_zfl8oj.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ci\Ï\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954293/quan-kaki-nam-QKM6017-XAM-5_ybq2i0.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cjP\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954294/quan-kaki-nam-QKM6017-XAM-6_b5sx9n.webp',_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cj¥\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954277/quan-kaki-nam-QKM6017-DEN-1_zypmwm.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ck\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954278/quan-kaki-nam-QKM6017-DEN-2_ydhnk1.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ckÜ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954280/quan-kaki-nam-QKM6017-DEN-3_ddprtv.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ck\Í\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954282/quan-kaki-nam-QKM6017-DEN-4_v9gn5i.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$clX\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954283/quan-kaki-nam-QKM6017-DEN-5_iepckl.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$clº\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954285/quan-kaki-nam-QKM6017-DEN-6_osciga.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cm\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954548/quan-jeans-QJM6041-DEN-1_mf6kmw.webp',_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cm¨\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954548/quan-jeans-QJM6041-DEN-2_pal07y.webp',_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cn\Z\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954549/quan-jeans-QJM6041-DEN-3_ayrp5w.webp',_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cn~\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954550/quan-jeans-QJM6041-DEN-4_kydjsb.webp',_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cn\‚\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954552/quan-jeans-QJM6041-DEN-5_bejzj5.webp',_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$coF\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954553/quan-jeans-QJM6041-XAD-1_arvwgl.jpg',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$co¥\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954554/quan-jeans-QJM6041-XAD-2_kvlajl.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cp\"\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954555/quan-jeans-QJM6041-XAD-3_hdx3dc.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cpÜ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954557/quan-jeans-QJM6041-XAD-4_zneppa.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cp\Í\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954558/quan-jeans-QJM6041-XAD-5_szancm.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cqN\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954672/ao-khoac-nu-AKN7001-BEE-1_ekkouu.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cqº\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954673/ao-khoac-nu-AKN7001-BEE-2_fuuldn.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cr \”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954674/ao-khoac-nu-AKN7001-BEE-3_v5vell.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$crÑ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954676/ao-khoac-nu-AKN7001-BEE-4_jxgphy.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cr\Ú\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954677/ao-khoac-nu-AKN7001-BEE-5_mw6bt2.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$csV\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954679/ao-khoac-nu-AKN7001-BEE-6_kwcccy.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cs∫\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954681/ao-khoac-nu-AKN7001-BEE-7_fx0r0n.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ct\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954764/ao-khoac-nu-KNN7004-DEN-1_p9tpwf.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ctx\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954753/ao-khoac-nu-KNN7004-BEE-2_fbbd6u.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$ct\‹\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954756/ao-khoac-nu-KNN7004-BEE-3_kvrl6q.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cuT\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954757/ao-khoac-nu-KNN7004-BEE-4_vede62.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cu∏\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954758/ao-khoac-nu-KNN7004-BEE-5_wcd8gu.jpg',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cv\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954758/ao-khoac-nu-KNN7004-BEE-6_eiwmfc.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cvÄ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954761/ao-khoac-nu-KNN7004-BEE-7_ntwdm5.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cv\‰\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954764/ao-khoac-nu-KNN7004-DEN-1_p9tpwf.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cwR\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954765/ao-khoac-nu-KNN7004-DEN-2_i2dmfb.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cw∂\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954767/ao-khoac-nu-KNN7004-DEN-3_zoqaev.jpg',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cx\Z\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954769/ao-khoac-nu-KNN7004-DEN-4_js8vgp.jpg',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cx~\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954772/ao-khoac-nu-KNN7004-DEN-5_oxg5n6.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÅH\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954772/ao-khoac-nu-KNN7004-DEN-6_pyc85h.webp',_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÅ¿\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955122/quan-short-nu-QSN7033-BEE-1_ulhpys.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÇ.\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955123/quan-short-nu-QSN7033-BEE-2_os2xpm.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÇí\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955124/quan-short-nu-QSN7033-BEE-3_q2h5tn.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÉ\0\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955126/quan-short-nu-QSN7033-BEE-4_abe82s.webp',_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÉd\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955127/quan-short-nu-QSN7033-NAD-1_ysmg3a.webp',_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÉ\»\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955128/quan-short-nu-QSN7033-NAD-2_wy4zio.webp',_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÑ,\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955131/quan-short-nu-QSN7033-NAD-3_mucx0q.webp',_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÑê\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955132/quan-short-nu-QSN7033-NAD-4_mtxpl8.webp',_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÑ\Ù\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955237/quan-jeans-nu-QJN7067-XDM-1_khoxws.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÖb\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955238/quan-jeans-nu-QJN7067-XDM-2_cqsuhg.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÖ\∆\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955240/quan-jeans-nu-QJN7067-XDM-3_ei32j4.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÜ*\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955242/quan-jeans-nu-QJN7067-XDM-4_affc57.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÜé\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955244/quan-jeans-nu-QJN7067-XDM-5_yf8thz.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cÜ\Ú\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955245/quan-jeans-nu-QJN7067-XDM-6_dlx9er.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cáV\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955247/quan-jeans-nu-QJN7067-XDM-7_mrfdzz.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cá∫\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955249/quan-jeans-nu-QJN7067-XNH-1_qmed2j.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cà<\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955250/quan-jeans-nu-QJN7067-XNH-2_j9an4r.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cà™\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955251/quan-jeans-nu-QJN7067-XNH-3_yocswr.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$câ\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955254/quan-jeans-nu-QJN7067-XNH-4_hfhe9b.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$câr\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955255/quan-jeans-nu-QJN7067-XNH-5_bnbi2u.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$câ\÷\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955258/quan-jeans-nu-QJN7067-XNH-6_jq9dln.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cä:\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955259/quan-jeans-nu-QJN7067-XNH-7_cohwuf.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cäû\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955382/quan-jeans-nu-QJN6052-XDM-1_cfhjet.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cã\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955385/quan-jeans-nu-QJN6052-XDM-2_ahgnvg.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cãp\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955387/quan-jeans-nu-QJN6052-XDM-3_yrsaaf.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cã\‘\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955387/quan-jeans-nu-QJN6052-XDM-4_wglmct.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cå8\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955392/quan-jeans-nu-QJN6052-XDM-6_ofrzzv.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cå¶\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955394/quan-jeans-nu-QJN6052-XDM-7_ju2an0.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cç\n\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955394/quan-jeans-nu-QJN6052-XDM-5_mn68er.webp',_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cçn\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955395/quan-jeans-nu-QJN6052-XNH-1_m3em1p.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cç\“\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955398/quan-jeans-nu-QJN6052-XNH-2_dy9in4.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cé6\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955400/quan-jeans-nu-QJN6052-XNH-3_rtg8sv.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$céö\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955402/quan-jeans-nu-QJN6052-XNH-4_uw8bnv.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cé˛\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955404/quan-jeans-nu-QJN6052-XNH-5_qmmfky.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cèb\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955405/quan-jeans-nu-QJN6052-XNH-6_rrlq5a.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$cè\∆\”\ﬂÔïß\…\Ã\‰æ','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000','2025-01-16 07:54:41.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955407/quan-jeans-nu-QJN6052-XNH-7_iz6c7b.webp',_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ');
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
INSERT INTO `product_product_variants` VALUES (_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`8\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`ñ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`r\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\‡\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\ZN\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\Z\∆\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`>\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`¢\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\Z\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`í\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\0\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`n\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\‹\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`∏\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`ä\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\Ó\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$` \\\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$` ¿\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`!.\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$`!ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\"\n\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\"x\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\"\Ê\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`#T\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`#\¬\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`$0\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`$û\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`%\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`%z\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$`%\Ë\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`&V\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`&\Œ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\'<\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`\'™\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`(\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`(|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`(\Í\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`)X\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`)\∆\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$`*4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`*ò\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`+\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`+~\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`+\ˆ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`,d\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`,\»\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`-6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`-ö\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`.\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`.v\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`.\‰\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`/H\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`/∂\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$`0$\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`0à\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`0\ˆ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`1d\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`1\“\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`2@\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`2Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`3\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`3Ä\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`3\Ó\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$`4\\\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`4\ \”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`58\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`5¶\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`6x\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`6\Ê\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`7J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`7∏\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`8&\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`8î\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`8¯\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`9f\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`9\‘\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$`:B\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ',_binary '$`:¶\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ',_binary '$`;\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ',_binary '$`;Ç\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`;\Ê\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`<T\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`<\¬\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`=0\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`=î\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`>\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`>p\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`?\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`?à\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`?\ˆ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`@d\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`@\“\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`A@\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$`AÆ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`B\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`Bû\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`C\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`Cp\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`C\Ë\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`DV\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`D∫\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`E(\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`Eå\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`E˙\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`F^\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$`F\Ã\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`G0\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`Gû\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`H\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`Hz\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`H\Ë\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`I`\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`I\ƒ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`J2\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`J†\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`K\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`Kr\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$`K\‡\”\ﬂÔïß\…\Ã\‰æ');
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
INSERT INTO `product_variant` VALUES (_binary '$`|\”\ﬂÔïß\…\Ã\‰æ','VEM7003-DEN-M',0,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`8\”\ﬂÔïß\…\Ã\‰æ','VEM7003-DEN-L',0,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\”\ﬂÔïß\…\Ã\‰æ','VEM7003-DEN-XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`ñ\”\ﬂÔïß\…\Ã\‰æ','VEM7003-DEN-2XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\”\ﬂÔïß\…\Ã\‰æ','VEM7003-DEN-3XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`r\”\ﬂÔïß\…\Ã\‰æ','VEM7003-NAU-M',10,_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\‡\”\ﬂÔïß\…\Ã\‰æ','VEM7003-NAU-L',10,_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\ZN\”\ﬂÔïß\…\Ã\‰æ','VEM7003-NAU-XL',10,_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\Z\∆\”\ﬂÔïß\…\Ã\‰æ','VEM7003-NAU-2XL',10,_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`>\”\ﬂÔïß\…\Ã\‰æ','VEM7003-NAU-3XL',10,_binary '$Xå¨\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`¢\”\ﬂÔïß\…\Ã\‰æ','VEM7003-GHI-M',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\Z\”\ﬂÔïß\…\Ã\‰æ','VEM7003-GHI-L',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`í\”\ﬂÔïß\…\Ã\‰æ','VEM7003-GHI-XL',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\0\”\ﬂÔïß\…\Ã\‰æ','VEM7003-GHI-2XL',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`n\”\ﬂÔïß\…\Ã\‰æ','VEM7003-GHI-3XL',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\‹\”\ﬂÔïß\…\Ã\‰æ','AKM7007-GHI-M',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`J\”\ﬂÔïß\…\Ã\‰æ','AKM7007-GHI-L',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`∏\”\ﬂÔïß\…\Ã\‰æ','AKM7007-GHI-XL',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\”\ﬂÔïß\…\Ã\‰æ','AKM7007-GHI-2XL',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`ä\”\ﬂÔïß\…\Ã\‰æ','AKM7007-GHI-3XL',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\Ó\”\ﬂÔïß\…\Ã\‰æ','AKM7007-DEN-M',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$` \\\”\ﬂÔïß\…\Ã\‰æ','AKM7007-DEN-L',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$` ¿\”\ﬂÔïß\…\Ã\‰æ','AKM7007-DEN-XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`!.\”\ﬂÔïß\…\Ã\‰æ','AKM7007-DEN-2XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`!ú\”\ﬂÔïß\…\Ã\‰æ','AKM7007-DEN-3XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\"\n\”\ﬂÔïß\…\Ã\‰æ','PHM7009-XDE-M',10,_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\"x\”\ﬂÔïß\…\Ã\‰æ','PHM7009-XDE-L',10,_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\"\Ê\”\ﬂÔïß\…\Ã\‰æ','PHM7009-XDE-XL',10,_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`#T\”\ﬂÔïß\…\Ã\‰æ','PHM7009-XDE-2XL',10,_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`#\¬\”\ﬂÔïß\…\Ã\‰æ','PHM7009-XDE-3XL',10,_binary '$Xä\Í\”\ﬂÔïß\…\Ã\‰æ',_binary '$_B|\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`$0\”\ﬂÔïß\…\Ã\‰æ','PHM7009-REU-M',10,_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`$û\”\ﬂÔïß\…\Ã\‰æ','PHM7009-REU-L',10,_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`%\”\ﬂÔïß\…\Ã\‰æ','PHM7009-REU-XL',10,_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`%z\”\ﬂÔïß\…\Ã\‰æ','PHM7009-REU-2XL',10,_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`%\Ë\”\ﬂÔïß\…\Ã\‰æ','PHM7009-REU-3XL',10,_binary '$Xã≤\”\ﬂÔïß\…\Ã\‰æ',_binary '$_An\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`&V\”\ﬂÔïß\…\Ã\‰æ','STM7055-XAM-M',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`&\Œ\”\ﬂÔïß\…\Ã\‰æ','STM7055-XAM-L',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\'<\”\ﬂÔïß\…\Ã\‰æ','STM7055-XAM-XL',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`\'™\”\ﬂÔïß\…\Ã\‰æ','STM7055-XAM-2XL',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`(\”\ﬂÔïß\…\Ã\‰æ','STM7055-XAM-3XL',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`(|\”\ﬂÔïß\…\Ã\‰æ','STM7055-DEN-M',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`(\Í\”\ﬂÔïß\…\Ã\‰æ','STM7055-DEN-L',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`)X\”\ﬂÔïß\…\Ã\‰æ','STM7055-DEN-XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`)\∆\”\ﬂÔïß\…\Ã\‰æ','STM7055-DEN-2XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`*4\”\ﬂÔïß\…\Ã\‰æ','STM7055-DEN-3XL',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`*ò\”\ﬂÔïß\…\Ã\‰æ','QKM7005-GHI-28',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`+\”\ﬂÔïß\…\Ã\‰æ','QKM7005-GHI-29',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`+~\”\ﬂÔïß\…\Ã\‰æ','QKM7005-GHI-30',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`+\ˆ\”\ﬂÔïß\…\Ã\‰æ','QKM7005-GHI-31',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\‡\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`,d\”\ﬂÔïß\…\Ã\‰æ','QKM7005-GHI-32',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`,\»\”\ﬂÔïß\…\Ã\‰æ','QKM7005-GHI-33',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9D\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`-6\”\ﬂÔïß\…\Ã\‰æ','QKM7005-GHI-34',10,_binary '$Xå*\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9v\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`-ö\”\ﬂÔïß\…\Ã\‰æ','QKM7005-DEN-28',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`.\”\ﬂÔïß\…\Ã\‰æ','QKM7005-DEN-29',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`.v\”\ﬂÔïß\…\Ã\‰æ','QKM7005-DEN-30',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`.\‰\”\ﬂÔïß\…\Ã\‰æ','QKM7005-DEN-31',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\‡\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`/H\”\ﬂÔïß\…\Ã\‰æ','QKM7005-DEN-32',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`/∂\”\ﬂÔïß\…\Ã\‰æ','QKM7005-DEN-33',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9D\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`0$\”\ﬂÔïß\…\Ã\‰æ','QKM7005-DEN-34',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_C®\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9v\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`0à\”\ﬂÔïß\…\Ã\‰æ','QKM6017-XAM-29',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`0\ˆ\”\ﬂÔïß\…\Ã\‰æ','QKM6017-XAM-30',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`1d\”\ﬂÔïß\…\Ã\‰æ','QKM6017-XAM-31',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\‡\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`1\“\”\ﬂÔïß\…\Ã\‰æ','QKM6017-XAM-32',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`2@\”\ﬂÔïß\…\Ã\‰æ','QKM6017-XAM-33',10,_binary '$Xè\÷\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9D\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`2Æ\”\ﬂÔïß\…\Ã\‰æ','QKM6017-DEN-29',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`3\”\ﬂÔïß\…\Ã\‰æ','QKM6017-DEN-30',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`3Ä\”\ﬂÔïß\…\Ã\‰æ','QKM6017-DEN-31',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\‡\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`3\Ó\”\ﬂÔïß\…\Ã\‰æ','QKM6017-DEN-32',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`4\\\”\ﬂÔïß\…\Ã\‰æ','QKM6017-DEN-33',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D4\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9D\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`4\ \”\ﬂÔïß\…\Ã\‰æ','QJM6041-XAD-28',10,_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`58\”\ﬂÔïß\…\Ã\‰æ','QJM6041-XAD-29',10,_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`5¶\”\ﬂÔïß\…\Ã\‰æ','QJM6041-XAD-30',10,_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`6\”\ﬂÔïß\…\Ã\‰æ','QJM6041-XAD-31',10,_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\‡\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`6x\”\ﬂÔïß\…\Ã\‰æ','QJM6041-XAD-32',10,_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`6\Ê\”\ﬂÔïß\…\Ã\‰æ','QJM6041-XAD-33',10,_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9D\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`7J\”\ﬂÔïß\…\Ã\‰æ','QJM6041-XAD-34',10,_binary '$Xê\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9v\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`7∏\”\ﬂÔïß\…\Ã\‰æ','QJM6041-DEN-28',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`8&\”\ﬂÔïß\…\Ã\‰æ','QJM6041-DEN-29',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`8î\”\ﬂÔïß\…\Ã\‰æ','QJM6041-DEN-30',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`8¯\”\ﬂÔïß\…\Ã\‰æ','QJM6041-DEN-31',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\‡\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`9f\”\ﬂÔïß\…\Ã\‰æ','QJM6041-DEN-32',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`9\‘\”\ﬂÔïß\…\Ã\‰æ','QJM6041-DEN-33',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9D\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`:B\”\ﬂÔïß\…\Ã\‰æ','QJM6041-DEN-34',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_D¿\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z9v\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`:¶\”\ﬂÔïß\…\Ã\‰æ','AKN7001-BEE-S',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z4b\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`;\”\ﬂÔïß\…\Ã\‰æ','AKN7001-BEE-M',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`;Ç\”\ﬂÔïß\…\Ã\‰æ','AKN7001-BEE-L',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_EB\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`;\Ê\”\ﬂÔïß\…\Ã\‰æ','KNN7004-BEE-S',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z4b\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`<T\”\ﬂÔïß\…\Ã\‰æ','KNN7004-BEE-M',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`<\¬\”\ﬂÔïß\…\Ã\‰æ','KNN7004-BEE-L',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`=0\”\ﬂÔïß\…\Ã\‰æ','KNN7004-DEN-S',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z4b\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`=î\”\ﬂÔïß\…\Ã\‰æ','KNN7004-DEN-M',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`>\”\ﬂÔïß\…\Ã\‰æ','KNN7004-DEN-L',10,_binary '$Xé\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$_E\Œ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`>p\”\ﬂÔïß\…\Ã\‰æ','QSN7033-BEE-S',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z4b\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`?\”\ﬂÔïß\…\Ã\‰æ','QSN7033-BEE-M',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`?à\”\ﬂÔïß\…\Ã\‰æ','QSN7033-BEE-L',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`?\ˆ\”\ﬂÔïß\…\Ã\‰æ','QSN7033-BEE-XL',10,_binary '$Xç¶\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`@d\”\ﬂÔïß\…\Ã\‰æ','QSN7033-NAD-S',10,_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z4b\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`@\“\”\ﬂÔïß\…\Ã\‰æ','QSN7033-NAD-M',10,_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`A@\”\ﬂÔïß\…\Ã\‰æ','QSN7033-NAD-L',10,_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`AÆ\”\ﬂÔïß\…\Ã\‰æ','QSN7033-NAD-XL',10,_binary '$Xå\Ë\”\ﬂÔïß\…\Ã\‰æ',_binary '$_FZ\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`B\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XDM-25',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7¥\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`Bû\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XDM-26',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\Ê\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`C\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XDM-27',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`Cp\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XDM-28',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`C\Ë\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XDM-29',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`DV\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XDM-30',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`D∫\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XNH-25',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7¥\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`E(\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XNH-26',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\Ê\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`Eå\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XNH-27',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`E˙\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XNH-28',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`F^\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XNH-29',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`F\Ã\”\ﬂÔïß\…\Ã\‰æ','QJN7067-XNH-30',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_F\‹\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`G0\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XDM-25',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7¥\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`Gû\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XDM-26',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\Ê\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`H\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XDM-27',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`Hz\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XDM-28',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`H\Ë\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XDM-29',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`I`\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XDM-30',10,_binary '$Xä\"\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`I\ƒ\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XNH-25',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7¥\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`J2\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XNH-26',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z7\Ê\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`J†\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XNH-27',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`K\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XNH-28',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`Kr\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XNH-29',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ'),(_binary '$`K\‡\”\ﬂÔïß\…\Ã\‰æ','QJN6052-XNH-30',10,_binary '$XäÆ\”\ﬂÔïß\…\Ã\‰æ',_binary '$_G^\”\ﬂÔïß\…\Ã\‰æ',_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ');
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
INSERT INTO `role` VALUES (_binary 'C@*ÔøΩ%@ÔøΩÔøΩD','Role for regular admin with basic permissions','ADMIN'),(_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í','Role for regular users with basic permissions','USER');
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
INSERT INTO `role_permissions` VALUES (_binary 'C@*ÔøΩ%@ÔøΩÔøΩD',_binary '$ÔøΩÔøΩÔøΩLs\Ôø'),(_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í',_binary '$ÔøΩÔøΩÔøΩLs\Ôø'),(_binary 'C@*ÔøΩ%@ÔøΩÔøΩD',_binary 'u6ÓëΩÔøΩGÔøΩÔøΩ\Ô'),(_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í',_binary 'u6ÓëΩÔøΩGÔøΩÔøΩ\Ô'),(_binary 'C@*ÔøΩ%@ÔøΩÔøΩD',_binary '|BÔøΩÔøΩÔøΩÔøΩNw'),(_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í',_binary '|BÔøΩÔøΩÔøΩÔøΩNw'),(_binary 'C@*ÔøΩ%@ÔøΩÔøΩD',_binary '}1*ÔøΩÔøΩÔøΩ@ÔøΩ'),(_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í',_binary '}1*ÔøΩÔøΩÔøΩ@ÔøΩ'),(_binary 'C@*ÔøΩ%@ÔøΩÔøΩD',_binary 'ÔøΩÔøΩyÃûLÔøΩ\Ôø'),(_binary 'ÔøΩ9;ÔøΩÔøΩÔøΩG\Í',_binary 'ÔøΩÔøΩyÃûLÔøΩ\Ôø');
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
INSERT INTO `size` VALUES (_binary '$Z4b\”\ﬂÔïß\…\Ã\‰æ','S','S'),(_binary '$Z6\”\ﬂÔïß\…\Ã\‰æ','M','M'),(_binary '$Z6ú\”\ﬂÔïß\…\Ã\‰æ','L','L'),(_binary '$Z6\‚\”\ﬂÔïß\…\Ã\‰æ','XL','XL'),(_binary '$Z7\”\ﬂÔïß\…\Ã\‰æ','2XL','2XL'),(_binary '$Z7P\”\ﬂÔïß\…\Ã\‰æ','3XL','3XL'),(_binary '$Z7Ç\”\ﬂÔïß\…\Ã\‰æ','4XL','4XL'),(_binary '$Z7¥\”\ﬂÔïß\…\Ã\‰æ','25','25'),(_binary '$Z7\Ê\”\ﬂÔïß\…\Ã\‰æ','26','26'),(_binary '$Z8\”\ﬂÔïß\…\Ã\‰æ','27','27'),(_binary '$Z8J\”\ﬂÔïß\…\Ã\‰æ','28','28'),(_binary '$Z8|\”\ﬂÔïß\…\Ã\‰æ','29','29'),(_binary '$Z8Æ\”\ﬂÔïß\…\Ã\‰æ','30','30'),(_binary '$Z8\‡\”\ﬂÔïß\…\Ã\‰æ','31','31'),(_binary '$Z9\”\ﬂÔïß\…\Ã\‰æ','32','32'),(_binary '$Z9D\”\ﬂÔïß\…\Ã\‰æ','33','33'),(_binary '$Z9v\”\ﬂÔïß\…\Ã\‰æ','34','34'),(_binary '$Z9®\”\ﬂÔïß\…\Ã\‰æ','35','35'),(_binary '$Z9\⁄\”\ﬂÔïß\…\Ã\‰æ','36','36'),(_binary '$Z:\”\ﬂÔïß\…\Ã\‰æ','37','37'),(_binary '$Z:>\”\ﬂÔïß\…\Ã\‰æ','38','38'),(_binary '$Z:p\”\ﬂÔïß\…\Ã\‰æ','40','40'),(_binary '$Z:¢\”\ﬂÔïß\…\Ã\‰æ','42','42'),(_binary '$Z:\‘\”\ﬂÔïß\…\Ã\‰æ','43','43'),(_binary '$Z;\”\ﬂÔïß\…\Ã\‰æ','44','44'),(_binary '$Z;8\”\ﬂÔïß\…\Ã\‰æ','45','45');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_collection`
--

DROP TABLE IF EXISTS `sub_collection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_collection` (
  `id` binary(16) NOT NULL,
  `description` text,
  `name` varchar(255) NOT NULL,
  `collection_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK92s55lhuwgasyxn1345ow26u7` (`collection_id`),
  CONSTRAINT `FK92s55lhuwgasyxn1345ow26u7` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_collection`
--

LOCK TABLES `sub_collection` WRITE;
/*!40000 ALTER TABLE `sub_collection` DISABLE KEYS */;
INSERT INTO `sub_collection` VALUES (_binary '§£\ﬁ\–Omæâ\÷Wƒæd','M√¥ t·∫£ Sub 1','Polo ch·∫•t',_binary '»ï\‘8ò#AQïÜèûSŒî'),(_binary '\Êô}-üM˝´=¿°ü2','M√¥ t·∫£ Sub 2','√Åo gi√≥',_binary '»ï\‘8ò#AQïÜèûSŒî'),(_binary '\Z\‚wÿ•$M®\˜Ió\ÁÜ','M√¥ t·∫£ Sub 1','Sub 1',_binary '?˚\œ]Å±Eõ°EÆ±^DJn'),(_binary 'Ä\ƒz9¡Cøò5\‰?ôkòí','M√¥ t·∫£ Sub 2','Sub 2',_binary '?˚\œ]Å±Eõ°EÆ±^DJn');
/*!40000 ALTER TABLE `sub_collection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_collection_media`
--

DROP TABLE IF EXISTS `sub_collection_media`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_collection_media` (
  `id` binary(16) NOT NULL,
  `alt` varchar(255) DEFAULT NULL,
  `owner_type` enum('COLLECTION','SUB_COLLECTION') NOT NULL,
  `sort_order` int DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `type` enum('IMAGE','VIDEO') NOT NULL,
  `url` varchar(255) NOT NULL,
  `collection_id` binary(16) DEFAULT NULL,
  `sub_collection_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKk1scnlmo3xhgydw2peefxrwa5` (`collection_id`),
  KEY `FK5j4xa8ms86juiqky071thsrqi` (`sub_collection_id`),
  CONSTRAINT `FK5j4xa8ms86juiqky071thsrqi` FOREIGN KEY (`sub_collection_id`) REFERENCES `sub_collection` (`id`),
  CONSTRAINT `FKk1scnlmo3xhgydw2peefxrwa5` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_collection_media`
--

LOCK TABLES `sub_collection_media` WRITE;
/*!40000 ALTER TABLE `sub_collection_media` DISABLE KEYS */;
INSERT INTO `sub_collection_media` VALUES (_binary '%˚\„πHz¢ö[\Ú\"íc',NULL,'SUB_COLLECTION',0,NULL,'IMAGE','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739259706/collections/bst-thu-ong-2024/sub_collection_media_bst-thu-ong-2024_Sub%202_.png',NULL,_binary 'Ä\ƒz9¡Cøò5\‰?ôkòí'),(_binary 'C∂ãéE6¢∆°aM):Q',NULL,'SUB_COLLECTION',0,NULL,'IMAGE','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739264289/collections/bst-mua-xuan-2025/sub_collection_media_bst-mua-xuan-2025_Polo%20ch%E1%BA%A5t_.jpg',NULL,_binary '§£\ﬁ\–Omæâ\÷Wƒæd'),(_binary 'ZT˘inúI¢±qæl\Ìy3<',NULL,'COLLECTION',0,NULL,'IMAGE','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739259686/collections/bst-thu-ong-2024/collection_media_bst-thu-ong-2024_0.jpg',_binary '?˚\œ]Å±Eõ°EÆ±^DJn',NULL),(_binary 'ær\‚]\‡KA±\Ê˙3ü\Àú',NULL,'SUB_COLLECTION',0,NULL,'IMAGE','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739264292/collections/bst-mua-xuan-2025/sub_collection_media_bst-mua-xuan-2025_%C3%81o%20gi%C3%B3_.jpg',NULL,_binary '\Êô}-üM˝´=¿°ü2'),(_binary '\»R/≤\·≠EyàÆl÷ê\È+¡',NULL,'COLLECTION',0,NULL,'IMAGE','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739264287/collections/bst-mua-xuan-2025/collection_media_bst-mua-xuan-2025_0.jpg',_binary '»ï\‘8ò#AQïÜèûSŒî',NULL),(_binary '\Ÿ&\◊\ÂùE öL\»\0]ãà',NULL,'SUB_COLLECTION',0,NULL,'IMAGE','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1739259704/collections/bst-thu-ong-2024/sub_collection_media_bst-thu-ong-2024_Sub%201_.jpg',NULL,_binary '\Z\‚wÿ•$M®\˜Ió\ÁÜ');
/*!40000 ALTER TABLE `sub_collection_media` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_collection_products`
--

DROP TABLE IF EXISTS `sub_collection_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_collection_products` (
  `sub_collection_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  KEY `FKlcbt2xfb7ofheag3d53bpans6` (`product_id`),
  KEY `FKd8q1275nylhlorm3rvnht6hw2` (`sub_collection_id`),
  CONSTRAINT `FKd8q1275nylhlorm3rvnht6hw2` FOREIGN KEY (`sub_collection_id`) REFERENCES `sub_collection` (`id`),
  CONSTRAINT `FKlcbt2xfb7ofheag3d53bpans6` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_collection_products`
--

LOCK TABLES `sub_collection_products` WRITE;
/*!40000 ALTER TABLE `sub_collection_products` DISABLE KEYS */;
INSERT INTO `sub_collection_products` VALUES (_binary '\Z\‚wÿ•$M®\˜Ió\ÁÜ',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary 'Ä\ƒz9¡Cøò5\‰?ôkòí',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '§£\ﬁ\–Omæâ\÷Wƒæd',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ'),(_binary '\Êô}-üM˝´=¿°ü2',_binary '$_?4\”\ﬂÔïß\…\Ã\‰æ');
/*!40000 ALTER TABLE `sub_collection_products` ENABLE KEYS */;
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
INSERT INTO `user` VALUES (_binary '◊Ñ~?EÖéç h9\ÿ^','2025-01-17 13:51:30.627000','2025-01-17 13:51:30.627000','2025-02-05 15:59:33.224000',NULL,'loc@gmail.com',_binary '','Nguyen','Loc','{bcrypt}$2a$10$d1zTlyNPQixk7q6mBfOyAOI9KTWtBKVxiQ/FMyucxOz0tYYxJnQvO','0937468384','MANUAL',NULL,NULL),(_binary '\Ìº¸ó˚INAì t\ÚR\\∑=','2025-02-05 15:57:05.298000','2025-02-05 15:57:05.298000','2025-02-05 15:58:06.709000',NULL,'nguyen@gmail.com',_binary '','Nguyen','Van','{bcrypt}$2a$10$joWG.mtyza7W/d6MVDUcce2dmfIDTFhtxRaNQxP3flq8Gd599YPWa','0928737378','MANUAL',NULL,NULL);
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

-- Dump completed on 2025-02-21 17:31:39
