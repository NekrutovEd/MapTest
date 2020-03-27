package ruf.view.locationmap.sample.list

import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import ruf.view.locationmap.sample.ListNavigator
import ruf.view.locationmap.sample.common.ExampleSharedModule
import ruf.view.multi_module_navigation.module.FragmentModule
import ruf.view.multi_module_navigation.navigator.INavigatorCommand
import ruf.view.multi_module_navigation.navigator.INavigatorLifeCycle
import toothpick.Scope
import toothpick.ktp.binding.bind
import java.util.*

@Parcelize
data class ListModule(
    private val tag: String,
    override val scopeName: String = UUID.randomUUID().toString()
) : FragmentModule(ListFragment::class) {

    @IgnoredOnParcel
    private val sharedModule = ExampleSharedModule(tag, "ShareModule $scopeName")

    init {
        val provider = ListFragment.ListNavigatorProvider()

        bind<INavigatorLifeCycle>().withName(ListNavigator::class).toProviderInstance(provider)
        bind<INavigatorCommand>().withName(ListNavigator::class).toProviderInstance(provider)
        bind<ListRouter>().toClass<ListRouter>().singleton()
        bind<ListPresenter>().toClass<ListPresenter>().singleton()
    }

    override fun onCloseScope() = sharedModule.close()

    override fun Scope.openDependentScopes(): Scope = installAndOpenSharedScope(sharedModule)
    /*.openSubScope(RepositoryModule::class)*/
    /*.openSubScope(ApiModule::class)*/
}
