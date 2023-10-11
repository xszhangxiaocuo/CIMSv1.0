package github.com.xszhagxiaocuo.servlet;

import github.com.xszhagxiaocuo.dao.FruitDB;
import github.com.xszhagxiaocuo.entity.Fruit;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import util.WebApplication;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AddServlet", value = "/AddServlet")
public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //创建模版引擎
        WebApplication webApplication = new WebApplication(this.getServletContext());
        TemplateEngine templateEngine = webApplication.getTemplateEngine();
        //获取上下文
        WebContext ctx = new WebContext(request,response,this.getServletContext());
        //渲染页面
        templateEngine.process("add",ctx,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String number = request.getParameter("number");
        String remark = request.getParameter("remark");

        try {
            if (id.equals("") == false && name.equals("") == false && price.equals("") == false && number.equals("") == false) {
                Fruit fruit = new Fruit(Integer.parseInt(id), name, Integer.parseInt(price), Integer.parseInt(number), remark);
                if(FruitDB.Add(fruit)){
                    System.out.println("添加成功！");
                }else {
                    System.out.println("添加失败！");
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        response.sendRedirect("/CIMS/IndexServlet");
    }
}