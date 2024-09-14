package wdfeer.ballktx.engine

import com.badlogic.ashley.core.Engine
import wdfeer.ballktx.system.*

class GameEngine : Engine() {
    fun initialize() {
        addSystem(PhysicsSystem())
        addSystem(DebugRenderSystem())
        addSystem(BallSystem(this)) // FIXME: system initialization stopping on BallSystem
        addSystem(RenderSystem())
        addSystem(InputSystem())
        addSystem(RoomSystem(this))
        addSystem(EnemySystem())
        addSystem(EnemySpawnSystem())
    }
}
