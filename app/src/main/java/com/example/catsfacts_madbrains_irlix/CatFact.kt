package com.example.catsfacts_madbrains_irlix

import io.realm.RealmObject

open class CatFact(var factText: String,  var iconUrl: String,
           var isFavorit: Boolean): RealmObject() {

    constructor() : this("", "", false)

}
