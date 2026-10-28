package com.sinamtd.sixappcompose.presentation.navigation

sealed class Screens(val rout: String) {
    object Main: Screens("main")
    object Details: Screens("details")

    object Charts: Screens("charts")




    fun routeWithArgs(vararg args: String)=
        buildString {
            append(rout)
            args.forEachIndexed { index, _ ->
                append("?$index={$index}")
            }
        }

    fun paramsWithArgs(vararg args: String)=

        buildString {
            append(rout)
            args.forEachIndexed { index, value ->
                append("?$index=$value")
            }
        }


}