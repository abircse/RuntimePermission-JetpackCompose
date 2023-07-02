package com.jetpackcompose.jetpackcomposeruntimepermission

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.jetpackcompose.jetpackcomposeruntimepermission.ui.theme.JetpackComposeRuntimePermissionTheme

@OptIn(ExperimentalPermissionsApi::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackComposeRuntimePermissionTheme {
                MultiplePermissions()
            }
        }
    }
}

@ExperimentalPermissionsApi
@Composable
fun MultiplePermissions() {
    val permissionStates = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.CAMERA,
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = lifecycleOwner, effect = {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    permissionStates.launchMultiplePermissionRequest()
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        permissionStates.permissions.forEach { it ->
            when (it.permission) {
                Manifest.permission.CAMERA -> {
                    when {
                        it.hasPermission -> {
                            /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */
//                            Text(text = "Read Ext Storage permission has been granted")
                        }

                        it.shouldShowRationale -> {
                            /*Happens if a user denies the permission two times

                             */
                            Text(text = "Read Ext Storage permission is needed")
                        }

                        !it.hasPermission && !it.shouldShowRationale -> {
                            /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                            Text(text = "Navigate to settings and enable the Storage permission")

                        }
                    }
                }

                Manifest.permission.POST_NOTIFICATIONS -> {
                    when {
                        it.hasPermission -> {
                            /* Permission has been granted by the user.
                               You can use this permission to now acquire the Notification permission for Android 12 or above.
                               You can perform some other tasks here.
                            */
//                            Text(text = "Read Ext Storage permission has been granted")
                        }

                        it.shouldShowRationale -> {
                            /*Happens if a user denies the permission two times

                             */
                            Text(text = "Read Ext Storage permission is needed")
                        }

                        !it.hasPermission && !it.shouldShowRationale -> {
                            /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                            Text(text = "Navigate to settings and enable the Notification Permission permission")

                        }
                    }
                }

                Manifest.permission.ACCESS_FINE_LOCATION -> {
                    when {
                        it.hasPermission -> {
                            /* Permission has been granted by the user.
                               You can use this permission to now acquire the location of the device.
                               You can perform some other tasks here.
                            */

                        }

                        it.shouldShowRationale -> {
                            /*Happens if a user denies the permission two times

                             */
                            Text(text = "Location permission is needed")

                        }

                        !it.hasPermission && !it.shouldShowRationale -> {
                            /* If the permission is denied and the should not show rationale
                                You can only allow the permission manually through app settings
                             */
                            Text(text = "Navigate to settings and enable the Location permission")

                        }
                    }
                }
            }
        }
    }
}
