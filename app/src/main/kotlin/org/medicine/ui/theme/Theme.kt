package org.medicine.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import org.medicine.R
import org.medicine.scheduler.theme.MedicalTherapyScheduleColors
import org.medicine.scheduler.theme.MedicalTherapyScheduleTheme

private val LightColorPalette
  @Composable get() = lightColors(
    primary = colorResource(id = R.color.mediumRose),
    primaryVariant = colorResource(id = R.color.rose),
    onPrimary = colorResource(id = R.color.haleNavy),

    secondary = colorResource(id = R.color.greenishBlue),
    secondaryVariant = colorResource(id = R.color.grayTurquoise),
    onSecondary = colorResource(id = R.color.seashell),

    background = colorResource(id = R.color.background),
    onBackground = colorResource(id = R.color.haleNavy),

    surface = colorResource(id = R.color.lightGray),
    onSurface = colorResource(id = R.color.darkGray),
  )

private val LightMedicineSchedulePalette
  @Composable get() = MedicalTherapyScheduleColors(
    content = Color(0xFF555B6E),
    today = Color(0xFFFFDCDC),
    onToday = Color(0xE6F13434),
    medicine = Color(0xFFF08C8C),
    onMedicine = Color(0xE6FFF9F9),
    deal = Color(0xFFA0CCCA),
    onDeal = Color(0xE6E2F1F1),
    true,
  )

@Composable
fun MedicalTherapyTheme(
  isDarkTheme: Boolean = isSystemInDarkTheme(),
  leafShapes: LeafShapes = MedicalTherapyTheme.leafShapes,
  content: @Composable () -> Unit
) {
  /** TODO: change colors theme here (Day or Night) */

  MaterialTheme(
    colors = LightColorPalette,
    shapes = Shapes,
  ) {
    CompositionLocalProvider(
      LocalLeafShapes provides leafShapes
    ) {
      MedicalTherapyScheduleTheme(
        medicalTherapyScheduleColors = LightMedicineSchedulePalette
      ) {
        /** Provide theme parts (style, color, etc). */
        content()
      }
    }
  }
}

object MedicalTherapyTheme {

  val leafShapes: LeafShapes
    @Composable
    @ReadOnlyComposable
    get() = LocalLeafShapes.current
}
