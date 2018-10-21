package com.peter.miaosha.access;

import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginInterceptor extends HandlerInterceptorAdapter{
	

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if(handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
			NeedLogin accessLimit = hm.getMethodAnnotation(NeedLogin.class);
			if (accessLimit == null) {
				return  true;
			}
			boolean needLogin = accessLimit.needLogin();
			if (needLogin) {
				response.sendRedirect("/login/to_login");
			}
		}
		return true;
	}
	

}
