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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `banners`
--

LOCK TABLES `banners` WRITE;
/*!40000 ALTER TABLE `banners` DISABLE KEYS */;
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
INSERT INTO `category` VALUES (_binary '#b6�\�E\�g\�0+\�\�F','2024-12-19 14:33:12.437000','2024-12-19 14:33:12.437000','2024-12-19 14:33:12.437000','',NULL,'Áo ba lỗ - Áo hai dây nữ','ao-ba-lo-ao-hai-day-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'H��lxLAa�\�ukN�G'),(_binary '�X!�MD��\�oM\�a\�','2024-12-19 14:26:23.893000','2024-12-19 14:26:23.893000','2024-12-19 14:26:23.893000','',NULL,'Áo hoodie - Áo nỉ nữ','ao-hoodie-ao-ni-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'J�Ho�L��\�;N�\"�>'),(_binary '\"L�\�*M��7\�U{�s','2024-12-19 09:25:14.944000','2024-12-19 09:25:14.944000','2024-12-19 09:25:14.944000','Áo Polo Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734600314/CATEGORIES/CATEGORIES_391x719%20polo.webp','Áo Polo Nam','ao-polo-nam',_binary '߭2��\�\���\Zuѯ',_binary '0\�:\n\�(J���\�L?\�'),(_binary '(�\�*�	N��7\�K\�4|','2024-12-19 14:26:04.879000','2024-12-19 14:26:04.879000','2024-12-19 14:26:04.879000','',NULL,'Áo khoác nữ','ao-khoac-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'J�Ho�L��\�;N�\"�>'),(_binary '0\�:\n\�(J���\�L?\�','2024-12-19 09:16:59.479000','2024-12-19 09:16:59.479000','2024-12-19 09:16:59.479000','Áo Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734599819/CATEGORIES/CATEGORIES_AKM6017-NAU%20SKM7003-NAV%20QKM7007-NAU%20%285%29.webp','Áo Nam','ao-nam',_binary '߭2��\�\���\Zuѯ',NULL),(_binary 'BB�\�\�BHq�O`ɶ\ZA','2024-12-19 09:28:10.280000','2024-12-19 09:28:10.280000','2024-12-19 09:28:10.280000','Quần nam',NULL,'Quần nam','quan-nam',_binary '߭2��\�\���\Zuѯ',NULL),(_binary 'G̴^�r@j���\�e5\�\n','2024-12-19 09:35:25.521000','2024-12-19 09:35:25.521000','2024-12-19 09:35:25.521000','Quần kaki nam',NULL,'Quần kaki nam','quan-kaki-nam',_binary '߭2��\�\���\Zuѯ',_binary 'BB�\�\�BHq�O`ɶ\ZA'),(_binary 'H��lxLAa�\�ukN�G','2024-12-19 14:32:33.893000','2024-12-19 14:32:33.893000','2024-12-19 14:32:33.893000','',NULL,'Đồ mặc trong nữ','do-mac-trong-nu',_binary '߸w\�\�\���\Zuѯ',NULL),(_binary 'H�i\�	O0����\�','2024-12-19 09:29:35.535000','2024-12-19 09:29:35.535000','2024-12-19 09:29:35.535000','Phụ kiện nam',NULL,'Phụ kiện nam','phu-kien-nam',_binary '߭2��\�\���\Zuѯ',NULL),(_binary 'J�Ho�L��\�;N�\"�>','2024-12-19 14:22:54.521000','2024-12-19 14:22:54.521000','2024-12-19 14:22:54.521000','','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734618174/CATEGORIES/CATEGORIES_menu_woman.webp','Áo nữ','ao-nu',_binary '߸w\�\�\���\Zuѯ',NULL),(_binary 'L?�h�\�B�;\�*=;\�]','2024-12-19 14:28:20.490000','2024-12-19 14:28:20.490000','2024-12-19 14:28:20.490000','',NULL,'Áo gió nữ','ao-gio-nu',_binary '߸w\�\�\���\Zuѯ',_binary '(�\�*�	N��7\�K\�4|'),(_binary 'PQLA\�\�H-�3\�\\\�\�3','2024-12-19 14:25:26.376000','2024-12-19 14:25:26.376000','2024-12-19 14:25:26.376000','',NULL,'Áo vest nữ','ao-vest-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'J�Ho�L��\�;N�\"�>'),(_binary 'S���?NF��\�	\�y�c','2024-12-19 09:39:34.916000','2024-12-19 09:39:34.916000','2024-12-19 09:39:34.916000','Áo polo thể thao nam',NULL,'Áo polo thể thao nam','ao-polo-the-thao-nam',_binary '߭2��\�\���\Zuѯ',_binary '�\�J\�@��sF�xSs�'),(_binary 'Sۑ`:iK��P\�.\�`n','2024-12-19 09:40:00.225000','2024-12-19 09:40:00.225000','2024-12-19 09:40:00.225000','Bộ thể thao nam',NULL,'Bộ thể thao nam','bo-the-thao-nam',_binary '߭2��\�\���\Zuѯ',_binary '�\�J\�@��sF�xSs�'),(_binary 'WZe�m\�G��N��\�u*','2024-12-19 14:26:14.949000','2024-12-19 14:26:14.949000','2024-12-19 14:26:14.949000','',NULL,'Áo chống nắng nữ','ao-chong-nang-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'J�Ho�L��\�;N�\"�>'),(_binary 'i)\�\�2A~�7�	�[','2024-12-19 09:34:34.923000','2024-12-19 09:34:34.923000','2024-12-19 09:34:34.923000','Quần dài nam',NULL,'Quần dài nam','quan-dai-nam',_binary '߭2��\�\���\Zuѯ',_binary 'BB�\�\�BHq�O`ɶ\ZA'),(_binary 'y�\�\�|�F;�Rq\�OA','2024-12-19 14:26:54.412000','2024-12-19 14:26:54.412000','2024-12-19 14:26:54.412000','',NULL,'Áo thun nữ','ao-thun-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'J�Ho�L��\�;N�\"�>'),(_binary '�\�WdSZL��_�\�\n{\�','2024-12-19 14:28:06.887000','2024-12-19 14:28:06.887000','2024-12-19 14:28:06.887000','',NULL,'Áo phao nữ','ao-phao-nu',_binary '߸w\�\�\���\Zuѯ',_binary '(�\�*�	N��7\�K\�4|'),(_binary '�\�#VzKÑ\�}a�\�','2024-12-19 09:21:59.277000','2024-12-19 09:21:59.277000','2024-12-19 09:21:59.277000','Áo Thun Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734600118/CATEGORIES/CATEGORIES_thun%20391x719.webp','Áo Thun Nam','ao-thun-nam',_binary '߭2��\�\���\Zuѯ',_binary '0\�:\n\�(J���\�L?\�'),(_binary '��q\�r�K\"�\\�7}\'','2024-12-19 09:20:05.059000','2024-12-19 09:20:05.059000','2024-12-19 09:20:05.059000','Áo Khoác Nam','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734600004/CATEGORIES/CATEGORIES_ao-khoac-391x719.webp','Áo Khoác Nam','ao-khoac-nam',_binary '߭2��\�\���\Zuѯ',_binary '0\�:\n\�(J���\�L?\�'),(_binary '��d�s�E��\�1\�Yn\�','2024-12-19 14:29:20.181000','2024-12-19 14:29:20.181000','2024-12-19 14:29:20.181000','',NULL,'Quần dài nữ','quan-dai-nu',_binary '߸w\�\�\���\Zuѯ',_binary '�z��rBʗȷ>\0>V'),(_binary '�\�J\�@��sF�xSs�','2024-12-19 09:29:11.791000','2024-12-19 09:29:11.791000','2024-12-19 09:29:11.791000','Đồ thể thao nam',NULL,'Đồ thể thao nam','do-the-thao-nam',_binary '߭2��\�\���\Zuѯ',NULL),(_binary '�\�h�JҮ��5p\�\�','2024-12-19 14:29:35.975000','2024-12-19 14:29:35.975000','2024-12-19 14:29:35.975000','',NULL,'Quần kaki nữ','quan-kaki-nu',_binary '߸w\�\�\���\Zuѯ',_binary '�z��rBʗȷ>\0>V'),(_binary '���\�\�KD�]^�:d\�\�','2024-12-19 09:38:48.011000','2024-12-19 09:38:48.011000','2024-12-19 09:38:48.011000','Quần thể thao nam',NULL,'Quần thể thao nam','quan-the-thao-nam',_binary '߭2��\�\���\Zuѯ',_binary '�\�J\�@��sF�xSs�'),(_binary '�z��rBʗȷ>\0>V','2024-12-19 14:28:51.017000','2024-12-19 14:28:51.017000','2024-12-19 14:28:51.017000','',NULL,'Quần nữ','quan-nu',_binary '߸w\�\�\���\Zuѯ',NULL),(_binary '�%JU\ZK�\�\�F�\�','2024-12-19 09:35:48.791000','2024-12-19 09:35:48.791000','2024-12-19 09:35:48.791000','Quần short nam',NULL,'Quần short nam','quan-short-nam',_binary '߭2��\�\���\Zuѯ',_binary 'BB�\�\�BHq�O`ɶ\ZA'),(_binary '�)\�\�D��/\rl�a�','2024-12-19 09:36:11.259000','2024-12-19 09:36:11.259000','2024-12-19 09:36:11.259000','Quần âu nam',NULL,'Quần âu nam','quan-au-nam',_binary '߭2��\�\���\Zuѯ',_binary 'BB�\�\�BHq�O`ɶ\ZA'),(_binary '�\�\�PJd��y\�Y\�M','2024-12-19 14:25:55.451000','2024-12-19 14:25:55.451000','2024-12-19 14:25:55.451000','',NULL,'Áo polo nữ','ao-polo-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'J�Ho�L��\�;N�\"�>'),(_binary '�;j\n\�J��\�\�\�;�h','2024-12-19 09:28:40.802000','2024-12-19 09:28:40.802000','2024-12-19 09:28:40.802000','Đồ bộ nam',NULL,'Đồ bộ nam','do-bo-nam',_binary '߭2��\�\���\Zuѯ',NULL),(_binary '\�c\���G�\��ҧK;','2024-12-19 09:37:18.175000','2024-12-19 09:37:18.175000','2024-12-19 09:37:18.175000','Đồ bộ dài tay nam',NULL,'Đồ bộ dài tay nam','do-bo-dai-tay-nam',_binary '߭2��\�\���\Zuѯ',_binary '�;j\n\�J��\�\�\�;�h'),(_binary 'ѹ%U�IP��|Α�M','2024-12-19 14:29:29.164000','2024-12-19 14:29:29.164000','2024-12-19 14:29:29.164000','',NULL,'Quần nỉ nữ','quan-ni-nu',_binary '߸w\�\�\���\Zuѯ',_binary '�z��rBʗȷ>\0>V'),(_binary '\�\�xJ�EJ���\�-s߹�','2024-12-19 14:32:58.868000','2024-12-19 14:32:58.868000','2024-12-19 14:32:58.868000','',NULL,'Áo bra nữ','ao-bra-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'H��lxLAa�\�ukN�G'),(_binary '\�\�\�S,�G���{�\\\�I\�','2024-12-19 09:38:03.447000','2024-12-19 09:38:03.447000','2024-12-19 09:38:03.447000','Đồ bộ ngắn tay nam',NULL,'Đồ bộ ngắn tay nam','do-bo-ngan-tay-nam',_binary '߭2��\�\���\Zuѯ',_binary '�;j\n\�J��\�\�\�;�h'),(_binary '\�b̏A\��B\�Nv�\�','2024-12-19 14:26:37.773000','2024-12-19 14:26:37.773000','2024-12-19 14:26:37.773000','',NULL,'Áo sơ mi nữ','ao-so-mi-nu',_binary '߸w\�\�\���\Zuѯ',_binary 'J�Ho�L��\�;N�\"�>');
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
INSERT INTO `category_discount` VALUES (_binary '\"L�\�*M��7\�U{�s',_binary '|x�\�9�E��3;dO\�\�o');
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
INSERT INTO `color` VALUES (_binary '5\Z�JBLF�0����7x','2024-12-19 04:05:11.388000','2024-12-19 04:05:11.388000','2024-12-19 04:05:11.388000','Trắng','#FFFFFF','white'),(_binary 'G���\�Nī?�\ZL\\�e','2024-12-19 04:05:24.870000','2024-12-19 04:05:24.870000','2024-12-19 04:05:24.870000','Xám','#808080','gray'),(_binary 'K\"\Z\�]�E��\�S�\�}{','2024-12-19 04:05:40.381000','2024-12-19 04:05:40.381000','2024-12-19 04:05:40.381000','Hồng','#FFC0CB','pink'),(_binary '�e�v4J/�6�\�Z\�m','2024-12-19 04:05:06.945000','2024-12-19 04:05:06.945000','2024-12-19 04:05:06.945000','Đen','#000000','black'),(_binary '���JK@\�H�1\�\�\�','2024-12-19 04:05:18.796000','2024-12-19 04:05:18.796000','2024-12-19 04:05:18.796000','Vàng','#FFFF00','yellow'),(_binary '\�3��+_O쌝\�4\�\�','2024-12-19 04:05:01.000000','2024-12-19 04:05:01.000000','2024-12-19 04:05:01.000000','Xanh lá','#008000','green'),(_binary 'ў3�OA�M\�Lش\�','2024-12-19 04:05:29.185000','2024-12-19 04:05:29.185000','2024-12-19 04:05:29.185000','Cam','#FFA500','orange'),(_binary '\�)a�/\�D���F\Z�\�1','2024-12-19 04:04:47.673000','2024-12-19 04:04:47.673000','2024-12-19 04:04:47.673000','Đỏ','#FF0000','red'),(_binary '�\�3<\\J��B0<3���','2024-12-19 04:04:55.066000','2024-12-19 04:04:55.066000','2024-12-19 04:04:55.066000','Xanh dương','#0000FF','blue');
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
INSERT INTO `discount` VALUES (_binary 'S��Ώ\"J柉�(B\�L\�','Áp dụng cho các mặt hàng áo polo nam mùa đông  2024','PERCENT',15.00,'2024-12-25 22:29:00.103000','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734700943/Discount%20Frame/Discount%20Frame_pngtree-product-border-orange-frame-promotion-big-sale-png-image_6134236.png',_binary '',30000.00,0.00,'Sale Noel 2024 V1 ','2024-12-20 22:29:00.103000'),(_binary '|x�\�9�E��3;dO\�\�o','Áp dụng cho các mặt hàng áo polo nam mùa đông  2024','PERCENT',15.00,'2024-12-25 22:29:00.103000','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734700943/Discount%20Frame/Discount%20Frame_pngtree-product-border-orange-frame-promotion-big-sale-png-image_6134236.png',_binary '',30000.00,0.00,'Sale Noel 2024 ','2024-12-20 22:29:00.103000');
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
INSERT INTO `gender` VALUES (_binary '��F�\�\���\Zuѯ','Unisex',NULL,'unisex'),(_binary '߭2��\�\���\Zuѯ','Nam',NULL,'nam'),(_binary '߸w\�\�\���\Zuѯ','Nữ',NULL,'nu'),(_binary '�i\"Ͻ\�\���\Zuѯ','Trẻ Em',NULL,'tre-em');
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
INSERT INTO `permission` VALUES (_binary '$\�\�\�Ls�\�*K9A\�','/address/**','POST','USER CREATE ADDRESS'),(_binary 'u6�G��\�L\�(@S','/cart-order/create','POST','USER CREATE ORDER'),(_binary '|B���\�Nw�3(\�\�<','/cart-order/user','GET','USER GET ORDER'),(_binary '}1*�\�\�@��7\�ogx','/cart-order/cancel/**','PUT','USER CANCEL ORDER'),(_binary '�\�y̞L���&�f�u','/user/**','GET','USER READ ACCOUNT');
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
INSERT INTO `product` VALUES (_binary ' \�`Bk�09�\�]','2024-12-19 14:14:59.197000','2024-12-19 14:14:59.197000','2024-12-19 14:15:04.973000','SWF205','TQuần gió YOGUU vải nhăn tự nhiên, mềm mại tạo cảm giác thoải mái tối đa. Nhẹ tênh, bền màu, định hình form dáng tốt mang đến vẻ ngoài thời trang. Thiết kế hiện đại, phối viền phản quang cá tính. Phù hợp với nhiều phong cách từ thể thao đến dạo phố. Giặt máy tiện lợi.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Quần Gió Dài Yoguu Phối Viền Phản Quang',250000.00,150000.00,0,0,'quan-gio-dai-yoguu-phoi-vien-phan-quang',_binary '','Thoải mái vận động với quần gió dài YOGUU chất liệu Guu cao cấp. Vải nhăn tự nhiên, bền màu, form dáng chuẩn. Thiết kế túi sườn tiện lợi, phù hợp nhiều phong cách. Giặt máy dễ dàng, nhanh khô.','HOT_DEALS',0,0,_binary 'i)\�\�2A~�7�	�['),(_binary '�\�XW3N߷\r7w\�d','2024-12-19 13:55:02.337000','2024-12-19 13:55:02.337000','2024-12-23 09:58:22.509000','UFB450','CHẤT LIỆU AIRY COOL - CÔNG NGHỆ VẢI THẾ HỆ MỚI Thấu hiểu mong muốn sử dụng những sả phẩm thời trang thoáng mát, thấm hút tốt cho mùa hè, đội ngũ YODY nghiên cứu và cho rời đời sản phẩm áo polo Airy Cool với những tính năng vượt trội. Công nghệ vải FREEZING tiên tiến, hạ nhiệt cho ngày hè.Sản phẩm được thiết kế với 85% Nylon và 15% Spandex. Trong đó, sợi Nylon là cấu tạo chính giúp tạo cảm giác thoải mái, dễ chịu khi mặc.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616502/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',NULL,'Áo Polo Thể Thao Nam Airy Cool Basic',105000.00,89250.00,0,0,'ao-polo-the-thao-nam-airy-cool-basic',_binary '','Thoải mái tập luyện nhờ công nghệ Airy cool tiến tiến giúp giải nhiệt cơ thể cho ngày hè mát mẻ. Kết cấu vải mềm mịn, khô nhanh. Thông thoáng, thấm hút mồ hôi tốt. Khả năng co giãn, giữ form tốt.','BEST_SELLERS',0,0,_binary '\"L�\�*M��7\�U{�s'),(_binary 'H��qE��\nm�\�R;','2024-12-19 13:46:28.315000','2024-12-19 13:46:28.315000','2024-12-19 13:46:31.914000','KXG917','Mùa hè nóng bức sẽ luôn cần đến những chiếc áo thun vừa nhẹ nhàng vừa thoáng mát để giảm bớt cái bí bách. Ngoài việc sử dụng các thiết bị làm mát, giải pháp hữu hiệu mà không phải ai cũng chú trọng đó là tìm đến những sản phẩm mặc thoáng, có độ thấm hút cao. Áo thun YODY ra đời với mục tiêu này. Những chiếc áo thun trải qua quá trình nghiên cứu bền bỉ của đội ngũ thiết kế nay đã được ra mắt sở hữu những ưu điểm nổi bật về giá cả và tính năng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615988/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',NULL,'Polo Nam Cafe Bo 3 Màu',149000.00,99000.00,0,0,'polo-nam-cafe-bo-3-mau',_binary '','Thiết kế dáng suông basic khoẻ khoắn. Áo tạo điểm nhấn ở phần phối cổ 3 màu, bo tay cùng hình thêu chú thỏ YODY trước ngực độc đáo. Chất liệu thấm hút mồ hôi, kháng khuẩn và khử mùi cơ thể cực tốt và bảo vệ khỏi tia UV đến 98%.','BEST_SELLERS',0,0,_binary '\"L�\�*M��7\�U{�s'),(_binary 'q!�\�\�Go�k\�Q��','2024-12-19 14:44:29.608000','2024-12-19 14:44:29.608000','2024-12-19 14:44:37.276000','LBX195','Vest gile nữ với chất liệu double face kim cương độc đáo, không nhăn, không xù. Vải co giãn nhẹ tạo cảm giác thoải mái. Thiết kế túi cơi tiện dụng tôn lên vẻ đẹp hiện đại, thanh lịch. Là sự lựa chọn hoàn hảo cho những quý cô sành điệu.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619470/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',NULL,'Vest Nữ Gile Cúc Bọc Túi Cơi',590000.00,499000.00,0,0,'vest-nu-gile-cuc-boc-tui-coi',_binary '','Vest gile nữ với chất liệu double face kim cương độc đáo, không nhăn, không xù. Vải co giãn nhẹ tạo cảm giác thoải mái. Thiết kế túi cơi tiện dụng tôn lên vẻ đẹp hiện đại, thanh lịch. Là sự lựa chọn hoàn hảo cho những quý cô sành điệu.','TRENDING',0,0,_binary 'PQLA\�\�H-�3\�\\\�\�3'),(_binary '�uv\�E$DY�$\�m�4he','2024-12-19 13:34:27.039000','2024-12-19 13:34:27.039000','2024-12-19 13:34:35.217000','ZBE174','Mùa hè nóng bức sẽ luôn cần đến những chiếc áo thun vừa nhẹ nhàng vừa thoáng mát để giảm bớt cái bí bách. Ngoài việc sử dụng các thiết bị làm mát, giải pháp hữu hiệu mà không phải ai cũng chú trọng đó là tìm đến những sản phẩm mặc thoáng, có độ thấm hút cao. Áo thun YODY ra đời với mục tiêu này. Những chiếc áo thun trải qua quá trình nghiên cứu bền bỉ của đội ngũ thiết kế nay đã được ra mắt sở hữu những ưu điểm nổi bật về giá cả và tính năng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615267/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',NULL,'T-shirt Nam Slimfit Thun Rib Cotton Mềm',149000.00,130000.00,0,0,'t-shirt-nam-slimfit-thun-rib-cotton-mem',_binary '','Everyday Basics: SẢN PHẨM TỐT - GIÁ TRẢI NGHIỆM Mềm mại - Thấm hút - Co giãn hiệu quả. Áo thun nam basic YODY là bí kíp cho mùa hè thoải mái của các anh. Một thiết kế đơn giản, bảng màu phong phú và tối ưu sự thoải mái trong thời tiết nắng nóng mùa hè. Sở hữu ngay nhé!','NEW_ARRIVALS',0,0,_binary '�\�#VzKÑ\�}a�\�'),(_binary '��\�\�csO��}fkx','2024-12-19 14:50:48.144000','2024-12-19 14:50:48.144000','2024-12-19 14:50:56.344000','JGI879','Vest gile nữ với chất liệu double face kim cương độc đáo, không nhăn, không xù. Vải co giãn nhẹ tạo cảm giác thoải mái. Thiết kế túi cơi tiện dụng tôn lên vẻ đẹp hiện đại, thanh lịch. Là sự lựa chọn hoàn hảo cho những quý cô sành điệu.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619848/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',NULL,'Vest Nữ Dáng Blazer Nắp Túi Xẻ Sau',899000.00,800000.00,0,0,'vest-nu-dang-blazer-nap-tui-xe-sau',_binary '','Vest gile nữ với chất liệu double face kim cương độc đáo, không nhăn, không xù. Vải co giãn nhẹ tạo cảm giác thoải mái. Thiết kế túi cơi tiện dụng tôn lên vẻ đẹp hiện đại, thanh lịch. Là sự lựa chọn hoàn hảo cho những quý cô sành điệu.','TRENDING',0,0,_binary 'PQLA\�\�H-�3\�\\\�\�3'),(_binary '\�ZR��3Bg�H\nn~h%','2024-12-19 14:10:40.495000','2024-12-19 14:10:40.495000','2024-12-19 14:10:47.051000','WPE143','Thấu hiểu mong muốn sử dụng những sản phẩm thời trang thoáng mát, thấm hút tốt cho mùa hè, đội ngũ YODY nghiên cứu và cho rời đời sản phẩm áo polo Airy Cool với những tính năng vượt trội. Công nghệ vải FREEZING tiên tiến, hạ nhiệt cho ngày hè.Sản phẩm được thiết kế với 85% Nylon và 15% Spandex. Trong đó, sợi Nylon là cấu tạo chính giúp tạo cảm giác thoải mái, dễ chịu khi mặc.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617440/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',NULL,'Quần Gió Dài Yoguu Bổ Thân Có Túi Sườn',300000.00,170000.00,0,0,'quan-gio-dai-yoguu-bo-than-co-tui-suon',_binary '','Thoải mái vận động với quần gió dài YOGUU chất liệu Guu cao cấp. Vải nhăn tự nhiên, bền màu, form dáng chuẩn. Thiết kế túi sườn tiện lợi, phù hợp nhiều phong cách. Giặt máy dễ dàng, nhanh khô.','HOT_DEALS',0,0,_binary 'i)\�\�2A~�7�	�['),(_binary '׸d�L��U��k׻\�','2024-12-19 13:17:21.647000','2024-12-19 13:17:21.647000','2024-12-19 13:17:29.728000','JPF593','<p><strong>Chất liệu đẳng cấp:</strong><br>Được làm từ 100% Cotton USA cao cấp, áo T-shirt mang lại cảm giác mềm mại, thoáng mát và dễ chịu suốt cả ngày dài. Chất liệu cotton tự nhiên giúp thấm hút mồ hôi tốt, không gây kích ứng da, phù hợp cho mọi loại thời tiết.</p><figure class=\"image\"><img style=\"aspect-ratio:1676/2276;\" src=\"https://bizweb.dktcdn.net/100/438/408/files/cotton-usa-01-c6e1caef-f6f9-4116-b198-f1f2136418cb.jpg?v=1687402779123\" width=\"1676\" height=\"2276\"></figure><p><strong>Thiết kế tối giản, sang trọng:</strong><br>Phiên bản Premium được thiết kế với phong cách tối giản nhưng tinh tế, dễ dàng phối hợp với nhiều loại trang phục từ quần jeans, quần short đến quần âu. Đường may tỉ mỉ, form dáng chuẩn, tôn lên sự khỏe khoắn và lịch lãm của phái mạnh.</p><p><strong>Độ bền vượt trội:</strong><br>Áo không chỉ đẹp mà còn bền bỉ với thời gian nhờ công nghệ xử lý vải hiện đại, giúp chống co rút, chống nhăn và giữ màu lâu. Bạn có thể yên tâm sử dụng áo trong nhiều lần giặt mà vẫn giữ được phom dáng ban đầu.</p>','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614242/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',NULL,'Áo Tshirt Nam Cotton USA Phiên Bản Premium',80000.00,50000.00,0,0,'ao-tshirt-nam-cotton-usa-phien-ban-premium',_binary '','Thiết kế cổ tròn cơ bản cùng dáng suông giúp tạo sự thoải mái cử động cho người mặc. Sợi cotton chất lượng cao với độ mảnh và khả năng nhuộm ưu việt. Đa năng, màu sắc đa dạng lựa chọn.','NEW_ARRIVALS',0,0,_binary '�\�#VzKÑ\�}a�\�');
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
INSERT INTO `product_discount` VALUES (_binary '�\�XW3N߷\r7w\�d',_binary 'S��Ώ\"J柉�(B\�L\�');
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
INSERT INTO `product_image` VALUES (_binary ' j�?-@��Ua\�\��?','2024-12-19 14:50:53.040000','2024-12-19 14:50:53.040000','2024-12-19 14:50:53.040000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619852/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\Z�JBLF�0����7x',_binary '��\�\�csO��}fkx'),(_binary 't4�LN�O\��\�\�','2024-12-19 14:15:04.173000','2024-12-19 14:15:04.173000','2024-12-19 14:15:04.173000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617703/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary ' \�`Bk�09�\�]'),(_binary '�\�\�{Ah�����\�g','2024-12-19 13:34:34.297000','2024-12-19 13:34:34.297000','2024-12-19 13:34:34.297000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615273/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '�\�3<\\J��B0<3���',_binary '�uv\�E$DY�$\�m�4he'),(_binary '\�v\��K��:\�e��','2024-12-19 13:17:27.377000','2024-12-19 13:17:27.377000','2024-12-19 13:17:27.377000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614246/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary '���JK@\�H�1\�\�\�',_binary '׸d�L��U��k׻\�'),(_binary 'p\�S\�L\n��}Ϡ','2024-12-19 13:34:28.911000','2024-12-19 13:34:28.911000','2024-12-19 13:34:28.911000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615268/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�uv\�E$DY�$\�m�4he'),(_binary '~\�1�\�OT�Ui\�\�w\�\�','2024-12-19 13:17:22.388000','2024-12-19 13:17:22.388000','2024-12-19 13:17:22.388000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614242/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\�)a�/\�D���F\Z�\�1',_binary '׸d�L��U��k׻\�'),(_binary 'jIأ�Nv�ܭ�O3�\�','2024-12-19 14:50:49.233000','2024-12-19 14:50:49.233000','2024-12-19 14:50:49.233000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619848/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '��\�\�csO��}fkx'),(_binary '\��9��IøQX\�|�H','2024-12-19 14:15:02.392000','2024-12-19 14:15:02.392000','2024-12-19 14:15:02.392000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617701/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary ' \�`Bk�09�\�]'),(_binary '\"����\�I��/1r&%>P','2024-12-19 13:34:27.943000','2024-12-19 13:34:27.943000','2024-12-19 13:34:27.943000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615267/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�uv\�E$DY�$\�m�4he'),(_binary '\"ߗJ=VK���\��q�i�','2024-12-19 13:34:29.946000','2024-12-19 13:34:29.946000','2024-12-19 13:34:29.946000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615269/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�uv\�E$DY�$\�m�4he'),(_binary '*�9��}J\��ʍ6=*\�\�','2024-12-19 13:17:24.296000','2024-12-19 13:17:24.296000','2024-12-19 13:17:24.296000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614243/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\�)a�/\�D���F\Z�\�1',_binary '׸d�L��U��k׻\�'),(_binary '-����O5�\���0\�','2024-12-19 13:55:06.481000','2024-12-19 13:55:06.481000','2024-12-19 13:55:06.481000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616505/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�\�XW3N߷\r7w\�d'),(_binary '/\�^\�_�A��\�@��\�w','2024-12-19 13:55:07.443000','2024-12-19 13:55:07.443000','2024-12-19 13:55:07.443000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616506/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�\�XW3N߷\r7w\�d'),(_binary '4\���d�K��Ƅ�z\�\�\�','2024-12-19 13:46:31.911000','2024-12-19 13:46:31.911000','2024-12-19 13:46:31.911000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615991/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\Z�JBLF�0����7x',_binary 'H��qE��\nm�\�R;'),(_binary '9\0\��aK���\�\�]t','2024-12-19 14:50:52.138000','2024-12-19 14:50:52.138000','2024-12-19 14:50:52.138000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619851/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '��\�\�csO��}fkx'),(_binary '9q\�J�\�M0��\���l','2024-12-19 13:17:28.503000','2024-12-19 13:17:28.503000','2024-12-19 13:17:28.503000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614247/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary '���JK@\�H�1\�\�\�',_binary '׸d�L��U��k׻\�'),(_binary ';�\�l�SOĺ\�\�ְ\�r6','2024-12-19 14:10:45.724000','2024-12-19 14:10:45.724000','2024-12-19 14:10:45.724000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617445/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary '\�ZR��3Bg�H\nn~h%'),(_binary '=��	�aIY��[m%/�','2024-12-19 14:44:37.274000','2024-12-19 14:44:37.274000','2024-12-19 14:44:37.274000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619476/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary 'q!�\�\�Go�k\�Q��'),(_binary 'D\�27.{C7�\�\�W�\'s\�','2024-12-19 14:50:56.333000','2024-12-19 14:50:56.333000','2024-12-19 14:50:56.333000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619855/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\Z�JBLF�0����7x',_binary '��\�\�csO��}fkx'),(_binary 'GoJs�D�~c��!','2024-12-19 14:44:36.089000','2024-12-19 14:44:36.089000','2024-12-19 14:44:36.089000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619475/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary 'q!�\�\�Go�k\�Q��'),(_binary 'J��&>@ɔs\�4k8(0','2024-12-19 13:46:29.370000','2024-12-19 13:46:29.370000','2024-12-19 13:46:29.370000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615988/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\Z�JBLF�0����7x',_binary 'H��qE��\nm�\�R;'),(_binary 'Q&\�\�\\IE����\�LL�','2024-12-19 13:34:31.142000','2024-12-19 13:34:31.142000','2024-12-19 13:34:31.142000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615270/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�uv\�E$DY�$\�m�4he'),(_binary 'R\Z����LȚ�]�F��','2024-12-19 13:55:05.500000','2024-12-19 13:55:05.500000','2024-12-19 13:55:05.500000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616504/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�\�XW3N߷\r7w\�d'),(_binary 'R/��\�\�JƟӿ �\�!','2024-12-19 13:55:03.580000','2024-12-19 13:55:03.580000','2024-12-19 13:55:03.580000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616502/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '�\�XW3N߷\r7w\�d'),(_binary 'UV\�\�\�AG����\n�~�','2024-12-19 14:10:41.476000','2024-12-19 14:10:41.476000','2024-12-19 14:10:41.476000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617440/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '\�ZR��3Bg�H\nn~h%'),(_binary 'X\� $�\�H�����*U','2024-12-19 13:17:25.434000','2024-12-19 13:17:25.434000','2024-12-19 13:17:25.434000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614244/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\�)a�/\�D���F\Z�\�1',_binary '׸d�L��U��k׻\�'),(_binary '^.\n,zLI�sՆ3#','2024-12-19 13:46:30.221000','2024-12-19 13:46:30.221000','2024-12-19 13:46:30.221000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615989/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\Z�JBLF�0����7x',_binary 'H��qE��\nm�\�R;'),(_binary 'b}-�\�Fَ\�\�1iORE','2024-12-19 14:15:04.971000','2024-12-19 14:15:04.971000','2024-12-19 14:15:04.971000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617704/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary ' \�`Bk�09�\�]'),(_binary 'e\��\�\�D��\�X\���','2024-12-19 13:17:29.714000','2024-12-19 13:17:29.714000','2024-12-19 13:17:29.714000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614248/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary '���JK@\�H�1\�\�\�',_binary '׸d�L��U��k׻\�'),(_binary 'gB\�$��E\�3��W\��','2024-12-19 14:10:44.128000','2024-12-19 14:10:44.128000','2024-12-19 14:10:44.128000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617443/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '\�ZR��3Bg�H\nn~h%'),(_binary 'g\�^���Oh���OuH��','2024-12-19 14:10:47.037000','2024-12-19 14:10:47.037000','2024-12-19 14:10:47.037000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617446/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary '\�ZR��3Bg�H\nn~h%'),(_binary 'k\"�i)NF�j\�4I\\h`','2024-12-19 14:50:54.172000','2024-12-19 14:50:54.172000','2024-12-19 14:50:54.172000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619853/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\Z�JBLF�0����7x',_binary '��\�\�csO��}fkx'),(_binary 'y(izAG9�\�O;g,X�','2024-12-19 13:55:08.143000','2024-12-19 13:55:08.143000','2024-12-19 13:55:08.143000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734616507/PRODUCTS/%C3%81o_Polo_Th%E1%BB%83_Thao_Nam_Airy_Cool_Basic_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary '�\�XW3N߷\r7w\�d'),(_binary '\"U\�_K��m\�.�\�\�','2024-12-19 13:34:32.120000','2024-12-19 13:34:32.120000','2024-12-19 13:34:32.120000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615271/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '�\�3<\\J��B0<3���',_binary '�uv\�E$DY�$\�m�4he'),(_binary '�\�\�p\�\�FO�\�3\�P��','2024-12-19 14:15:01.404000','2024-12-19 14:15:01.404000','2024-12-19 14:15:01.404000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617700/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary ' \�`Bk�09�\�]'),(_binary '�T\�0\�*G��<�2�0F)','2024-12-19 13:34:33.019000','2024-12-19 13:34:33.019000','2024-12-19 13:34:33.019000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615272/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '�\�3<\\J��B0<3���',_binary '�uv\�E$DY�$\�m�4he'),(_binary '�?AHb�Dٳ�]c�k\�T','2024-12-19 14:44:35.169000','2024-12-19 14:44:35.169000','2024-12-19 14:44:35.169000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619474/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary 'q!�\�\�Go�k\�Q��'),(_binary '��R�\�G��+��\��','2024-12-19 13:17:26.208000','2024-12-19 13:17:26.208000','2024-12-19 13:17:26.208000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614245/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Yellow_.webp',_binary '���JK@\�H�1\�\�\�',_binary '׸d�L��U��k׻\�'),(_binary '�R\�l�	OÏI�\�N\�Л','2024-12-19 14:10:42.419000','2024-12-19 14:10:42.419000','2024-12-19 14:10:42.419000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617441/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '\�ZR��3Bg�H\nn~h%'),(_binary '\�\�Fvk�O ��\�\'\��\�r','2024-12-19 14:50:55.035000','2024-12-19 14:50:55.035000','2024-12-19 14:50:55.035000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619854/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_White_.webp',_binary '5\Z�JBLF�0����7x',_binary '��\�\�csO��}fkx'),(_binary '\�\����\�M��	}\�\��\�','2024-12-19 13:17:23.197000','2024-12-19 13:17:23.197000','2024-12-19 13:17:23.197000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734614242/PRODUCTS/%C3%81o_Tshirt_Nam_Cotton_USA_Phi%C3%AAn_B%E1%BA%A3n_Premium_Red_.webp',_binary '\�)a�/\�D���F\Z�\�1',_binary '׸d�L��U��k׻\�'),(_binary '\�\�\�.\�C_�\�b4J\"\�','2024-12-19 14:10:43.316000','2024-12-19 14:10:43.316000','2024-12-19 14:10:43.316000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617442/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '\�ZR��3Bg�H\nn~h%'),(_binary 'Ί�R&@r�V[\�,\�R','2024-12-19 14:15:03.256000','2024-12-19 14:15:03.256000','2024-12-19 14:15:03.256000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617702/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary ' \�`Bk�09�\�]'),(_binary '\�ǃ?j\�C��ē{����','2024-12-19 13:46:31.015000','2024-12-19 13:46:31.015000','2024-12-19 13:46:31.015000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615990/PRODUCTS/Polo_Nam_Cafe_Bo_3_Ma%CC%80u_White_.webp',_binary '5\Z�JBLF�0����7x',_binary 'H��qE��\nm�\�R;'),(_binary '\�^R?�XF\�M\�jJBH�','2024-12-19 14:44:30.979000','2024-12-19 14:44:30.979000','2024-12-19 14:44:30.979000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619470/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary 'q!�\�\�Go�k\�Q��'),(_binary '\�}\�\�K�A���}5z��','2024-12-19 14:44:31.939000','2024-12-19 14:44:31.939000','2024-12-19 14:44:31.939000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619471/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary 'q!�\�\�Go�k\�Q��'),(_binary '\��|\�SFl��\�;�GcA','2024-12-19 14:44:32.766000','2024-12-19 14:44:32.766000','2024-12-19 14:44:32.766000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619472/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary 'q!�\�\�Go�k\�Q��'),(_binary '\���hFu���p�A\�','2024-12-19 14:44:34.225000','2024-12-19 14:44:34.225000','2024-12-19 14:44:34.225000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619473/PRODUCTS/Vest_N%E1%BB%AF_Gile_C%C3%BAc_B%E1%BB%8Dc_T%C3%BAi_C%C6%A1i_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary 'q!�\�\�Go�k\�Q��'),(_binary 'ẋ�\�aC��?Q�J','2024-12-19 13:34:35.215000','2024-12-19 13:34:35.215000','2024-12-19 13:34:35.215000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734615274/PRODUCTS/T-shirt_Nam_Slimfit_Thun_Rib_Cotton_M%E1%BB%81m_Blue_.webp',_binary '�\�3<\\J��B0<3���',_binary '�uv\�E$DY�$\�m�4he'),(_binary '\�.^��\�J\Z�\�\n\�!','2024-12-19 14:15:00.286000','2024-12-19 14:15:00.286000','2024-12-19 14:15:00.286000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary ' \�`Bk�09�\�]'),(_binary '\�\�v|�Go��\�a^1M','2024-12-19 14:50:50.080000','2024-12-19 14:50:50.080000','2024-12-19 14:50:50.080000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619849/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '��\�\�csO��}fkx'),(_binary '\�;�Q\�\�@���)	\�a�b','2024-12-19 14:50:50.984000','2024-12-19 14:50:50.984000','2024-12-19 14:50:50.984000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734619850/PRODUCTS/Vest_N%E1%BB%AF_D%C3%A1ng_Blazer_N%E1%BA%AFp_T%C3%BAi_X%E1%BA%BB_Sau_Black_.webp',_binary '�e�v4J/�6�\�Z\�m',_binary '��\�\�csO��}fkx'),(_binary '��\��\�F��rp�c�','2024-12-19 14:10:44.943000','2024-12-19 14:10:44.943000','2024-12-19 14:10:44.943000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617444/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_B%E1%BB%95_Th%C3%A2n_C%C3%B3_T%C3%BAi_S%C6%B0%E1%BB%9Dn_Gray_.webp',_binary 'G���\�Nī?�\ZL\\�e',_binary '\�ZR��3Bg�H\nn~h%');
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
INSERT INTO `product_product_variants` VALUES (_binary ' \�`Bk�09�\�]',_binary '�\\}��L+���;\�p('),(_binary ' \�`Bk�09�\�]',_binary '-Ȇ��Hm��r�\Z8u�'),(_binary ' \�`Bk�09�\�]',_binary 'hHo�5�M��\��o4��~'),(_binary ' \�`Bk�09�\�]',_binary 'Ś��V�L\�!\�S\�\�z'),(_binary '�\�XW3N߷\r7w\�d',_binary '�\�\�)\�Cۚ\�P\�6o'),(_binary '�\�XW3N߷\r7w\�d',_binary 'M#��N�A��ҞTr�H'),(_binary '�\�XW3N߷\r7w\�d',_binary '[\�5\�\�#AG�19POk �'),(_binary '�\�XW3N߷\r7w\�d',_binary '�n7\�7L\�j���>\�\�'),(_binary 'H��qE��\nm�\�R;',_binary 'u�`�aIȬҚ\��\�I'),(_binary 'H��qE��\nm�\�R;',_binary '���\�alO}�\�f�L'),(_binary 'q!�\�\�Go�k\�Q��',_binary '\�fH4�N��W����'),(_binary 'q!�\�\�Go�k\�Q��',_binary 'q�N�{[Jׁi'),(_binary 'q!�\�\�Go�k\�Q��',_binary '��\n-�@���G��j97'),(_binary 'q!�\�\�Go�k\�Q��',_binary '�H�2\�M��l��t��\�'),(_binary '�uv\�E$DY�$\�m�4he',_binary '\n��A�N�}��\�\�'),(_binary '�uv\�E$DY�$\�m�4he',_binary 'O}�,j)Nh�jź<�\�'),(_binary '�uv\�E$DY�$\�m�4he',_binary 'l���\�A��C�\�7\�|+'),(_binary '�uv\�E$DY�$\�m�4he',_binary '�\�\�J?J�\Z \�ʨ\�'),(_binary '��\�\�csO��}fkx',_binary 'A(���En�\�w�\�G}'),(_binary '��\�\�csO��}fkx',_binary 'R���F\'CǬ�1ɮ\�@'),(_binary '��\�\�csO��}fkx',_binary '��Y�i\�L\"�Gsك<\�\�'),(_binary '��\�\�csO��}fkx',_binary 'Ĥ\�:NEI��)#\��c�'),(_binary '\�ZR��3Bg�H\nn~h%',_binary '	;w\�&@5�l-��:�('),(_binary '\�ZR��3Bg�H\nn~h%',_binary 'zR\�\�O9�rw�^y5'),(_binary '\�ZR��3Bg�H\nn~h%',_binary '��\�&OВL�N\�9�'),(_binary '\�ZR��3Bg�H\nn~h%',_binary '��9L��AԆ�Zc���'),(_binary '׸d�L��U��k׻\�',_binary '5�Mj²Nx�/y~h�'),(_binary '׸d�L��U��k׻\�',_binary '��uG�Kv� +wTk|'),(_binary '׸d�L��U��k׻\�',_binary '�	\�\�DJ�Ȥ6͓r1'),(_binary '׸d�L��U��k׻\�',_binary '�\�u×9Jh�Q(rS\�');
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
INSERT INTO `product_variant` VALUES (_binary '	;w\�&@5�l-��:�(','WPE143-BLACK-SMALL',100,_binary '�e�v4J/�6�\�Z\�m',_binary '\�ZR��3Bg�H\nn~h%',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '\n��A�N�}��\�\�','ZBE174-BLUE-SMALL',10,_binary '�\�3<\\J��B0<3���',_binary '�uv\�E$DY�$\�m�4he',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '\�fH4�N��W����','LBX195-GRAY-MEDIUM',15,_binary 'G���\�Nī?�\ZL\\�e',_binary 'q!�\�\�Go�k\�Q��',_binary 'B�}�MZ�p�ȺTOU'),(_binary 'q�N�{[Jׁi','LBX195-BLACK-MEDIUM',20,_binary '�e�v4J/�6�\�Z\�m',_binary 'q!�\�\�Go�k\�Q��',_binary 'B�}�MZ�p�ȺTOU'),(_binary 'u�`�aIȬҚ\��\�I','KXG917-WHITE-EXTRA LARGE',20,_binary '5\Z�JBLF�0����7x',_binary 'H��qE��\nm�\�R;',_binary '�\�\�~rE�kp	8\�'),(_binary 'A(���En�\�w�\�G}','JGI879-BLACK-SMALL',20,_binary '�e�v4J/�6�\�Z\�m',_binary '��\�\�csO��}fkx',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '��\n-�@���G��j97','LBX195-GRAY-SMALL',20,_binary 'G���\�Nī?�\ZL\\�e',_binary 'q!�\�\�Go�k\�Q��',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '�\\}��L+���;\�p(','SWF205-BLACK-EXTRA LARGE',20,_binary '�e�v4J/�6�\�Z\�m',_binary ' \�`Bk�09�\�]',_binary '�\�\�~rE�kp	8\�'),(_binary '�\�\�)\�Cۚ\�P\�6o','UFB450-GRAY-LARGE',20,_binary 'G���\�Nī?�\ZL\\�e',_binary '�\�XW3N߷\r7w\�d',_binary '6r=�\�AØ�\r�\�\']'),(_binary '-Ȇ��Hm��r�\Z8u�','SWF205-GRAY-SMALL',20,_binary 'G���\�Nī?�\ZL\\�e',_binary ' \�`Bk�09�\�]',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '5�Mj²Nx�/y~h�','JPF593-YELLOW-SMALL',10,_binary '���JK@\�H�1\�\�\�',_binary '׸d�L��U��k׻\�',_binary ' �t\�\�zM6�Vo%�\�'),(_binary 'M#��N�A��ҞTr�H','UFB450-BLACK-EXTRA LARGE',20,_binary '�e�v4J/�6�\�Z\�m',_binary '�\�XW3N߷\r7w\�d',_binary '�\�\�~rE�kp	8\�'),(_binary 'O}�,j)Nh�jź<�\�','ZBE174-BLUE-MEDIUM',5,_binary '�\�3<\\J��B0<3���',_binary '�uv\�E$DY�$\�m�4he',_binary 'B�}�MZ�p�ȺTOU'),(_binary 'R���F\'CǬ�1ɮ\�@','JGI879-BLACK-MEDIUM',35,_binary '�e�v4J/�6�\�Z\�m',_binary '��\�\�csO��}fkx',_binary 'B�}�MZ�p�ȺTOU'),(_binary '[\�5\�\�#AG�19POk �','UFB450-GRAY-LARGE',20,_binary 'G���\�Nī?�\ZL\\�e',_binary '�\�XW3N߷\r7w\�d',_binary '6r=�\�AØ�\r�\�\']'),(_binary 'hHo�5�M��\��o4��~','SWF205-BLACK-SMALL',10,_binary '�e�v4J/�6�\�Z\�m',_binary ' \�`Bk�09�\�]',_binary ' �t\�\�zM6�Vo%�\�'),(_binary 'l���\�A��C�\�7\�|+','ZBE174-BLACK-SMALL',15,_binary '�e�v4J/�6�\�Z\�m',_binary '�uv\�E$DY�$\�m�4he',_binary ' �t\�\�zM6�Vo%�\�'),(_binary 'zR\�\�O9�rw�^y5','WPE143-GRAY-LARGE',20,_binary 'G���\�Nī?�\ZL\\�e',_binary '\�ZR��3Bg�H\nn~h%',_binary '6r=�\�AØ�\r�\�\']'),(_binary '��\�&OВL�N\�9�','WPE143-BLACK-EXTRA LARGE',50,_binary '�e�v4J/�6�\�Z\�m',_binary '\�ZR��3Bg�H\nn~h%',_binary '�\�\�~rE�kp	8\�'),(_binary '��uG�Kv� +wTk|','JPF593-YELLOW-LARGE',10,_binary '���JK@\�H�1\�\�\�',_binary '׸d�L��U��k׻\�',_binary '6r=�\�AØ�\r�\�\']'),(_binary '�\�\�J?J�\Z \�ʨ\�','ZBE174-BLACK-MEDIUM',5,_binary '�e�v4J/�6�\�Z\�m',_binary '�uv\�E$DY�$\�m�4he',_binary 'B�}�MZ�p�ȺTOU'),(_binary '��Y�i\�L\"�Gsك<\�\�','JGI879-WHITE-SMALL',15,_binary '5\Z�JBLF�0����7x',_binary '��\�\�csO��}fkx',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '���\�alO}�\�f�L','KXG917-WHITE-SMALL',20,_binary '5\Z�JBLF�0����7x',_binary 'H��qE��\nm�\�R;',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '�	\�\�DJ�Ȥ6͓r1','JPF593-RED-LARGE',10,_binary '\�)a�/\�D���F\Z�\�1',_binary '׸d�L��U��k׻\�',_binary '6r=�\�AØ�\r�\�\']'),(_binary '�n7\�7L\�j���>\�\�','UFB450-BLACK-EXTRA LARGE',20,_binary '�e�v4J/�6�\�Z\�m',_binary '�\�XW3N߷\r7w\�d',_binary '�\�\�~rE�kp	8\�'),(_binary '�\�u×9Jh�Q(rS\�','JPF593-RED-SMALL',10,_binary '\�)a�/\�D���F\Z�\�1',_binary '׸d�L��U��k׻\�',_binary ' �t\�\�zM6�Vo%�\�'),(_binary '��9L��AԆ�Zc���','WPE143-GRAY-SMALL',50,_binary 'G���\�Nī?�\ZL\\�e',_binary '\�ZR��3Bg�H\nn~h%',_binary ' �t\�\�zM6�Vo%�\�'),(_binary 'Ĥ\�:NEI��)#\��c�','JGI879-WHITE-MEDIUM',30,_binary '5\Z�JBLF�0����7x',_binary '��\�\�csO��}fkx',_binary 'B�}�MZ�p�ȺTOU'),(_binary 'Ś��V�L\�!\�S\�\�z','SWF205-GRAY-LARGE',15,_binary 'G���\�Nī?�\ZL\\�e',_binary ' \�`Bk�09�\�]',_binary '6r=�\�AØ�\r�\�\']'),(_binary '�H�2\�M��l��t��\�','LBX195-BLACK-SMALL',10,_binary '�e�v4J/�6�\�Z\�m',_binary 'q!�\�\�Go�k\�Q��',_binary ' �t\�\�zM6�Vo%�\�');
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
INSERT INTO `role` VALUES (_binary 'C@*�%@��D\�v���','Role for regular admin with basic permissions','ADMIN'),(_binary '�9;�\��Gꩈ p<p�*','Role for regular users with basic permissions','USER');
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
INSERT INTO `role_permissions` VALUES (_binary 'C@*�%@��D\�v���',_binary '$\�\�\�Ls�\�*K9A\�'),(_binary '�9;�\��Gꩈ p<p�*',_binary '$\�\�\�Ls�\�*K9A\�'),(_binary 'C@*�%@��D\�v���',_binary 'u6�G��\�L\�(@S'),(_binary '�9;�\��Gꩈ p<p�*',_binary 'u6�G��\�L\�(@S'),(_binary 'C@*�%@��D\�v���',_binary '|B���\�Nw�3(\�\�<'),(_binary '�9;�\��Gꩈ p<p�*',_binary '|B���\�Nw�3(\�\�<'),(_binary 'C@*�%@��D\�v���',_binary '}1*�\�\�@��7\�ogx'),(_binary '�9;�\��Gꩈ p<p�*',_binary '}1*�\�\�@��7\�ogx'),(_binary 'C@*�%@��D\�v���',_binary '�\�y̞L���&�f�u'),(_binary '�9;�\��Gꩈ p<p�*',_binary '�\�y̞L���&�f�u');
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
INSERT INTO `size` VALUES (_binary '\0\r\�\�J	�,\�±5\�','Extra Small','XS'),(_binary '�\�;\�N�\�\�V\�','Double Extra Large','XXL'),(_binary ' �t\�\�zM6�Vo%�\�','Small','S'),(_binary '6r=�\�AØ�\r�\�\']','Large','L'),(_binary 'B�}�MZ�p�ȺTOU','Medium','M'),(_binary '�\�\�~rE�kp	8\�','Extra Large','XL'),(_binary '�a\����B����\�\"�','32','32'),(_binary '�\��\�G���|\�\��I','30','30'),(_binary '�\�!\�\�B\�D\�\�!0\�','34','34'),(_binary '�gU�?Lː\�Iu\�\�&','28','28'),(_binary '\�}\�0L\�\r�8S`','36','36');
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

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-01-09 15:23:46
