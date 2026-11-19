package dev.arnv.bluke

import android.content.Context
import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performSemanticsAction
import androidx.compose.ui.test.assertRangeInfoEquals
import androidx.compose.ui.semantics.SemanticsActions
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import dev.arnv.bluke.ui.GAMEPAD_STICK_TOUCH_HAPTICS_PREFERENCE
import dev.arnv.bluke.ui.GAMEPAD_STICK_MOVE_HAPTICS_PREFERENCE
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
class ControllerSettingsHapticsTest {
    @get:Rule val compose = createAndroidComposeRule<ControllerSettingsActivity>()

    @Test
    fun switchDefaultsOnAndRemembersItsValueAfterRecreation() {
        val toggle = compose.onNodeWithTag("stick_touch_haptics_switch")
        toggle.performScrollTo().assertIsOn().performClick().assertIsOff()
        compose.runOnIdle {
            val preferences = compose.activity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            assertFalse(preferences.getBoolean(GAMEPAD_STICK_TOUCH_HAPTICS_PREFERENCE, true))
        }
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("stick_touch_haptics_switch").performScrollTo().assertIsOff()
            .performClick().assertIsOn()
    }

    @Test
    fun movementSliderCanDisableFeedbackAndRemembersTheSelectedStrength() {
        val slider = compose.onNodeWithTag("stick_move_haptics_slider")
        slider.performScrollTo().assertRangeInfoEquals(ProgressBarRangeInfo(2f, 0f..4f, 3))
        slider.performSemanticsAction(SemanticsActions.SetProgress) { it(0f) }
        compose.runOnIdle {
            val preferences = compose.activity.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            assertEquals(0, preferences.getInt(GAMEPAD_STICK_MOVE_HAPTICS_PREFERENCE, -1))
        }
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("stick_move_haptics_slider").performScrollTo()
            .assertRangeInfoEquals(ProgressBarRangeInfo(0f, 0f..4f, 3))
            .performSemanticsAction(SemanticsActions.SetProgress) { it(4f) }
        compose.activityRule.scenario.recreate()
        compose.onNodeWithTag("stick_move_haptics_slider").performScrollTo()
            .assertRangeInfoEquals(ProgressBarRangeInfo(4f, 0f..4f, 3))
    }
}
