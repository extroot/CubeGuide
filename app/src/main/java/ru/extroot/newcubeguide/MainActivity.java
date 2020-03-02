package ru.extroot.newcubeguide;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ExpandableDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import org.jetbrains.annotations.NotNull;


public class MainActivity extends AppCompatActivity {
    /*
    TODO: Rewrite full app
    3x3x3:
        F2l     - 1
        OLL     - 2
        PLL     - 3
        TODO: Expert F2L, OLL, PLL
    3x3x3 PRO:
        VHF2L   - 4
        OPF2L   - 5
        COLL    - 6
        OLE     - 10
        OLC     - 11
        WV      - 12
        SV      - 13
    3x3x3 OH:
        OH_OLL  - 7
        OH_PLL  - 8
        OH_COLL - 14
    2x2x2 EG:
        CLL     - 9
        EG1     - 15
        EG2     - 16
        LEG1    - 17
        TODO: ORTEGA
     */
    private Toolbar toolbar;

    private static final int F2L_ID     = 1;
    private static final int PLL_ID     = 2;
    private static final int OLL_ID     = 3;
    private static final int VHF2L_ID   = 4;
    private static final int OPF2L_ID   = 5;
    private static final int COLL_ID    = 6;
    private static final int OH_OLL_ID  = 7;
    private static final int OH_PLL_ID  = 8;
    private static final int CLL_ID     = 9;
    private static final int OLE_ID     = 10;
    private static final int OLC_ID     = 11;
    private static final int WV_ID      = 12;
    private static final int SV_ID      = 13;
    private static final int OH_COLL_ID = 14;
    private static final int EG1_ID     = 15;
    private static final int EG2_ID     = 16;
    private static final int LEG1_ID    = 17;

    private int mode = 1;
    public int picLen = 250;
    public int textSize = 16;
    public String locale = "en_";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.f2l_header));
        setSupportActionBar(toolbar);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels < 1000) {
            picLen = 150;
            textSize = 14;
        }
        if ((getResources().getConfiguration().locale.getLanguage()).equals("ru")) {
            locale = "ru_";
        }
        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withDelayOnDrawerClose(-1)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(getResources().getString(R.string.header_3x3x3))
                                .withSelectable(false)
                                .withEnabled(false)
                                .withIconTintingEnabled(true),
                        new SecondaryDrawerItem()
                                .withName(getResources().getString(R.string.f2l_header))
                                .withLevel(2)
                                .withIconTintingEnabled(true)
                                .withIdentifier(F2L_ID),
                        new SecondaryDrawerItem()
                                .withName(getResources().getString(R.string.oll_header))
                                .withLevel(2)
                                .withIconTintingEnabled(true)
                                .withIdentifier(OLL_ID),
                        new SecondaryDrawerItem()
                                .withName(getResources().getString(R.string.pll_header))
                                .withLevel(2)
                                .withIconTintingEnabled(true)
                                .withIdentifier(PLL_ID),
                        new ExpandableDrawerItem()
                                .withName(getResources().getString(R.string.header_3x3x3_oh))
                                .withSelectable(false)
                                .withIconTintingEnabled(true)
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.oh_oll_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(OH_OLL_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.oh_pll_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(OH_PLL_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.oh_coll_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(OH_COLL_ID)),
                        new ExpandableDrawerItem()
                                .withName(getResources().getString(R.string.header_3x3x3_pro))
                                .withSelectable(false)
                                .withIconTintingEnabled(true)
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.vh_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(VHF2L_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.op_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(OPF2L_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.coll_header))
                                                .withLevel(2)
                                                .withTextColor(R.color.colorBlack)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(COLL_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.ole_header))
                                                .withLevel(2)
                                                .withTextColor(R.color.colorBlack)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(OLE_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.olc_header))
                                                .withLevel(2)
                                                .withTextColor(R.color.colorBlack)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(OLC_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.wv_header))
                                                .withLevel(2)
                                                .withTextColor(R.color.colorBlack)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(WV_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.sv_header))
                                                .withLevel(2)
                                                .withTextColor(R.color.colorBlack)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(SV_ID)),
                        new ExpandableDrawerItem()
                                .withName(getResources().getString(R.string.header_2x2x2))
                                .withSelectable(false)
                                .withIconTintingEnabled(true)
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.cll_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(CLL_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.eg1_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(EG1_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.eg2_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(EG2_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.leg1_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(LEG1_ID))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, @NotNull IDrawerItem drawerItem) {
                        mode = (int) drawerItem.getIdentifier();
                        int count = 40;
                        String modeName = "f2l";
                        switch (mode) {
                            case F2L_ID:                                       break;
                            case OLL_ID:     count = 57; modeName = "oll";     break;
                            case PLL_ID:     count = 21; modeName = "pll";     break;
                            case VHF2L_ID:   count = 31; modeName = "vh";      break;
                            case OPF2L_ID:   count = 28; modeName = "op";      break;
                            case COLL_ID:    count = 39; modeName = "coll";    break;
                            case OH_OLL_ID:  count = 57; modeName = "oh_oll";  break;
                            case OH_PLL_ID:  count = 21; modeName = "oh_pll";  break;
                            case CLL_ID:     count = 39; modeName = "cll";     break;
                            case OLE_ID:     count = 25; modeName = "ole";     break;
                            case OLC_ID:     count = 26; modeName = "olc";     break;
                            case WV_ID:      count = 51; modeName = "wv";      break;
                            case SV_ID:      count = 25; modeName = "sv";      break;
                            case OH_COLL_ID: count = 39; modeName = "oh_coll"; break;
                            case EG1_ID:     count = 39; modeName = "eg1";     break;
                            case EG2_ID:     count = 39; modeName = "eg2";     break;
                            case LEG1_ID:    count = 39; modeName = "leg1";     break;
                        }
                        draw(modeName, count);
                        return false;
                        }
                    })
                .withSavedInstance(savedInstanceState);
        Drawer mDrawer = drawerBuilder.build();
        mDrawer.setSelection(F2L_ID);
    }

    void draw(String picName, int pic_count) {
        String[] algoritms = getResources().getStringArray(getResources().getIdentifier(picName, "array", getPackageName()));
        toolbar.setTitle(getResources().getString(getResources().getIdentifier(picName + "_header", "string", getPackageName())));

        switch (picName) {
            case "oh_oll":  picName = "oll";  break;
            case "oh_pll":  picName = "pll";  break;
            case "oh_coll": picName = "coll"; break;
            case "eg1":
            case "leg1":
            case "eg2":
                picName = "cll";  break;
        }


        String[] titles = getResources().getStringArray(getResources().getIdentifier(locale + "oll" + "_headers", "array", getPackageName()));
        if (picName.equals("pll") || picName.equals("coll") || picName.equals("ole") || picName.equals("cll")) {
            titles = getResources().getStringArray(getResources().getIdentifier(locale + picName + "_headers", "array", getPackageName()));
        }
        LinearLayout mainLayout = findViewById(R.id.main_view);
        mainLayout.removeAllViews();
        mainLayout.setPadding(0,5,0,40);

        ScrollView scrollview = findViewById(R.id.main_scroll);
        scrollview.scrollTo(0,0);
        for (int i = 0, j = 0; i <= pic_count; i++, j += 3) {
            if ((picName.equals("oll") || picName.equals("pll")) && i == 3) {
                continue;
            }

            LinearLayout line = new LinearLayout(this);
            line.setOrientation(LinearLayout.HORIZONTAL);
            line.setPadding(20, 30, 0, 0);

            ImageView image = new ImageView(this);
            String name = picName + i;
            image.setImageResource(getResources().getIdentifier(name, "drawable", getPackageName()));
            image.setLayoutParams(new LinearLayout.LayoutParams(picLen, picLen));

            LinearLayout algLayout = new LinearLayout(this);
            algLayout.setOrientation(LinearLayout.VERTICAL);
            algLayout.setPadding(20,0,0,0);
            algLayout.setGravity(Gravity.CENTER);
            for (int n = 0; n < 3; n++) {
                TextView text = new TextView(this);
                text.setText(algoritms[j + n]);
                text.setTextColor(getResources().getColor(R.color.colorBlack));
                text.setTextSize(textSize);
                algLayout.addView(text);
            }

            if ((picName.equals("oll") || picName.equals("pll") || picName.equals("coll") || picName.equals("ole") || picName.equals("cll")) && !titles[i].equals("0")) {
                TextView title = new TextView(this);
                title.setTextColor(getResources().getColor(R.color.colorBlack));
                title.setText(titles[i]);
                title.setPadding(0,20,0,0);
                title.setTextSize(textSize + 4);
                title.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                mainLayout.addView(title);
            }
            line.setId(100 + i);

            line.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    return false;
                }
            });

            line.addView(image);
            line.addView(algLayout);
            mainLayout.addView(line);
        }
    }
}
