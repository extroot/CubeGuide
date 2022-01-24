package ru.extroot.newcubeguide

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

import io.sentry.Sentry
import io.sentry.SentryLevel
import com.google.android.material.color.MaterialColors

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

    private fun checkVerticalMode(): Boolean {
        return "l3c" == picMode || "eo" == picMode || "cp" == picMode || "ep" == picMode
    }


    private fun draw() {
        val imageParams = LinearLayout.LayoutParams(picLen, picLen)
        val imageParamsVertical = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        val sepParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 2)

        val count = getAlgCount()
        var offset = 0
        for (i in 0 until count) {
            val image = ImageView(requireContext())
            image.setImageResource(getImageId(i))
            image.layoutParams = imageParams

            val alg = getAlgText(i)
            if (alg == null) {
                offset++
                continue
            }

            val title = getAlgTitle(i)

            if (title != null) {
                val titleView = TextView(ContextThemeWrapper(requireContext(), R.style.title))
                titleView.text = title
                titleView.textSize = (textSize + 4).toFloat()
                 _binding.fragmentMainLayout.addView(titleView)
            }
            else if (i != 0) {
                val sep = View(requireContext())
                sep.layoutParams = sepParams
                sep.setBackgroundColor(MaterialColors.getColor(requireContext(), R.attr.dividerColor, Color.BLACK))
                sep.setPadding(3, 1, 1, 3)
                 _binding.fragmentMainLayout.addView(sep)
            }

            val algText = TextView(ContextThemeWrapper(requireContext(), R.style.formulaText))
            algText.text = alg
            algText.textSize = textSize.toFloat()

            val line = if (checkVerticalMode()) {
                algText.gravity = Gravity.CENTER_HORIZONTAL
                image.layoutParams = imageParamsVertical
                LinearLayout(ContextThemeWrapper(requireContext(), R.style.lineVertical))
            } else {
                LinearLayout(ContextThemeWrapper(requireContext(), R.style.line))
            }

            if (isCounting) {
                val countingText = TextView(ContextThemeWrapper(requireContext(), R.style.countingText))
                countingText.text = (i + 1 - offset).toString()
                countingText.textSize = textSize.toFloat()
                line.addView(countingText)
            }

            line.addView(image)
            line.addView(algText)

             _binding.fragmentMainLayout.addView(line)
        }
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