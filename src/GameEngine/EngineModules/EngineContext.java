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

package GameEngine.EngineModules;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Interfaces.Clickable;
import GameEngine.Interfaces.Updatable;
import GameEngine.Interfaces.Drawables.*;

public class EngineContext {
     private final List<Drawable> worldDrawables = new ArrayList<>();
     private final List<Drawable> uiDrawables = new ArrayList<>();

     private final List<CursorDrawable> cursorDrawables = new ArrayList<>();

     private final List<Updatable> updatables = new ArrayList<>();

     private final List<Clickable> clickables = new ArrayList<>();

     private List<List<?>> allLists = List.of(
               worldDrawables,
               uiDrawables,
               cursorDrawables,
               updatables,
               clickables);

     public List<List<?>> getAllLists() {
          return allLists;
     }

     /**
      * Returns the internal list of objects registered with the game engine that
      * implement {@link Drawable}.
      *
      * @return the mutable list of drawable objects
      */
     public List<Drawable> getWorldDrawables() {
          return worldDrawables;
     }

     /**
      * Returns the internal list of objects registered with the game engine that
      * implement {@link UIDrawable}.
      *
      * @return the mutable list of drawable objects
      */
     public List<Drawable> getUiDrawables() {
          return uiDrawables;
     }

     /**
      * Returns the internal list of objects registered with the game engine that
      * implement {@link CursorDrawable}.
      *
      * @return the mutable list of drawable objects
      */
     public List<CursorDrawable> getCursorDrawables() {
          return cursorDrawables;
     }

     /**
      * Returns the internal list of objects registered with the game engine that
      * implement {@link Updatable}.
      *
      * @return the mutable list of updatable objects
      */
     public List<Updatable> getUpdatables() {
          return updatables;
     }

     /**
      * Returns the internal list of objects registered with the game engine that
      * implement {@link Clickable}.
      *
      * @return the mutable list of clickable objects
      */
     public List<Clickable> getClickables() {
          return clickables;
     }

}
