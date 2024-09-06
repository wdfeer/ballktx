package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import com.badlogic.gdx.math.Vector2
import ktx.math.plus
import wdfeer.ballktx.component.EnemyComponent
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.entity.Enemy
import wdfeer.ballktx.entity.Wall
import kotlin.random.Random

class SpawnSystem(engine: Engine) : IntervalSystem(0.5f) {
    init {
        val physics = engine.getSystem(PhysicsSystem::class.java)
        val render = engine.getSystem(RenderSystem::class.java)

        spawnBall(engine, physics, render)
        spawnWalls(engine, physics, render)
    }

    private fun spawnBall(engine: Engine, physics: PhysicsSystem, render: RenderSystem) {
        engine.addEntity(Ball(
            physics.world,
            render.camera.position.run { Vector2(x, y) }
        ))
    }

    private fun spawnWalls(engine: Engine, physics: PhysicsSystem, render: RenderSystem) {
        val camera = render.camera
        val camWidth = camera.viewportWidth
        val camHeight = camera.viewportHeight
        val camPosition = camera.position

        val wallThickness = 1f

        // Left wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(camPosition.x - camWidth / 2 - wallThickness / 2, camPosition.y),  // Position
                Vector2(wallThickness, camHeight)  // Size
            )
        )
        // Right wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(camPosition.x + camWidth / 2 + wallThickness / 2, camPosition.y),  // Position
                Vector2(wallThickness, camHeight)  // Size
            )
        )
        // Bottom wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(camPosition.x, camPosition.y - camHeight / 2 - wallThickness / 2),  // Position
                Vector2(camWidth, wallThickness)  // Size
            )
        )
        // Top wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(camPosition.x, camPosition.y + camHeight / 2 + wallThickness / 2),  // Position
                Vector2(camWidth, wallThickness)  // Size
            )
        )
    }

    override fun updateInterval() {
        updateEnemySpawn()
    }

    private fun updateEnemySpawn() {
        if (engine.getEntitiesFor(Family.one(EnemyComponent::class.java).get()).size() < 2) {
            val pos = engine.getSystem(RenderSystem::class.java).camera.position.run {
                Vector2(x, y)
            } + Vector2(Random.nextFloat() * 30f - 15f, Random.nextFloat() * 30f - 15f)
            engine.addEntity(Enemy(engine.getSystem(PhysicsSystem::class.java).world, pos))
        }
    }
}
