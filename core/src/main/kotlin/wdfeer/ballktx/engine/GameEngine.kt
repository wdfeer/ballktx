package wdfeer.ballktx.engine

import com.badlogic.ashley.core.Engine
import wdfeer.ballktx.system.*

class GameEngine : Engine() {
    fun initialize() {
        addSystem(PhysicsSystem())
        addSystem(RoomSystem(this))
        addSystem(BallSystem(this)) // FIXME: system initialization stopping on BallSystem

        addSystem(DebugRenderSystem())
        addSystem(RenderSystem())
        addSystem(InputSystem())

        addSystem(EnemySystem())
        addSystem(EnemySpawnSystem())
    }
}
