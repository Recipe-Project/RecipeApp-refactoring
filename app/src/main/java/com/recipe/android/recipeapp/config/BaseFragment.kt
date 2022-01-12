package com.recipe.android.recipeapp.config

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.recipe.android.recipeapp.common.LoadingDialog

abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null

    protected val binding get() = _binding!!

    lateinit var loadingDialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        loadingDialog = LoadingDialog(requireContext())

        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String?) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }

    fun showLoadingDialog(){
        loadingDialog.show()
        Thread {
            try {
                Thread.sleep(6000)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            if (loadingDialog.isShowing) {
                loadingDialog.dismiss()
            }
        }.start()
    }

    fun dismissLoadingDialog(){
        if (loadingDialog.isShowing) {
            loadingDialog.dismiss()
        }
    }

}