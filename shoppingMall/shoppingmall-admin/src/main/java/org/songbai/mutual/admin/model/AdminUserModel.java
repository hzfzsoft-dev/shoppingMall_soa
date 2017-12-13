package org.songbai.mutual.admin.model;

import org.songbai.mutual.utils.encrypt.PasswordEncryptUtil;

/**
 * 
 * @author SUNDONG_ 用户 与系统交互的人，是使用软件的人 系统的登录 可以对用户授予角色{@link AdminRoleModel}、权限
 *         {@link AdminPerssionModel}
 */
public class AdminUserModel extends AdminActorModel {

	private static final long serialVersionUID = -6471062676097305807L;
	/**
	 * 默认初始密码，也不建议修改
	 */
	private static final String INIT_PASSWORD = "888888";
	public static final String CATEGORY = "USER";
	public static final String USERACCOUNT = "admin";
	/**
	 * 加密言，不要修改这个值了，除非你想重新生成所有的用户
	 */
	private static final String SALT = "asldkjalksdjalkjcvxzlka.,das.,dmqwlkeaicsk";

	/**
	 * 生成直播老师账户时，默认有一个liveType=1
	 */
	public static final Integer TEACHERTYPE = 1;
	/**
	 * 用户登录密码，初次创建时可以使用默认密码。 注意以后修改用户时该值不能随便的修改了用户的密码
	 * 在修改用户信息时或的用户对象的SQL中不应查询用户的密码
	 */
	private String password = INIT_PASSWORD;

	/**
	 * 加密盐值
	 */
	private String salt;

	/**
	 * 关联员工名称，Actor中无对应字段，
	 */
	private String employeeName;

	private Integer level;// 登陆用户的水平

	private Integer roleType;
	/**
	 * 弹窗提醒，如果为1，则表示无需弹窗
	 */
	private Integer remindType;
	/**
	 * 用户输入密码错误次数
	 */
	private Integer passEncryptTimes;

	/**
	 * 账户限制时间
	 */
	private Long accountLimitTime;

	private Integer resourceType;
	
	/**
	 * 代理关联码
	 */
	private String agencyRelationCode;

	public AdminUserModel() {
		super();
		this.setCategory(CATEGORY);
	}

	public Integer getRemindType() {
		return remindType;
	}

	public void setRemindType(Integer remindType) {
		this.remindType = remindType;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * 返回用户的默认密码 不需要再次加密直接保存到数据库的密码
	 * 
	 * @return
	 */
	public String getDefaultPassword() {

		String temp = PasswordEncryptUtil.digest(INIT_PASSWORD, "", 0);

		return PasswordEncryptUtil.digest(temp + "/" + SALT, "", 0);
	}

	/**
	 * 用户登录密码的二次处理
	 * 
	 * @param password
	 * @return
	 */
	public String handlePassword(String password) {

		return PasswordEncryptUtil.digest(password + "/" + SALT, "", 0);
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}


	public Integer getRoleType() {
		return roleType;
	}

	public void setRoleType(Integer roleType) {
		this.roleType = roleType;
	}

	public Integer getPassEncryptTimes() {
		return passEncryptTimes;
	}

	public void setPassEncryptTimes(Integer passEncryptTimes) {
		this.passEncryptTimes = passEncryptTimes;
	}

	public Long getAccountLimitTime() {
		return accountLimitTime;
	}

	public void setAccountLimitTime(Long accountLimitTime) {
		this.accountLimitTime = accountLimitTime;
	}

	public Integer getResourceType() {
		return resourceType;
	}

	public void setResourceType(Integer resourceType) {
		this.resourceType = resourceType;
	}

	public String getAgencyRelationCode() {
		return agencyRelationCode;
	}

	public void setAgencyRelationCode(String agencyRelationCode) {
		this.agencyRelationCode = agencyRelationCode;
	}

}
