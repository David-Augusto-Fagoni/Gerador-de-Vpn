Create Database gerenciadorVpn
USE gerenciadorVpn

CREATE FUNCTION fn_login(@usuario VARCHAR (80),@senha VARCHAR (20))
RETURNS @tabela TABLE (
	usuario				VARCHAR(80)
) AS BEGIN
	DECLARE @novoUsu VARCHAR(80),
			@novoSen VARCHAR(30)
	SELECT  @novoUsu = f.permissao
	FROM login l, funcionario f
	WHERE l.senha = @senha
	AND l.usuario = @usuario
	AND l.funcionario_email = f.email
	INSERT INTO @tabela VALUES (@novoUsu)
	RETURN
END

CREATE FUNCTION fn_procNome (@nome VARCHAR (80))
RETURNS @tabela TABLE (
	ativo			bit,
	email			VARCHAR(100),
	nome					VARCHAR(255),
	permissao	VARCHAR(255)
) AS BEGIN
	INSERT INTO @tabela (ativo, email, nome, permissao)
	SELECT f.ativo, f.email, f.nome, f.permissao
	FROM funcionario f
	WHERE f.nome LIKE '%'+@nome+'%'
	RETURN
END

CREATE FUNCTION fn_procVpn(@id VARCHAR (80), @email VARCHAR (100))
RETURNS @tabela TABLE (
	dt_criacao			DATE,
	dt_validade			DATE,
	id					VARCHAR(10),
	funcionarioemail	VARCHAR(100)
) AS BEGIN
	INSERT INTO @tabela (dt_criacao, dt_validade, id, funcionarioemail)
	SELECT v.dt_criacao, v.dt_validade, v.id, v.funcionarioemail
	FROM vpn v
	WHERE v.id LIKE '%'+@id+'%'
	AND v.funcionarioemail = @email
	RETURN
END

CREATE FUNCTION fn_procUsuario (@nome VARCHAR(80))
RETURNS VARCHAR(100)
AS
BEGIN
    DECLARE @usuario VARCHAR(100)
    SELECT @usuario = l.usuario
    FROM login l
    WHERE l.usuario = @nome

    RETURN @usuario
END

CREATE PROCEDURE sp_del_token(@email VARCHAR(255)) AS
	IF EXISTS(SELECT * FROM token_redefinicao t WHERE t.login_email = @email) BEGIN
		DELETE token_redefinicao
		WHERE login_email = @email
	END

Select *
FROM funcionario f
WHERE f.nome LIKE '%D%'

SELECT * 
FROM login, funcionario

SELECT * FROM fn_procNome('D')

INSERT INTO funcionario
VALUES(1,'david.fagoni@gmail.com@empresa.com.br','DAVEWQEW','Administrador')
INSERT INTO login
VALUES(123456,'Dave','david.fagoni@gmail.com@empresa.com.br')

UPDATE login
SET senha = '123456789', usuario = 'Daves'
WHERE funcionario_email = 'DSADASDASDASD@empresa.com.br'

UPDATE funcionario
SET permissao = 'Administrador'
WHERE email= 'DSADASDASDASD@empresa.com.br'

EXEC fn_procUsuario ('a')

SELECT fn_procUsuario('a')

SELECT dbo.fn_procUsuario('admin')

	SELECT  *




	FROM login l, funcionario f
	WHERE l.senha = '123456789'
	AND l.usuario = 'Daves'
	AND l.funcionario_email = f.email

SELECT *
FROM token_redefinicao

Ameba486924!