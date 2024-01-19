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
  `issueId` int NOT NULL AUTO_INCREMENT,
  `projectId` varchar(45) NOT NULL,
  `issueName` varchar(45) NOT NULL,
  `issueClassId` varchar(45) NOT NULL,
  `issueContent` varchar(255) NOT NULL,
  `issueStatus` tinyint NOT NULL DEFAULT '1',
  `issueDateTime` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`issueId`),
  KEY `fk_issue_project_id_idx` (`projectId`),
  KEY `fk_issue_issue_class_idx` (`issueClassId`),
  CONSTRAINT `fk_issue_issue_class_id` FOREIGN KEY (`issueClassId`) REFERENCES `issueclass` (`issueClassId`),
  CONSTRAINT `fk_issue_project_id` FOREIGN KEY (`projectId`) REFERENCES `project` (`projectId`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issue`
--

LOCK TABLES `issue` WRITE;
/*!40000 ALTER TABLE `issue` DISABLE KEYS */;
INSERT INTO `issue` VALUES (1,'AC23020','馬桶壞掉','D','因投入不當物品，造成堵塞',1,'2023-12-07 00:00:00');
/*!40000 ALTER TABLE `issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issueclass`
--

DROP TABLE IF EXISTS `issueclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issueclass` (
  `issueClassId` varchar(45) NOT NULL,
  `issueClassName` varchar(45) NOT NULL,
  PRIMARY KEY (`issueClassId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issueclass`
--

LOCK TABLES `issueclass` WRITE;
/*!40000 ALTER TABLE `issueclass` DISABLE KEYS */;
INSERT INTO `issueclass` VALUES ('A','設計類'),('B','公務類'),('C','環安衛類'),('D','其他');
/*!40000 ALTER TABLE `issueclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `issuefile`
--

DROP TABLE IF EXISTS `issuefile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `issuefile` (
  `issueFileId` int NOT NULL AUTO_INCREMENT,
  `issueId` int NOT NULL,
  `issueFilePath` varchar(255) NOT NULL,
  PRIMARY KEY (`issueFileId`),
  KEY `fk_issue_issue_id_idx` (`issueId`),
  CONSTRAINT `fk_issue_issue_id` FOREIGN KEY (`issueId`) REFERENCES `issue` (`issueId`)
) ENGINE=InnoDB AUTO_INCREMENT=502 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `issuefile`
--

LOCK TABLES `issuefile` WRITE;
/*!40000 ALTER TABLE `issuefile` DISABLE KEYS */;
INSERT INTO `issuefile` VALUES (501,1,'馬桶.jpg');
/*!40000 ALTER TABLE `issuefile` ENABLE KEYS */;
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
INSERT INTO `project` VALUES ('AC23020','SleepingBeauty','TO+ Boiler system','2023-12-01','2024-06-01','103'),('AC23021','littleMI','littleMIlittleMIlittleMI','2024-01-23','2024-07-16','201'),('AC23024','FatFish','dadadadadadad','2023-12-11','2024-03-08','103'),('AC23028','28','28282','2024-01-22','2024-02-10','102');
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
INSERT INTO `projectmember` VALUES ('AC23020',1),('AC23028',102),('AC23024',103),('AC23020',201),('AC23021',201),('AC23028',201),('AC23020',202),('AC23021',202),('AC23021',203),('AC23028',203),('AC23020',301),('AC23021',301),('AC23024',301),('AC23024',302),('AC23028',302),('AC23024',303);
/*!40000 ALTER TABLE `projectmember` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `schedule`
--

DROP TABLE IF EXISTS `schedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `schedule` (
  `scheduleId` int NOT NULL,
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
INSERT INTO `schedule` VALUES (1,'AC23020');
/*!40000 ALTER TABLE `schedule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `task`
--

DROP TABLE IF EXISTS `task`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `task` (
  `taskId` int NOT NULL AUTO_INCREMENT,
  `scheduleId` int NOT NULL,
  `taskName` varchar(255) NOT NULL,
  `taskResource` varchar(255) NOT NULL,
  `taskStartDate` date NOT NULL,
  `taskEndDate` date NOT NULL,
  `taskDependency` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`taskId`),
  KEY `fk_schedule_taskId_idx` (`scheduleId`),
  CONSTRAINT `fk_schedule_taskId` FOREIGN KEY (`scheduleId`) REFERENCES `schedule` (`scheduleId`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `task`
--

LOCK TABLES `task` WRITE;
/*!40000 ALTER TABLE `task` DISABLE KEYS */;
INSERT INTO `task` VALUES (1,1,'專案','專案部','2023-12-01','2024-06-01',NULL),(2,1,'採購','採購部','2023-12-01','2024-01-01',NULL),(3,1,'執行','工程部','2024-01-02','2024-03-01','2'),(4,1,'驗收','專案部','2024-03-02','2024-05-01','3'),(5,1,'請款','採購部','2024-05-02','2024-06-01','4');
/*!40000 ALTER TABLE `task` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-01-19 15:10:27
