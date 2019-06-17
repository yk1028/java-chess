package chess;

import java.util.Objects;

public class Score {
	private final int ROUNDING_CRITERION = 10;
	private final double score;

	public Score(final double score) {
		this.score = round(score);
	}

	private double round(double score) {
		return Math.round(score * ROUNDING_CRITERION) / (double) ROUNDING_CRITERION;
	}

	public Result compare(final Score score) {
		if (this.score > score.score) {
			return Result.WIN;
		}
		if (this.score < score.score) {
			return Result.LOSE;
		}
		return Result.DRAW;
	}

	public Score add(Score score) {
		return new Score(this.score + score.score);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof Score)) {
			return false;
		}
		final Score score1 = (Score) o;

		return Double.compare(score1.score, score) == 0;
	}

	@Override
	public int hashCode() {
		return Objects.hash(score);
	}
}
