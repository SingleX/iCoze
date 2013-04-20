<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>iCoze - 注册</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link rel="stylesheet" type="text/css" href="styles.css">
	<script language="javascript">
	function check() {
		var username = document.getElementById("username");
		var password = document.getElementById("password");
		var password2 = document.getElementById("password2");
		
		if (username.value == "") {
			alert("请输入昵称！");
			username.focus();
			return false;
		}
		
		if (password.value == "") {
			alert("请输入密码！");
			password.focus();
			return false;
		}
		if (password2.value == "") {
			alert("请确认密码！");
			password2.focus();
			return false;
		}
		if (password.value != password2.value) {
			alert("两次输入的密码不一致，请重新输入！");
			password2.focus();
			password.focus();
			return false;
		}
		return true;
	}
</script>
	
  </head>
  
  <body>
    <div class="bg">
    	<div class="container">
    		<div class="header"></div>
    		<div class="content">
    			<div class="left">
    				<div class="left01">iCoze账号</div>
    				<div class="left02">邮箱账号</div>
    				<div class="left03">手机账号</div>
    			</div>
    			<div class="right">
    				
	    				<div class="right01">
	    					<div class="item1">您的号码</div>
	    					<div class="item1">昵称</div>
	    					<div class="item1">密码</div>
	    					<div class="item1">确认密码 </div>
	    					<div class="item1">性别</div>
	    					<div class="item1"> </div>
	    					<div class="item1"> </div>
	    				</div>
	    				<div class="right02">
	    				<form action="reg" onSubmit="return check()">
	    					<div class="item2">
	    					<%
	    					String n = null;
	    					if((String)session.getAttribute("NUMBER") == null){
	    						n = "";
	    					}else{
	    						n = (String)session.getAttribute("NUMBER");
	    					}
	    					%>
	    						<input type="text" name="number" value="<%= (n == "") ? "-- iCoze --" : n%>" readonly="true" /><br>
	    					</div>
	    					<div class="item2">
	    						<input type="text" name="username" /><br>
	    					</div>
	    					<div class="item2">
	    						<input type="text" name="password" /><br>
	    					</div>
	    					<div class="item2">
	    						<input type="text" name="password2" /><br>
	    					</div>
	    					<div class="item2">
	    						<input type="radio" name="gender" value="0" />男
	    						&nbsp;&nbsp;&nbsp;&nbsp;
	    						<input type="radio" name="gender" value="1" checked />女
	    					</div>
	    					<div class="item2">
	    						<input type="submit" value="注册"> 
	    						<input type="reset" value="重写">
	    					</div>	
	    					</form>
	    				</div>
    				
    			</div>
    		</div>
    	</div>
    </div>
    <div class="footer"> 
    </div>
  </body>
</html>
