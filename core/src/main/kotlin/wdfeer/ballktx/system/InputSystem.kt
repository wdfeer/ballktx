package wdfeer.ballktx.system

import com.badlogic.ashley.core.EntitySystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.math.Vector2
import ktx.math.times
import wdfeer.ballktx.component.BodyComponent

class InputSystem : EntitySystem() {
    init {
        priority = -100 // Highest priority
    }

    override fun update(deltaTime: Float) {
        Gdx.input.handle()
    }

    private fun Input.handle() {
        val camera = engine.getSystem(RenderSystem::class.java).camera

        if (isKeyPressed(Keys.EQUALS))
            camera.zoom += 0.003f
        if (isKeyPressed(Keys.MINUS))
            camera.zoom -= 0.003f

        val forceMagnitude: Float = if (isKeyPressed(Keys.SHIFT_LEFT)) 15f else 5f
        val forceVector = Vector2(0f, 0f)

        if (isKeyPressed(Keys.W))
            forceVector.y += forceMagnitude
        if (isKeyPressed(Keys.A))
            forceVector.x -= forceMagnitude
        if (isKeyPressed(Keys.S))
            forceVector.y -= forceMagnitude
        if (isKeyPressed(Keys.D))
            forceVector.x += forceMagnitude

        val ball = engine.entities[0]!!
        val ballBody = ball.getComponent(BodyComponent::class.java).body

        if (isKeyPressed(Keys.CONTROL_LEFT)) {
            ballBody.linearVelocity *= 0.99f
            ballBody.angularVelocity *= 0.99f
        }

        ballBody.applyForceToCenter(forceVector, true)
    }
}
