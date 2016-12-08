/**
 * Copyright 2014  XCL-Charts
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 	
 * @Project XCL-Charts 
 * @Description Android图表基类库
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * @license http://www.apache.org/licenses/  Apache v2 License
 * @version 1.0
 */

package com.soaring.widget.chart.xclchart.event.zoom;


/**
 * @InterfaceName IChartZoom
 * @Description  用于放大缩小图表的接口
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com)
 * 
 */

public interface IChartZoom {
	
	
	  // 最大最小缩小比好像没啥意义，不实现了
	  // public void setMaxRate(float rate);
	  // public void setMinRate(float rate);
	 
	   void setZoomRate(float rate);
	   void zoomIn();
	   void zoomOut();

}
