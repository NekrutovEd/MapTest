package ruf.view.multi_module_navigation.module

import androidx.annotation.IdRes
import ruf.view.multi_module_navigation.ILauncher
import ruf.view.multi_module_navigation.module.ScopeModule.Companion.randomScopeIdentifier
import ruf.view.multi_module_navigation.navigator.INavigator
import toothpick.ktp.KTP
import toothpick.ktp.extension.getInstance
import java.util.*
import javax.inject.Provider

abstract class NavigatorProvider(
    @IdRes
    private val containerId: Int,
    private val launcher: ILauncher?,
    private val scopeIdentifier: ScopeModule.ScopeIdentifier = randomScopeIdentifier<NavigatorModule>()
) : Provider<INavigator> {

    private fun initNavigator() {
        NavigatorModule(containerId, launcher, scopeIdentifier).installModule()
    }

    final override fun get(): INavigator {
        initNavigator()
        return KTP.openScope(scopeIdentifier).getInstance()
    }
}