/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.idealbank.ib_secretassetcontrol.mvp.ui.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.idealbank.ib_secretassetcontrol.R;
import com.idealbank.ib_secretassetcontrol.app.rfid_module.RfidData;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;

//import com.chad.library.adapter.base.BaseViewHolder;


/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>出勤统计 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class RFIDAdapter extends BaseQuickAdapter<RfidData, BaseViewHolder> {
    private AppComponent mAppComponent;
    private ImageLoader mImageLoader;//用于加载图片的管理类,默认使用 Glide,使用策略模式,可替换框架
    public RFIDAdapter() {
        super(R.layout.item_rv_rfid);
    }

    @Override
    protected void convert(BaseViewHolder helper, RfidData data) {

        helper.setText(R.id.tv_rfid,data.getTagid() );

    }
}