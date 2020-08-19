package com.ljj.base.helper

import android.Manifest
import android.annotation.SuppressLint
import androidx.fragment.app.FragmentActivity
import com.tbruyelle.rxpermissions2.RxPermissions


/**
 * 权限帮助类
 */
object PermissionHelper {
    private val TAG = "PermissionHelper"

    @SuppressLint("CheckResult")
    fun checkSdcardPermission(activity: FragmentActivity? = null, noPermissionTip: String? = null, success: () -> Unit = {}, error: () -> Unit = {}) {
        activity?.let {
            RxPermissions(it).apply {
                request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe { flag ->
                            if (flag) {
                                success()
                            } else {
                                showPermissionDialog(noPermissionTip
                                        ?: "请打开存储权限")
                                error()
                            }
                        }
            }
        }

    }

    @SuppressLint("CheckResult")
    fun checkCameraPermission(activity: FragmentActivity? = null, noPermissionTip: String? = null, success: () -> Unit = {}, error: () -> Unit = {}) {
        activity?.let {
            RxPermissions(it).apply {
                request(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                        .subscribe { flag ->
                            if (flag) {
                                success()
                            } else {
                                showPermissionDialog(noPermissionTip
                                        ?: "请打开相机、存储权限")
                                error()
                            }
                        }
            }
        }
    }

    @SuppressLint("CheckResult")
    fun checkPhonePermission(activity: FragmentActivity? = null, success: () -> Unit = {}, error: () -> Unit = {}) {
        activity?.let {
            RxPermissions(it).request(Manifest.permission.READ_PHONE_STATE)
                    ?.subscribe {flag->
                        if (flag) {
                            success()
                        } else {
                            error()
                        }
                    }
        }
    }

    fun checkLocationPermission(activity: FragmentActivity? = null,success: () -> Unit = {}, error: () -> Unit = {}) {
        activity?.let {
            RxPermissions(it).request(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe {flag->
                        if (flag) {
                            success()
                        } else {
                            showPermissionDialog("请打开地理位置权限")
                            error()
                        }

                    }
        }
    }

    fun showPermissionDialog(content: String) {
        ToastHelper.showToast(content)
    }


}