<%@ page
	import="java.sql.*, javax.sql.*,javax.naming.*,com.zys.idtv.impl.*"
	contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="com.zys.idtv.impl.UserImpl"%>
<html>
<head>
<title></title>
</head>
<body>
	<h2 style="text-align: center">JSP数据库连接测试</h2>
	<%
		ResultSet rs = null;
		try {
			DataSource ds;
			Context ctx = new InitialContext(); //建立Context对象
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/idtvDB"); //建立datasource对象
			Connection conn = ds.getConnection(); //通过数据源对象建连接
			Statement st = conn.createStatement();
			String sql = "select * from user";
			rs = st.executeQuery(sql);
			while (rs.next()) {
	%>
	您的第一个字段内容为：<%=rs.getString(1)%>
	您的第二个字段内容为：<%=rs.getString(2)%>
	<br>
	<%
		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	%>
	<%
		try {
			if (rs != null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	%>
	<form action="addUsers.action" method="post">
		<p>
			姓名: <input type="text" name="name" />
		</p>
		<p>
			密码: <input type="password" name="passwd" />
		</p>
		<p>
			类型: <input type="text" name="typeId" />(必须是数字)
		</p>
		<p>
			QQ : <input type="text" name="qq" />
		</p>
		<p><input type="submit" value="提交"/></p>
	</form>
</body>
</html>
