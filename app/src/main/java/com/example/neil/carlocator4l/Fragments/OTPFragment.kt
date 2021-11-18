package com.example.neil.carlocator4l.Fragments

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getDrawable
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.chaos.view.PinView
import com.example.neil.carlocator4l.DefaultActivity
import com.example.neil.carlocator4l.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import neilsayok.github.carlocatorapi.API.Data.VerifyOTPData
import neilsayok.github.carlocatorapi.API.ResponseCodes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OTPFragment: Fragment() {

    private lateinit var v: View
    private lateinit var otpView: PinView
    private lateinit var one: FloatingActionButton 
    private lateinit var two:FloatingActionButton 
    private lateinit var three:FloatingActionButton 
    private lateinit var four:FloatingActionButton 
    private lateinit var five:FloatingActionButton 
    private lateinit var six:FloatingActionButton 
    private lateinit var seven:FloatingActionButton 
    private lateinit var eight:FloatingActionButton 
    private lateinit var nine:FloatingActionButton 
    private lateinit var zero:FloatingActionButton 
    private lateinit var bksp:FloatingActionButton 
    private lateinit var done:FloatingActionButton
    private lateinit var verifyBtn: Button
    private lateinit var sentTo: TextView
    private lateinit var goToLoginTV: TextView
    private lateinit var progressLayout: ViewGroup

    private lateinit var anim: Animation
    private lateinit var vibrator: Vibrator
    private lateinit var sp: SharedPreferences
    private lateinit var api: neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.otp_activity, container, false)
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.shake)
        vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        sp = requireContext().getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        api = neilsayok.github.carlocatorapi.API.Retrofit.RetrofitBuilder().retrofit.create(
            neilsayok.github.carlocatorapi.API.Retrofit.RetrofitAPI::class.java)

        sentTo = v.findViewById(R.id.sentTo)

        sentTo.text = sp.getString("email", "")

        navController = findNavController()

        otpView = v.findViewById(R.id.otp)
        verifyBtn = v.findViewById(R.id.verifyBtn)

        one = v.findViewById(R.id.one)
        two = v.findViewById(R.id.two)
        three = v.findViewById(R.id.three)
        four = v.findViewById(R.id.four)
        five = v.findViewById(R.id.five)
        six = v.findViewById(R.id.six)
        seven = v.findViewById(R.id.seven)
        eight = v.findViewById(R.id.eight)
        nine = v.findViewById(R.id.nine)
        zero = v.findViewById(R.id.zero)
        bksp = v.findViewById(R.id.bksp)
        done = v.findViewById(R.id.done)
        goToLoginTV = v.findViewById(R.id.goToLoginTV)
        progressLayout = v.findViewById(R.id.progressLayout)

        one.setOnClickListener(keypadOnClick)
        two.setOnClickListener(keypadOnClick)
        three.setOnClickListener(keypadOnClick)
        four.setOnClickListener(keypadOnClick)
        five.setOnClickListener(keypadOnClick)
        six.setOnClickListener(keypadOnClick)
        seven.setOnClickListener(keypadOnClick)
        eight.setOnClickListener(keypadOnClick)
        nine.setOnClickListener(keypadOnClick)
        zero.setOnClickListener(keypadOnClick)
        bksp.setOnClickListener(keypadOnClick)
        bksp.setOnLongClickListener(bkspLongPress)
        done.setOnClickListener(keypadOnClick)
        goToLoginTV.setOnClickListener(reOpenLogin)

        otpView.addTextChangedListener(otpTextWatcher)
        verifyBtn.setOnClickListener { resendOtp() }



    }

    private var keypadOnClick = View.OnClickListener { v ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(30,VibrationEffect.DEFAULT_AMPLITUDE))
        }else{
            vibrator.vibrate(30);
        }

        when (v.id) {
            R.id.one -> otpView.setText(otpView.text.toString() + getString(R.string._1))
            R.id.two -> otpView.setText(otpView.text.toString() + getString(R.string._2))
            R.id.three -> otpView.setText(otpView.text.toString() + getString(R.string._3))
            R.id.four -> otpView.setText(otpView.text.toString() + getString(R.string._4))
            R.id.five -> otpView.setText(otpView.text.toString() + getString(R.string._5))
            R.id.six -> otpView.setText(otpView.text.toString() + getString(R.string._6))
            R.id.seven -> otpView.setText(otpView.text.toString() + getString(R.string._7))
            R.id.eight -> otpView.setText(otpView.text.toString() + getString(R.string._8))
            R.id.nine -> otpView.setText(otpView.text.toString() + getString(R.string._9))
            R.id.zero -> otpView.setText(otpView.text.toString() + getString(R.string._0))
            R.id.bksp -> try {
                otpView.setText(
                    otpView.text.toString().substring(0, otpView.text.toString().length - 1)
                )
            } catch (e: Exception) {
                otpView.startAnimation(anim)
                e.printStackTrace()
            }
            R.id.done -> {
                val otp = otpView.text.toString()
                if (otp.length != 6) {
                    otpView.startAnimation(anim)
                } else {
                    // TODO call verify otp function
                    Log.d("Done", otp)
                    verifyOtp(otp)
                }
            }
        }
    }


    var otpTextWatcher: TextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            if (count == 6) {
                verifyBtn.background = getDrawable(requireContext(),R.drawable.rect_btn_green)
                verifyBtn.text = "VERIFY"
                verifyBtn.setOnClickListener { verifyOtp(otpView.text.toString()) }
            } else {
                verifyBtn.background = getDrawable(requireContext(),R.drawable.rect_btn_red)
                verifyBtn.text = "RESEND OTP"
                verifyBtn.setOnClickListener { resendOtp() }
            }
        }

        override fun afterTextChanged(s: Editable) {}
    }

    var bkspLongPress = OnLongClickListener {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(30,VibrationEffect.DEFAULT_AMPLITUDE))
            }else{
                vibrator.vibrate(30);
            }
            otpView.setText("")
        } catch (e: Exception) {
            Log.w("num chars", "0")
        }
        false
    }

    fun relogin(){
        val i = Intent(requireContext(), DefaultActivity::class.java)
        sp.edit().remove("email").apply()
        sp.edit().remove("name").apply()
        sp.edit().remove("reg_id").apply()
        sp.edit().remove("verified").apply()
        sp.edit().remove("serviceStat").apply()
        startActivity(i)
        requireActivity().finish()
    }

    val reOpenLogin = View.OnClickListener{
        relogin()
    }

    val resCodes = ResponseCodes().responses

    fun verifyOtp(otp: String){
        progressLayout.visibility = View.VISIBLE
        lifecycleScope.launch {
            val email = sp.getString("email","")
            if (email.isNullOrBlank()){
                Snackbar.make(v,"No Email is present.",Snackbar.LENGTH_LONG).show()
                relogin()
            }else{
                val call: Call<VerifyOTPData> = api.verify_otp(otp,email)
                call.enqueue(object: Callback<VerifyOTPData>{
                    override fun onResponse(
                        call: Call<VerifyOTPData>,
                        response: Response<VerifyOTPData>
                    ) {
                        val res = response.body()
                        when(res!!.code){
                            resCodes["stat_otp_verified"]->{
                                sp.edit().putBoolean("verified", true).apply()
                                navController.navigate(R.id.action_OTPFragment_to_trackFragment)
                            }
                            resCodes["stat_otp_not_verified"]->{
                                Snackbar.make(v,"Wrong OTP", Snackbar.LENGTH_LONG).show()
                                otpView.setText("")
                                otpView.startAnimation(anim)
                            }
                            resCodes["stat_otp_error"]->{
                                Snackbar.make(v,"Something went wrong. Please try again", Snackbar.LENGTH_LONG).
                                    setAction("Try Again", View.OnClickListener {
                                        verifyOtp(otp)
                                    }).show()

                            }
                        }
                        progressLayout.visibility = View.GONE

                    }

                    override fun onFailure(call: Call<VerifyOTPData>, t: Throwable) {
                        Snackbar.make(v,"Something went wrong. Please try again", Snackbar.LENGTH_LONG).
                        setAction("Try Again", View.OnClickListener {
                            verifyOtp(otp)
                        }).show()
                        progressLayout.visibility = View.GONE

                    }

                })
            }

        }
    }

    fun resendOtp(){
        lifecycleScope.launch {
            progressLayout.visibility = View.VISIBLE
            val email = sp.getString("email","")
            if (email.isNullOrBlank()){

            }else{
                api.send_otp(email).enqueue(object :Callback<neilsayok.github.carlocatorapi.API.Data.OTPData>{
                    override fun onResponse(
                        call: Call<neilsayok.github.carlocatorapi.API.Data.OTPData>,
                        response: Response<neilsayok.github.carlocatorapi.API.Data.OTPData>
                    ) {
                        Log.d("OTP Mail Call success", response.body().toString())
                        Log.d("OTP codes", "${response.body()!!.code} -> ${resCodes["Mail_sent"]}")

                        progressLayout.visibility = View.GONE

                        when(response.body()!!.code){
                            resCodes["OTP_Mail_sent"]->{
                                Snackbar.make(v,"Email has been resent to $email. Please check your inbox/promotions/spam folders.",
                                    Snackbar.LENGTH_INDEFINITE).setAction("Open Mailbox", View.OnClickListener {
                                    try {
                                        val intent = Intent(Intent.ACTION_MAIN)
                                        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                                        requireContext().startActivity(intent)
                                    } catch (e: ActivityNotFoundException) {
                                        Toast.makeText(
                                            requireContext(),
                                            "There is no email client installed.",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }).show()

                            }
                            resCodes["OTP_Mail_not_sent"]->{
                                Snackbar.make(v,"Resending OTP failed. Please try again",Snackbar.LENGTH_INDEFINITE)
                                    .setAction("Resend OTP", View.OnClickListener { resendOtp() })
                                    .show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<neilsayok.github.carlocatorapi.API.Data.OTPData>, t: Throwable) {
                        progressLayout.visibility = View.GONE
                        Log.d("OTP Mail Call Fail", t.message.toString())

                        Log.d("OnFaliure","Otp Resend failed")
                        Snackbar.make(v,"Resending OTP failed. Please try again",Snackbar.LENGTH_INDEFINITE)
                            .setAction("Resend OTP", View.OnClickListener { resendOtp() })
                            .show()
                    }

                })
            }
        }
    }





}