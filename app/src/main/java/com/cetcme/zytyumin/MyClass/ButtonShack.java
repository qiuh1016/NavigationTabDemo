package com.cetcme.zytyumin.MyClass;

import android.os.Handler;
import android.widget.Button;

/**
 * Created by qiuhong on 9/3/16.
 */
public class ButtonShack {

    public static void run(Button button) {
        final Button toShackButton = button;
        toShackButton.setEnabled(false);
        toShackButton.animate()
                .translationX(-50)
                .setDuration(50);
        delay(50, new Runnable() {
            @Override
            public void run() {
                toShackButton.animate()
                        .translationX(50)
                        .setDuration(100);
                delay(100, new Runnable() {
                    @Override
                    public void run() {
                        toShackButton.animate()
                                .translationX(-50)
                                .setDuration(100);
                        delay(100, new Runnable() {
                            @Override
                            public void run() {
                                toShackButton.animate()
                                        .translationX(50)
                                        .setDuration(100);
                                delay(100, new Runnable() {
                                    @Override
                                    public void run() {
                                        toShackButton.animate()
                                                .translationX(-50)
                                                .setDuration(100);
                                        delay(100, new Runnable() {
                                            @Override
                                            public void run() {
                                                toShackButton.animate()
                                                        .translationX(0)
                                                        .setDuration(50);
                                                toShackButton.setEnabled(true);
//                                                delay(100, new Runnable() {
//                                                    @Override
//                                                    public void run() {
//                                                        toShackButton.animate()
//                                                                .translationX(0)
//                                                                .setDuration(50);
//                                                    }
//                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });


//        toShackButton.animate()
//                .translationX(-50)
//                .setDuration(50);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toShackButton.animate()
//                        .translationX(50)
//                        .setDuration(100);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        toShackButton.animate()
//                                .translationX(-50)
//                                .setDuration(100);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                toShackButton.animate()
//                                        .translationX(50)
//                                        .setDuration(100);
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        toShackButton.animate()
//                                                .translationX(-50)
//                                                .setDuration(100);
//                                        new Handler().postDelayed(new Runnable() {
//                                            @Override
//                                            public void run() {
//                                                toShackButton.animate()
//                                                        .translationX(0)
//                                                        .setDuration(50);
//                                            }
//                                        },50);
//                                    }
//                                },100);
//                            }
//                        },100);
//                    }
//                },100);
//            }
//        },50);
    }

    private static void delay(int ms , final Runnable runnable) {
        new Handler().postDelayed(runnable, ms);
    }
}
