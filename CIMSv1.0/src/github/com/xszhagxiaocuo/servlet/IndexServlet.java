package github.com.xszhagxiaocuo.servlet;

import github.com.xszhagxiaocuo.dao.FruitDB;
import github.com.xszhagxiaocuo.entity.Fruit;
import github.com.xszhagxiaocuo.entity.PageInfo;
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
    int pageNum=5;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //（tomcat9中get没有参数的中文乱码问题，但是post有）解决中文乱码，在每次处理请求前先执行request.setCharacterEncoding("UTF-8");
        //request.setCharacterEncoding("UTF-8");必须放在所有代码的前面
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        List<Fruit> allFruits = FruitDB.getFruits();

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        session.setAttribute("username",user.getName());

        Integer page;
        PageInfo pageInfo = new PageInfo();
        if (request.getParameter("page")==null) {
            page=1;
        }else {
            page = Integer.parseInt(request.getParameter("page"));
            if (page>(allFruits.size()-1)/pageNum+1){
                page=1;
            }
        }
        pageInfo.setCurrentPage(page);
        pageInfo.setPrevPage(page-1);
        pageInfo.setNextPage(page+1);
        pageInfo.setTotalPages((allFruits.size()-1)/pageNum+1);
        pageInfo.setTotalRecords(allFruits.size());
        if (page==pageInfo.getTotalPages()){
            pageInfo.setNextPage(0);
        }
        session.setAttribute("pageInfo",pageInfo);

        List<Fruit> fruits = new ArrayList<>();
        for (int i = (page-1)*pageNum; i < (page-1)*pageNum + pageNum; i++) {
            if (i>=allFruits.size()){
                break;
            }
            fruits.add(allFruits.get(i));
        }

        session.setAttribute("fruits",fruits);

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
       request.getRequestDispatcher("/ResultServlet").forward(request,response);
    }
}