package com.example.neil.carlocatoruserside1m.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.neil.carlocatoruserside1m.DefaultActivity
import com.example.neil.carlocatoruserside1m.R
import com.example.neil.carlocatoruserside1m.Room.UserData
import com.example.neil.carlocatoruserside1m.Utils.Converter
import com.example.neil.carlocatoruserside1m.ViewModels.UserDataViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import neilsayok.github.carlocatorapi.API.Data.LoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddUserFragment: Fragment() {

    private lateinit var v: View
    private var isPassVisible = false


    private lateinit var viewModel : UserDataViewModel
    private lateinit var navController: NavController

    private lateinit var progressLayout: ViewGroup

    private lateinit var baseMotionLayout : MotionLayout
    private lateinit var emailEt: EditText
    private lateinit var passwordEt: EditText
    private lateinit var passwordToggleIB: ImageButton
    private lateinit var loginBTN: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.activity_adduser, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[UserDataViewModel::class.java]
        navController = findNavController()

        baseMotionLayout = v.findViewById(R.id.base)
        emailEt = v.findViewById(R.id.userNameET)
        passwordEt = v.findViewById(R.id.passwordET)
        passwordToggleIB = v.findViewById(R.id.viewPass)
        loginBTN = v.findViewById(R.id.logInBT)

        progressLayout = v.findViewById(R.id.progressLayout)

        baseMotionLayout.transitionToEnd()

        passwordToggleIB.setOnClickListener(onClickListener)
        loginBTN.setOnClickListener(onClickListener)

    }

    private val onClickListener = View.OnClickListener{
        when(it.id){
            R.id.viewPass->{
                isPassVisible = !isPassVisible
                setPasswordETVisibility(passwordEt,passwordToggleIB)
            }
            R.id.logInBT->{
                hideKeyBoard()
                val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                if (emailEt.text.isNullOrBlank()){
                    Snackbar.make(v, "Please Input an email", Snackbar.LENGTH_SHORT).show()
                    emailEt.startAnimation(anim)
                }else if(passwordEt.text.isNullOrBlank()){
                    Snackbar.make(v, "Please Input an password", Snackbar.LENGTH_SHORT).show()
                    passwordEt.startAnimation(anim)

                }else{
                    progressLayout.visibility = View.VISIBLE

                    lifecycleScope.launch(Dispatchers.IO) {
                        login(emailEt.text.toString(),passwordEt.text.toString())
                    }
                }
            }
        }
    }

    private fun setPasswordETVisibility(editText: EditText, imageButton: ImageButton) {
        if (isPassVisible) {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setSelection(editText.text.length)
            imageButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_visibility_24px
                )
            )
        } else {
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setSelection(editText.text.length)
            imageButton.setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.ic_baseline_visibility_off_24px
                )
            )
        }
    }

    private fun hideKeyBoard() {
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }

    fun login(email: String, password: String){
        //val api = RetrofitBuilder().retrofit.create(RetrofitAPI::class.java)

        val call: Call<LoginData> = DefaultActivity.api.login(email,password)

        call.enqueue(object : Callback<LoginData> {
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                val res = response.body()
                progressLayout.visibility = View.GONE
                Log.d("Respose", res.toString())
                if (res!!.response.equals("success",true)){
                    if (res.verified == "0"){
                        Snackbar.make(v,"User is not verified. Please verify user to continue.",Snackbar.LENGTH_LONG).show()
                    }else{
                        val userData = Converter().convertLoginDataToUserData(res)
                        viewModel.addUser(userData)
                        navController.navigateUp()
                        //navController.navigate(R.id.action_signinFragment_to_trackFragment)
                    }
                }else{
                    Snackbar.make(v, "Please Enter a Valid ID and Password", Snackbar.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                progressLayout.visibility = View.GONE
            }

        })
    }


}