package com.zhy.lib_library;

public class MyClass {
    public static int index;
    public static String test;

    public static void main(String[] args) {
        System.out.println("hello !");
        System.out.println("i = " + index);
        System.out.println("test = " + test);

//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("thread start.");
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("thread end.");
//                for (int i = 0; i < 10000; i++) {
//                    System.out.println("i :" + i);
//                }
//            }
//        });
//        executorService.shutdownNow();
//        System.out.println("executorService is isTerminated :" + executorService.isTerminated());
//        System.out.println("executorService is isShutdown :" + executorService.isShutdown());

        int percent = (int) (2852385782369l * 100 / 2852385782370l);
        System.out.println("percent :" + percent);
    }
}