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
    <title>Gerenciamento de Funcionários</title>
    <!-- Adicione aqui os links para Bootstrap, FontAwesome etc. se necessário -->
</head>
	<body>
	<div align="center">
		<jsp:include page="header.jsp" />
	</div>
		<div class="border p-2" style="width: 100%; height: 100%; display: grid; grid-template-columns: 16% 1% 83%;">
			<div style="width: 100%;height: 100%;">
				<jsp:include page="menu.jsp" />
			</div>
			<div></div>
			<div>
				<form action="procFuncionario" method="post">
			   		<div>
			            <input type="text" placeholder="Search.." name="nomeFuncionario">
			            <button type="submit"><i class="fa fa-search">Procurar</i></button>
			            <b><c:out value="${erro}"/></b>
			    	</div>
			    </form>
				<form action="acaoFuncionario" method="post" id="formAcaoFuncionario">
				    <div style="width: 100%; height: 100%; display: grid; grid-template-columns: 20% 60% 20%; padding-top: 1%;">
					        <select name="acao" id="acaoSelect" onchange="enviarAcao()" style="border-color:rgb(1,1,1) !important;">
						        <option value="">-- Editar --</option>
								<option value="elegerAdm">Eleger como administrador</option>
								<option value="revogarAdm">Revogar administrador</option>
								<option value="revogarAcess">Revogar acesso</option>
								<option value="ativarAcess">Ativar acesso</option>
								<option value="removerCad">Remover o cadastro</option>	
							</select>
				        <div class="form-container m-auto border p-2" style="width: 100%; height: 100%; border-color:rgb(1,1,1) !important;"></div>
				        <button type="button" class="fa fa-edit" data-toggle="modal" data-target="#exampleModal"> Novo </button>
				    </div>
			
				    <div class="form-container m-auto border p-2" style="max-width: 100%;">
				        <table class="table" id="myTable">
				                <tr>
				                	<th style="width: 10%;text-align:center;">Seleção</th>
				                    <th style="width: 70%;" onclick="sortTable(1)">Nome</th>
				                    <th style="width: 20%;" onclick="sortTable(2)">Função</th>
				                </tr>
				            <tbody>
								<c:if test="${not empty funcionarios}">
									<c:forEach var="f" items="${funcionarios}">
										<tr>
											<td style="text-align:center"><input type="checkbox" name="selecionados" value="${f.email}"></td>
											<td scope="row"><c:out value="${f.nome}"/></td>
											<td><c:out value="${f.permissao}"/></td>
										</tr>
									</c:forEach>
								</c:if>
				            </tbody>
				        </table>
				    </div>
				</form>
						
				<!-- Modal -->
				<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
					  <div class="modal-dialog modal-dialog-centered" style="max-width: 80% !important; height: 80vh !important;">
					    	<div class="modal-content">
								<div class="modal-header d-flex justify-content-between align-items-center">
									<h5 class="modal-title" id="exampleModalLabel">Adicionando novo funcionario</h5>
									<button type="button" class="close" data-dismiss="modal" aria-label="Close" >
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
							<form action="funcionario" method="post">
								<div class="modal-body">
									<div class="form-floating mb-3 mt-4" style="padding-left:10%; padding-right:10%;">
										<div>
					                        <h5> Nome Completo </h5>
					                        <input type="text" class="form-control" placeholder="Nome Completo" id="floatingInput" name="nomeFuncionario">
				                        </div>
				                        <div style="padding-top: 20px;">
					                        <h5> Usuario </h5>
					                        <input type="text" class="form-control" placeholder="Nome de Usuario" id="floatingInput" name="usuarioFuncionario" style="max-width: 60%">
				                        </div>
				                        <div style="padding-top: 20px;">
					                        <h5> Email </h5>
					                        <div style="display: flex; align-items: center; gap: 10px;">
						                        <input type="text" class="form-control" placeholder="Email" id="floatingInput" name="emailFuncionario" style="max-width: 60%">
						                        <label> @empresa.com.br</label>
					                       	</div>
				                        </div>
				                        <div style="padding-top: 20px;">
					                        <input type="checkbox" id="administrador" name="administradorCheck">
					                        <label for="administrador"> Administrador</label>
				                        </div>
			                    	</div>
								</div>
								<div class="modal-footer">
									<button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
									<button type="submit" class="btn btn-primary">Cadastrar Funcionario</button>
								</div>
							</form>
					    </div>
					</div>
				</div>
			</div>
		</div>
		<script>
		    function enviarAcao() {
		        const select = document.getElementById("acaoSelect");
		        if (select.value !== "") {
		            document.getElementById("formAcaoFuncionario").submit();
		        }
		    }
		</script>
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
