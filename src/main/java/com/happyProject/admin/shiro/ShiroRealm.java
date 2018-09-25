package com.happyProject.admin.shiro;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.happyProject.admin.model.RolePerm;
import com.happyProject.admin.model.TUser;
import com.happyProject.admin.model.UserRole;
import com.happyProject.admin.service.impl.RolePermServiceImpl;
import com.happyProject.admin.service.impl.TUserServiceImpl;
import com.happyProject.admin.service.impl.UserRoleServiceImpl;

public class ShiroRealm extends AuthorizingRealm {

	@Resource
	private TUserServiceImpl tUserServiceImpl;
	// 用户,角色
	@Resource
	private UserRoleServiceImpl userRoleServiceImpl;
	// 权限,角色
	@Resource
	private RolePermServiceImpl rolePermServiceImpl;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("权限配置");
		String username = (String) getAvailablePrincipal(principals);

		TUser login = tUserServiceImpl.login(username, null);

		if (login != null) {
			Map<String, Object> smap = new HashMap<String, Object>();
			smap.put("user_id", login.getId());
			List<UserRole> findByMap = userRoleServiceImpl.findByMap(-1, -1, smap);
			if (findByMap != null && findByMap.size() > 0) {
				UserRole userRole = (UserRole) findByMap.get(0);
				// 角色
				Map<String, Object> rpMap = new HashMap<String, Object>();
				rpMap.put("role_id", userRole.getRole_id());
				List<RolePerm> rolePermList = rolePermServiceImpl.findByMap(-1, -1, rpMap);
				// 存放权限集合
				List<String> permissionsList = new ArrayList<String>();
				if (rolePermList != null && rolePermList.size() > 0) {
					for (Object object : rolePermList) {
						RolePerm rolePerm = (RolePerm) object;
						String id = rolePerm.getPerm_id();
						permissionsList.add(id);
					}
				}

				if (permissionsList.size() > 0) {
					// shiro权限自带的类
					SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
					// 授权
					authorizationInfo.addStringPermissions(permissionsList);
					// 此shiro对象在整个系统中都能获取到相对应的权限
					return authorizationInfo;
				}
			}
		}
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken)
			throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		// 从令牌中将用户名取出
		String username = token.getUsername();
		// 从令牌中将密码取出
		String password = new String(token.getPassword());
		TUser tUser = tUserServiceImpl.login(username, password);
		//System.out.println("t:" + tUser);
		/*if (tUser.getStatus() == 1 && tUser != null) {
			return (AuthenticationInfo) new DisabledAccountException();
		}*/
		if (tUser != null && tUser.getUser_name() != null) {
			// 下一步，则将从数据库种取出来的对象进行存储，
			// 存储到Shiro的框架系统中
			// 只要是当前回话不结束
			// 在系统的任何一个地方都能获取到用户名以及密码
			// (注意，当前用户名以及密码存放在shiro中，跟数据库无关)
			AuthenticationInfo authcInfo = new SimpleAccount(tUser.getUser_name(), tUser.getPassword(), getName());

			return authcInfo;
		}
		return null;
	}

	/**
	 * 更新用户授权信息缓存.
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
		super.clearCachedAuthorizationInfo(principals);
	}

	/**
	 * 更新用户信息缓存.
	 */
	public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
		super.clearCachedAuthenticationInfo(principals);
	}

	/**
	 * 清除用户授权信息缓存.
	 */
	public void clearAllCachedAuthorizationInfo() {
		getAuthorizationCache().clear();
	}

	/**
	 * 清除用户信息缓存.
	 */
	public void clearAllCachedAuthenticationInfo() {
		getAuthenticationCache().clear();
	}

	/**
	 * 清空所有缓存
	 */
	public void clearCache(PrincipalCollection principals) {
		super.clearCache(principals);
	}

	/**
	 * 清空所有认证缓存
	 */
	public void clearAllCache() {
		clearAllCachedAuthenticationInfo();
		clearAllCachedAuthorizationInfo();
	}

	/**
	 * 清理权限缓存
	 */
	public void clearCachedAuthorization() {
		// 清空权限缓存
		clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
	}

}
