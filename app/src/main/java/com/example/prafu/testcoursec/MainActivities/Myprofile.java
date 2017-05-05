package com.example.prafu.testcoursec.MainActivities;


import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.prafu.testcoursec.ContributionFragment;
import com.example.prafu.testcoursec.Detailfragment;
import com.example.prafu.testcoursec.R;
import com.example.prafu.testcoursec.other.CircleTransform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import java.util.ArrayList;
import java.util.List;

public class Myprofile extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {

private static final int PERCENTAGE_TO_ANIMATE_AVATAR = 20;
private boolean mIsAvatarShown = true;

private ImageView mProfileImage;
private int mMaxScrollSize;
    private TextView nameofuser,developer_name;
    String name;
    Uri photoUrl;

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);


        ViewPager viewPager  = (ViewPager) findViewById(R.id.materialup_viewpager);
        setupViewPager(viewPager);
    TabLayout tabLayout = (TabLayout) findViewById(R.id.materialup_tabs);
    tabLayout.setupWithViewPager(viewPager);
    AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.materialup_appbar);
    nameofuser=(TextView) findViewById(R.id.name_of_the_user);
    developer_name=(TextView) findViewById(R.id.developer_name);
        mProfileImage = (ImageView) findViewById(R.id.materialup_profile_image);
getuserprofile();
        Toolbar toolbar = (Toolbar) findViewById(R.id.materialup_toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
@Override public void onClick(View v) {
        onBackPressed();
        }
        });

        appbarLayout.addOnOffsetChangedListener(this);
        mMaxScrollSize = appbarLayout.getTotalScrollRange();

        //viewPager.setAdapter(new TabsAdapter(getSupportFragmentManager()));

        }



@Override
public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
        if (mMaxScrollSize == 0)
        mMaxScrollSize = appBarLayout.getTotalScrollRange();

        int percentage = (Math.abs(i)) * 100 / mMaxScrollSize;

        if (percentage >= PERCENTAGE_TO_ANIMATE_AVATAR && mIsAvatarShown) {
        mIsAvatarShown = false;

        mProfileImage.animate()
        .scaleY(0).scaleX(0)
        .setDuration(200)
        .start();
        }

        if (percentage <= PERCENTAGE_TO_ANIMATE_AVATAR && !mIsAvatarShown) {
        mIsAvatarShown = true;

        mProfileImage.animate()
        .scaleY(1).scaleX(1)
        .start();
        }
        }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new Detailfragment(), "Details");
        adapter.addFrag(new ContributionFragment(), "Contribution");

        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
            // Drawable image = ContextCompat.getDrawable(context, tabIcons[position]);
            ////image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
            // SpannableString sb = new SpannableString(" ");
            // ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
            // sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
    }
    public void getuserprofile(){
        String name_developer;
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
               nameofuser.setText(name);
                name_developer=name.toLowerCase();
                name_developer='@'+name_developer.replaceAll("\\s","");
                developer_name.setText(name_developer);
                // String signinemail=profile.getEmail();
               // Log.d(TAG,"emial="+signinemail);
                photoUrl = profile.getPhotoUrl();
                //useremail.setText(signinemail);

                Glide.with(this).load(photoUrl.toString())
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mProfileImage);


            }
        }
    }
    }



