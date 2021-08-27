package com.fernandolerma.super_heros_coppel.models

class AvatarModel(title: String?, imagePath: String?) {
    private var title: String?
    private var imagePath: String?

    init {
        this.title = title!!
        this.imagePath = imagePath!!
    }

    fun getTitle(): String? {
        return title
    }

    fun setTitle(title: String?) {
        this.title = title!!
    }

    fun getImagePath(): String? {
        return imagePath
    }

    fun setImagePath(path: String?) {
        imagePath = path!!
    }
}