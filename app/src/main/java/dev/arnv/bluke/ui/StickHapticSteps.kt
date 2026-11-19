package dev.arnv.bluke.ui

internal const val GAMEPAD_STICK_MOVE_HAPTICS_PREFERENCE = "gamepad_stick_move_haptics_level"
internal const val DEFAULT_STICK_MOVE_HAPTICS_LEVEL = 2
internal val stickMoveHapticLabels = listOf("关闭", "很轻", "轻柔", "适中", "较强")

internal class StickHapticSteps {
    private var step = 0

    fun update(distance: Float): Int {
        val radius = distance.coerceIn(0f, 1f)
        val previous = step
        // Separate entry and exit thresholds keep small finger tremors from buzzing repeatedly.
        while (step < 4 && radius >= (step + 1) * 0.2f + 0.03f) step++
        while (step > 0 && radius < step * 0.2f - 0.03f) step--
        return if (step != previous) step else 0
    }
}

private val stickHapticScales = listOf(0.25f, 0.4f, 0.65f, 1f)

internal fun stickHapticScale(level: Int, step: Int): Float {
    if (level <= 0 || step <= 0) return 0f
    return stickHapticScales[level.coerceAtMost(4) - 1] *
        stickHapticScales[step.coerceAtMost(4) - 1]
}
