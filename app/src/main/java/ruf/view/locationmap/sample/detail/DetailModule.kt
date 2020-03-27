package ruf.view.locationmap.sample.detail

import kotlinx.android.parcel.Parcelize
import ruf.view.multi_module_navigation.module.FragmentModule
import ruf.view.shared_listdata.ExampleSharedModule
import toothpick.Scope
import toothpick.ktp.binding.bind
import java.util.*

@Parcelize
data class DetailModule(
    private val data: DetailData,
    private val scopeNameModel: ExampleSharedModule.ScopeNameModel,
    override val scopeName: String = UUID.randomUUID().toString()
) : FragmentModule(DetailFragment::class) {

    init {
        bind<DetailData>().toInstance(data)
        bind<DetailRouter>().toClass<DetailRouter>().singleton()
        bind<DetailPresenter>().toClass<DetailPresenter>().singleton()
    }

    override fun Scope.openDependentScopes(): Scope = openSubScope(scopeNameModel)
}