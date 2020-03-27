package ruf.view.mediator.detail

import kotlinx.android.parcel.Parcelize
import ruf.view.core.RouterClass
import ruf.view.mediator.detail.DetailRouter

@Parcelize
object DetailRouterClass : RouterClass<DetailRouter> {

    override val kClass get() = DetailRouter::class
}