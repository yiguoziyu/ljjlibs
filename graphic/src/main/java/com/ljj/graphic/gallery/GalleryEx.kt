package com.ljj.graphic.gallery

/**
 * 检查url类型
 */
fun getUrlType(url: String?): GalleryBean {
    if (url?.endsWith(".gif") == true) {
        return GalleryBean.GalleryGifImageTarget(url)
    } else {
        return GalleryBean.GalleryImageTarget(url)
    }

}