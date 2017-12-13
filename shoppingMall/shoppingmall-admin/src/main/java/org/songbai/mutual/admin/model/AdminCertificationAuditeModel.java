package org.songbai.mutual.admin.model;

import java.io.Serializable;
import java.util.Date;


/**
 * 实名审核
 * @author wangmeishu
 * @date 2017年4月18日下午1:59:45
 */
public class AdminCertificationAuditeModel implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -897549463898200755L;
	
	private Integer id;
	private Integer userId;
	
	private String nickName;
	private String realName;
	private String certCode;
	private String userPhone;
	//审核人
	private String auditer;
	//实名状态 未审核0、审核通过1、审核未通过2
	private Integer status;
	private String certBack;
	private String certPositive;
	//申请时间
	private Date createTime;
	//审核时间
	private Date auditeTime;
	private String remark;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getCertCode() {
		return certCode;
	}
	public void setCertCode(String certCode) {
		this.certCode = certCode;
	}
	
	
	
	public String getCertBack() {
		return certBack;
	}
	public void setCertBack(String certBack) {
		this.certBack = certBack;
	}
	public String getCertPositive() {
		return certPositive;
	}
	public void setCertPositive(String certPositive) {
		this.certPositive = certPositive;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getAuditer() {
		return auditer;
	}
	public void setAuditer(String auditer) {
		this.auditer = auditer;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getAuditeTime() {
		return auditeTime;
	}
	public void setAuditeTime(Date auditeTime) {
		this.auditeTime = auditeTime;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Override
	public String toString() {
		return "AdminCertificationAuditeModel [id=" + id + ", userId=" + userId + ", nickName=" + nickName
				+ ", realName=" + realName + ", certCode=" + certCode + ", userPhone=" + userPhone + ", auditer="
				+ auditer + ", status=" + status + ", certBack=" + certBack + ", certPositive=" + certPositive
				+ ", createTime=" + createTime + ", auditeTime=" + auditeTime + "]";
	}
	
	
	
	
	

}
