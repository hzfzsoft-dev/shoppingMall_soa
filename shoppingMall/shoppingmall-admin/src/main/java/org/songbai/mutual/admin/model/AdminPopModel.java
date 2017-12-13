
package org.songbai.mutual.admin.model;

public class AdminPopModel extends AdminSecurityResourceModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7328299347981137537L;

	public static final String CATEGORY = "POP_UP";

	public AdminPopModel() {
		this.setCategory(CATEGORY);
	}

	/**
	 * 资源标识
	 */
	private String identifier;

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
