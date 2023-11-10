package github.com.xszhagxiaocuo.servlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", value = "/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 注销session
        request.getSession().invalidate();
        //注销cookie
        for (Cookie cookie:request.getCookies()){
            cookie.setMaxAge(0);
            response.addCookie(cookie);//提交修改后的cookie
        }
        response.sendRedirect("/CIMS/LoginServlet");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}