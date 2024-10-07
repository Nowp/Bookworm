package fr.atticap.bookworm.ui.gestures

import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.PointerInputScope

suspend fun PointerInputScope.detectPinch(
    triggerDelta: Float = 0.05f,
    onPinchOut: () -> Unit = {},
    onPinchIn: () -> Unit = {}
) {
    awaitEachGesture {
        awaitFirstDown(pass = PointerEventPass.Initial)

        do {
            val event = awaitPointerEvent(pass = PointerEventPass.Initial)

            event.run {
                val size = changes.size
                if (size == 2){
                    changes.forEach { it.consume() }

                    val zoom = calculateZoom()
                    when {
                        zoom - 1f < -triggerDelta -> onPinchOut()
                        zoom - 1f > triggerDelta -> onPinchIn()
                    }
                }
            }
        } while (event.changes.any { it.pressed })
    }
}