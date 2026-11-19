package dev.arnv.bluke.ui

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class StickHapticStepsTest {
    @Test
    fun crossingBandsSignalsTheNewStrengthInBothDirections() {
        val steps = StickHapticSteps()
        assertEquals(0, steps.update(0f))
        for ((radius, expected) in listOf(0.24f to 1, 0.44f to 2, 0.64f to 3, 0.84f to 4)) {
            assertEquals(expected, steps.update(radius))
            assertEquals(0, steps.update(radius + 0.01f))
        }
        assertEquals(3, steps.update(0.76f))
        assertEquals(2, steps.update(0.56f))
        assertEquals(1, steps.update(0.36f))
        assertEquals(0, steps.update(0f))
    }

    @Test
    fun smallBoundaryTremorsDoNotRepeatFeedback() {
        val steps = StickHapticSteps()
        assertEquals(1, steps.update(0.3f))
        for (radius in listOf(0.38f, 0.41f, 0.39f, 0.42f)) assertEquals(0, steps.update(radius))
        assertEquals(2, steps.update(0.44f))
        for (radius in listOf(0.41f, 0.39f, 0.38f, 0.42f)) assertEquals(0, steps.update(radius))
    }

    @Test
    fun fastPushSignalsOnlyItsFinalBandAndSeparateSticksHaveSeparateState() {
        val left = StickHapticSteps()
        val right = StickHapticSteps()
        assertEquals(4, left.update(1f))
        assertEquals(0, left.update(1.5f))
        assertEquals(1, right.update(0.3f))
        assertEquals(0, left.update(0.9f))
    }

    @Test
    fun strengthIncreasesWithDistanceAndSliderLevelAndZeroAlwaysDisablesIt() {
        for (step in 0..4) assertEquals(0f, stickHapticScale(0, step), 0f)
        for (level in 1..4) {
            var previous = 0f
            for (step in 1..4) {
                val scale = stickHapticScale(level, step)
                assertTrue(scale > previous && scale <= 1f)
                previous = scale
            }
            assertEquals(0f, stickHapticScale(level, 0), 0f)
        }
        for (step in 1..4) {
            for (level in 2..4) assertTrue(stickHapticScale(level, step) > stickHapticScale(level - 1, step))
        }
    }
}
