<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>

<head>
    <title>Redefinir Senha</title>
</head>
<body>
    <div style="padding: 10%;">
    	<div class="form-container m-auto border p-2" style="width: 50%; height: 50%; min-width: 40%;">
    		<h2>Redefinir Senha</h2>
    	</div>	
		<div class="form-container m-auto border p-2" style="width: 50%; height: 50%; min-width: 40%;">
			<form action="redefinirSenha" method="post">
			        <input type="hidden" name="token" value="${param.token}" />
			        <p>Nova Senha: <input type="password" name="novaSenha" required /></p>
			        <p>Confirmar Senha: <input type="password" name="confirmarSenha" required /></p>
			        <p><input type="submit" value="Atualizar Senha" /></p>
			        <b><c:out value="${erro}"/></b>
			</form>
		</div>
	</div>
</body>
</html>