package me.panl.qqslidemenu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import me.panl.qqslidemenu.anotation.OnClick;
import me.panl.qqslidemenu.anotation.PanViewInjector;
import me.panl.qqslidemenu.view.QQSlideMenu;

public class MainActivity extends AppCompatActivity {

    QQSlideMenu slideMenu;

    @OnClick(R.id.centerButton)
    public void toCenter(){
        Toast.makeText(this,"注解起作用啦",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(MainActivity.this,CenterActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PanViewInjector.process(this);
        slideMenu = (QQSlideMenu)findViewById(R.id.slide_menu);

        slideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void toggleMenu(View view){
        slideMenu.toggleMenu();
    }
}
