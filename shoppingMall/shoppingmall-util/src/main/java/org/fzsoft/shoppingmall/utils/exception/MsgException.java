package org.fzsoft.shoppingmall.utils.exception;

import org.fzsoft.shoppingmall.utils.base.exception.BusinessException;
import org.fzsoft.shoppingmall.utils.mvc.ResponseCode;

/**
 * Created by 12 on 2017/4/27.
 */
public class MsgException extends BusinessException {
    public MsgException(int code) {
        super(code);
    }

    public MsgException(int code, String msg) {
        super(code, msg);
    }


    @Override
    protected String errorMsg() {

        switch (code){
            case ResponseCode.MSG_NOT_EXSIT :
                return "消息不存在";

        }

        return super.errorMsg();
    }
}
