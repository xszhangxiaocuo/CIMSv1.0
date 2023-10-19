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
import java.util.List;

@WebServlet(name = "ModifyServlet", value = "/ModifyServlet")
public class ModifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String id = request.getParameter("id");
        Fruit fruit = FruitDB.SearchById(Integer.parseInt(id)).get(0);
        request.setAttribute("modifyId", id);
        request.setAttribute("modifyName", fruit.getName());
        request.setAttribute("modifyPrice", fruit.getPrice());
        request.setAttribute("modifyNumber", fruit.getNumber());
        request.setAttribute("modifyRemark", fruit.getRemark());
        //创建模版引擎
        WebApplication webApplication = new WebApplication(this.getServletContext());
        TemplateEngine templateEngine = webApplication.getTemplateEngine();
        //获取上下文
        WebContext ctx = new WebContext(request, response, this.getServletContext());
        //渲染页面
        templateEngine.process("modify", ctx, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        if (request.getHeader("Referer").contains("/IndexServlet")) {
            doGet(request, response);
            return;
        }

        String id = request.getParameter("id");
        String name = request.getParameter("name");
        String price = request.getParameter("price");
        String number = request.getParameter("number");
        String remark = request.getParameter("remark");
        System.out.println("modifyname:"+name);
        List<Fruit> list = FruitDB.SearchById(Integer.parseInt(id));
        if (list == null) {
            response.sendRedirect("/CIMS/IndexServlet");
            return;
        }
        try {
            Fruit fruit = list.get(0);
            if (name.equals("") == false) {
                fruit.setName(name);
            }
            if (price.equals("") == false && Integer.parseInt(price) >= 0) {
                fruit.setPrice(Integer.parseInt(price));
            }
            if (number.equals("") == false && Integer.parseInt(number) >= 0) {
                fruit.setNumber(Integer.parseInt(number));
            }
            if (remark.equals("") == false) {
                fruit.setRemark(remark);
            }
            if (FruitDB.Modify(fruit)) {
                System.out.println("修改成功！");
            } else {
                System.out.println("修改失败！");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        response.sendRedirect("/CIMS/IndexServlet");
    }
}