package com.neuedu.oa.web.servlet.userhome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.neuedu.oa.entity.MenuEntity;
import com.neuedu.oa.service.UserService;
import com.neuedu.oa.web.servlet.AbstractAjaxHttpServlet;
@WebServlet("/api/user/loadMenu")
public class LoadMyMenusServlet extends AbstractAjaxHttpServlet {

	@Override
	public Object handleRequest(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		UserService userService=this.getBean(UserService.class);
		String userId=this.getCurrentUserId(req);
		List<MenuEntity> menus = userService.loadUserMenus(userId);
		
		List<Map<String,Object>> dtos=new ArrayList<>(menus.size());
		//转换实体为dto /vo=
		for (MenuEntity menu : menus) {
			dtos.add(entity2dto(menu));
		}
		return dtos;
	}
	//bean2map
	private Map<String,Object> entity2dto(MenuEntity m){
		Map<String,Object> dto=new HashMap<>();
		dto.put("id", m.getId());
		dto.put("name", m.getName());
		dto.put("url", m.getUrl());
		dto.put("parentId", m.getParentId());
		return dto;
	}
}
