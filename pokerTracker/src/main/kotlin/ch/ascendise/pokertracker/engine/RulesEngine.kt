package ch.ascendise.pokertracker.engine

abstract class RulesEngine<State, Command>(
    private val beforeRules: Iterable<Rule<State>> = emptyList(),
    private val afterRules: Iterable<Rule<State>> = emptyList()) {

    var state: State? = null
        protected set

    protected abstract fun onExecute(command: Command)

    fun execute(command: Command) {
        beforeRules.onEach { it.act(state) }
        onExecute(command)
        afterRules.onEach { it.act(state) }
    }

}