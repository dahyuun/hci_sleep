package com.example.hci_sleep

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hci_sleep.ui.theme.Hci_sleepTheme


class MainActivity : ComponentActivity() {
    companion object {
        const val PERMISSION_REQUEST_CODE = 101
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startCameraService() {
        val intent = Intent(this, CameraService::class.java)
        this.startForegroundService(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Hci_sleepTheme {
                StartCameraServiceButton(onClick = {
                    startCameraService()
                })
//                StopCameraServiceButton(onClick = {
//                    stopCameraService(this)
//                })
            }
        }

        // check whether camera permission is granted and request if not
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                PERMISSION_REQUEST_CODE
            )
        }


    }
}


@Composable
fun StartCameraServiceButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Start Camera Service")
    }
}

@Composable
fun StopCameraServiceButton(onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = "Stop Camera Service")
    }
}