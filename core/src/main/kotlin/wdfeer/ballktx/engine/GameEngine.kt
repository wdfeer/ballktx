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
        initializeStaticSystems()
        initializeRendering()
        initializeInput()
        initializeDynamicSystems()
    }

    private fun initializeStaticSystems() {
        addSystem(PhysicsSystem())
        addSystem(RoomSystem(this))
    }

    private fun initializeRendering() {
        addSystem(RenderSystem())
        addSystem(DebugRenderSystem())
    }
    private fun initializeInput() = addSystem(InputSystem())

    private fun initializeDynamicSystems() {
        addSystem(BallSystem(this))
        addSystem(EnemyLogicSystem())
        addSystem(EnemySpawnSystem())
        addSystem(TutorialSystem(this))
    }


    override fun removeEntity(entity: Entity?) {
        entity?.body?.let { physics.bodiesToDelete.add(it) }
        super.removeEntity(entity)
    }

    override fun dispose() = systems.filterIsInstance<Disposable>().forEach(Disposable::disposeSafely)
}
