package ru.extroot.newcubeguide;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    3x3x3 EASY  - 22

    TODO: easy Fridrich, 4x4x4, 5x5x5, 2x2x2

    3x3x3 OH:
        OH_OLL  - 7
        OH_PLL  - 8
        OH_COLL - 14

    3x3x3 PRO:
        VHF2L   - 4
        OPF2L   - 5
        COLL    - 6
        OLE     - 10
        OLC     - 11
        WV      - 12
        SV      - 13
        VLS     - 18  TODO: VLS titles
        MW      - 19

    2x2x2 EG:
        CLL     - 9
        EG1     - 15
        EG2     - 16
        TODO: Ortega
        LEG1    - 17

    Megaminx:
        MG_OLL  - 20
        PLLR    - 21
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
    private static final int VLS_ID     = 18;
    private static final int MW_ID      = 19;
    private static final int MG_OLL_ID  = 20;
    private static final int PLLR_ID    = 21;
    private static final int easy_3_ID  = 22;

    private int picLen = 250;
    private int textSize = 16;
    String picMode, mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getResources().getString(R.string.f2l_header));
        setSupportActionBar(toolbar);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        if (displaymetrics.widthPixels < 700) {
            picLen = 100 ;
            textSize = 14;
        } else if (displaymetrics.widthPixels < 1000) {
            picLen = 150;
            textSize = 14;
        }
        System.out.println(displaymetrics.widthPixels);
        System.out.println(picLen);


        DrawerBuilder drawerBuilder = new DrawerBuilder()
                .withActivity(this)
                .withDelayOnDrawerClose(-1)
                .withToolbar(toolbar)
                .addDrawerItems(
                        new PrimaryDrawerItem()
                                .withName(getResources().getString(R.string.easy3_header))
                                .withSelectable(false)
                                .withIconTintingEnabled(true)
                                .withIdentifier(easy_3_ID),
                        new ExpandableDrawerItem()
                                .withName(getResources().getString(R.string.header_3x3x3))
                                .withSelectable(false)
                                .withIconTintingEnabled(true)
                                .withSubItems(
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
                                                .withIdentifier(PLL_ID)),
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
                                                .withIdentifier(SV_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.vls_header))
                                                .withLevel(2)
                                                .withTextColor(R.color.colorBlack)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(VLS_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.mw_header))
                                                .withLevel(2)
                                                .withTextColor(R.color.colorBlack)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(MW_ID)),
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
                                                .withName(getResources().getString(R.string.eg1__header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(EG1_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.eg2__header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(EG2_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.leg1__header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(LEG1_ID)),
                        new ExpandableDrawerItem()
                                .withName(getResources().getString(R.string.header_mg))
                                .withSelectable(false)
                                .withIconTintingEnabled(true)
                                .withSubItems(
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.mg_oll_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(MG_OLL_ID),
                                        new SecondaryDrawerItem()
                                                .withName(getResources().getString(R.string.pllr_header))
                                                .withLevel(2)
                                                .withIconTintingEnabled(true)
                                                .withIdentifier(PLLR_ID))
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, @NotNull IDrawerItem drawerItem) {

                        switch ((int) drawerItem.getIdentifier()) {
                            case F2L_ID: mode = picMode = "f2l"; break;
                            case OLL_ID: mode = picMode = "oll"; break;
                            case OH_OLL_ID: mode = "oh_oll"; picMode = "oll"; break;

                            case PLL_ID: mode = picMode = "pll"; break;
                            case OH_PLL_ID: mode = "oh_pll"; picMode = "pll"; break;

                            case COLL_ID: mode = picMode = "coll"; break;
                            case OH_COLL_ID: mode = "oh_coll"; picMode = "coll"; break;

                            case OPF2L_ID: mode = picMode = "op"; break;
                            case VHF2L_ID: mode = picMode = "vh"; break;
                            case WV_ID: mode = picMode = "wv"; break;
                            case SV_ID: mode = picMode = "sv"; break;
                            case MW_ID: mode = picMode = "mw"; break;
                            case OLE_ID: mode = picMode = "ole"; break;
                            case OLC_ID: mode = picMode = "olc"; break;
                            case VLS_ID: mode = picMode = "vls"; break;

                            case CLL_ID: mode = picMode = "cll"; break;
                            case EG1_ID: mode = "eg1_"; picMode = "cll"; break;
                            case EG2_ID: mode = "eg2_"; picMode = "cll"; break;
                            case LEG1_ID: mode = "leg1_"; picMode = "cll"; break;

                            case MG_OLL_ID: mode = picMode = "mg_oll"; break;
                            case PLLR_ID: mode = picMode = "pllr"; break;

                            case easy_3_ID: tutorials_3x3x3(); return false;
                            default: return true;
                        }
                        newDraw();

                        return false;
                        }
                    })
                .withSavedInstance(savedInstanceState);
        Drawer mDrawer = drawerBuilder.build();
        mDrawer.setSelection(easy_3_ID);
    }


    void tutorials_3x3x3() {
        toolbar.setTitle(getResources().getString(R.string.easy3_header));

        LinearLayout mainLayout = findViewById(R.id.main_view);
        mainLayout.removeAllViews();

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        View childLayout = inflater.inflate(R.layout.easy3,
                (ViewGroup) findViewById(R.id.easy3));
        mainLayout.addView(childLayout);
    }


    void newDraw() {
        toolbar.setTitle(getResources().getString(getResources().getIdentifier(mode + "_header", "string", getPackageName())));
        LinearLayout mainLayout = findViewById(R.id.main_view);
        mainLayout.removeAllViews();
        mainLayout.setPadding(0,5,0,40);

        ScrollView scrollview = findViewById(R.id.main_scroll);
        scrollview.scrollTo(0,0);
        for (int i = 0; i < Integer.parseInt(getResources().getString(getResources().getIdentifier(mode + "_count", "string", getPackageName()))); i++) {
            int algCount = Integer.parseInt(getResources().getString(getResources().getIdentifier(mode + i + "_count", "string", getPackageName())));
            if (algCount == 0) continue;
            LinearLayout line = new LinearLayout(new ContextThemeWrapper(this, R.style.line));
            ImageView image = new ImageView(this);
            image.setImageResource(getResources().getIdentifier(picMode + i, "drawable", getPackageName()));
            image.setLayoutParams(new LinearLayout.LayoutParams(picLen, picLen));

            LinearLayout algLayout = new LinearLayout(this);
            algLayout.setOrientation(LinearLayout.VERTICAL);
            algLayout.setPadding(20,0,0,0);

            for (int n = 0; n < algCount; n++) {
                String alg = getResources().getString(getResources().getIdentifier(mode + i + "_" + n, "string", getPackageName()));
                TextView text = new TextView(this);
                text.setText(alg);
                text.setTextColor(getResources().getColor(R.color.colorBlack));
                text.setTextSize(textSize);
                algLayout.addView(text);
            }
            String title = getResources().getString(getResources().getIdentifier(picMode + i + "_title", "string", getPackageName()));
            if (!title.equals("")) {
                TextView titleView = new TextView(this);
                titleView.setTextColor(getResources().getColor(R.color.colorBlack));
                titleView.setText(title);
                titleView.setPadding(0,20,0,0);
                titleView.setTextSize(textSize + 4);
                titleView.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                mainLayout.addView(titleView);
            } else {
                    View sep = new View(this);
                    LinearLayout.LayoutParams sepParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
                    sep.setLayoutParams(sepParams);
                    sep.setBackgroundColor(getResources().getColor(R.color.material_drawer_divider));
                    sep.setPadding(3, 1, 1, 3);
                    mainLayout.addView(sep);
                }
            line.addView(image);
            line.addView(algLayout);
            mainLayout.addView(line);
        }
    }
}
