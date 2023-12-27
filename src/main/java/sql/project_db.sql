CREATE DATABASE  IF NOT EXISTS `projectdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `projectdb`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: projectdb
-- ------------------------------------------------------
-- Server version	8.0.35

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
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `employeeId` int NOT NULL,
  `employeeName` varchar(45) NOT NULL,
  PRIMARY KEY (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'Eric'),(101,'Amy'),(102,'Freda'),(103,'Lisa'),(201,'James'),(202,'Leo'),(203,'Menu'),(301,'Chad'),(302,'Ariel'),(303,'Tim'),(401,'Dan'),(402,'Adam'),(403,'Tom'),(501,'Camel'),(502,'Allen'),(503,'Paul');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issue`
--

DROP TABLE IF EXISTS `issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issue` (
  `projectId` varchar(45) NOT NULL,
  `issueId` int NOT NULL AUTO_INCREMENT,
  `issueClass` varchar(45) NOT NULL,
  `issueName` varchar(45) NOT NULL,
  `issueContent` varchar(255) NOT NULL,
  `issueFileId` varchar(45) NOT NULL,
  `issueStatus` int NOT NULL,
  `issueDateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`issueId`,`issueFileId`),
  KEY `fk_issue_project_id_idx` (`projectId`),
  KEY `fk_issue_issue_class_idx` (`issueClass`),
  KEY `fk_issue_issue_file_id_idx` (`issueFileId`),
  CONSTRAINT `fk_issue_issue_class` FOREIGN KEY (`issueClass`) REFERENCES `issuetype` (`issueClass`),
  CONSTRAINT `fk_issue_issue_file_id` FOREIGN KEY (`issueFileId`) REFERENCES `issuefile` (`issueFileId`),
  CONSTRAINT `fk_issue_project_id` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
INSERT INTO `issue` VALUES ('AC23020',1,'D','馬桶壞掉','因投入不當物品，造成堵塞','1',1,'2023-12-07 00:00:00');
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issuefile`
--

DROP TABLE IF EXISTS `issuefile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issuefile` (
  `issueFileId` varchar(45) NOT NULL,
  `issueFileName` varchar(45) NOT NULL,
  PRIMARY KEY (`issueFileId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issuefile`
--

LOCK TABLES `issuefile` WRITE;
/*!40000 ALTER TABLE `issuefile` DISABLE KEYS */;
INSERT INTO `issuefile` VALUES ('1','馬桶.jpg');
/*!40000 ALTER TABLE `issuefile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issuetype`
--

DROP TABLE IF EXISTS `issuetype`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issuetype` (
  `issueClass` varchar(45) NOT NULL,
  `issueClassName` varchar(45) NOT NULL,
  PRIMARY KEY (`issueClass`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issuetype`
--

LOCK TABLES `issuetype` WRITE;
/*!40000 ALTER TABLE `issuetype` DISABLE KEYS */;
INSERT INTO `issuetype` VALUES ('A','稽核缺失'),('B','進度DELAY'),('C','工安事件'),('D','事務維修');
/*!40000 ALTER TABLE `issuetype` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `project` (
  `projectId` varchar(45) NOT NULL,
  `projectName` varchar(45) NOT NULL,
  `projectContent` varchar(255) NOT NULL,
  `projectStartDate` date NOT NULL,
  `projectEndDate` date NOT NULL,
  `projectOwner` varchar(45) NOT NULL,
  PRIMARY KEY (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES ('AC23020','SleepingBeauty','TO+ Boiler system','2023-12-01','2024-06-01','Leo');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projectmember`
--

DROP TABLE IF EXISTS `projectmember`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `projectmember` (
  `projectId` varchar(45) NOT NULL,
  `employeeId` int NOT NULL,
  PRIMARY KEY (`projectId`,`employeeId`),
  KEY `fk_pm_employee_id_idx` (`employeeId`),
  CONSTRAINT `fk_pm_employee_id` FOREIGN KEY (`employeeId`) REFERENCES `employee` (`employeeId`),
  CONSTRAINT `fk_pm_project_id` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projectmember`
--

LOCK TABLES `projectmember` WRITE;
/*!40000 ALTER TABLE `projectmember` DISABLE KEYS */;
INSERT INTO `projectmember` VALUES ('AC23020',103),('AC23020',201),('AC23020',202),('AC23020',203),('AC23020',301),('AC23020',401),('AC23020',402),('AC23020',501),('AC23020',502);
/*!40000 ALTER TABLE `projectmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `scheduleId` varchar(45) NOT NULL,
  `purchaseStartDate` date NOT NULL,
  `purchaseEndDate` date NOT NULL,
  `executionStartDate` date NOT NULL,
  `executionEndDate` date NOT NULL,
  `checkandacceptStartDate` date NOT NULL,
  `checkandacceptEndDate` date NOT NULL,
  `paymentStartDate` date NOT NULL,
  `paymentEndDate` date NOT NULL,
  `projectId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`scheduleId`),
  KEY `fk_schedule_project_id_idx` (`projectId`),
  CONSTRAINT `fk_schedule_project_id` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `schedule`
--

LOCK TABLES `schedule` WRITE;
/*!40000 ALTER TABLE `schedule` DISABLE KEYS */;
INSERT INTO `schedule` VALUES ('1','2023-12-01','2024-01-01','2024-01-02','2024-03-01','2024-03-02','2024-05-01','2024-05-02','2024-06-01','AC23020');
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-12-27 14:40:22
