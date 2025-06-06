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

SELECT * 
FROM login, funcionario