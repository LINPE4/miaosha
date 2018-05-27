package com.peter.miaosha.controller;

import com.peter.miaosha.redis.RedisService;
import com.peter.miaosha.result.CodeMsg;
import com.peter.miaosha.result.Result;
import com.peter.miaosha.service.MiaoshaUserService;
import com.peter.miaosha.util.ValidatorUtil;
import com.peter.miaosha.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger log = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
    MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }
    
    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response,@Valid LoginVo loginVo) {
    	log.info(loginVo.toString());
    	//登录
    	userService.login(response, loginVo);
        return Result.success(true);
    }
}
