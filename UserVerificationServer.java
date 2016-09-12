package com.homelink.storein.common;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;

import com.homelink.storein.pojo.Employees;


/**
 * ldap 用户验证服务类
 * 
 * @author homelink
 *
 */
public class UserVerificationServer {

	// @Value(value = "${ldap.base}")
	private static final String BASE = "OU=北京链家,dc=corp,dc=homelink,dc=com,dc=cn";
	// @Value(value = "${ldap.url}")
	private static final String URL = "ldap://ldap.homelink.com.cn:389";
	// @Value(value = "${ldap.referral}")
	private static final String REFERRAL = "follow";
	// @Value(value = "${ldap.username}")
	private static final String USERNAME = "ldap";
	// @Value(value = "${ldap.password}")
	private static final String PASSWORD = "homelink";

	private static final String ATTRIBUTE_NAME = "distinguishedname";
	private static final String TYPE = "pager";// 工号
	
	/**
	 * 
	 * @Title: validate
	 * @Description: 登录验证
	 * @param @param dn 系统号
	 * @param @param password 密码
	 * @param @param type 验证条件
	 * @param @return 用户信息
	 * @param @throws Exception 设定文件
	 * @return boolean 返回类型
	 * @throws
	 */
	public static Employees validate(String dn, String password) {
		SearchControls sc = new SearchControls();
		InitialDirContext initialContext1 = null;
		InitialDirContext initialContext = null;
		try {
			sc.setSearchScope(2);
			initialContext = getInitialContext();
			NamingEnumeration<SearchResult> search = initialContext.search(
					BASE, TYPE + "=" + dn, sc);
			if (search.hasMoreElements()) {
				SearchResult next = search.next();
				Attributes attributes = next.getAttributes();
				String attribute_dn = (String) attributes.get(ATTRIBUTE_NAME)
						.get();
				if (attribute_dn == null) {
					return null;
				}
				Hashtable<String, String> env = new Hashtable<String, String>();
				env.put(Context.INITIAL_CONTEXT_FACTORY,
						"com.sun.jndi.ldap.LdapCtxFactory");
				env.put(Context.SECURITY_AUTHENTICATION, "simple");
				env.put(Context.PROVIDER_URL, URL);
				env.put(Context.SECURITY_PRINCIPAL, attribute_dn);
				env.put(Context.SECURITY_CREDENTIALS, password);
				env.put(Context.REFERRAL, REFERRAL);

				initialContext1 = new InitialDirContext(env);
				if (initialContext1 != null) {
					// 用户信息
					String pager = (String) attributes.get("pager").get();
					String mail = (String) attributes.get("mail").get();
					String name = (String) attributes.get("name").get();
					String title = (String) attributes.get("title").get();
					String department = (String) attributes.get("department")
							.get();
					String sAMAccountName = (String) attributes.get(
							"sAMAccountName").get();
					//经纪人手机号码
					String mobile ="";
					String attributeStr=attributes.toString();
					if(attributeStr.indexOf("mobile")>0){
						mobile= (String) attributes.get("mobile").get();
					}
					if(attributeStr.indexOf("telephoneNumber")>0){
						mobile= (String) attributes.get("telephoneNumber").get();
					}
					Employees employees=new Employees();
					employees.setPager(pager);
					employees.setMail(mail);
					employees.setName(name);
					employees.setTitle(title);
					employees.setDepartment(department);
					employees.setSAMAccountName(sAMAccountName);
					employees.setMobile(mobile);
					return employees;
				}
			}
		} catch (Exception e) {
			return null;
		} finally {
			try {
				if (initialContext != null) {
					initialContext.close();
				}
				if (initialContext1 != null) {
					initialContext1.close();
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 连接ldap
	 * 
	 * @return
	 * @throws Exception
	 */
	private static InitialDirContext getInitialContext() throws Exception {
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, URL);
		env.put(Context.REFERRAL, REFERRAL);
		env.put(Context.SECURITY_PRINCIPAL, USERNAME);
		env.put(Context.SECURITY_CREDENTIALS, PASSWORD);
		return new InitialLdapContext(env, null);
	}
	
	/**
	 * 只验证系统号,不验证密码
	 * @param dn
	 * @return
	 */
	public static Employees validatePager(String dn){
		SearchControls sc = new SearchControls();
		InitialDirContext initialContext = null;
		try{
			sc.setSearchScope(2);
			initialContext = getInitialContext();
			NamingEnumeration<SearchResult> search = initialContext.search(
					BASE, TYPE + "=" + dn, sc);
			if (search.hasMoreElements()){
				SearchResult next = search.next();
				Attributes attributes = next.getAttributes();
				String attribute_dn = (String) attributes.get(ATTRIBUTE_NAME)
						.get();
				if (attribute_dn == null) {
					return null;
				}
				// 用户信息
				String pager = (String) attributes.get("pager").get();
				String mail = (String) attributes.get("mail").get();
				String name = (String) attributes.get("name").get();
				String title = (String) attributes.get("title").get();
				String department = (String) attributes.get("department")
						.get();
				String sAMAccountName = (String) attributes.get(
						"sAMAccountName").get();
				//经纪人手机号码
				String mobile ="";
				String attributeStr=attributes.toString();
				if(attributeStr.indexOf("mobile")>0){
					mobile= (String) attributes.get("mobile").get();
				}
				if(attributeStr.indexOf("telephoneNumber")>0){
					mobile= (String) attributes.get("telephoneNumber").get();
				}
				Employees employees=new Employees();
				employees.setPager(pager);
				employees.setMail(mail);
				employees.setName(name);
				employees.setTitle(title);
				employees.setDepartment(department);
				employees.setSAMAccountName(sAMAccountName);
				employees.setMobile(mobile);
				return employees;
			}
			return null;
		}catch(Exception ex){
			ex.printStackTrace();
			return null;
		}finally{
			try {
				if(initialContext != null){
					initialContext.close();
				}
			} catch (NamingException e) {
				e.printStackTrace();
			}
		}
	}

}