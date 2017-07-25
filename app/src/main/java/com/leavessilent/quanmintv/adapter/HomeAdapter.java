package com.leavessilent.quanmintv.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leavessilent.quanmintv.R;
import com.leavessilent.quanmintv.common.base.Constant;
import com.leavessilent.quanmintv.home.model.HomeModel;
import com.leavessilent.quanmintv.home.model.LinkObject;
import com.leavessilent.quanmintv.live.view.AllLiveActivity;
import com.leavessilent.quanmintv.loader.GlideImageLoader;
import com.leavessilent.quanmintv.play.view.PlayActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;


/**
 * 首页的RecyclerView 适配器
 * Created by Administrator on 2017/2/16.
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener {
    private static final int TYPE_AD = 0;
    private static final int TYPE_CLASSIFY = 1;
    private static final int TYPE_LIVE = 2;
    private static final int TYPE_RECOMMEND = 3;

    private OnItemClickListener mListener;

    public static final String TAG = HomeAdapter.class.getSimpleName();
    private final Context mContext;

    private HomeModel mHomeModel;
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    private HomeRecommendAdapter mHomeRecommendAdapter;

    public void setListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public HomeAdapter(Context context, HomeModel homeModel) {
        mContext = context;
        mHomeModel = homeModel;

        mInflater = LayoutInflater.from(context);
    }

    public void switchRecommendData() {
        if (mHomeRecommendAdapter != null) {
            mHomeRecommendAdapter.switchData();
        }
    }

    public void update(HomeModel homeModel) {
        if (homeModel != null) {
            mHomeModel = homeModel;
            this.notifyDataSetChanged();
        }
    }


    @Override
    public int getItemViewType(int position) {
        HomeModel.ListBean listBean = mHomeModel.getList().get(position);
        String name = listBean.getSlug();
        int viewType;
        if ("app-index".equals(name)) {
            viewType = TYPE_AD;
        } else if ("app-classification".equals(name)) {
            viewType = TYPE_CLASSIFY;
        } else if ("app-recommendation".equals(name)) {
            viewType = TYPE_RECOMMEND;
        } else {
            viewType = TYPE_LIVE;
        }
        return viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = null;
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case TYPE_AD:
                itemView = mInflater.inflate(R.layout.item_home_ad, parent, false);
                viewHolder = new AdViewHolder(itemView);
                break;
            case TYPE_CLASSIFY:
                itemView = mInflater.inflate(R.layout.item_home_classify, parent, false);
                viewHolder = new ClassifyViewHolder(itemView);
                break;
            case TYPE_RECOMMEND:
                itemView = mInflater.inflate(R.layout.item_home_recommend, parent, false);
                viewHolder = new RecommendViewHolder(itemView);
                break;
            case TYPE_LIVE:
                itemView = mInflater.inflate(R.layout.item_home_live, parent, false);
                viewHolder = new LiveViewHolder(itemView);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_AD:
                adBindViewHolder(holder, position);
                break;
            case TYPE_CLASSIFY:
                classyBindViewHolder(holder, position);
                break;
            case TYPE_LIVE:
                liveBindViewHolder(holder, position);
                break;
            case TYPE_RECOMMEND:
                recommendViewHolder(holder, position);
                break;
        }
    }

    /**
     * 绑定推荐数据
     *
     * @param holder
     * @param position
     */
    private void recommendViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RecommendViewHolder) {
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.mTitleTv.setText("  " + mHomeModel.getList().get(position).getName());

            recommendViewHolder.mMoreTv.setOnClickListener(this);

            LinearLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            recommendViewHolder.mRecyclerView.setLayoutManager(layoutManager);
            mHomeRecommendAdapter = new HomeRecommendAdapter(mContext, mHomeModel.getApprecommendation());
            recommendViewHolder.mRecyclerView.setAdapter(mHomeRecommendAdapter);
            mHomeRecommendAdapter.setData(mHomeModel.getApprecommendation());
            mHomeRecommendAdapter.setListener(new CommonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String uid = mHomeRecommendAdapter.get(position).getLink_object().getUid();
                    Intent intent = new Intent(mContext, PlayActivity.class);
                    intent.putExtra(Constant.UID, uid);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    public String getSlug(int position) {
        return mHomeModel.getList().get(position).getCategory_slug();
    }

    /**
     * 绑定直播房间数据
     *
     * @param holder
     * @param position
     */
    private void liveBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof LiveViewHolder) {
            LiveViewHolder liveViewHolder = (LiveViewHolder) holder;
            List<HomeModel.ListBean> list = mHomeModel.getList();
            liveViewHolder.mTitleTv.setText("  " + list.get(position).getName());
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
            liveViewHolder.mRecyclerView.setLayoutManager(layoutManager);

            LiveAdapter adapter = new LiveAdapter(mContext, null);
            liveViewHolder.mRecyclerView.setAdapter(adapter);
            final List<LinkObject> data;
            data = new ArrayList<>();

            String slug = list.get(position).getCategory_slug();
            liveViewHolder.mMoreTv.setText("瞅一瞅");
            liveViewHolder.mMoreTv.setOnClickListener(this);
            if ("lol".equals(slug)) {
                for (HomeModel.ApplolBean value : mHomeModel.getApplol()) {
                    data.add(value.getLink_object());
                }
            } else if ("beauty".equals(slug)) {
                for (HomeModel.AppbeautyBean value : mHomeModel.getAppbeauty()) {
                    data.add(value.getLink_object());
                }
            } else if ("heartstone".equals(slug)) {
                for (HomeModel.AppheartstoneBean value : mHomeModel.getAppheartstone()) {
                    data.add(value.getLink_object());
                }
            } else if ("huwai".equals(slug)) {
                for (HomeModel.ApphuwaiBean value : mHomeModel.getApphuwai()) {
                    data.add(value.getLink_object());
                }
            } else if ("overwatch".equals(slug)) {
                for (HomeModel.AppoverwatchBean value : mHomeModel.getAppoverwatch()) {
                    data.add(value.getLink_object());
                }
            } else if ("blizzard".equals(slug)) {
                for (HomeModel.AppblizzardBean value : mHomeModel.getAppblizzard()) {
                    data.add(value.getLink_object());
                }
            } else if ("qqfeiche".equals(slug)) {
                for (HomeModel.AppqqfeicheBean value : mHomeModel.getAppqqfeiche()) {
                    data.add(value.getLink_object());
                }
            } else if ("mobilegame".equals(slug)) {
                for (HomeModel.AppmobilegameBean value : mHomeModel.getAppmobilegame()) {
                    data.add(value.getLink_object());
                }
            } else if ("wangzhe".equals(slug)) {
                for (HomeModel.AppwangzheBean value : mHomeModel.getAppwangzhe()) {
                    data.add(value.getLink_object());
                }
            } else if ("dota2".equals(slug)) {
                for (HomeModel.Appdota2Bean value : mHomeModel.getAppdota2()) {
                    data.add(value.getLink_object());
                }
            } else if ("tvgame".equals(slug)) {
                for (HomeModel.ApptvgameBean value : mHomeModel.getApptvgame()) {
                    data.add(value.getLink_object());
                }
            } else if ("webgame".equals(slug)) {
                for (HomeModel.AppwebgameBean value : mHomeModel.getAppwebgame()) {
                    data.add(value.getLink_object());
                }
            } else if ("dnf".equals(slug)) {
                for (HomeModel.AppdnfBean value : mHomeModel.getAppdnf()) {
                    data.add(value.getLink_object());
                }
            } else if ("minecraft".equals(slug)) {
                for (HomeModel.AppminecraftBean value : mHomeModel.getAppminecraft()) {
                    data.add(value.getLink_object());
                }
            }

            adapter.setListener(new CommonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String uid = data.get(position).getUid();
                    Intent intent = new Intent(mContext, PlayActivity.class);
                    intent.putExtra(Constant.UID, uid);
                    mContext.startActivity(intent);
                }
            });
            adapter.update(data);


        }
    }

    /**
     * 绑定分类数据
     *
     * @param holder
     * @param position
     */
    private void classyBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ClassifyViewHolder) {
            final ClassifyViewHolder classifyViewHolder = (ClassifyViewHolder) holder;
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
            classifyViewHolder.mRecyclerView.setLayoutManager(layoutManager);
            final List<HomeModel.AppclassificationBean> appclassification = mHomeModel.getAppclassification();
            HomeClassifyAdapter adapter = new HomeClassifyAdapter(mContext, appclassification);
            classifyViewHolder.mRecyclerView.setAdapter(adapter);
            adapter.setListener(new CommonAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    String classification = appclassification.get(position).getExt().getClassification();
                    Intent intent = new Intent(mContext, AllLiveActivity.class);
                    intent.putExtra(Constant.CLASSIFY, classification);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    /**
     * 绑定轮播页数据
     *
     * @param holder
     * @param position
     */
    private void adBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof AdViewHolder) {
            AdViewHolder adViewHolder = (AdViewHolder) holder;
            final List<HomeModel.AppindexBean> appindex = mHomeModel.getAppindex();
            List<String> imageUrls = new ArrayList<>();
            List<String> titles = new ArrayList<>();
            for (int i = 0, size = appindex.size(); i < size; i++) {
                HomeModel.AppindexBean appindexBean = appindex.get(i);
                LinkObject object = appindexBean.getLink_object();
                imageUrls.add(object.getRecommend_image());
                titles.add(object.getTitle());
            }

            adViewHolder.mBanner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void OnBannerClick(int position) {
                    String uid = appindex.get(position - 1).getLink_object().getUid();
                    Intent intent = new Intent(mContext, PlayActivity.class);
                    intent.putExtra(Constant.UID, uid);
                    mContext.startActivity(intent);
                }
            });

            adViewHolder.mBanner
                    .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                    .setIndicatorGravity(BannerConfig.RIGHT)
                    .setImageLoader(new GlideImageLoader())
                    .setImages(imageUrls)
                    .setBannerTitles(titles)
                    .setDelayTime(3000)
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return mHomeModel == null ? 0 : mHomeModel.getList().size();
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }

    @Override
    public void onClick(View v) {
        int position = mRecyclerView.getChildAdapterPosition(((View) v.getParent().getParent()));
        if (position >= 0 && position < this.getItemCount()) {
            if (mListener != null) {
                mListener.onItemClick(v, position);
            }
        }
    }


    public static class LiveViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTv;
        private TextView mMoreTv;
        private RecyclerView mRecyclerView;

        public LiveViewHolder(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.item_tv_title);
            mMoreTv = (TextView) itemView.findViewById(R.id.item_tv_more);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_rv);
        }
    }

    public static class RecommendViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleTv;
        private TextView mMoreTv;
        private RecyclerView mRecyclerView;

        public RecommendViewHolder(View itemView) {
            super(itemView);
            mTitleTv = (TextView) itemView.findViewById(R.id.item_tv_title);
            mMoreTv = (TextView) itemView.findViewById(R.id.item_tv_more);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_rv);
        }
    }

    public static class AdViewHolder extends RecyclerView.ViewHolder {
        private Banner mBanner;

        public AdViewHolder(View itemView) {
            super(itemView);
            mBanner = (Banner) itemView.findViewById(R.id.item_home_banner);

        }
    }

    public static class ClassifyViewHolder extends RecyclerView.ViewHolder {
        private RecyclerView mRecyclerView;


        public ClassifyViewHolder(View itemView) {
            super(itemView);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.item_classify_rv);
        }
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

}
