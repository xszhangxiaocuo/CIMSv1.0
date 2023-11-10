package github.com.xszhagxiaocuo.servlet;

import github.com.xszhagxiaocuo.dao.UserDB;
import github.com.xszhagxiaocuo.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.html").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String autoLoginDuration = request.getParameter("autoLoginDuration");
        try {
            User user = UserDB.findUser(username).get(0);
            if (user.getPassword().equals(password)){
                int duration=5;//默认五分钟自动登录
                if (autoLoginDuration!=null){
                    duration = Integer.parseInt(autoLoginDuration);
                }
                Cookie cookie = new Cookie("autoLogin",username);
                cookie.setMaxAge(duration*60);  //单位分钟
                cookie.setPath(request.getContextPath());
                response.addCookie(cookie);
                request.getSession().setAttribute("user",user);
                response.sendRedirect("/CIMS/IndexServlet");
                return;
            }
        }catch (Exception e){
            System.out.println("用户不存在！");
        }
        request.getRequestDispatcher("/login.html").forward(request,response);

    }
}