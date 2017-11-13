CREATE TABLE corrales (
  Id int(11) NOT NULL AUTO_INCREMENT,
  nombre varchar(255) NOT NULL DEFAULT '',
  etapa int(11) unsigned NOT NULL DEFAULT '0',
  capacidad int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (Id)
) AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;