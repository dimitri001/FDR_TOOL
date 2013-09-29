package libreria;

public class TablasBBDD {

	
	private String fabricante = 
		"CREATE  TABLE IF NOT EXISTS  `Fabricante` ("+
		"`idFabricante` INT NOT NULL AUTO_INCREMENT ,"+
		"`nombre` VARCHAR(45) NULL ,"+
		"`Web` VARCHAR(45) NULL ,"+
		"PRIMARY KEY (`idFabricante`) )"+
		"ENGINE = InnoDB;";
	
	private String modelo = 
		"CREATE  TABLE IF NOT EXISTS  `Modelo` ("+
		"		  `idModelo` INT NOT NULL AUTO_INCREMENT ,"+
		"		  `idFabricante` INT NOT NULL ,"+
		"		  `nombre` VARCHAR(45) NULL ,"+
		"		  `descripcion` VARCHAR(350) NULL ,"+
		"		  `fecha_fabricacion` DATE NULL ,"+
		"		  `anyo_lanzamiento` VARCHAR(45) NULL ,"+
		"		  PRIMARY KEY (`idModelo`, `idFabricante`) ,"+
		"		  INDEX `fk_Modelo_Fabricante` (`idFabricante` ASC) ,"+
		"		  CONSTRAINT `fk_Modelo_Fabricante`"+
		"		    FOREIGN KEY (`idFabricante` )"+
		"		    REFERENCES  `Fabricante` (`idFabricante` )"+
		"		    ON DELETE NO ACTION"+
		"		    ON UPDATE NO ACTION)"+
		"		ENGINE = InnoDB;";
	
	private String serie =
		
		"CREATE  TABLE IF NOT EXISTS  `Serie` ("+
		"		  `idSerie` INT NOT NULL AUTO_INCREMENT ,"+
		"		  `idModelo` INT NOT NULL ,"+
		"		  `idFabricante` INT NOT NULL ,"+
		"		  `numero_serie` VARCHAR(45) NULL ,"+
		"		  `descripcion` VARCHAR(350) NULL ,"+
		"		  `fecha_fabricacion` DATE NULL ,"+
		"		  `anyo_lanzamiento` VARCHAR(45) NULL ,"+
		"		  PRIMARY KEY (`idSerie`, `idModelo`, `idFabricante`) ,"+
		"		  INDEX `fk_Serie_Modelo1` (`idModelo` ASC, `idFabricante` ASC) ,"+
		"		  CONSTRAINT `fk_Serie_Modelo1`"+
		"		    FOREIGN KEY (`idModelo` , `idFabricante` )"+
		"		    REFERENCES  `Modelo` (`idModelo` , `idFabricante` )"+
		"		    ON DELETE NO ACTION"+
		"		    ON UPDATE NO ACTION)"+
		"		ENGINE = InnoDB;";

	private String  estructuraFrame =
		"CREATE  TABLE IF NOT EXISTS  `Estructura_Frame` ("+
		"		  `idEstructura_Frame` INT NOT NULL AUTO_INCREMENT ,"+
		"		  `idSerie` INT NOT NULL ,"+
		"		  `idModelo` INT NOT NULL ,"+
		"		  `idFabricante` INT NOT NULL ,"+
		"		  `N_subframes` INT NULL ,"+
		"		  `tiempo_subframe` DECIMAL (17, 3) NULL ,"+
		"		  `wps` INT NULL ,"+
		"		  `bits_pw` INT NULL ,"+
		"		  `descripcion` VARCHAR(350) NULL ,"+
		"		  PRIMARY KEY (`idEstructura_Frame`, `idSerie`, `idModelo`, `idFabricante`) ,"+
		"		  INDEX `fk_Estructura_Frame_Serie1` (`idSerie` ASC, `idModelo` ASC, `idFabricante` ASC) ,"+
		"		  CONSTRAINT `fk_Estructura_Frame_Serie1`"+
		"		    FOREIGN KEY (`idSerie` , `idModelo` , `idFabricante` )"+
		"		    REFERENCES  `Serie` (`idSerie` , `idModelo` , `idFabricante` )"+
		"		    ON DELETE NO ACTION"+
		"		    ON UPDATE NO ACTION)"+
		"		ENGINE = InnoDB;";
	
	
	private String sistemaEspecifico =
		"CREATE  TABLE IF NOT EXISTS  `Sistema_Especifico` ("+
		"		  `idSistema_Especifico` INT NOT NULL AUTO_INCREMENT ,"+
		"		  `idSerie` INT NOT NULL ,"+
		"		  `idModelo` INT NOT NULL ,"+
		"		  `idFabricante` INT NOT NULL ,"+
		"		  `nombre` VARCHAR(45) NULL ,"+
		"		  `descripcion` VARCHAR(350) NULL ,"+
		"		  PRIMARY KEY (`idSistema_Especifico`, `idSerie`, `idModelo`, `idFabricante`) ,"+
		"		  INDEX `fk_Sistema_Especifico_Serie1` (`idSerie` ASC, `idModelo` ASC, `idFabricante` ASC) ,"+
		"		  CONSTRAINT `fk_Sistema_Especifico_Serie1`"+
		"		    FOREIGN KEY (`idSerie` , `idModelo` , `idFabricante` )"+
		"		    REFERENCES  `Serie` (`idSerie` , `idModelo` , `idFabricante` )"+
		"		    ON DELETE NO ACTION"+
		"		    ON UPDATE NO ACTION)"+
		"		ENGINE = InnoDB;";

	
	private String manual =
		"CREATE  TABLE IF NOT EXISTS  `Manual` ("+
		"		  `idManual` INT NOT NULL AUTO_INCREMENT ,"+
		"		  `idSerie` INT NOT NULL ,"+
		"		  `idModelo` INT NOT NULL ,"+
		"		  `idFabricante` INT NOT NULL ,"+
		"		  `descripcion` VARCHAR(350) NULL ,"+
		"		  `ruta_fichero_pdf` VARCHAR(45) NULL ,"+
		"		  PRIMARY KEY (`idManual`, `idSerie`, `idModelo`, `idFabricante`) ,"+
		"		  INDEX `fk_Manual_Serie1` (`idSerie` ASC, `idModelo` ASC, `idFabricante` ASC) ,"+
		"		  CONSTRAINT `fk_Manual_Serie1`"+
		"		    FOREIGN KEY (`idSerie` , `idModelo` , `idFabricante` )"+
		"		    REFERENCES  `Serie` (`idSerie` , `idModelo` , `idFabricante` )"+
		"		    ON DELETE NO ACTION"+
		"		    ON UPDATE NO ACTION)"+
		"		ENGINE = InnoDB;";

	private String elementosSistemaEspecifico =
	
		"CREATE  TABLE IF NOT EXISTS  `Elementos_Sistema_Especifico` ("+
		"	  	`idElementos_Sistema_Especifico` INT NOT NULL AUTO_INCREMENT ,"+
		"	 	 `idSistema_Especifico` INT NOT NULL ,"+
		"		  `idSerie` INT NOT NULL ,"+
		"		  `idModelo` INT NOT NULL ,"+
		"		  `idFabricante` INT NOT NULL ,"+
		"		  `nombre_elemento` VARCHAR(45) NULL ,"+
		"		  `interesting` TINYINT(1) NULL ,"+
		"		  `subframes` VARCHAR(45) NULL ,"+
		"		  `palabras` VARCHAR(45) NULL ,"+
		"		  `bits` VARCHAR(45) NULL ,"+
		"		  `n_atributos` INT NULL ,"+
		"		  PRIMARY KEY (`idElementos_Sistema_Especifico`, `idSistema_Especifico`, `idSerie`, `idModelo`, `idFabricante`) ,"+
		"		  INDEX `fk_Elementos_Sistema_Especifico_Sistema_Especifico1` (`idSistema_Especifico` ASC, `idSerie` ASC, `idModelo` ASC, `idFabricante` ASC) ,"+
		"		  CONSTRAINT `fk_Elementos_Sistema_Especifico_Sistema_Especifico1`"+
		"		    FOREIGN KEY (`idSistema_Especifico` , `idSerie` , `idModelo` , `idFabricante` )"+
		"		    REFERENCES  `Sistema_Especifico` (`idSistema_Especifico` , `idSerie` , `idModelo` , `idFabricante` )"+
		"		    ON DELETE NO ACTION"+
		"		    ON UPDATE NO ACTION)"+
		"		ENGINE = InnoDB;";
	
	
	private String estructuraAtributosElemento =
		"CREATE  TABLE IF NOT EXISTS  `Estructura_Atributos_de_Elemento` ("+
		"		  `idEstructura_Atributos_de_Elemento` INT NOT NULL AUTO_INCREMENT ,"+
		"		  `idElementos_Sistema_Especifico` INT NOT NULL ,"+
		"		  `idSistema_Especifico` INT NOT NULL ,"+
		"		  `idSerie` INT NOT NULL ,"+
		"		  `idModelo` INT NOT NULL ,"+
		"		  `idFabricante` INT NOT NULL ,"+
		"		  `nombre_atributo` VARCHAR(45) NULL ,"+
		"		  `tipo_atributo` VARCHAR(45) NULL ,"+
		"		  `visible` TINYINT(1) NULL ,"+
		"		  PRIMARY KEY (`idEstructura_Atributos_de_Elemento`, `idElementos_Sistema_Especifico`, `idSistema_Especifico`, `idSerie`, `idModelo`, `idFabricante`) ,"+
		"		  INDEX `fk_Estructura_Atributos_de_Elemento_Elementos_Sistema_Especif1` (`idElementos_Sistema_Especifico` ASC, `idSistema_Especifico` ASC, `idSerie` ASC, `idModelo` ASC, `idFabricante` ASC) ,"+
		"		  CONSTRAINT `fk_Estructura_Atributos_de_Elemento_Elementos_Sistema_Especif1`"+
		"		    FOREIGN KEY (`idElementos_Sistema_Especifico` , `idSistema_Especifico` , `idSerie` , `idModelo` , `idFabricante` )"+
		"		    REFERENCES  `Elementos_Sistema_Especifico` (`idElementos_Sistema_Especifico` , `idSistema_Especifico` , `idSerie` , `idModelo` , `idFabricante` )"+
		"		    ON DELETE NO ACTION"+
		"		    ON UPDATE NO ACTION)"+
		"		ENGINE = InnoDB;";
	
	public String getFabricante()
	{
		return fabricante;
	}
	
	public String getModelo()
	{
		return modelo;
	}

	public String getSerie()
	{
		return serie;
	}

	public String getEstructuraFrame()
	{
		return estructuraFrame;
	}
	
	public String getSistemaEspecifico()
	{
		return sistemaEspecifico;
	}
	
	
	public String getManual()
	{
		return manual;
	}
	
	public String getElementosSistemaEspecifico()
	{
		return elementosSistemaEspecifico;
	}
	
	public String getEstructuraAtributosElemento()
	{
		return  estructuraAtributosElemento;
	}
	
	
}
