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

class SpawnSystem(engine: Engine) : IntervalSystem(0.2f) {
    init {
        val physics = engine.getSystem(PhysicsSystem::class.java)

        spawnBall(engine, physics)
        spawnWalls(engine, physics)
    }

    lateinit var ball: Ball

    private fun spawnBall(engine: Engine, physics: PhysicsSystem) {
        ball = Ball(
            physics.world,
            chamberCenter
        )
        engine.addEntity(ball)
    }

    private fun spawnWalls(engine: Engine, physics: PhysicsSystem) {
        val wallThickness = 1f

        // Left wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(chamberCenter.x - CHAMBER_WIDTH / 2 - wallThickness / 2, chamberCenter.y),  // Position
                Vector2(wallThickness, CHAMBER_HEIGHT)  // Size
            )
        )
        // Right wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(chamberCenter.x + CHAMBER_WIDTH / 2 + wallThickness / 2, chamberCenter.y),  // Position
                Vector2(wallThickness, CHAMBER_HEIGHT)  // Size
            )
        )
        // Bottom wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(chamberCenter.x, chamberCenter.y - CHAMBER_HEIGHT / 2 - wallThickness / 2),  // Position
                Vector2(CHAMBER_WIDTH, wallThickness)  // Size
            )
        )
        // Top wall
        engine.addEntity(
            Wall(
                physics.world,
                Vector2(chamberCenter.x, chamberCenter.y + CHAMBER_HEIGHT / 2 + wallThickness / 2),  // Position
                Vector2(CHAMBER_WIDTH, wallThickness)  // Size
            )
        )
    }

    override fun updateInterval() {
        updateEnemySpawn()
    }

    private fun updateEnemySpawn() {
        if (engine.getEntitiesFor(Family.one(EnemyComponent::class.java).get()).size() < 2) {
            val pos = getRandomPositionInChamber()

            engine.addEntity(Enemy(engine.getSystem(PhysicsSystem::class.java).world, pos))
        }
    }


    companion object {
        const val CHAMBER_WIDTH = 160f
        const val CHAMBER_HEIGHT = 100f

        val chamberCenter: Vector2 get() = Vector2(0f, 0f)
        val chamberSize: Vector2 get() = Vector2(CHAMBER_WIDTH, CHAMBER_HEIGHT)

        fun getRandomPositionInChamber() = chamberCenter + chamberSize.apply {
            x *= Random.nextFloat() - 0.5f
            y *= Random.nextFloat() - 0.5f
        }
    }
}
