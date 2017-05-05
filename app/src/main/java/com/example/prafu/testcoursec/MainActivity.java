package com.example.prafu.testcoursec;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.prafu.testcoursec.MainActivities.Myprofile;
import com.example.prafu.testcoursec.MainActivities.Pending;
import com.example.prafu.testcoursec.MainActivities.UploadSelector;
import com.example.prafu.testcoursec.other.CircleTransform;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ImageView imgProfile;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private String name;
    private Uri photoUrl;
    private ImageView mPic;
    private TextView useremail;
    private TextView user_name;
    private static final String TAG = "MainActivity";
    String value;
    private Context context;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int[] tabIcons = {
            R.drawable.notes,
            R.drawable.questionpaper,
            R.drawable.ebook
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAuth = FirebaseAuth.getInstance();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = firebaseAuth.getCurrentUser();

                if(mUser != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + mUser.getUid());
                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
        }};
        setSupportActionBar(toolbar);
        initNavigationDrawer();


    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.home:
                        //Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.upload:
                        //Toast.makeText(getApplicationContext(),"Settings",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, UploadSelector.class));
                        break;
                    case R.id.profile:
                       // Toast.makeText(getApplicationContext(),"Trash",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, Myprofile.class));
                        Log.d("profile","opened atleast");
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.pending:
                        startActivity(new Intent(MainActivity.this, Pending.class));
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.logout:
                        finish();

                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
         useremail = (TextView)header.findViewById(R.id.user_emailid);
        user_name=(TextView)header.findViewById(R.id.name_user);
        //tv_email.setText("raj.amalw@learn2crack.com");
        mPic=(ImageView)header.findViewById(R.id.img_profile);
        getCurrentinfo();
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);



        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }
    private void getCurrentinfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String signinemail=user.getEmail();
        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
               // String signinemail=profile.getEmail();
                Log.d(TAG,"emial="+signinemail);
                photoUrl = profile.getPhotoUrl();
                useremail.setText(signinemail);
                user_name.setText(name);
                Glide.with(this).load(photoUrl.toString())
                        .crossFade()
                        .thumbnail(0.5f)
                        .bitmapTransform(new CircleTransform(this))
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(mPic);


            }
        }
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new notes_fragment(),"NOTES");
        adapter.addFrag(new question_papers(), "QUESTION PAPER");
        adapter.addFrag(new ebooks(), "EBOOKS");
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


}