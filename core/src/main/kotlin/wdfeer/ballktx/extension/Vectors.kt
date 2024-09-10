package wdfeer.ballktx.extension

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3

fun Vector2.toVector3(): Vector3 = Vector3(x, y, 0f)
