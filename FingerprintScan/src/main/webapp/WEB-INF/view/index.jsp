<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<jsp:include page="header.jsp" />  
<body>  
	<div class="row mt-3">
		<div class="col-md-2">
			<div class="card" style="width: 10rem;">
			    <a href="#" onclick="crunchifyAjax('SG400')" class="btn btn-danger btn-block">TEMPLATE_FORMAT_SG400</a>
			    <a href="#" onclick="crunchifyAjax('ISO')" class="btn btn-danger btn-block">TEMPLATE_FORMAT_ISO19794</a>
			    <a href="#" onclick="crunchifyAjax('ANSI')" class="btn btn-danger btn-block">TEMPLATE_FORMAT_ANSI378</a>
			</div>
		</div>
		<div class="col-md-3">
			<textarea id="txtArea1" rows="10" cols="50">
			TEMPLATE_FORMAT_SG400
			</textarea>
			<img src="" id="scan_images"/>
		</div>
		<div class="col-md-3">
			<textarea id="txtArea2" rows="10" cols="50">
			TEMPLATE_FORMAT_ISO19794
			</textarea>
		</div>
		<div class="col-md-3">
			<textarea id="txtArea3" rows="10" cols="50">
			TEMPLATE_FORMAT_ANSI378
			</textarea>
		</div>
	</div>
 <script type="text/javascript"
    src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<script type="text/javascript">
    function crunchifyAjax(templateType) {
    	console.log(templateType);
    	var templateType = templateType;
        $.ajax({
            url : '/saveScanData?templateType='+templateType,
            success : function(data) {debugger;
                console.log(data.tempType);
                if(data.tempType == 'SG400'){
                	$("#txtArea1").text(data.byteStr);
                	 document.getElementById('scan_images').src = "data:image/bmp;base64," + data.byteStr;
                }else if(data.tempType == 'ISO'){
                	$("#txtArea2").text(data.byteStr);
                }else if(data.tempType == 'ANSI'){
                	$("#txtArea3").text(data.byteStr);
                }
            }
        });
    }
</script> 



</body>
