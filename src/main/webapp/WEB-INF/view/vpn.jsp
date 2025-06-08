<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Gerenciamento de VPNs</title>
    <!-- Aqui você pode adicionar os links para Bootstrap, FontAwesome, etc. -->
</head>
<body>
<div>
	<div align="center">
		<jsp:include page="header.jsp" />
	</div>
	<div class="border p-2" style="width: 100%; height: 100%; display: grid; grid-template-columns: 16% 1% 83%;">
		<div>
			<jsp:include page="menu.jsp" />
		</div>
		<div></div>
		<div>
		    <div>
		        <form action="/">
		            <input type="text" placeholder="Search.." name="search">
		            <button type="submit"><i class="fa fa-search">Procurar</i></button>
		        </form>
		    </div>
		
		    <div style="display: grid; grid-template-columns: 20% 60% 20%; padding-top: 1%;">
		        <button type="button" class="fa fa-edit"> Remover </button>
		        <div class="form-container m-auto border p-2" style="width: 100%; height: 100%; border-color:rgb(1,1,1) !important;"></div>
		        <button type="button" class="fa fa-edit" data-toggle="modal" data-target="#exampleModal"> Novo </button>
		    </div>
		
		    <div class="form-container m-auto border p-2" style="max-width: 100%;">
		        <table class="table" id="myTable">
		            <thead>
		                <tr>
		                	<th style="width: 5%; text-align:center;">Seleção</th>
		                	<th style="width: 5%; text-align:center;">Download</th>
		                    <th style="width: 28%;" onclick="sortTable(0)">Identificador</th>
		                    <th style="width: 35%;" onclick="sortTable(1)">Data</th>
		                    <th style="width: 31%;" onclick="sortTable(2)">Validade</th>
		                </tr>
		            </thead>
		            <tbody>
						<c:if test="${not empty vpns}">
							<c:forEach var="v" items="${vpns}">
								<tr>
									<td style="text-align:center;"><input type="checkbox" name="selecao${v.id}"></td>
									<td style="text-align:center;"><button type="button" class="fa fa-edit" id="${v.id}">↓</button></td>
									<td><c:out value="${v.id}"/></td>
									<td><c:out value="${v.dt_criacao}"/></td>
									<td><c:out value="${v.dt_validade}"/></td>
								</tr>
							</c:forEach>
						</c:if>
		            </tbody>
		        </table>
		
		        <!-- Modal -->
		        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
		            <div class="modal-dialog modal-dialog-centered" style="max-width: 80% !important; height: 80vh !important;">
		                <div class="modal-content">
		                    <div class="modal-header d-flex justify-content-between align-items-center">
		                        <h5 class="modal-title" id="exampleModalLabel">Adicionando novo funcionário</h5>
		                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		                            <span aria-hidden="true">&times;</span>
		                        </button>
		                    </div>
		                    <div class="modal-body">
		                        <div class="form-floating mb-3 mt-4" style="padding-left:10%; padding-right:10%;">
		                            <div>
		                                <h5> Nome Completo </h5>
		                                <input type="text" class="form-control" placeholder="Nome Completo" id="floatingInput" name="nomeUsuario">
		                            </div>
		                            <div style="padding-top: 20px;">
		                                <h5> Usuário </h5>
		                                <input type="text" class="form-control" placeholder="Nome de Usuário" id="floatingInput" name="usuarioUsuario" style="max-width: 60%;">
		                            </div>
		                            <div style="padding-top: 20px;">
		                                <h5> Email </h5>
		                                <div style="display: flex; align-items: center; gap: 10px;">
		                                    <input type="text" class="form-control" placeholder="Email" id="floatingInput" name="emailUsuario" style="max-width: 60%;">
		                                    <label> @empresa.com.br</label>
		                                </div>
		                            </div>
		                            <div style="padding-top: 20px;">
		                                <input type="checkbox" id="administrador" name="administrador">
		                                <label for="administrador"> Administrador</label>
		                            </div>
		                        </div>
		                    </div>
		                    <div class="modal-footer">
		                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
		                        <button type="button" class="btn btn-primary">Cadastrar Funcionário</button>
		                    </div>
		                </div>
		            </div>
		        </div>
		    </div>
	    </div>
	</div>
</div>
			<script>
		    function sortTable(n) {
		      var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
		      table = document.getElementById("myTable");
		      switching = true;
		      //Set the sorting direction to ascending:
		      dir = "asc"; 
		      /*Make a loop that will continue until
		      no switching has been done:*/
		      while (switching) {
		        //start by saying: no switching is done:
		        switching = false;
		        rows = table.rows;
		        /*Loop through all table rows (except the
		        first, which contains table headers):*/
		        for (i = 1; i < (rows.length - 1); i++) {
		          //start by saying there should be no switching:
		          shouldSwitch = false;
		          /*Get the two elements you want to compare,
		          one from current row and one from the next:*/
		          x = rows[i].getElementsByTagName("TD")[n];
		          y = rows[i + 1].getElementsByTagName("TD")[n];
		          /*check if the two rows should switch place,
		          based on the direction, asc or desc:*/
		          if (dir == "asc") {
		            if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
		              //if so, mark as a switch and break the loop:
		              shouldSwitch= true;
		              break;
		            }
		          } else if (dir == "desc") {
		            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
		              //if so, mark as a switch and break the loop:
		              shouldSwitch = true;
		              break;
		            }
		          }
		        }
		        if (shouldSwitch) {
		          /*If a switch has been marked, make the switch
		          and mark that a switch has been done:*/
		          rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
		          switching = true;
		          //Each time a switch is done, increase this count by 1:
		          switchcount ++;      
		        } else {
		          /*If no switching has been done AND the direction is "asc",
		          set the direction to "desc" and run the while loop again.*/
		          if (switchcount == 0 && dir == "asc") {
		            dir = "desc";
		            switching = true;
		          }
		        }
		      }
		    }
		    </script>
</body>
</html>
