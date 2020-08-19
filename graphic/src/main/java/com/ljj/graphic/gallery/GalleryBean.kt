package com.ljj.graphic.gallery

sealed class GalleryBean(val url: String?) {
    class GalleryImageTarget(url: String?) : GalleryBean(url)
    class GalleryGifImageTarget(url: String?) : GalleryBean(url)
    class GalleryVideoTarget(url: String?) : GalleryBean(url)
}
