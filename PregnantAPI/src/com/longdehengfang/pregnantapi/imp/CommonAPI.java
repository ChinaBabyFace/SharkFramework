/**
 * WeightAPI.java 2015-1-7
 * 
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 *
 */
package com.longdehengfang.pregnantapi.imp;

import android.content.Context;

import com.longdehengfang.pregnantapi.base.BaseAPI;
import com.soaring.io.http.auth.IUserAgent;
import com.soaring.io.http.net.RequestListener;
import com.soaring.io.http.net.SoaringParam;

/**
 * <b>CommonAPI。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-7 下午3:07:51</td><td>建立类型</td></tr>
 * 
 * </table>
 * @version 1.0
 * @author Renyuxiang
 * @since 1.0
 */
public class CommonAPI
		extends BaseAPI {

	/**GET_LOGIN。*/
	private static final String GET_LOGIN_URL = API_SERVER + "/Login";
	/**GET_REGISTER_URL。*/
	private static final String POST_REGISTER_URL = API_SERVER + "/Register";
	/**GET_CITIES*/
	private static final String GET_CITIES_URL = API_SERVER + "/Cities";
	/**ProvinceVersion*/
	private static final String GET_CITIE_VERSION_URL = API_SERVER + "/ProvinceVersion";
	/**PUT_SELECT_STATE_URL用户状态选择。*/
	private static final String PUT_SELECT_STATE_URL = API_SERVER + "/PregnancyStatus";
	/**GET_MOBILEEXISTS_URL手机号是否存在。*/
	private static final String GET_MOBILEEXISTS_URL = API_SERVER + "/MobileExists";
	/**PUT_VALIDATEMOBILE_URL重设密码。*/
	private static final String PUT_VALIDATEMOBILE_URL = API_SERVER + "/ValidateMobile";
	/**PUT_PASSWORD_URL。修改密码*/
	private static final String PUT_PASSWORD_URL = API_SERVER + "/Password";
	/**POST_PREGNANCIES_URL。提交预产期*/
	private static final String POST_PREGNANCIES_URL = API_SERVER + "/Pregnancies";
	/**第三方平台一键登陆后用户信息上传注册接口。*/
	private static final String POST_THIRD_PART_LOGIN = API_SERVER + "/ThirdPartLogin";
	private static final String POST_NOTIFICATION = API_SERVER + "/Notification";
	/**拉取广告数据。*/
	private static final String GET_AD = API_SERVER + "/Ads";
	private static final String GET_USERSET=API_SERVER+"/UserSet";

//	private static final String DEMO_LOGIN_URL = "http://192.168.10.15:8090/PregnantAPI/v2.0/Login";
//	private static final String DEMO_RIGISTER_URL = "http://192.168.10.15:8090/PregnantAPI/v2.0/Register";

	public CommonAPI(Context context, IUserAgent userAgent) {
		super(context, userAgent);
	}

	public void login(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "UserName", "PassWord","DevType","UserType" };
//        {"UserType":"member","Password":"$2a$10$VaqiwfNUq1UBMzfhYWVzBu.b6LjEr7HTgLc1KA7eby70KzYYkfaXC","DevType":"android","Username":"18232596161"}
//        {"MemberId":"e3fb6e43-0a9d-11e5-a1d8-00163e005349","NickName":"啦佛教","Username":"18232596161","Age":45,"Mobile":"18232596161","Email":"fhhbvg@qq.com",
//                "InfoIntegrity":100,"HasAnaphylactogen":true,"Anaphylactogen":"过敏","HasAvoidCertainFood":true,"AvoidCertainFood":"忌口","PregnancyStatus":2,"Height":168,
// "LMP":"2015-10-15 00:00:00","EDC":"2016-07-21 00:00:00","PrePregnancyWeight":0,"PortraitUrl":"http://7xixd7.com2.z0.glb.clouddn.com/71cf76f0-2145-11e5-82b6-6d4efb65f22d","CityId":"63","PregnantDate":"2015-11-18 00:00:00"}}
        requestAsync(GET_LOGIN_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}

	public void getCities(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "Version" };
//        {"Version":1}
//        [{"ProvinceId":"1","ProvinceName":"北京","ProvinceVersion":1,"CityList":[{"CityId":"1","ProvinceId":"1","CityName":"北京"}]}]
		requestAsync(GET_CITIES_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}

	public void getCityVersion(SoaringParam param, RequestListener requestListener) {
//        {"sortType":1,"id":0,"sortMode":1,"Version":0,"PageIndex":1,"PageSize":25}
//        {"meta":null,"data":{"Version":1}}
		requestAsync(GET_CITIE_VERSION_URL, param, HTTPMETHOD_GET, requestListener);
	}

	public void register(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "Mobile", "PassWord", "SourceType","DevType","UserType" };
//        {"UserType":"member","SourceType":1,"Password":"$2a$10$VaqiwfNUq1UBMzfhYWVzBu.b6LjEr7HTgLc1KA7eby70KzYYkfaXC","DevType":"android","Mobile":"13116016526"}
//         {"MemberId":"575fcfae-add1-11e5-b987-00163e005349","NickName":"完美孕程","Username":"13116016526","Age":null,"Mobile":"13116016526","Email":null,"InfoIntegrity":40,
//                 "HasAnaphylactogen":false,"Anaphylactogen":null,"HasAvoidCertainFood":false,"AvoidCertainFood":null,"Height":null,"LMP":null,"EDC":null,"PortraitUrl":null,"CityId":null}

        requestAsync(POST_REGISTER_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}

//	public String register(SoaringParam param) throws SoaringException {
//		String[] keyArray = { "Mobile", "PassWord", "SourceType" };
//		return requestSync(POST_REGISTER_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_POST);
//	}

	public void selectstate(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "PregnancyStatus","PrePregnancyWeight","PregnantDate" };
//        {"PrePregnancyWeight":50000,"PregnancyStatus":"1","PregnantDate":"2015-11-18"}
//        {"MemberId":"e3fb6e43-0a9d-11e5-a1d8-00163e005349","HasAnaphylactogen":false,"HasAvoidCertainFood":false,"PregnancyStatus":1}
		requestAsync(PUT_SELECT_STATE_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_PUT, requestListener);
	}

	public void mobileexists(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "MobileNum" };
//        {"MobileNum":"13116016526"}
//        {"effectiveUserCount":2}
		requestAsync(GET_MOBILEEXISTS_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}

	public void validateMobile(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "Npw", "MobileNum" ,"MobileType","CheckNum"};
//        {"CheckNum":"9505","MobileType":2,"MobileNum":"13116016526","Npw":"$2a$10$VaqiwfNUq1UBMzfhYWVzBu.b6LjEr7HTgLc1KA7eby70KzYYkfaXC"}
//        {"meta":null,"data":null}
        requestAsync(PUT_VALIDATEMOBILE_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_PUT, requestListener);
	}

	public void editPassword(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "NewPassword", "OldPassword" };
//        {"NewPassword":"$2a$10$VaqiwfNUq1UBMzfhYWVzBu.b6LjEr7HTgLc1KA7eby70KzYYkfaXC","OldPassword":"$2a$10$VaqiwfNUq1UBMzfhYWVzBu.b6LjEr7HTgLc1KA7eby70KzYYkfaXC"}
//        {"meta":null,"data":null}
        requestAsync(PUT_PASSWORD_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_PUT, requestListener);
	}

	public void walk(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "SourceType","DevType","UserType" };
//        {"UserType":"member","SourceType":0,"DevType":"android"}
//         {"MemberId":"48e9a7e5-adcf-11e5-b987-00163e005349","NickName":"游客","Username":"Preg2015-12-29 09:55:49","Age":null,"Mobile":null,"Email":null,"InfoIntegrity":30,"HasAnaphylactogen":false,"Anaphylactogen":null,"HasAvoidCertainFood":false,"AvoidCertainFood":null,"Height":null,"LMP":null,"EDC":null,"PortraitUrl":null,"CityId":null}

        requestAsync(POST_REGISTER_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}

	public void expectBirth(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "EDC", "LMP", "PrePregnancyWeight" };
//        {"PrePregnancyWeight":50000,"EDC":"2016-07-21","LMP":"2015-10-15"}
//        {"LMP":"2015-10-15","EDC":"2016-07-21","PrePregnancyWeight":50000}
		requestAsync(POST_PREGNANCIES_URL, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}

	public void postUserInfoFromThirdPart(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "ThirdPartId", "Name", "PortraitUrl", "SourceData", "SourceType" ,"DevType","UserType"};
//        {"UserType":"member","Name":"鵬噝灬莪","SourceType":4,"SourceData":"{\"is_yellow_year_vip\":\"0\",\"vip\":\"0\",\"level\":\"0\",\"yellow_vip_level\":\"0\",\"province\":\"山西\",\"gender\":\"男\",\"is_yellow_vip\":\"0\",\"screen_name\":\"鵬噝灬莪\",\"profile_image_url\":\"http:\\\/\\\/q.qlogo.cn\\\/qqapp\\\/1104536398\\\/DE54359025AA39DEF96FF2CDB0EB8AD6\\\/100\",\"msg\":\"\",\"city\":\"临汾\"}","PortraitUrl":"http:\/\/q.qlogo.cn\/qqapp\/1104536398\/DE54359025AA39DEF96FF2CDB0EB8AD6\/100","DevType":"android","ThirdPartId":"DE54359025AA39DEF96FF2CDB0EB8AD6"}
//         {"MemberId":"c4a49750-adce-11e5-b987-00163e005349","NickName":"鵬噝灬莪","Username":"鵬噝灬莪","Password":"000000","Age":null,"Sex":2,"Mobile":null,"Email":null,"InfoIntegrity":30,"Nation":null,"UserLevel":0,"HasAnaphylactogen":false,"Anaphylactogen":null,"HasAvoidCertainFood":false,"AvoidCertainFood":null,"PregnancyStatus":0,"Height":null,"SelfTestCount":0,"LMP":null,"EDC":null,"PrePregnancyWeight":null,"PortraitUrl":"http://q.qlogo.cn/qqapp/1104536398/DE54359025AA39DEF96FF2CDB0EB8AD6/100","ActivePoint":0,"CityId":null,"PregnantDate":null}}

        requestAsync(POST_THIRD_PART_LOGIN, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}
	public void postNotification(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "IsReceive"};
//        {"IsReceive":true}
//        {"meta":null,"data":null}
		requestAsync(POST_NOTIFICATION, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}
	public void getUserSet(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "SettingName"};
//        {"SettingName":"PregnantTools"}
//        {"UserSettingId":"0a05bea4-a7c9-11e5-b987-00163e005349","MemberId":"e3fb6e43-0a9d-11e5-a1d8-00163e005349","SettingName":"PregnantTools",
//       "Detail":"[{\"GroupName\":\"孕期工具\",\"GroupId\":\"1\",\"GroupDetail\":[{\"state\":false,\"id\":\"0\",\"name\":\"体重管理\"},"CreateTime":"2015-12-21 17:56:00","ModifyTime":"2015-12-29 09:34:22"}}

        requestAsync(GET_USERSET, removeInvalidParam(param, keyArray), HTTPMETHOD_GET, requestListener);
	}
	public void postUserSet(SoaringParam param, RequestListener requestListener) {
		String[] keyArray = { "SettingName","Detail"};
//        {"SettingName":"PregnantTools","Detail":"[{\"GroupName\":\"孕期工具\",\"GroupId\":\"1\",\"GroupDetail\":[{\"state\":false,\"id\":\"0\",\"name\":\"体重管理\"}]}]"}
//        {"meta":null,"data":null}
        requestAsync(GET_USERSET, removeInvalidParam(param, keyArray), HTTPMETHOD_POST, requestListener);
	}

	public void getAd( RequestListener requestListener) {
//        Request Param:{}
//        {"AdvertisementId":"a3579764-f474-11e4-9791-000c29192b61","ShowTime":5,"ActionUrl":"","IsActived":1,"ImageUrl":"http://7xixda.com2.z0.glb.clouddn.com/1080_1920_1435304740972.jpg"}
		requestAsync(GET_AD, new SoaringParam(), HTTPMETHOD_GET, requestListener);
	}

}
