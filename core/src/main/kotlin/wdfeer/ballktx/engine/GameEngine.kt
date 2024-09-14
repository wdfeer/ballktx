package wdfeer.ballktx.engine

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.utils.Disposable
import ktx.assets.disposeSafely
import wdfeer.ballktx.component.BodyComponent.Companion.body
import wdfeer.ballktx.system.*
import wdfeer.ballktx.system.PhysicsSystem.Companion.physics

class GameEngine : Engine(), Disposable {
    fun initialize() {
        addSystem(PhysicsSystem())
        addSystem(RoomSystem(this))
        addSystem(BallSystem(this))

        addSystem(RenderSystem())
        addSystem(DebugRenderSystem())

        addSystem(InputSystem())

        addSystem(EnemyLogicSystem())
        addSystem(EnemySpawnSystem())
    }

    override fun removeEntity(entity: Entity?) {
        entity?.body?.let { physics.bodiesToDelete.add(it) }
        super.removeEntity(entity)
    }

    override fun dispose() = systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
}
