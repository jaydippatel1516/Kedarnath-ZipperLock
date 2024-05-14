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

public class ClassicWallpaperActivity extends BaseActivity {
    List<CustpmItems> itemsList;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classic_wallpaper);
        findViewById(R.id.back).setOnClickListener(v -> onBackPressed());
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        itemsList = new ArrayList();
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=LbJP74SFjv9C-t0Up8k2ufa2EaNgmXK73AzyTtgYCRiPg1L1SFiJhcB2VDF2HH3VIkQtossZiElaFuOF4ZLsAE1BXz0MtDd5sisqvY9pp9dOaijMNXDqOPXByvreQ4I928lxqVVab74P_YFn"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=R6inEQkLXMTeQ5OrlbpKodd_3BX0_0MYpCvlDg9715EQ-1FTNQoa7zoSvl5EQ_LULi7t0otazDfDpNMF-acZ30t7aTOSiyEUBIsE8TcJlWvYnQXLYlSF-oxNYKEqaeH2PgjP_9zvKyNzz1Gc"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=MPJKVqFdg2hlyXrvKrPD_GI9zZg01pz8eHyzUosto9CkMZoPLKrKgaVZeh5D7tljECOTGUTVxYS8VZQqJUNedoU_-Gb757ffJAid9Z3YpFIm9L_bP3DUg1XuzhEIrDOvOmoOiar7ao77RNMz"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=IGbcmkyDZadkanVdQZT1ZDThR5bchQfPTPicsPwJSIYsyLYBmmDWkivEAr_eqI-s8v06vjQCUl7UFq4dZ3-SzSH0JficpaFhjdsgMAAnMpNjuTbQtSwhDMgXR5Tas_dR0qHJ9mchxXGn1IA2"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=2a_ZV0zGGu5la7pq9L0hpsAtjc6xNw4g1DhgZw64Qn5Z0XqVGxkN2cZ763G7owrM6h5yYy38zMgApom2J7DhSLz1aHpms6Z3ALSFCvT69COClvLa2ArF3Ga0CjsGkvJ6o-mCDUkdUfqnroUH"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=uZiquJ-d0KXUMSmjs6yEALiHFY7xXw0pLHOQDyDyjrt5OKg1b5YnMjG90x92sw3IrW9RQVQc_FtaSxbisRyHIID41AnIHjAjijoKk0wRpDGzHaMdLQljSpA-MAEqLbLNH6GefpPMUXbACO1E"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=npqsPOyC5ZqXgbJr2vaNxp8emU2Kt53EAfVMI9AFRNduosk88MuH01PvVTfeZWr9NeEgLLl5rpLzF07YJtqbC3MklJ7-vhj1OZhzc4pWa4Cpp4HyT5tnWR0VuKAddyPXCzBCIeIpYBbjlxPp"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=YykDjp_0xB4cimxb6kJlyEwQK7AAQrKo8DHLLhSX9xKlIlSbPY8uPcCgBDGdi5IQvsdD8QdAGTP6O4ueb8ArqzGgiaM1Tk3XUZtabZglyM1_baiQf6Am-OBIgaN9-SrG00MgyjNgzbXNY5_7"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=OpBkkLx3jI6O7HSumdac9QL6s0AcbhvhrWdHJej0pHoUCXgfnDAP7m3dD1gbm_38dg7wRD5uWXdW0kFtzNSvn8CK-UM8yxzL9f3xIWbcpM-xUQvgimaeTPwbBz0cLgnoLgFUngfJ0-6nkibB"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=LFZTVnb6t_Wx6kCrlV35fOQOTN1YMyNB8ilyv-UvcwLHMZT51jNGaxk7hRdHwCEdeZOrUkLF-uRhfjy3bwFQknDHD9RFqMvDOWsNhJNQL36KTIO1lyxV54BOG0swSzPfMsv7g0mSBPI2HvUe"));
        itemsList.add(new CustpmItems("https://fsa.zobj.net/crop.php?r=tX2eA6bvm2TVJWjnCaPbCtlcuKKQORi8YRyGAzMP0nvgxecDSkuN1sTv3WwwzDSBXSCQ5a6rGjK4g3ndmnJ85dOdrBVz4dQbZr3PX88dwlreqR60FmNfedU3-E3UtNzvpvLg06HRC5C7Wpo9"));
        itemsList.add(new CustpmItems("https://fsb.zobj.net/crop.php?r=ZrMG4-Q5n8iEdtcY-jklWZnC29zWbkhqXZ-IAl_Fj3jiAhcIQA9TVZQz97Hn5Clh841izQq2OjzHauie7Xu4Jk8HjDcxIR-adfwg65xRnDsMR6KuSPoJXDGvlTp2mrheAMsrr_5WBNhVr4_N"));
        recyclerViewAdapter = new RecyclerViewAdapter(this.itemsList, this);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected Context getContext() {
        return ClassicWallpaperActivity.this;
    }
}