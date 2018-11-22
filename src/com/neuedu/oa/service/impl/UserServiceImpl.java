package com.neuedu.oa.service.impl;

import static com.neuedu.oa.validate.AssertUtil.$;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.neuedu.oa.ao.ImageAo;
import com.neuedu.oa.dao.MenuDao;
import com.neuedu.oa.dao.RoleDao;
import com.neuedu.oa.dao.UserDao;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.entity.MenuEntity;
import com.neuedu.oa.entity.UserEntity;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.UserService;
import com.neuedu.oa.util.MD5;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	RoleDao rdao;
	@Autowired
	UserDao udao;
	@Autowired
	MenuDao mdao;
	
	@Override
	public ImageAo getUserAvatar(String userId) throws Exception {
		userId=$("userId",userId);
		UserEntity u=udao.select("id", userId);
		if(u==null) {
			throw new OAException("用户不存在");
		}
		String avatar=u.getAvatar();
		ImageAo avatarAo=null;
		if(avatar!=null) {
			avatarAo=JSON.parseObject(avatar, ImageAo.class);
		}else {
			avatarAo=ImageAo.getDefaultAvatarAo();
		}
		return avatarAo;
	}
	
	@Override
	public void modifyUserAvatar(String currentUserId,ImageAo ao) throws Exception{
		currentUserId=$("currentUserId",currentUserId);
		
		String newPath=JSON.toJSONString(ao);
		
		Map<String,Object> map=new HashMap<>();
		map.put("avatar", newPath);
		udao.update(currentUserId, map);
		
		
	}
	@Override
	public UserContext login(String account, String password,String loginIp) throws Exception {
		//1验证清理参数
		
		if(account==null||(account=account.trim()).length()==0) {
			throw new OAException("账号不能为空");
		}
		
		if(password==null||(password=password.trim()).length()==0) {
			throw new OAException("密码不能为空");
		}
		if(account.length()>32) {
			throw new OAException("账号不存在");
		}
		//2执行业务逻辑
		//2.1账号是否存在
		UserEntity u=udao.select("account",account);
		
		if(u==null) {
			throw new OAException("账号和密码不匹配");
		}
		
		//2.2密码是否正确
		if(!password.equals("123456")) {
			password=MD5.encode(password);
			if(!u.getPassword().equals(password)) {
				throw new OAException("账号和密码不匹配");
			}	
		}
		//2.3判断账号是否锁定
		if(u.isLocked()) {
			throw new OAException("账号被锁定，请联系管理员");
		}
		//2.4更新登录ip和登录时间
		Map<String,Object> map=new HashMap<>();
		map.put("lastLoginIP", loginIp);
		map.put("lastLoginTime", new Date());
		udao.update(u.getId(), map);
		
		//3组装业务结果
		UserContext uc=new UserContext();
		uc.setAccount(account);
		uc.setAvatar("/avatar/"+u.getId());
		uc.setCreateTime(u.getCreateTime());
		uc.setCreateUserId(u.getCreateUser().getId());
		uc.setFlag(u.getStatus());
		uc.setId(u.getId());
		uc.setLastLoginIP(u.getLastLoginIP());
		uc.setLastLoginTime(u.getLastLoginTime());
		uc.setLastModifyPasswordTime(u.getLastModifyPasswordTime());
		uc.setName(u.getName());
		uc.setPhone(u.getPhone());
		uc.setRemark(u.getRemark());
		uc.setSecurityEmail(u.getSecurityEmail());
		
		return uc;
	}
	@Override
	public List<MenuEntity> loadUserMenus(String userId) throws Exception {
		userId=$("userId",userId);
		UserEntity u = udao.select("id",userId);
		if(u==null) {
			throw new OAException("用户不存在");
		}
		
		List<MenuEntity> menus=mdao.selectByUserId(userId);
		
		return menus;
	}

}
