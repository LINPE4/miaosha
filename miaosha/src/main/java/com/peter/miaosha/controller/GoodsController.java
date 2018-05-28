package com.peter.miaosha.controller;

import com.peter.miaosha.domain.MiaoshaUser;
import com.peter.miaosha.redis.RedisService;
import com.peter.miaosha.service.GoodsService;
import com.peter.miaosha.service.MiaoshaUserService;
import com.peter.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

	@Autowired
	MiaoshaUserService userService;
	
	@Autowired
	RedisService redisService;

	@Autowired
	GoodsService goodsService;
	
    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user) {
		//查询商品列表
		List<GoodsVo> goodsList = goodsService.listGoodsVo();
		model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }
    
}
