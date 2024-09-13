package wdfeer.ballktx.extension

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.physics.box2d.Body
import ktx.ashley.get
import wdfeer.ballktx.component.BodyComponent

val Entity.body: Body? get() = this[BodyComponent.MAPPER]?.body
