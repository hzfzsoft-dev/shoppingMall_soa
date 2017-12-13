package org.fzsoft.shoppingmall.utils.distributelock;


import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;

import java.util.concurrent.TimeUnit;

/**
 * @author Boyce
 * 2016年6月29日 上午10:25:05 
 */
public class CuratorLock implements DistributeLock{
	InterProcessMutex lock;
	public CuratorLock(CuratorFramework client,String monitor) {
		lock=new InterProcessMutex(client, monitor);
	}
	
	
	public void lock() {
		try {
			lock.acquire();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void unlock() {
		try {
			lock.release();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean acquireLock(long time, TimeUnit unit) {
		try {
			return lock.acquire(time, unit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
