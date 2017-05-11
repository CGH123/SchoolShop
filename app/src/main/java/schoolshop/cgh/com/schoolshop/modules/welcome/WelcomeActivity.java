package schoolshop.cgh.com.schoolshop.modules.welcome;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;

import com.trello.rxlifecycle.components.RxActivity;

import schoolshop.cgh.com.schoolshop.R;
import schoolshop.cgh.com.schoolshop.modules.main.ui.MainActivity;

/**
 * Created by HUI on 2017-05-03.
 */

public class WelcomeActivity extends RxActivity{

    private RelativeLayout welcomeImg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        welcomeImg = (RelativeLayout) this.findViewById(R.id.welcome_img);
        AlphaAnimation anima = new AlphaAnimation(0.5f, 1.0f);
        anima.setDuration(3000);// 设置动画显示时间
        welcomeImg.startAnimation(anima);
        anima.setAnimationListener(new AnimationImpl());

    }

    private class AnimationImpl implements Animation.AnimationListener {

        @Override
        public void onAnimationStart(Animation animation) {
            //welcomeImg.setBackgroundResource(R.drawable.main_back);
        }

        @Override
        public void onAnimationEnd(Animation animation) {
            // 动画结束后跳转到别的页面
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }

    }

}
