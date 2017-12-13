package org.fzsoft.shoppingmall.utils.exception;

import org.fzsoft.shoppingmall.utils.base.exception.BusinessException;

/**
 * Created by 12 on 2017/4/26.
 */
public class QuotaExcetion extends BusinessException {
    public QuotaExcetion(int code) {
        super(code);
    }

    public QuotaExcetion(int code, String msg) {
        super(code, msg);
    }
}
