package org.fzsoft.shoppingmall.utils.tcp.mvc.internal;

import com.alibaba.fastjson.JSONObject;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

/**
 * Created by yhj on 17/4/8.
 */
public class RequestWrapper {
    private String requestBody;
    private JSONObject requestJson;

    public RequestWrapper(String requestBody) {
        this.requestBody = requestBody;
        requestJson = JSONObject.parseObject(requestBody);
    }

    public int getLengthForInt() {
        return requestBody.length();
    }

    public long getLengthForLong() {
        return requestBody.length();
    }

    public String getParameter(String key) {
        return requestJson.getString(key);
    }

    public Enumeration<String> getParameterNames() {

        return new CollectionEnumeration<String>(requestJson.keySet());
    }

    public String[] getParameterValues(String name) {

        return new String[]{getParameter(name)};
    }





    class CollectionEnumeration<T> implements Enumeration<T> {

        Iterator<T> iterator;

        CollectionEnumeration(Collection<T> collection) {
            iterator = collection.iterator();
        }

        @Override
        public boolean hasMoreElements() {
            return iterator.hasNext();
        }

        @Override
        public T nextElement() {
            return iterator.next();
        }
    }
}
