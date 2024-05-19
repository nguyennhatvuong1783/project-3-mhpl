-- -------------------------------------------------------------
-- TablePlus 5.9.0(538)
--
-- https://tableplus.com/
--
-- Database: qlthanhvien
-- Generation Time: 2024-05-19 15:55:55.0010
-- -------------------------------------------------------------


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


DROP TABLE IF EXISTS `thanhvien`;
CREATE TABLE `thanhvien` (
  `MaTV` int NOT NULL,
  `Password` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `HoTen` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `Khoa` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Nganh` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `Email` varchar(25) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `SDT` varchar(15) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_admin` bit(1) DEFAULT NULL,
  `ho_ten` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`MaTV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `thietbi`;
CREATE TABLE `thietbi` (
  `MaTB` int NOT NULL,
  `TenTB` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `MoTaTB` text COLLATE utf8mb4_general_ci,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`MaTB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `thongtinsd`;
CREATE TABLE `thongtinsd` (
  `MaTT` int NOT NULL,
  `MaTV` int NOT NULL,
  `MaTB` int DEFAULT NULL,
  `TGDatcho` datetime DEFAULT NULL,
  `TGVao` datetime DEFAULT NULL,
  `TGMuon` datetime DEFAULT NULL,
  `TGTra` datetime DEFAULT NULL,
  PRIMARY KEY (`MaTT`),
  KEY `MaTV` (`MaTV`,`MaTB`),
  KEY `MaTB` (`MaTB`),
  CONSTRAINT `thongtinsd_ibfk_1` FOREIGN KEY (`MaTV`) REFERENCES `thanhvien` (`MaTV`),
  CONSTRAINT `thongtinsd_ibfk_2` FOREIGN KEY (`MaTB`) REFERENCES `thietbi` (`MaTB`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

DROP TABLE IF EXISTS `xuly`;
CREATE TABLE `xuly` (
  `MaXL` int NOT NULL AUTO_INCREMENT,
  `MaTV` int NOT NULL,
  `HinhThucXL` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `SoTien` int DEFAULT NULL,
  `NgayXL` datetime DEFAULT NULL,
  `TrangThaiXL` int DEFAULT '0',
  PRIMARY KEY (`MaXL`),
  KEY `MaTV` (`MaTV`),
  KEY `MaTV_2` (`MaTV`),
  CONSTRAINT `xuly_ibfk_1` FOREIGN KEY (`MaTV`) REFERENCES `thanhvien` (`MaTV`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `thanhvien` (`MaTV`, `Password`, `HoTen`, `Khoa`, `Nganh`, `Email`, `SDT`, `is_admin`, `ho_ten`) VALUES
(1, '1', 'Amin', NULL, NULL, NULL, NULL, b'1', NULL),
(1120150184, '12345', 'Trần Thị Nữ', 'GDTH', 'GDTH', 'tranthinu@gmail.comm', '0123123123', b'0', NULL),
(1121430037, '1121430037', 'Nguyễn Thị Phương Diễm', 'Luật', 'LHC', '1121430037@gmail.com', '0911214300', b'0', NULL),
(1121430051, '1121430051', 'Nguyễn Thị Tuyết Dung', 'Luật', 'LHC', '1121430051@gmail.com', '0911214300', b'0', NULL),
(1121530087, '12345', 'Trần Thiếu Nam', 'TLH', 'QLGD', 'tranthieunam@gmail.com', '1111111112', b'0', NULL),
(1122130055, '1122130055', 'Phan Văn Anh', 'Ngoại Ngữ', 'Anh', '1122130055@gmail.com', '0911221300', b'0', NULL),
(1122380173, '1122380173', 'Hoàng Bích Hà', 'Ngoại Ngữ', 'NNA', '1122380173@gmail.com', '0911223801', b'0', NULL),
(1122520013, '1122520013', 'Lê Nguyễn Phương Linh', 'ĐT-VT', 'CNKTDTTT', '1122520013@gmail.com', '0911225200', b'0', NULL),
(1123330257, '12345', 'Ngô Tuyết Nhi', 'QTKD', 'QTKD', 'ngotuyetnhi@gmail.com', '1111111113', b'0', NULL),
(1123380190, '1123380190', 'Trần Bùi Thảo My', 'Ngoại Ngữ', 'NNA', '1123380190@gmail.com', '0911233801', b'0', NULL),
(1123380205, '1123380205', 'Mai Thị Minh Huyền', 'Ngoại Ngữ', 'NNA', '1123380205@gmail.com', '0911233802', b'0', NULL),
(1123380365, '1123380365', 'Nguyễn Ngọc Thảo Vy', 'Ngoại Ngữ', 'NNA', '1123380365@gmail.com', '0911233803', b'0', NULL),
(1123430154, '1123430154', 'Đinh Hoàng Linh Chi', 'Luật', 'LHC', '1123430154@gmail.com', '0911234301', b'0', NULL),
(1231231231, 'DW0HOD', 'Tuan', 'CNTT', 'CNTT', 'khactuans.tran@gmail.com', '0123123123', b'0', NULL),
(2147483647, '12345', 'Nguyễn Văn Nam', 'CNTT', 'HTTT', 'nguyenvannam@gmail.com', '123456789', b'0', NULL);

INSERT INTO `thietbi` (`MaTB`, `TenTB`, `MoTaTB`, `status`) VALUES
(1000001, 'Micro', 'Micro không dây MS2023', NULL),
(1000002, 'Micro', 'Micro không dây MS2024', NULL),
(1000003, 'Bảng điện tử', 'Bản điện tử trình chiếu', NULL);

INSERT INTO `thongtinsd` (`MaTT`, `MaTV`, `MaTB`, `TGDatcho`, `TGVao`, `TGMuon`, `TGTra`) VALUES
(1, 1120150184, NULL, '2024-01-01 21:57:51', '2024-01-05 09:00:00', NULL, NULL),
(2, 1123330257, 1000001, NULL, NULL, '2024-02-12 10:00:32', '2024-02-12 14:00:00');

INSERT INTO `xuly` (`MaXL`, `MaTV`, `HinhThucXL`, `SoTien`, `NgayXL`, `TrangThaiXL`) VALUES
(1, 1121530087, 'Khóa thẻ 1 tháng', NULL, '2024-05-19 14:39:41', 1),
(2, 2147483647, 'Khóa thẻ 2 tháng', NULL, '2024-05-19 14:33:33', 0),
(3, 1123330257, 'Bồi thường mất tài sản', 300000, '2024-05-19 14:33:36', 0),
(6, 1121530087, 'Test', 123, '2024-05-19 14:27:49', 0),
(7, 1122130055, '', NULL, '2024-05-19 14:27:52', 0);



/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;