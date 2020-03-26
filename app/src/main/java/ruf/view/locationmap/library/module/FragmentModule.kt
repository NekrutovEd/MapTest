package ruf.view.locationmap.library.module

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import kotlinx.android.parcel.IgnoredOnParcel
import toothpick.Scope
import kotlin.reflect.KClass

const val INDIVIDUALITY = "INDIVIDUALITY"

abstract class FragmentModule(private val fragment: Class<out Fragment>) : ScopeModule(), Parcelable {

    constructor(fragment: KClass<out Fragment>) : this(fragment.java)

    abstract override val scopeName: String

    @IgnoredOnParcel
    lateinit var navigatorScopeName: String

    open fun customizeTransactionsWithModule(transaction: FragmentTransaction): FragmentTransaction = transaction

    fun createFragment(): Fragment = fragment.newInstance().also {
        val arguments = it.arguments ?: Bundle()
        arguments.putString(INDIVIDUALITY, scopeName)
        it.arguments = arguments
    }

    protected open fun Scope.openDependentScopes(): Scope = this

    protected fun Scope.installAndOpenSharedScope(sharedModule: SharedModule): Scope {
        sharedModule.navigatorScopeName = navigatorScopeName
        sharedModule.installModule()
        return openSubScope(sharedModule.scopeName)
    }

    final override fun Scope.openSubScopes(): Scope {
        return openSubScope(ScopeIdentifier(NavigatorModule::class, navigatorScopeName))
            .openDependentScopes()
            .openSubScope(ScopeIdentifier(this@FragmentModule::class, scopeName))
    }

    companion object {
        inline fun <reified SM : FragmentModule> Fragment.injectScope(arguments: Bundle?) {
            this.injectScope<SM>(arguments?.getString(INDIVIDUALITY) ?: "")
        }
    }
}