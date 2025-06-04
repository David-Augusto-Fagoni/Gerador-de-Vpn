<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<div>
    <div>
        <form action="/action_page.php">
            <input type="text" placeholder="Search.." name="search">
            <button type="submit"><i class="fa fa-search">Procurar</i></button>
        </form>
    </div>

    <div style="width: 100%; height: 100%; display: grid; grid-template-columns: 20% 60% 20%; padding-top: 1%;">
        <button type="button" class="fa fa-edit"> Remover </button>
        <div class="form-container m-auto border p-2" style="width: 100%; height: 100%; border-color:rgb(1,1,1) !important;"></div>
        <button type="button" class="fa fa-edit"> Novo </button>
    </div>

    <div class="form-container m-auto border p-2" style="max-width: 100%;">
        <table class="table" id="myTable">
                <tr>
                	<th style="width: 5%; text-align:center;">Seleção</th>
                	<th style="width: 5%; text-align:center;">Download</th>
                    <th style="width: 28%;" onclick="sortTable(0)">Identificador</th>
                    <th style="width: 35%;" onclick="sortTable(1)">Data</th>
                    <th style="width: 31%;" onclick="sortTable(2)">Validade</th>
                </tr>
            <tbody>
				<c:if test="${not empty vpns}">
					<c:forEach var="v" items="${vpns}">
						<tr>
							<td style="text-align:center;"><input type="checkbox" name="selecao${v.id}"></td>
							<td style="text-align:center;"><button type="button" class="fa fa-edit" id="${v.id}">↓</button></td>
							<td> <c:out value="${v.id}"/></td>
							<td> <c:out value="${v.dt_criacao}"/></td>
							<td> <c:out value="${v.dt_validade}"/></td>
						</tr>
					</c:forEach>
				</c:if>
            </tbody>
        </table>
			
		<!-- Modal -->
		<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			  <div class="modal-dialog modal-dialog-centered" Style="max-width: 80% !important; height: 80vh !important;">
			    	<div class="modal-content">
						<div class="modal-header d-flex justify-content-between align-items-center">
							<h5 class="modal-title" id="exampleModalLabel">Adicionando novo funcionario</h5>
							<button type="button" class="close" data-dismiss="modal" aria-label="Close" >
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
					<div class="modal-body">
						<div class="form-floating mb-3 mt-4" Style="padding-left:10%; padding-right:10%;">
							<div>
		                        <h5> Nome Completo </h5>
		                        <input type="text" class="form-control" placeholder="Nome Completo" id="floatingInput" name="nomeUsuario">
	                        </div>
	                        <div Style="padding-top: 20px;">
		                        <h5> Usuario </h5>
		                        <input type="text" class="form-control" placeholder="Nome de Usuario" id="floatingInput" name="usuarioUsuario" Style="max-width: 60%">
	                        </div>
	                        <div Style="padding-top: 20px;">
		                        <h5> Email </h5>
		                        <div style="display: flex; align-items: center; gap: 10px;">
			                        <input type="text" class="form-control" placeholder="Email" id="floatingInput" name="emailUsuario" Style="max-width: 60%">
			                        <label> @empresa.com.br</label>
		                       	</div>
	                        </div>
	                        <div Style="padding-top: 20px;">
		                        <input type="checkbox" id="administrador" name="administrador">
		                        <label for="administrador"> Administrador</label>
	                        </div>
                    	</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-secondary" data-dismiss="modal">Fechar</button>
						<button type="button" class="btn btn-primary">Cadastrar Funcionario</button>
					</div>
			    </div>
			</div>
		</div>
    </div>
</div>

