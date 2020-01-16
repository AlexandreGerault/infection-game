package io.github.alexandregerault.infectiongame;

public class Main {

    public static void main(String[] args) {

        if (args.length < 6) {
            System.out.println("Not enough arguments!");
            System.exit(1);
        }
    }


    /**
     * A basic implementation of the minmax algorithm without alpha-beta pruning
     *
     * @param _node   The node we're evaluating
     * @param _depth  The reasoning depth
     * @param _player The active player
     * @return evaluation (recursive function)
     */
    public float minmax(GameNode _node, int _depth, Colors _player) {
        if (_depth == 0 || _node.board().gameOver(_player)) {
            return _node.board().evaluate(_player);
        }

        if (_player.equals(Colors.WHITE)) {
            float maxEvaluation = Float.NEGATIVE_INFINITY;

            for (Move move : _node.board().allMoves(_player)) {
                float evaluation = minmax(_node.newState(move), _depth - 1, Colors.BLACK);

                maxEvaluation = Math.max(maxEvaluation, evaluation);
            }

            return maxEvaluation;
        } else {
            float minEvaluation = Float.POSITIVE_INFINITY;

            for (Move move : _node.board().allMoves(_player)) {
                float evaluation = minmax(_node.newState(move), _depth - 1, Colors.WHITE);

                minEvaluation = Math.min(minEvaluation, evaluation);

            }

            return minEvaluation;
        }
    }

    /**
     * A basic implementation of the minmax algorithm with alpha-beta pruning
     *
     * @param _node   The node we're evaluating
     * @param _depth  The reasoning depth
     * @param _player The active player
     * @param _alpha  Alpha from pruning algorithm
     * @param _beta   Beta from pruning algorithm
     * @return evaluation
     */
    public float minmax(GameNode _node, int _depth, Colors _player, float _alpha, float _beta) {
        if (_depth == 0 || _node.board().gameOver(_player)) {
            return _node.board().evaluate(_player);
        }

        if (_player.equals(Colors.WHITE)) {
            float maxEvaluation = Float.NEGATIVE_INFINITY;
            float alpha = _alpha;

            for (Move move : _node.board().allMoves(_player)) {
                float evaluation = minmax(_node.newState(move), _depth - 1, Colors.BLACK, _alpha, _beta);

                maxEvaluation = Math.max(maxEvaluation, evaluation);
                alpha = Math.max(_alpha, evaluation);

                if (_beta <= alpha) {
                    break;
                }
            }

            return maxEvaluation;
        } else {
            float minEvaluation = Float.POSITIVE_INFINITY;
            float beta = _beta;

            for (Move move : _node.board().allMoves(_player)) {
                float evaluation = minmax(_node.newState(move), _depth - 1, Colors.WHITE, _alpha, _beta);

                minEvaluation = Math.min(minEvaluation, evaluation);

                beta = Math.min(_beta, evaluation);

                if (beta <= _alpha) {
                    break;
                }
            }

            return minEvaluation;
        }
    }
}
