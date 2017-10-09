package com.classs.skhuter.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.classs.skhuter.R;
import com.classs.skhuter.util.Connection;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Handler handler = new Handler();
    Toast t;
    private boolean notiVisiblity = false;

    HomeFragment homeFragment;
    UserListFragment userListFragment;
    ScheduleFragment scheduleFragment;
    MeetingNoteFragment meetingNoteFragment;
    NoticeFragment noticeFragment;
    StuScheduleFragment stuScheduleFragment;
    AccountFragment accountFragment;
    VoteFragment voteFragment;
    BoardFragment boardFragment;

    ImageView ivHome;
    TextView tvProfile;

    int beforeNav = 0;

    /// Fragment 변수들
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fm = getSupportFragmentManager();

        homeFragment = (HomeFragment) fm.findFragmentById(R.id.frag1);
        userListFragment = new UserListFragment();
        scheduleFragment = new ScheduleFragment();
        meetingNoteFragment = new MeetingNoteFragment();
        noticeFragment = new NoticeFragment();
        stuScheduleFragment = new StuScheduleFragment();
        accountFragment = new AccountFragment();
        voteFragment = new VoteFragment();
        boardFragment = new BoardFragment();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ivHome = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivHome);
        tvProfile = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvProfile);

        tvProfile.setText(Connection.loginUser.getName());
        ivHome.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                moveToFragment(0);
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.main_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        moveToFragment(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void moveToFragment(int navId) {
        // 처음 홈 화면 클릭 시 에러 잡음
        if (beforeNav == navId && navId == 0) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return ;
        }
        FragmentTransaction tran = fm.beginTransaction();
        //fm.popBackStack();
        if (navId == R.id.nav_user_list) {
            // 학생목록
            tran.replace(R.id.container, userListFragment);
        } else if (navId == R.id.nav_schedule) {
            // 학생회일정
            tran.replace(R.id.container, scheduleFragment);
        } else if (navId == R.id.nav_meeting_note) {
            // 회의록
            tran.replace(R.id.container, meetingNoteFragment);
        } else if (navId == R.id.nav_notice) {
            // 공지사항
            tran.replace(R.id.container, noticeFragment);
        } else if (navId == R.id.nav_stu_schedule) {
            // 학사일정
            tran.replace(R.id.container, stuScheduleFragment);
        } else if (navId == R.id.nav_account) {
            // 회계내역
            tran.replace(R.id.container, accountFragment);
        } else if (navId == R.id.nav_vote) {
            // 투표목록
            tran.replace(R.id.container, voteFragment);
        } else if (navId == R.id.nav_board) {
            // 익명게시판
            tran.replace(R.id.container, boardFragment);
        } else if (navId == R.id.nav_logout) {
            // 로그아웃
            // TODO 로그아웃 기능 실행
        } else {
            // home
            tran.replace(R.id.container, homeFragment);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }
        tran.commit();

        beforeNav = navId;
    }

    /* 뒤로가기 시 실제 핸들링 처리 부분 */
    Runnable backKeyRun = new Runnable() {
        int count = 0;
        @Override
        public void run() {
            if (count < 1) {
                showToast("뒤로가기를 한번 더 누르시면 종료됩니다");
                count++;
            } else {
                finish();
            }
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    count = 0;
                }
            }, 2000);
        }
    };

    /* 백키 눌렀을 때 처리사항들 => UX 적으로 매우 중요 ! 우선순위 파악필요. */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            handler.post(backKeyRun);
            if (!notiVisiblity) {
                handler.post(backKeyRun);
            } else {
                //toggleFragment();
            }
        }
    } // end of onBackPressed

    public void showToast(String text) {
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(),
                text,
                Toast.LENGTH_SHORT);
        t.show();
    }
}
