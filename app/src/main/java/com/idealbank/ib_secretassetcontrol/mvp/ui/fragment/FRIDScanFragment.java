package com.idealbank.ib_secretassetcontrol.mvp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.idealbank.ib_secretassetcontrol.app.rfid_module.ONDEVMESSAGE;
import com.idealbank.ib_secretassetcontrol.app.rfid_module.RfidData;
import com.idealbank.ib_secretassetcontrol.mvp.ui.adapter.RFIDAdapter;
import com.jess.arms.base.BaseFragment;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import com.idealbank.ib_secretassetcontrol.di.component.DaggerFRIDScanComponent;
import com.idealbank.ib_secretassetcontrol.mvp.contract.FRIDScanContract;
import com.idealbank.ib_secretassetcontrol.mvp.presenter.FRIDScanPresenter;

import com.idealbank.ib_secretassetcontrol.R;

import org.simple.eventbus.Subscriber;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnCheckedChanged;
import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.base.fragment.BaseActionBarFragment;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;

import static com.jess.arms.utils.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 08/09/2021 10:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class FRIDScanFragment extends BaseActionBarFragment<FRIDScanPresenter> implements FRIDScanContract.View {
    @BindView(R.id.btn_switch)
    CheckBox btn_switch;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private TimeCount time;
    public ONDEVMESSAGE OnMsg = new ONDEVMESSAGE();
    List<RfidData> list = new ArrayList<RfidData>();
    RFIDAdapter mAdapter = new RFIDAdapter();

    public static FRIDScanFragment newInstance() {
        FRIDScanFragment fragment = new FRIDScanFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerFRIDScanComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fridscan, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        setTitleText("扫描RFID", R.mipmap.ic_scan, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        initRecyclerView();

        mAdapter.replaceData(list);
        time = new TimeCount(10000, 1000);
    }

    @Override
    public void setData(@Nullable Object data) {

    }


    @Override
    protected void initEventAndData() {

    }

    private void initRecyclerView() {
        ArmsUtils.configRecyclerView(mRecyclerView, new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if (btn_switch != null && btn_switch.isChecked()) {
                btn_switch.setText("停止盘点倒计时" + "(" + millisUntilFinished / 1000 + ") ");
            }
        }

        @Override
        public void onFinish() {
            if (btn_switch != null) {
                btn_switch.setText("开始盘点");
                btn_switch.setChecked(false);
            }
            MyApplication.sv_Main.DoInventoryTag(false);
//            closeRfid();
        }
    }

    @OnCheckedChanged({R.id.btn_switch})
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.btn_switch) {
            Log.e("isChecked", "" + isChecked);
            if (isChecked) {
//                MyApplication.sv_Main.DoInventoryTag(true);
                openRfid();
                btn_switch.setText("停止盘点");
                time.start();
            } else {
                mAdapter.addData(new RfidData("212", 1));
                btn_switch.setText("开始盘点");
                closeRfid();
//                MyApplication.sv_Main.DoInventoryTag(false);
                time.onFinish();
            }
        }
    }

    //创建handler
    final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            MyApplication.sv_Main.DoInventoryTag(true);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        time.onFinish();
        time = null;
//        EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH_CUR);
    }

    private void openRfid() {
        //开启服务
        boolean b = MyApplication.sv_Main.Create(OnMsg);
        //先关闭盘点
        MyApplication.sv_Main.DoInventoryTag(false);
        MyApplication.sv_Main.DoIndentify(true);
        // rfid power on
        MyApplication.sv_Main.gpio_rfid_config(true);
        handler.sendMessageDelayed(new Message(), 2000);

    }

    private void closeRfid() {
        try {
            MyApplication.sv_Main.DoInventoryTag(false);
            MyApplication.sv_Main.DoIndentify(false);
            MyApplication.sv_Main.gpio_rfid_config(false);
            MyApplication.sv_Main.Free();
        } catch (UnsupportedOperationException e) {
            System.out.print("******************");
        } catch (Exception e) {
            System.out.print("******************");
        }
    }

    //接受手持机扫描  RFID
    @Subscriber(tag = EventBusTags.SCANRFID)
    private void scaanRfid(Event event) {

        RfidData rfidData = (RfidData) event.getData();
//        list.add(rfidData);
        mAdapter.addData(rfidData);

    }
}
