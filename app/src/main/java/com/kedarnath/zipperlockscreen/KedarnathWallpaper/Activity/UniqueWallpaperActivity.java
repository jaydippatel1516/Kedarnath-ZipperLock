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

public class UniqueWallpaperActivity extends BaseActivity {
    List<CustpmItems> itemsList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unique_wallpaper);
        

        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        recyclerView =  findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        itemsList = new ArrayList();
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=riut05tRcRmqRva64x-OT52f7TcjNFh2SSGNx3pXFalqir_oQONWg0RWeRErtZFqTrWOmnrzLszaDGw2mZmqQHFJ3RSOLBcmk-bBMBX09mm-bZrS7hlFb7fkQcpYXbjjQ_X5Mel6YrYloqa2"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=VEvjXYulTDlS258jxMCvidZWmwln6vx_Cca64iTuOik31RnDv5YgI_6YXxq9VDFdXU4WDnGCaUkfCNIINJV4NkvnaGhweADebweXXMSpc0XRvcSH5Afo9Ah9U8F2Hx8_hafN9S78MFefmpe_"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=BHVttzm2_1AbnIA9TUG8F0XCxYdENsafEpPGkvwkCDeyfLtGazy1dN-m7Kq085DQfaWv9Wmls7qAGsVb2BfZvFE-R04ydOu_LNVX2mkIZzmGb_p9u91HfI9EY3LabCVE9gKFXsUIlx_u7ovM"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=iu-X4Jl8VlmJmSO2oMtF8WSSKy2LeOf3noakDrWJHqeG4JDEdJY7k49HQVDD0WxImcPW3QNsI0Y7hx-KzUWMj6yl4ZuaOYHPo43B45eq_j22T08bvjzwhjHWgRiGRrOArsBc_8phy9HzTOXs"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=KoLaHxuTB7Hg33XDXD8bTVhlbBubML7WwLCKSi_9gFdihcC2svKmhD-ZOXV3gvUp4ua19o-LhKJDA_ZoB7WX86CysCWiEC2240YoE7zJ4cmPdsQrPJ9G8PZGlEGPrr7SLy37-reTQiIO4XVj"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=kP1O0arE0_0u8deHZxoejK5ibeXyT0C6qSiUkT3t7gPvDKKU-5kudG_4nbKoT7ojApuUcOZwDNaKOa02SxbP9H6BwjRkEmzG273gwH5Pamniwzk9ci8JD7dfVc8m7VxUPTRnwi-8RUzhBRSM"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=xviKB6UuD8zKRzdPsFJgpjVA7DFUbW5QcaXFl45BXNri82S-uLVzk0XOWVCDZQd1LwKH9DMM09vvNzzUJzLJB09ycD40uJJCs_YwzijXyZ2lPu_NIgWYJHLEE4wl42A6LQcEHB-a9xJvMjrM"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=CXGZCvj0R4Y2QLuTpFFmjKHLxUFjYX58RlIynv1gB-UjEhgp0f30OZta3zrI0wT91eEVgLY-Z9tn9Tghs1gi8ZWoJWrVooQSDY3iJx_pq_fDqzoR-vc4JVr9teqv0knorDaGiYcALDgtmXYp"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=r4O4SYMWF2kIjKNAkoIJ2XaUpVZXzjczmxcI6Wu0VMHVN9waq6P3c0EBi65Q9-SaFr0_u4eTM0-4FZaXhblfmPzpDKdkrxcCpwKpOWiSJg-5SEFTt5qfeofJnLGSdN8r5LO6qAmv6EFB1FHw"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=w29Vx6zmo7oIw6C1lhYK_62YrR3TK3FIVV4zXJSdjXrMRfHJey3Wv95DyeqGVvbRHwM43x3FyOJbQ5zw_6GizeoQkfsDVKRmLhaqXmKd3hTsgbJT0RJXZoWh0u-BiZyFmgdYgStJnx_1_f4E"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=hCBYQXNphNnoqi4tpuTUHBfirwxRSmk6LfSpMjf1bBualgJmqtHYoFstFXNibVDr6QFf2ZL3FwcNGJ8ySEXXDJ4sQZtpfaQlGZvc79xfK7PvuHiYMn2X7HuvE85qqhyCKwW7Ef45-pwOCs9f"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=me917Vfo4jqkP1_wWuZfCgw1ZblB8e9Ar6RGfdTYMKEP1WoprVvrdeuzHZDTGWVnG6uEdsVv73vjvLCLuXA10CNKO4vSzfpZetPyiZpARK9qkqc8unIocEXc7ix3vSQSIQE-Ym8KDkI_ldvn"));
        recyclerViewAdapter= new RecyclerViewAdapter(this.itemsList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected Context getContext() {
        return UniqueWallpaperActivity.this;
    }
}