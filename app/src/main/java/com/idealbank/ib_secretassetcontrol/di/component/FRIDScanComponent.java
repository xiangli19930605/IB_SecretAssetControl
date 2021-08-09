package com.idealbank.ib_secretassetcontrol.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.ib_secretassetcontrol.di.module.FRIDScanModule;
import com.idealbank.ib_secretassetcontrol.mvp.contract.FRIDScanContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.FRIDScanFragment;


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
@FragmentScope
@Component(modules = FRIDScanModule.class, dependencies = AppComponent.class)
public interface FRIDScanComponent {
    void inject(FRIDScanFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        FRIDScanComponent.Builder view(FRIDScanContract.View view);

        FRIDScanComponent.Builder appComponent(AppComponent appComponent);

        FRIDScanComponent build();
    }
}