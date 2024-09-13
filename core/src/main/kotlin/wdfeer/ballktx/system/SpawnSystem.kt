package wdfeer.ballktx.system

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IntervalSystem
import wdfeer.ballktx.component.EnemyComponent
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.entity.Enemy
import wdfeer.ballktx.system.RoomSystem.Companion.roomCenter

class SpawnSystem(engine: Engine) : IntervalSystem(0.2f) {
    init {
        val physics = engine.getSystem(PhysicsSystem::class.java)

        spawnBall(engine, physics)
    }

    lateinit var ball: Ball

    private fun spawnBall(engine: Engine, physics: PhysicsSystem) {
        ball = Ball(
            physics.world,
            roomCenter
        )
        engine.addEntity(ball)
    }

    override fun updateInterval() {
        updateEnemySpawn()
    }

    private fun updateEnemySpawn() {
        if (engine.getEntitiesFor(Family.one(EnemyComponent::class.java).get()).none()) {
            spawnEnemies()
        }
    }

    private fun spawnEnemies() {
        repeat(ENEMY_COUNT) {
            val pos = engine.getSystem(RoomSystem::class.java).room.getRandomPosition()
            engine.addEntity(Enemy(engine.getSystem(PhysicsSystem::class.java).world, pos))
        }
    }

    companion object {
        const val ENEMY_COUNT = 5
    }
}
