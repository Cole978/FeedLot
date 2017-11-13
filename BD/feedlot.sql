# Host: localhost  (Version 5.5.20)
# Date: 2017-10-09 12:51:15
# Generator: MySQL-Front 6.0  (Build 2.20)


#
# Structure for table "categorias"
#

CREATE TABLE `categorias` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "categorias"
#


#
# Structure for table "corrales"
#

CREATE TABLE `corrales` (
  `Id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL DEFAULT '',
  `estapa` int(11) unsigned NOT NULL DEFAULT '0',
  `cantidad` int(11) unsigned NOT NULL DEFAULT '0',
  `mayor_peso` double(8,2) unsigned NOT NULL DEFAULT '0.00',
  `menor_peso` double(8,2) unsigned NOT NULL DEFAULT '0.00',
  `medio_peso` double(8,2) unsigned NOT NULL DEFAULT '0.00',
  `fecha` varchar(10) NOT NULL DEFAULT '00/00/0000',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "corrales"
#


#
# Structure for table "proveedores"
#

CREATE TABLE `proveedores` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) NOT NULL DEFAULT '',
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#
# Data for table "proveedores"
#

