package ru.extroot.newcubeguide


import android.app.AlertDialog
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.material.color.MaterialColors
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener
import com.mikepenz.materialdrawer.model.*
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikhaellopez.ratebottomsheet.RateBottomSheet
import com.mikhaellopez.ratebottomsheet.RateBottomSheetManager
import ru.extroot.newcubeguide.databinding.ActivityMainBinding


class MainActivity: AppCompatActivity() {
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

    // TODO: CFOP about page
    // private val CFOP_ABOUT_ID: Long = 47

    private val COUNTING_SWITCH_ID: Long = 101
    private val REVIEW_ID: Long          = 102

    // private val EASY_4_ID: Long = 47;

    private val PREFS_FILE = "main"
    private val PREF_NUMB = "numbering"

    private var isCounting: Boolean = false
    private var mode: String = "easy3"
    private var picMode: String = "easy3"

    private var picLen: Int = 250
    private var textSize: Int = 16

    private lateinit var result: Drawer
    private lateinit var topAdView: AdView
    private lateinit var bottomAdView: AdView
    private lateinit var adRequest: AdRequest

    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.setTitle(R.string.easy3_header)
        setSupportActionBar(binding.toolbar)

        MobileAds.initialize(this) {}

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

        topAdView = AdView(this)
        topAdView.adSize = AdSize.SMART_BANNER
        topAdView.adUnitId = "ca-app-pub-9813480536729767/9546708066"

        bottomAdView = AdView(this)
        bottomAdView.adSize = AdSize.SMART_BANNER
        bottomAdView.adUnitId = "ca-app-pub-9813480536729767/4920397105"

        adRequest = AdRequest.Builder().build()

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
        val prefEditor = settings.edit()
        prefEditor.apply()
        isCounting = settings.getBoolean(PREF_NUMB, false)

        handleDrawer()

        RateBottomSheetManager(this)
            .setInstallDays(3)
            .setLaunchTimes(8)
            .setRemindInterval(3)
            .monitor()
        RateBottomSheet.showRateBottomSheetIfMeetsConditions(this)
    }

    private fun getModeById(id: Long): String? {
        return when (id) {
            F2L_ID -> { "f2l"}
            OLL_ID -> { "oll"}
            PLL_ID -> { "pll"}

            OH_OLL_LH_ID ->   { "oh_oll_lh" }
            OH_PLL_LH_ID ->   { "oh_pll_lh" }
            OH_COLL_LH_ID ->  { "oh_coll_lh" }
            OH_OLL_RH_ID ->   { "oh_oll_rh" }
            OH_PLL_RH_ID ->   { "oh_pll_rh" }
            OH_COLL_RH_ID ->  { "oh_coll_rh" }

            COLL_ID ->  { "coll"}
            OPF2L_ID -> { "op"}
            VHF2L_ID -> { "vh"}
            WV_ID ->    { "wv"}
            SV_ID ->    { "sv"}
            MW_ID ->    { "mw"}
            OLE_ID ->   { "ole"}
            OLC_ID ->   { "olc"}
            VLS_ID ->   { "vls"}
            ELL_ID ->   { "ell"}

            OELLCP_ID ->  { "oellcp"}
            PLL_SC_ID ->  { "pll_sc"}
            ZBLL_T_ID ->  { "zbll_t"}
            ZBLL_U_ID ->  { "zbll_u"}
            ZBLL_L_ID ->  { "zbll_l"}
            ZBLL_H_ID ->  { "zbll_h"}
            ZBLL_PI_ID ->        { "zbll_pi"}
            ZBLL_SUNE_ID ->      { "zbll_sune"}
            ZBLL_ANTISUNE_ID ->  { "zbll_antisune"}

            UZ_ID ->     { "uz"}
            CLL_ID ->    { "cll"}
            ORTEGA_ID -> { "ortega"}
            EG1_ID ->    { "eg1_"}
            EG2_ID ->    { "eg2_"}
            LEG1_ID ->   { "leg1_"}
            TCLLP_ID ->  { "tcllp"}
            L2C_ID ->    { "l2c"}
            L2E_ID ->    { "l2e"}
            MG_OLL_ID -> { "mg_oll"}
            MG_PLL_ID -> { "mg_pll"}
            L3C_ID ->    { "l3c"}
            L3E_ID ->    { "l3e"}
            EO_ID ->     { "eo"}
            CP_ID ->     { "cp"}
            EP_ID ->     { "ep"}
            else -> { null }
        }
    }

    private fun getPicModeById(id: Long): String? {
        return when (id) {
            OH_OLL_LH_ID ->   { "oll" }
            OH_PLL_LH_ID ->   { "pll" }
            OH_COLL_LH_ID ->  { "coll" }
            OH_OLL_RH_ID ->   { "oll" }
            OH_PLL_RH_ID ->   { "pll" }
            OH_COLL_RH_ID ->  { "coll" }

            EG1_ID ->    { "cll" }
            EG2_ID ->    { "cll" }
            LEG1_ID ->   { "cll" }

            else -> { null }
        }
    }

    private fun handleDrawer() {
        result = DrawerBuilder()
            .withActivity(this)
            .withToolbar(binding.toolbar)
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.easy3_header).withIdentifier(EASY_3_ID),
                ExpandableDrawerItem().withName(R.string.header_3x3x3).withSelectable(false)
                    .withSubItems(
                        // SecondaryDrawerItem().withName(R.string.cfop_about_header).withIdentifier(CFOP_ABOUT_ID).withLevel(2),
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
                ExpandableDrawerItem().withName(R.string.header_mg).withSelectable(false).withSubItems(
                    SecondaryDrawerItem().withName(R.string.mg_oll_header).withIdentifier(MG_OLL_ID).withLevel(2),
                    SecondaryDrawerItem().withName(R.string.mg_pll_header).withIdentifier(MG_PLL_ID).withLevel(2)
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
                    .withIdentifier(COUNTING_SWITCH_ID).withChecked(isCounting)
                    .withOnCheckedChangeListener(object : OnCheckedChangeListener {
                        override fun onCheckedChanged(drawerItem: IDrawerItem<*>, buttonView: CompoundButton, isChecked: Boolean) {
                            isCounting = isChecked
                            val prefEditor = settings.edit()
                            prefEditor.putBoolean(PREF_NUMB, isCounting)
                            prefEditor.apply()
                            draw()
                            // result.closeDrawer()
                        }
                    }),
                PrimaryDrawerItem().withName(R.string.review_btn).
                withIcon(R.drawable.ic_outline_favorite_border_24px).
                withIconTintingEnabled(true).
                withSelectable(false).
                withIdentifier(REVIEW_ID),
                )
            .withOnDrawerItemClickListener(object: Drawer.OnDrawerItemClickListener {
                override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {

                    when (drawerItem.identifier) {
                        EASY_3_ID -> { picMode = "easy3"; mode = picMode }
                        // CFOP_ABOUT_ID -> { picMode = "cfop_about"; mode = picMode }

                        REVIEW_ID -> {
                            RateBottomSheetManager(this@MainActivity)
                                .setDebugForceOpenEnable(true) // False by default
                            RateBottomSheet.showRateBottomSheetIfMeetsConditions(
                                activity = this@MainActivity
                            )
                            return false
                        }
                        else -> {
                            if (getModeById(drawerItem.identifier) == null) return true
                            mode = getModeById(drawerItem.identifier)!!
                            picMode = getPicModeById(drawerItem.identifier) ?: mode
                        }
                    }
                    binding.toolbar.title = getHeader()
                    binding.mainScroll.scrollTo(0, 0)
                    draw()
                    return false
                }
            })
            .build()
        result.setSelection(EASY_3_ID)
    }

    private fun getHeader(): String {
        return getString(resources.getIdentifier(mode + "_header", "string", packageName))
    }

    private fun getImageId(imageNumber: Int): Int {
        return resources.getIdentifier(picMode + imageNumber, "drawable", packageName)
    }

    private fun getAlgCount(): Int {
        return getString(resources.getIdentifier(mode + "_count", "string", packageName)).toInt()
    }

    private fun getAlgTitle(algNumber: Int): String? {
        val title: String?
        val name = picMode + algNumber.toString() + "_title"
        title = getString(resources.getIdentifier(name, "string", packageName))

        if ("" == title) {
            return null
        }
        return title
    }

    private fun getAlgText(algNumber: Int): String? {
        val text: String?
        val name = mode + algNumber.toString()
        text =getString(resources.getIdentifier(name, "string", packageName))

        if ("" == text) {
            return null
        }
        return text
    }

    private fun checkVerticalMode(): Boolean {
        return "l3c" == picMode || "eo" == picMode || "cp" == picMode || "ep" == picMode
    }

    private fun isTextMode(): Boolean {
        return "easy3" == mode || "cfop_about" == mode
    }

    private fun addBottomAds() {
        binding.mainView.addView(bottomAdView)
        bottomAdView.loadAd(adRequest)
    }

    private val clickListener = View.OnClickListener {view ->
        val formulaNumber: Int = view.tag.toString().toInt()
        showCustomAlert(formulaNumber)
    }

    private fun showCustomAlert(formulaNumber: Int) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_formula_preview, null)
        val customDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .show()
//        val btDismiss = dialogView.findViewById<Button>(R.id.btDismissCustomDialog)
//        btDismiss.setOnClickListener {
//            customDialog.dismiss()
//        }
    }


    fun draw() {
        binding.mainView.removeAllViews()

        binding.mainView.addView(topAdView)
        topAdView.loadAd(adRequest)

        if (isTextMode()) {
            val inflater = this.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val childLayout = when (mode) {
                "easy3" -> inflater.inflate(R.layout.easy3, findViewById(R.id.easy3), false)
                "cfop_about" -> inflater.inflate(R.layout.cfop_about, findViewById(R.id.cfop_about), false)
                else -> throw Exception("Invalid Mode")
            }
            binding.mainView.addView(childLayout)
            addBottomAds()
            return
        }

        val imageParams = LinearLayout.LayoutParams(picLen, picLen)
        val imageParamsVertical = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val sepParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2)

        val count = getAlgCount()
        var offset = 0
        for (i in 0 until count) {
            val image = ImageView(this)
            image.setImageResource(getImageId(i))
            image.layoutParams = imageParams

            val alg = getAlgText(i)
            if (alg == null) {
                offset++
                continue
            }

            val title = getAlgTitle(i)

            if (title != null) {
                val titleView = TextView(ContextThemeWrapper(this, R.style.title))
                titleView.text = title
                titleView.textSize = (textSize + 4).toFloat()
                binding.mainView.addView(titleView)
            }
            else if (i != 0) {
                val sep = View(this)
                sep.layoutParams = sepParams
                sep.setBackgroundColor(MaterialColors.getColor(this, R.attr.dividerColor, Color.BLACK))
                sep.setPadding(3, 1, 1, 3)
                binding.mainView.addView(sep)
            }

            val algText = TextView(ContextThemeWrapper(this, R.style.formulaText))
            algText.text = alg
            algText.textSize = textSize.toFloat()

            val line = if (checkVerticalMode()) {
                algText.gravity = Gravity.CENTER_HORIZONTAL
                image.layoutParams = imageParamsVertical
                LinearLayout(ContextThemeWrapper(this, R.style.lineVertical))
            } else {
                LinearLayout(ContextThemeWrapper(this, R.style.line))
            }

            if (isCounting) {
                val countingText = TextView(ContextThemeWrapper(this, R.style.countingText))
                countingText.text = (i + 1 - offset).toString()
                countingText.textSize = textSize.toFloat()
                line.addView(countingText)
            }

            line.addView(image)
            line.addView(algText)

            line.tag = i
            line.setOnClickListener(clickListener)

            binding.mainView.addView(line)
        }

        if (count > 6 || count == 0) {
            addBottomAds()
        }
    }
}