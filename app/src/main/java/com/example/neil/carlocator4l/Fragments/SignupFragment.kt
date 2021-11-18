package com.example.neil.carlocator4l.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.neil.carlocator4l.R
import com.example.neil.carlocator4l.Utils.PasswordStrength
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt


class SignupFragment: Fragment() {

    private lateinit var v:View

    private lateinit var anim: Animation

    private lateinit var nameET: EditText
    private lateinit var vehIDET: EditText
    private lateinit var mailET: EditText
    private lateinit var passET: EditText
    private lateinit var repassET: EditText

    private lateinit var viewPass: ImageButton
    private lateinit var viewRePass: ImageButton

    private lateinit var passStrength:TextView
    private lateinit var repassStrength:TextView

    private lateinit var SignUpBT: Button

    private lateinit var progressLayout: ViewGroup

    private lateinit var passDraw: GradientDrawable
    private lateinit var rePassDraw: GradientDrawable
    private lateinit var  passwordStrengthManager: PasswordStrength
    private var width: Int = 0
    private var isPassVisible = false
    private var isRepassVisible = false
    private lateinit var sp: SharedPreferences
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.sign_up, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)

        sp = requireContext().getSharedPreferences("LOGIN", Context.MODE_PRIVATE)

        navController = findNavController()


        nameET = v.findViewById(R.id.nameET)
        vehIDET = v.findViewById(R.id.vehIDET)
        mailET = v.findViewById(R.id.mailET)
        passET = v.findViewById(R.id.passET)
        repassET = v.findViewById(R.id.repassET)

        viewPass = v.findViewById(R.id.viewPass)
        viewRePass = v.findViewById(R.id.viewRePass)

        passStrength = v.findViewById(R.id.passStrength)
        repassStrength = v.findViewById(R.id.repassStrength)

        SignUpBT = v.findViewById(R.id.SignUpBT)

        progressLayout = v.findViewById(R.id.progressLayout)

        passDraw = passET.background as GradientDrawable
        rePassDraw = repassET.background as GradientDrawable

        passwordStrengthManager= PasswordStrength()
        width = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            3f,
            resources.displayMetrics
        ).roundToInt()

        passET.addTextChangedListener(passTextWatcher)
        repassET.addTextChangedListener(rePassTextWatcher)

        viewPass.setOnClickListener(onClick)
        viewRePass.setOnClickListener(onClick)
        SignUpBT.setOnClickListener(onClick)


        isPassVisible = !setPasswordVisibility(passET,viewPass,isPassVisible)
        isRepassVisible = !setPasswordVisibility(repassET,viewRePass,isRepassVisible)



    }




    val onClick = View.OnClickListener{
        when(it.id){
            R.id.viewPass->{
                isPassVisible = setPasswordVisibility(passET,viewPass,isPassVisible)
            }
            R.id.viewRePass->{
                isRepassVisible = setPasswordVisibility(repassET,viewRePass,isRepassVisible)
            }
            R.id.SignUpBT->{

                if (nameET.text.isNullOrBlank()){
                    nameET.requestFocus()
                    nameET.startAnimation(anim)
                    Snackbar.make(v,"Please enter a valid email.",Snackbar.LENGTH_SHORT).show()
                }else if (vehIDET.text.isNullOrBlank()){
                    vehIDET.requestFocus()
                    vehIDET.startAnimation(anim)
                    Snackbar.make(v,"Please enter a valid vehicle id.",Snackbar.LENGTH_SHORT).show()

                }else if (mailET.text.isNullOrBlank()){
                    mailET.requestFocus()
                    mailET.startAnimation(anim)
                    Snackbar.make(v,"Please enter a valid email.",Snackbar.LENGTH_SHORT).show()

                }else if (passET.text.isNullOrBlank()){
                    passET.requestFocus()
                    passET.startAnimation(anim)
                    Snackbar.make(v,"Please enter a valid please enter a valid password.",Snackbar.LENGTH_SHORT).show()

                }else if (repassET.text.isNullOrBlank() || repassET.text.equals(passET.text)){
                    repassET.requestFocus()
                    repassET.startAnimation(anim)
                    Snackbar.make(v,"Please reenter correct password. ",Snackbar.LENGTH_SHORT).show()

                }else{
                    lifecycleScope.launch {
                        callServer()
                    }



                }
            }

        }
    }

    fun callServer(){
        progressLayout.visibility = View.VISIBLE
        val api = neilsayok.github.carlocatorapi.API.Retrofit.RetrofitBuilder().retrofit.create(
            neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI::class.java)
        val call: Call<neilsayok.github.carlocatorapi.API.Data.CreateAccountData> = api.signup(nameET.text.toString(),
            vehIDET.text.toString(),
            mailET.text.toString(),
            passET.text.toString(),
            repassET.text.toString(),
        )

        val resCodes = neilsayok.github.carlocatorapi.API.ResponseCodes().responses
        call.enqueue(object: Callback<neilsayok.github.carlocatorapi.API.Data.CreateAccountData>{
            override fun onResponse(
                call: Call<neilsayok.github.carlocatorapi.API.Data.CreateAccountData>,
                response: Response<neilsayok.github.carlocatorapi.API.Data.CreateAccountData>
            ) {
                Log.d("Signup Call success", response.body().toString())

                val res = response.body()
                when(res!!.code){
                    resCodes["Account_Created"]->{
                        sp.edit().putBoolean("verified", false).apply()
                        sp.edit().putString("name", nameET.text.toString()).apply()
                        sp.edit().putString("reg_id", vehIDET.text.toString()).apply()
                        sp.edit().putString("email", mailET.text.toString()).apply()


                        api.send_otp(mailET.text.toString()).enqueue(object :Callback<neilsayok.github.carlocatorapi.API.Data.OTPData>{
                            override fun onResponse(
                                call: Call<neilsayok.github.carlocatorapi.API.Data.OTPData>,
                                response: Response<neilsayok.github.carlocatorapi.API.Data.OTPData>
                            ) {
                                Log.d("OTP Mail Call success", response.body().toString())
                                Log.d("OTP codes", "${response.body()!!.code} -> ${resCodes["Mail_sent"]}")

                                progressLayout.visibility = View.GONE

                                when(response.body()!!.code){
                                    resCodes["OTP_Mail_sent"]->{
                                        Toast.makeText(requireContext(),"OTP sent successfully to ${mailET.text}",
                                        Toast.LENGTH_SHORT).show()
                                        navController.navigate(R.id.action_signupFragment_to_OTPFragment)

                                    }
                                    resCodes["OTP_Mail_not_sent"]->{
                                        Toast.makeText(requireContext(),"We are unable to send OTP to ${mailET.text}",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<neilsayok.github.carlocatorapi.API.Data.OTPData>, t: Throwable) {
                                progressLayout.visibility = View.GONE
                                Log.d("OTP Mail Call Fail", t.message.toString())

                                Toast.makeText(requireContext(),"Sorry Something went wrong.",
                                    Toast.LENGTH_SHORT).show()
                            }

                        })



                    }
                    resCodes["Email_format_wrong"]->{
                        Snackbar.make(v,"Please Enter a valid Email.", Snackbar.LENGTH_SHORT).show()
                    }
                    resCodes["Email_Already_Pressent"]->{
                        progressLayout.visibility = View.GONE

                        Snackbar.make(v,"Email Already Present,Do you want to Signin using the account.", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Yes", View.OnClickListener {
                                val b = Bundle()
                                b.putString("email", mailET.text.toString())
                                b.putString("password",passET.text.toString())
                                navController.navigate(R.id.action_signupFragment_to_signinFragment,b)
                            }).show()
                    }

                    else->{
                        Snackbar.make(v,"Something went Wrong. Please try again", Snackbar.LENGTH_SHORT).show()
                    }

                }

            }

            override fun onFailure(call: Call<neilsayok.github.carlocatorapi.API.Data.CreateAccountData>, t: Throwable) {
                progressLayout.visibility = View.GONE
                Log.d("Signup Call Fail", t.message.toString())
            }

        })
    }

    fun setPasswordVisibility(editText: EditText, imageButton: ImageButton,isVisible: Boolean): Boolean{
        if(isVisible){
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setSelection(editText.text.length)
            imageButton.
            setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_visibility_24px))
        }else{
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setSelection(editText.text.length)
            imageButton.
            setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.ic_baseline_visibility_off_24px))
        }
        return !isVisible
    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    var passTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            setPassEditTextColor(passStrength, s.toString(), count, start,passDraw)
        }
        override fun afterTextChanged(s: Editable) {
            if (s.length == 0) {
                passStrength.setVisibility(View.GONE)
            }
        }
    }

    var rePassTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            setPassEditTextColor(repassStrength, s.toString(), count, start,rePassDraw)
        }
        override fun afterTextChanged(s: Editable) {
            if (s.length == 0) {
                repassStrength.setVisibility(View.GONE)
            }
        }
    }

    fun showDialog(title: String?, message: CharSequence?) {
        val builder = AlertDialog.Builder(requireContext())
        if (title != null) builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("OK", null)
        builder.show()
    }

    fun setPassEditTextColor(v: TextView,s: String, count: Int, start: Int,passDraw: GradientDrawable){
        if (count + start != 0) {
            when (passwordStrengthManager.passwordStrength(s)) {
                "weak" -> {
                    v.setVisibility(View.VISIBLE)
                    v.setText(resources.getString(R.string.pass_strength_weak))
                    passDraw.setStroke(width,ContextCompat.getColor(requireContext(), R.color.red))

                }
                "medium_weak" -> {
                    v.setText(resources.getString(R.string.pass_strength_weak))
                    passDraw.setStroke(width, resources.getColor(R.color.orange))
                    passDraw.setStroke(width,ContextCompat.getColor(requireContext(), R.color.orange))

                }
                "medium" -> {
                    v.setText(resources.getString(R.string.pass_strength_med))
                    passDraw.setStroke(width,ContextCompat.getColor(requireContext(), R.color.yellow))

                }
                "medium_strong" -> {
                    v.setText(resources.getString(R.string.pass_strength_good))
                    passDraw.setStroke(width,ContextCompat.getColor(requireContext(), R.color.blue))

                }
                "strong" -> {
                    v.setText(resources.getString(R.string.pass_strength_strong))
                    passDraw.setStroke(width,ContextCompat.getColor(requireContext(), R.color.green))

                }
            }
        } else {
            passDraw.setStroke(width,ContextCompat.getColor(requireContext(), R.color.white))
        }
    }

}