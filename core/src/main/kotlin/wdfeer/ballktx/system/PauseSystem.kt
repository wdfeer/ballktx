package wdfeer.ballktx.system

import com.badlogic.ashley.core.EntitySystem
import wdfeer.ballktx.system.parent.PauseImmune

class PauseSystem : EntitySystem(), PauseImmune {
    init {
        priority = Int.MIN_VALUE
    }

    var paused = false
    fun toggle() { paused = !paused }

    override fun update(deltaTime: Float) {
        engine.systems.filter { it !is PauseImmune }.forEach { it.setProcessing(!paused) }
    }
}
