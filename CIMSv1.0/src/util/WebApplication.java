package util;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;

public class WebApplication {
    private TemplateEngine templateEngine;
    private static WebApplication webApplication;

    public WebApplication(ServletContext servletContext) {
        //创建模板解析器
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);
        //设置模板为html
        templateResolver.setTemplateMode(TemplateMode.HTML);
        //设置前缀和后缀
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        //禁止缓存
        templateResolver.setCacheable(false);
        templateResolver.setCharacterEncoding("utf-8");
        //Thymeleaf中最核心的类
        this.templateEngine = new TemplateEngine();
        //将解析器对象设置到engine中
        this.templateEngine.setTemplateResolver(templateResolver);
    }

    //得到模板引擎
    public TemplateEngine getTemplateEngine() {
        return this.templateEngine;
    }

    public static WebApplication getInstance(ServletContext servletContext) {
        if (webApplication == null) {
            webApplication = new WebApplication(servletContext);
        }
        return webApplication;
    }
}
