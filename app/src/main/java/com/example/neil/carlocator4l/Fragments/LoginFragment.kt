package com.example.neil.carlocator4l.Fragments

import android.content.Context
import android.content.SharedPreferences
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
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.neil.carlocator4l.DefaultActivity
import com.example.neil.carlocator4l.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import neilsayok.github.carlocatorapi.API.Data.LoginData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginFragment: Fragment() {

    private var isPassVisible = false

    private lateinit var v: View
    private lateinit var signinButton: Button
    private lateinit var signupButton: Button

    private lateinit var idET: EditText
    private lateinit var passwordET: EditText

    private lateinit var loadingLayout: ViewGroup

    private lateinit var passwordVisisbilityIB: ImageButton

    private lateinit var navController: NavController

    private lateinit var sp: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.activity_signin, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sp = requireContext().getSharedPreferences("LOGIN", Context.MODE_PRIVATE)

       navController = findNavController()

        signinButton = v.findViewById(R.id.LogInBT)
        signupButton = v.findViewById(R.id.SignUpBT)

        idET = v.findViewById(R.id.userNameET)
        passwordET = v.findViewById(R.id.passwordET)

        idET.setText(arguments?.getString("email")?:"")
        passwordET.setText(arguments?.getString("password")?:"")


        passwordVisisbilityIB = v.findViewById(R.id.viewPass)

        loadingLayout = v.findViewById(R.id.progressRelLayout)

        setPasswordET()


        signinButton.setOnClickListener(clickListener)
        signupButton.setOnClickListener(clickListener)
        passwordVisisbilityIB.setOnClickListener(clickListener)




    }

    private fun hideKeyBoard(){
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }


    private val clickListener = View.OnClickListener{
        when(it.id){
            R.id.LogInBT->{
                hideKeyBoard()
                val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
                if (idET.text.isNullOrBlank()){
                    Snackbar.make(v, "Please Input an email", Snackbar.LENGTH_SHORT).show()
                    idET.startAnimation(anim)
                }else if(passwordET.text.isNullOrBlank()){
                    Snackbar.make(v, "Please Input an password", Snackbar.LENGTH_SHORT).show()
                    passwordET.startAnimation(anim)

                }else{
                    lifecycleScope.launch {
                        loadingLayout.visibility = View.VISIBLE
                        login(idET.text.toString(),passwordET.text.toString())
                    }
                }

            }
            R.id.SignUpBT->{
                hideKeyBoard()
                navController.navigate(R.id.action_signinFragment_to_signupFragment)
            }
            R.id.viewPass->{
                isPassVisible = !isPassVisible
                setPasswordET()
            }
        }
    }

    fun setPasswordET(){
        if(isPassVisible){
            passwordET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            passwordET.setSelection(passwordET.text.length)
            passwordVisisbilityIB.
            setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_visibility_24px))
        }else{
            passwordET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            passwordET.setSelection(passwordET.text.length)
            passwordVisisbilityIB.
            setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_visibility_off_24px))
        }
    }

    fun login(email: String, password: String){
        //val api = RetrofitBuilder().retrofit.create(RetrofitAPI::class.java)

        val call: Call<LoginData> = DefaultActivity.api.login(email,password)

        call.enqueue(object : Callback<LoginData>{
            override fun onResponse(call: Call<LoginData>, response: Response<LoginData>) {
                val res = response.body()
                loadingLayout.visibility = View.GONE
                Log.d("Respose", res.toString())
                if (res!!.response.equals("success",true)){
                    sp.edit().putBoolean("verified", res.verified != "0").apply()
                    sp.edit().putString("name", res.email).apply()
                    sp.edit().putString("reg_id", res.regId).apply()
                    sp.edit().putString("email", res.email).apply()

                    if (res.verified == "0"){
                        navController.navigate(R.id.action_signinFragment_to_OTPFragment)
                    }else{
                        navController.navigate(R.id.action_signinFragment_to_trackFragment)
                    }
                }else{

                    Snackbar.make(v, "Please Enter a Valid ID and Password", Snackbar.LENGTH_SHORT).show()
                }

            }

            override fun onFailure(call: Call<LoginData>, t: Throwable) {
                loadingLayout.visibility = View.GONE
            }

        })
    }

}