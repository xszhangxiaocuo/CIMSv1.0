package github.com.xszhagxiaocuo.servlet;

import github.com.xszhagxiaocuo.dao.FruitDB;
import github.com.xszhagxiaocuo.entity.Fruit;
import github.com.xszhagxiaocuo.entity.User;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import util.WebApplication;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "IndexServlet", value = "/IndexServlet")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //（tomcat9中get没有参数的中文乱码问题，但是post有）解决中文乱码，在每次处理请求前先执行request.setCharacterEncoding("UTF-8");
        //request.setCharacterEncoding("UTF-8");必须放在所有代码的前面
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        List<Fruit> fruits = FruitDB.getFruits();

        HttpSession session = request.getSession();
        session.setAttribute("fruits",fruits);
        User user = (User) session.getAttribute("user");
        session.setAttribute("username",user.getName());
        //创建模版引擎
        WebApplication webApplication = new WebApplication(this.getServletContext());
        TemplateEngine templateEngine = webApplication.getTemplateEngine();
        //获取上下文
        WebContext ctx = new WebContext(request,response,this.getServletContext());
        //渲染页面
        templateEngine.process("index",ctx,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String field = request.getParameter("field");
        String key = request.getParameter("key");

        HttpSession session = request.getSession(false);
        List<Fruit> result=null;
        if (key.equals("")==false) {
           result = FruitDB.Search(field, key);
        }

        session.setAttribute("result",result);
        //创建模版引擎
        WebApplication webApplication = new WebApplication(this.getServletContext());
        TemplateEngine templateEngine = webApplication.getTemplateEngine();
        //获取上下文
        WebContext ctx = new WebContext(request,response,this.getServletContext());
        //渲染页面
        templateEngine.process("result",ctx,response.getWriter());
    }
}