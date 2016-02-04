package me.panl.qqslidemenu.anotation;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Method;

/**
 * Created by panl
 * Date at 16/2/1.
 */
public class PanViewInjector {
    public static void process(final Object o) {
        Class c = o.getClass();
        Method[] methods = c.getDeclaredMethods();
        for (final Method m : methods) {
            OnClick click = m.getAnnotation(OnClick.class);//通过反射api获取方法上面的注解
            if (click != null) {
                if (o instanceof Activity) {
                    if (click.value() == -1) return;
                    View view = ((Activity) o).findViewById(click.value());//通过注解的值获取View控件
                    if (view == null) return;
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                m.invoke(o);//通过反射来调用被注解修饰的方法
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        }

    }
}
