package ruf.view.locationmap.library.module

import android.support.v4.app.DialogFragment
import kotlin.reflect.KClass

abstract class DialogFragmentModule(fragment: Class<out DialogFragment>) : FragmentModule(fragment) {

    constructor(fragment: KClass<out DialogFragment>) : this(fragment.java)
}