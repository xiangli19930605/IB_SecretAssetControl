package com.idealbank.ib_secretassetcontrol.di.module;

import com.jess.arms.di.scope.FragmentScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

import com.idealbank.ib_secretassetcontrol.mvp.contract.FRIDScanContract;
import com.idealbank.ib_secretassetcontrol.mvp.model.FRIDScanModel;


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
@Module
public abstract class FRIDScanModule {

    @Binds
    abstract FRIDScanContract.Model bindFRIDScanModel(FRIDScanModel model);
}