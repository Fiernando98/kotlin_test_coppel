package com.fernandolerma.super_heros_coppel.models

class AvatarModel(name: String?, imagePath: String?) {
    private var name: String?
    private var imagePath: String?

    init {
        this.name = name!!
        this.imagePath = imagePath!!
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String?) {
        this.name = name!!
    }

    fun getImagePath(): String? {
        return imagePath
    }

    fun setImagePath(path: String?) {
        imagePath = path!!
    }
}