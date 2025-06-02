<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
                    	<p style="padding-left: 20px;">VPN</p>
                    	<p style="padding-left: 20px;">Cliente</p>
                    </div>
                    <div></div>
                    <div class="m-auto border p-2" style="width: 100%;height: 100%;">
						<div>
							<jsp:include page="cliente.jsp"/>
						</div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>