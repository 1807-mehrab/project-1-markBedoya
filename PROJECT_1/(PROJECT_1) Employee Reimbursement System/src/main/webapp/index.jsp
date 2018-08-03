<%-- 
    Document   : Login
    Created on : Jul 23, 2018, 6:11:24 PM
    Author     : Mark
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="webjars/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
        <link href="customStyle.css" rel="stylesheet"> 
        <title>login</title>
    </head>
    
    <body>
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
              <div class="navbar-header">
                <a class="navbar-brand" href="#">Employment Reimbursement Service</a>
          </nav>
        
        <div class="container text-center">
            <h1 >Login</h1>
            <br>
        
            <form action="/PROJECT_1/login" method="post">
                <p><font color="red">${errorMessage}</font></p>
                <label>Username:</label> 
                <br>
                <input type="text" name="username"/> 
                <br>
                <br>
                <label>Password:</label> 
                <br>
                <input type="password" name="password"/>
                <br>
                <br>
                <!-- <input type="submit" value="Login"/> -->
                <button type="submit" class="btn btn-primary" >Login</button>
            </form>
            
        </div>
        
        <footer class="footer">
            <p>Employee Reimbursement System by: Mark Bedoya</p>
        </footer>    
        
       
        
        <script> src="webjars/jquery/1.9.1/jquery.min.js"</script>  
        <script> src="webjars/bootstrap/3.3.7/js/bootstrap.min.js"</script> 
    </body>
</html>





