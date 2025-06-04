<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<head>
<meta charset="UTF-8">
<title>Menu Principal</title>
</head>
    <body>
    <div align="center">
		<jsp:include page="header.jsp" />
	</div>
        <div>
            <div>
                <div class="form-container m-auto border p-2">
                    <button class="btn btn-secondary" style="width: 20%;">Sair</button>
				</div>
                <div class="border p-2" style="width: 100%; height: 100%; display: grid; grid-template-columns: 20% 2% 75%;">
                    <div class="m-auto border p-2" style="width: 100%;height: 100%;">
                    	<p>Opçoes:</p>
		                <a class="menu-link" onclick="carregarConteudo('cliente')" style="padding-left: 20px;">Cliente</a>
		                <a class="menu-link" onclick="carregarConteudo('vpn')" >VPN</a>
                    </div>
                    <div></div>
		            <div class="m-auto border p-2" style="width: 100%; height: 100%;">
		                <main id="conteudoPrincipal">
		                    <%-- Carrega cliente.jsp na primeira visita --%>
		                    <p>Sececione a tela no menu a esquerda</p>
		                </main>
		            </div>
                </div>
            </div>
        </div>
    <script>
	    function carregarConteudo(pagina) {
	        const principal = document.getElementById("conteudoPrincipal");
	        principal.innerHTML = "<p>Carregando...</p>";
	
	        fetch(pagina, {
	            headers: {
	                'X-Requested-With': 'XMLHttpRequest'
	            }
	        })
	        .then(response => {
	            if (!response.ok) {
	                throw new Error("Erro HTTP " + response.status);
	            }
	            return response.text();
	        })
	        .then(html => {
	            principal.innerHTML = html;
	        })
	        .catch(error => {
	            principal.innerHTML = "<p class='text-danger'>Erro ao carregar conteúdo.</p>";
	            console.error("Erro ao carregar:", error);
	        });
	    }
    </script>
    </body>
</html>