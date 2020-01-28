package io.github.alexandregerault.infectiongame;

public class Minimax {
	
	/**
	 * A basic implementation of the minmax algorithm with alpha-beta pruning
	 * 
	 * @param initialState_
	 * @param depth_
	 * @param alpha_
	 * @param beta_
	 * @param activePlayer_
	 * @return
	 */
	public static float alphaBeta(GameState initialState_, int depth_, float alpha_, float beta_, Colors activePlayer_) {
		if (depth_ == 0 || initialState_.board().gameOver(initialState_.player().color())) {
            return initialState_.board().evaluate(initialState_.player().color());
        }

        if (initialState_.player().equals(activePlayer_)) {
            float maxEvaluation = Float.NEGATIVE_INFINITY;
            float alpha;

            for (Move move : initialState_.board().allMoves(initialState_.player().color())) {
                float evaluation = alphaBeta(initialState_.newState(move), depth_ - 1, alpha_, beta_, activePlayer_);

                maxEvaluation = maxEvaluation > evaluation ? maxEvaluation : evaluation;
                alpha = alpha_ > maxEvaluation ? maxEvaluation : alpha_;

                if (beta_ <= alpha) {
                    break;
                }
            }

            return maxEvaluation;
        } else {
            float minEvaluation = Float.POSITIVE_INFINITY;
            float beta;

            for (Move move : initialState_.board().allMoves(initialState_.player().color())) {
                float evaluation = alphaBeta(initialState_.newState(move), depth_ - 1, alpha_, beta_, activePlayer_);

                minEvaluation = minEvaluation < evaluation ? minEvaluation : evaluation;

                beta = beta_ < minEvaluation ? minEvaluation : beta_;

                if (beta <= alpha_) {
                    break;
                }
            }

            return minEvaluation;
        }
	}
	
	/**
     * A basic implementation of the minmax algorithm without alpha-beta pruning
     *
     * @param initialState_   The node we're evaluating
     * @param depth_  The reasoning depth
     * @return evaluation (recursive function)
     */
    public static float minmax(GameState initialState_, int depth_, Colors activePlayer_) {
        if (depth_ == 0 || initialState_.board().gameOver(initialState_.player().color())) {
            return initialState_.board().evaluate(initialState_.player().color());
        }

        if (initialState_.player().equals(activePlayer_)) {
            float maxEvaluation = Float.NEGATIVE_INFINITY;

            for (Move move : initialState_.board().allMoves(initialState_.player().color())) {
                float evaluation = minmax(initialState_.newState(move), depth_ - 1, activePlayer_);

                maxEvaluation = maxEvaluation > evaluation ? maxEvaluation : evaluation;
            }

            return maxEvaluation;
        } else {
            float minEvaluation = Float.POSITIVE_INFINITY;

            for (Move move : initialState_.board().allMoves(initialState_.player().color())) {
                float evaluation = minmax(initialState_.newState(move), depth_ - 1, activePlayer_);

                minEvaluation = minEvaluation < evaluation ? minEvaluation : evaluation;
            }

            return minEvaluation;
        }
    }
}
