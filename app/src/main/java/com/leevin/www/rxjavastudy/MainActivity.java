package com.leevin.www.rxjavastudy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import rx.Observable;
import rx.Single;
import rx.SingleSubscriber;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button operation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        operation = (Button) findViewById(R.id.start_btn);
    }

    public void start(View view) {
        // 1.可观察对象，事件或数据源
        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello");
                subscriber.onNext("world");
                subscriber.onNext("love java ,love life");
                subscriber.onCompleted();
            }
        });
        // 2.观察者，事件或数据的响应者
        Subscriber<String> subscriber = new Subscriber<String>(){
            @Override
            public void onCompleted() {
                Log.e(TAG, "===========onCompleted:========== " );
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.e(TAG, "onNext: ================"+s );
            }
        };
        // 3.绑定，触发事年
        observable.subscribe(subscriber);
    }

    public void just(View view) {
        // just 第个item执行完后，执行complete
        Observable.just(1,2,3,4,5,6)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 ==1; // 筛选奇数
                    }
                })
                .map(new Func1<Integer, Double>() {
                    @Override
                    public Double call(Integer integer) {
                        return Math.sqrt(integer); // 返回平方根
                    }
                })
                .subscribe(new Subscriber<Number>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Number number) {
                        Log.e(TAG, "onNext: ============" + number);
                    }
        });
    }

    public void operation(View view) {
      /*  operation.setClickable(false);
        Observable operationObservable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber subscriber) {
                subscriber.onNext(longRunningOperation());
                subscriber.onCompleted();
            }
        });

        operationObservable.subscribe(new Subscriber() {
            @Override
            public void onCompleted() {
                operation.setClickable(true);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Object o) {
                Log.e(TAG, "onNext: =========="+ ((String) o));
            }
        });*/
        Single.create(new Single.OnSubscribe<String>(){
            @Override
            public void call(SingleSubscriber<? super String> singleSubscriber) {
                String s = longRunningOperation();
                singleSubscriber.onSuccess(s);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e(TAG, "call:====================== "+s);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {

                    }
                });
    }

    public String longRunningOperation() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            // error
        }
        return "Complete!";
    }


    public void showRecycle(View view) {
        Intent intent = new Intent(this, ShowRecycleActivity.class);
        startActivity(intent);
    }


}
