package github.com.xszhagxiaocuo.filter;

import github.com.xszhagxiaocuo.dao.UserDB;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "AutoLoginFilter",urlPatterns = "/*")
public class AutoLoginFilter implements Filter {
    FilterConfig fc = null;
    public void init(FilterConfig config) throws ServletException {
        this.fc = config;
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if (req.getRequestURI().contains("/LogoutServlet")||req.getRequestURI().contains("/RegisterServlet")){//登录和注册页面不需要登录
            chain.doFilter(req,res);
            return;
        }

        boolean islogin = false,isaoutoLogin = false;

        Cookie[] cookies = req.getCookies();
        if (cookies!=null) {
            for (Cookie cookie : cookies) {
                if (islogin&&isaoutoLogin){
                    break;
                }
                if (cookie.getName().equals("isLogin")){
                    String isLogin = cookie.getValue();
                    if (isLogin.equals("true")){    //已经完成登录
                       islogin=true;
                    }
                } else if (cookie.getName().equals("autoLogin")) {//自动登录还未过期
                    String username = cookie.getValue();
                    if (UserDB.findUser(username).size() != 0) {
                        req.getSession().setAttribute("user",UserDB.findUser(username).get(0));
                        isaoutoLogin=true;
                        Cookie c = new Cookie("isLogin", "true");
                        c.setPath(req.getContextPath());
                        c.setMaxAge(5*60);
                        res.addCookie(c);
                    }
                }
            }
        }
        if (isaoutoLogin==false){//自动登录过期销毁session
            req.getSession().invalidate();
        }
        boolean isLoginServlet = req.getRequestURI().contains("/LoginServlet");
        if (islogin&&isaoutoLogin){
            if (isLoginServlet){
                res.sendRedirect("/CIMS/IndexServlet");
            }else {
                chain.doFilter(req, res);
            }
        }else {
            if (!isLoginServlet) {
                res.sendRedirect("/CIMS/LoginServlet");
            }else {
                chain.doFilter(req,res);
            }
        }

    }

}
