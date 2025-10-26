-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: trendista
-- ------------------------------------------------------
-- Server version	8.0.44

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
-- Table structure for table `address`
--

DROP TABLE IF EXISTS `address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `address` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `city` varchar(255) NOT NULL,
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
  `role_id` binary(16) NOT NULL,
  KEY `FK711tgkb30bg1wa40uqe39qxag` (`role_id`),
  KEY `FKhnndtpgongj5lyfux2tdv8ip` (`user_id`),
  CONSTRAINT `FK711tgkb30bg1wa40uqe39qxag` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`),
  CONSTRAINT `FKhnndtpgongj5lyfux2tdv8ip` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_authority`
--

LOCK TABLES `auth_user_authority` WRITE;
/*!40000 ALTER TABLE `auth_user_authority` DISABLE KEYS */;
INSERT INTO `auth_user_authority` VALUES (_binary '¬\ÏÙŸıA„J“\áø¹?',_binary 'SÆİ·\ÊF	_…zC½3i'),(_binary '¬\ÏÙŸıA„J“\áø¹?',_binary 'gU¼¹C\í¢ŒhŸ(ˆ9'),(_binary '¥øh|¨L\Z˜r¡c¨',_binary 'SÆİ·\ÊF	_…zC½3i'),(_binary '@­z]R@w€½şqR{\å',_binary 'SÆİ·\ÊF	_…zC½3i');
/*!40000 ALTER TABLE `auth_user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banner_images`
--

DROP TABLE IF EXISTS `banner_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banner_images` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) DEFAULT NULL,
  `display_order` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `banner_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK633x0a6l0tvcjj7xoeaivj92t` (`banner_id`),
  CONSTRAINT `FK633x0a6l0tvcjj7xoeaivj92t` FOREIGN KEY (`banner_id`) REFERENCES `banners` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banner_images`
--

LOCK TABLES `banner_images` WRITE;
/*!40000 ALTER TABLE `banner_images` DISABLE KEYS */;
INSERT INTO `banner_images` VALUES (1,'Discount up to 50% off!',1,'https://res.cloudinary.com/trendistashop/image/upload/v1234567890/banners/black-friday-1.jpg','',1),(2,'Discount up to 50% off!',1,'https://res.cloudinary.com/trendistashop/image/upload/v1234567890/banners/black-friday-1.jpg',NULL,2);
/*!40000 ALTER TABLE `banner_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `banners`
--

DROP TABLE IF EXISTS `banners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `banners` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `event` varchar(255) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `type` enum('CAROUSEL','CATEGORY','COUNTDOWN','DISCOUNT','FEATURED','HERO','INFO','PROMOTIONAL') DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
INSERT INTO `banners` VALUES (1,'2025-10-26 12:59:00.140000',NULL,NULL,'Black Friday Sale',_binary '','HERO'),(2,'2025-10-26 12:59:37.417000',NULL,NULL,'19/11 For Man',_binary '','HERO');
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
INSERT INTO `cart` VALUES (_binary '?¹;NQDx ^\å\å^^','2025-10-26 11:31:49.289000',NULL,'2025-10-26 11:31:49.314000',0.00,_binary '¬\ÏÙŸıA„J“\áø¹?'),(_binary 'oZ‡\Ú\ÖB¼\Ìˆo›¬õ','2025-10-26 13:00:24.730000',NULL,NULL,0.00,NULL),(_binary '‰©Ö©¦C©¢]‡\í®¶','2025-10-26 11:30:30.512000',NULL,NULL,0.00,NULL),(_binary 'ª³Á\Ë\\·M’´X˜zYY','2025-10-26 13:01:13.898000',NULL,'2025-10-26 13:01:13.902000',0.00,_binary '¥øh|¨L\Z˜r¡c¨'),(_binary '¼\í0®fG#©0¸¸Á\â@L','2025-10-26 13:06:45.740000',NULL,NULL,0.00,NULL);
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `cart_item_quantity` int DEFAULT NULL,
  `product_image_id` binary(16) DEFAULT NULL,
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `index_num` int DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) NOT NULL,
  `gender_id` binary(16) NOT NULL,
  `parent_id` binary(16) DEFAULT NULL,
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
INSERT INTO `category` VALUES (_binary '\æú_Í²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Khi nháº¯c Ä‘áº¿n tá»§ Ä‘á»“ cá»§a cÃ¡c chá»‹ em, báº¡n sáº½ hoÃ n toÃ n bá»‹ thu hÃºt bá»Ÿi nhá»¯ng chiá»…cÂ Ã¡o ná»¯ Ä‘ang Ä‘Æ°á»£c treo ngay ngáº¯n. Thá»i trang Ã¡o ná»¯ kiá»ƒuÂ luÃ´n Ä‘a dáº¡ng vÃ  phÃ¡t triá»ƒn khÃ´ng ngá»«ng vá»›i Ä‘a dáº¡ng thiáº¿t káº¿. Váº­y nhá»¯ng kiá»ƒu Ã¡o ná»¯ Ä‘áº¹p nÃ o Ä‘ang Ä‘Æ°á»£c Ä‘Ã´ng Ä‘áº£o phÃ¡i ná»¯ quan tÃ¢m? HÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu ngay nhÃ©!',NULL,0,'Ão ná»¯','ao-nu',_binary '\æ½²gğ“VŠµ£§',NULL),(_binary '\æúk;²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n lÃ  má»™t trong nhá»¯ng trang phá»¥c khÃ´ng thá»ƒ thiáº¿u cá»§a phÃ¡i Ä‘áº¹p. Quáº§n ná»¯ Ä‘Æ°á»£c chia lÃ m nhiá»u loáº¡i nhÆ° quáº§n jeans, quáº§n ná»‰, quáº§n kaki, quáº§n thá»ƒ thao... TÃ¹y thuá»™c vÃ o sá»Ÿ thÃ­ch hay má»¥c Ä‘Ã­ch cá»§a báº¡n mÃ  báº¡n cÃ³ thá»ƒ lá»±a chá»n chiáº¿c quáº§n phÃ¹ há»£p nháº¥t vá»›i mÃ¬nh. HÃ£y cÃ¹ng TRENDISTA Ä‘iá»ƒm láº¡i nhá»¯ng loáº¡i quáº§n ná»•i báº­t',NULL,1,'Quáº§n ná»¯','quan-nu',_binary '\æ½²gğ“VŠµ£§',NULL),(_binary '\æúqo²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','PhÃ¡i Ä‘áº¹p luÃ´n mong muá»‘n báº£n thÃ¢n Ä‘Æ°á»£c trÆ°ng diá»‡n vÃ  xinh Ä‘áº¹p hÆ¡n má»—i ngÃ y khÃ´ng chá»‰ khi ra ngoÃ i mÃ  ngay cáº£ á»Ÿ nhÃ . Nhu cáº§u Ä‘Æ°á»£c máº·c nhá»¯ng bá»™ Ä‘á»“ ná»¯ há»£p má»‘t vÃ  thoáº£i mÃ¡i chÃ­nh lÃ  nhu cáº§u chÃ­nh Ä‘Ã¡ng.\n\n\n\nCÃ¡c chá»‹ em khi máº·c Ä‘á»“ bá»™ sáº½ hoÃ n toÃ n tiáº¿t kiá»‡m Ä‘Æ°á»£c tá»‘i Ä‘a thá»i gian bá»Ÿi chÃºng khÃ´ng cáº§n pháº£i káº¿t',NULL,2,'Äá»“ bá»™ ná»¯','do-bo-nu',_binary '\æ½²gğ“VŠµ£§',NULL),(_binary '\æúrı²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Thá»i trang Ä‘á»“ máº·c trong ná»¯ TRENDISTA vá»›i kiá»ƒu dÃ¡ng gá»£i cáº£m, cháº¥t liá»‡u cao cáº¥p, nhiá»u Æ°u Ä‘Ã£i háº¥p dáº«n Ä‘ang chá» báº¡n khÃ¡m phÃ¡. Mua Ä‘á»“ ná»™i y chÆ°a bao giá» lÃ  Ä‘á»… dÃ ng Ä‘áº¿n váº­y.\n\nCÃ¹ng Ä‘iá»ƒm danh má»™t sá»‘ kiá»ƒu máº«u Ä‘á»“ máº·c trong cho ná»¯ mÃ  báº¥t ká»ƒ nÃ ng nÃ o cÅ©ng cáº§n nhÃ©.\n\n1. Äá»“ máº·c trong cho ná»¯ khÃ´ng thá»ƒ thiáº¿u - Quáº§n lÃ³t',NULL,3,'Äá»“ máº·c trong ná»¯','do-mac-trong-nu',_binary '\æ½²gğ“VŠµ£§',NULL),(_binary '\æúvN²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,4,'Äá»“ thá»ƒ thao ná»¯','do-the-thao-nu',_binary '\æ½²gğ“VŠµ£§',NULL),(_binary '\æúx¬²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,5,'Äáº§m vÃ  chÃ¢n vÃ¡y ná»¯','dam-va-chan-vay-nu',_binary '\æ½²gğ“VŠµ£§',NULL),(_binary '\æúz\ç²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Táº§m quan trá»ng cá»§a phá»¥ kiá»‡n Ä‘á»‘i vá»›i thá»i trang ná»¯\n\nPhá»¥ kiá»‡n thá»i trang ná»¯ khÃ´ng chá»‰ lÃ  nhá»¯ng mÃ³n Ä‘á»“ Ä‘i kÃ¨m, dÃ¹ng thÃªm Ä‘á»ƒ Ä‘á»±ng Ä‘á»“ mÃ  Ä‘Ã³ cÃ²n lÃ  nhá»¯ng mÃ³n Ä‘á»“ giÃºp nÃ¢ng táº§m gu Äƒn máº·c cá»§a báº¡n.\n\nMá»™t bá»™ trang phá»¥c trÃ´ng ráº¥t bÃ¬nh thÆ°á»ng nhÆ°ng náº¿u báº¡n biáº¿t mix thÃªm cÃ¡c phá»¥ kiá»‡n Ä‘i kÃ¨m nhÆ° tÃºi xÃ¡ch, hoa tai',NULL,6,'Phá»¥ kiá»‡n ná»¯','phu-kien-nu',_binary '\æ½²gğ“VŠµ£§',NULL),(_binary '\æú|A²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão nam TRENDISTA chÃ­nh hÃ£ng, cháº¥t liá»‡u cao cáº¥p, Ä‘a dáº¡ng máº«u mÃ£, kiá»ƒu dÃ¡ng, cháº¥t liá»‡u tá»‘t,... Mua Ã¡o nam chÃ­nh hÃ£ng TRENDISTA ngay hÃ´m nay!',NULL,0,'Ão nam','ao-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',NULL),(_binary '\æú}4²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Quáº§n nam','quan-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',NULL),(_binary '\æú}ò²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,2,'Äá»“ bá»™ nam','do-bo-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',NULL),(_binary '\æú~¦²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,3,'Äá»“ máº·c trong nam','do-mac-trong-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',NULL),(_binary '\æúk²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,4,'Äá»“ thá»ƒ thao nam','do-the-thao-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',NULL),(_binary '\æú€\"²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,5,'Phá»¥ kiá»‡n nam','phu-kien-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',NULL),(_binary '\æú€Ö²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão tráº» em TRENDISTA - CÃ¡c máº«u Ã¡o tráº» em Ä‘áº¹p, cao cáº¥p nháº¥t. CÃ¡c máº«u Ã¡o thun, Ã¡o phÃ´ng cho bÃ©, Ã¡o polo tráº» em cháº¥t tá»‘t.',NULL,0,'Ão Tráº» em','ao-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',NULL),(_binary '\æú±²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','NgÃ y nay sá»± Ä‘a dáº¡ng khÃ´ng cÃ²n giá»›i háº¡n trong cÃ¡c sáº£n pháº©m thá»i trang ngÆ°á»i lá»›n mÃ  thá»i trang tráº» em cÅ©ng váº­y. Quáº§n Ã¡o tráº» em Ä‘áº·c biá»‡t quáº§n tráº» em cÅ©ng ngÃ y cÃ ng Ä‘Æ°á»£c chÃº trá»ng trong thiáº¿t káº¿ kiá»ƒu dÃ¡ng Ä‘a dáº¡ng, tinh táº¿ vá»›i cháº¥t liá»‡u,... cho Ä‘áº¿n mÃ u sáº¯c cho cÃ¡c bÃ©.',NULL,1,'Quáº§n Tráº» em','quan-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',NULL),(_binary '\æú„6²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Äá»“ bá»™ tráº» em hiá»‡n nay ráº¥t phong phÃº,Â Ä‘a dáº¡ng, báº¯t máº¯t vÃ  táº¡o Ä‘Æ°á»£c sá»± hÃ o há»©ng, tÃ² mÃ² choÂ cÃ¡c bÃ© Ä‘ang trong Ä‘á»™ tuá»•iÂ phÃ¡t triá»ƒn.\n\nHÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu vá» bá»™ Ä‘á»“Â tráº» em, cÃ¡c lá»±a chá»n Ä‘á»“ bá»™ tráº» em cho bÃ© theo tá»«ng Ä‘á»™ tuá»•i, lÆ°u Ã½ khi chá»n Ä‘á»“ bá»™ cho tráº» em cÅ©ng nhÆ° Ä‘iá»ƒm qua má»™t vÃ i bá»™ Ä‘á»“ tráº» em báº¯t trend',NULL,2,'Äá»“ bá»™ Tráº» em','do-bo-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',NULL),(_binary '\æúˆ5²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,3,'Äá»“ máº·c trong Tráº» em','do-mac-trong-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',NULL),(_binary '\æú‹²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,4,'Äá»“ thá»ƒ thao Tráº» em','do-the-thao-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',NULL),(_binary '\æúŒ+²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','NÃ³i Ä‘áº¿n thá»i trang tráº» em khÃ´ng thá»ƒ khÃ´ng ká»ƒ Ä‘áº¿n nhá»¯ng máº«u vÃ¡y tráº» em Ä‘áº¹p. Nhá»¯ng chiáº¿c vÃ¡y Ä‘áº§m tráº» em cao cáº¥p khÃ´ng chá»‰ Ä‘Æ°á»£c cÃ¡c bÃ© gÃ¡i yÃªu thÃ­ch vÃ  lá»±a chá»n khi Ä‘i mua sáº¯m cÃ¹ng bá»‘ máº¹. TRENDISTA mÃ¡ch báº¡n nhá»¯ng kiá»ƒu vÃ¡y cÆ¡ báº£n vÃ  nhá»¯ng tip hÆ°á»›ng dáº«n mix trang phá»¥c Ä‘á»ƒ máº¹ trá»Ÿ thÃ nh chuyÃªn gia',NULL,5,'Äáº§m vÃ  chÃ¢n vÃ¡y bÃ© gÃ¡i','dam-va-chan-vay-be-gai',_binary '\æ½\'¬²gğ“VŠµ£§',NULL),(_binary '\æú²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Phá»¥ kiá»‡n thá»i trang tráº» em TRENDISTA nhÆ° mÅ©/ nÃ³n cho bÃ©, táº¥t, kháº©u trang tráº» em, giÃ y dÃ©p tráº» em...Ä‘a dáº¡ng máº«u mÃ£, mÃ u sáº¯c ngá»™ nghÄ©nh Ä‘Ã¡ng yÃªuÂ táº¡o phong cÃ¡ch má»›i máº» cho bÃ©. Mua ngay táº¡i TRENDISTA.VN Ä‘á»ƒ nháº­n hÃ ng ngÃ n Æ°u Ä‘Ã£i háº¥p dáº«n.',NULL,6,'Phá»¥ kiá»‡n Tráº» em','phu-kien-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',NULL),(_binary '\ç:¿ö²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Ão vest ná»¯','ao-vest-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\Ç²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão khoÃ¡c ná»¯Â lÃ  trang phá»¥c ráº¥t cáº§n thiáº¿t trong nhá»¯ng ngÃ y ÄÃ´ng láº¡nh giÃ¡. HÃ£y cÃ¹ng TRENDISTA khÃ¡m phÃ¡ ngay má»™t sá»‘ máº«u Ã¡o khoÃ¡c ná»¯ , Ã¡o giÃ³ ná»¯ Ä‘áº¹p Ä‘ang â€œÄ‘Æ°á»£c lÃ²ngâ€ táº¥t cáº£ cÃ¡c khÃ¡ch hÃ ng ná»¯ nhÃ©!\n\n\n\nÃo khoÃ¡c ná»¯ form rá»™ng, Ã¡o khoÃ¡c ná»¯ hÃ n quá»‘c lÃ  má»™t trong nhá»¯ng item khÃ´ng thá»ƒ thiáº¿u',NULL,1,'Ão khoÃ¡c ná»¯','ao-khoac-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\È²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','MÃ¹a Ä‘Ã´ng Ä‘Ã£ Ä‘áº¿n, vÃ  khi nhiá»‡t Ä‘á»™ giáº£m, lá»±a chá»n trang phá»¥c cá»§a chÃºng ta trá»Ÿ nÃªn quan trá»ng Ä‘á»ƒ giá»¯ áº¥m vÃ  phong cÃ¡ch. Trong tháº¿ giá»›i thá»i trang Ä‘Ã´ng, Ão Phao Ná»¯Â ná»•i báº­t nhÆ° má»™t trang phá»¥c Ä‘a dáº¡ng vÃ  quan trá»ng.',NULL,2,'Ão phao ná»¯','ao-phao-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:È¸²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,3,'Ão giÃ³ ná»¯','ao-gio-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\ÉA²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão chá»‘ng náº¯ng ná»¯ TRENDISTA Ä‘áº·t chá»‰ sá»‘ UPF 50+Â ngÄƒn chÄƒn tiaÂ UVA vÃ  UVBÂ lÃªn tá»›i 98%, cháº¥t liá»‡u láº¡i co giÃ£n, mÃ¡t máº» khi máº·c lÃ  lá»±a chá»n hoÃ n háº£o Ä‘á»ƒ chá»‹ em báº£o vá»‡ lÃ n da cá»§a mÃ¬nh trong nhá»¯ng ngÃ y hÃ¨ náº¯ng gáº¯t.\n\nTáº§m quan trá»ng cá»§a Ã¡o chá»‘ng náº¯ng ná»¯\n\nÃo chá»‘ng náº¯ng ná»¯ lÃ  má»™t trong nhá»¯ng váº­t dá»¥ng khÃ´ng thá»ƒ thiáº¿u.',NULL,4,'Ão chá»‘ng náº¯ng ná»¯','ao-chong-nang-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\Ë²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Mua Ã¡o sweater ná»¯ form rá»™ng online nhanh chÃ³ng vÃ  thuáº­n tiá»‡n Freeship toÃ n quá»‘c cÃ¹ng chÃ­nh sÃ¡ch Ä‘á»•i tráº£ dá»… dÃ ng. Sá»Ÿ há»¯u ngay Ã¡o ná»‰ ná»¯ cÃ¹ng nhiá»u Æ°u Ä‘Ã£i táº¡i Ä‘Ã¢y.\n\n1. Ão sweater ná»¯ local brand\n\nVá»›i Ä‘á»™ phá»• biáº¿n rá»™ng rÃ£i cá»§a sweater thÃ¬ tá»›i thá»i Ä‘iá»ƒm hiá»‡n táº¡i, sweaters local brand Ä‘ang trá»Ÿ nÃªn thá»‹nh hÃ nh',NULL,5,'Ão hoodie - Ão ná»‰ ná»¯','ao-hoodie-ao-ni-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\ËÅ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Má»—i khi Ä‘Ã´ng Ä‘áº¿n, Ã¡o len ná»¯ láº¡i lÃ m khuáº¥y Ä‘áº£o giá»›i má»™ Ä‘iá»‡u vá»›i kiá»ƒu thiáº¿t káº¿ vÃ  hoáº¡ tiáº¿t Ä‘a dáº¡ng cá»§a mÃ¬nh. Trong pháº§n ná»™i dung dÆ°á»›i Ä‘Ã¢y, thá»i trang TRENDISTA sáº½ báº­t mÃ­ vá»›i cÃ¡c báº¡n nhá»¯ngÂ máº«u Ã¡o len ná»¯ Ä‘áº¹p vÃ  trendy nháº¥t hiá»‡n nay.',NULL,6,'Ão len ná»¯','ao-len-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\ÌS²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Mua Ã¡o thun ná»¯ Ä‘áº¹p giÃ¡ ráº», Ã¡p phÃ´ng in hÃ¬nh nÄƒng Ä‘á»™ng, thiáº¿t káº¿ tráº» trung, cháº¥t liá»‡u cao cáº¥p co giÃ£n 4 chiá»u, thoáº£i mÃ¡i trong tá»«ng cá»­ Ä‘á»™ng.',NULL,7,'Ão thun ná»¯','ao-thun-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\ÌÒ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','BST 100 máº«u Ã¡o sÆ¡ mi ná»¯ Ä‘áº¹p, cÃ¡c kiá»ƒu Ã¡o sÆ¡ mi Ä‘áº¹p, tráº» trung chÃ­nh hÃ£ng TRENDISTA. Mua Ã¡o sÆ¡ mi ná»¯ cao cáº¥p, giÃ¡ tá»‘t 2024 táº¡i Ä‘Ã¢y.',NULL,8,'Ão sÆ¡ mi ná»¯','ao-so-mi-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:\ÏÏ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão polo ná»¯ chÃ­nh hÃ£ng TRENDISTA - BST Ã¡o polo ná»¯ cao cáº¥p, tráº» trung, Ã¡o thun ná»¯ cÃ³ cá»• Ä‘áº¹p, giÃ¡ tá»‘t nháº¥t 2024. Æ¯u Ä‘Ã£i lá»›n, freeship toÃ n quá»‘c. Mua ngay!',NULL,9,'Ão polo ná»¯','ao-polo-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æú_Í²gğ“VŠµ£§'),(_binary '\ç:Ğ„²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Mua quáº§n thá»ƒ thao cho ná»¯ nÄƒng Ä‘á»™ng, thá»i trang chÃ­nh hÃ£ng, máº·c lÃ  mÃ¡t, giÃ¡  tá»‘t, ship toÃ n quá»‘c, miá»…n phÃ­ Ä‘á»•i tráº£ 15 ngÃ y Ä‘áº§u.',NULL,0,'Quáº§n dÃ i ná»¯','quan-dai-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúk;²gğ“VŠµ£§'),(_binary '\ç:\Ñ\Z²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Má»™t trong nhá»¯ng loÃ i quáº§n Ä‘Æ°á»£c Æ°a chuá»™ng nháº¥t hiá»‡n nay cháº¯c cháº¯c khÃ´ng thá»ƒ khÃ´ng nháº¯c Ä‘áº¿n quáº§n ná»‰ ná»¯. Quáº§n ná»‰ ná»¯ mang dÃ¡ng thá»ƒ thao láº¡i khá»e khoáº¯n phÃ¹ há»£p vá»›i phong cÃ¡ch street-style. Chiáº¿c quáº§n nÃ yÂ cÃ³ thá»ƒ máº·c trong ráº¥t nhiá»u hoÃ n cáº£nh khÃ¡c nhau nhÆ° Ä‘i chÆ¡i, Ä‘i há»c, Ä‘i lÃ m á»Ÿ nhá»¯ng nÆ¡i khÃ´ng bá»‹ gÃ² bÃ³',NULL,1,'Quáº§n ná»‰ ná»¯','quan-ni-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúk;²gğ“VŠµ£§'),(_binary '\ç:Ò½²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Mua quáº§n kaki ná»¯, quáº§n khaki chÃ­nh hÃ£ng giÃ¡ tá»‘t, Ä‘áº§y Ä‘á»§ kiá»ƒu dÃ¡ng. Mua quáº§n kaki ná»¯ táº¡i TRENDISTA vá»›i Æ°u Ä‘Ã£i, giao hÃ ng miá»…n phÃ­, Ä‘á»•i tráº£ dá»… dÃ ng.',NULL,2,'Quáº§n kaki ná»¯','quan-kaki-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúk;²gğ“VŠµ£§'),(_binary '\ç:\Ós²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n short ná»¯ lÃ  sá»± lá»±a chá»n Ä‘a phong cÃ¡ch, Ä‘a cÃ¡ tÃ­nh cho má»i cÃ´ nÃ ng. ÄÆ°á»£c Æ°a chuá»™ng trong mÃ¹a hÃ¨, mÃ¹a thu vá»›i Æ°u Ä‘iá»ƒm táº¡o Ä‘Æ°á»£c sá»± thoáº£i mÃ¡i, nÄƒng Ä‘á»™ng, vÃ  cá»±c thá»i trang.',NULL,3,'Quáº§n short ná»¯','quan-short-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúk;²gğ“VŠµ£§'),(_binary '\ç:\Óı²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,4,'Quáº§n jeans ná»¯','quan-jeans-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúk;²gğ“VŠµ£§'),(_binary '\ç:\Ôw²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n TÃ¢y ná»¯ lÃ  máº«u quáº§n â€œhuyá»n thoáº¡iâ€, báº¡n cÃ³ thá»ƒ hÃ´ biáº¿n tá»« cÃ´ nÃ ng cÃ´ng sá»Ÿ trang nhÃ£ hay cÃ¡ tÃ­nh dáº¡o phá»‘ theo nhiá»u style khÃ¡c nhau. Thiáº¿t káº¿ hÆ°á»›ng Ä‘áº¿n sá»± Ä‘Æ¡n giáº£n, tiá»‡n lá»£i, form chuáº©n, phÃ¹ há»£p trong má»i hoÃ n cáº£nh.\n\n\n\nQuáº§n Ã‚u ná»¯ lÃ  má»™t trong nhá»¯ng items khÃ´ng cÃ²n xa láº¡ vá»›i cÃ¡c quÃ½ cÃ´. Sá»± thoáº£i mÃ¡',NULL,5,'Quáº§n Ã¢u ná»¯','quan-au-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúk;²gğ“VŠµ£§'),(_binary '\ç:\Õ\0²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Äá»“ bá»™ dÃ i tay ná»¯','do-bo-dai-tay-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúqo²gğ“VŠµ£§'),(_binary '\ç:Õ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Äá»“ bá»™ ngáº¯n tay ná»¯','do-bo-ngan-tay-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúqo²gğ“VŠµ£§'),(_binary '\ç:\Õ÷²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Ão bra ná»¯','ao-bra-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúrı²gğ“VŠµ£§'),(_binary '\ç:Ø²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Quáº§n lÃ³t ná»¯','quan-lot-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúrı²gğ“VŠµ£§'),(_binary '\ç:\ÙC²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Mua Ã¡o hai dÃ¢y, Ã¡o ba lá»— ná»¯ giÃ¡ tá»‘t, thiáº¿t káº¿ tráº» trung tÃ´n dÃ¡ng, thu hÃºt má»i Ã¡nh nhÃ¬n. Mua ngay Ä‘á»ƒ nháº­n Æ°u Ä‘Ã£i giáº£m 50%',NULL,2,'Ão ba lá»— - Ão hai dÃ¢y ná»¯','ao-ba-lo-ao-hai-day-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúrı²gğ“VŠµ£§'),(_binary '\ç:\ÙË²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão giá»¯ nhiá»‡t ná»¯ Ä‘Æ°á»£c xem nhÆ° lÃ  má»™t mÃ³n Ä‘á»“ â€œbáº£o bá»‘iâ€ vÃ´ cÃ¹ng cáº§n thiáº¿t dÃ nh cho cÃ¡c cÃ´ nÃ ng trong mÃ¹a Ä‘Ã´ng láº¡nh giÃ¡. KhÃ´ng nhá»¯ng giÃºp giá»¯ áº¥m cÆ¡ thá»ƒ hiá»‡u quáº£ cÃ²n ráº¥t dá»… phá»‘i Ä‘á»“ vá»›i nhá»¯ng trang phá»¥c khÃ¡c nhau mÃ  váº«n Ä‘áº£m báº£o Ä‘Æ°á»£c thá»i trang cÅ©ng nhÆ° cÃ¡ tÃ­nh cá»§a ngÆ°á»i máº·c.',NULL,3,'Ão giá»¯ nhiá»‡t ná»¯','ao-giu-nhiet-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúrı²gğ“VŠµ£§'),(_binary '\ç:\ÛG²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Quáº§n thá»ƒ thao ná»¯','quan-the-thao-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúvN²gğ“VŠµ£§'),(_binary '\ç:\ÜA²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Ão polo thá»ƒ thao ná»¯','ao-polo-the-thao-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúvN²gğ“VŠµ£§'),(_binary '\ç:\İ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,2,'Ão thun thá»ƒ thao ná»¯','ao-thun-the-thao-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúvN²gğ“VŠµ£§'),(_binary '\ç:\İ\é²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,3,'Bá»™ thá»ƒ thao ná»¯','bo-the-thao-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúvN²gğ“VŠµ£§'),(_binary '\ç:Şº²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Äáº§m ná»¯ - vÃ¡y liá»n thÃ¢n lÃ  item thá»i trang Ä‘em láº¡i nÃ©t dá»‹u dÃ ng, ná»¯ tÃ­nh cho phÃ¡i Ä‘áº¹p. Trang phá»¥c nÃ y thÆ°á»ng Ä‘Æ°á»£c thiáº¿t káº¿ vá»›i cháº¥t váº£i má»ng, mÃ¡t giÃºp chá»‹ em thoáº£i mÃ¡i váº­n Ä‘á»™ng. Váº­y, báº¡n Ä‘Ã£ biáº¿t máº«u vÃ¡y ná»¯, Ä‘áº§m body ná»¯Â nÃ oÂ Ä‘ang thá»‹nh hÃ nh hiá»‡n nay chÆ°a? Náº¿u chÆ°a thÃ¬ Ä‘á»«ng bá» qua pháº§n gá»£i Ã½ dÆ°á»›i Ä‘Ã¢y nha',NULL,0,'Äáº§m ná»¯','dam-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúx¬²gğ“VŠµ£§'),(_binary '\ç:\à²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','ChÃ¢n vÃ¡y ná»¯, chÃ¢n vÃ¡y chá»¯ A, chÃ¢n vÃ¡y cÃ´ng sá»­ vá»›i nhiá»u thiáº¿t káº¿ tinh táº¿ Ä‘áº£m báº£o sáº½ lÃ m báº¡n hÃ i lÃ²ng. Xem ngay Ä‘á»ƒ mua ChÃ¢n vÃ¡y TRENDISTA chÃ­nh hÃ£ng',NULL,1,'ChÃ¢n vÃ¡y ná»¯','chan-vay-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúx¬²gğ“VŠµ£§'),(_binary '\ç:\à–²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Táº¥t ná»¯','tat-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúz\ç²gğ“VŠµ£§'),(_binary '\ç:\á²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'TÃºi xÃ¡ch ná»¯','tui-xach-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúz\ç²gğ“VŠµ£§'),(_binary '\ç:á²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Bá»™ sÆ°u táº­p giÃ y cá»§a cÃ¡c cÃ´ gÃ¡i cÃ³ gu cháº¯c cháº¯n sáº½ khÃ´ng thá»ƒ thiáº¿u nhá»¯ng Ä‘Ã´i giÃ y cao gÃ³t. Chá»‹ em thanh lá»‹ch vá»›i má»™t outfit hoÃ n háº£o bÆ°á»›c Ä‘i tá»± tin trÃªn má»™t Ä‘Ã´i giÃ y cao gÃ³t sáº½ thu hÃºt Ä‘Æ°á»£c Ã¡nh nhÃ¬n cá»§a ráº¥t nhiá»u ngÆ°á»i. Váº­y, nhá»¯ng Ä‘Ã´i giÃ y cao gÃ³t nÃ o Ä‘ang Ä‘Æ°á»£c Æ°a chuá»™ng nháº¥t hiá»‡n nay? CÃ¹ng TRNEDISTA khÃ¡m phÃ¡',NULL,2,'GiÃ y ná»¯','giay-nu',_binary '\æ½²gğ“VŠµ£§',_binary '\æúz\ç²gğ“VŠµ£§'),(_binary '\ç:ã³²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','ThiÃªn Ä‘Æ°á»ng phá»¥ kiá»‡n thá»i trang ná»¯ cao cáº¥p, thiáº¿t káº¿ Ä‘áº¹p, khÃ´ng thá»ƒ thiáº¿u cá»§a cÃ´ nÃ ng hiá»‡n Ä‘áº¡i â€“ phá»¥ kiá»‡n thá»i trang ná»¯ Ä‘Æ¡n giáº£n mÃ  khÃ´ng kÃ©m pháº§n tinh táº¿.',NULL,3,'Phá»¥ kiá»‡n ná»¯ khÃ¡c','phu-kien-nu-khac',_binary '\æ½²gğ“VŠµ£§',_binary '\æúz\ç²gğ“VŠµ£§'),(_binary '\ç:\äÂ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão vest nam lÃ  má»™t sáº£n pháº©m thá»i trang táº¡o Ä‘Æ°á»£c ráº¥t nhiá»u sá»± sang trá»ng, khÃ¡c biá»‡t so vá»›i nhiá»u mÃ³n Ä‘á»“ thá»i trang khÃ¡c.\n\n\n\nHÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu Ã¡o vest nam lÃ  gÃ¬, cÃ¡ch Ä‘á»ƒ phÃ¢n biá»‡t Ã¡o vest nam cÅ©ng nhÆ° tham kháº£o Ä‘Æ°á»£c má»™t vÃ i máº«u Ã¡o vest nam phÃ¹ há»£p nháº¥t cho báº£n thÃ¢n qua bÃ i viáº¿t dÆ°á»›i Ä‘Ã¢y.',NULL,0,'Ão vest nam','ao-vest-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çMøÁ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão khoÃ¡c nam TRENDISTA chÃ­nh hÃ£ng giÃ¡ ráº», cháº¥t lÆ°á»£ng cao, chá»‘ng nÆ°á»›c cáº£n giÃ³, mua hÃ ng chÃ­nh hÃ£ng táº¡i website, giao hÃ ng nhanh chÃ³ng.',NULL,1,'Ão khoÃ¡c nam','ao-khoac-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çMû²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,2,'Ão phao nam','ao-phao-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çMş:²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,3,'Ão giÃ³ nam','ao-gio-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çMÿ{²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Nhá»¯ng ngÃ y thu Ä‘Ã´ng gáº§n tá»›i, Ä‘á»ƒ khá»i nhÃ m chÃ¡n vá»›i cÃ¡c kiá»ƒu Ã¡o nhÆ° Ã¡o len, Ã¡o giá»¯ nhiá»‡t khÃ´ng cÃ³ chÃºt phÃ¡ cÃ¡ch nÃ o thÃ¬ Ã¡o hoodie nam lÃ  lá»±a chá»n sÃ¡ng suá»‘t. Váº­y Ã¡o hoodie namÂ Ä‘Æ°á»£c máº·c nhÆ° tháº¿ nÃ o vÃ  mua Ã¡o hoodie nam á»Ÿ Ä‘Ã¢u cháº¥t lÆ°á»£ng nháº¥t?',NULL,4,'Ão hoodie - Ão ná»‰ nam','ao-hoodie-ao-ni-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çN\0¦²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão len nam cÃ³ vÃ´ cÃ¹ng nhá»¯ng kiá»ƒu cÃ¡ch khÃ¡c nhau tá»«: Ã¡o len cá»• cao nam, Ã¡o len nam cá»• lá», Ã¡o len namâ€¦ Nhá»¯ng kiá»ƒu Ã¡o nÃ y thÆ°á»ng mang form rá»™ng, cháº¥t má»m má»‹n vÃ  báº¯t ká»‹p xu hÆ°á»›ng thá»i trang nam. Phong cÃ¡ch TRENDISTA giÃºp chÃ ng tÃºt tÃ¡tÂ láº¡i váº» Ä‘áº¹p trai Ä‘Ã³n mÃ¹a Ä‘Ã´ng áº¥m Ã¡p.',NULL,5,'Ão len nam','ao-len-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çNÁ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','BST máº«u Ã¡o thun nam Ä‘áº¹p, Ã¡o phÃ´ng nam hÃ ng hiá»‡u cao cáº¥p YODY má»›i nháº¥t 2024. Mua Ã¡o thun nam TRENDISTA vá»›i Æ°u Ä‘Ã£i háº¥p dáº«n, freeship toÃ n quá»‘c.',NULL,6,'Ão thun nam','ao-thun-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çN½²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','BST Ã¡o sÆ¡ mi nam Ä‘áº¹p, cao cáº¥p, chÃ­nh hÃ£ng TRENDISTA, Ã¡o sÆ¡ mi cÃ´ng sá»Ÿ nam hiá»‡n Ä‘áº¡i, tráº» trung, Ä‘a dáº¡ng máº«u mÃ£. Mua Ã¡o sÆ¡ mi táº¡i TRENDISTA ngay!',NULL,7,'Ão sÆ¡ mi nam','ao-so-mi-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çN¬²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','BST Ã¡o polo nam chÃ­nh hÃ£ng, Ä‘a dáº¡ng kiá»ƒu dÃ¡ng táº¡i TRENDISTA. Cháº¥t liá»‡u cao cáº¥p, thoÃ¡ng mÃ¡t, giÃ¡ tá»‘t nháº¥t. Mua online, giao hÃ ng nhanh toÃ n quá»‘c!',NULL,8,'Ão polo nam','ao-polo-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú|A²gğ“VŠµ£§'),(_binary '\çN•²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Quáº§n dÃ i nam','quan-dai-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú}4²gğ“VŠµ£§'),(_binary '\çN‰²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Váº» thanh lá»‹ch, tráº» trung vÃ Â nam tÃ­nh lÃ  táº¥t cáº£ nhá»¯ng gÃ¬ báº¡n nháº­n Ä‘Æ°á»£c khi diá»‡n quáº§n kaki nam. DÆ°á»›i Ä‘Ã¢y, thá»i trang TRENDISTA sáº½ báº­t mÃ­ vá»›i má»i ngÆ°á»i nhá»¯ng máº«u quáº§n thá»‹nh hÃ nh nháº¥t vÃ  cÃ¡ch phá»‘i Ä‘á»“ vá»›i quáº§n kaki nam chuáº©n fashionista.',NULL,1,'Quáº§n kaki nam','quan-kaki-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú}4²gğ“VŠµ£§'),(_binary '\çN~²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','HÃ ng ngÃ n quáº§n short nam tráº» trung, quáº§n sooc nam thá»ƒ thao giÃ¡ ráº». Máº«u mÃ£ Ä‘a dáº¡ng, kiá»ƒu dÃ¡ng nÄƒng Ä‘á»™ng, mua quáº§n Ä‘Ã¹i nam vá»›i Æ°u Ä‘Ã£i khá»§ng lÃªn Ä‘áº¿n 50%!',NULL,2,'Quáº§n short nam','quan-short-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú}4²gğ“VŠµ£§'),(_binary '\çNÓ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','BST quáº§n jeans nam Ä‘áº¹p, quáº§n bÃ² nam chÃ­nh hÃ£ng TRENDISTA, máº«u má»›i nháº¥t 2024. Mua online ngay Ä‘á»ƒ nháº­n Æ°u Ä‘Ã£i giáº£m giÃ¡ lÃªn tá»›i 50%',NULL,3,'Quáº§n jeans nam','quan-jeans-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú}4²gğ“VŠµ£§'),(_binary '\çN\n\0²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n Ã¢u nam cÃ´ng sá»Ÿ Ä‘áº¹p, quáº§n tÃ¢y nam cao cáº¥p chÃ­nh hÃ£ng TRENDISTA Ä‘a dáº¡ng máº«u mÃ£, kiá»ƒu dÃ¡ng. Mua ngay quáº§n Ã¢u TRENDISTA vá»›i Æ°u Ä‘Ã£i cá»±c sá»‘c!',NULL,4,'Quáº§n Ã¢u nam','quan-au-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú}4²gğ“VŠµ£§'),(_binary '\çN\n÷²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Äá»“ bá»™ dÃ i tay nam','do-bo-dai-tay-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú}ò²gğ“VŠµ£§'),(_binary '\çNò²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Äá»“ bá»™ ngáº¯n tay nam','do-bo-ngan-tay-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú}ò²gğ“VŠµ£§'),(_binary '\çNÒ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n lÃ³t nam lÃ  mÃ³n Ä‘á»“ Ä‘Æ°á»£c máº·c bÃªn trong nháº±m báº£o vá»‡ â€œcáº­u nhá»â€. ÄÃ¢y chÃ­nh lÃ  lÃ½ do cÆ¡ báº£n nháº¥t khi máº·c Ä‘á»“ lÃ³t khiáº¿n phÃ¡i máº¡nh luÃ´n tá»± tin vÃ  thoáº£i mÃ¡i má»—i khi sá»­ dá»¥ng.\n\nCÃ¡c loáº¡i quáº§n lÃ³t namÂ Ä‘Æ°á»£c sáº£n xuáº¥t tá»« nhiá»u cháº¥t liá»‡u váº£i khÃ¡c nhau, chá»§ yáº¿u lÃ  nhá»¯ng cháº¥t liá»‡u tá»± nhiÃªn, khÃ´ng gÃ¢y kÃ­ch á»©ng da',NULL,0,'Quáº§n lÃ³t nam','quan-lot-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú~¦²gğ“VŠµ£§'),(_binary '\çN\r×²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Mua Ã¡o ba lá»— nam chÃ­nh hÃ£ng, Ã¡o thun 3 lá»— cháº¥t liá»‡u cao cáº¥p, co giÃ£n 4 chiá»u, tá»± tin trong cÃ¡c hoáº¡t Ä‘á»™ng thá»ƒ thao nhÆ° gym, bÃ³ng rá»•, bÃ³ng Ä‘Ã¡, cháº¡y bá»™,..',NULL,1,'Ão ba lá»— nam','ao-ba-lo-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú~¦²gğ“VŠµ£§'),(_binary '\çNÍ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão giá»¯ nhiá»‡t nam lÃ  trang phá»¥c sá»Ÿ há»¯u nhiá»u Æ°u Ä‘iá»ƒm vÆ°á»£t trá»™i Ä‘Æ°á»£c thiáº¿t káº¿ má»ng, nháº¹ giÃºp giá»¯ áº¥m Ä‘Æ°á»£c sáº£n xuáº¥t vá»›i cáº¥u táº¡o Ä‘áº·c biá»‡t. Váº­y Ã¡o giá»¯ nhiá»‡t nam cÃ³ thiáº¿t káº¿ nhÆ° tháº¿ nÃ o? Mua Ã¡o giá»¯ nhiá»‡t nam á»Ÿ Ä‘Ã¢u? CÃ´ng dá»¥ng cá»§a Ã¡o giá»¯ nhiá»‡t nam lÃ  gÃ¬? CÃ¹ng tÃ¬m hiá»ƒu ngay vá»›i TRENDISTA nhÃ©!',NULL,2,'Ão giá»¯ nhiá»‡t nam','ao-giu-nhiet-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú~¦²gğ“VŠµ£§'),(_binary '\çNİ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Má»™t trong nhá»¯ng loáº¡i quáº§n khÃ´ng thá»ƒ thiáº¿u dÃ nh cho phÃ¡i máº¡nh lÃ  quáº§n thá»ƒ thao nam. Quáº§n thá»ƒ thao nam khÃ´ng chá»‰ Ä‘Ã¡p á»©ng phong cÃ¡ch mÃ  cÃ²n giÃºp báº¡n cáº£m tháº¥y thoáº£i mÃ¡i khi tham gia cÃ¡c hoáº¡t Ä‘á»™ng. NgÃ y nay cÃ³ nhiá»u loáº¡i quáº§n thá»ƒ thao Ä‘Æ°á»£c phÃ¡t triá»ƒn cho báº¡n cÃ³ thÃªm nhiá»u sá»± lá»±a chá»n nhÆ°:Â quáº§n dÃ i, quáº§n',NULL,0,'Quáº§n thá»ƒ thao nam','quan-the-thao-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æúk²gğ“VŠµ£§'),(_binary '\çN\è²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão polo thá»ƒ thao nam lÃ  má»™t trong nhá»¯ng máº«u Ã¡o Ä‘Æ°á»£c sá»­ dá»¥ng phá»• biáº¿n, rá»™ng rÃ£i vÃ  Ä‘áº¡t Ä‘Æ°á»£c cÃ¡nh mÃ y rÃ¢u cá»±c ká»³ yÃªu thÃ­ch.\n\nHÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu Ã¡o polo thá»ƒ thao cho nam lÃ  Ã¡o gÃ¬, cÃ¡c máº«u Ã¡o polo thá»ƒ thao nam hot nháº¥t nÄƒm 2024 cÃ¹ng cÃ¡c cháº¥t liá»‡u lÃ m nÃªn Ã¡o polo thá»ƒ thaoÂ qua bÃ i viáº¿t dÆ°á»›i Ä‘Ã¢y.',NULL,1,'Ão polo thá»ƒ thao nam','ao-polo-the-thao-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æúk²gğ“VŠµ£§'),(_binary '\çNh²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão thun thá»ƒ thao, Ã¡oÂ phÃ´ng thá»ƒ thao nam thÆ°á»ng Ä‘Æ°á»£c cÃ¡nh mÃ y rÃ¢uÂ sá»­ dá»¥ng trong nhá»¯ng lÃºc táº­p luyá»‡n thá»ƒ dá»¥c thá»ƒ thao hay cÃ¡c hoáº¡t Ä‘á»™ngÂ ngoÃ i trá»i cáº§n váº­n Ä‘á»™ng máº¡nh. Loáº¡i Ã¡o nÃ y cÃ³ cáº¥u táº¡o vÃ  cháº¥t liá»‡u Ä‘áº·c biá»‡t giÃºp cho viá»‡c hoáº¡t Ä‘á»™ng cá»§a nhá»¯ng ngÆ°á»i sá»­ dá»¥ng sáº£n pháº©m nÃ y cáº£m tháº¥y thoáº£i mÃ¡i, mÃ¡t láº¡nh',NULL,2,'Ão thun thá»ƒ thao nam','ao-thun-the-thao-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æúk²gğ“VŠµ£§'),(_binary '\çNÅ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Bá»™ Ä‘á»“ thá»ƒ thao cho nam lÃ  má»™t trong nhá»¯ng sáº£n pháº©m thá»i trang phá»¥c vá»¥ hiá»‡u quáº£ cho Ä‘á»i sá»‘ng thÆ°á»ng ngÃ y, thá»ƒ dá»¥c thá»ƒ thao má»—i ngÃ y mÃ  váº«n ráº¥t thá»i trang, phong cÃ¡ch.\n\n\n\nHÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu bá»™ Ä‘á»“ thá»ƒ thao cho nam lÃ  gÃ¬, cÃ¡c bá»™ Ä‘á»“ thá»ƒ thao cho nam Ä‘ang hot nháº¥t hiá»‡n nay cÃ¹ng má»™t vÃ i lÆ°u Ã½ khi mua bá»™ thá»ƒ thao nam',NULL,3,'Bá»™ thá»ƒ thao nam','bo-the-thao-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æúk²gğ“VŠµ£§'),(_binary '\çNÒ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Táº¥t nam','tat-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú€\"²gğ“VŠµ£§'),(_binary '\çN\\²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'TÃºi xÃ¡ch nam','tui-xach-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú€\"²gğ“VŠµ£§'),(_binary '\çNQ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Mua mÅ© lÆ°á»¡i trai nam, nÃ³n nam chÃ­nh hÃ£ng giÃ¡ tá»‘t. Mua mÅ© lÆ°á»¡i trai nam táº¡i TRENDISTA vá»›i Æ°u Ä‘Ã£i, giao hÃ ng miá»…n phÃ­, Ä‘á»•i tráº£ dá»… dÃ ng.',NULL,2,'MÅ© nam','mu-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú€\"²gğ“VŠµ£§'),(_binary '\çbR²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Tháº¯t lÆ°ng nam lÃ  mÃ³n phá»¥ kiá»‡n thá»i trang khÃ´ng thá»ƒ thiáº¿u cho phÃ¡i máº¡nh ngÃ y nay. ÄÃ¢y lÃ  mÃ³n phá»¥ kiá»‡n dÃ¹ng Ä‘á»ƒ káº¿t há»£p vá»›i quáº§n Ã¢u, quáº§n jeans, bá»™ vest lá»‹ch lÃ£m,... Ä‘á»ƒ tÄƒng sá»± thanh lá»‹ch vÃ  sang trá»ng cá»§a ngÆ°á»i máº·c. \n\nNhÆ°ng tháº¯t lÆ°ng nam cÃ³ nhiá»u kiá»ƒu dÃ¡ng vÃ  cháº¥t liá»‡u, má»—i kiá»ƒu dÃ¡ng, cháº¥t liá»‡uÂ láº¡i cÃ³',NULL,3,'Tháº¯t lÆ°ng nam','that-lung-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú€\"²gğ“VŠµ£§'),(_binary '\çbSš²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','GiÃ y nam lÃ  mÃ³n Ä‘á»“ thá»i trangÂ khÃ´ng láº¡ láº«m gÃ¬ vá»›i phÃ¡i máº¡nh ná»¯a, nháº¥t lÃ  vÃ o nÄƒm 2024 khi mÃ  dÃ©p cÃ³ xu hÆ°á»›ng Ä‘i xuá»‘ng. Má»—i kiá»ƒu giÃ y nam láº¡i cÃ³ má»¥c Ä‘Ã­ch sá»­ dá»¥ng khÃ¡c nhau nÃªn ngÆ°á»i Ä‘i sáº½ pháº£i Ä‘Æ°a ra quyáº¿t Ä‘á»‹nh há»£p lÃ­ Ä‘á»ƒ cÃ³ bá»™ Ä‘á»“ Ä‘áº¹p nháº¥t.\n\nCÃ³ nhiá»u kiá»ƒu giÃ y nam nhÆ° giÃ y tÃ¢y, giÃ y da, giÃ y lÆ°á»i,...',NULL,4,'GiÃ y nam','giay-nam',_binary '\æ¼ÿ\0²gğ“VŠµ£§',_binary '\æú€\"²gğ“VŠµ£§'),(_binary '\çbU—²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão khoÃ¡c tráº» em thÆ°Æ¡ng hiá»‡u TRENDISTA luÃ´n Ä‘Ã¡p á»©ng Ä‘Æ°á»£c cháº¥t lÆ°á»£ng cÅ©ng nhÆ° Ä‘a dáº¡ng vá» chá»§ng loáº¡i: Ão phao tráº» em, Ã¡o giÃ³ tráº» em, Ã¡o áº¥m,... giÃºp cÃ¡c bÃ© giá»¯ áº¥m hiá»‡u quáº£ trong thá»i tiáº¿t mÃ¹a Ä‘Ã´ng kháº¯c nghiá»‡t.',NULL,0,'Ão khoÃ¡c tráº» em','ao-khoac-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbV‰²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Ão phao tráº» em','ao-phao-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbW-²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,2,'Ão giÃ³ tráº» em','ao-gio-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbWÂ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão ná»‰ tráº» emÂ lÃ  má»™t mÃ³n Ä‘á»“ thá»i trang thiáº¿t yáº¿u giÃºp bÃ© yÃªu giá»¯ áº¥m trong nhá»¯ng ngÃ y thá»i tiáº¿t chuyá»ƒn láº¡nh.Â HÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu vá» má»™t vÃ i máº«u Ã¡o ná»‰, Ã¡o hoodie tráº» em hot trend nháº¥t hiá»‡n nay vÃ  tham kháº£o thÃªm má»™t vÃ i cÃ¡ch phá»‘i Ä‘á»“ vá»›i Ã¡o ná»‰ tráº» em trong ná»™i dung dÆ°á»›i Ä‘Ã¢y nhÃ©!',NULL,3,'Ão hoodie - Ão ná»‰ tráº» em','ao-hoodie-ao-ni-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbXg²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Ão len tráº»Â emÂ luÃ´n lÃ  má»™t mÃ³n Ä‘á»“ thá»i trang thiáº¿t yáº¿u giÃºp bÃ© yÃªu giá»¯ áº¥m trong nhá»¯ng ngÃ y thá»i tiáº¿t chuyá»ƒn láº¡nh.Â Â HÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu vá» má»™t vÃ i máº«uÂ Ã¡o len tráº» em hot trend nháº¥t hiá»‡n nay, cÅ©ng nhÆ° cÃ¡c lÆ°u Ã½ trong khÃ¢u lá»±a chá»n Ã¡o len cho bÃ© vÃ  tham kháº£o thÃªm má»™t vÃ i cÃ¡ch phá»‘i Ä‘á»“ vá»›iÂ Ã¡o len tráº» em',NULL,4,'Ão len tráº» em','ao-len-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbY²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','BST Ã¡o thun tráº» em, Ã¡o phÃ´ng bÃ© trai, bÃ© gÃ¡i cao cáº¥p, cháº¥t liá»‡u thoÃ¡ng mÃ¡t. Mua Ã¡o phÃ´ng tráº» em cao cáº¥p TRENDISTA Ä‘á»ƒ nháº­n Æ°u Ä‘Ã£i háº¥p dáº«n!',NULL,5,'Ão thun tráº» em','ao-thun-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbY¥²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Cáº­p nháº­t BST Ã¡o sÆ¡ mi tráº» em Ä‘áº¹p, cao cáº¥p, Ã¡o sÆ¡ mi cho bÃ© chÃ­nh hÃ£ng TRENDISTA cháº¥t liá»‡u vÆ°á»£t trá»™i. Mua Ã¡o so mi tráº» em TRENDISTA giÃ¡ Æ°u Ä‘Ã£i ngay!',NULL,6,'Ão sÆ¡ mi tráº» em','ao-so-mi-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbZB²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','BST Ã¡o polo tráº» em TRENDISTA Ä‘a dáº¡ng máº«u mÃ£: Ã¡o polo bÃ© trai, Ã¡o polo bÃ© gÃ¡i cao cáº¥p, thoÃ¡ng mÃ¡t. Mua Ã¡o polo tráº» em chÃ­nh hÃ£ng TRENDISTA ngay!',NULL,7,'Ão polo tráº» em','ao-polo-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú€Ö²gğ“VŠµ£§'),(_binary '\çbZØ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n legging tráº» em laÌ€ mÃ´Ì£t trong nhÆ°Ìƒng item khÃ´ng thá»ƒ thiáº¿u cá»§a caÌc beÌ. Quáº§n legging vÆ°Ì€a hÆ¡Ì£p thÆ¡Ì€i trang laÌ£i vÆ°Ì€a thoáº£i maÌi cho caÌc beÌ cáº£ ngaÌ€y hoaÌ£t Ä‘Ã´Ì£ng. VÃ¢Ì£y baÌ£n Ä‘aÌƒ biáº¿t coÌ nhÆ°Ìƒng laÌ£oi legging naÌ€o phuÌ€ hÆ¡Ì£p vÆ¡Ìi caÌc beÌ chÆ°a? HÃ£y cuÌ€ng TRENDISTA tiÌ€m hiá»ƒu ngay Ä‘á»ƒ khaÌm phÃ¡.',NULL,0,'Quáº§n dÃ i tráº» em','quan-dai-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú±²gğ“VŠµ£§'),(_binary '\çb\\°²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n kaki tráº» em Ä‘Æ°á»£c xem lÃ  mÃ³n Ä‘á»“ khÃ´ng thá»ƒ thiáº¿u trong tá»§ Ä‘á»“ cá»§a cÃ¡c bÃ© bá»Ÿi Æ°u Ä‘iá»ƒm khi máº·c chÃ­nh lÃ  sá»± thoáº£i mÃ¡i, dá»… chá»‹u hÆ¡n nÃªn cÃ¡c máº¹ lá»±a chá»n mua nhiá»u. ChÆ°a dá»«ng láº¡i á»Ÿ Ä‘Ã³ chiáº¿c quáº§n kaki cho bÃ© traiÂ nÃ y cÃ²n dá»… dÃ ng káº¿t há»£p Ä‘Æ°á»£c vá»›i nhiá»u loáº¡i Ã¡o giÃºp nÃ¢ng táº§m diá»‡n máº¡o cho cÃ¡c bÃ© yÃªu.',NULL,1,'Quáº§n kaki tráº» em','quan-kaki-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú±²gğ“VŠµ£§'),(_binary '\çb]’²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n ná»‰ tráº» em lÃ  chiáº¿c quáº§n ráº¥t hoÃ n háº£o vÃ  khÃ´ng thá»ƒ thiáº¿u trong tá»§ Ä‘á»“ dÃ nh cho cÃ¡c bÃ©. HÃ£y cÃ¹ng TRENDISTA tÃ¬m hiá»ƒu quáº§n ná»‰ em lÃ  gÃ¬, Ä‘iá»ƒm qua nhá»¯ng máº«u quáº§n ná»‰ tráº» em hot nháº¥t hiá»‡n nay cÅ©ng nhÆ° gá»£i Ã½ phá»‘i Ä‘á»“ vá»›i quáº§n ná»‰ tráº» em qua bÃ i viáº¿t dÆ°á»›i Ä‘Ã¢y.',NULL,2,'Quáº§n ná»‰ tráº» em','quan-ni-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú±²gğ“VŠµ£§'),(_binary '\çb^y²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Quáº§n short tráº» em khÃ´ng chá»‰ mang tÃ¡c dá»¥ng phá»‘i Ä‘á»“ lÃ m Ä‘áº¹p mÃ  cÃ²n lÃ  má»™t mÃ³n Ä‘á»“ thá»i trang ráº¥t cáº§n thiáº¿t Ä‘á»‘i vá»›i má»i Ä‘á»™ tuá»•i cá»§a bÃ©.',NULL,3,'Quáº§n short tráº» em','quan-short-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú±²gğ“VŠµ£§'),(_binary '\çb_“²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','TRENDISTA mang Ä‘áº¿n tháº¿ giá»›i thá»i trang nÄƒng Ä‘á»™ng vÃ  phong cÃ¡ch cho cÃ¡c bÃ© yÃªu vá»›i bá»™ sÆ°u táº­p quáº§n jean tráº» em, quáº§n bÃ² tráº» em Ä‘a dáº¡ng vÃ  cháº¥t lÆ°á»£ng. TRENDISTA hiá»ƒu ráº±ng cÃ¡c bÃ© cáº§n sá»± thoáº£i mÃ¡i vÃ  tá»± do váº­n Ä‘á»™ng, Ä‘á»“ng thá»i váº«n thá»ƒ hiá»‡n cÃ¡ tÃ­nh riÃªng.',NULL,4,'Quáº§n jeans tráº» em','quan-jeans-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú±²gğ“VŠµ£§'),(_binary '\çb`¦²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Äá»“ bá»™ dÃ i tay tráº» em','do-bo-dai-tay-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú„6²gğ“VŠµ£§'),(_binary '\çbeN²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Äá»“ bá»™ ngáº¯n tay tráº» em','do-bo-ngan-tay-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú„6²gğ“VŠµ£§'),(_binary '\çbfz²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Ão giá»¯ nhiá»‡t tráº» em','ao-giu-nhiet-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æúˆ5²gğ“VŠµ£§'),(_binary '\çbg²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','NÃ³i Ä‘áº¿n thá»i trang tráº» em khÃ´ng thá»ƒ khÃ´ng ká»ƒ Ä‘áº¿n nhá»¯ng máº«u vÃ¡y tráº» em Ä‘áº¹p. Nhá»¯ng chiáº¿c vÃ¡y Ä‘áº§m tráº» em cao cáº¥p khÃ´ng chá»‰ Ä‘Æ°á»£c cÃ¡c bÃ© gÃ¡i yÃªu thÃ­ch vÃ  lá»±a chá»n khi Ä‘i mua sáº¯m cÃ¹ng bá»‘ máº¹. TRENDISTA mÃ¡ch báº¡n nhá»¯ng kiá»ƒu vÃ¡y cÆ¡ báº£n vÃ  nhá»¯ng tip hÆ°á»›ng dáº«n mix trang phá»¥c Ä‘á»ƒ máº¹ trá»Ÿ thÃ nh chuyÃªn gia vÃ  biáº¿n bÃ© trá»Ÿ',NULL,0,'Äáº§m bÃ© gÃ¡i','dam-be-gai',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æúŒ+²gğ“VŠµ£§'),(_binary '\çbi‹²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000','Diá»‡n cÃ¡c máº«u chÃ¢n vÃ¡y bÃ© gÃ¡i Ä‘áº¹p luÃ´n lÃ  niá»m yÃªu thÃ­ch cá»§a cÃ¡c cÃ´ bÃ© yÃªu thÃ­ch sá»± dá»… thÆ°Æ¡ng, Ä‘iá»‡u Ä‘Ã . ÄÃ¢y cÅ©ng lÃ  item nÃ y mÃ  phá»¥ huynh hiá»‡n nay lá»±a chá»n cho cÃ¡c cÃ´ cÃ´ng chÃºa nhá» cá»§a mÃ¬nh. Vá»›i bÃ i viáº¿t dÆ°á»›i Ä‘Ã¢y, TRENDISTA mong ráº±ng báº¡n sáº½ tÃ¬m Ä‘Æ°á»£c cho bÃ© yÃªu nhÃ  mÃ¬nh nhá»¯ng máº«u chÃ¢n vÃ¡y phÃ¹ há»£p nháº¥t!',NULL,1,'ChÃ¢n vÃ¡y bÃ© gÃ¡i','chan-vay-be-gai',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æúŒ+²gğ“VŠµ£§'),(_binary '\çbj^²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,0,'Táº¥t tráº» em','tat-tre-em',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú²gğ“VŠµ£§'),(_binary '\çbjö²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,NULL,1,'Phá»¥ kiá»‡n tráº» em khÃ¡c','phu-kien-tre-em-khac',_binary '\æ½\'¬²gğ“VŠµ£§',_binary '\æú²gğ“VŠµ£§');
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
INSERT INTO `category_discount` VALUES (_binary '\çMøÁ²gğ“VŠµ£§',_binary '\í\Äj¤õ–B¥PE¾Á5&');
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
  `content_metadata` varchar(255) DEFAULT NULL,
  `content_url` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `is_read` bit(1) NOT NULL,
  `message` varchar(255) DEFAULT NULL,
  `message_type` varchar(255) DEFAULT NULL,
  `thumbnail_url` varchar(255) DEFAULT NULL,
  `receiver_id` binary(16) DEFAULT NULL,
  `sender_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8535ns29owlsavcfbdx1fqejr` (`receiver_id`),
  KEY `FKm92rh2bmfw19xcn7nj5vrixsi` (`sender_id`),
  CONSTRAINT `FK8535ns29owlsavcfbdx1fqejr` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKm92rh2bmfw19xcn7nj5vrixsi` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_message`
--

LOCK TABLES `chat_message` WRITE;
/*!40000 ALTER TABLE `chat_message` DISABLE KEYS */;
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `banner_url` varchar(255) DEFAULT NULL,
  `description` text,
  `name` varchar(255) NOT NULL,
  `order_index` int DEFAULT NULL,
  `slug` varchar(255) NOT NULL,
  `status` bit(1) DEFAULT NULL,
  `thumbnail` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKy9tqsfhcsdnb3oqmc6dmb2i1` (`name`),
  UNIQUE KEY `UK6ddpfjq9st4mbnjvqyi3aaqkj` (`slug`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `collection`
--

LOCK TABLES `collection` WRITE;
/*!40000 ALTER TABLE `collection` DISABLE KEYS */;
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `code` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color`
--

LOCK TABLES `color` WRITE;
/*!40000 ALTER TABLE `color` DISABLE KEYS */;
INSERT INTO `color` VALUES (_binary '\ÂÀf^²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','sky-blue','xanh lÆ¡','#8ABCC0'),(_binary '\ÂÇ´]²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','teal','xanh cá»• vá»‹t','#244368'),(_binary '\ÂÇ¹#²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','dark-blue','xanh Ä‘áº­m','#235387'),(_binary '\ÂÇ¹Ë²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','mint-green','xanh mint','#CDDCDB'),(_binary '\ÂÇº²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','light-blue','xanh nháº¡t','#A8D3D7'),(_binary '\ÂÇºQ²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','black-blue','xanh Ä‘en','#13293D'),(_binary '\ÂÇº‹²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','cobalt-blue','xanh coban','#0E405F'),(_binary '\ÂÇºÃ²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','green','xanh lÃ¡','#008000'),(_binary '\ÂÇ¼\n²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','moss-green','rÃªu','#45503B'),(_binary '\ÂÇ¼¯²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','olive-green','xanh rÃªu','#747553'),(_binary '\ÂÇ½0²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','ash','ghi','#AE9B91'),(_binary '\ÂÇ½œ²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','gray-ash','ghi xÃ¡m','#C5C9CA'),(_binary '\ÂÇ½\â²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','brown','nÃ¢u','#C09A7E'),(_binary '\Â\ÇÀ(²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','dark-brown','nÃ¢u Ä‘áº­m','#3A2923'),(_binary '\Â\ÇÀ´²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','orange','cam','#FE523D'),(_binary '\Â\ÇÁü²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','earth-orange','cam Ä‘áº¥t','#E35125'),(_binary '\Â\Ç\Âh²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','beige','be','#F5F5DC'),(_binary '\Â\Ç\Ãt²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','pink','há»“ng','#FFC0CB'),(_binary '\Â\Ç\Ä\n²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','purple','tÃ­m','#DEBCC8'),(_binary '\Â\Ç\ÄT²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','light-purple','tÃ­m nháº¡t','#C3BCE8'),(_binary '\Â\ÇÄ²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','indigo','tÃ­m than','#204A80'),(_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','black','Ä‘en','#000000'),(_binary '\Â\Ç\Äı²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','navy','navy','#27285C'),(_binary '\Â\Ç\Å3²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','yellow','vÃ ng','#FFFF00'),(_binary '\Â\Ç\Ål²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','red','Ä‘á»','#FF0000'),(_binary '\Â\ÇÅ¥²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','gray','xÃ¡m','#DDDDDD'),(_binary '\Â\Ç\ÅÛ²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','dark-gray','xÃ¡m Ä‘áº­m','#9B8F91'),(_binary '\Â\Ç\Æ²fğ“VŠµ£§','2025-10-26 12:24:47.000000',NULL,'2025-10-26 12:24:47.000000','white','tráº¯ng','#FFFFFF');
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `code` varchar(255) NOT NULL,
  `description` text,
  `discount_apply` enum('ORDER','PRODUCT','SHIPPING') NOT NULL,
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
INSERT INTO `discount` VALUES (_binary 'y\ÏÕ¼$K±òÙ¦®Í«','2025-10-26 12:56:49.202000',NULL,NULL,'TRENDISTATRIAN','<p>Khuyáº¿n mÃ£i  dÃ nh cho khÃ¡ch hÃ ng má»›i nhÃ  Trendista !</p>','ORDER','PERCENT',10.00,'2025-11-23 23:59:59.000000','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761483398/promotions/BANNER-FLASH-SALE-5-20_20f126ca-1b17-4edc-8167-4607f76263dc.jpg',_binary '',20000.00,NULL,200000.00,'2025-10-01 00:00:00.000000',NULL),(_binary '\í\Äj¤õ–B¥PE¾Á5&','2025-10-26 12:54:45.839000',NULL,NULL,'TRENDISTA_OPEN','<p>Khuyáº¿n mÃ£i má»Ÿ  bÃ¡n</p>','PRODUCT','PERCENT',40.00,'2025-11-30 23:59:59.000000','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761483263/discounts/promotion-12-0_e19e182d-68b7-4186-9b61-93e6362500a3.webp',_binary '',100000.00,NULL,NULL,'2025-10-26 00:00:00.000000',NULL);
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `slug` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gender`
--

LOCK TABLES `gender` WRITE;
/*!40000 ALTER TABLE `gender` DISABLE KEYS */;
INSERT INTO `gender` VALUES (_binary '\æ¼ÿ\0²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,'Nam','nam'),(_binary '\æ½²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,'Ná»¯','nu'),(_binary '\æ½\'¬²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,'Tráº» em','tre-em'),(_binary '\æ½(Ğ²gğ“VŠµ£§','2025-10-26 12:32:57.000000',NULL,'2025-10-26 12:32:57.000000',NULL,'Unisex','unisex');
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
  `category` varchar(255) DEFAULT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `redirect_url` varchar(255) DEFAULT NULL,
  `reference_id` varchar(255) DEFAULT NULL,
  `status` enum('ARCHIVED','READ','UNREAD') DEFAULT NULL,
  `type` enum('NEW_MESSAGE','ORDER_PLACED','ORDER_STATUS_CHANGED','PASSWORD_CHANGED','PAYMENT_RECEIVED','PROMOTION','SHIPPING_UPDATE','SYSTEM_ALERT') DEFAULT NULL,
  `user_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb0yvoep4h4k92ipon31wmdf7e` (`user_id`),
  CONSTRAINT `FKb0yvoep4h4k92ipon31wmdf7e` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `amount` int NOT NULL,
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
  `collection_id` binary(16) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1mtsbur82frn64de7balymq9s` (`category_id`),
  KEY `FK1m7avyryg7yow6ytttlt7qcun` (`collection_id`),
  CONSTRAINT `FK1m7avyryg7yow6ytttlt7qcun` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`),
  CONSTRAINT `FK1mtsbur82frn64de7balymq9s` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (_binary '\á\\x@B”·!X	·Bı','2025-10-26 12:37:07.130000',NULL,'2025-10-26 12:54:55.473000','EOH135','<p>Ão KhoÃ¡c Nam MÅ© Liá»n LÃ³t LÃ´ng</p>','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482143/products/ao-khoac-nam-AKM7007-DEN_a7f8dd62-2f06-43d3-96ce-6bd8ca209aa2.webp',_binary '','Ão KhoÃ¡c Nam MÅ© Liá»n LÃ³t LÃ´ng',200000.00,150000.00,0,0,'ao-khoac-nam-mu-lien-lot-long',_binary '','Ão KhoÃ¡c Nam MÅ© Liá»n LÃ³t LÃ´ng','NEW_ARRIVALS',0,2,_binary '\çMøÁ²gğ“VŠµ£§',NULL);
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
INSERT INTO `product_discount` VALUES (_binary '\á\\x@B”·!X	·Bı',_binary '\í\Äj¤õ–B¥PE¾Á5&');
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `is_thumbnail` bit(1) DEFAULT NULL,
  `image_order` int NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  `color_id` binary(16) NOT NULL,
  `product_id` binary(16) NOT NULL,
  `size_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqcabopu2ex77fymxr9rhxb5uj` (`color_id`),
  KEY `FK6oo0cvcdtb6qmwsga468uuukk` (`product_id`),
  KEY `FKrbl70ok8ups9p1rv9cjnrls8i` (`size_id`),
  CONSTRAINT `FK6oo0cvcdtb6qmwsga468uuukk` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  CONSTRAINT `FKqcabopu2ex77fymxr9rhxb5uj` FOREIGN KEY (`color_id`) REFERENCES `color` (`id`),
  CONSTRAINT `FKrbl70ok8ups9p1rv9cjnrls8i` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_image`
--

LOCK TABLES `product_image` WRITE;
/*!40000 ALTER TABLE `product_image` DISABLE KEYS */;
INSERT INTO `product_image` VALUES (_binary '\n¿V=\ê–H»¤ı>œ±¦p','2025-10-26 12:37:07.262000',NULL,NULL,_binary '\0',5,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482145/products/ao-khoac-nam-AKM7007-DEN-5_d4dbb58d-e521-4135-bfcc-dc9e536eeb18.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '&ŠQñDÌ˜\ÅZù\ï÷¤ğ','2025-10-26 12:37:07.268000',NULL,NULL,_binary '\0',5,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482182/products/ao-khoac-nam-AKM7007-GHI-5_00a15a23-758c-4593-a978-b12832dd9331.webp',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '\'Ácx¥A¦4.ñ\Âxrf','2025-10-26 12:37:07.267000',NULL,NULL,_binary '\0',1,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482182/products/ao-khoac-nam-AKM7007-GHI-1_cfed2585-147a-45e8-b753-7ed93e9045ca.webp',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary ';d\Ø\ÆHú‹\n£¶1\nõŒ','2025-10-26 12:37:07.262000',NULL,NULL,_binary '\0',6,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482143/products/ao-khoac-nam-AKM7007-DEN-6_69053f57-1183-4541-8ba6-8c5ed2825c7e.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '@\é9@kMj¯`<ú÷`','2025-10-26 12:37:07.262000',NULL,NULL,_binary '\0',4,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482143/products/ao-khoac-nam-AKM7007-DEN-4_560c636a-f915-464a-8f3c-faf630fd0b04.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary 'C‘~À×¹BH&H\Ù}¼\'','2025-10-26 12:37:07.268000',NULL,NULL,_binary '\0',3,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482184/products/ao-khoac-nam-AKM7007-GHI-3_c0983442-a087-4c83-a543-1286b799e42a.webp',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary 'PÈƒ\ä\ã\ÏIq©òdˆ$e \Ë','2025-10-26 12:37:07.269000',NULL,NULL,_binary '\0',7,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482184/products/ao-khoac-nam-AKM7007-GHI-7_c76940bb-9131-47e0-b44c-f199a674301d.webp',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '{¨\ÆÛ¼kC³s\\õv«#','2025-10-26 12:37:07.261000',NULL,NULL,_binary '\0',1,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482142/products/ao-khoac-nam-AKM7007-DEN-1_ef05d1cf-c77c-45ae-9415-1cf5b8f19780.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '€(øI„ğ@-‡¹ƒ•\Ğ±','2025-10-26 12:37:07.268000',NULL,NULL,_binary '\0',4,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482182/products/ao-khoac-nam-AKM7007-GHI-4_eec95fd7-2020-4ff4-b5a6-0dbde88b8524.webp',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary 'Hh \È\×Aö’—\0G','2025-10-26 12:37:07.267000',NULL,NULL,_binary '\0',2,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482182/products/ao-khoac-nam-AKM7007-GHI-2_5624cc72-37e7-4474-b650-4c357f9f44f3.webp',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '”ÿœ\å®\Z@\ë¨m\Õ\Ôoq‡C','2025-10-26 12:37:07.261000',NULL,NULL,_binary '\0',3,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482145/products/ao-khoac-nam-AKM7007-DEN-3_750f9fab-3545-47a4-b044-a023072a8eb6.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary 'š\äGõ\ÚDÌ¶\â8Ë •\Û','2025-10-26 12:37:07.263000',NULL,NULL,_binary '\0',7,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482145/products/ao-khoac-nam-AKM7007-DEN-7_c1ad1afc-0376-4905-8616-77d1e4b51642.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary 'œ;\ãM©¦H\Ç\åş3','2025-10-26 12:37:07.266000',NULL,NULL,_binary '',0,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482183/products/ao-khoac-nam-AKM7007-GHI_34f7cc11-1fc1-401c-b826-288b89fd77b6.jpg',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary 'Ùƒ\íqiJI\0ÁCw÷~','2025-10-26 12:37:07.269000',NULL,NULL,_binary '\0',6,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482182/products/ao-khoac-nam-AKM7007-GHI-6_f6f25819-3fd5-44f7-83f5-c293e3f8b34e.webp',_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '\ãVOıOC½œ1Œ—3D:ú','2025-10-26 12:37:07.261000',NULL,NULL,_binary '\0',2,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482142/products/ao-khoac-nam-AKM7007-DEN-2_207fdc76-7b7a-4534-bfb1-79947695b1a8.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary 'ôR\Z°\ÈE¦€\Ó\çl£1¾','2025-10-26 12:37:07.234000',NULL,NULL,_binary '',0,'https://res.cloudinary.com/dwyjwk0mf/image/upload/v1761482143/products/ao-khoac-nam-AKM7007-DEN_a7f8dd62-2f06-43d3-96ce-6bd8ca209aa2.webp',_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§');
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
INSERT INTO `product_product_variants` VALUES (_binary '\á\\x@B”·!X	·Bı',_binary '\Ë:”vH\ÌL^ƒ¦	bµŠ3\æ'),(_binary '\á\\x@B”·!X	·Bı',_binary '÷\ß•\ÏA™9Œq86»');
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
-- Table structure for table `product_sub_themes`
--

DROP TABLE IF EXISTS `product_sub_themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_sub_themes` (
  `product_id` binary(16) NOT NULL,
  `sub_theme_id` bigint NOT NULL,
  KEY `FK6t8f122f5mypidugmr8g6l0jx` (`sub_theme_id`),
  KEY `FKq5i0lqadkf65pct5lw3ua07bs` (`product_id`),
  CONSTRAINT `FK6t8f122f5mypidugmr8g6l0jx` FOREIGN KEY (`sub_theme_id`) REFERENCES `sub_themes` (`id`),
  CONSTRAINT `FKq5i0lqadkf65pct5lw3ua07bs` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_sub_themes`
--

LOCK TABLES `product_sub_themes` WRITE;
/*!40000 ALTER TABLE `product_sub_themes` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_sub_themes` ENABLE KEYS */;
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
  `variant_order` int NOT NULL,
  `price` decimal(38,2) NOT NULL,
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
INSERT INTO `product_variant` VALUES (_binary '\Ë:”vH\ÌL^ƒ¦	bµŠ3\æ','EOH135-TRáº®NG-M',1,0.00,30,_binary '\Â\Ç\Æ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§'),(_binary '÷\ß•\ÏA™9Œq86»','EOH135-ÄEN-M',0,20.00,20,_binary '\Â\Ç\ÄÈ²fğ“VŠµ£§',_binary '\á\\x@B”·!X	·Bı',_binary '\ÉB	²gğ“VŠµ£§');
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
INSERT INTO `role` VALUES (_binary 'SÆİ·\ÊF	_…zC½3i','2025-10-26 11:27:48.311000',NULL,NULL,'Role for user','USER'),(_binary 'gU¼¹C\í¢ŒhŸ(ˆ9','2025-10-26 11:29:15.822000',NULL,NULL,'Role for admin','ADMIN');
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
  KEY `FKh0v7u4w7mttcu81o8wegayr8e` (`permission_id`),
  KEY `FKlodb7xh4a2xjv39gc3lsop95n` (`role_id`),
  CONSTRAINT `FKh0v7u4w7mttcu81o8wegayr8e` FOREIGN KEY (`permission_id`) REFERENCES `permission` (`id`),
  CONSTRAINT `FKlodb7xh4a2xjv39gc3lsop95n` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_permissions`
--

LOCK TABLES `role_permissions` WRITE;
/*!40000 ALTER TABLE `role_permissions` DISABLE KEYS */;
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (_binary '\Èhğ²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','S'),(_binary '\ÉB	²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','M'),(_binary '\ÉM%²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','L'),(_binary '\ÉMõ²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','XL'),(_binary '\ÉN9²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','2XL'),(_binary '\ÉNw²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','3XL'),(_binary '\ÉN°²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','4XL'),(_binary '\ÉN\å²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','25'),(_binary '\ÉO²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','26'),(_binary '\ÉOS²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','27'),(_binary '\ÉPn²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','28'),(_binary '\ÉR@²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','29'),(_binary '\ÉR¤²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','30'),(_binary '\ÉR\á²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','31'),(_binary '\ÉS\Z²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','32'),(_binary '\ÉSM²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','33'),(_binary '\ÉS‚²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','34'),(_binary '\ÉSÀ²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','35'),(_binary '\ÉT²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','36'),(_binary '\ÉTu²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','37'),(_binary '\ÉV²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','38'),(_binary '\ÉWˆ²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','39'),(_binary '\ÉWø²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','40'),(_binary '\ÉX^²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','42'),(_binary '\ÉX­²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','43'),(_binary '\ÉY²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','44'),(_binary '\ÉYV²gğ“VŠµ£§','2025-10-26 12:26:56.000000',NULL,'2025-10-26 12:26:56.000000','45'),(_binary '&¼\Ç	ED#Š’U\àø\ßP','2025-10-26 11:51:37.009000',NULL,NULL,'S'),(_binary 'J}l®\çF…€\Âü;z­ò','2025-10-26 11:51:28.910000',NULL,NULL,'M'),(_binary 'f¼’§ÁFA­£–l4\×\æ€','2025-10-26 11:51:45.506000',NULL,NULL,'28'),(_binary 'x+E\Ş²IcŠ>Ë€ó\ÚQ','2025-10-26 11:51:49.473000',NULL,NULL,'29'),(_binary '°VªIX£¼\ì\Ö\Î\Ğ\Ó','2025-10-26 11:51:53.753000',NULL,NULL,'30'),(_binary '\ë´^\èAÄ¿0\ïW?¿\á','2025-10-26 11:51:41.216000',NULL,NULL,'L');
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_themes`
--

DROP TABLE IF EXISTS `sub_themes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_themes` (
  `id` bigint NOT NULL,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `priority` int DEFAULT NULL,
  `collection_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbrqpnlo06se6s6vbfip9sweva` (`collection_id`),
  CONSTRAINT `FKbrqpnlo06se6s6vbfip9sweva` FOREIGN KEY (`collection_id`) REFERENCES `collection` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_themes`
--

LOCK TABLES `sub_themes` WRITE;
/*!40000 ALTER TABLE `sub_themes` DISABLE KEYS */;
/*!40000 ALTER TABLE `sub_themes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sub_themes_seq`
--

DROP TABLE IF EXISTS `sub_themes_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sub_themes_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sub_themes_seq`
--

LOCK TABLES `sub_themes_seq` WRITE;
/*!40000 ALTER TABLE `sub_themes_seq` DISABLE KEYS */;
INSERT INTO `sub_themes_seq` VALUES (1);
/*!40000 ALTER TABLE `sub_themes_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `code_expiry` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `locked` bit(1) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL,
  `provider` enum('FACEBOOK','GOOGLE','MANUAL') NOT NULL,
  `verification_code` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (_binary '¬\ÏÙŸıA„J“\áø¹?','2025-10-26 11:30:30.500000',NULL,'2025-10-26 11:34:07.988000',NULL,NULL,'admin@gmail.com',_binary '','Admin','Trendista',_binary '\0','{bcrypt}$2a$10$TpNtNA5JR5l3nQktkAbIGe1PvUTjrxI7h.Tc.fuVrfIby.CsNf87C','0987654321','MANUAL',NULL),(_binary '¥øh|¨L\Z˜r¡c¨','2025-10-26 13:00:24.728000',NULL,'2025-10-26 13:01:13.901000',NULL,NULL,'user@gmail.com',_binary '','User','Trendista',_binary '\0','{bcrypt}$2a$10$hPdAuV9ZSTRpjoML/yIyTOzcixnRs/ifyIYNvmSWTj1t69uy9nwiy','0987654321','MANUAL',NULL),(_binary '@­z]R@w€½şqR{\å','2025-10-26 13:06:45.738000',NULL,NULL,NULL,NULL,'vipboy15102002@gmail.com',_binary '','Lá»™c','Leo',_binary '\0',NULL,NULL,'GOOGLE',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_attempts`
--

DROP TABLE IF EXISTS `verification_attempts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `verification_attempts` (
  `id` binary(16) NOT NULL,
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `user_id` binary(16) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_attempts`
--

LOCK TABLES `verification_attempts` WRITE;
/*!40000 ALTER TABLE `verification_attempts` DISABLE KEYS */;
INSERT INTO `verification_attempts` VALUES (_binary 'e´G®~EM‡¾£¬\\ò(\Æ','2025-10-26 11:30:30.912000',NULL,NULL,'admin@gmail.com','eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0cmVuZGlzdGFTaG9wIiwic3ViIjoiYWRtaW5AZ21haWwuY29tIiwiaWF0IjoxNzYxNDc4MjMwLCJleHAiOjE3NjE1NjQ2MzAsInR5cGUiOiJ2ZXJpZmljYXRpb24ifQ.yK5m7KxiKpOMmAaDzDCOV7r6WdiqW9ynTk4C4aTfmDQ',_binary '¬\ÏÙŸıA„J“\áø¹?'),(_binary 'u\0òeƒH†‡~\àc^','2025-10-26 13:00:24.877000',NULL,NULL,'user@gmail.com','eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ0cmVuZGlzdGFTaG9wIiwic3ViIjoidXNlckBnbWFpbC5jb20iLCJpYXQiOjE3NjE0ODM2MjQsImV4cCI6MTc2MTU3MDAyNCwidHlwZSI6InZlcmlmaWNhdGlvbiJ9.MWeb-4DxO3J1UzhvWQQYNmKmJk5Z00LNwgm2E9dsraY',_binary '¥øh|¨L\Z˜r¡c¨');
/*!40000 ALTER TABLE `verification_attempts` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-10-26 20:19:31
