CREATE DATABASE IF NOT EXISTS `filedatabase` CHARACTER SET utf8 COLLATE utf8_general_ci;
CREATE USER 'file_user'@'%' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON `filedatabase`.* TO `file_user`@`%`;
FLUSH PRIVILEGES;