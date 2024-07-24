package ch.ascendise.pokertracker

/**
 * Tracks chip balance
 *
 * Operation design makes sure that the amount of chips are moved accordingly and that there are never
 * more or less chips in circulation than started with
 *
 * @property startingAmount initializes the balance
 * @property owner defines who owns the chips. If this property is null, the balance belongs to the table
 */
abstract class ChipsBalance(startingAmount: Int, protected val owner: Player?) {

   var amount: Int
      protected set

   init {
      if(startingAmount < 0)
         throw IllegalArgumentException("Starting amount cannot be less than 0")
      amount = startingAmount
   }

   abstract fun deposit(amount: Chips)
   abstract fun withdraw(amount: Int): Chips

   /**
    * Defines a transaction of chips in limbo between balances
    * The owner removed the chips from his balance with the specified value, and they are now
    * to be moved into the new balance
    */
   abstract class Chips(amount: Int, val owner: Player?) : AutoCloseable {

      var amount: Int = amount
         private set

      /**
       * Moves the chips into the provided balance
       * Amount will be set to 0
       *
       * @param targetBalance
       */
      fun depositTo(targetBalance: ChipsBalance) {
         targetBalance.amount += amount
         this.amount = 0
      }

      /**
       * Checks if chips were all transferred. If not, an exception is thrown
       * to mark the position of unused chips and notify the users
       *
       */
      override fun close() {
         if(amount > 0)
            throw ChipsDestroyedException(this)
      }

   }
}