package wdfeer.ballktx.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import wdfeer.ballktx.entity.Ball
import wdfeer.ballktx.entity.Enemy
import kotlin.math.min

class PhysicsSystem : EntitySystem() {
    val world = World(Vector2.Zero, true).apply { setContactListener(object : ContactListener {
        override fun beginContact(contact: Contact) {}
        override fun endContact(contact: Contact) {
            val entities = listOf(contact.fixtureA, contact.fixtureB).map { it.body.userData as? Entity }

            val ball = entities.find { it is Ball } as? Ball ?: return
            val enemy = entities.find { it is Enemy } as? Enemy ?: return

            engine.getSystem(EnemySystem::class.java).onCollideWithBall(enemy, this@PhysicsSystem)
        }
        override fun preSolve(contact: Contact?, oldManifold: Manifold?) {}
        override fun postSolve(contact: Contact?, impulse: ContactImpulse?) {}
    }) }
    var bodiesToDelete: MutableList<Body> = mutableListOf()

    private var accumulator = 0f
    override fun update(deltaTime: Float) {
        // max frame time to avoid spiral of death (on slow devices)
        accumulator += min(deltaTime, 0.25f)

        while (accumulator >= TIME_STEP) {
            world.step(TIME_STEP, VELOCITY_ITERATIONS, POSITION_ITERATIONS)

            bodiesToDelete.forEach { world.destroyBody(it) }
            bodiesToDelete = mutableListOf()

            accumulator -= TIME_STEP
        }
    }

    companion object {
        const val TIME_STEP = 1/60f
        const val VELOCITY_ITERATIONS = 6
        const val POSITION_ITERATIONS = 2
    }
}
