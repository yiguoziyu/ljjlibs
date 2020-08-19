package com.ljj.graphic.image

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentActivity
import com.ljj.base.constants.PAGE_KEY_URL
import com.ljj.base.helper.PermissionHelper
import com.ljj.base.helper.ToastHelper
import com.ljj.graphic.R
import com.ljj.graphic.compression.ComPressionProxy
import com.ljj.graphic.compression.CompressCallBack
import com.yalantis.iucrop.UCrop
import com.yalantis.iucrop.UCropActivity
import java.io.File
import java.io.FileInputStream

/**
 * 图片处理器
 */
class ImageGraphicManger(val mActvity: FragmentActivity) {
    //文件地址
    private var mCurrentFile: File? = null

    //图片地址
    private var mImagePath: String = ""

    //是否压缩
    var needCompression = true

    //是否裁剪
    var needCrop = true

    //回调
    private var mIamgeGraphicCallBack: IamgeGraphicCallBack? = null

    fun initConfig(needCompression: Boolean = true, needCrop: Boolean = true): ImageGraphicManger {
        this.needCompression=needCompression
        this.needCrop=needCrop
        return this
    }

    fun setIamgeGraphicCallBack(mIamgeGraphicCallBack: IamgeGraphicCallBack?): ImageGraphicManger {
        this.mIamgeGraphicCallBack = mIamgeGraphicCallBack
        return this
    }

    companion object {
        private const val TAG = "ImageGraphicManger"
        private const val TAKE_PHOTO_CODE = 0x1005
        private const val CHOOSE_PHOTO_CODE = 0x1002
        private const val CROP_PHOTO_CODE = 0x1003
    }

    /**
     * 选择照片
     */
    fun chooseImage(func: () -> Unit) {
        PermissionHelper.checkSdcardPermission(activity = mActvity, success = {
            Log.e(TAG, "start chooseImage")
            func()
        }, error = {
            Log.e(TAG, "start chooseImage no cameraPermission")
        })
    }

    /**
     * 调用系统拍照
     */
    fun takePicture() {
        PermissionHelper.checkCameraPermission(activity = mActvity, success = {
            Log.e(TAG, "start takePicture")
            mActvity.apply {
                val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                val mImagePath = "${mActvity.getExternalFilesDir(null)}/img/"
                intent.resolveActivity(packageManager)?.let {
                    mkdirFile(mImagePath)
                    mCurrentFile = File(mImagePath, System.currentTimeMillis().toString() + ".jpg")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, getUri())
                    startActivityForResult(intent, TAKE_PHOTO_CODE)
                }
            }
        }, error = {
            Log.e(TAG, "start takePicture no cameraPermission")
        })
    }


    private fun getUri(): Uri? {
        return if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(mActvity, "${mActvity.packageName}.app.fileprovider", mCurrentFile!!)
        } else {
            Uri.fromFile(mCurrentFile)
        }
    }

    /**
     * 图片裁剪
     */
    fun takeImageCrop(path: String) {
        Log.e(TAG, "start takeImageCrop")
        val uri = Uri.Builder()
                .scheme("file")
                .appendPath(path)
                .build()
        val crop = UCrop.Options()
        crop.setCompressionFormat(Bitmap.CompressFormat.PNG)
        crop.withMaxResultSize(1000, 1000)//730,493
        crop.withAspectRatio(1f, 1.5f)
        crop.setCropGridColor(0x00ffffff)
        crop.setCircleDimmedLayer(false)
        crop.setCropGridColor(0x00ffffff)
        crop.setAllowedGestures(UCropActivity.ALL, UCropActivity.ALL, UCropActivity.ALL)
        crop.setToolbarTitle("\u3000")
        crop.setHideBottomControls(true)
        crop.setStatusBarColor(mActvity.resources.getColor(R.color.colorPrimary))
        crop.setToolbarColor(mActvity.resources.getColor(R.color.colorPrimary))
        crop.setActiveWidgetColor(mActvity.resources.getColor(R.color.colorPrimary))
        crop.setShowCropFrame(false)
        //结束设置
        mImagePath = mActvity.getExternalFilesDir(null)!!.toString() + "/img/"
        mkdirFile(mImagePath)
        val name = System.currentTimeMillis()
        mCurrentFile = File(mImagePath, "$name.jpg")
        val destUri = Uri.Builder()
                //.scheme("img")
                .appendPath(mActvity.getExternalFilesDir(null)!!.toString() + "/img/")
                .appendPath("$name.jpg")
                .build()
        UCrop.of(uri!!, destUri)
                .withOptions(crop)
                .start(mActvity, CROP_PHOTO_CODE)
    }


    /***判断是否符合图片要求***/
    private fun checkImgeRuler(): Boolean {
        if (mCurrentFile == null) return false
        if (mCurrentFile!!.path.endsWith(".gif")) {
            ToastHelper.showToast("不支持gif格式")
            return false
        } else {
            val options = BitmapFactory.Options();
            options.inJustDecodeBounds = true;//这个参数设置为true才有效，
            val bmp = BitmapFactory.decodeFile(mCurrentFile!!.path, options);//这里的bitmap是个空
            val outHeight = options.outHeight
            val outWidth = options.outWidth
            return if (outHeight >= 300 && outWidth >= 300 && getFileSize(mCurrentFile!!) > 1024 * 40) {
                true
            } else {
                ToastHelper.showToast("照片尺寸太小了，请换张清晰本人照片")
                false
            }
        }
    }

    /**
     * 获取指定文件大小 　　
     */
    private fun getFileSize(file: File): Long {
        var size = 0
        if (file.exists()) {
            val fis = FileInputStream(file);
            size = fis.available()
        }
        return size.toLong()
    }


    private fun mkdirFile(path: String): File {
        val file = File(path)
        if (!file.exists()) {
            file.mkdir()
        }
        return file
    }

    /**
     * 需要与Activity的onActivityResult绑定
     */
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                TAKE_PHOTO_CODE -> dealPicture()
                CHOOSE_PHOTO_CODE -> {
                    val url = data?.getStringExtra(PAGE_KEY_URL) ?: return
                    mCurrentFile = File(url)
                    dealPicture()
                }
                CROP_PHOTO_CODE -> {
                    if (needCompression) {
                        takeCompression(mCurrentFile)
                    } else {
                        processingSuc()
                    }
                }
            }
        }
    }

    private fun dealPicture() {
        if (checkImgeRuler()) {
            mCurrentFile?.let { file ->
                when {
                    needCrop -> takeImageCrop(file.path)
                    needCompression -> takeCompression(file)
                    else -> processingSuc()
                }
            }
        }
    }

    /**
     * 处理成功
     */
    private fun processingSuc() {
        mIamgeGraphicCallBack?.processingSuc(mCurrentFile)
    }

    /***
     * 压缩
     */
    private fun takeCompression(mCurrentFile: File?) {
        ComPressionProxy.create().compression(mActvity, mCurrentFile?.path, object : CompressCallBack {
            override fun onStart(postion: Int?) {}

            override fun onSuccess(file: File?, postion: Int?) {
                mIamgeGraphicCallBack?.processingSuc(file)
            }

            override fun onError(e: Throwable?, postion: Int?) {
                mIamgeGraphicCallBack?.processingError(e?.message)
            }
        }, 0)

    }

    interface IamgeGraphicCallBack {
        fun processingSuc(mFile: File?)
        fun processingError(e: String?)
    }
}