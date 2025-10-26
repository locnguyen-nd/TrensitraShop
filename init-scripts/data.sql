-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: trendista_database
-- ------------------------------------------------------
-- Server version	8.0.40

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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
  KEY `FKnunur1rxqo9uqop5s56shq6yp` (`role_id`),
  KEY `FKhnndtpgongj5lyfux2tdv8ip` (`user_id`),
  CONSTRAINT `FKhnndtpgongj5lyfux2tdv8ip` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FKnunur1rxqo9uqop5s56shq6yp` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `auth_user_authority`
--

LOCK TABLES `auth_user_authority` WRITE;
/*!40000 ALTER TABLE `auth_user_authority` DISABLE KEYS */;
INSERT INTO `auth_user_authority` VALUES (_binary 'bE\�C�\�\"\�]ddi',_binary 'b@q\�C�\�\"\�]ddi'),(_binary 'bE\�C�\�\"\�]ddi',_binary 'b@}�C�\�\"\�]ddi'),(_binary 'bE\�C�\�\"\�]ddi',_binary 'b@}�C�\�\"\�]ddi');
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  `display_order` int DEFAULT NULL,
  `event` varchar(255) NOT NULL,
  `image_url` varchar(255) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `link_url` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
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
INSERT INTO `cart` VALUES (_binary 'bG\�zC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',0.00,_binary 'bE\�C�\�\"\�]ddi'),(_binary 'bG\�8C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',0.00,_binary 'bE\�C�\�\"\�]ddi');
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
INSERT INTO `category` VALUES (_binary 'bfx\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Khi nhắc đến tủ đồ của các chị em, bạn sẽ hoàn toàn bị thu hút bởi những chiễc áo nữ đang được treo ngay ngắn. Thời trang áo nữ kiểu luôn đa dạng và phát triển không ngừng với đa dạng thiết kế. Vậy những kiểu áo nữ đẹp nào đang được đông đảo phái nữ quan tâm? Hãy cùng TRENDISTA tìm hiểu ngay nhé!',NULL,0,'Áo nữ','ao-nu',_binary 'bc�\�C�\�\"\�]ddi',NULL),(_binary 'bf�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần là một trong những trang phục không thể thiếu của phái đẹp. Quần nữ được chia làm nhiều loại như quần jeans, quần nỉ, quần kaki, quần thể thao... Tùy thuộc vào sở thích hay mục đích của bạn mà bạn có thể lựa chọn chiếc quần phù hợp nhất với mình. Hãy cùng TRENDISTA điểm lại những loại quần nổi bật',NULL,1,'Quần nữ','quan-nu',_binary 'bc�\�C�\�\"\�]ddi',NULL),(_binary 'bf�tC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Phái đẹp luôn mong muốn bản thân được trưng diện và xinh đẹp hơn mỗi ngày không chỉ khi ra ngoài mà ngay cả ở nhà. Nhu cầu được mặc những bộ đồ nữ hợp mốt và thoải mái chính là nhu cầu chính đáng.\n\n\n\nCác chị em khi mặc đồ bộ sẽ hoàn toàn tiết kiệm được tối đa thời gian bởi chúng không cần phải kết',NULL,2,'Đồ bộ nữ','do-bo-nu',_binary 'bc�\�C�\�\"\�]ddi',NULL),(_binary 'bf��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Thời trang đồ mặc trong nữ TRENDISTA với kiểu dáng gợi cảm, chất liệu cao cấp, nhiều ưu đãi hấp dẫn đang chờ bạn khám phá. Mua đồ nội y chưa bao giờ là đễ dàng đến vậy.\n\nCùng điểm danh một số kiểu mẫu đồ mặc trong cho nữ mà bất kể nàng nào cũng cần nhé.\n\n1. Đồ mặc trong cho nữ không thể thiếu - Quần lót',NULL,3,'Đồ mặc trong nữ','do-mac-trong-nu',_binary 'bc�\�C�\�\"\�]ddi',NULL),(_binary 'bf�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,4,'Đồ thể thao nữ','do-the-thao-nu',_binary 'bc�\�C�\�\"\�]ddi',NULL),(_binary 'bf��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,5,'Đầm và chân váy nữ','dam-va-chan-vay-nu',_binary 'bc�\�C�\�\"\�]ddi',NULL),(_binary 'bf��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Tầm quan trọng của phụ kiện đối với thời trang nữ\n\nPhụ kiện thời trang nữ không chỉ là những món đồ đi kèm, dùng thêm để đựng đồ mà đó còn là những món đồ giúp nâng tầm gu ăn mặc của bạn.\n\nMột bộ trang phục trông rất bình thường nhưng nếu bạn biết mix thêm các phụ kiện đi kèm như túi xách, hoa tai',NULL,6,'Phụ kiện nữ','phu-kien-nu',_binary 'bc�\�C�\�\"\�]ddi',NULL),(_binary 'bf�mC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo nam TRENDISTA chính hãng, chất liệu cao cấp, đa dạng mẫu mã, kiểu dáng, chất liệu tốt,... Mua áo nam chính hãng TRENDISTA ngay hôm nay!',NULL,0,'Áo nam','ao-nam',_binary 'bc�=C�\�\"\�]ddi',NULL),(_binary 'bf�7C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Quần nam','quan-nam',_binary 'bc�=C�\�\"\�]ddi',NULL),(_binary 'bf�@C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,2,'Đồ bộ nam','do-bo-nam',_binary 'bc�=C�\�\"\�]ddi',NULL),(_binary 'bf�/C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,3,'Đồ mặc trong nam','do-mac-trong-nam',_binary 'bc�=C�\�\"\�]ddi',NULL),(_binary 'bf�BC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,4,'Đồ thể thao nam','do-the-thao-nam',_binary 'bc�=C�\�\"\�]ddi',NULL),(_binary 'bf�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,5,'Phụ kiện nam','phu-kien-nam',_binary 'bc�=C�\�\"\�]ddi',NULL),(_binary 'bf�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo trẻ em TRENDISTA - Các mẫu áo trẻ em đẹp, cao cấp nhất. Các mẫu áo thun, áo phông cho bé, áo polo trẻ em chất tốt.',NULL,0,'Áo Trẻ em','ao-tre-em',_binary 'bc��C�\�\"\�]ddi',NULL),(_binary 'bf�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Ngày nay sự đa dạng không còn giới hạn trong các sản phẩm thời trang người lớn mà thời trang trẻ em cũng vậy. Quần áo trẻ em đặc biệt quần trẻ em cũng ngày càng được chú trọng trong thiết kế kiểu dáng đa dạng, tinh tế với chất liệu,... cho đến màu sắc cho các bé.',NULL,1,'Quần Trẻ em','quan-tre-em',_binary 'bc��C�\�\"\�]ddi',NULL),(_binary 'bf�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Đồ bộ trẻ em hiện nay rất phong phú, đa dạng, bắt mắt và tạo được sự hào hứng, tò mò cho các bé đang trong độ tuổi phát triển.\n\nHãy cùng TRENDISTA tìm hiểu về bộ đồ trẻ em, các lựa chọn đồ bộ trẻ em cho bé theo từng độ tuổi, lưu ý khi chọn đồ bộ cho trẻ em cũng như điểm qua một vài bộ đồ trẻ em bắt trend',NULL,2,'Đồ bộ Trẻ em','do-bo-tre-em',_binary 'bc��C�\�\"\�]ddi',NULL),(_binary 'bf�4C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,3,'Đồ mặc trong Trẻ em','do-mac-trong-tre-em',_binary 'bc��C�\�\"\�]ddi',NULL),(_binary 'bf�4C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,4,'Đồ thể thao Trẻ em','do-the-thao-tre-em',_binary 'bc��C�\�\"\�]ddi',NULL),(_binary 'bf�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Nói đến thời trang trẻ em không thể không kể đến những mẫu váy trẻ em đẹp. Những chiếc váy đầm trẻ em cao cấp không chỉ được các bé gái yêu thích và lựa chọn khi đi mua sắm cùng bố mẹ. TRENDISTA mách bạn những kiểu váy cơ bản và những tip hướng dẫn mix trang phục để mẹ trở thành chuyên gia',NULL,5,'Đầm và chân váy bé gái','dam-va-chan-vay-be-gai',_binary 'bc��C�\�\"\�]ddi',NULL),(_binary 'bf�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Phụ kiện thời trang trẻ em TRENDISTA như mũ/ nón cho bé, tất, khẩu trang trẻ em, giày dép trẻ em...đa dạng mẫu mã, màu sắc ngộ nghĩnh đáng yêu tạo phong cách mới mẻ cho bé. Mua ngay tại TRENDISTA.VN để nhận hàng ngàn ưu đãi hấp dẫn.',NULL,6,'Phụ kiện Trẻ em','phu-kien-tre-em',_binary 'bc��C�\�\"\�]ddi',NULL),(_binary 'bpX$C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Áo vest nữ','ao-vest-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bp`qC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo khoác nữ là trang phục rất cần thiết trong những ngày Đông lạnh giá. Hãy cùng TRENDISTA khám phá ngay một số mẫu áo khoác nữ , áo gió nữ đẹp đang “được lòng” tất cả các khách hàng nữ nhé!\n\n\n\nÁo khoác nữ form rộng, áo khoác nữ hàn quốc là một trong những item không thể thiếu',NULL,1,'Áo khoác nữ','ao-khoac-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpc�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mùa đông đã đến, và khi nhiệt độ giảm, lựa chọn trang phục của chúng ta trở nên quan trọng để giữ ấm và phong cách. Trong thế giới thời trang đông, Áo Phao Nữ nổi bật như một trang phục đa dạng và quan trọng.',NULL,2,'Áo phao nữ','ao-phao-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpe\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,3,'Áo gió nữ','ao-gio-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bphBC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo chống nắng nữ TRENDISTA đặt chỉ số UPF 50+ ngăn chăn tia UVA và UVB lên tới 98%, chất liệu lại co giãn, mát mẻ khi mặc là lựa chọn hoàn hảo để chị em bảo vệ làn da của mình trong những ngày hè nắng gắt.\n\nTầm quan trọng của áo chống nắng nữ\n\nÁo chống nắng nữ là một trong những vật dụng không thể thiếu.',NULL,4,'Áo chống nắng nữ','ao-chong-nang-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpj�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mua áo sweater nữ form rộng online nhanh chóng và thuận tiện Freeship toàn quốc cùng chính sách đổi trả dễ dàng. Sở hữu ngay áo nỉ nữ cùng nhiều ưu đãi tại đây.\n\n1. Áo sweater nữ local brand\n\nVới độ phổ biến rộng rãi của sweater thì tới thời điểm hiện tại, sweaters local brand đang trở nên thịnh hành',NULL,5,'Áo hoodie - Áo nỉ nữ','ao-hoodie-ao-ni-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpm\'C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mỗi khi đông đến, áo len nữ lại làm khuấy đảo giới mộ điệu với kiểu thiết kế và hoạ tiết đa dạng của mình. Trong phần nội dung dưới đây, thời trang TRENDISTA sẽ bật mí với các bạn những mẫu áo len nữ đẹp và trendy nhất hiện nay.',NULL,6,'Áo len nữ','ao-len-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpoAC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mua áo thun nữ đẹp giá rẻ, áp phông in hình năng động, thiết kế trẻ trung, chất liệu cao cấp co giãn 4 chiều, thoải mái trong từng cử động.',NULL,7,'Áo thun nữ','ao-thun-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpq\ZC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','BST 100 mẫu áo sơ mi nữ đẹp, các kiểu áo sơ mi đẹp, trẻ trung chính hãng TRENDISTA. Mua áo sơ mi nữ cao cấp, giá tốt 2024 tại đây.',NULL,8,'Áo sơ mi nữ','ao-so-mi-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpr\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo polo nữ chính hãng TRENDISTA - BST áo polo nữ cao cấp, trẻ trung, áo thun nữ có cổ đẹp, giá tốt nhất 2024. Ưu đãi lớn, freeship toàn quốc. Mua ngay!',NULL,9,'Áo polo nữ','ao-polo-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bfx\�C�\�\"\�]ddi'),(_binary 'bpt�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mua quần thể thao cho nữ năng động, thời trang chính hãng, mặc là mát, giá  tốt, ship toàn quốc, miễn phí đổi trả 15 ngày đầu.',NULL,0,'Quần dài nữ','quan-dai-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bpv�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Một trong những loài quần được ưa chuộng nhất hiện nay chắc chắc không thể không nhắc đến quần nỉ nữ. Quần nỉ nữ mang dáng thể thao lại khỏe khoắn phù hợp với phong cách street-style. Chiếc quần này có thể mặc trong rất nhiều hoàn cảnh khác nhau như đi chơi, đi học, đi làm ở những nơi không bị gò bó',NULL,1,'Quần nỉ nữ','quan-ni-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bpxrC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mua quần kaki nữ, quần khaki chính hãng giá tốt, đầy đủ kiểu dáng. Mua quần kaki nữ tại TRENDISTA với ưu đãi, giao hàng miễn phí, đổi trả dễ dàng.',NULL,2,'Quần kaki nữ','quan-kaki-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bpzlC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần short nữ là sự lựa chọn đa phong cách, đa cá tính cho mọi cô nàng. Được ưa chuộng trong mùa hè, mùa thu với ưu điểm tạo được sự thoải mái, năng động, và cực thời trang.',NULL,3,'Quần short nữ','quan-short-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bp|/C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,4,'Quần jeans nữ','quan-jeans-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bp}\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần Tây nữ là mẫu quần “huyền thoại”, bạn có thể hô biến từ cô nàng công sở trang nhã hay cá tính dạo phố theo nhiều style khác nhau. Thiết kế hướng đến sự đơn giản, tiện lợi, form chuẩn, phù hợp trong mọi hoàn cảnh.\n\n\n\nQuần Âu nữ là một trong những items không còn xa lạ với các quý cô. Sự thoải má',NULL,5,'Quần âu nữ','quan-au-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bp\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Đồ bộ dài tay nữ','do-bo-dai-tay-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�tC�\�\"\�]ddi'),(_binary 'bp��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Đồ bộ ngắn tay nữ','do-bo-ngan-tay-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�tC�\�\"\�]ddi'),(_binary 'bp�HC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Áo bra nữ','ao-bra-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Quần lót nữ','quan-lot-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mua áo hai dây, áo ba lỗ nữ giá tốt, thiết kế trẻ trung tôn dáng, thu hút mọi ánh nhìn. Mua ngay để nhận ưu đãi giảm 50%',NULL,2,'Áo ba lỗ - Áo hai dây nữ','ao-ba-lo-ao-hai-day-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo giữ nhiệt nữ được xem như là một món đồ “bảo bối” vô cùng cần thiết dành cho các cô nàng trong mùa đông lạnh giá. Không những giúp giữ ấm cơ thể hiệu quả còn rất dễ phối đồ với những trang phục khác nhau mà vẫn đảm bảo được thời trang cũng như cá tính của người mặc.',NULL,3,'Áo giữ nhiệt nữ','ao-giu-nhiet-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Quần thể thao nữ','quan-the-thao-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bp�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Áo polo thể thao nữ','ao-polo-the-thao-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bp�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,2,'Áo thun thể thao nữ','ao-thun-the-thao-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bp��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,3,'Bộ thể thao nữ','bo-the-thao-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bp�XC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Đầm nữ - váy liền thân là item thời trang đem lại nét dịu dàng, nữ tính cho phái đẹp. Trang phục này thường được thiết kế với chất vải mỏng, mát giúp chị em thoải mái vận động. Vậy, bạn đã biết mẫu váy nữ, đầm body nữ nào đang thịnh hành hiện nay chưa? Nếu chưa thì đừng bỏ qua phần gợi ý dưới đây nha',NULL,0,'Đầm nữ','dam-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�AC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Chân váy nữ, chân váy chữ A, chân váy công sử với nhiều thiết kế tinh tế đảm bảo sẽ làm bạn hài lòng. Xem ngay để mua Chân váy TRENDISTA chính hãng',NULL,1,'Chân váy nữ','chan-vay-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�OC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Tất nữ','tat-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Túi xách nữ','tui-xach-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Bộ sưu tập giày của các cô gái có gu chắc chắn sẽ không thể thiếu những đôi giày cao gót. Chị em thanh lịch với một outfit hoàn hảo bước đi tự tin trên một đôi giày cao gót sẽ thu hút được ánh nhìn của rất nhiều người. Vậy, những đôi giày cao gót nào đang được ưa chuộng nhất hiện nay? Cùng TRNEDISTA khám phá',NULL,2,'Giày nữ','giay-nu',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp�YC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Thiên đường phụ kiện thời trang nữ cao cấp, thiết kế đẹp, không thể thiếu của cô nàng hiện đại – phụ kiện thời trang nữ đơn giản mà không kém phần tinh tế.',NULL,3,'Phụ kiện nữ khác','phu-kien-nu-khac',_binary 'bc�\�C�\�\"\�]ddi',_binary 'bf��C�\�\"\�]ddi'),(_binary 'bp\�gC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo vest nam là một sản phẩm thời trang tạo được rất nhiều sự sang trọng, khác biệt so với nhiều món đồ thời trang khác.\n\n\n\nHãy cùng TRENDISTA tìm hiểu áo vest nam là gì, cách để phân biệt áo vest nam cũng như tham khảo được một vài mẫu áo vest nam phù hợp nhất cho bản thân qua bài viết dưới đây.',NULL,0,'Áo vest nam','ao-vest-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq\'C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo khoác nam TRENDISTA chính hãng giá rẻ, chất lượng cao, chống nước cản gió, mua hàng chính hãng tại website, giao hàng nhanh chóng.',NULL,1,'Áo khoác nam','ao-khoac-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq*�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,2,'Áo phao nam','ao-phao-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq,\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,3,'Áo gió nam','ao-gio-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq/2C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Những ngày thu đông gần tới, để khỏi nhàm chán với các kiểu áo như áo len, áo giữ nhiệt không có chút phá cách nào thì áo hoodie nam là lựa chọn sáng suốt. Vậy áo hoodie nam được mặc như thế nào và mua áo hoodie nam ở đâu chất lượng nhất?',NULL,4,'Áo hoodie - Áo nỉ nam','ao-hoodie-ao-ni-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq1ZC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo len nam có vô cùng những kiểu cách khác nhau từ: áo len cổ cao nam, áo len nam cổ lọ, áo len nam… Những kiểu áo này thường mang form rộng, chất mềm mịn và bắt kịp xu hướng thời trang nam. Phong cách TRENDISTA giúp chàng tút tát lại vẻ đẹp trai đón mùa đông ấm áp.',NULL,5,'Áo len nam','ao-len-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq3�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','BST mẫu áo thun nam đẹp, áo phông nam hàng hiệu cao cấp YODY mới nhất 2024. Mua áo thun nam TRENDISTA với ưu đãi hấp dẫn, freeship toàn quốc.',NULL,6,'Áo thun nam','ao-thun-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq5\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','BST áo sơ mi nam đẹp, cao cấp, chính hãng TRENDISTA, áo sơ mi công sở nam hiện đại, trẻ trung, đa dạng mẫu mã. Mua áo sơ mi tại TRENDISTA ngay!',NULL,7,'Áo sơ mi nam','ao-so-mi-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq7�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','BST áo polo nam chính hãng, đa dạng kiểu dáng tại TRENDISTA. Chất liệu cao cấp, thoáng mát, giá tốt nhất. Mua online, giao hàng nhanh toàn quốc!',NULL,8,'Áo polo nam','ao-polo-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�mC�\�\"\�]ddi'),(_binary 'bq9\'C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Quần dài nam','quan-dai-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�7C�\�\"\�]ddi'),(_binary 'bq:kC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Vẻ thanh lịch, trẻ trung và nam tính là tất cả những gì bạn nhận được khi diện quần kaki nam. Dưới đây, thời trang TRENDISTA sẽ bật mí với mọi người những mẫu quần thịnh hành nhất và cách phối đồ với quần kaki nam chuẩn fashionista.',NULL,1,'Quần kaki nam','quan-kaki-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�7C�\�\"\�]ddi'),(_binary 'bq;�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Hàng ngàn quần short nam trẻ trung, quần sooc nam thể thao giá rẻ. Mẫu mã đa dạng, kiểu dáng năng động, mua quần đùi nam với ưu đãi khủng lên đến 50%!',NULL,2,'Quần short nam','quan-short-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�7C�\�\"\�]ddi'),(_binary 'bq<\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','BST quần jeans nam đẹp, quần bò nam chính hãng TRENDISTA, mẫu mới nhất 2024. Mua online ngay để nhận ưu đãi giảm giá lên tới 50%',NULL,3,'Quần jeans nam','quan-jeans-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�7C�\�\"\�]ddi'),(_binary 'bq>C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần âu nam công sở đẹp, quần tây nam cao cấp chính hãng TRENDISTA đa dạng mẫu mã, kiểu dáng. Mua ngay quần âu TRENDISTA với ưu đãi cực sốc!',NULL,4,'Quần âu nam','quan-au-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�7C�\�\"\�]ddi'),(_binary 'bq?UC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Đồ bộ dài tay nam','do-bo-dai-tay-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�@C�\�\"\�]ddi'),(_binary 'bq@yC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Đồ bộ ngắn tay nam','do-bo-ngan-tay-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�@C�\�\"\�]ddi'),(_binary 'bqA�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần lót nam là món đồ được mặc bên trong nhằm bảo vệ “cậu nhỏ”. Đây chính là lý do cơ bản nhất khi mặc đồ lót khiến phái mạnh luôn tự tin và thoải mái mỗi khi sử dụng.\n\nCác loại quần lót nam được sản xuất từ nhiều chất liệu vải khác nhau, chủ yếu là những chất liệu tự nhiên, không gây kích ứng da',NULL,0,'Quần lót nam','quan-lot-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�/C�\�\"\�]ddi'),(_binary 'bqB\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mua áo ba lỗ nam chính hãng, áo thun 3 lỗ chất liệu cao cấp, co giãn 4 chiều, tự tin trong các hoạt động thể thao như gym, bóng rổ, bóng đá, chạy bộ,..',NULL,1,'Áo ba lỗ nam','ao-ba-lo-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�/C�\�\"\�]ddi'),(_binary 'bqDC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo giữ nhiệt nam là trang phục sở hữu nhiều ưu điểm vượt trội được thiết kế mỏng, nhẹ giúp giữ ấm được sản xuất với cấu tạo đặc biệt. Vậy áo giữ nhiệt nam có thiết kế như thế nào? Mua áo giữ nhiệt nam ở đâu? Công dụng của áo giữ nhiệt nam là gì? Cùng tìm hiểu ngay với TRENDISTA nhé!',NULL,2,'Áo giữ nhiệt nam','ao-giu-nhiet-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�/C�\�\"\�]ddi'),(_binary 'bqE^C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Một trong những loại quần không thể thiếu dành cho phái mạnh là quần thể thao nam. Quần thể thao nam không chỉ đáp ứng phong cách mà còn giúp bạn cảm thấy thoải mái khi tham gia các hoạt động. Ngày nay có nhiều loại quần thể thao được phát triển cho bạn có thêm nhiều sự lựa chọn như: quần dài, quần',NULL,0,'Quần thể thao nam','quan-the-thao-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�BC�\�\"\�]ddi'),(_binary 'bqF�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo polo thể thao nam là một trong những mẫu áo được sử dụng phổ biến, rộng rãi và đạt được cánh mày râu cực kỳ yêu thích.\n\nHãy cùng TRENDISTA tìm hiểu áo polo thể thao cho nam là áo gì, các mẫu áo polo thể thao nam hot nhất năm 2024 cùng các chất liệu làm nên áo polo thể thao qua bài viết dưới đây.',NULL,1,'Áo polo thể thao nam','ao-polo-the-thao-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�BC�\�\"\�]ddi'),(_binary 'bqG\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo thun thể thao, áo phông thể thao nam thường được cánh mày râu sử dụng trong những lúc tập luyện thể dục thể thao hay các hoạt động ngoài trời cần vận động mạnh. Loại áo này có cấu tạo và chất liệu đặc biệt giúp cho việc hoạt động của những người sử dụng sản phẩm này cảm thấy thoải mái, mát lạnh',NULL,2,'Áo thun thể thao nam','ao-thun-the-thao-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�BC�\�\"\�]ddi'),(_binary 'bqH�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Bộ đồ thể thao cho nam là một trong những sản phẩm thời trang phục vụ hiệu quả cho đời sống thường ngày, thể dục thể thao mỗi ngày mà vẫn rất thời trang, phong cách.\n\n\n\nHãy cùng TRENDISTA tìm hiểu bộ đồ thể thao cho nam là gì, các bộ đồ thể thao cho nam đang hot nhất hiện nay cùng một vài lưu ý khi mua bộ thể thao nam',NULL,3,'Bộ thể thao nam','bo-the-thao-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�BC�\�\"\�]ddi'),(_binary 'bqK[C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Tất nam','tat-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bqL\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Túi xách nam','tui-xach-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bqNOC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Mua mũ lưỡi trai nam, nón nam chính hãng giá tốt. Mua mũ lưỡi trai nam tại TRENDISTA với ưu đãi, giao hàng miễn phí, đổi trả dễ dàng.',NULL,2,'Mũ nam','mu-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bq=C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Thắt lưng nam là món phụ kiện thời trang không thể thiếu cho phái mạnh ngày nay. Đây là món phụ kiện dùng để kết hợp với quần âu, quần jeans, bộ vest lịch lãm,... để tăng sự thanh lịch và sang trọng của người mặc. \n\nNhưng thắt lưng nam có nhiều kiểu dáng và chất liệu, mỗi kiểu dáng, chất liệu lại có',NULL,3,'Thắt lưng nam','that-lung-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Giày nam là món đồ thời trang không lạ lẫm gì với phái mạnh nữa, nhất là vào năm 2024 khi mà dép có xu hướng đi xuống. Mỗi kiểu giày nam lại có mục đích sử dụng khác nhau nên người đi sẽ phải đưa ra quyết định hợp lí để có bộ đồ đẹp nhất.\n\nCó nhiều kiểu giày nam như giày tây, giày da, giày lười,...',NULL,4,'Giày nam','giay-nam',_binary 'bc�=C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo khoác trẻ em thương hiệu TRENDISTA luôn đáp ứng được chất lượng cũng như đa dạng về chủng loại: Áo phao trẻ em, áo gió trẻ em, áo ấm,... giúp các bé giữ ấm hiệu quả trong thời tiết mùa đông khắc nghiệt.',NULL,0,'Áo khoác trẻ em','ao-khoac-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Áo phao trẻ em','ao-phao-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,2,'Áo gió trẻ em','ao-gio-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�RC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo nỉ trẻ em là một món đồ thời trang thiết yếu giúp bé yêu giữ ấm trong những ngày thời tiết chuyển lạnh. Hãy cùng TRENDISTA tìm hiểu về một vài mẫu áo nỉ, áo hoodie trẻ em hot trend nhất hiện nay và tham khảo thêm một vài cách phối đồ với áo nỉ trẻ em trong nội dung dưới đây nhé!',NULL,3,'Áo hoodie - Áo nỉ trẻ em','ao-hoodie-ao-ni-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Áo len trẻ em luôn là một món đồ thời trang thiết yếu giúp bé yêu giữ ấm trong những ngày thời tiết chuyển lạnh.  Hãy cùng TRENDISTA tìm hiểu về một vài mẫu áo len trẻ em hot trend nhất hiện nay, cũng như các lưu ý trong khâu lựa chọn áo len cho bé và tham khảo thêm một vài cách phối đồ với áo len trẻ em',NULL,4,'Áo len trẻ em','ao-len-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','BST áo thun trẻ em, áo phông bé trai, bé gái cao cấp, chất liệu thoáng mát. Mua áo phông trẻ em cao cấp TRENDISTA để nhận ưu đãi hấp dẫn!',NULL,5,'Áo thun trẻ em','ao-thun-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Cập nhật BST áo sơ mi trẻ em đẹp, cao cấp, áo sơ mi cho bé chính hãng TRENDISTA chất liệu vượt trội. Mua áo so mi trẻ em TRENDISTA giá ưu đãi ngay!',NULL,6,'Áo sơ mi trẻ em','ao-so-mi-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','BST áo polo trẻ em TRENDISTA đa dạng mẫu mã: áo polo bé trai, áo polo bé gái cao cấp, thoáng mát. Mua áo polo trẻ em chính hãng TRENDISTA ngay!',NULL,7,'Áo polo trẻ em','ao-polo-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần legging trẻ em là một trong những item không thể thiếu của các bé. Quần legging vừa hợp thời trang lại vừa thoải mái cho các bé cả ngày hoạt động. Vậy bạn đã biết có những lạoi legging nào phù hợp với các bé chưa? Hãy cùng TRENDISTA tìm hiểu ngay để khám phá.',NULL,0,'Quần dài trẻ em','quan-dai-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�MC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần kaki trẻ em được xem là món đồ không thể thiếu trong tủ đồ của các bé bởi ưu điểm khi mặc chính là sự thoải mái, dễ chịu hơn nên các mẹ lựa chọn mua nhiều. Chưa dừng lại ở đó chiếc quần kaki cho bé trai này còn dễ dàng kết hợp được với nhiều loại áo giúp nâng tầm diện mạo cho các bé yêu.',NULL,1,'Quần kaki trẻ em','quan-kaki-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�AC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần nỉ trẻ em là chiếc quần rất hoàn hảo và không thể thiếu trong tủ đồ dành cho các bé. Hãy cùng TRENDISTA tìm hiểu quần nỉ em là gì, điểm qua những mẫu quần nỉ trẻ em hot nhất hiện nay cũng như gợi ý phối đồ với quần nỉ trẻ em qua bài viết dưới đây.',NULL,2,'Quần nỉ trẻ em','quan-ni-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Quần short trẻ em không chỉ mang tác dụng phối đồ làm đẹp mà còn là một món đồ thời trang rất cần thiết đối với mọi độ tuổi của bé.',NULL,3,'Quần short trẻ em','quan-short-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','TRENDISTA mang đến thế giới thời trang năng động và phong cách cho các bé yêu với bộ sưu tập quần jean trẻ em, quần bò trẻ em đa dạng và chất lượng. TRENDISTA hiểu rằng các bé cần sự thoải mái và tự do vận động, đồng thời vẫn thể hiện cá tính riêng.',NULL,4,'Quần jeans trẻ em','quan-jeans-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�KC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Đồ bộ dài tay trẻ em','do-bo-dai-tay-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq�RC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Đồ bộ ngắn tay trẻ em','do-bo-ngan-tay-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�\�C�\�\"\�]ddi'),(_binary 'bq��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Áo giữ nhiệt trẻ em','ao-giu-nhiet-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�4C�\�\"\�]ddi'),(_binary 'bq�EC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Nói đến thời trang trẻ em không thể không kể đến những mẫu váy trẻ em đẹp. Những chiếc váy đầm trẻ em cao cấp không chỉ được các bé gái yêu thích và lựa chọn khi đi mua sắm cùng bố mẹ. TRENDISTA mách bạn những kiểu váy cơ bản và những tip hướng dẫn mix trang phục để mẹ trở thành chuyên gia và biến bé trở',NULL,0,'Đầm bé gái','dam-be-gai',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bq�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Diện các mẫu chân váy bé gái đẹp luôn là niềm yêu thích của các cô bé yêu thích sự dễ thương, điệu đà. Đây cũng là item này mà phụ huynh hiện nay lựa chọn cho các cô công chúa nhỏ của mình. Với bài viết dưới đây, TRENDISTA mong rằng bạn sẽ tìm được cho bé yêu nhà mình những mẫu chân váy phù hợp nhất!',NULL,1,'Chân váy bé gái','chan-vay-be-gai',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bq�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,0,'Tất trẻ em','tat-tre-em',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi'),(_binary 'bq�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,1,'Phụ kiện trẻ em khác','phu-kien-tre-em-khac',_binary 'bc��C�\�\"\�]ddi',_binary 'bf�C�\�\"\�]ddi');
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
INSERT INTO `color` VALUES (_binary 'bH�JC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','sky-blue','xanh lơ','#8ABCC0'),(_binary 'bH�\ZC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','teal','xanh cổ vịt','#244368'),(_binary 'bH�wC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','dark-blue','xanh đậm','#235387'),(_binary 'bH\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','mint-green','xanh mint','#CDDCDB'),(_binary 'bH¯C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','light-blue','xanh nhạt','#A8D3D7'),(_binary 'bH\�@C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','black-blue','xanh đen','#13293D'),(_binary 'bH\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','cobalt-blue','xanh coban','#0E405F'),(_binary 'bH\�LC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','green','xanh lá','#008000'),(_binary 'bH\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','moss-green','rêu','#45503B'),(_binary 'bH\�^C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','olive-green','xanh rêu','#747553'),(_binary 'bH\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','ash','ghi','#AE9B91'),(_binary 'bH\�pC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','gray-ash','ghi xám','#C5C9CA'),(_binary 'bH\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','brown','nâu','#C09A7E'),(_binary 'bH\�}C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','dark-brown','nâu đậm','#3A2923'),(_binary 'bH\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','orange','cam','#FE523D'),(_binary 'bH\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','earth-orange','cam đất','#E35125'),(_binary 'bHɿC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','beige','be','#F5F5DC'),(_binary 'bH\�|C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','pink','hồng','#FFC0CB'),(_binary 'bH\�\rC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','purple','tím','#DEBCC8'),(_binary 'bHˤC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','light-purple','tím nhạt','#C3BCE8'),(_binary 'bH\�.C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','indigo','tím than','#204A80'),(_binary 'bH̹C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','black','đen','#000000'),(_binary 'bH\�DC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','navy','navy','#27285C'),(_binary 'bH\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','yellow','vàng','#FFFF00'),(_binary 'bH\�LC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','red','đỏ','#FF0000'),(_binary 'bH\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','gray','xám','#DDDDDD'),(_binary 'bH\�YC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','dark-gray','xám đậm','#9B8F91'),(_binary 'bH\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','white','trắng','#FFFFFF');
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
INSERT INTO `gender` VALUES (_binary 'bc�=C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,'Nam','nam'),(_binary 'bc�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,'Nữ','nu'),(_binary 'bc��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,'Trẻ em','tre-em'),(_binary 'bc�oC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,'Unisex','unisex');
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
INSERT INTO `permission` VALUES (_binary 'bB�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','/address/**','POST','USER CREATE ADDRESS'),(_binary 'bBàC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','/cart-order/create','POST','USER CREATE ORDER'),(_binary 'bB\�]C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','/cart-order/user','GET','USER GET ORDER'),(_binary 'bB\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','/cart-order/cancel/**','PUT','USER CANCEL ORDER'),(_binary 'bB\�nC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','/user/**','GET','USER READ ACCOUNT');
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
INSERT INTO `product` VALUES (_binary 'bwC?C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','VEM7003','Chất liệu Nano waffle cao cấp tạo nên sự khác biệt: mềm mại, thoáng mát, chống nhăn và chống UV hiệu quả. Công nghệ sợi nano siêu mảnh mang đến trải nghiệm mặc vô cùng thoải mái và sang trọng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Vest Nam Một Lớp',1499000.00,1499000.00,0,0,'vest-nam-mot-lop',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bp\�gC�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','AKM7007','Áo khoác nam đa năng mũ liền tiện lợi, lót lông ấm áp. Công nghệ màng PU siêu mỏng nhẹ, chống thấm nước hiệu quả. Thiết kế hiện đại với phối khoá trẻ trung, dây rút chắc chắn, khoá túi trong an toàn để đựng đồ, cúc bấm cổ tay tránh gió lùa. Vải polyester cao cấp, bền bỉ, trượt nước 3k phù hợp mọi thời tiết.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Áo Khoác Nam Mũ Liền Lót Lông',849000.00,849000.00,0,0,'ao-khoac-nam-mu-lien-lot-long',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bq,\�C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','PHM7009','Trang bị cho mùa đông lạnh giá một chiếc áo phao ưu việt: Áo Phao Nam Trần Trám Nẹp Giấu Khoá. Chiếc áo có trọng lượng nhẹ, khả năng giữ ấm tốt cùng thiết kế hiện đại. Thiết kế trần trám bền đẹp theo thời gian, không sợ lỗi mốt. Khoá kéo YKK - loại khoá hàng đầu thế giới, bền chắc','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Áo Phao Nam Trần Trám Nẹp Giấu Khoá',699000.00,699000.00,0,0,'ao-phao-nam-tran-tram-nep-giau-khoa',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bq*�C�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','STM7055','Công nghệ dệt Jacquard hiện đại, tạo họa tiết lỗ độc đáo. Thấm hút tốt, khô nhanh, thông thoáng nhờ kiểu dệt - tạo cảm thoải mái cho làn da. Kiểu dáng áo thun thể thao basic, trẻ trung, năng động. Cổ tròn, ôm sát cổ, tạo cảm giác thoải mái khi vận động.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Áo Thun Thể Thao Nam Siêu Nhẹ',279000.00,279000.00,0,0,'ao-thun-the-thao-nam-sieu-nhe',NULL,NULL,'NEW_ARRIVALS',0,0,_binary 'bq3�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','QKM7005','Quần Kaki Nam Cạp Di Động Kiểu dáng cạp di động thời trang, form chuẩn đẹp. Chất liệu Khaki cotton cao cấp, thành phần cotton cao giúp quần thông thoáng, dễ chịu khi mặc. Vải đanh chắc giữ phom lâu dài nhưng vẫn mềm mại, không thô cứng, không bị nhăn nhúm hay bai dão','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Quần Kaki Nam Cạp Di Động',499000.00,499000.00,0,0,'quan-kaki-nam-cap-di-dong',NULL,NULL,'NEW_ARRIVALS',0,0,_binary 'bq:kC�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','QKM6017','Kiểu dáng Slim fit ôm sát tôn lên đường nét cơ thể, giúp bạn tự tin và khoẻ khoắn trong mọi hoạt động từ đi làm, đi chơi đến dự tiệc. Sản phẩm hoàn hảo cho phái mạnh hiện đại - tự tin khẳng định phong cách.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Quần Kaki Nam Slimfit',499000.00,499000.00,0,0,'quan-kaki-nam-slimfit',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bq:kC�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','QJM6041','Quần co giãn nhẹ giúp người mặc thoải mái khi vận động, chất liệu denim like được làm từ vải mộc màu trắng sau đó được nhuộm màu nên màu sắc phong phú đa dạng, tươi sáng.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Quần Jeans Nam Slim Denim Like Cơ Bản',599000.00,599000.00,0,0,'quan-jeans-nam-slim-denim-like-co-ban',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bq<\�C�\�\"\�]ddi'),(_binary 'bw_\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','AKN7001','Sản phẩm được chờ đón mỗi mùa thu đông tại TRENDISTA: áo măng tô dáng dài dành cho phái đẹp. Sử dụng sợi tencel có nguồn gốc tự nhiên mang đến độ mượt bóng nhẹ cho áo. Thiết kế cổ điển được chú trọng tỉ mỉ trong từng chi tiết đem lại độ hoàn thiện cao.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Áo Măng Tô Nữ Đai Eo Casual',1399000.00,1399000.00,0,0,'ao-mang-to-nu-dai-eo-casual',NULL,NULL,'NEW_ARRIVALS',0,0,_binary 'bp`qC�\�\"\�]ddi'),(_binary 'bwa\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','KNN7004','Chất liệu PU siêu mềm, giống da thật nhưng không gây hại cho động vật. Lớp lót mềm mại, giữ ấm tuyệt vời, bo gấu co giãn thoải mái. Thiết kế trẻ trung, năng động. Thời trang, bền đẹp, thân thiện với môi trường.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Khoác Ngắn Nữ Bomber Da Phôi Bo Chun',990000.00,990000.00,0,0,'khoac-ngan-nu-bomber-da-phoi-bo-chun',NULL,NULL,'NEW_ARRIVALS',0,0,_binary 'bp`qC�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','QSN7033','Quần short nữ dáng ngắn thiết kế trẻ trung. Chất liệu dạ cao cấp, bền màu, không nhăn. Thành phần spandex giúp quần co giãn thoải mái. Đai kim loại tạo điểm nhấn thời trang.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Quần Short Nữ Dạ Đai Kim loại',399000.00,399000.00,0,0,'quan-sooc-nu-da-dai-kim-loai',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bpzlC�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','QJN7067','Quần jeans nữ thiết kế ống loe cá tính, gấu nhăn độc đáo tạo phong cách thời thượng. Chất liệu denim cotton mềm mại, thông thoáng, có độ bền cao. Quần co giãn nhẹ do chứa thành phần spandex giúp người mặc thoải mái khi vận động. Hạn chế khô cứng sau giặt.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Quần Jean Nữ Ống Loe Gấu Nhăn',549000.00,549000.00,0,0,'quan-jean-nu-ong-loe-gau-nhan',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bp|/C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','QJN6052','Một thiết kế quần jean nữ ống suông trẻ trung tôn dáng, chất vải thoáng mát, mềm, độ bền cao. Sở hữu ngay.','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1734617699/PRODUCTS/Qu%E1%BA%A7n_Gi%C3%B3_D%C3%A0i_Yoguu_Ph%E1%BB%91i_Vi%E1%BB%81n_Ph%E1%BA%A3n_Quang_Black_.webp',NULL,'Quần Jeans Nữ Dáng Suông Cotton Trơn',549000.00,164700.00,0,0,'quan-jeans-nu-dang-suong-cotton-tron',NULL,NULL,'BEST_SELLERS',0,0,_binary 'bp|/C�\�\"\�]ddi');
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
INSERT INTO `product_image` VALUES (_binary 'b�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952046/ao-vest-nam-VEM7003-DEN_ndii5v.jpg',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952035/ao-vest-nam-VEM7003-DEN-1_arvuth.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�PC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952038/ao-vest-nam-VEM7003-DEN-2_uuxai7.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�5C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952036/ao-vest-nam-VEM7003-DEN-3_xhioc9.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952036/ao-vest-nam-VEM7003-DEN-4_mgqsew.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952036/ao-vest-nam-VEM7003-DEN-5_p8citu.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�WC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952037/ao-vest-nam-VEM7003-DEN-6_eohpkx.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952041/ao-vest-nam-VEM7003-DEN-7_fjel6t.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952064/ao-vest-nam-VEM7003-NAU_bwpwcr.jpg',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952054/ao-vest-nam-VEM7003-NAU-1_iqf3yn.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952055/ao-vest-nam-VEM7003-NAU-2_veptzv.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�#.C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952057/ao-vest-nam-VEM7003-NAU-3_k72fdj.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�\'C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952058/ao-vest-nam-VEM7003-NAU-4_hkilbl.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�)nC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952059/ao-vest-nam-VEM7003-NAU-5_bqb5pk.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�+KC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952062/ao-vest-nam-VEM7003-NAU-6_egzpgf.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�,\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952063/ao-vest-nam-VEM7003-NAU-7_aed7da.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�.�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952053/ao-vest-nam-VEM7003-GHI_jm2iwg.jpg',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�2fC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952043/ao-vest-nam-VEM7003-GHI-1_az7hdc.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�4�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952044/ao-vest-nam-VEM7003-GHI-2_xsiope.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�6�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952045/ao-vest-nam-VEM7003-GHI-3_or9cuj.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�9KC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952046/ao-vest-nam-VEM7003-GHI-4_l1dn13.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�;\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952048/ao-vest-nam-VEM7003-GHI-5_kqbcjr.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�>C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952050/ao-vest-nam-VEM7003-GHI-6_r4yiy1.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�?\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736952051/ao-vest-nam-VEM7003-GHI-7_wg8nsh.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi'),(_binary 'b�BAC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-1_dlw5o5.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�E\nC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-2_flmfac.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�F\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945685/ao-khoac-nam-AKM7007-DEN-3_lwyulf.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�H�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-4_qbnzzw.webp_flmfac.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�J�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-5_q8real.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�L{C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945684/ao-khoac-nam-AKM7007-DEN-6_xv8rl5.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�NYC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945685/ao-khoac-nam-AKM7007-DEN-7_rgwihy.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�PC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945686/ao-khoac-nam-AKM7007-GHI-1_iax9ht.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�U�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945686/ao-khoac-nam-AKM7007-GHI-2_ocqcrd.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�X-C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945686/ao-khoac-nam-AKM7007-GHI-3_geiggd.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�Y\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945689/ao-khoac-nam-AKM7007-GHI-4_wpdtqy.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�[mC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945689/ao-khoac-nam-AKM7007-GHI-5_sbq7x2.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�]GC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945689/ao-khoac-nam-AKM7007-GHI-6_af2m2m.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�_�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736945690/ao-khoac-nam-AKM7007-GHI-7_strbfp.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi'),(_binary 'b�a�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953566/ao-phao-nam-PHM7009-XDE-1_v4ifxk.webp',_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�d\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953567/ao-phao-nam-PHM7009-XDE-2_clxzqi.webp',_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�gC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953568/ao-phao-nam-PHM7009-XDE-3_zw822y.webp',_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�iC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953570/ao-phao-nam-PHM7009-XDE-4_efoohd.webp',_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�k�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953570/ao-phao-nam-PHM7009-XDE-5_bz8uc7.webp',_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�m�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953572/ao-phao-nam-PHM7009-XDE-6_hfy2ne.webp',_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�o�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953573/ao-phao-nam-PHM7009-XDE-7_bwnsrm.webp',_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�q�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953560/ao-phao-nam-PHM7009-REU-1_xja9nz.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�sbC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953560/ao-phao-nam-PHM7009-REU-2_lpxfeu.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�u_C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953561/ao-phao-nam-PHM7009-REU-3_bbau3g.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�wZC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953560/ao-phao-nam-PHM7009-REU-4_a8qvou.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�z\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953561/ao-phao-nam-PHM7009-REU-5_t7zpuw.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�|\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953561/ao-phao-nam-PHM7009-REU-6_wg7wfj.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b�zC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953564/ao-phao-nam-PHM7009-REU-7_hvrgjl.jpg',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953773/ao-thun-STM7055-XAM-1_yfid3s.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953775/ao-thun-STM7055-XAM-2_p4fwel.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953776/ao-thun-STM7055-XAM-3_fc4ffb.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��CC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953777/ao-thun-STM7055-XAM-4_xhumck.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953779/ao-thun-STM7055-XAM-5_jgigdx.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��%C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953781/ao-thun-STM7055-XAM-6_wodhrg.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953766/ao-thun-STM7055-DEN-1_zcdq3l.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953767/ao-thun-STM7055-DEN-2_ighc7z.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953768/ao-thun-STM7055-DEN-3_lyrrsa.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��%C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953769/ao-thun-STM7055-DEN-4_nsxqkx.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��UC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953770/ao-thun-STM7055-DEN-5_r3g3ny.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��|C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736953772/ao-thun-STM7055-DEN-6_gswzbd.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi'),(_binary 'b��\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954062/quan-kaki-nam-QKM7005-GHI-1_gba4eg.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954065/quan-kaki-nam-QKM7005-GHI-2_hrrfej.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��CC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954066/quan-kaki-nam-QKM7005-GHI-3_cbxu42.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��)C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954069/quan-kaki-nam-QKM7005-GHI-4_vdvyjx.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954070/quan-kaki-nam-QKM7005-GHI-5_ewyryb.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��ZC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954071/quan-kaki-nam-QKM7005-GHI-6_ldvjdp.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��9C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954073/quan-kaki-nam-QKM7005-GHI-7_mxzkfe.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954052/quan-kaki-nam-QKM7005-DEN-1_lxxkwx.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954053/quan-kaki-nam-QKM7005-DEN-2_yq9032.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954055/quan-kaki-nam-QKM7005-DEN-3_f63waj.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954056/quan-kaki-nam-QKM7005-DEN-4_o6lapx.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��~C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954058/quan-kaki-nam-QKM7005-DEN-5_xdezr0.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��mC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954059/quan-kaki-nam-QKM7005-DEN-6_ajzt5l.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��aC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954061/quan-kaki-nam-QKM7005-DEN-7_wvtqaz.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi'),(_binary 'b��-C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954286/quan-kaki-nam-QKM6017-XAM-1_ejvx0p.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�\�?C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954288/quan-kaki-nam-QKM6017-XAM-2_dwxyts.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954290/quan-kaki-nam-QKM6017-XAM-3_utabal.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954292/quan-kaki-nam-QKM6017-XAM-4_zfl8oj.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�\�*C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954293/quan-kaki-nam-QKM6017-XAM-5_ybq2i0.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�n\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954294/quan-kaki-nam-QKM6017-XAM-6_b5sx9n.webp',_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�r�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954277/quan-kaki-nam-QKM6017-DEN-1_zypmwm.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�v*C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954278/quan-kaki-nam-QKM6017-DEN-2_ydhnk1.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�x�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954280/quan-kaki-nam-QKM6017-DEN-3_ddprtv.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�z\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954282/quan-kaki-nam-QKM6017-DEN-4_v9gn5i.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b�}IC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954283/quan-kaki-nam-QKM6017-DEN-5_iepckl.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b��/C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954285/quan-kaki-nam-QKM6017-DEN-6_osciga.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi'),(_binary 'b��YC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954548/quan-jeans-QJM6041-DEN-1_mf6kmw.webp',_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b��\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954548/quan-jeans-QJM6041-DEN-2_pal07y.webp',_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b��\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954549/quan-jeans-QJM6041-DEN-3_ayrp5w.webp',_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954550/quan-jeans-QJM6041-DEN-4_kydjsb.webp',_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b��[C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954552/quan-jeans-QJM6041-DEN-5_bejzj5.webp',_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954553/quan-jeans-QJM6041-XAD-1_arvwgl.jpg',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b��\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954554/quan-jeans-QJM6041-XAD-2_kvlajl.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954555/quan-jeans-QJM6041-XAD-3_hdx3dc.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b��bC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954557/quan-jeans-QJM6041-XAD-4_zneppa.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954558/quan-jeans-QJM6041-XAD-5_szancm.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954672/ao-khoac-nu-AKN7001-BEE-1_ekkouu.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi'),(_binary 'b��MC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954673/ao-khoac-nu-AKN7001-BEE-2_fuuldn.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi'),(_binary 'b��\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954674/ao-khoac-nu-AKN7001-BEE-3_v5vell.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954676/ao-khoac-nu-AKN7001-BEE-4_jxgphy.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi'),(_binary 'b��FC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954677/ao-khoac-nu-AKN7001-BEE-5_mw6bt2.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954679/ao-khoac-nu-AKN7001-BEE-6_kwcccy.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954681/ao-khoac-nu-AKN7001-BEE-7_fx0r0n.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi'),(_binary 'b��AC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954764/ao-khoac-nu-KNN7004-DEN-1_p9tpwf.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b��\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954753/ao-khoac-nu-KNN7004-BEE-2_fbbd6u.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954756/ao-khoac-nu-KNN7004-BEE-3_kvrl6q.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b���C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954757/ao-khoac-nu-KNN7004-BEE-4_vede62.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b��\'C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954758/ao-khoac-nu-KNN7004-BEE-5_wcd8gu.jpg',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b��JC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954758/ao-khoac-nu-KNN7004-BEE-6_eiwmfc.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b��QC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954761/ao-khoac-nu-KNN7004-BEE-7_ntwdm5.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b�\�iC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954764/ao-khoac-nu-KNN7004-DEN-1_p9tpwf.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954765/ao-khoac-nu-KNN7004-DEN-2_i2dmfb.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b�\�YC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954767/ao-khoac-nu-KNN7004-DEN-3_zoqaev.jpg',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b�\�YC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954769/ao-khoac-nu-KNN7004-DEN-4_js8vgp.jpg',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b�̄C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954772/ao-khoac-nu-KNN7004-DEN-5_oxg5n6.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b�ΊC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736954772/ao-khoac-nu-KNN7004-DEN-6_pyc85h.webp',_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi'),(_binary 'b�\�LC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955122/quan-short-nu-QSN7033-BEE-1_ulhpys.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�ҷC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955123/quan-short-nu-QSN7033-BEE-2_os2xpm.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955124/quan-short-nu-QSN7033-BEE-3_q2h5tn.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955126/quan-short-nu-QSN7033-BEE-4_abe82s.webp',_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�zC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955127/quan-short-nu-QSN7033-NAD-1_ysmg3a.webp',_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�#\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955128/quan-short-nu-QSN7033-NAD-2_wy4zio.webp',_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�&C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955131/quan-short-nu-QSN7033-NAD-3_mucx0q.webp',_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�(C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955132/quan-short-nu-QSN7033-NAD-4_mtxpl8.webp',_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi'),(_binary 'b�)�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955237/quan-jeans-nu-QJN7067-XDM-1_khoxws.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�-gC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955238/quan-jeans-nu-QJN7067-XDM-2_cqsuhg.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�0�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955240/quan-jeans-nu-QJN7067-XDM-3_ei32j4.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�2C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955242/quan-jeans-nu-QJN7067-XDM-4_affc57.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�B{C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955244/quan-jeans-nu-QJN7067-XDM-5_yf8thz.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�O�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955245/quan-jeans-nu-QJN7067-XDM-6_dlx9er.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�R\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955247/quan-jeans-nu-QJN7067-XDM-7_mrfdzz.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�T�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955249/quan-jeans-nu-QJN7067-XNH-1_qmed2j.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�VTC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955250/quan-jeans-nu-QJN7067-XNH-2_j9an4r.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�XC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955251/quan-jeans-nu-QJN7067-XNH-3_yocswr.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�Y\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955254/quan-jeans-nu-QJN7067-XNH-4_hfhe9b.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�[�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955255/quan-jeans-nu-QJN7067-XNH-5_bnbi2u.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�]C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955258/quan-jeans-nu-QJN7067-XNH-6_jq9dln.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�^�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955259/quan-jeans-nu-QJN7067-XNH-7_cohwuf.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi'),(_binary 'b�`C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955382/quan-jeans-nu-QJN6052-XDM-1_cfhjet.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�a�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955385/quan-jeans-nu-QJN6052-XDM-2_ahgnvg.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�dC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955387/quan-jeans-nu-QJN6052-XDM-3_yrsaaf.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�e\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955387/quan-jeans-nu-QJN6052-XDM-4_wglmct.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�gaC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955392/quan-jeans-nu-QJN6052-XDM-6_ofrzzv.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�i\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955394/quan-jeans-nu-QJN6052-XDM-7_ju2an0.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�k}C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955394/quan-jeans-nu-QJN6052-XDM-5_mn68er.webp',_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�moC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955395/quan-jeans-nu-QJN6052-XNH-1_m3em1p.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�o�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955398/quan-jeans-nu-QJN6052-XNH-2_dy9in4.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�q[C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955400/quan-jeans-nu-QJN6052-XNH-3_rtg8sv.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�r\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955402/quan-jeans-nu-QJN6052-XNH-4_uw8bnv.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�tVC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955404/quan-jeans-nu-QJN6052-XNH-5_qmmfky.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�wC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955405/quan-jeans-nu-QJN6052-XNH-6_rrlq5a.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi'),(_binary 'b�x\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',_binary '\0','https://res.cloudinary.com/dwyjwk0mf/image/upload/v1736955407/quan-jeans-nu-QJN6052-XNH-7_iz6c7b.webp',_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi');
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
INSERT INTO `product_product_variants` VALUES (_binary 'bwC?C�\�\"\�]ddi',_binary 'b~ifC�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~tC�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~x\ZC�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~z8C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~{\�C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~}\�C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~�tC�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~�RC�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~�LC�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~�\�C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~�\�C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~�C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~��C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~C�\�\"\�]ddi'),(_binary 'bwC?C�\�\"\�]ddi',_binary 'b~ĘC�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~ǑC�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�\�C�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�/C�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�yC�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�9C�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�5C�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�FC�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�,C�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�.C�\�\"\�]ddi'),(_binary 'bwL�C�\�\"\�]ddi',_binary 'b~\�oC�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~ݖC�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~\�C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~\�\�C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~\�C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~\�C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~\�\�C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~\�C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~\�4C�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~�cC�\�\"\�]ddi'),(_binary 'bwQ\�C�\�\"\�]ddi',_binary 'b~�MC�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b�C�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b�C�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b	EC�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b\�C�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b,C�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'bAC�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b\0C�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'bhC�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b�C�\�\"\�]ddi'),(_binary 'bwVC�\�\"\�]ddi',_binary 'b -C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b#C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b\'dC�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b*�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b-�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b1C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b3�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b5\�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b9�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b<nC�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'b?�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'bC\�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'bG+C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'bH\�C�\�\"\�]ddi'),(_binary 'bwX�C�\�\"\�]ddi',_binary 'bM\�C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'bQKC�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'bS\�C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'bW�C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'bZ\�C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'b\\\�C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'b^�C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'b`�C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'bb^C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'bd5C�\�\"\�]ddi'),(_binary 'bw[cC�\�\"\�]ddi',_binary 'be�C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'bg\�C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'bjC�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'blQC�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'bnKC�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'br_C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'buuC�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'bx#C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'b{,C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'b}C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'b~�C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'b�hC�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'b�C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bw]�C�\�\"\�]ddi',_binary 'b�C�\�\"\�]ddi'),(_binary 'bw_\�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bw_\�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bw_\�C�\�\"\�]ddi',_binary 'b�\�C�\�\"\�]ddi'),(_binary 'bwa\�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bwa\�C�\�\"\�]ddi',_binary 'b�ZC�\�\"\�]ddi'),(_binary 'bwa\�C�\�\"\�]ddi',_binary 'b�\�C�\�\"\�]ddi'),(_binary 'bwa\�C�\�\"\�]ddi',_binary 'b�RC�\�\"\�]ddi'),(_binary 'bwa\�C�\�\"\�]ddi',_binary 'b�#C�\�\"\�]ddi'),(_binary 'bwa\�C�\�\"\�]ddi',_binary 'b�\�C�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b�%C�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b�\�C�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b�cC�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b�&C�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b�\0C�\�\"\�]ddi'),(_binary 'bwc\�C�\�\"\�]ddi',_binary 'b\�\�C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'bɌC�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�1C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�\�C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b۬C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'bݸC�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b߯C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�mC�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�C�\�\"\�]ddi'),(_binary 'bwfmC�\�\"\�]ddi',_binary 'b\�C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b\�,C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b\��C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b\��C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b�mC�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b�YC�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b�\0dC�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b�^C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b��C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b�	7C�\�\"\�]ddi'),(_binary 'bwh�C�\�\"\�]ddi',_binary 'b�(C�\�\"\�]ddi');
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
INSERT INTO `product_variant` VALUES (_binary 'b~ifC�\�\"\�]ddi','VEM7003-DEN-M',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~tC�\�\"\�]ddi','VEM7003-DEN-L',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~x\ZC�\�\"\�]ddi','VEM7003-DEN-XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~z8C�\�\"\�]ddi','VEM7003-DEN-2XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~{\�C�\�\"\�]ddi','VEM7003-DEN-3XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b~}\�C�\�\"\�]ddi','VEM7003-NAU-M',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~�tC�\�\"\�]ddi','VEM7003-NAU-L',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~�RC�\�\"\�]ddi','VEM7003-NAU-XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~�LC�\�\"\�]ddi','VEM7003-NAU-2XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~�\�C�\�\"\�]ddi','VEM7003-NAU-3XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b~�\�C�\�\"\�]ddi','VEM7003-GHI-M',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~�C�\�\"\�]ddi','VEM7003-GHI-L',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~��C�\�\"\�]ddi','VEM7003-GHI-XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~C�\�\"\�]ddi','VEM7003-GHI-2XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~ĘC�\�\"\�]ddi','VEM7003-GHI-3XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwC?C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b~ǑC�\�\"\�]ddi','AKM7007-GHI-M',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�\�C�\�\"\�]ddi','AKM7007-GHI-L',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�/C�\�\"\�]ddi','AKM7007-GHI-XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�yC�\�\"\�]ddi','AKM7007-GHI-2XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�9C�\�\"\�]ddi','AKM7007-GHI-3XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b~\�5C�\�\"\�]ddi','AKM7007-DEN-M',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�FC�\�\"\�]ddi','AKM7007-DEN-L',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�,C�\�\"\�]ddi','AKM7007-DEN-XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�.C�\�\"\�]ddi','AKM7007-DEN-2XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�oC�\�\"\�]ddi','AKM7007-DEN-3XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b~ݖC�\�\"\�]ddi','PHM7009-XDE-M',10,_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�C�\�\"\�]ddi','PHM7009-XDE-L',10,_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�\�C�\�\"\�]ddi','PHM7009-XDE-XL',10,_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�C�\�\"\�]ddi','PHM7009-XDE-2XL',10,_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�C�\�\"\�]ddi','PHM7009-XDE-3XL',10,_binary 'bH\�@C�\�\"\�]ddi',_binary 'bwQ\�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b~\�\�C�\�\"\�]ddi','PHM7009-REU-M',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�C�\�\"\�]ddi','PHM7009-REU-L',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~\�4C�\�\"\�]ddi','PHM7009-REU-XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~�cC�\�\"\�]ddi','PHM7009-REU-2XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b~�MC�\�\"\�]ddi','PHM7009-REU-3XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwL�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b�C�\�\"\�]ddi','STM7055-XAM-M',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�C�\�\"\�]ddi','STM7055-XAM-L',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b	EC�\�\"\�]ddi','STM7055-XAM-XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b\�C�\�\"\�]ddi','STM7055-XAM-2XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b,C�\�\"\�]ddi','STM7055-XAM-3XL',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'bAC�\�\"\�]ddi','STM7055-DEN-M',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b\0C�\�\"\�]ddi','STM7055-DEN-L',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'bhC�\�\"\�]ddi','STM7055-DEN-XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�C�\�\"\�]ddi','STM7055-DEN-2XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b -C�\�\"\�]ddi','STM7055-DEN-3XL',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwVC�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b#C�\�\"\�]ddi','QKM7005-GHI-28',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b\'dC�\�\"\�]ddi','QKM7005-GHI-29',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b*�C�\�\"\�]ddi','QKM7005-GHI-30',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b-�C�\�\"\�]ddi','QKM7005-GHI-31',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU�QC�\�\"\�]ddi'),(_binary 'b1C�\�\"\�]ddi','QKM7005-GHI-32',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU�#C�\�\"\�]ddi'),(_binary 'b3�C�\�\"\�]ddi','QKM7005-GHI-33',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b5\�C�\�\"\�]ddi','QKM7005-GHI-34',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU�C�\�\"\�]ddi'),(_binary 'b9�C�\�\"\�]ddi','QKM7005-DEN-28',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b<nC�\�\"\�]ddi','QKM7005-DEN-29',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b?�C�\�\"\�]ddi','QKM7005-DEN-30',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bC\�C�\�\"\�]ddi','QKM7005-DEN-31',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU�QC�\�\"\�]ddi'),(_binary 'bG+C�\�\"\�]ddi','QKM7005-DEN-32',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU�#C�\�\"\�]ddi'),(_binary 'bH\�C�\�\"\�]ddi','QKM7005-DEN-33',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bM\�C�\�\"\�]ddi','QKM7005-DEN-34',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwX�C�\�\"\�]ddi',_binary 'bU�C�\�\"\�]ddi'),(_binary 'bQKC�\�\"\�]ddi','QKM6017-XAM-29',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bS\�C�\�\"\�]ddi','QKM6017-XAM-30',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bW�C�\�\"\�]ddi','QKM6017-XAM-31',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU�QC�\�\"\�]ddi'),(_binary 'bZ\�C�\�\"\�]ddi','QKM6017-XAM-32',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU�#C�\�\"\�]ddi'),(_binary 'b\\\�C�\�\"\�]ddi','QKM6017-XAM-33',10,_binary 'bH\�\�C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b^�C�\�\"\�]ddi','QKM6017-DEN-29',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b`�C�\�\"\�]ddi','QKM6017-DEN-30',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bb^C�\�\"\�]ddi','QKM6017-DEN-31',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU�QC�\�\"\�]ddi'),(_binary 'bd5C�\�\"\�]ddi','QKM6017-DEN-32',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU�#C�\�\"\�]ddi'),(_binary 'be�C�\�\"\�]ddi','QKM6017-DEN-33',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw[cC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bg\�C�\�\"\�]ddi','QJM6041-XAD-28',10,_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'bjC�\�\"\�]ddi','QJM6041-XAD-29',10,_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'blQC�\�\"\�]ddi','QJM6041-XAD-30',10,_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bnKC�\�\"\�]ddi','QJM6041-XAD-31',10,_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU�QC�\�\"\�]ddi'),(_binary 'br_C�\�\"\�]ddi','QJM6041-XAD-32',10,_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU�#C�\�\"\�]ddi'),(_binary 'buuC�\�\"\�]ddi','QJM6041-XAD-33',10,_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'bx#C�\�\"\�]ddi','QJM6041-XAD-34',10,_binary 'bH\�YC�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU�C�\�\"\�]ddi'),(_binary 'b{,C�\�\"\�]ddi','QJM6041-DEN-28',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b}C�\�\"\�]ddi','QJM6041-DEN-29',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b~�C�\�\"\�]ddi','QJM6041-DEN-30',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b�hC�\�\"\�]ddi','QJM6041-DEN-31',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU�QC�\�\"\�]ddi'),(_binary 'b�C�\�\"\�]ddi','QJM6041-DEN-32',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU�#C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','QJM6041-DEN-33',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b�C�\�\"\�]ddi','QJM6041-DEN-34',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bw]�C�\�\"\�]ddi',_binary 'bU�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','AKN7001-BEE-S',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','AKN7001-BEE-M',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','AKN7001-BEE-L',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bw_\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','KNN7004-BEE-S',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b�ZC�\�\"\�]ddi','KNN7004-BEE-M',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','KNN7004-BEE-L',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�RC�\�\"\�]ddi','KNN7004-DEN-S',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b�#C�\�\"\�]ddi','KNN7004-DEN-M',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','KNN7004-DEN-L',10,_binary 'bH̹C�\�\"\�]ddi',_binary 'bwa\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�%C�\�\"\�]ddi','QSN7033-BEE-S',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b�\�C�\�\"\�]ddi','QSN7033-BEE-M',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�cC�\�\"\�]ddi','QSN7033-BEE-L',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�&C�\�\"\�]ddi','QSN7033-BEE-XL',10,_binary 'bHɿC�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','QSN7033-NAD-S',10,_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','QSN7033-NAD-M',10,_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�\0C�\�\"\�]ddi','QSN7033-NAD-L',10,_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b\�\�C�\�\"\�]ddi','QSN7033-NAD-XL',10,_binary 'bH\�}C�\�\"\�]ddi',_binary 'bwc\�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'bɌC�\�\"\�]ddi','QJN7067-XDM-25',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�DC�\�\"\�]ddi'),(_binary 'b\�1C�\�\"\�]ddi','QJN7067-XDM-26',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�%C�\�\"\�]ddi'),(_binary 'b\�\�C�\�\"\�]ddi','QJN7067-XDM-27',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b۬C�\�\"\�]ddi','QJN7067-XDM-28',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'bݸC�\�\"\�]ddi','QJN7067-XDM-29',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b߯C�\�\"\�]ddi','QJN7067-XDM-30',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b\�C�\�\"\�]ddi','QJN7067-XNH-25',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�DC�\�\"\�]ddi'),(_binary 'b\�mC�\�\"\�]ddi','QJN7067-XNH-26',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�%C�\�\"\�]ddi'),(_binary 'b\�C�\�\"\�]ddi','QJN7067-XNH-27',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b\�C�\�\"\�]ddi','QJN7067-XNH-28',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b\�C�\�\"\�]ddi','QJN7067-XNH-29',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b\�C�\�\"\�]ddi','QJN7067-XNH-30',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwfmC�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b\�,C�\�\"\�]ddi','QJN6052-XDM-25',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�DC�\�\"\�]ddi'),(_binary 'b\��C�\�\"\�]ddi','QJN6052-XDM-26',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�%C�\�\"\�]ddi'),(_binary 'b\��C�\�\"\�]ddi','QJN6052-XDM-27',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�mC�\�\"\�]ddi','QJN6052-XDM-28',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�YC�\�\"\�]ddi','QJN6052-XDM-29',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','QJN6052-XDM-30',10,_binary 'bH�wC�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b�\0dC�\�\"\�]ddi','QJN6052-XNH-25',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�DC�\�\"\�]ddi'),(_binary 'b�^C�\�\"\�]ddi','QJN6052-XNH-26',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�%C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','QJN6052-XNH-27',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b��C�\�\"\�]ddi','QJN6052-XNH-28',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU\�\�C�\�\"\�]ddi'),(_binary 'b�	7C�\�\"\�]ddi','QJN6052-XNH-29',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi'),(_binary 'b�(C�\�\"\�]ddi','QJN6052-XNH-30',10,_binary 'bH¯C�\�\"\�]ddi',_binary 'bwh�C�\�\"\�]ddi',_binary 'bU��C�\�\"\�]ddi');
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
INSERT INTO `role` VALUES (_binary 'b@q\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Role for regular admin with basic permissions','ADMIN'),(_binary 'b@}�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','Role for regular users with basic permissions','USER');
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
INSERT INTO `role_permissions` VALUES (_binary 'b@q\�C�\�\"\�]ddi',_binary 'bB�\�C�\�\"\�]ddi'),(_binary 'b@q\�C�\�\"\�]ddi',_binary 'bBàC�\�\"\�]ddi'),(_binary 'b@q\�C�\�\"\�]ddi',_binary 'bB\�]C�\�\"\�]ddi'),(_binary 'b@q\�C�\�\"\�]ddi',_binary 'bB\�\�C�\�\"\�]ddi'),(_binary 'b@q\�C�\�\"\�]ddi',_binary 'bB\�nC�\�\"\�]ddi'),(_binary 'b@}�C�\�\"\�]ddi',_binary 'bB�\�C�\�\"\�]ddi'),(_binary 'b@}�C�\�\"\�]ddi',_binary 'bBàC�\�\"\�]ddi'),(_binary 'b@}�C�\�\"\�]ddi',_binary 'bB\�]C�\�\"\�]ddi'),(_binary 'b@}�C�\�\"\�]ddi',_binary 'bB\�\�C�\�\"\�]ddi'),(_binary 'b@}�C�\�\"\�]ddi',_binary 'bB\�nC�\�\"\�]ddi');
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
INSERT INTO `size` VALUES (_binary 'bU\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','S'),(_binary 'bU\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','M'),(_binary 'bU\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','L'),(_binary 'bU\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','XL'),(_binary 'bU\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','2XL'),(_binary 'bU\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','3XL'),(_binary 'bU\�uC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','4XL'),(_binary 'bU\�DC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','25'),(_binary 'bU\�%C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','26'),(_binary 'bU\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','27'),(_binary 'bU\�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','28'),(_binary 'bU��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','29'),(_binary 'bU��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','30'),(_binary 'bU�QC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','31'),(_binary 'bU�#C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','32'),(_binary 'bU��C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','33'),(_binary 'bU�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','34'),(_binary 'bU�?C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','35'),(_binary 'bU�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','36'),(_binary 'bU�\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','37'),(_binary 'bV\0�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','38'),(_binary 'bVRC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','39'),(_binary 'bVC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','40'),(_binary 'bVC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','42'),(_binary 'bV\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','43'),(_binary 'bVAC�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','44'),(_binary 'bV�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000','45');
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
  `created_at` datetime(6) NOT NULL,
  `deleted_at` datetime(6) DEFAULT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
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
INSERT INTO `user` VALUES (_binary 'bE\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,'admin@gmail.com',_binary '','Admin','Admin',_binary '\0','{bcrypt}$2a$10$WUd3NdUPvo0/dWteuLXm6.ugk1CIQK/tjkTcbgFrUbWfuYLEyhfjW','0123456789','MANUAL',NULL),(_binary 'bE\�C�\�\"\�]ddi','2025-06-07 16:47:11.000000',NULL,'2025-06-07 16:47:11.000000',NULL,NULL,'user@gmail.com',_binary '','User','User',_binary '\0','{bcrypt}$2a$10$f2zvi3JQBdjeYZJaxR0yJ./MjE9RXmliZbAr0maKHvfySxgm4Y/2a','0122222222','MANUAL',NULL);
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

-- Dump completed on 2025-06-07 16:52:05
