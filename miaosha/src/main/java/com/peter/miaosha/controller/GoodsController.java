package com.peter.miaosha.controller;

import com.peter.miaosha.domain.MiaoshaUser;
import com.peter.miaosha.redis.RedisService;
import com.peter.miaosha.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;
	
    @RequestMapping("/to_list")
    public String list(Model model,
					   @CookieValue(value = MiaoshaUserService.COOKI_NAME_TOKEN, required = false) String cookieToken,
					   @RequestParam(value = MiaoshaUserService.COOKI_NAME_TOKEN, required = false) String paramToken,
					   HttpServletResponse response) {
    	if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
    		return "login";
		}
		String token = StringUtils.isEmpty(cookieToken)? paramToken : cookieToken;
    	MiaoshaUser user = userService.getByToken(response,token);
    	model.addAttribute("user", user);
        return "goods_list";
    }
    
}
