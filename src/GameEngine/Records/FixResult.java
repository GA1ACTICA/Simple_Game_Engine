/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package GameEngine.Records;

import java.awt.Point;
import java.util.Objects;

import Utils.MathTools;

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
 * 
 * @throws NullPointerException if {@code point} or {@code progress} is
 *                              {@code null}
 */
public record FixResult(Point point, double progress) {
    public FixResult {
        Objects.requireNonNull(point, "point must not be null");
        Objects.requireNonNull(progress, "progress must not be null");
    }
}
