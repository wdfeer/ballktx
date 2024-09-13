package wdfeer.ballktx.engine

import com.badlogic.ashley.core.Engine
import ktx.ashley.add
import wdfeer.ballktx.system.*

class GameEngine : Engine() {
    fun initialize() {
        add {
            addSystem(PhysicsSystem())
            addSystem(DebugRenderSystem())
            addSystem(RenderSystem())
            addSystem(InputSystem())
            addSystem(SpawnSystem())
            addSystem(RoomSystem(this))
            addSystem(EnemySystem())
        }
    }
}
