package org.fzsoft.shoppingmall.utils.exception;


import org.fzsoft.shoppingmall.utils.base.exception.BusinessException;

import static org.fzsoft.shoppingmall.utils.mvc.ResponseCode.USER_NOT_EXSIT;

/**
 * Created by 12 on 2017/3/16.
 */
public class UserException extends BusinessException {
    public UserException(int code) {
        super(code);
    }


    @Override
    protected String errorMsg() {
        switch (code) {
            case USER_NOT_EXSIT:
                return "用户不存在";
        }

        return super.errorMsg();
    }
}
