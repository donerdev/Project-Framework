package com.runda.projectframework.app.page.activity.home;

import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.gyf.immersionbar.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.lxj.xpopup.XPopup;
import com.runda.projectframework.R;
import com.runda.projectframework.app.base.BaseActivity;
import com.runda.projectframework.app.base.BaseViewModel;
import com.runda.projectframework.app.others.rxjava.RxUtil;
import com.runda.projectframework.app.widget.PopNormal;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

/**
 *
 * @Description:    xpop取代dialog popwindow等
 * @Author:         An_K
 * @CreateDate:     2020/9/8 9:43
 * @Version:        1.0
 */
public class Activity_Popup extends BaseActivity<BaseViewModel> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.textView_normal)
    TextView textView_normal;

    @BindView(R.id.textView_noTitle)
    TextView textView_noTitle;

    @BindView(R.id.textView_noContent)
    TextView textView_noContent;

    @BindView(R.id.textView_singleButton)
    TextView textView_singleButton;


    @Override
    public int getLayout() {
        return R.layout.activity_popup;
    }

    @Override
    protected void initImmersionBar() {
        super.initImmersionBar();
        ImmersionBar.with(this).titleBar(toolbar).autoStatusBarDarkModeEnable(true,0.2f).init();
    }

    @Override
    public BaseViewModel initViewModel() {
        return ViewModelProviders.of(this, getViewModelFactory()).get(BaseViewModel.class);
    }

    @Override
    public void initEvents() {
        toolbar.setNavigationOnClickListener(view -> finish());

        Disposable normalPopClick = RxView.clicks(textView_normal)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("标题标题标题标题","内容内容内容内容内容内容内容","否","是");
                });
        Disposable singleChoosePopClick = RxView.clicks(textView_noTitle)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("","内容内容内容内容内容内容内容","否","是");
                });
        Disposable multiChoosePopClick = RxView.clicks(textView_noContent)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("标题标题标题标题","","否","是");
                });
        Disposable bottomPopClick = RxView.clicks(textView_singleButton)
                .compose(RxUtil.operateDelay())
                .subscribe(o -> {
                    showNormalPop("标题标题标题标题","内容内容内容内容内容内容内容","","是");
                });
        getViewModel().getRxEventManager().addRxEvent(normalPopClick);
        getViewModel().getRxEventManager().addRxEvent(singleChoosePopClick);
        getViewModel().getRxEventManager().addRxEvent(multiChoosePopClick);
        getViewModel().getRxEventManager().addRxEvent(bottomPopClick);
    }


    private void showNormalPop(String title,String content,String lText,String rText) {
        PopNormal popNormal = new PopNormal(Activity_Popup.this,title,content,lText,rText);
        new XPopup.Builder(Activity_Popup.this)
//                .popupAnimation(PopupAnimation.NoAnimation)
                .asCustom(popNormal)
                .show();
        popNormal.setChooseNoListener(new PopNormal.ChooseNoListener() {
            @Override
            public void onClick() {
                popNormal.dismiss();
            }
        });

        popNormal.setChooseYesListener(new PopNormal.ChooseYesListener() {
            @Override
            public void onClick() {
                popNormal.dismiss();
            }
        });
    }


    @Override
    public void start() {}



    @Override
    public void initNoNetworkEvent() {

    }

    @Override
    public void initTokenOverTimeEvent() {

    }

    @Override
    public void initShowOrDismissWaitingEvent() {

    }

    @Override
    public void initStateLayoutEvent() {

    }
}
