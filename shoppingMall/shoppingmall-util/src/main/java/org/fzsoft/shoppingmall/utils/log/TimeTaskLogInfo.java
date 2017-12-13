package org.fzsoft.shoppingmall.utils.log;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 系统定时任务执行日志
 * @author wangd
 *
 */
public class TimeTaskLogInfo implements Serializable{

    private static final long serialVersionUID = 3251120302816098821L;
    private Integer taskId;
    private Timestamp executionTime;
    private Boolean isSucess;
    private Boolean hasException;
    private Boolean sleepDead;
    public Integer getTaskId() {
        return taskId;
    }
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }
    public Timestamp getExecutionTime() {
        return executionTime;
    }
    public void setExecutionTime(Timestamp executionTime) {
        this.executionTime = executionTime;
    }
    public Boolean getIsSucess() {
        return isSucess;
    }
    public void setIsSucess(Boolean isSucess) {
        this.isSucess = isSucess;
    }
    public Boolean getHasException() {
        return hasException;
    }
    public void setHasException(Boolean hasException) {
        this.hasException = hasException;
    }
	/**
	 * @return the sleepDead
	 */
	public Boolean getSleepDead() {
		return sleepDead;
	}
	/**
	 * @param sleepDead the sleepDead to set
	 */
	public void setSleepDead(Boolean sleepDead) {
		this.sleepDead = sleepDead;
	}
    
}
