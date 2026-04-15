/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the MIT License.
 * See LICENSE file in the project root for full license information.
 *
 * Copyright © 2026 Galactica
 */

package Utils;

public class ErrorManagement {

    /**
     * @param exception
     * @param message
     */
    public static void reportError(Exception exception, String message) {
        System.err.println('\n' + message + ": " + exception.getMessage() + '\n');
        exception.printStackTrace();
    }

}
