package ruf.view.mediator.router.detail

import kotlinx.android.parcel.Parcelize
import ruf.view.core.IRouterClass
import ruf.view.feature_detail_presentation.DetailData
import ruf.view.feature_detail_presentation.DetailModule
import ruf.view.feature_detail_presentation.IDetailRouter
import ruf.view.feature_list_presentation.ListModule
import ruf.view.multi_module_navigation.ParentNavigator
import ruf.view.multi_module_navigation.navigator.INavigator
import ruf.view.multi_module_navigation.navigator.ICommanderNavigator
import ruf.view.multi_module_navigation.navigator.backTo
import ruf.view.shared_listdata.ExampleSharedModule
import toothpick.InjectConstructor

@InjectConstructor
class DetailRouter(
    @ParentNavigator private val commander: ICommanderNavigator,
    private val scopeNameIdentifier: ExampleSharedModule.ExampleSharedIdentifier
) : IDetailRouter {

    override fun addDetail(data: DetailData) {
        commander.forward {
            val newData = data.copy(text = "${data.text}+${++(commander as INavigator).counter}")

            DetailModule(Class, newData, scopeNameIdentifier)
        }
    }

    override fun replace(data: DetailData) {
        commander.replace {
            val newData = data.copy(text = data.text.replaceAfterLast('+', (++(commander as INavigator).counter).toString()))

            DetailModule(Class, newData, scopeNameIdentifier)
        }
    }

    override fun back() = commander.back()

    override fun closeDetail() = commander.backTo<ListModule>()

    @Parcelize
    object Class : IRouterClass<DetailRouter> {
        override val kClass get() = DetailRouter::class
    }
}
