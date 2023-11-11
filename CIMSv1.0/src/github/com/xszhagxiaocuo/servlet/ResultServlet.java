package github.com.xszhagxiaocuo.servlet;

import github.com.xszhagxiaocuo.dao.FruitDB;
import github.com.xszhagxiaocuo.entity.Fruit;
import github.com.xszhagxiaocuo.entity.PageInfo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import util.WebApplication;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ResultServlet", value = "/ResultServlet")
public class ResultServlet extends HttpServlet {
    List<Fruit> AllResult=new ArrayList<>();
    int pageNum = 5;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Integer page;
        PageInfo pageInfo = new PageInfo();
        List<Fruit> result = new ArrayList<>();
        if (AllResult!=null&&!AllResult.isEmpty()) {
            if (request.getParameter("page") == null) {
                page = 1;
            } else {
                page = Integer.parseInt(request.getParameter("page"));
                if (page > (AllResult.size() - 1) / pageNum + 1) {
                    page = 1;
                }
            }
            pageInfo.setCurrentPage(page);
            pageInfo.setPrevPage(page - 1);
            pageInfo.setNextPage(page + 1);
            pageInfo.setTotalPages((AllResult.size() - 1) / 5 + 1);
            pageInfo.setTotalRecords(AllResult.size());
            if (page == pageInfo.getTotalPages()) {
                pageInfo.setNextPage(0);
            }

            for (int i = (page - 1) * pageNum; i < (page - 1) * pageNum + pageNum; i++) {
                if (i >= AllResult.size()) {
                    break;
                }
                result.add(AllResult.get(i));
            }
        }
        session.setAttribute("resultPageInfo", pageInfo);
        session.setAttribute("result",result);
        //创建模版引擎
        WebApplication webApplication = new WebApplication(this.getServletContext());
        TemplateEngine templateEngine = webApplication.getTemplateEngine();
        //获取上下文
        WebContext ctx = new WebContext(request,response,this.getServletContext());
        //渲染页面
        templateEngine.process("result",ctx,response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        String field = request.getParameter("field");
        String key = request.getParameter("key");

        if (key.equals("")==false) {
            AllResult = FruitDB.Search(field, key);
        }
        doGet(request,response);

    }
}