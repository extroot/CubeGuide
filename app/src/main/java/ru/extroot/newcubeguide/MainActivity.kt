package ru.extroot.newcubeguide

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.gms.ads.*
import com.google.android.material.color.MaterialColors
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener
import com.mikepenz.materialdrawer.model.*
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import java.util.*


class MainActivity : AppCompatActivity() {
    private val F2L_ID: Long = 1
    private val PLL_ID: Long = 2
    private val OLL_ID: Long = 3
    private val VHF2L_ID: Long = 4
    private val OPF2L_ID: Long = 5
    private val COLL_ID: Long = 6

    private val OH_OLL_LH_ID: Long = 7
    private val OH_PLL_LH_ID: Long = 8

    private val CLL_ID: Long = 9
    private val OLE_ID: Long = 10
    private val OLC_ID: Long = 11
    private val WV_ID: Long = 12
    private val SV_ID: Long = 13

    private val OH_COLL_LH_ID: Long = 14

    private val EG1_ID: Long = 15
    private val EG2_ID: Long = 16
    private val LEG1_ID: Long = 17
    private val VLS_ID: Long = 18
    private val MW_ID: Long = 19
    private val MG_OLL_ID: Long = 20
    private val MG_PLL_ID: Long = 21
    private val EASY_3_ID: Long = 22
    private val ORTEGA_ID: Long = 23
    private val TCLLP_ID: Long = 24

    // private val POLL_ID: Long    = 25;
    private val L2C_ID: Long = 26
    private val L2E_ID: Long = 27
    private val ELL_ID: Long = 28
    private val OELLCP_ID: Long = 29
    private val PLL_SC_ID: Long = 30
    private val L3C_ID: Long = 31
    private val L3E_ID: Long = 32
    private val EO_ID: Long = 33
    private val CP_ID: Long = 34
    private val EP_ID: Long = 35
    private val UZ_ID: Long = 36

    private val ZBLL_T_ID: Long = 37
    private val ZBLL_U_ID: Long = 38
    private val ZBLL_L_ID: Long = 39
    private val ZBLL_H_ID: Long = 40
    private val ZBLL_PI_ID: Long = 41
    private val ZBLL_SUNE_ID: Long = 42
    private val ZBLL_ANTISUNE_ID: Long = 43

    private val OH_OLL_RH_ID: Long = 44
    private val OH_PLL_RH_ID: Long = 45
    private val OH_COLL_RH_ID: Long = 46

    private val NUMBER_SWITCH_ID: Long = 101
    private val REVIEW_ID: Long        = 102
    private val TRANSLATE_ID: Long     = 103

    // private val EASY_4_ID: Long = 47;

    private val APP_PNAME = "ru.extroot.newcubeguide"
    private val PREFS_FILE = "main"
    private val PREF_NUMB = "numbering"

    var numbering: Boolean = false
    var mode: String = "easy3"
    var picMode: String = "easy3"

    var picLen: Int = 250
    var textSize: Int = 16

    lateinit var result: Drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setTitle(R.string.easy3_header)
        setSupportActionBar(toolbar)

        MobileAds.initialize(this) {}
        /*
        val testDeviceIds = Arrays.asList("395B9D062C64202DCC84E20BCC69A4DD")
        val configuration = RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build()
        MobileAds.setRequestConfiguration(configuration)
         */

        val displaymetrics = DisplayMetrics()




        // Java function to update display metrics.
        // idk how correctly use it in kotlin, but it's working like this
        windowManager.defaultDisplay.getMetrics(displaymetrics)

        if (displaymetrics.widthPixels < 700) { 
            picLen = 100
            textSize = 14
        } else if (displaymetrics.widthPixels < 1000) {
            picLen = 150
            textSize = 14
        }

        val settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
        val prefEditor = settings.edit()
        prefEditor.apply()
        numbering = settings.getBoolean(PREF_NUMB, false)

        result = DrawerBuilder()
            .withActivity(this)
            .withToolbar(toolbar)
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.easy3_header).withIdentifier(EASY_3_ID),
                ExpandableDrawerItem().withName(R.string.header_3x3x3).withSelectable(false)
                    .withSubItems(
                        SecondaryDrawerItem().withName(R.string.f2l_header).withIdentifier(F2L_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.oll_header).withIdentifier(OLL_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.pll_header).withIdentifier(PLL_ID).withLevel(2)
                    ),
                ExpandableDrawerItem().withName(R.string.header_3x3x3_oh).withSelectable(false)
                    .withSubItems(
                        ExpandableDrawerItem().withName(R.string.header_3x3x3_oh_lh).withSelectable(false).withLevel(2).withSubItems(
                            SecondaryDrawerItem().withName(R.string.oh_oll_lh_header).withIdentifier(OH_OLL_LH_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.oh_pll_lh_header).withIdentifier(OH_PLL_LH_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.oh_coll_lh_header).withIdentifier(OH_COLL_LH_ID).withLevel(3)
                        ),
                        ExpandableDrawerItem().withName(R.string.header_3x3x3_oh_rh).withSelectable(false).withLevel(2).withSubItems(
                            SecondaryDrawerItem().withName(R.string.oh_oll_rh_header).withIdentifier(OH_OLL_RH_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.oh_pll_rh_header).withIdentifier(OH_PLL_RH_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.oh_coll_rh_header).withIdentifier(OH_COLL_RH_ID).withLevel(3)
                        )
                    ),
                ExpandableDrawerItem().withName(R.string.header_3x3x3_pro).withSelectable(false).withSubItems(
                        ExpandableDrawerItem().withName(R.string.ls_ll_header).withSelectable(false).withLevel(2).withSubItems(
                            SecondaryDrawerItem().withName(R.string.vh_header).withIdentifier(VHF2L_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.op_header).withIdentifier(OPF2L_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.ole_header).withIdentifier(OLE_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.olc_header).withIdentifier(OLC_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.wv_header).withIdentifier(WV_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.sv_header).withIdentifier(SV_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.vls_header).withIdentifier(VLS_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.mw_header).withIdentifier(MW_ID).withLevel(3)
                        ),
                        ExpandableDrawerItem().withName(R.string.ll_header).withSelectable(false).withLevel(2).withSubItems(
                            SecondaryDrawerItem().withName(R.string.coll_header).withIdentifier(COLL_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.ell_header).withIdentifier(ELL_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.oellcp_header).withIdentifier(OELLCP_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.pll_sc_header).withIdentifier(PLL_SC_ID).withLevel(3)
                        ),
                        ExpandableDrawerItem().withName(R.string.zbll_header).withSelectable(false).withLevel(2).withSubItems(
                            SecondaryDrawerItem().withName(R.string.zbll_t_header).withIdentifier(ZBLL_T_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.zbll_u_header).withIdentifier(ZBLL_U_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.zbll_l_header).withIdentifier(ZBLL_L_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.zbll_h_header).withIdentifier(ZBLL_H_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.zbll_pi_header).withIdentifier(ZBLL_PI_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.zbll_sune_header).withIdentifier(ZBLL_SUNE_ID).withLevel(3),
                            SecondaryDrawerItem().withName(R.string.zbll_antisune_header).withIdentifier(ZBLL_ANTISUNE_ID).withLevel(3)
                        ),
                    ),
                PrimaryDrawerItem().withName(R.string.uz_header).withIdentifier(UZ_ID),
                ExpandableDrawerItem().withName(R.string.header_2x2x2).withSelectable(false).withSubItems(
                        SecondaryDrawerItem().withName(R.string.ortega_header).withIdentifier(ORTEGA_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.cll_header).withIdentifier(CLL_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.eg1__header).withIdentifier(EG1_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.eg2__header).withIdentifier(EG2_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.leg1__header).withIdentifier(LEG1_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.tcllp_header).withIdentifier(TCLLP_ID).withLevel(2)
                    ),
                ExpandableDrawerItem().withName(R.string.header_5x5x5).withSelectable(false).withSubItems(
                        SecondaryDrawerItem().withName(R.string.l2c_header).withIdentifier(L2C_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.l2e_header).withIdentifier(L2E_ID).withLevel(2)
                    ),
                ExpandableDrawerItem().withName(R.string.header_pyraminx).withSelectable(false).withSubItems(
                        SecondaryDrawerItem().withName(R.string.l3c_header).withIdentifier(L3C_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.l3e_header).withIdentifier(L3E_ID).withLevel(2)
                    ),
                ExpandableDrawerItem().withName(R.string.header_square1).withSelectable(false).withSubItems(
                        SecondaryDrawerItem().withName(R.string.eo_header).withIdentifier(EO_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.cp_header).withIdentifier(CP_ID).withLevel(2),
                        SecondaryDrawerItem().withName(R.string.ep_header).withIdentifier(EP_ID).withLevel(2)
                    ),

                DividerDrawerItem(),
                SwitchDrawerItem()
                    .withName(R.string.number_switch)
                    .withIcon(R.drawable.ic_format_list_numbered_black_24dp)
                    .withIconTintingEnabled(true).withSelectable(false)
                    .withIdentifier(NUMBER_SWITCH_ID).withChecked(numbering)
                    .withOnCheckedChangeListener(object : OnCheckedChangeListener {
                        override fun onCheckedChanged(drawerItem: IDrawerItem<*>, buttonView: CompoundButton, isChecked: Boolean) {
                            numbering = isChecked
                            prefEditor.putBoolean(PREF_NUMB, numbering)
                            prefEditor.apply()
                            draw()
                            // result.closeDrawer()
                        }
                    }),
                PrimaryDrawerItem().withName(R.string.review_btn).withIcon(R.drawable.ic_outline_favorite_border_24px).withIconTintingEnabled(true).withSelectable(false).withIdentifier(REVIEW_ID),
                //PrimaryDrawerItem().withName(R.string.translate_btn).withIcon(R.drawable.ic_outline_translate_18px).withIconTintingEnabled(true).withSelectable(false).withIdentifier(TRANSLATE_ID)

            )
            .withOnDrawerItemClickListener(object : Drawer.OnDrawerItemClickListener {
                override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {

                    when (drawerItem.identifier) {
                        EASY_3_ID -> { picMode = "easy3"; mode = picMode }
                        F2L_ID -> { picMode = "f2l"; mode = picMode }
                        OLL_ID -> { picMode = "oll"; mode = picMode }
                        PLL_ID -> { picMode = "pll"; mode = picMode }

                        OH_OLL_LH_ID ->   { mode = "oh_oll_lh"; picMode = "oll" }
                        OH_PLL_LH_ID ->   { mode = "oh_pll_lh"; picMode = "pll" }
                        OH_COLL_LH_ID ->  { mode = "oh_coll_lh"; picMode = "coll" }
                        OH_OLL_RH_ID ->   { mode = "oh_oll_rh"; picMode = "oll" }
                        OH_PLL_RH_ID ->   { mode = "oh_pll_rh"; picMode = "pll" }
                        OH_COLL_RH_ID ->  { mode = "oh_coll_rh"; picMode = "coll" }

                        COLL_ID ->  { picMode = "coll"; mode = picMode }
                        OPF2L_ID -> { picMode = "op"; mode = picMode }
                        VHF2L_ID -> { picMode = "vh"; mode = picMode }
                        WV_ID ->    { picMode = "wv"; mode = picMode }
                        SV_ID ->    { picMode = "sv"; mode = picMode }
                        MW_ID ->    { picMode = "mw"; mode = picMode }
                        OLE_ID ->   { picMode = "ole"; mode = picMode }
                        OLC_ID ->   { picMode = "olc"; mode = picMode }
                        VLS_ID ->   { picMode = "vls"; mode = picMode }
                        ELL_ID ->   { picMode = "ell"; mode = picMode }

                        OELLCP_ID ->  { picMode = "oellcp"; mode = picMode }
                        PLL_SC_ID ->  { picMode = "pll_sc"; mode = picMode }
                        ZBLL_T_ID ->  { picMode = "zbll_t"; mode = picMode }
                        ZBLL_U_ID ->  { picMode = "zbll_u"; mode = picMode }
                        ZBLL_L_ID ->  { picMode = "zbll_l"; mode = picMode }
                        ZBLL_H_ID ->  { picMode = "zbll_h"; mode = picMode }
                        ZBLL_PI_ID ->        { picMode = "zbll_pi"; mode = picMode }
                        ZBLL_SUNE_ID ->      { picMode = "zbll_sune"; mode = picMode }
                        ZBLL_ANTISUNE_ID ->  { picMode = "zbll_antisune"; mode = picMode }

                        UZ_ID ->     { picMode = "uz"; mode = picMode }
                        CLL_ID ->    { picMode = "cll"; mode = picMode }
                        ORTEGA_ID -> { picMode = "ortega"; mode = picMode }
                        EG1_ID ->    { mode = "eg1_"; picMode = "cll" }
                        EG2_ID ->    { mode = "eg2_"; picMode = "cll" }
                        LEG1_ID ->   { mode = "leg1_"; picMode = "cll" }
                        TCLLP_ID ->  { picMode = "tcllp"; mode = picMode }
                        L2C_ID ->    { picMode = "l2c"; mode = picMode }
                        L2E_ID ->    { picMode = "l2e"; mode = picMode }
                        MG_OLL_ID -> { picMode = "mg_oll"; mode = picMode }
                        MG_PLL_ID -> { picMode = "mg_pll"; mode = picMode }
                        L3C_ID ->    { picMode = "l3c"; mode = picMode }
                        L3E_ID ->    { picMode = "l3e"; mode = picMode }
                        EO_ID ->     { picMode = "eo"; mode = picMode }
                        CP_ID ->     { picMode = "cp"; mode = picMode }
                        EP_ID ->     { picMode = "ep"; mode = picMode }
                        REVIEW_ID -> {
                            try {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$APP_PNAME"))
                                )
                            } catch (e: Exception) { e.printStackTrace() }
                            return false
                        }
                        TRANSLATE_ID -> {
                            try {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://crowdin.com/project/cubeguide"))
                                )
                            } catch (e: Exception) { e.printStackTrace() }
                            return false
                        }
                        else -> return true
                    }
                    toolbar.title = getString(resources.getIdentifier(mode + "_header", "string", packageName))
                    draw()
                    return false
                }
            })
            .build()
        result.setSelection(EASY_3_ID)
    }

    fun draw() {
        var offset = 0

        val mainLayout = findViewById<LinearLayout>(R.id.main_view)
        mainLayout.removeAllViews()

        val scrollview = findViewById<ScrollView>(R.id.main_scroll)
        scrollview.scrollTo(0, 0)

        val adView = AdView(this)
        adView.adSize = AdSize.SMART_BANNER
        adView.adUnitId = "ca-app-pub-9813480536729767/9546708066"
        mainLayout.addView(adView)
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)


        if ("easy3" == mode) {
            val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val childLayout: View = inflater.inflate(R.layout.easy3, findViewById(R.id.easy3))
            mainLayout.addView(childLayout)
            return
        }

        for (i in 0 until getString(resources.getIdentifier(mode + "_count", "string", packageName)).toInt()) {
            val image = ImageView(this)
            image.setImageResource(resources.getIdentifier(picMode + i, "drawable", packageName))
            image.layoutParams = LinearLayout.LayoutParams(picLen, picLen)

            val line = LinearLayout(ContextThemeWrapper(this, R.style.line))
            val alg = getString(resources.getIdentifier(mode + i, "string", packageName))

            if ("" == alg) {
                offset++
                continue
            }

            if (numbering) {
                val numberText = TextView(this)
                numberText.text = (i + 1 - offset).toString()
                numberText.setPadding(0, 0, 10, 0)
                numberText.gravity = Gravity.CENTER_VERTICAL
                numberText.textSize = textSize.toFloat()
                line.addView(numberText)
            }

            val algText = TextView(this)
            algText.text = alg
            algText.gravity = Gravity.CENTER_VERTICAL
            algText.textSize = textSize.toFloat()
            algText.setPadding(15, 0, 10, 0)

            if ("l3c" == picMode || "eo" == picMode || "cp" == picMode || "ep" == picMode) {
                image.layoutParams =
                    LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                line.orientation = LinearLayout.VERTICAL
                line.gravity = Gravity.CENTER_HORIZONTAL
                algText.gravity = Gravity.CENTER_HORIZONTAL
            }

            val title = getString(resources.getIdentifier(picMode + i + "_title", "string", packageName))

            if ("" != title) {
                val titleView = TextView(this)
                titleView.text = title
                titleView.setPadding(0, 20, 0, 0)
                titleView.textSize = (textSize + 4).toFloat()
                titleView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
                mainLayout.addView(titleView)
            } else if (i != 0) {
                val sep = View(this)
                val sepParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1)
                sep.layoutParams = sepParams
                // sep.setBackgroundColor(getColor(R.color.material_drawer_divider))
                sep.setBackgroundColor(MaterialColors.getColor(this, R.attr.dividerColor, Color.BLACK))
                sep.setPadding(3, 1, 1, 3)
                mainLayout.addView(sep)
            }
            line.addView(image)
            line.addView(algText)
            mainLayout.addView(line)
        }
    }
}