package com.example.wit.base.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

abstract class BaseDialogFragment<B : ViewDataBinding> : DialogFragment() {

    private var _binding: B? = null
    open val binding: B get() = _binding!!

    val bindingInitialized: Boolean
        get() = _binding != null && isAdded

    //    lateinit var binding: B
    //    val bindingInitialized: Boolean
    //        get() = this::binding.isInitialized && isAdded

    abstract fun setBinding(layoutInflater: LayoutInflater): B

    /**
     * 로직 구현은 여기서 ( onViewCreated 에서 호출 )
     *
     * onCreateView 재정의 필요 시, super 호출 필수
     * B.onCreateView(view: View) 에서 이벤트 등, 로직 처리
     * fun B.onCreateView(view: View)
     */
    abstract fun B.onCreateView(view: View)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = getInflatedLayout(layoutInflater).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) { onCreateView(root) }
        initAutoCleared()
    }

    override fun onResume() {
        super.onResume()
        setLayoutFullScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding?.let { _binding = null }
    }

    private fun getInflatedLayout(inflater: LayoutInflater): B =
        setBinding(inflater).also {
            it.lifecycleOwner = this.viewLifecycleOwner
            _binding = it
        }

    private fun setLayoutFullScreen() {
        dialog?.run {
            window?.run {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
        }
    }

    /**
     * Dialog의 크기를 지정
     */
    fun initDialogViewSize(width: Int = 0, height: Int = 0) =
        dialog?.window?.setLayout(width, height)

    private fun initAutoCleared() {
        lifecycle.addObserver(object : DefaultLifecycleObserver {
            override fun onCreate(owner: LifecycleOwner) {
                viewLifecycleOwnerLiveData.observe(this@BaseDialogFragment) { owner ->
                    owner?.lifecycle?.addObserver(object : DefaultLifecycleObserver {
                        override fun onDestroy(owner: LifecycleOwner) {
                            try {
                                if (isAdded) {
                                    this@BaseDialogFragment.dismissAllowingStateLoss()
                                }
                            } catch (e: Exception) {
                                Log.e("BaseDialogFragment","dismissAllowingStateLoss, dialogFragment is Not Added")
                            }
                        }
                    })
                }
            }
        })
    }

    fun safeDismissAllowingStateLoss() {
        try {
            if (isAdded) {
                dismissAllowingStateLoss()
            }
        } catch (e: Exception) {
            Log.e("BaseDialogFragment","dismissAllowingStateLoss, dialogFragment is Not Added")
        }
    }

    /**
     * show()가 호출되는 경우, 내부에서
     * FragmentTransaction의 commit이 호출,
     * onSaveInstanceState 함수가 호출된 상태에서는 IllegalStateException 발생
     * ( https://beginner97.tistory.com/2 )
     */
    fun showAllowingStateLoss(fm: FragmentManager, tag: String = "") {
        fm.beginTransaction().add(this, tag)
            .commitAllowingStateLoss()
    }
}