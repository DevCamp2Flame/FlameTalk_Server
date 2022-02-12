SET @OLD_UNIQUE_CHECKS = @@UNIQUE_CHECKS, UNIQUE_CHECKS = 0;
SET @OLD_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS = 0;
SET @OLD_SQL_MODE = @@SQL_MODE, SQL_MODE =
        'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

CREATE SCHEMA IF NOT EXISTS `flametalk_db` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
USE `flametalk_db`;

-- -----------------------------------------------------
-- Table `flametalk_db`.`user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`user`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`user`
(
    `id`           VARCHAR(20)  NOT NULL,
    `email`        VARCHAR(320) NOT NULL,
    `password`     VARCHAR(60)  NULL,
    `nickname`     VARCHAR(20)  NOT NULL,
    `phone_number` VARCHAR(13)  NOT NULL,
    `birthday`     VARCHAR(10)  NOT NULL,
    `social`       TINYINT      NOT NULL,
    `status`       TINYINT      NOT NULL,
    `region`       VARCHAR(2)   NOT NULL,
    `language`     VARCHAR(3)   NOT NULL,
    `created_at`   DATETIME     NOT NULL,
    `updated_at`   DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`device`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`device`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`device`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT,
    `user_id`            VARCHAR(20)  NOT NULL,
    `device_id`          VARCHAR(16)  NOT NULL,
    `token`              VARCHAR(163) NULL,
    `cur_max_message_id` VARCHAR(45)  NULL,
    `created_at`         DATETIME     NOT NULL,
    `updated_at`         DATETIME     NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `device_user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `flametalk_db`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`profile`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `flametalk_db`.`profile`;

CREATE TABLE IF NOT EXISTS `flametalk_db`.`profile`
(
    `id`           BIGINT      NOT NULL AUTO_INCREMENT,
    `user_id`      VARCHAR(20) NOT NULL,
    `image_url`    BLOB        NULL,
    `bg_image_url` BLOB        NULL,
    `sticker`      JSON        NULL,
    `description`  VARCHAR(60) NULL,
    `is_default`   TINYINT     NOT NULL,
    `created_at`   DATETIME    NOT NULL,
    `updated_at`   DATETIME    NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `profile_user_id`
        FOREIGN KEY (`user_id`)
            REFERENCES `flametalk_db`.`user` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB;

SET SQL_MODE = @OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS = @OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS = @OLD_UNIQUE_CHECKS;