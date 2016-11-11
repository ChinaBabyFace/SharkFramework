/**
 * OnShareClickListener.java 2015-1-10
 * <p>
 * 天津云翔联动科技有限公司(c) 1995 - 2015 。
 * http://www.soaring-cloud.com.cn
 */
package com.soaring.umeng.share;

import com.umeng.socialize.bean.SHARE_MEDIA;


/**
 * <b>OnShareClickListener。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * <p><b>修改列表：</b></p>
 * <table width="100%" cellSpacing=1 cellPadding=3 border=1>
 * <tr bgcolor="#CCCCFF"><td>序号</td><td>作者</td><td>修改日期</td><td>修改内容</td></tr>
 * <!-- 在此添加修改列表，参考第一行内容 -->
 * <tr><td>1</td><td>Renyuxiang</td><td>2015-1-10 下午10:08:17</td><td>建立类型</td></tr>
 * <p>
 * </table>
 *
 * @author Renyuxiang
 * @version 1.0
 * @since 1.0
 */
public interface OnShareClickListener {

    void onClick(SHARE_MEDIA platform);
}
