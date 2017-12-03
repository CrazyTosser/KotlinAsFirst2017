package lesson7.task2

class Astar {
    data class SolverState(
            var board: Board,
            var moves: Int,
            var previous: SolverState?
    )

    data class SolverSolution(
            var states: MutableList<SolverState>,
            var moves: Int,
            var solvable: Boolean,
            var context: Astar
    )

    data class PriorityQueueItem(
            var priority: Int,
            var board: Board
    )


    /**
     * Checks to see whether a provided board has been used within a queue.
     * @param {Board} board - The board to check against the queue.
     * @param {Array<SolverState>} queue - The queue to check in.
     * @returns boolean - true if the board has been used previously.
     */
    fun hasBoardBeenUsed(board: Board, queue: Array<SolverState>): Boolean =
            queue.all { board != it.board }

    /** The current state of the Solver */
    val state: SolverState

    /** The starting board we are solving for */
    val start: Board

    /** The goal board we are trying to get to */
    val goal: Board

    /** The Solvers moves queue */
    // history: Array<SolverState>;

    constructor(initial: Board, state: SolverState) {
        this.start = initial
        this.goal = Board(initial.goal);
        this.state = state
    }

    /**
     * Main solver function. Continues searching for moves until the state board equals the goal board.
     * @todo: This should probably have less dependencies so that multiple solves can be happening at once.
     */
    fun solve(): SolverSolution {
        var state = SolverState(this.start, 0, null)
        val history = mutableListOf(state)
        while (!state.board.equals(this.goal)) {
            state = getNextMove(state, history)!!;
            history.add(state)
        }
        return SolverSolution(history, state.moves, true, this)
    }

    fun getNextMove(state: SolverState, history: List<SolverState>): SolverState? {
        println(state.board)
        // Create a new priority queue with all neighbors that haven't already been used
        val neighbors = state.board.getNeighbors()
                .filter { !hasBoardBeenUsed(it, history.toTypedArray()) }
        val priority = createPriorityQueue(neighbors, state.moves)

        // If the priority queue is empty that means we've tried everything already
        if (priority.isEmpty()) {
            return null
        }

        // Return a new state
        return SolverState(priority[0].board, state.moves + 1, state)
    }


    fun createPriorityQueue(boards: List<Board>, moves: Int): List<PriorityQueueItem> {
        val res = boards.map { board -> (PriorityQueueItem(board.getPriority(moves), board)) }
        return res.sortedBy { it.priority }
    }
}