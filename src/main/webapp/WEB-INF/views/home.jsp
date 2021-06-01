<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>

<P>  The time on the server is 
<!-- jsp에서 컨트롤러에서 넘어온 자바자료(object)를 출력한다 -->
${jspObject}
</P>
</body>
</html>
