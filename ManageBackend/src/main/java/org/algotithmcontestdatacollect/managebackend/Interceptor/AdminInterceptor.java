package org.algotithmcontestdatacollect.managebackend.Interceptor;
import org.algotithmcontestdatacollect.managebackend.Utils.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var auth = request.getAttribute("isSuper");
        if (auth == null || !auth.equals("1")) {
            response.setStatus(200);
            response.setCharacterEncoding("UTF8");
            response.getWriter().write(ResponseUtil.JSONReturn(401,"非管理员"));
            return false;
        }
        return true;
    }
}
