package it.s.skin.model

import it.s.skin.R

enum class SkinType(val value:Int, val referenceString: Int){
        NORMAL(0, R.string.normal_skin),
        OILY (1, R.string.oily_skin),
        DRY (2, R.string.dry_skin),
        SENSITIVE (3, R.string.sensitive_skin);

        companion object {
                fun getByValue(value: Int): SkinType{
                        return when(value){
                                0 -> NORMAL
                                1 -> OILY
                                2 -> DRY
                                3 -> SENSITIVE
                                else -> NORMAL
                        }
                }
        }
}
