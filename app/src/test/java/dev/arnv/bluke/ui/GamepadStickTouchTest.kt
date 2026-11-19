package dev.arnv.bluke.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class GamepadStickTouchTest {
    @get:Rule val compose = createComposeRule()

    @Test
    fun eachStickSignalsOnTouchDownButNotOnMovementOrRelease() {
        val touches = mutableListOf<String>()
        val positions = mutableMapOf<String, Offset>()
        compose.setContent {
            Row {
                for (label in listOf("L", "R")) {
                    GamepadAnalogStick(
                        label = label,
                        isClicked = { false },
                        onMove = { x, y -> positions[label] = Offset(x, y) },
                        onTouchStart = { touches += label },
                        onStickClick = {},
                    )
                }
            }
        }

        for (label in listOf("L", "R")) {
            val stick = compose.onNodeWithTag("gamepad_stick_$label")
            stick.performTouchInput { down(center) }
            compose.runOnIdle { assertEquals(1, touches.count { it == label }) }
            stick.performTouchInput {
                moveTo(center + Offset(20f, 0f))
                moveTo(center + Offset(25f, 10f))
                up()
            }
            compose.runOnIdle {
                assertEquals(1, touches.count { it == label })
                assertEquals(Offset.Zero, positions[label])
            }
            stick.performTouchInput {
                down(center)
                up()
            }
            compose.runOnIdle { assertEquals(2, touches.count { it == label }) }
        }
    }

    @Test
    fun touchOutsideStickDoesNotSignal() {
        var touches = 0
        compose.setContent {
            Box(Modifier.size(240.dp).testTag("surface")) {
                GamepadAnalogStick(
                    label = "L",
                    isClicked = { false },
                    onMove = { _, _ -> },
                    onTouchStart = { touches++ },
                    onStickClick = {},
                )
            }
        }
        compose.onNodeWithTag("surface").performTouchInput {
            down(bottomRight - Offset(10f, 10f))
            up()
        }
        compose.runOnIdle { assertEquals(0, touches) }
    }

    @Test
    fun movementSignalsOnlyBandCrossingsAndNeverSignalsOnRelease() {
        val steps = mutableListOf<Int>()
        compose.setContent {
            GamepadAnalogStick(
                label = "L", isClicked = { false }, onMove = { _, _ -> },
                onTouchStart = {}, onStickClick = {}, onHapticStep = { steps += it },
            )
        }
        val stick = compose.onNodeWithTag("gamepad_stick_L")
        val radius = stick.fetchSemanticsNode().size.width * 32f / 90f
        stick.performTouchInput {
            down(center)
            for (distance in listOf(0.3f, 0.32f, 0.35f, 0.5f, 0.51f, 0.7f, 0.9f)) {
                moveTo(center + Offset(radius * distance, 0f))
            }
            up()
        }
        compose.runOnIdle { assertEquals(listOf(1, 2, 3, 4), steps) }
    }

    @Test
    fun offCenterTouchDoesNotDoubleBuzzAndNextTouchStartsFresh() {
        val steps = mutableListOf<Int>()
        compose.setContent {
            GamepadAnalogStick(
                label = "R", isClicked = { false }, onMove = { _, _ -> },
                onTouchStart = {}, onStickClick = {}, onHapticStep = { steps += it },
            )
        }
        val stick = compose.onNodeWithTag("gamepad_stick_R")
        val radius = stick.fetchSemanticsNode().size.width * 32f / 90f
        stick.performTouchInput {
            down(center + Offset(radius * 0.9f, 0f))
            moveTo(center + Offset(radius * 0.95f, 0f))
            up()
        }
        compose.runOnIdle { assertEquals(emptyList<Int>(), steps) }
        stick.performTouchInput {
            down(center)
            moveTo(center + Offset(radius * 0.3f, 0f))
            up()
        }
        compose.runOnIdle { assertEquals(listOf(1), steps) }
    }
}
