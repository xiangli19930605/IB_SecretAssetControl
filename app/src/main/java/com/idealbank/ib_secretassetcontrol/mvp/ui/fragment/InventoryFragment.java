package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idealbank.ib_secretassetcontrol.Netty.ChannelMap;
import com.idealbank.ib_secretassetcontrol.Netty.bean.Message;
import com.idealbank.ib_secretassetcontrol.Netty.bean.MsgType;
import com.idealbank.ib_secretassetcontrol.app.AppApplication;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.bean.Upload;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerInventoryComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.InventoryContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.InventoryPresenter;

import com.idealbank.ib_secretassetcontrol.R;

import butterknife.OnClick;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseFragment;
import me.jessyan.armscomponent.commonsdk.utils.GsonUtil;
import me.jessyan.autosize.utils.LogUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;
import static me.jessyan.armscomponent.commonsdk.utils.ToastUtil.showToast;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/10/2019 16:06
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class InventoryFragment extends BaseFragment<InventoryPresenter> implements InventoryContract.View {

    public static InventoryFragment newInstance() {
        InventoryFragment fragment = new InventoryFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerInventoryComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inventory, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.btn_received, R.id.btn_send, R.id.btn_upload,R.id.public_toolbar_right})
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btn_received:
                if (ChannelMap.getChannel("1") != null) {
                    Message message = new Message();
                    message.setId(1);
                    message.setType(MsgType.DOWNLOAD);
                    message.setResponseMessage("");
                    ChannelMap.getChannel("1").writeAndFlush(new TextWebSocketFrame(GsonUtil.GsonString(message)));
                } else {
                    showToast("未连接");
                }
                break;
            case R.id.btn_send:
                if (ChannelMap.getChannel("1") != null) {
                    Message message = new Message();
                    message.setId(1);
                    message.setType(MsgType.RFID);
                    message.setResponseMessage("");

                    ChannelMap.getChannel("1").writeAndFlush(new TextWebSocketFrame(GsonUtil.GsonString(message)));
                } else {
                    showToast("未连接");
                }
                break;
            case R.id.btn_upload:
                if (ChannelMap.getChannel("1") != null) {
                    Message message = new Message();
                    message.setId(1);
                    message.setType(MsgType.UPLOADDATA);

                    Upload upload=  new Upload();
                    upload.setCheck(new DbManager().queryTaskBeanWhereID("1233"));
                    upload.setCheckAssets(new   DbManager().queryAssetsBeanWhereId("1233") );
                    message.setResponseMessage(upload);
                    ChannelMap.getChannel("1").writeAndFlush(new TextWebSocketFrame(GsonUtil.GsonString(message)));
                } else {
                    showToast("未连接");
                }
                break;
            case R.id.public_toolbar_right:
                break;


        }
    }


}
