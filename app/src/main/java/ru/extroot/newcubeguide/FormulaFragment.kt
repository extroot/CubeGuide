package ru.extroot.newcubeguide

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import io.sentry.Sentry
import io.sentry.SentryLevel

import ru.extroot.newcubeguide.databinding.FragmentFormulaBinding

private const val ARG_PARAM1 = "mode"
private const val ARG_PARAM2 = "packageName"
private const val ARG_PARAM3 = "isCounting"
private const val TAG = "FormulaFragment"

class FormulaFragment : Fragment() {
    private var mode: String? = null
    private var packageName: String? = null
    private var picMode: String? = null
    private var isCounting: Boolean = false

    private var picLen: Int = 250
    private var textSize: Int = 16

    private lateinit var _binding: FragmentFormulaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            mode = it.getString(ARG_PARAM1)
            packageName = it.getString(ARG_PARAM2)
            isCounting = it.getBoolean(ARG_PARAM3)
        }
        if (mode == null) {
            Sentry.captureMessage("null Mode parameter", SentryLevel.FATAL)
            mode = "f2l"
            // TODO: message for user
        }
        picMode = getPicModeByMode()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFormulaBinding.inflate(inflater, container, false)
        draw()
        return _binding.root
    }

    private fun getPicModeByMode(): String? {
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
        println(name)
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

    private fun draw() {
        val recyclerView: RecyclerView = _binding.recycleView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.isNestedScrollingEnabled = false

        val count = getAlgCount()

        val imageData = mutableListOf<Int>()
        val algData = mutableListOf<String>()
        val titleData = mutableListOf<String?>()

        for (i in 0 until count) {
            val alg = getAlgText(i) ?: continue
            imageData.add(getImageId(i))
            algData.add(alg)
            titleData.add(getAlgTitle(i))
        }
        recyclerView.adapter = CustomRecyclerAdapter(titleData, imageData, algData, isCounting)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String, param3: Boolean) =
            FormulaFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putBoolean(ARG_PARAM3, param3)
                }
            }
    }
}