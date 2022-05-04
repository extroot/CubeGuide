package ru.extroot.newcubeguide

import android.view.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView

import io.sentry.Sentry
import ru.extroot.newcubeguide.databinding.DialogFormulaPreviewBinding

import ru.extroot.newcubeguide.databinding.FragmentFormulaBinding

private const val ARG_PARAM1 = "mode"
private const val ARG_PARAM2 = "packageName"
private const val ARG_PARAM3 = "settingPreview"

class FormulaFragment : Fragment() {
    private var mode: String = "f2l"
    private var packageName: String? = null
    private var picMode: String = "f2l"
    private var isCounting: Boolean = true
    private var isGrid = true
    private var settinsPreview = false

    private var replaceRw = false
    private var replaceLw = false
    private var replaceDw = false
    private var replaceUw = false

    private lateinit var dialogView: View

    private lateinit var _binding: FragmentFormulaBinding
    private lateinit var dialogPreviewBinding: DialogFormulaPreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(ARG_PARAM1) ?: "f2l"
            packageName = it.getString(ARG_PARAM2)
            settinsPreview = it.getBoolean(ARG_PARAM3)
        }
        picMode = getPicModeByMode()
        getSettings()

        dialogPreviewBinding = DialogFormulaPreviewBinding.inflate(layoutInflater)
        dialogView = dialogPreviewBinding.root
    }

    private fun getSettings() {
        val isCountingDefault = resources.getBoolean(R.bool.counting_default_key)
        val isGridDefault = resources.getBoolean(R.bool.grid_default_key)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        if (sharedPref == null) {
            Sentry.captureMessage("getPreferences error")
            isGrid = isGridDefault
            isCounting = isCountingDefault
        } else  {
            isCounting = sharedPref.getBoolean(getString(R.string.counting_key), isCountingDefault)
            isGrid = sharedPref.getBoolean(getString(R.string.grid_key), isGridDefault)

            replaceRw = sharedPref.getBoolean("replace_rw", false)
            replaceLw = sharedPref.getBoolean("replace_lw", false)
            replaceDw = sharedPref.getBoolean("replace_dw", false)
            replaceUw = sharedPref.getBoolean("replace_uw", false)
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFormulaBinding.inflate(inflater, container, false)
        draw()
        return _binding.root
    }

    private fun getPicModeByMode(): String {
        return when (mode) {
            "oh_oll_lh" -> "oll"
            "oh_pll_lh" -> "pll"
            "oh_coll_lh" -> "coll"
            "oh_oll_rh" -> "oll"
            "oh_pll_rh" -> "pll"
            "oh_coll_rh" -> "coll"
            "eg1_" -> "cll"
            "eg2_" -> "cll"
            "leg1_" -> "cll"
            else -> mode
        }
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
        var text: String?
        val name = mode + algNumber.toString()
        text = getString(resources.getIdentifier(name, "string", packageName))

        if ("" == text) {
            return null
        }

        if (replaceRw) text = text.replace("Rw", "r")
        if (replaceLw) text = text.replace("Lw", "l")
        if (replaceDw) text = text.replace("Dw", "d")
        if (replaceUw) text = text.replace("Uw", "u")
        return text
    }

    private val onClickListener = View.OnClickListener { v:View ->
        val position = v.tag.toString().toInt()
        val title = mode.uppercase() + " " + (position + 1).toString()

        dialogPreviewBinding.previewImage.setImageResource(getImageId(position))

        MaterialDialog(requireContext()).show {
            title(text=title)
            customView(view=dialogView)
            message(text=getAlgText(position))
        }
    }

    private fun draw() {
        val recyclerView: RecyclerView = _binding.recycleView
        recyclerView.isNestedScrollingEnabled = false

        var count = getAlgCount()
        if (settinsPreview && isGrid) count = 6
        if (settinsPreview && !isGrid) count = 2

        val imageData = mutableListOf<Int>()
        val algData = mutableListOf<String>()
        val titleData = mutableListOf<String?>()

        for (i in 0 until count) {
            val alg = getAlgText(i) ?: continue
            imageData.add(getImageId(i))
            algData.add(alg)
            titleData.add(getAlgTitle(i))
        }
        val adapter = if (isGrid) {
            recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
            CustomRecyclerAdapter(imageData, mode, isCounting)
        } else {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            CustomRecyclerAdapter(titleData, imageData, algData, isCounting)
        }
        if (!settinsPreview) adapter.onClickListener = onClickListener
        recyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: Boolean = false) =
            FormulaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putBoolean(ARG_PARAM3, param3)
                }
            }
    }
}