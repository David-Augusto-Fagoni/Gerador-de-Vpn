<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
<link rel="stylesheet" href="https://cdn.datatables.net/1.13.6/css/jquery.dataTables.min.css">
<script src="https://code.jquery.com/jquery-3.7.0.js"></script>
<script src="https://cdn.datatables.net/1.13.6/js/jquery.dataTables.min.js"></script>
<head>
    <meta charset="UTF-8">
    <title>Gerenciamento de VPNs</title>
    <!-- Aqui você pode adicionar os links para Bootstrap, FontAwesome, etc. -->
</head>
	<body>
	<div class="text-end">
		<a href="/VPN/index" class="btn btn-secondary d-inline-block text-center" style="width: 20%;">Sair</a>
	</div>
	<div>
		<div class="border p-2" style="width: 100%; height: 100%; display: grid; grid-template-columns: 16% 1% 83%;">
			<div>
				<jsp:include page="menu.jsp" />
			</div>
			<div></div>
			<div>
			    <div>
			        <form action="procurarVpn" method="post">
			            <input type="text" placeholder="Search.." name="nomeVpn">
			            <button type="submit"><i class="fa fa-search">Procurar</i></button>
			            <b><c:out value="${erro}"/></b>
			        </form>	
			    </div>
			
			    <div style="display: grid; grid-template-columns: 20% 60% 20%; padding-top: 1%;">
			        <button type="button" id="btnRemover" class="fa fa-edit"> Remover </button>
			        <div class="form-container m-auto border p-2" style="width: 100%; height: 100%; border-color:rgb(1,1,1) !important;"></div>
			        <form action="vpn" method="post">
			        	<button type="submit" class="fa fa-edit" style="width: 100%;"> Novo </button>
			        </form>
			    </div>
				<form action="removerVpn" method="post" id="formRemoverVpn">
				    <div class="form-container m-auto border p-2" style="max-width: 100%;">
				        <table class="table" id="myTable">
				            <thead>
				                <tr>
				                	<th style="width: 5%; text-align:center;">Seleção</th>
				                	<th style="width: 5%; text-align:center;">Download</th>
				                    <th style="width: 28%;" onclick="sortTable(2)">Identificador</th>
				                    <th style="width: 35%;" onclick="sortTable(3)">Data</th>
				                    <th style="width: 31%;" onclick="sortTable(4)">Validade</th>
				                </tr>
				            </thead>
				            <tbody>
								<c:if test="${not empty vpns}">
									<c:forEach var="v" items="${vpns}">
										<tr>														
											<td style="text-align:center;"><input type="checkbox" name="selecionados" value="${v.id}"></td>
											<td style="text-align:center;">
											    <a href="downloadVpn/${v.id}" target="_blank" class="btn btn-outline-success btn-sm" title="Download">
											        <i class="fa fa-download">↓</i>
											    </a>
											</td>
											<td><c:out value="${v.id}"/></td>
											<td><c:out value="${v.dt_criacao}"/></td>
											<td><c:out value="${v.dt_validade}"/></td>
										</tr>
									</c:forEach>
								</c:if>
				            </tbody>
				        </table>
				    </div>
			    </form>
		    </div>
		</div>
	</div>
		<script>
			    function sortTable(n) {
			      var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
			      table = document.getElementById("myTable");
			      switching = true;
			      dir = "asc"; 
			      while (switching) {
			        switching = false;
			        rows = table.rows;
			        for (i = 1; i < (rows.length - 1); i++) {
			          shouldSwitch = false;
			          x = rows[i].getElementsByTagName("TD")[n];
			          y = rows[i + 1].getElementsByTagName("TD")[n];
			          if (dir == "asc") {
			            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
			              shouldSwitch= true;
			              break;
			            }
			          } else if (dir == "desc") {
			            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
			              shouldSwitch = true;
			              break;
			            }
			          }
			        }
			        if (shouldSwitch) {
			          rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			          switching = true;
			          switchcount ++;      
			        } else {
			          if (switchcount == 0 && dir == "asc") {
			            dir = "desc";
			            switching = true;
			          }
			        }
			      }
			    }
		</script>
		<script>
		    document.getElementById("btnRemover").addEventListener("click", function (event) {
		        event.preventDefault();
		        if (confirm("Você tem certeza que deseja remover as VPNs selecionadas?")) {
		            document.getElementById("formRemoverVpn").submit();
		        }
		    });
		</script>

				
	</body>
</html>
