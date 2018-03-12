import java.awt.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;;

public class UploadPhotoServlet extends HttpServlet{
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String fileName = "";
		try {
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			factory.setSizeThreshold(1024 * 1024); //�����ϴ��ļ��Ĵ�С����Ϊ1M
			
			java.util.List items = null;
			try {
				items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
			Iterator iterator = items.iterator();
			while (iterator.hasNext() ) {
				FileItem item = (FileItem) iterator.next();
				if (!item.isFormField() ) {
					fileName = System.currentTimeMillis() + ".jpg";  //����ʱ�������ͷ���ļ�
					String photoFolder = request.getServletContext().getRealPath("uploaded");  //ͨ��getRealPath��ȡ�ϴ��ļ��У������Ŀ��G:/eclipse-project/j2eeDemo/web����ô�ͻ��Զ���ȡ��G:/eclipse-project/j2eeDemo/web/uploaded
					System.out.println(request.getServletContext().getRealPath("uploaded") );
					File file = new File(photoFolder, fileName);
					file.getParentFile().mkdirs();
					
					InputStream inputStream = item.getInputStream();  //ͨ��item.getInputStream()��ȡ������ϴ����ļ���������
					
					//�����ļ�
					FileOutputStream fos = new FileOutputStream(file);
					byte b[] = new byte[1024 * 1024];
					int length = 0;
					while (-1 != (length = inputStream.read(b) ) ) {
						fos.write(b, 0, length);
					}
					fos.close();
				
				} else {
					System.out.println(item.getFieldName() );
					String value = item.getString();
					value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
					System.out.println(value);
				}
				
			}
			String html = "<img width='200' height='150' src='uploaded/%s'/>";
			response.setContentType("text/html");
			PrintWriter printWriter = response.getWriter();
			printWriter.format(html, fileName);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (Exception e) {
		e.printStackTrace();
	}
		
		
		
	}

}
