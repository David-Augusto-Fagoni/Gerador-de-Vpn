CREATE DATABASE gerenciadorVpn
USE gerenciadorVpn

DELIMITER $$

CREATE FUNCTION fn_login(usuario VARCHAR(80), senha VARCHAR(20))
RETURNS VARCHAR(80)
DETERMINISTIC
BEGIN
    DECLARE novoUsu VARCHAR(80);

    SELECT f.permissao INTO novoUsu
    FROM login l
    JOIN funcionario f ON l.funcionario_email = f.email
    WHERE l.usuario = usuario
      AND l.senha = senha
    LIMIT 1;

    RETURN novoUsu;
END$$

DELIMITER ;

DELIMITER $$

CREATE FUNCTION fn_procUsuario(nome VARCHAR(80))
RETURNS VARCHAR(100)
DETERMINISTIC
BEGIN
    DECLARE email VARCHAR(100);

    SELECT l.funcionario_email INTO email
    FROM login l
    WHERE l.usuario = nome
    LIMIT 1;

    RETURN email;
END$$

DELIMITER ;


DELIMITER //

CREATE PROCEDURE sp_del_token(IN email VARCHAR(255))
BEGIN
    IF EXISTS (
        SELECT 1 FROM token_redefinicao WHERE login_email = email
    ) THEN
        DELETE FROM token_redefinicao WHERE login_email = email;
    END IF;
END //

DELIMITER ;


INSERT INTO funcionario (email, nome, permissao, ativo)
VALUES ('david.fagoni@gmail.com@empresa.com.br', 'DAVEWQEW', 'Administrador', 1);
INSERT INTO login (funcionario_email, usuario, senha)
VALUES ('david.fagoni@gmail.com@empresa.com.br', 'Dave', '123456');
SELECT * FROM token_redefinicao

DELIMITER $$

CREATE PROCEDURE sp_procVpn(
    IN p_id VARCHAR(80),
    IN p_email VARCHAR(100)
)
BEGIN
    SELECT 
        v.dt_criacao,
        v.dt_validade,
        v.id,
        v.funcionarioemail
    FROM vpn v
    WHERE v.id LIKE CONCAT('%', p_id, '%')
      AND v.funcionarioemail = p_email;
END $$

DELIMITER ;

DELIMITER //
SELECT * FROM (CALL sp_procVpn('P', 'david.fagoni@gmail.com@empresa.com.br'))
DELIMITER ;
SELECT * FROM ip_bloqueado
DELETE FROM ip_bloqueado WHERE ip = '0:0:0:0:0:0:0:1';
