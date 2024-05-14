package com.kedarnath.zipperlockscreen.KedarnathWallpaper.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.kedarnath.zipperlockscreen.Activity.BaseActivity;


import com.kedarnath.zipperlockscreen.KedarnathWallpaper.Adapter.RecyclerViewAdapter;
import com.kedarnath.zipperlockscreen.Model.CustpmItems;
import com.kedarnath.zipperlockscreen.R;

import java.util.ArrayList;
import java.util.List;

public class HD4KWallpaperActivity extends BaseActivity {
    List<CustpmItems> itemsList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hd4_kwallpaper);

        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        itemsList = new ArrayList();
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=Lwk3QH2qUtUYebVOYhDNCBjuwSui1bKZcKogB8w-1DRAHwm3e1gVG2HAu7JcfhE8RBuwoKPGmqPRsOWU8O7f-RIsAY9IGprfMH-njeC2xFcAdL9qbjpswn1UCJOGdtc9Gk9gqqCcxam2bmtf"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=mhb00D6BLnDsfJ4Ipvv8mvXC3kWafusnU4_JAO_8C6jhmzoZiiYwueFGJ_v_5csTsbH_hzaHxb1UNLySB0vYEJPLD9ZAmxSF4v4bcfEUOwSXEOo9CI2sI1QndeHi5a6ZT4l5HVdJvi-E6MLR"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=8K9DXKm3wVP36RfxWemG6hvtuJB0JywFn0NENYQtnZYjhqpYcqSKvNdQmBaWN4QoBw7Xw7leIvqMFj4XpnsgaMe73R_Zu2PqUkE2kXfxI4eVrps1XHRxo1G8_A7fp875kaZGUO_vRqnE3Gmq"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=q0Ztwl8NNj3V-H-sI2Y1ekbFvlxr1mCigowq_LD7uwTQJyVPNf4NYW201dTRbxQt4BwhJTW2OElDMwkFJ8fxxUa0VKRcqpUMklWJUjoOj7MP28U4GrqB0sPiI6I8vaU8i-u2LFsmSiaCKtuP"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=L2SoWn94sXGZuAMPsUyk08u5of7eGCMGuY3OtqNuUszNTIG3Y5zfB4FCE4p-DEGk34IcCQB7OG51RgBkbeQJSmxEv61_C5BCAwi5zOBw0USccn_VN7V5MEdGxEwjr0b3wIpUKfYAUoHtu2kZ"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=jfzpnnPpitcNPSjvzqztDAO1kis6BvYieOj_skSI81UJKVfEjD0nUmmdU40On28RM5UbRrJ8mBikE2n5FzMWvEH4wD621Fef97qGctBnElF7HYvE0cZEyGfCaxrsZoR36fTDNoONGSIA09v4"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=Q2jXJ9ek3pppDOgwnL9FqFMqx184cpZQXOhbbeTKZIFtdPF1WqAjpFfhLffYcAPZ46ejC4EPWMQvvX8ugwyLl7yrRpjQa1v0U9tlgVL891RToQSY021_Y4_pP-Xy9tqoQhDHP-No_5kQZoi7"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=MMeCohoNkkxSYyF6GSLHqgvJGPOQE-M4pu1Norf_59Z7Ddl4PSFKIPWAfza-BioCrd0jhrbzNUimwrsU1zyZBOHNS_qzvpj9nq9Q8MvNF4FAOYPWqv5yZvVi0tRbFztQf9CVEVlLpsy0T40U"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=4YnOz9fPF-b9m05o1SCbZtz7X1XKDoDxieuaaHuMlds3szEpXWY-pb0Gnzu51xAPaLWC3q_dxCny2h7GRsjum7GDsHAzkIh4Qnk3NgqWW3wXEUGq5Hgw_hoyG1WcqfmKBlRKvAqBrU5fxTyd"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=8GI_dOHX8JAop86mgyczCXbvItzhY_IMYse6qlZcGvwkMKf5JxKc571jM9DPyHszxs46RhCjQfogB_7JXpuHki9qZZmp0o400yw5XbcU_VRjWbev8kJVD2HWt1SlvEcxynPQy3FdrbQIdgVn"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=1BgsG6dsj6Z1Mbs6LI1K_f6JCCwww0wP3Ai2bzajWnegv3kyjGTBVLa5_hnpK4z6b25Gs89IVUhejOTUhM-vK31CqwLgPaaV5fmMje_SBdrP8sn2-bggvN901JY94VYJ_BCykNJ56YsoN5jy"));
        recyclerViewAdapter = new RecyclerViewAdapter(this.itemsList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    protected Context getContext() {
        return HD4KWallpaperActivity.this;
    }
}