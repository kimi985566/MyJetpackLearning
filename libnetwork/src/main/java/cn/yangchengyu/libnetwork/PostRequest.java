package cn.yangchengyu.libnetwork;

import java.util.Map;

import okhttp3.FormBody;

/**
 * Desc  :
 * Author: Chengyu Yang
 * Date  : 2020/3/23
 */
public class PostRequest<T> extends Request<T, PostRequest> {

    public PostRequest(String url) {
        super(url);
    }

    @Override
    protected okhttp3.Request generateRequest(okhttp3.Request.Builder builder) {
        //post请求表单提交
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            bodyBuilder.add(entry.getKey(), String.valueOf(entry.getValue()));
        }
        return builder.url(mUrl).post(bodyBuilder.build()).build();
    }
}
