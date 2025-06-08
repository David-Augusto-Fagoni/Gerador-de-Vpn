Create Database gerenciadorVpn
USE gerenciadorVpn

CREATE FUNCTION fn_login(@usuario VARCHAR (80),@senha VARCHAR (20))
RETURNS @tabela TABLE (
	usuario				VARCHAR(80)
) AS BEGIN
	DECLARE @novoUsu VARCHAR(80),
			@novoSen VARCHAR(30)
	SELECT  @novoUsu = l.usuario, @novoSen = l.senha
	FROM login l
	WHERE l.senha = @senha
	AND l.usuario = @usuario
	INSERT INTO @tabela VALUES (@novoUsu)
	RETURN
END

CREATE FUNCTION fn_procNome (@nome VARCHAR (80))
RETURNS @tabela TABLE (
	ativo		bit,
	email		VARCHAR(100),
	nome		VARCHAR(255),
	permissao	VARCHAR(255)
) AS BEGIN
	INSERT INTO @tabela (ativo, email, nome, permissao)
	SELECT f.ativo, f.email, f.nome, f.permissao
	FROM funcionario f
	WHERE f.nome LIKE '%'+@nome+'%'
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


Select *
FROM funcionario f
WHERE f.nome LIKE '%D%'

SELECT * 
FROM login, funcionario

SELECT * FROM fn_procNome('D')

UPDATE login
SET senha = '123456789', usuario = 'Daves'
WHERE funcionario_email = 'Dawvedwd@empresa.com.br'

EXEC fn_procUsuario ('a')

SELECT fn_procUsuario('a')

SELECT dbo.fn_procUsuario('admin')