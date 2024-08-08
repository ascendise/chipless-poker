package ch.ascendise.pokertracker.engine

internal abstract class Rule<State> {

    /**
     * Evaluates current state and applies changes if applicable
     *
     * @param state
     * @return new state or null if rule was not applied
     */
    fun act(state: State?): Result<State?> {
        if(evaluate(state)) {
            return apply(state).let { Result.applied(it) }
        }
        return Result.none()
    }

    /**
     * Defines the condition at which the rule should be applied
     */
    protected abstract fun evaluate(state: State?): Boolean


    /**
     * Applies changes to state
     *
     * @param state
     */
    protected abstract fun apply(state: State?): State?

    data class Result<State>(
        val applied: Boolean,
        val state: State?) {

        companion object {

            fun <State> applied(state: State)
                = Result<State>(true, state)

            fun <State> none()
                    = Result<State>(false, null)

        }

    }
}