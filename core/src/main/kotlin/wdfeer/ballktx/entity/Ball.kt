package wdfeer.ballktx.entity

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.CircleShape
import com.badlogic.gdx.physics.box2d.FixtureDef
import com.badlogic.gdx.physics.box2d.World
import ktx.assets.disposeSafely
import ktx.assets.toInternalFile
import wdfeer.ballktx.component.BodyComponent
import wdfeer.ballktx.component.BodyComponent.Companion.body
import wdfeer.ballktx.component.DrawComponent

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
    }

    companion object {
        const val RADIUS = 1.5f
    }
}
