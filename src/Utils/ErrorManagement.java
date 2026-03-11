/**
 * Project: Simple_Game_Engine
 *
 * Author: Galactica
 *
 * Licensed under the GPL 3.0 License.
 * See LICENSE file in the project root for full license information.

 Coppyright © 2026 Galactica
 */

package Utils;

public class ErrorManagement {

    public static void reportError(Exception exception, String message) {
        System.err.println('\n' + message + ": " + exception.getMessage() + '\n');
        exception.printStackTrace();
    }

}
