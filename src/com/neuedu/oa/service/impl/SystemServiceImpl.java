package com.neuedu.oa.service.impl;

import static com.neuedu.oa.validate.AssertUtil.$;
import static com.neuedu.oa.validate.AssertUtil.assertBetween;
import static com.neuedu.oa.validate.AssertUtil.assertFalse;
import static com.neuedu.oa.validate.AssertUtil.assertMatch;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.transaction.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.neuedu.oa.Application;
import com.neuedu.oa.ao.ImageAo;
import com.neuedu.oa.ao.QueryUserAo;
import com.neuedu.oa.ao.UserAo;
import com.neuedu.oa.dao.RoleDao;
import com.neuedu.oa.dao.UserDao;
import com.neuedu.oa.dto.QueryResult;
import com.neuedu.oa.dto.UserContext;
import com.neuedu.oa.entity.RoleEntity;
import com.neuedu.oa.entity.UserEntity;
import com.neuedu.oa.exception.OAException;
import com.neuedu.oa.service.SystemService;
import com.neuedu.oa.util.MD5;
import com.neuedu.oa.util.StringUtil;
import com.neuedu.oa.util.UUID;
@Service
public class SystemServiceImpl implements SystemService {
	@Autowired
	RoleDao rdao;
	@Autowired
	UserDao udao;
	@Override
	public List<RoleEntity> queryRole() throws Exception {
		//2执行业务逻辑
		List<RoleEntity> rows=rdao.loadAll(UserContext.getCurrentUserId());
		return rows;
	}
	@Override
	public QueryResult<UserEntity> queryUser(QueryUserAo ao) throws Exception {
		//1验证参数
		String key=StringUtil.blankAsNull(ao.getKey());
		int defaultPageSize=Application.getInt("sys.default-page-size", 10);
		
		int pageSize=StringUtil.parseInt(ao.getPageSize(),defaultPageSize);
		int pageNo=StringUtil.parseInt(ao.getPageNo(),1);
		Integer state=StringUtil.parseInt(ao.getState(),null);
		//2执行业务逻辑
		
		
		
		if(key!=null) {
			key="%"+key+"%";
		}
		
		PageHelper.startPage(pageNo, pageSize);
		List<UserEntity> rows=udao.selectByLike(key, state,UserContext.getCurrentUserId());
		
		PageInfo<UserEntity> page = new PageInfo<>(rows);
		//组装业务结果
		QueryResult<UserEntity> result=new QueryResult<UserEntity>();
		
		result.setPageNo(pageNo);
		result.setPageSize(pageSize);
		result.setRows(rows);
		result.setTotalRows((int)page.getTotal());
		
		return result;
	}
	@Override
	public UserEntity addUser(UserAo ao) throws Exception {
		//1获取清理参数
		String account=$("账号", ao.getAccount());
		String name=$("名字",ao.getName());
		String remark=ao.getRemark();
		//2执行业务逻辑
		//2.1账号
		assertBetween("账号", account, 6, 32);
		assertMatch("账号只能是字母，数字，下划线", account, "\\w+");
		assertFalse("账号已存在", udao.exists("account",account));
		//2.2名字
		assertMatch("名字必须是中文,且不能超过10个", name, "[\\u4e00-\\u9fa5]{2,10}");
		
		//2.3存入
		UserEntity u=new UserEntity();
		
		u.setAccount(account);
		u.setAvatar(JSON.toJSONString(ImageAo.getDefaultAvatarAo()));
		u.setCreateTime(new Date());
		
		u.setStatus(UserEntity.STATUS_NORMAL);
		
		u.setId(UUID.random32());

		u.setLastLoginIP(null);
		u.setLastModifyPasswordTime(null);
		u.setName(name);
		String defaultPassword=Application.getString("sys.default-new-user-password", "123456");
		u.setPassword(MD5.encode(defaultPassword));
		u.setPhone(null);
		
		u.setRemark(remark);
		u.setSecurityEmail(null);
		UserEntity createUser=new UserEntity();
		createUser.setId(UserContext.getCurrentUserId());
		u.setCreateUser(createUser);
		u.setLastLoginTime(null);
		
		
		udao.insert(u);
		
		
		//3组装业务结果
		
		return u;
	}

	@Override
	public void deleteUser(String id) throws Exception {
		id=$("id",id);
		//仅允许创建人删除
		UserEntity u=udao.select("id",id);
		String createUserId=u.getCreateUser().getId();
		String currentUserId=UserContext.getCurrentUserId();
		if(!currentUserId.equals(createUserId)) {
			throw new OAException("非法操作");
		}
		udao.delete(id);
		
	}

	@Override
	public UserEntity modifyUser(String id, UserAo ao) throws Exception {
		id=$("id",id);
		String name=StringUtil.trim(ao.getName());
		String account=StringUtil.trim(ao.getAccount());
		String remark=StringUtil.trim(ao.getRemark());
		UserEntity old=udao.select("id",id);
		if(old==null) {
			throw new OAException("数据不存在或已被删除");
		}
		//仅允许创建人删除
		String createUserId=old.getCreateUser().getId();
		String currentUserId=UserContext.getCurrentUserId();
		if(!currentUserId.equals(createUserId)) {
			throw new OAException("非法操作");
		}
		//依次来判断需要修改的属性
		Map<String,Object> needUpdateMap=new HashMap<>(4,1);
		//name
		if(name!=null) {
			if(!name.equals(old.getName())) {
				needUpdateMap.put("name",name);
				old.setName(name);
			}
		}
		//account
		if(account!=null) {
			if(!account.equals(old.getAccount())) {
				needUpdateMap.put("account",account);
				old.setAccount(account);
			}
		}
		//remark
		if(remark!=null) {
			if(!remark.equals(old.getRemark())) {
				needUpdateMap.put("remark",remark);
				old.setRemark(remark);
			}
		}
		if(!needUpdateMap.isEmpty()) {
			udao.update(id, needUpdateMap);
		}
		
		return old;
	}

	@Override
	public UserEntity loadUser(String id) throws Exception {
		id=$("id", id);
		UserEntity e=udao.select("id",id);
		
		if(e==null) {
			throw new OAException("数据不存在或已删除");
		}
		//仅允许创建人删除
		String createUserId=e.getCreateUser().getId();
		String currentUserId=UserContext.getCurrentUserId();
		if(!currentUserId.equals(createUserId)) {
			throw new OAException("非法操作");
		}
		//查询创建人
		UserEntity createUser=e.getCreateUser();
		if(createUser!=null) {
			createUser=udao.select("id",createUser.getId());
			e.setCreateUser(createUser);
		}
		return e;
	}

}
