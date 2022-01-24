package ru.extroot.newcubeguide

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.*

import io.sentry.Sentry
import io.sentry.SentryLevel
import com.google.android.gms.ads.*

import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.interfaces.OnCheckedChangeListener
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem
import com.mikepenz.materialdrawer.model.*

import com.mikhaellopez.ratebottomsheet.RateBottomSheet
import com.mikhaellopez.ratebottomsheet.RateBottomSheetManager
import io.sentry.UserFeedback

import ru.extroot.newcubeguide.databinding.ActivityMainBinding
import ru.extroot.newcubeguide.databinding.DialogSendFeedbackBinding


class MainActivity: AppCompatActivity() {
    companion object {
        private const val F2L_ID: Long = 1
        private const val PLL_ID: Long = 2
        private const val OLL_ID: Long = 3
        private const val VHF2L_ID: Long = 4
        private const val OPF2L_ID: Long = 5
        private const val COLL_ID: Long = 6

        private const val OH_OLL_LH_ID: Long = 7
        private const val OH_PLL_LH_ID: Long = 8

        private const val CLL_ID: Long = 9
        private const val OLE_ID: Long = 10
        private const val OLC_ID: Long = 11
        private const val WV_ID: Long = 12
        private const val SV_ID: Long = 13

        private const val OH_COLL_LH_ID: Long = 14

        private const val EG1_ID: Long = 15
        private const val EG2_ID: Long = 16
        private const val LEG1_ID: Long = 17
        private const val VLS_ID: Long = 18
        private const val MW_ID: Long = 19
        private const val MG_OLL_ID: Long = 20
        private const val MG_PLL_ID: Long = 21
        private const val EASY_3_ID: Long = 22
        private const val ORTEGA_ID: Long = 23
        private const val TCLLP_ID: Long = 24

        // private const val POLL_ID: Long    = 25;
        private const val L2C_ID: Long = 26
        private const val L2E_ID: Long = 27
        private const val ELL_ID: Long = 28
        private const val OELLCP_ID: Long = 29
        private const val PLL_SC_ID: Long = 30
        private const val L3C_ID: Long = 31
        private const val L3E_ID: Long = 32
        private const val EO_ID: Long = 33
        private const val CP_ID: Long = 34
        private const val EP_ID: Long = 35
        private const val UZ_ID: Long = 36

        private const val ZBLL_T_ID: Long = 37
        private const val ZBLL_U_ID: Long = 38
        private const val ZBLL_L_ID: Long = 39
        private const val ZBLL_H_ID: Long = 40
        private const val ZBLL_PI_ID: Long = 41
        private const val ZBLL_SUNE_ID: Long = 42
        private const val ZBLL_ANTISUNE_ID: Long = 43

        private const val OH_OLL_RH_ID: Long = 44
        private const val OH_PLL_RH_ID: Long = 45
        private const val OH_COLL_RH_ID: Long = 46

        // private const val EASY_4_ID: Long = 47;
        // private const val CFOP_ABOUT_ID: Long = 48

        private const val COUNTING_SWITCH_ID: Long = 101
        private const val REVIEW_ID: Long          = 102
        private const val FEEDBACK_ID: Long        = 103
    }

    private val PREFS_FILE = "main"
    private val PREF_NUMB = "numbering"

    private var isCounting: Boolean = false
    private var mode: String = "easy3"

    private lateinit var result: Drawer
    private lateinit var topAdView: AdView
    private lateinit var bottomAdView: AdView
    private lateinit var adRequest: AdRequest
    private lateinit var feedbackDialog: AlertDialog

    private lateinit var settings: SharedPreferences

    private lateinit var binding: ActivityMainBinding
    private lateinit var dialogFeedBackBinding: DialogSendFeedbackBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.easy3_header)

        settings = getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
        val prefEditor = settings.edit()
        prefEditor.apply()
        isCounting = settings.getBoolean(PREF_NUMB, false)


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Easy3Fragment>(R.id.mainLayout)
            }
        }

        dialogFeedBackBinding = DialogSendFeedbackBinding.inflate(layoutInflater)
        initFeedBackDialog()

        initAd()
        handleDrawer()

        RateBottomSheetManager(this)
            .setInstallDays(3)
            .setLaunchTimes(8)
            .setRemindInterval(3)
            .monitor()
        RateBottomSheet.showRateBottomSheetIfMeetsConditions(this)
    }

    private fun initFeedBackDialog() {
        feedbackDialog = AlertDialog.Builder(this)
            .setView(dialogFeedBackBinding.root)
            .setPositiveButton(R.string.rating_dialog_feedback_custom_button_submit
            ) { _, _ ->
                val userFeedbackEditText = dialogFeedBackBinding.userFeedbackEditText
                val userComment = userFeedbackEditText.text.toString()

                if (userComment != "") {
                    val sentryId = Sentry.captureMessage("User FeedBack")
                    val userFeedback = UserFeedback(sentryId).apply {
                        comments = userComment
                    }
                    Sentry.captureUserFeedback(userFeedback)
                } else {
                    Toast.makeText(this, R.string.emptyFeedBackField, Toast.LENGTH_SHORT).show()
                    // Sentry.captureMessage("Empty user Feedback", SentryLevel.WARNING)
                }
                userFeedbackEditText.text = ""
            }
            .setNegativeButton(R.string.rating_dialog_feedback_button_cancel, null)
            .create()
    }

    private fun initAd() {
        MobileAds.initialize(this) {}
        topAdView = AdView(this)
        topAdView.adSize = AdSize.SMART_BANNER
        topAdView.adUnitId = "ca-app-pub-9813480536729767/9546708066"

        bottomAdView = AdView(this)
        bottomAdView.adSize = AdSize.SMART_BANNER
        bottomAdView.adUnitId = "ca-app-pub-9813480536729767/4920397105"

        adRequest = AdRequest.Builder().build()

        binding.mainView.addView(topAdView, 0)
        binding.mainView.addView(bottomAdView)

        topAdView.loadAd(adRequest)
        bottomAdView.loadAd(adRequest)
    }

    private fun getModeById(id: Long): String? {
        return when (id) {
            EASY_3_ID -> { "easy3"}

            F2L_ID -> { "f2l" }
            OLL_ID -> { "oll" }
            PLL_ID -> { "pll" }

            OH_OLL_LH_ID ->   { "oh_oll_lh" }
            OH_PLL_LH_ID ->   { "oh_pll_lh" }
            OH_COLL_LH_ID ->  { "oh_coll_lh" }
            OH_OLL_RH_ID ->   { "oh_oll_rh" }
            OH_PLL_RH_ID ->   { "oh_pll_rh" }
            OH_COLL_RH_ID ->  { "oh_coll_rh" }

            COLL_ID ->  { "coll" }
            OPF2L_ID -> { "op" }
            VHF2L_ID -> { "vh" }
            WV_ID ->    { "wv" }
            SV_ID ->    { "sv" }
            MW_ID ->    { "mw" }
            OLE_ID ->   { "ole" }
            OLC_ID ->   { "olc" }
            VLS_ID ->   { "vls" }
            ELL_ID ->   { "ell" }

            OELLCP_ID ->  { "oellcp" }
            PLL_SC_ID ->  { "pll_sc" }
            ZBLL_T_ID ->  { "zbll_t" }
            ZBLL_U_ID ->  { "zbll_u" }
            ZBLL_L_ID ->  { "zbll_l" }
            ZBLL_H_ID ->  { "zbll_h" }
            ZBLL_PI_ID ->        { "zbll_pi" }
            ZBLL_SUNE_ID ->      { "zbll_sune" }
            ZBLL_ANTISUNE_ID ->  { "zbll_antisune" }

            UZ_ID ->     { "uz" }
            CLL_ID ->    { "cll" }
            ORTEGA_ID -> { "ortega" }
            EG1_ID ->    { "eg1_" }
            EG2_ID ->    { "eg2_" }
            LEG1_ID ->   { "leg1_" }
            TCLLP_ID ->  { "tcllp" }
            L2C_ID ->    { "l2c" }
            L2E_ID ->    { "l2e" }
            MG_OLL_ID -> { "mg_oll" }
            MG_PLL_ID -> { "mg_pll" }
            L3C_ID ->    { "l3c" }
            L3E_ID ->    { "l3e" }
            EO_ID ->     { "eo" }
            CP_ID ->     { "cp" }
            EP_ID ->     { "ep" }
            else -> { null }
        }
    }

    private fun getHeader(cMode: String=mode): String {
        return getString(resources.getIdentifier(cMode + "_header", "string", packageName))
    }

    private fun handleDrawer() {
        result = DrawerBuilder()
            .withActivity(this)
            .withToolbar(binding.toolbar)
            .withSelectedItem(EASY_3_ID)
            .addDrawerItems(
                PrimaryDrawerItem().withName(R.string.easy3_header).withIdentifier(EASY_3_ID),
                ExpandableDrawerItem().withName(R.string.header_3x3x3).withSelectable(false).withSubItems(
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
                            if (mode != "easy3") {
                                replaceFr()
                            }
                        }
                    }),
                PrimaryDrawerItem()
                    .withName(R.string.rating_dialog_feedback_title)
                    .withIcon(R.drawable.baseline_feedback_24)
                    .withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIdentifier(FEEDBACK_ID),
                PrimaryDrawerItem().withName(R.string.review_btn)
                    .withIcon(R.drawable.ic_outline_favorite_border_24px)
                    .withIconTintingEnabled(true)
                    .withSelectable(false)
                    .withIdentifier(REVIEW_ID),
                )
            .withOnDrawerItemClickListener(object: Drawer.OnDrawerItemClickListener {
                override fun onItemClick(view: View?, position: Int, drawerItem: IDrawerItem<*>): Boolean {
                    when (drawerItem.identifier) {
                        EASY_3_ID -> {
                            binding.toolbar.title = getHeader("easy3")
                            binding.mainScroll.scrollTo(0, 0)
                            supportFragmentManager.commit {
                                setReorderingAllowed(true)
                                replace<Easy3Fragment>(R.id.mainLayout)
                            }
                            return false
                        }
                        REVIEW_ID -> {
                            RateBottomSheetManager(this@MainActivity)
                                .setDebugForceOpenEnable(true) // False by default
                            RateBottomSheet.showRateBottomSheetIfMeetsConditions(
                                activity = this@MainActivity
                            )
                            return true
                        }
                        FEEDBACK_ID -> {
                            feedbackDialog.show()
                            return true
                        }
                        else -> {
                            if (getModeById(drawerItem.identifier) == null) {
                                return true
                            }
                            mode = getModeById(drawerItem.identifier)!!
                            binding.toolbar.title = getHeader()
                            binding.mainScroll.scrollTo(0, 0)
                            replaceFr()
                            return false
                        }
                    }
                }
            })
            .build()
    }

    private fun replaceFr() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<FormulaFragment>(R.id.mainLayout, args=bundleOf(
                "mode" to mode,
                "packageName" to packageName,
                "isCounting" to isCounting
            ))
        }
    }
}