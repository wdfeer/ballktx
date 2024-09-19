package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import wdfeer.ballktx.entity.Label

class TutorialSystem(eng: Engine) : EntitySystem() {
    val moveControls = Label("WASD to move", Vector2.Zero).also { eng.addEntity(it) }
}
