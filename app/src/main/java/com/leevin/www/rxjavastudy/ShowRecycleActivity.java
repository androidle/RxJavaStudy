package com.leevin.www.rxjavastudy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by  Leevin
 * on 2016/5/30 ,21:15.
 */
public class ShowRecycleActivity  extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    static int[] vRes = new int[] { R.drawable.p1, R.drawable.p2,
            R.drawable.p3, R.drawable.p4, R.drawable.p5, R.drawable.p6,
            R.drawable.p7, R.drawable.p8, R.drawable.p9, R.drawable.p10,
            R.drawable.p11, R.drawable.p12, R.drawable.p13, R.drawable.p14,
            R.drawable.p15, R.drawable.p16, R.drawable.p17, R.drawable.p18,
            R.drawable.p19, R.drawable.p20, R.drawable.p21, R.drawable.p22,
            R.drawable.p23, R.drawable.p24, R.drawable.p25, R.drawable.p26,
            R.drawable.p27, R.drawable.p28, R.drawable.p29, R.drawable.p30,
            R.drawable.p31, R.drawable.p32, R.drawable.p33, R.drawable.p34,
            R.drawable.p35, R.drawable.p36, R.drawable.p37, R.drawable.p38,
            R.drawable.p39, R.drawable.p40, R.drawable.p41, R.drawable.p42,
            R.drawable.p43 };

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        // 瀑布流
//        final StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, 1);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                // 如果是最后一项，就加载下一页数据
                LinearLayoutManager layoutManager  = (LinearLayoutManager) recyclerView.getLayoutManager();
                layoutManager.findLastVisibleItemPosition();

            }
        });

        // swipeRefreshLayout
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        refreshLayout.setOnRefreshListener(this);
    }

    private class MyAdapter extends RecyclerView.Adapter {
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(ShowRecycleActivity.this, R.layout.item_recycler, null);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            // 刷新数据
           MyViewHolder myViewHolder = (MyViewHolder) holder;
            myViewHolder.ivContent.setImageResource(vRes[position]);
            myViewHolder.tvContent.setText(position+"");
             myViewHolder.itemView.setTag(position);
             myViewHolder.itemView.setOnClickListener(myClickListener);
        }

        @Override
        public int getItemCount() {
            return vRes.length;
        }
    }

    private View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ShowRecycleActivity.this, ""+(int)v.getTag(), Toast.LENGTH_SHORT).show();
        }
    };

    private class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView ivContent;
        private final TextView tvContent;

        public MyViewHolder(View view) {
            super(view);
            ivContent =  (ImageView) view.findViewById(R.id.ivContent);
            tvContent =  (TextView)view.findViewById(R.id.tvContent);
        }
    }


    @Override
    public void onRefresh() {
        // 在这里进行网络数据的访问
        refreshLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }
}
