package com.demo.whatsappcall

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.appcompat.app.AppCompatActivity
import com.demo.whatsappcall.databinding.ActivityMainBinding

const val WHATS_APP_PACKAGE_NAME = "com.whatsapp"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)

        binding.phoneCallButton.setOnClickListener {
            makePhoneCall()
        }
    }

    private fun makePhoneCall() {
        val telManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager
        val simState = telManager.simState

        when (simState) {
            TelephonyManager.SIM_STATE_ABSENT -> {
                println("❤ SIM card is absent")
                packageManager.openWhatsApp()
            }

            TelephonyManager.SIM_STATE_NETWORK_LOCKED -> {
                println("❤ SIM card is locked")
            }

            TelephonyManager.SIM_STATE_PIN_REQUIRED -> {
                println("❤ SIM card PIN is required")
            }

            TelephonyManager.SIM_STATE_UNKNOWN -> {
                println("❤ Unknown SIM card error")
            }

            TelephonyManager.SIM_STATE_READY -> {
                println("❤ SIM card is ready")
            }
            TelephonyManager.SIM_STATE_CARD_IO_ERROR -> {
                println("❤ SIM card IO error")
            }
            TelephonyManager.SIM_STATE_CARD_RESTRICTED -> {
                println("❤ SIM card is restricted")
            }
            TelephonyManager.SIM_STATE_NOT_READY -> {
                println("❤ SIM card is not ready")
            }
            TelephonyManager.SIM_STATE_PERM_DISABLED -> {
                println("❤ SIM card PERM is disabled")
            }
            TelephonyManager.SIM_STATE_PUK_REQUIRED -> {
                println("❤ SIM card PUK is required")
            }
        }
    }

    private fun PackageManager.openWhatsApp() {

        if (isPackageInstalled(WHATS_APP_PACKAGE_NAME)) {
            startActivity(
                    Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(
                                    "https://api.whatsapp.com/send?phone=+358465712157&text=abcde"
                            )
                    )
            )
        } else {
            println("❤ WhatsApp not installed")
            openWhatsAppDownloadPage()
        }


    }

    private fun PackageManager.isPackageInstalled(packageName: String): Boolean {
        return try {
            getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    private fun openWhatsAppDownloadPage() {
        openBrowser("https://www.whatsapp.com/android/")
    }

    private fun openBrowser(url: String) {
        val uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}