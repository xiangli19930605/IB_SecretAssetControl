package com.idealbank.ib_secretassetcontrol.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.idealbank.ib_secretassetcontrol.di.module.ScanAssetModule;
import com.idealbank.ib_secretassetcontrol.mvp.contract.ScanAssetContract;

import com.jess.arms.di.scope.FragmentScope;
import com.idealbank.ib_secretassetcontrol.mvp.ui.fragment.ScanAssetFragment;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 06/21/2019 09:53
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@FragmentScope
@Component(modules = ScanAssetModule.class, dependencies = AppComponent.class)
public interface ScanAssetComponent {
    void inject(ScanAssetFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        ScanAssetComponent.Builder view(ScanAssetContract.View view);

        Builder appComponent(AppComponent appComponent);

        ScanAssetComponent build();
    }
}