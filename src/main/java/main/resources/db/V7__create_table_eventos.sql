CREATE TABLE eventos (
  id int(11) NOT NULL AUTO_INCREMENT,
  peso_promedio double(11,2) DEFAULT NULL,
  fecha date NOT NULL,
  id_animales int(11) NOT NULL,
  id_corrales int(11) NOT NULL,
  id_vacunas int(11) DEFAULT NULL,
  id_racion int(11) DEFAULT NULL,
  estado varchar(100) NOT NULL,
  PRIMARY KEY (id),
  KEY animal_pesado (id_animales),
  KEY animal_tiene_corral (id_corrales),
  KEY animal_tiene_vacuna (id_vacunas),
  KEY animal_tiene_racion (id_racion),
  CONSTRAINT animal_pesado FOREIGN KEY (id_animales) REFERENCES animales (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT animal_tiene_corral FOREIGN KEY (id_corrales) REFERENCES corrales (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT animal_tiene_racion FOREIGN KEY (id_racion) REFERENCES raciones (Id) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT animal_tiene_vacuna FOREIGN KEY (id_vacunas) REFERENCES vacunas (id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;
