import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

	public LoginServlet() {
		System.out.println("�ұ���������");
	}
	
	public void init(ServletConfig config) {
		System.out.println("init HelloServlet!!!");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println("�������������ʱ������URL������Э�� ������ �˿�(�����): " + request.getRequestURL());
        System.out.println("����������������Դ�����֣�ȥ����Э���������: " + request.getRequestURI());
        System.out.println("�������еĲ�������: " + request.getQueryString());
        System.out.println("����������ڵĿͻ�����IP��ַ: " + request.getRemoteAddr());
        System.out.println("����������ڵĿͻ�����������: " + request.getRemoteHost());
        System.out.println("����������ڵĿͻ���ʹ�õ�����˿�: " + request.getRemotePort());
        System.out.println("��������IP��ַ: " + request.getLocalAddr());
        System.out.println("��������������: " + request.getLocalName());
        System.out.println("�õ��ͻ�������ʽ: " + request.getMethod());

		request.setCharacterEncoding("UTF-8");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String html = null;

		if ("admin".equals(name) && "123456".equals(password)) {

			request.getRequestDispatcher("success.html").forward(request, response);

			html = "<div style='color:green'>��¼�ɹ�!</div>";
		} else {
			response.sendRedirect("fail.html");
			html = "<div style='color:red'>failed!��¼ʧ�ܣ�</div>";
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
