package org.fzsoft.shoppingmall.utils.distributelock;

import java.util.concurrent.TimeUnit;

/**
 * 不需要实现lock接口中的多数功能，加锁与解锁足够，但是一定要保证可重入性
 * @author Boyce
 * 2016年5月19日 下午3:53:28
 */
public interface DistributeLock {
	
	public void lock();
	public void unlock();
	public boolean acquireLock(long time, TimeUnit unit);
}