package GameEngine.Records;

import GameEngine.EngineModules.EngineTools.MathTools;

import java.awt.Point;

/**
 * The {@code FixResult} record encapsulates the result of the
 * {@link MathTools#fixToLine(Point, Point, Point)
 * fixToLine(Point, Point, Point)}
 * method.
 * <p>
 * It contains the calculated point constrained to a line segment and a
 * progress value representing the relative position of that point between
 * the segment's start and end points.
 * </p>
 *
 * <p>
 * The progress value is a decimal in the range {@code [0.0, 1.0]}, where:
 * <ul>
 * <li>{@code 0.0} corresponds to the start point</li>
 * <li>{@code 1.0} corresponds to the end point</li>
 * </ul>
 * </p>
 */
public record FixResult(Point point, double progress) {
}
