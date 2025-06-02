<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Depto</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
	<div align="center">
		<jsp:include page="header.jsp" />
	</div>
	<div class="conteiner" align="center">
		<h1>Cadastro de Depto</h1>
		<br />
		<form action="depto" method="post">
			<table >
				<tr style="border-bottom: solid white 12px;">
					<td colspan="3">
						<input type="number" min="0" step="1" name="codigo" id="codigo" placeholder="Codigo" value='<c:out value="${depto.codigo }" />'>
					</td>
					<td colspan="1"><input type="submit" id="botao" name="botao" value="Buscar" class="btn btn-dark"></td>
				</tr>
				<tr style="border-bottom: solid white 12px;">
					<td colspan="4">
						<input type="text" name="nome" id="nome"placeholder="Nome" value='<c:out value="${depto.nome }" />'>
					</td>
				</tr>
				<tr style="border-bottom: solid white 12px;">
					<td colspan="4">
						<input type="text" name="sigla" id="sigla" placeholder="Sigla" value='<c:out value="${depto.sigla }" />'>
					</td>
				</tr>
				<tr style="border-bottom: solid white 8px;">
					<td><input style="margin: 0 2px;" type="submit" id="botao" name="botao" value="Inserir" class="btn btn-dark"></td>
					<td><input style="margin: 0 2px;" type="submit" id="botao" name="botao" value="Atualizar" class="btn btn-dark"></td>
					<td><input style="margin: 0 2px;" type="submit" id="botao" name="botao" value="Excluir" class="btn btn-dark"></td>
					<td><input style="margin: 0 2px;" type="submit" id="botao" name="botao" value="Listar" class="btn btn-dark"></td>			
				</tr>
				<tr style="border-bottom: solid white 8px;">
					<td colspan="4" align="center"><input style="margin: 0 2px;" type="submit" id="botao" name="botao" value="Criar Sigla" class="btn btn-dark"></td>
				</tr>
			</table>
		</form>
	</div>
	<br />
	<div class="conteiner" align="center">
		<c:if test="${not empty saida }">
			<h2 style="color: blue;"><c:out value="${saida }"></c:out></h2>
		</c:if>
	</div>
	<div class="conteiner" align="center">
		<c:if test="${not empty erro }">
			<h2 style="color: red;"><c:out value="${erro }"></c:out></h2>
		</c:if>
	</div>
	<div class="conteiner" align="center">
		<c:if test="${not empty deptos }">
			<table class="table table-dark table-striped-columns">
				<thead>
					<tr>
						<th>Codigo</th>
						<th>Nome</th>
						<th>Sigla</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="d" items="${deptos }">
						<tr>
							<td>${d.codigo }</td>
							<td>${d.nome }</td>
							<td>${d.sigla }</td>
							<td><a href="depto?acao=editar&codigo=${d.codigo}">EDITAR</a></td>
							<td><a href="depto?acao=excluir&codigo=${d.codigo}">EXCLUIR</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</div>
</body>
</html>