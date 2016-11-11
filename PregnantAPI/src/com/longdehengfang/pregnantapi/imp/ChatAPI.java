package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * Created by xunxiaojing on 2015/12/10.
 */
public class ChatAPI extends BaseAPI {

    /*搜索消息*/
    private static final String KEYWORD_ANSWER = API_SERVER + "/AutoReply";
    /*自动回复*/
    private static final String SEARCH_KEYWORD = API_SERVER + "/SearchMessage";
    /*修改阅读状态*/
    private static final String MESSAGES_STATUS=API_SERVER+"/ModifiedReadState";
    /*搜索详情*/
    private static final String MESSAGE_DETAIL=API_SERVER+"/SearchMessageDetail";
    /**
     * 构造函数，使用各个 API 接口提供的服务前必须先获取 Token。
     *
     * @param context
     * @param userAgent 访问令牌
     */
    public ChatAPI(Context context, IUserAgent userAgent) {
        super(context, userAgent);
    }

    //私人营养师关键字回复
    public void keywordAnswer(SoaringParam param, RequestListener requestListener) {
        String[] keyArray = {"keyWord"};
        requestAsync(KEYWORD_ANSWER, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
    }

    /*获取聊天信息*/
    public void getMessages(SoaringParam param, RequestListener requestListener) {
        String[] keyArray = {"flag", "pageSize", "time"};
        requestAsync(KEYWORD_ANSWER, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
    }

    /*搜索聊天记录*/
    public void getMessagesByKeyword(SoaringParam param, RequestListener requestListener) {
        String[] keyArray = {"KeyWord", "PageIndex", "PageSize"};
        requestAsync(SEARCH_KEYWORD, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
    }
    /*修改阅读状态*/
    public void changeStatus(SoaringParam param, RequestListener requestListener) {
        String[] keyArray = {"MessageIds"};
        requestAsync(MESSAGES_STATUS, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
    }
    /*搜索详情*/
    public void searchDetail(SoaringParam param,RequestListener requestListener){
        String[]keyArray={"MessageId","PageSize"};
        requestAsync(MESSAGE_DETAIL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
    }


}
