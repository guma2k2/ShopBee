-- MySQL dump 10.13  Distrib 8.0.31, for macos12 (x86_64)
--
-- Host: localhost    Database: nhom11
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `ct_hoa_don`
--

DROP TABLE IF EXISTS `ct_hoa_don`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_hoa_don` (
  `id` int NOT NULL AUTO_INCREMENT,
  `gia` double DEFAULT NULL,
  `so_luong` int NOT NULL,
  `hoa_don_id` int DEFAULT NULL,
  `san_pham_id` int DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK44kusnmkh0ki0vecvsig9d48m` (`hoa_don_id`),
  KEY `FKvsmw62nqd38x1964byf7l8xr` (`san_pham_id`),
  KEY `FK7f257sy1aremlkq9fxbet0q5s` (`size_id`),
  CONSTRAINT `FK44kusnmkh0ki0vecvsig9d48m` FOREIGN KEY (`hoa_don_id`) REFERENCES `don_dat_hang` (`id`),
  CONSTRAINT `FK7f257sy1aremlkq9fxbet0q5s` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`),
  CONSTRAINT `FKvsmw62nqd38x1964byf7l8xr` FOREIGN KEY (`san_pham_id`) REFERENCES `san_pham` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ct_hoa_don`
--

LOCK TABLES `ct_hoa_don` WRITE;
/*!40000 ALTER TABLE `ct_hoa_don` DISABLE KEYS */;
INSERT INTO `ct_hoa_don` VALUES (1,100000,1,1,1,NULL),(2,200000,2,2,1,NULL),(4,100000,1,4,1,NULL),(6,100000,1,7,1,1),(7,100000,1,8,1,1),(8,100000,1,9,1,1),(9,54000,1,10,13,NULL),(10,100000,1,11,1,1),(11,54000,1,12,13,NULL);
/*!40000 ALTER TABLE `ct_hoa_don` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ct_nhap_hang`
--

DROP TABLE IF EXISTS `ct_nhap_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ct_nhap_hang` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `so_luong` int NOT NULL,
  `nhan_vien_id` int DEFAULT NULL,
  `san_pham_id` int DEFAULT NULL,
  `gia_moi_san_pham` bigint DEFAULT NULL,
  `thoi_gian_nhap` datetime(6) DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKmqe5dd69stu03olchiy2pufiq` (`nhan_vien_id`),
  KEY `FKckobu948qqt180i02k9uqw4t` (`san_pham_id`),
  KEY `FK92pdj9ps2wh7jxp6ff9lx9spj` (`size_id`),
  CONSTRAINT `FK92pdj9ps2wh7jxp6ff9lx9spj` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`),
  CONSTRAINT `FKckobu948qqt180i02k9uqw4t` FOREIGN KEY (`san_pham_id`) REFERENCES `san_pham` (`id`),
  CONSTRAINT `FKmqe5dd69stu03olchiy2pufiq` FOREIGN KEY (`nhan_vien_id`) REFERENCES `nhan_vien` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ct_nhap_hang`
--

LOCK TABLES `ct_nhap_hang` WRITE;
/*!40000 ALTER TABLE `ct_nhap_hang` DISABLE KEYS */;
INSERT INTO `ct_nhap_hang` VALUES (8,2,1,1,100000,'2023-05-17 15:31:16.158227',1),(9,2,1,1,100000,'2023-05-17 14:40:42.234030',2),(10,100,1,13,87000,'2023-05-23 12:06:33.047498',NULL),(11,1000,1,1,100000,'2023-05-23 21:39:57.940918',1);
/*!40000 ALTER TABLE `ct_nhap_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `don_dat_hang`
--

DROP TABLE IF EXISTS `don_dat_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `don_dat_hang` (
  `id` int NOT NULL AUTO_INCREMENT,
  `ngay_tao` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `thanh_tien` double NOT NULL,
  `customer_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhl1n549bg51jsie7ga4lylevo` (`customer_id`),
  CONSTRAINT `FKhl1n549bg51jsie7ga4lylevo` FOREIGN KEY (`customer_id`) REFERENCES `khach_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `don_dat_hang`
--

LOCK TABLES `don_dat_hang` WRITE;
/*!40000 ALTER TABLE `don_dat_hang` DISABLE KEYS */;
INSERT INTO `don_dat_hang` VALUES (1,'2023-05-08 16:23:38.379640','DELIVERED',100000,1),(2,'2023-05-08 16:26:44.214743','NEW',200000,1),(4,'2023-05-08 17:36:16.116622','DELIVERED',100000,1),(7,'2023-05-17 18:14:42.301024','NEW',100000,1),(8,'2023-05-17 18:17:00.097599','NEW',100000,3),(9,'2023-05-17 18:20:52.235591','NEW',100000,1),(10,'2023-05-23 17:49:20.200942','NEW',54000,1),(11,'2023-05-23 21:40:37.512644','DELIVERED',100000,10),(12,'2023-05-24 19:44:54.167996','DELIVERED',54000,1);
/*!40000 ALTER TABLE `don_dat_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gio_hang`
--

DROP TABLE IF EXISTS `gio_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gio_hang` (
  `id` int NOT NULL AUTO_INCREMENT,
  `so_luong` int DEFAULT NULL,
  `khach_hang_id` bigint DEFAULT NULL,
  `san_pham_id` int DEFAULT NULL,
  `size_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtfg3dplbmn3wiwy26si1daye3` (`khach_hang_id`),
  KEY `FK39mx182qcf584c8jgdlstnwad` (`san_pham_id`),
  KEY `FKnsript6nr5x0hdegbbk4254m7` (`size_id`),
  CONSTRAINT `FK39mx182qcf584c8jgdlstnwad` FOREIGN KEY (`san_pham_id`) REFERENCES `san_pham` (`id`),
  CONSTRAINT `FKnsript6nr5x0hdegbbk4254m7` FOREIGN KEY (`size_id`) REFERENCES `size` (`id`),
  CONSTRAINT `FKtfg3dplbmn3wiwy26si1daye3` FOREIGN KEY (`khach_hang_id`) REFERENCES `khach_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gio_hang`
--

LOCK TABLES `gio_hang` WRITE;
/*!40000 ALTER TABLE `gio_hang` DISABLE KEYS */;
INSERT INTO `gio_hang` VALUES (22,2,10,13,NULL);
/*!40000 ALTER TABLE `gio_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `khach_hang`
--

DROP TABLE IF EXISTS `khach_hang`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `khach_hang` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `dia_chi` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `ho` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photos` varchar(255) DEFAULT NULL,
  `sdt` varchar(255) DEFAULT NULL,
  `ten` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_j3lhg8opnqln2wcb41cp14xn9` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `khach_hang`
--

LOCK TABLES `khach_hang` WRITE;
/*!40000 ALTER TABLE `khach_hang` DISABLE KEYS */;
INSERT INTO `khach_hang` VALUES (1,NULL,'thuanngo723@yahoo.com','Ngô',NULL,NULL,NULL,'Thuan'),(3,NULL,'thuanngo3072002@gmail.com','Ngô',NULL,NULL,NULL,'Thuan'),(10,'Bien Hoa','n20dccn153@student.ptithcm.edu.vn','thu','thuan2023',NULL,'0918467137','thu');
/*!40000 ALTER TABLE `khach_hang` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `loai_san_pham`
--

DROP TABLE IF EXISTS `loai_san_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `loai_san_pham` (
  `id` int NOT NULL AUTO_INCREMENT,
  `photo` varchar(255) DEFAULT NULL,
  `ten` varchar(255) NOT NULL,
  `trang_thai` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `loai_san_pham`
--

LOCK TABLES `loai_san_pham` WRITE;
/*!40000 ALTER TABLE `loai_san_pham` DISABLE KEYS */;
INSERT INTO `loai_san_pham` VALUES (1,'aokhoac.jpeg','Áo khoác ',_binary ''),(2,'quanduivang.jpeg','Quần đùi',_binary ''),(3,'aodaitay.jpeg','Áo dài tay',_binary ''),(4,'quantay.jpeg','Quần tây',_binary ''),(5,'aosweater.jpeg','Áo sweater',_binary ''),(7,'dam.jpeg','Đầm',_binary '');
/*!40000 ALTER TABLE `loai_san_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhan_vien`
--

DROP TABLE IF EXISTS `nhan_vien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhan_vien` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dia_chi` varchar(255) DEFAULT NULL,
  `authentication_type` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `forgot_password_code` varchar(100) DEFAULT NULL,
  `ho` varchar(40) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `photos` varchar(64) DEFAULT NULL,
  `sdt` varchar(20) DEFAULT NULL,
  `ten` varchar(40) NOT NULL,
  `trang_thai` bit(1) DEFAULT NULL,
  `verification_code` varchar(64) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_12waxxsiniyddv2lt9ianfh8a` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhan_vien`
--

LOCK TABLES `nhan_vien` WRITE;
/*!40000 ALTER TABLE `nhan_vien` DISABLE KEYS */;
INSERT INTO `nhan_vien` VALUES (1,'Bien Hoa','DATABASE','thuanngo307202@gmail.com',NULL,'Ngo','$2a$10$rfU230XMA9SxE2Bd1Bsd3uuhM3ML6yPbMDRYpIG1N2cR40fPKCZBa','nguctoivarong.jpeg','0918462888','Thuan',_binary '',NULL),(2,NULL,'FACEBOOK','thuanngo723@yahoo.com',NULL,'Ngo','$2a$10$N.hzQjxaBkqWJJheGz/cpOv6FNKry9bmfTI5xJXm5YGRDi85DxF7C',NULL,'0918462888','Thuan',_binary '',NULL),(4,'Đồng Nai','DATABASE','thuanngo722@yahoo.com',NULL,'Ngô','$2a$10$Dv/hPWIhrvkTmZ2HCTu2UOROlMHp1ldhci1sOtfyBJmqQ4GtKxIYC','avatar.png','0918467137','Thuận',_binary '',NULL),(5,'ho chi minh','GOOGLE','thuanngo3072002@gmail.com',NULL,'thuan','','avatar.png','0918467137',' ngo',_binary '',NULL),(10,'Bien Hoa','DATABASE','n20dccn153@student.ptithcm.edu.vn','I2gPfrXE8i32mdFammNFXMjdiwsDNGYu9P2smFVgBM9hrrIM2tVfpq5TXZsujbkv','thu','$2a$10$5pa.ghsYWb3qj2ynlNlaWuUw3RCKoFTEZk1JiEJF1Lb3IVWKK50w2','quanlongloe.jpeg','0918467137','thu',_binary '',NULL),(11,'Đồng Nai',NULL,'kanna.allada@gmail.com',NULL,'Ngô ','$2a$10$R7mIhJC4vuI2MwumS3KjrubVITv.b.PLLXWewUXds5QRYJW/ujw8m','avatar.png','Hồ Chí Minh','Quần đùi',_binary '',NULL),(12,'Đồng Nai',NULL,'thu2k2@gmail.com',NULL,'thu','$2a$10$5rsEJC6r1tdaRrr5kX0W5uL.jiPVH7W6Y6N55NPTsjhiq5t/APzJq','avatar2.jpeg','0918467137','thu',_binary '',NULL);
/*!40000 ALTER TABLE `nhan_vien` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nhanviens_roles`
--

DROP TABLE IF EXISTS `nhanviens_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nhanviens_roles` (
  `nhavien_id` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`nhavien_id`,`role_id`),
  KEY `FKidsgqmt48p5w5wftamr0a3pda` (`role_id`),
  CONSTRAINT `FKidsgqmt48p5w5wftamr0a3pda` FOREIGN KEY (`role_id`) REFERENCES `vai_tro` (`id`),
  CONSTRAINT `FKnpow3g5mrrm0ml90rvx6dqeay` FOREIGN KEY (`nhavien_id`) REFERENCES `nhan_vien` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nhanviens_roles`
--

LOCK TABLES `nhanviens_roles` WRITE;
/*!40000 ALTER TABLE `nhanviens_roles` DISABLE KEYS */;
INSERT INTO `nhanviens_roles` VALUES (1,1),(5,1),(12,1),(4,2),(11,2),(2,3),(10,3);
/*!40000 ALTER TABLE `nhanviens_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_track`
--

DROP TABLE IF EXISTS `order_track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_track` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `notes` varchar(255) DEFAULT NULL,
  `status` varchar(45) NOT NULL,
  `updated_time` datetime(6) DEFAULT NULL,
  `order_id` int DEFAULT NULL,
  `shipper_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK7bx0x0cev2ujueux6wgk79bee` (`order_id`),
  KEY `FKr3tipslf5j8cowcdq8rys46iu` (`shipper_id`),
  CONSTRAINT `FK7bx0x0cev2ujueux6wgk79bee` FOREIGN KEY (`order_id`) REFERENCES `don_dat_hang` (`id`),
  CONSTRAINT `FKr3tipslf5j8cowcdq8rys46iu` FOREIGN KEY (`shipper_id`) REFERENCES `nhan_vien` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_track`
--

LOCK TABLES `order_track` WRITE;
/*!40000 ALTER TABLE `order_track` DISABLE KEYS */;
INSERT INTO `order_track` VALUES (1,'Đon đặt hàng đã được đặt','NEW','2023-05-08 16:23:38.439033',1,4),(2,'Đon đặt hàng đã được đặt','NEW','2023-05-08 16:26:44.230868',2,4),(4,'Order was picked by the shipper','PICKED','2023-05-08 16:34:19.326817',1,4),(5,'Order was shipping by the shipper','SHIPPING','2023-05-08 16:34:21.680882',1,4),(6,'Order was delivered by the shipper','DELIVERED','2023-05-08 16:34:24.008576',1,4),(7,'Đon đặt hàng đã được đặt','NEW','2023-05-08 17:36:16.181302',4,4),(8,'Order was picked by the shipper','PICKED','2023-05-08 17:37:18.292119',4,4),(9,'Order was shipping by the shipper','SHIPPING','2023-05-08 17:37:22.231482',4,4),(10,'Order was delivered by the shipper','DELIVERED','2023-05-08 17:37:25.759944',4,4),(16,'Đon đặt hàng đã được đặt','NEW','2023-05-17 18:14:42.341495',7,4),(17,'Đon đặt hàng đã được đặt','NEW','2023-05-17 18:17:00.111756',8,4),(18,'Đon đặt hàng đã được đặt','NEW','2023-05-17 18:20:52.255814',9,4),(19,'Đon đặt hàng đã được đặt','NEW','2023-05-23 17:49:20.231732',10,NULL),(20,'Đon đặt hàng đã được đặt','NEW','2023-05-23 21:40:37.562238',11,NULL),(21,'Order was picked by the shipper','PICKED','2023-05-23 21:41:46.217224',11,4),(22,'Order was shipping by the shipper','SHIPPING','2023-05-23 21:41:49.795379',11,4),(23,'Order was delivered by the shipper','DELIVERED','2023-05-23 21:41:52.961909',11,4),(24,'Đon đặt hàng đã được đặt','NEW','2023-05-24 19:44:54.206527',12,NULL),(25,'Order was picked by the shipper','PICKED','2023-05-24 19:45:46.636769',12,4),(26,'Order was shipping by the shipper','SHIPPING','2023-05-24 19:45:50.055867',12,4),(27,'Order was delivered by the shipper','DELIVERED','2023-05-24 19:45:53.126415',12,4);
/*!40000 ALTER TABLE `order_track` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review`
--

DROP TABLE IF EXISTS `review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `comment` varchar(1000) DEFAULT NULL,
  `headline` varchar(255) DEFAULT NULL,
  `rating` int NOT NULL,
  `review_time` datetime(6) DEFAULT NULL,
  `khach_hang_id` bigint DEFAULT NULL,
  `san_pham_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKkt3qodog08sx3a5ps2i2sb42f` (`khach_hang_id`),
  KEY `FK57r9k4s9639u9i5ywqkpc0jrf` (`san_pham_id`),
  CONSTRAINT `FK57r9k4s9639u9i5ywqkpc0jrf` FOREIGN KEY (`san_pham_id`) REFERENCES `san_pham` (`id`),
  CONSTRAINT `FKkt3qodog08sx3a5ps2i2sb42f` FOREIGN KEY (`khach_hang_id`) REFERENCES `khach_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review`
--

LOCK TABLES `review` WRITE;
/*!40000 ALTER TABLE `review` DISABLE KEYS */;
INSERT INTO `review` VALUES (1,'Bao bì đẹp \r\nĐóng gói cẩn thận','Sản phẩm chất lượng',4,'2023-05-08 16:40:41.905484',1,1),(3,'Màu sắc: ok \r\nShipper: đẹp trai','Sản phẩm chất lượng',5,'2023-05-23 21:42:58.361906',10,1),(4,'Sản phẩm phù hợp với mình lắm. Thank you Shop','Sản phẩm chất lượng',4,'2023-05-24 20:00:45.461311',1,13);
/*!40000 ALTER TABLE `review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `review_vote`
--

DROP TABLE IF EXISTS `review_vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `review_vote` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `voted` bit(1) NOT NULL,
  `khach_hang_id` bigint DEFAULT NULL,
  `review_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsugmjooo1olyd2e9pg9dpqe9o` (`khach_hang_id`),
  KEY `FKjh1cnatsu8d6bfkrms74ns6os` (`review_id`),
  CONSTRAINT `FKjh1cnatsu8d6bfkrms74ns6os` FOREIGN KEY (`review_id`) REFERENCES `review` (`id`),
  CONSTRAINT `FKsugmjooo1olyd2e9pg9dpqe9o` FOREIGN KEY (`khach_hang_id`) REFERENCES `khach_hang` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `review_vote`
--

LOCK TABLES `review_vote` WRITE;
/*!40000 ALTER TABLE `review_vote` DISABLE KEYS */;
INSERT INTO `review_vote` VALUES (2,_binary '',3,1),(3,_binary '',1,4);
/*!40000 ALTER TABLE `review_vote` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `san_pham`
--

DROP TABLE IF EXISTS `san_pham`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `san_pham` (
  `id` int NOT NULL AUTO_INCREMENT,
  `anh` varchar(100) NOT NULL,
  `gia` int NOT NULL,
  `mo_ta` varchar(800) DEFAULT NULL,
  `ten` varchar(100) NOT NULL,
  `trang_thai` bit(1) DEFAULT NULL,
  `loai_san_pham_id` int DEFAULT NULL,
  `so_luong` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ixkt2s9hxy5bvdgk6g60ewax` (`loai_san_pham_id`),
  CONSTRAINT `FK8ixkt2s9hxy5bvdgk6g60ewax` FOREIGN KEY (`loai_san_pham_id`) REFERENCES `loai_san_pham` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `san_pham`
--

LOCK TABLES `san_pham` WRITE;
/*!40000 ALTER TABLE `san_pham` DISABLE KEYS */;
INSERT INTO `san_pham` VALUES (1,'aokhoac2.jpeg',100000,'<font color=\"#000000\">- Free ship, chất lượng vải tốt&nbsp;</font>','Áo lạnh',_binary '',1,0),(2,'aofake.jpeg',100000,'<div>- Chất lương vải : đẹp&nbsp;</div><div>-&nbsp; Xuất sứ : Trung Quốc</div>','Áo hoodie',_binary '',1,0),(3,'aosweaternu.jpeg',120000,'<div><div>- Chất liệu: Nỉ</div><div>- Kích thước: freesize dưới 68kg tùy chiều cao</div></div>','Áo nỉ nữ cổ bẻ khóa',_binary '',5,0),(4,'aosweaternucaocap.jpeg',85000,'Chất liệu : thun nỉ, ngoài thoáng mát dưới trời nắng<div><br></div>','Áo Hoodie Sweater Nữ Cao Cấp',_binary '',5,0),(5,'aokhoaccardigan.jpeg',65000,'<div>- Form: free-size, 60-70kg đổ lại mặc ok&nbsp;</div><div>- Kích thước: dài 66cm, ngang 56cm , tay 50cm, bắp tay 44cm&nbsp;</div>','Áo Khoác Cardigan W Xanh Mặt Cười Ulzzang',_binary '',1,0),(6,'damhaiday.jpeg',227000,'<div><span style=\"font-size: 1rem;\">- Đường may tinh tế, tỉ mỉ đến từng chi tiết&nbsp;</span><br><br></div>\r\n','Đầm hai dây bản to xốp nổi dáng xòe dự tiệc đi biển sang chảnh cho nữ ',_binary '',7,0),(7,'vay2day.jpeg',159000,'<div><div>Váy 2 dây caro đỏ vintage dáng dài, đầm dài hai dây sọc caro dáng xoè chữ A bánh bèo tiểu thư</div></div>','Váy 2 dây caro đỏ vintage dáng xoè chữ A bánh bèo tiểu thư',_binary '',7,0),(8,'vaytieuthu.jpeg',109000,'<div><div>Chất liệu gấm xốp</div><div>size S eo 67cm</div><div>Size m eo 74cm</div></div>','Váy Tiểu Thư Xốp Bèo Cổ Phong Cách Nhật Bản, Đầm Nữ Bánh Bèo Màu Be',_binary '',7,0),(9,'quanjeannu.jpeg',125000,'<div><div>Size: S-M-L-XL</div></div><div><div>Chất liệu: Jean Co giãn</div></div>','Quần bò jean nữ ống loe đứng jeans cạp cao co giãn phong cách style jeanhot_0102 ms21',_binary '',4,0),(10,'quanduinu.jpeg',127000,'<div><div>Quần Giả Váy denim Lưng Cao có đai Dáng Ôm<span class=\"Apple-converted-space\">&nbsp;</span></div></div><div><span class=\"Apple-converted-space\"><div>Thời Trang Hàn Quốc Quyến Rũ Cho Nữ</div></span></div>','Quần Giả Váy denim Lưng Cao có đai Dáng Ôm',_binary '',2,0),(11,'aodaitaynam.jpeg',80000,'<div><div>Chất Liệu: Thun dày dặn vừa phải-co dãn 4 chiều, mềm mịn</div></div><div><div>Size áo: FREESIZE<span class=\"Apple-converted-space\">&nbsp; </span>(Từ 40kg đến 67kg mặc sẽ rất đẹp)</div></div>','Áo Thun Tay Dài Unisex',_binary '',3,0),(12,'quantaynam.jpeg',84000,'<div><div>Màu: Ghi sáng, Đen, xanh đen.</div></div><div><div>Quần Tây Nam: Vải co dãn , mặc mát và chất liệu nhẹ hơn so với vải bình thường</div></div>','Quần Tây Nam - Quần Âu Nam TCE',_binary '',4,0),(13,'quanlongloe.jpeg',54000,'<div><div>- Thương hiệu: Timo Fashion</div></div><div><div>- Xuất Xứ: Việt Nam</div></div>','Quần ống loe siêu tôn dáng',_binary '',4,98);
/*!40000 ALTER TABLE `san_pham` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `setting`
--

DROP TABLE IF EXISTS `setting`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `setting` (
  `key` varchar(40) NOT NULL,
  `loai` varchar(255) NOT NULL,
  `value` varchar(1000) NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `setting`
--

LOCK TABLES `setting` WRITE;
/*!40000 ALTER TABLE `setting` DISABLE KEYS */;
INSERT INTO `setting` VALUES ('CUSTOMER_VERIFY_CONTENT','MAIL_TEMPLATES','<span style=\"font-size:18px;\">Dear [[name]]<br>\r\nPlease the link below to verify your registration&nbsp;<br>\r\n<br>\r\n<a href=\"[[URL]]\" target=\"_self\"><font color=\"#0000ff\">Verify</font></a><br>\r\n<br>\r\nThank you,&nbsp;<br>\r\nShopBee team&nbsp;</span><div><br></div>'),('CUSTOMER_VERIFY_SUBJECT','MAIL_TEMPLATES','Please verify your registration to continue shopping'),('MAIL_FROM','MAIL_SERVER','thuanngo3072002@gmail.com'),('MAIL_HOST','MAIL_SERVER','smtp.gmail.com'),('MAIL_PASSWORD','MAIL_SERVER','jsudbijepfpjzjwc'),('MAIL_PORT','MAIL_SERVER','587'),('MAIL_SENDER_NAME','MAIL_SERVER','MyShop_ShopBee'),('MAIL_USERNAME','MAIL_SERVER','thuanngo3072002@gmail.com'),('SMTP_AUTH','MAIL_SERVER','true'),('SMTP_SECURED','MAIL_SERVER','true');
/*!40000 ALTER TABLE `setting` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size`
--

DROP TABLE IF EXISTS `size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size` (
  `id` bigint NOT NULL,
  `mo_ta` varchar(30) DEFAULT NULL,
  `so_luong` bigint NOT NULL,
  `ten` varchar(255) DEFAULT NULL,
  `san_pham_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKfkexq19dgof7k23fqk69ac5bj` (`san_pham_id`),
  CONSTRAINT `FKfkexq19dgof7k23fqk69ac5bj` FOREIGN KEY (`san_pham_id`) REFERENCES `san_pham` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size`
--

LOCK TABLES `size` WRITE;
/*!40000 ALTER TABLE `size` DISABLE KEYS */;
INSERT INTO `size` VALUES (1,'',999,'M',1),(2,'',0,'N',1),(202,'Size M',0,'M',2),(252,'asf',0,'M',3),(253,'ád',0,'N',3),(254,'S',0,'S',4),(255,'M',0,'M',4),(256,'L',0,'L',4),(257,'XL',0,'XL',4),(302,'S',0,'S',5),(352,'S',0,'Size S',6),(353,'M',0,'Size M',6),(354,'L',0,'Size L',6),(355,'full size',0,'F',7),(356,'s',0,'S',8),(357,'m',0,'M',8),(358,'s',0,'S',9),(359,'M',0,'M',9),(360,'L',0,'L',9),(361,'XL',0,'XL',9),(403,'s',0,'S',10),(404,'m',0,'M',10),(405,'l',0,'L',10),(406,'full size',0,'F',11),(407,'28',0,'Size28',12),(408,'29',0,'Size29',12),(409,'30',0,'Size30',12);
/*!40000 ALTER TABLE `size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `size_seq`
--

DROP TABLE IF EXISTS `size_seq`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `size_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `size_seq`
--

LOCK TABLES `size_seq` WRITE;
/*!40000 ALTER TABLE `size_seq` DISABLE KEYS */;
INSERT INTO `size_seq` VALUES (1);
/*!40000 ALTER TABLE `size_seq` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vai_tro`
--

DROP TABLE IF EXISTS `vai_tro`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vai_tro` (
  `id` int NOT NULL AUTO_INCREMENT,
  `mo_ta` varchar(40) DEFAULT NULL,
  `ten` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_19fdfmoa7i3s54reitajsmonw` (`ten`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vai_tro`
--

LOCK TABLES `vai_tro` WRITE;
/*!40000 ALTER TABLE `vai_tro` DISABLE KEYS */;
INSERT INTO `vai_tro` VALUES (1,'all','Admin'),(2,'giao','Shipper'),(3,'mua ','Customer');
/*!40000 ALTER TABLE `vai_tro` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-02 12:58:16
