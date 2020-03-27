package ruf.view.multi_module_navigation.command

import ruf.view.multi_module_navigation.navigator.INavigatorManager

interface ICommand {

    fun execute(navigatorManager: INavigatorManager)
}