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

public class TopWallpaperActivity extends BaseActivity {
    List<CustpmItems> itemsList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_wallpaper);

        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        itemsList = new ArrayList();
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=2D0fhDxulM1QOag12Qt5v2jWsXMj-u3kPfXpE6jP7E7s96wdS12yxrBWR6ghkA2a7TMi736cW6CuyDKLjFrZ6d92g_9BvxriSug9fITfhYs5MnM86P8Y2HUJuvpegFWlaE6SMdan71xc4cvw"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=kK71A_5n-Yg0bSwZrUsCSc47q1fhgbi5CqW0mLy9wvrlmH74K_CwpboMssU6Rj-vIG0mNApaGGfPy2g9dUMSI044bd7eFz2NQ1HnGoWdecqADA9JxJBcFu9J40R1HG7L5Avgdl5vKd34KGEo"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=xhvAWp62EGlgNwM6oPQPEtQo1HL4k4yRne2h0zs-pjYDx6Y48J7aL51wudC2xM58DJ0bJklsXwYP5LdmGrM157MaMsVPt7-tFj7qWsjmbKn5eanOah6i8XiQXA-rUu_IE2vjWKiiI1K23sSm"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=Q5IHZq_g_sVcKtEndYEGtxb-hY1BRwyuqOsoEHlsc8Pl6gppJw_noDarFh5E0mvhYkR7HWMJhpCpzkJacKZwSifRU3KesBKnvfcgEtZCsZAxQFIES1KZvptJn98HzXIOFNcwouRCfKGQ-9uw"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=YBI-EoWu0XBD1pVjhgUfm_P9wuCc2HcKvXoJUungNQUl4qZ2uow1Cx67sMDZsJKWW7fDW3828j62xWUwUFrfb6bvsJuvAGQ0kWROlRYVuBFSsMpOuO3-n0812XG2ATozjPc_zpJtEg2E1a6r"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=QYTTTd8x9pmW3oi0IJOM3XKx98HlqtEawTpjtuJPM7-sP9qAM8ph9U11NLwrYNardkN01gQwZbhHbzxvrkw5mcXhjmnH8vXogAL0cyDo552lovmRd2VQ1uvRigi4NEH2vJpFVyGw6-QN25lg"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=-9OPlsHNxEvCimP4WgCX0mCCVKLlTy9dBeskLENQ2juOi3rJMpi0352EOohESno5_pPl-TWtF9Hzg6RdGhHAQpniuEslMavvf6qobYFXBhbHLzglzL2vetvNWYlhYVkqdo4s-gMDueo8b9ca"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=RccU_C1zdIY9-ij35m7fbCaXQGum8BqxQAubWCsIMSeQH3FS7v7fsber5epsi8FEGqNjn4bpdXP3f3DW5_7_W6h2hExrYjWfDbix7vtCUa_TC2mqAgv5UBMuWoaWiMs6pyUJBmE7BVg05gCV"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=j5Hs6ZgAmveFn2tjUa7VQHgZGwr0zA0TVrno_-2ZT8Uz4ylF95_WAyUHKRgN9qFTJwx7ZLZzTc-Qdg54Cg4pCR2GrkvlorMAlKkt9W-VBvsaku20q5KdVMQaTI9rJPRUF41uKPhnHe8h9vxK"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=xGiOHts46EhOUhTNOXk6Mw9DBS8-_OXLh_Fdu8UfQU2UiJunsZuyi2aAe-Lk5SOXSETkqiOn6V5VfHv9O4I6p_0igOB4lPn9iV8yesyDIsxTtDPyd3PJbtD7_rC79S5SrePqPMXjn1FlmFAK"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=gvU659zBCSYcgPrw98Jq0itvh2_SMamZXc4PtJ81wofc_gGHXFrPA23bWFpI9TEAaN1BmrSdE6ZpQCpF9og6KybtCybMJvypBMilql-ngUFkSGttDpqP1voZ-2z3iHovnGyI3hQf2MYnUt60"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=eehZqWzWuEn9p3J8w1Uc6mTZox53FUry1FnliIHt422P1AvmvUQOzC_aM8IBp1Hd4ikQRMv6ZnCzg0tBcmiyMDkRrquMdv7shATgeZjStDjN5TiDoeLeqw6gQrb2rRxWjKlBPDCIvRXc6_Kp"));
        recyclerViewAdapter = new RecyclerViewAdapter(this.itemsList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected Context getContext() {
        return TopWallpaperActivity.this;
    }
}