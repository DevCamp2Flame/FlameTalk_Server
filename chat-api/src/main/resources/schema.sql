-- -----------------------------------------------------
-- Table `flametalk_db`.`chatroom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `flametalk_db`.`chatroom` (
    `id` VARCHAR(36) NOT NULL,
    `host_id` VARCHAR(20) NULL,
    `count` INT NOT NULL,
    `is_open` TINYINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `host_id_idx` (`host_id` ASC) VISIBLE,
    CONSTRAINT `host_id`
    FOREIGN KEY (`host_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `flametalk_db`.`user_chatroom`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `flametalk_db`.`user_chatroom` (
    `id` VARCHAR(36) NOT NULL,
    `chatroom_id` VARCHAR(36) NOT NULL,
    `user_id` VARCHAR(20) NOT NULL,
    `title` VARCHAR(50) NOT NULL,
    `last_read_message_id` VARCHAR(45) NULL,
    `image_id` BIGINT NULL,
    `input_lock` TINYINT NOT NULL,
    `created_at` DATETIME NOT NULL,
    `updated_at` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
    INDEX `user_chatroom_chatroom_id_idx` (`chatroom_id` ASC) VISIBLE,
    CONSTRAINT `user_chatroom_user_id`
    FOREIGN KEY (`user_id`)
    REFERENCES `flametalk_db`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    CONSTRAINT `user_chatroom_chatroom_id`
    FOREIGN KEY (`chatroom_id`)
    REFERENCES `flametalk_db`.`chatroom` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION
)
    ENGINE = InnoDB;