package me.panl.qqslidemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import me.panl.qqslidemenu.view.QQSlideMenu;

public class MainActivity extends AppCompatActivity {

    QQSlideMenu slideMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        slideMenu = (QQSlideMenu)findViewById(R.id.slide_menu);
    }

    public void toggleMenu(View view){
        slideMenu.toggleMenu();
    }

}
