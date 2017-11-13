CREATE TABLE proveedores (
  Id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(150) NOT NULL DEFAULT '',
  Domicilio varchar(150) DEFAULT NULL,
  Localidad varchar(150) DEFAULT NULL,
  Provincia varchar(150) DEFAULT NULL,
  Celular_1 varchar(150) DEFAULT NULL,
  Celular_2 varchar(150) DEFAULT NULL,
  Mail_1 varchar(150) DEFAULT NULL,
  Mail_2 varchar(150) DEFAULT NULL,
  PRIMARY KEY (Id)
) AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;