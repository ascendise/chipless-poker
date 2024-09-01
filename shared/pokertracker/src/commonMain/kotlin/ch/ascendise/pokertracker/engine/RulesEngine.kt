package ch.ascendise.pokertracker.engine

internal abstract class RulesEngine<State, Command>(
    private val beforeRules: Iterable<Rule<State>> = emptyList(),
    private val afterRules: Iterable<Rule<State>> = emptyList()) {

    var state: State? = null
        protected set

    protected abstract fun onExecute(command: Command)

    fun execute(command: Command) {
        beforeRules.onEach { applyRules(it) }
        onExecute(command)
        afterRules.onEach { applyRules(it) }
    }

    private fun applyRules(rule: Rule<State>) {
        val result = rule.act(state)
        if(result.applied)
           state = result.state
    }

}