/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.
 *
 *Coppyright © 2026 Galactica
 */

package GameEngine.Records;

/**
 * The {@code SliderInfromation} class is used to encapsulate the values of the
 * {@link #AdvancedRendering.uiRendering.Slider Slider} class. It contains the
 * current value of the slider which is calculated with the formula
 * {@code sliderProgress / 100 * sliderMax}
 * 
 * <p>
 * {@code sliderProgress} is the progress between the "start"
 * and "end"
 * points, represented as a decimal value between 0 and 100.
 * <p>
 */
public record SliderInformation(double sliderValue, double sliderProgress) {
}
