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

import ru.extroot.newcubeguide.databinding.FragmentFormulaBinding
import ru.extroot.newcubeguide.databinding.DialogFormulaPreviewBinding

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

    private lateinit var dialogView: View
    private var previewDialog: MaterialDialog? = null

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

    /**
     * Updates sharedPref variables.
     */
    private fun getSettings() {
        val isCountingDefault = resources.getBoolean(R.bool.counting_default_key)
        val isGridDefault = resources.getBoolean(R.bool.grid_default_key)
        val replaceRwDefault = resources.getBoolean(R.bool.replace_rw_default_key)

        val sharedPref = PreferenceManager.getDefaultSharedPreferences(requireContext())
        isCounting = sharedPref.getBoolean(getString(R.string.counting_key), isCountingDefault)
        isGrid = sharedPref.getBoolean(getString(R.string.grid_key), isGridDefault)
        replaceRw = sharedPref.getBoolean(getString(R.string.replace_rw_key), replaceRwDefault)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFormulaBinding.inflate(inflater, container, false)
        draw()
        return _binding.root
    }

    /**
     * Returns images prefix in current mode.
     */
    private fun getPicModeByMode(): String {
        return getString(resources.getIdentifier(mode + "_picmode", "string", packageName))
    }

    /**
     * Returns image identifier by [position].
     */
    private fun getImageId(position: Int): Int {
        return resources.getIdentifier(picMode + position, "drawable", packageName)
    }

    /**
     * Returns prefix of current mode.
     * Uses in grid style as title prefix.
     */
    private fun getPrefix(): String {
        return getString(resources.getIdentifier(mode + "_prefix", "string", packageName))
    }

    /**
     * Returns count of algorithms in current mode.
     */
    private fun getAlgCount(): Int {
        return getString(resources.getIdentifier(mode + "_count", "integer", packageName)).toInt()
    }

    /**
     * Returns title of algorithm if exists by it's [position].
     */
    private fun getAlgTitle(position: Int): String? {
        val title: String?
        val name = picMode + position.toString() + "_title"
        title = getString(resources.getIdentifier(name, "string", packageName))

        if ("" == title) {
            return null
        }
        return title
    }

    /**
     * Returns text of algorithm by it's [position].
     */
    private fun getAlgText(position: Int): String? {
        var text: String?
        val name = mode + position.toString()
        text = getString(resources.getIdentifier(name, "string", packageName))

        if ("" == text) {
            return null
        }

        if (replaceRw) {
            text = text.replace("Rw", "r")
                .replace("Lw", "l")
                .replace("Dw", "d")
                .replace("Uw", "u")
        }

        return text
    }

    private val onClickListener = View.OnClickListener { v:View ->
        val position = v.tag.toString().toInt()
        val title = mode.uppercase() + " " + (position + 1).toString()

        if (previewDialog?.isShowing != true) {
            dialogPreviewBinding.previewImage.setImageResource(getImageId(position))

            previewDialog = MaterialDialog(requireContext()).show {
                title(text = title)
                customView(view = dialogView)
                message(text = getAlgText(position))
            }
        }
    }

    /**
     * Creating lists of elements data for recycler view.
     */
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
            CustomRecyclerAdapter(imageData, isCounting, getPrefix())
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