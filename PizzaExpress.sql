-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user` (
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(32) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP);


-- -----------------------------------------------------
-- Table `mydb`.`user_1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`user_1` (
  `username` VARCHAR(16) NOT NULL,
  `email` VARCHAR(255) NULL,
  `password` VARCHAR(32) NOT NULL,
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP);


-- -----------------------------------------------------
-- Table `mydb`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`category` (
  `category_id` INT NOT NULL,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`category_id`));


-- -----------------------------------------------------
-- Table `mydb`.`timestamps`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`timestamps` (
  `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` TIMESTAMP NULL);


-- -----------------------------------------------------
-- Table `mydb`.`Roles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Roles` (
  `id_rol` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_rol`),
  UNIQUE INDEX `nombre_UNIQUE` (`nombre` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Sucursales`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Sucursales` (
  `id_sucursal` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_sucursal`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Usuarios`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Usuarios` (
  `id_usuario` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `id_rol` INT NOT NULL,
  `id_sucursal` INT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE,
  INDEX `fk_usuario_rol_idx` (`id_rol` ASC) VISIBLE,
  INDEX `fk_usuario_sucursal_idx` (`id_sucursal` ASC) VISIBLE,
  CONSTRAINT `fk_usuario_rol`
    FOREIGN KEY (`id_rol`)
    REFERENCES `mydb`.`Roles` (`id_rol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_usuario_sucursal`
    FOREIGN KEY (`id_sucursal`)
    REFERENCES `mydb`.`Sucursales` (`id_sucursal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Productos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Productos` (
  `id_producto` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NOT NULL,
  `estado_activo` TINYINT NOT NULL,
  `id_sucursal` INT NOT NULL,
  PRIMARY KEY (`id_producto`),
  INDEX `fk_producto_sucursal_idx` (`id_sucursal` ASC) VISIBLE,
  CONSTRAINT `fk_producto_sucursal`
    FOREIGN KEY (`id_sucursal`)
    REFERENCES `mydb`.`Sucursales` (`id_sucursal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Partidas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Partidas` (
  `id_partida` INT NOT NULL AUTO_INCREMENT,
  `id_usuario` INT NOT NULL,
  `id_sucursal` INT NOT NULL,
  `puntaje_total` INT NOT NULL,
  `nivel_alcanzado` INT NULL,
  `fecha_registro` DATETIME NOT NULL,
  PRIMARY KEY (`id_partida`),
  INDEX `fk_partida_usuario_idx` (`id_usuario` ASC) VISIBLE,
  INDEX `fk_partidas_sucursal_idx` (`id_sucursal` ASC) VISIBLE,
  CONSTRAINT `fk_partida_usuario`
    FOREIGN KEY (`id_usuario`)
    REFERENCES `mydb`.`Usuarios` (`id_usuario`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_partidas_sucursal`
    FOREIGN KEY (`id_sucursal`)
    REFERENCES `mydb`.`Sucursales` (`id_sucursal`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Pedidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pedidos` (
  `id_pedido` INT NOT NULL AUTO_INCREMENT,
  `id_partida` INT NOT NULL,
  `tiempo_limite` INT NOT NULL,
  `estado_actual` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id_pedido`),
  INDEX `fk_pedido_partida_idx` (`id_partida` ASC) VISIBLE,
  CONSTRAINT `fk_pedido_partida`
    FOREIGN KEY (`id_partida`)
    REFERENCES `mydb`.`Partidas` (`id_partida`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Detalle`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Detalle` (
  `id_detalle` INT NOT NULL AUTO_INCREMENT,
  `id_pedido` INT NOT NULL,
  `id_producto` INT NOT NULL,
  PRIMARY KEY (`id_detalle`),
  INDEX `fk_detalle_pedido_idx` (`id_pedido` ASC) VISIBLE,
  INDEX `fk_detalle_producto_idx` (`id_producto` ASC) VISIBLE,
  CONSTRAINT `fk_detalle_pedido`
    FOREIGN KEY (`id_pedido`)
    REFERENCES `mydb`.`Pedidos` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_detalle_producto`
    FOREIGN KEY (`id_producto`)
    REFERENCES `mydb`.`Productos` (`id_producto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Historial`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Historial` (
  `id_historial` INT NOT NULL AUTO_INCREMENT,
  `id_pedido` INT NOT NULL,
  `estado` VARCHAR(45) NOT NULL,
  `fecha_registro` DATETIME NOT NULL,
  PRIMARY KEY (`id_historial`),
  INDEX `fk_historial_pedido_idx` (`id_pedido` ASC) VISIBLE,
  CONSTRAINT `fk_historial_pedido`
    FOREIGN KEY (`id_pedido`)
    REFERENCES `mydb`.`Pedidos` (`id_pedido`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Configuracion_Niveles`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Configuracion_Niveles` (
  `id_nivel` INT NOT NULL,
  `tiempo_base_segundos` INT NOT NULL,
  PRIMARY KEY (`id_nivel`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;