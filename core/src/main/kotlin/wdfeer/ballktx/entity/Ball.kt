package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Input.Keys
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import ktx.math.times
import wdfeer.ballktx.component.BodyComponent
import wdfeer.ballktx.component.BodyComponent.Companion.body
import wdfeer.ballktx.component.DrawComponent
import wdfeer.ballktx.component.InputComponent

class Ball(world: World, pos: Vector2) : Entity() {
    private val image = Texture("ball.png".toInternalFile(), true).apply { setFilter(
        Texture.TextureFilter.Linear,
        Texture.TextureFilter.Linear
    ) }

    init {
        val circle = CircleShape().apply { radius = RADIUS }

        add(BodyComponent(world, pos, BodyDef.BodyType.DynamicBody) {
            createFixture(FixtureDef().apply {
                this.shape = circle
                density = 0.5f
                friction = 0.1f
                restitution = 1f
            })

            userData = this@Ball
        })

        circle.disposeSafely()

        add(DrawComponent {
            body!!.position.run { it.draw(image, x - RADIUS, y - RADIUS, RADIUS * 2f, RADIUS * 2f) }
        })

        add(InputComponent {
            val forceMagnitude: Float = if (isKeyPressed(Keys.SHIFT_LEFT)) 25f else 10f
            val forceVector = Vector2(0f, 0f)

            if (isKeyPressed(Keys.W))
                forceVector.y += forceMagnitude
            if (isKeyPressed(Keys.A))
                forceVector.x -= forceMagnitude
            if (isKeyPressed(Keys.S))
                forceVector.y -= forceMagnitude
            if (isKeyPressed(Keys.D))
                forceVector.x += forceMagnitude

            val ballBody = this@Ball.body!!

            if (isKeyPressed(Keys.CONTROL_LEFT)) {
                ballBody.linearVelocity *= 0.99f
                ballBody.angularVelocity *= 0.99f
            }

            ballBody.applyForceToCenter(forceVector, true)
        })
    }

    companion object {
        const val RADIUS = 1.5f
    }
}
