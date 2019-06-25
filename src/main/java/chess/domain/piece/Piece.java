package chess.domain.piece;

import java.util.List;

import chess.PieceImage;
import chess.domain.*;
import chess.exception.NotFoundPathException;
import com.sun.javafx.iio.ImageStorage;

public abstract class Piece {
	private final Player player;
	private final Type type;
	private final List<MovementInfo> movementInfos;

	private Score score;
	protected Position position;

	public Piece(Player player, Type type, List<MovementInfo> movementInfos, Position position, Score score) {
		this.player = player;
		this.type = type;
		this.movementInfos = movementInfos;
		this.position = position;
		this.score = score;
	}

	public Position getPosition() {
		return position;
	}

	public PieceImage getPieceImage() {
		return PieceImage.getPieceImage(this.player, this.type);
	}

	public Path getMovablePath(Position end) {
		return getValidPath(end, movementInfos);
	}

	public abstract Path getAttackablePath(Position end);

	Path getValidPath(Position end, List<MovementInfo> movementInfos) {
		for (MovementInfo movementInfo : movementInfos) {
			Path path = getPath(movementInfo, end);
			if (path.contains(end)) {
				path.removeEndPosition();
				return path;
			}
		}
		throw new NotFoundPathException();
	}

	private Path getPath(MovementInfo movementInfo, Position end) {
		Path path = new Path();
		Direction direction = movementInfo.getDirection();
		Position currentPosition = position;
		int validDistance = getValidDistance(movementInfo, direction, end);

		for (int i = 0; i < validDistance; i++) {
			currentPosition = currentPosition.move(direction);
			path.add(currentPosition);
		}
		return path;
	}

	private int getValidDistance(MovementInfo movementInfo, Direction direction, Position end) {
		int distance = movementInfo.getMaxDistance();
		if (distance > position.getMaxDistance(direction, end)) {
			distance = position.getMaxDistance(direction, end);
		}
		return distance;
	}

	public abstract void changePosition(Position position);

	protected void changeMovementInfo(MovementInfo movementInfo) {
		this.movementInfos.clear();
		this.movementInfos.add(movementInfo);
	}

	public boolean isSamePosition(Position position) {
		return this.position.equals(position);
	}

	public boolean isSamePosition(Piece piece) {
		return this.position.equals(piece.position);
	}

	public boolean isMine(Player player) {
		return this.player.equals(player);
	}

	public Score getScore() {
		return score;
	}

	public abstract boolean isPawn();

	public boolean isSameCoordinateX(int x) {
		return position.isSameCoordinateX(x);
	}
}
