package random

trait RandomWithState {
  def nextInt(n: Int): (Int, RandomWithState)
}
