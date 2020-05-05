# Multi-Module-Navigation
## No finished

Библиотека, позволяющая сделать навигацию в мульти-модульном android проекте не через вызов фрагмента, а через DI-модули.

На данный момент поддерживается только Toothpick. 
В будущем планируется поддержка Koin. 
С Dagger2 пока вопросы. На мой взгляд с ним не получится сделать также просто.

### Обозначения:

Модуль = gradle module. Набор папок и файлов, относящихся к одному экрану. 
         Можно разделить на множество маленьких модулей или объеденить в один общий. Для примера 1 модуль = 1 фрагмент.

DI-модуль = Класс, наследник FragmentModule(из либы), который наследник Module(из toothpick). 
            Отвечает за создание экрана и предоставления всех зависимостей.


### Плюсы у библиотеки MMN:
- Реализация модуля зависит только от core модуля. Он ничего не знает о других модулях.
- Наружу модуля нужно показать только DI-модуль и интерфейс для роутера.
- Логика навигации инкапсулируется в Mediator.
- Можно сделать DI-модуль в виде контейнера и вкладывать в него другой DI-модуль. 
  (Например реализовать модуль toolbar. Тогда остальные модули не будут об этом думать, а мы сможем настраивать отображение в медиаторе)
- Можно запустить приложение сразу с нужного модуля, благодаря использованию нужного launcher.
- Можно использовать MVP, MVVM и другие паттерны.
  (MVP и MVVM проверены и выглядят прекрасно, остальные не реализовывал, но проблем быть не должно)
- Никаких больше newInstance во фрагментах. Данные передаются через DI-модуль и сразу доставляются в presenter или viewModel.
- Не нужно думать о сохранении переданных данных. Они сохранятся и востановятся автоматически.
- Вообще весь стек навигации, вместе с переданными данными автоматически пересоздастся и востановит свое состояние. Даже при уничтожении процесса.
  (Для кастомных view нужно не забыть реализовать сохраниение и востановление состояние в самой view)
- Не нужно управлять скоупом DI-модуля. Он не зависит от жц фрагмента, так что будет открыт до тех пор, пока данный DI-модуль находится в стеке навигатора.
  (Фрагмент после пересоздания получит зависимости из уже открытого модуля. Соответственно .singleton() просто работает. Не нужно париться, что скоуп переоткроется из-за жц фрагмента)
- В DI модуле проставляются только те зависимости, которые действительно нужны этому модулю. У него есть доступ только к общим зависимостям, или тем зависимостям которые он запросит, из ранее открытых независимых скоупов.
- Можно подменять или мокать реализацию роутеров. Например для тестов, покрытия фичетоглами или A/B тестирования.
- Не нужно переписывать весь проект, чтобы внедрить. Можно начать только с одного экрана, с помощью прослойки ContainerFragment.
- Анимация перехода в роутере. Можно сделать анимацию для всех переходов на конктный модуль, и переопределить ее для конкретного случая.
- Навигация осуществляется командами, которые работают через буфер, так что выполнятся, даже при вызове во время пересоздания. (Так же, как в Cicherone)

### Минусы:
- Зависимость от toothpick.
- Навигация только на фрагментах.(можно исправить или использовать прямой переход на другую активити, но зачем?)
    Так что в конечном итоге придется использовать сингл-активити. Для меня это даже плюс. 
- В начале могут быть сложности с пониманием при написании универсальных модулей. Зато нету сложностей с их использованием. Возможен прекрасный декларативный стиль.
  (В будущем появится либа с готовыми универсальными модулями, типа toolbar)
- Нужно во фрагменте контейнере вызвать методы навигатора, для управления состоянием и привязки/отвязки фрагмент менеджера. Целых 4 метода.
- Возможно не простой порог входа, для полного понимания. Зато можно сказать "делай по шаблону" для простых модулей.

### Хотелки:
- Создать плагин для генерации шаблонов. (Кода мало, но даже его лень писать)
- Создать библиотеку с универсальными модулями - контейнерами, диалогами, заглушками.
- Сделать настройку для статического анализатора.


### Принцип работы: ( -> = то, что мы пишем | (->) = то, что делает либа )
- -> Стартует Activity или ContainerFragment
- -> Создается NavigatorProvider c нужным id для ViewGroup из xml, лаунчером и остальными необязательными параметрами.
- -> Получаем из NavigatorProvider INavigatorLifeCycle и вызываем его методы в нужных методах жц
- (->) Создается NavigatorModule, открывается скоуп навигатора.
- (->) Создается Navigator и получает команду forward с модулем из launcher (если он был передан) 
- (->) Создается модуль, открывается скоуп, создается фрагмент, проставляются зависимости, фрагмент отображается на viewGroup навигатора.
- -> Вызывается метод роутера
- -> В реализации роутера создаются нужные команды
- -> Полученному через инъекции ICommanderNavigator, отдаются команды на исполнение
- (->) Нужный Navigator получает команду и выполняет ее

### Схема зависимостей: https://www.figma.com/file/BBDBKeaGKbkd9aohT3mqmc/Multi-Module-Navigation?node-id=0%3A1


#### Сама библиотека находится в модуле "general_dependencies : multi_module_navigation"











