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

import java.util.Objects;

/**
 * The {@code SliderInformation} record is used to encapsulate the values of the
 * {@link #AdvancedRendering.uiRendering.Slider Slider} class. It contains the
 * current value of the slider which is calculated with the formula
 * {@code sliderProgress * sliderMax}
 * 
 * <p>
 * {@code sliderProgress} is the progress between the "start"
 * and "end"
 * points, represented as a decimal value between 0.0 and 1.0
 * <p>
 * 
 * @throws NullPointerException if {@code sliderValue} or {@code sliderProgress}
 *                              is {@code null}
 */
public record SliderInformation(double sliderValue, double sliderProgress) {
    public SliderInformation {
        Objects.requireNonNull(sliderValue, "sliderValue must not be null");
        Objects.requireNonNull(sliderProgress, "sliderProgress must not be null");
    }
}
