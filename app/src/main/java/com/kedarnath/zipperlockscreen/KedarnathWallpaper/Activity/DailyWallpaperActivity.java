package com.kedarnath.zipperlockscreen.KedarnathWallpaper.Activity;

import android.content.Context;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kedarnath.zipperlockscreen.Activity.BaseActivity;


import com.kedarnath.zipperlockscreen.KedarnathWallpaper.Adapter.RecyclerViewAdapter;
import com.kedarnath.zipperlockscreen.Model.CustpmItems;
import com.kedarnath.zipperlockscreen.R;

import java.util.ArrayList;
import java.util.List;

public class DailyWallpaperActivity extends BaseActivity {
    List<CustpmItems> itemsList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_wallpaper);

        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        itemsList = new ArrayList();
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=OswzXtZMqEbsNG9HCDb7oa0U1GqcC9AJtGuXPGnU8A67kXIQcI0jPRzM-wrs0ZPl3omlAaOngw5Jv6NVpYVTIHBn4MfFAG337UP99b46Hvip20Y-O5DpHS0Ze7sr4ILW1edcEM-XTFlGgRpQ"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=S3kKIzqjz7q1jGAJuLktis1gDXNBWGs7FV2kEENY8zE7LiayiLngbNiC5zaE9-qrV-Q_0fKM4f4dqk5B5kdDJwacQZBlN97hrBsR7itSSU9bGjH_cCo6sXbYr16U3W-16qebzvXCi_j2mLcS"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=cqoRixQOL7XH5yin9BiK2i-DODFYYzJ12WPP3exRJB5CBeLGkMKGTcdd3ycxjLnhZNP6ysNkVuEgwOkbugMDcWCG3HHyBK4hSkPwWUP93uu3-kFnfZ6ZiDcALsP2xpsQmrgQZh2FboIGLcBG"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=Wp3gY2YenKDjAMEC642-3eiQlFmOmsdg2-GccPlGy84r_VyJlKA1sSxADB6kNqSOf_f6jk3wEiUqu6u1NUtKzfRv0gJzazXd_hCuhsFdrU3LH6ommivlF9emfvethobcUqWpq9Jy_JIyBOB2"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=Ih9gbo8xi1pyBL7nNcVkWHgnfZLl5EbVEYAucWf5ff_OzcnBfdMA79S6TgTuWHWgsr9KIcJYhbLrjkCuylK_sQPitnfm8YgNSxZcdm2W1Iq2tvnIHXppHEACmaohYG_DGfKjHY3wHOwo66n8"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=3ylL9t71DQpy2TITmET_giBcIu4CR1Yl8R5n7hG5oM19sTW6Tt-WjuuzKis7p6dg1u1zKrcei3apivCZ0m_RUsYyzjR_USQe6p0wvw3kuwhVJtDTK8Vao37ND9dJIOraA53lJfahxSe-PlRO"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=b09uMtxUBg7QyHhXKn3kC3ppIZuIiKqnw6SEkDwDfI71LDgPdCGRkLow4ZtmWg89S4tAuNHYDldKNNJ7MIHjWOQJX8eGxrW2c0Q6glRQl3esIx6CUiYwTqBeTniFphDfNutnjyTF2o6oAsQi"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=XVQkvrkF6idK6a6zwBQOFaQfls2fkNd-kqynTtdCU4uEV4cGEkIy6n1ecyCN5OTrxbcY1nxORIukW3-ezxkIHTFy4HD8JptYCAbkmWezLhYfy5B-fwglrmCfqHQ46kvRfx99A9SzXYin2e1K"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=dJukvdG5GO7bCO3KrI0tMerAZ-eBDn56aNREYZg7KUo7YdhpW3PIzXoGuJtB37_m-DlIBoy-opArDXcsJ4mvSxhsExWUEUu8FvcC_CnOpt3zVlRYkkDNUtxxJeACu2REG3XjtBbWaF6CuzYP"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=JNsyvGTy5-MXXgIoZuw9oLPimDcjIULBj24nK2JMAvk20Mqcjo_89uOBDlOMoB1E61Mqy4CZpPfoyYbYQNNICCzPp-Qh8f6SVagAErVhrEQuG_xUNkFnFsBxHuoO-L_icr4pE9kuRUJMCXDJ"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=ot3pTjweNAqF3mjY7wKuQmmVGVQTBPcyEEWJOKXxWZOOcK9UB2y4_DOmCR1pPb0hBi3805fFBxW5rikf_-iI63wEeLi0B7fMR2I3139GmKpeEFgArMZYjRwtgl8I-R6iTfdT3pTixZIk-xXe"));
        recyclerViewAdapter = new RecyclerViewAdapter(this.itemsList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected Context getContext() {
        return DailyWallpaperActivity.this;
    }
}