package org.fzsoft.shoppingmall.utils.mvc;

public class Response {
    protected int code;
    protected String msg;

    protected Integer total;
    protected Integer page;
    protected Integer pageSize;
    protected Integer resultCount;

    public static class DataResponse<T> extends Response {
        private T data;

        public T getData() {
            return data;
        }

        @Override
        public String toString() {
            return super.toString()+ " DataResponse{" +
                    "data=" + data +
                    '}';
        }
    }


    public static Response success() {
        Response resp = new Response();
        resp.code = (ResponseCode.SUCCESS);
        resp.msg = (ResponseCode.SUCCESS_MSG);
        return resp;
    }

    public static Response successResponse(String msg) {
        Response resp = new Response();
        resp.code = ResponseCode.SUCCESS;
        resp.msg = msg;
        return resp;
    }

    public static Response error() {
        Response resp = new Response();
        resp.code = (ResponseCode.SERVER_ERROR);
        resp.msg = (ResponseCode.SERVER_ERROR_MSG);
        return resp;
    }

    public static Response errorResponse(String msg) {
        Response resp = new Response();
        resp.code = ResponseCode.SERVER_ERROR;
        resp.msg = msg;
        return resp;
    }

    public static Response response(int code, String msg) {
        Response resp = new Response();
        resp.code = (code);
        resp.msg = (msg);
        return resp;
    }

    public static <E> DataResponse<E> response(int code, String msg, E data) {
        DataResponse<E> resp = new DataResponse<E>();
        resp.code = (code);
        resp.msg = (msg);
        resp.data = data;
        return resp;
    }

    public static <E> DataResponse<E> success(E data) {
        DataResponse<E> resp = new DataResponse<E>();
        resp.code = (ResponseCode.SUCCESS);
        resp.msg = (ResponseCode.SUCCESS_MSG);
        resp.data = data;
        return resp;
    }

    public static <E> DataResponse<E> successPage(Page page) {


        DataResponse<E> resp = new DataResponse<>();
        resp.code = (ResponseCode.SUCCESS);
        resp.msg = (ResponseCode.SUCCESS_MSG);
        resp.data = (E) page.getData();
        resp.total =page.total;
        resp.page=page.getStart();
        resp.pageSize=page.getPageSize();
        resp.resultCount = page.resultCount;
        return resp;
    }

    public static <E> DataResponse<E> error(E data) {
        DataResponse<E> resp = new DataResponse<E>();
        resp.code = (ResponseCode.SERVER_ERROR);
        resp.msg = (ResponseCode.SERVER_ERROR_MSG);
        resp.data = data;
        return resp;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    
    public Integer getResultCount() {
		return resultCount;
	}

	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}

	@Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", total=" + total +
                ", page=" + page +
                ", pageSize=" + pageSize +
                '}';
    }
}
