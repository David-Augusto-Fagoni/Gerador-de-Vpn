<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
	
	<title>Lista de Funcionários</title>
</head>
    <body>
        <div style="padding: 10%;">
            <div>
            	<form action="index" method="post">
	                <div class="form-container m-auto border p-2" style="width: 50%; height: 50%; min-width: 40%;">
	                    <h1> Login </h1>
	                    <div class="form-floating mb-3 mt-4">
	                        <h2> Usuario </h2>
	                        <input type="text" class="form-control input-height" placeholder="Nome de Usuario" id="floatingInput" name="usuarioFuncionario">
	                    </div>
	                    <div class="form-floating mb-3 mt-4">
	                        <h2> Senha </h2>
	                        <input type="password" class="form-control input-height" placeholder="Senha de Usuario" id="floatingInput" name="senhaFuncionario">
	                    </div>
	                    <div class="d-flex">
	                        <a href="./esqueciSenha">Esqueci minha senha/Primeiro acesso</a>
	                    </div>
	                    <div class="d-flex">
	                        <button type="submit" class="btn btn-secondary" style="width: 20%;">Entrar</button>
	                    </div>
					</div>
					<div>
						<c:if test="${not empty erro}">
							<h2 class="text-center"><b><c:out value="${erro}"/></b></h2>				
						</c:if>
					</div>
				</form>
            </div>
        </div>
    </body>
</html>