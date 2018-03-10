import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		System.out.println("我被调用啦！");
	}
	
	public void init(ServletConfig config) {
		System.out.println("init HelloServlet!!!");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("浏览器发出请求时的完整URL，包括协议 主机名 端口(如果有): " + request.getRequestURL());
        System.out.println("浏览器发出请求的资源名部分，去掉了协议和主机名: " + request.getRequestURI());
        System.out.println("请求行中的参数部分: " + request.getQueryString());
        System.out.println("浏览器所处于的客户机的IP地址: " + request.getRemoteAddr());
        System.out.println("浏览器所处于的客户机的主机名: " + request.getRemoteHost());
        System.out.println("浏览器所处于的客户机使用的网络端口: " + request.getRemotePort());
        System.out.println("服务器的IP地址: " + request.getLocalAddr());
        System.out.println("服务器的主机名: " + request.getLocalName());
        System.out.println("得到客户机请求方式: " + request.getMethod());

		request.setCharacterEncoding("UTF-8");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String html = null;

		if ("admin".equals(name) && "123456".equals(password)) {

			request.getRequestDispatcher("success.html").forward(request, response);

			html = "<div style='color:green'>登录成功!</div>";
		} else {
			response.sendRedirect("fail.html");
			html = "<div style='color:red'>failed!登录失败！</div>";
		}

		// byte[] bytes = name.getBytes("ISO-8859-1");
		// name = new String(bytes, "UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter printWriter = response.getWriter();
		printWriter.println(html);

		System.out.println("Your name is: " + name);
		System.out.println("and your passwd: " + password);
	}

}
