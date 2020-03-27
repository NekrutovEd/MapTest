package ruf.view.feature_list_presentation

import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize
import ruf.view.core.RouterClass
import ruf.view.multi_module_navigation.module.FragmentModule
import ruf.view.multi_module_navigation.navigator.ICommanderNavigator
import ruf.view.multi_module_navigation.navigator.INavigatorLifeCycle
import ruf.view.shared_listdata.ExampleSharedModule
import toothpick.Scope
import toothpick.ktp.binding.bind
import java.util.*

@Parcelize
data class ListModule (
    private val routerClass: RouterClass<IListRouter>,
    private val tag: String,
    override val scopeName: String = UUID.randomUUID().toString()
) : FragmentModule(ListFragment::class) {

    @IgnoredOnParcel
    private val sharedModule = ExampleSharedModule(tag, "ShareModule $scopeName")

    init {
        val provider = ListFragment.ListNavigatorProvider()

        bind<INavigatorLifeCycle>().withName(ListNavigator::class).toProviderInstance(provider)
        bind<ICommanderNavigator>().withName(ListNavigator::class).toProviderInstance(provider)
        bind<IListRouter>().toClass(routerClass.kClass).singleton()
        bind<ListPresenter>().toClass<ListPresenter>().singleton()
    }

    override fun onCloseScope() = sharedModule.close()

    override fun Scope.openDependentScopes(): Scope = installAndOpenSharedScope(sharedModule)
    /*.openSubScope(RepositoryModule::class)*/
    /*.openSubScope(ApiModule::class)*/
}
