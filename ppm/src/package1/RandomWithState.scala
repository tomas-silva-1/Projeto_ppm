package package1

trait RandomWithState {
    def nextInt(n: Int): (Int, RandomWithState)
}
