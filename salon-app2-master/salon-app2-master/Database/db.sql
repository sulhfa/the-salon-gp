CREATE DATABASE `salondb` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `salondb`;

CREATE TABLE `securityquestions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `usertypes` (
  `id` int(11) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `usertypes` VALUES
(1,'admin'),
(2,'owner'),
(3,'client');

INSERT INTO `salondb`.`securityquestions` VALUES
(1,"What is your Birthdate?"),
(2,"What is Your old Phone Number"),
(3,"What is your Pet Name?");

CREATE TABLE `users` (
  `userId` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(512) DEFAULT NULL,
  `fullname` varchar(45) DEFAULT NULL,
  `userType` int(11) DEFAULT NULL,
  `securityQuestionId` int(11) DEFAULT NULL,
  `securityAnswer` varchar(512) DEFAULT NULL,
  `status` int(11) DEFAULT '0',
  `createdAt` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `Address` varchar(45) DEFAULT NULL,
  `Phone` varchar(45) DEFAULT NULL,
  `Location` varchar(25) DEFAULT NULL,
  `Notes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`userId`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  KEY `fk01_idx` (`userType`),
  KEY `fk02_idx` (`securityQuestionId`),
  CONSTRAINT `fk01` FOREIGN KEY (`userType`) REFERENCES `usertypes` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk02` FOREIGN KEY (`securityQuestionId`) REFERENCES `securityquestions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

CREATE TABLE `customers` (
  `custId` int(11) NOT NULL,
  PRIMARY KEY (`custId`),
  UNIQUE KEY `id_UNIQUE` (`custId`),
  CONSTRAINT `fk11` FOREIGN KEY (`custId`) REFERENCES `users` (`userId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `owners` (
  `ownerId` int(11) NOT NULL,
  `salonName` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ownerId`),
  CONSTRAINT `fk10` FOREIGN KEY (`ownerId`) REFERENCES `users` (`userId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `custId` int(11) DEFAULT NULL,
  `orderdate` datetime DEFAULT NULL,
  `orderstatus` int(11) DEFAULT '0',
  `paymentType` int(11) DEFAULT NULL,
  `total` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk2_idx` (`custId`),
  CONSTRAINT `fk2` FOREIGN KEY (`custId`) REFERENCES `customers` (`custId`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;


CREATE TABLE `services` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ownerId` int(11) DEFAULT NULL,
  `title` varchar(45) DEFAULT NULL,
  `details` varchar(100) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT '0.00',
  `status` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `fk1_idx` (`ownerId`),
  CONSTRAINT `fk1` FOREIGN KEY (`ownerId`) REFERENCES `owners` (`ownerId`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;


CREATE TABLE `servicedetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serviceId` int(11) DEFAULT NULL,
  `pictureName` varchar(45) DEFAULT NULL,
  `picturePath` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk16_idx` (`serviceId`),
  CONSTRAINT `fk16` FOREIGN KEY (`serviceId`) REFERENCES `services` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `reviewers` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `serviceId` int(11) DEFAULT NULL,
  `custId` int(11) DEFAULT NULL,
  `orderId` int(11) DEFAULT NULL,
  `rate` int(11) DEFAULT NULL,
  `comments` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk13_idx` (`custId`),
  KEY `fk14_idx` (`orderId`),
  KEY `fk15_idx` (`serviceId`),
  CONSTRAINT `fk13` FOREIGN KEY (`custId`) REFERENCES `customers` (`custId`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk14` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk15` FOREIGN KEY (`serviceId`) REFERENCES `services` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `orderdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  `servicesId` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk3_idx` (`orderId`),
  KEY `fk4_idx` (`servicesId`),
  CONSTRAINT `fk12` FOREIGN KEY (`servicesId`) REFERENCES `services` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk3` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

CREATE TABLE `notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `orderId` int(11) DEFAULT NULL,
  `eventDate` date DEFAULT NULL,
  `eventStatus` int(11) DEFAULT NULL,
  `eventDetail` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk6_idx` (`orderId`),
  CONSTRAINT `fk6` FOREIGN KEY (`orderId`) REFERENCES `orders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
