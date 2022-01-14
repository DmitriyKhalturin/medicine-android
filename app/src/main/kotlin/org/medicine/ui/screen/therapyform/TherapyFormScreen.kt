package org.medicine.ui.screen.therapyform

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.material.datepicker.MaterialDatePicker
import org.medicine.common.ui.setSystemUiColors
import org.medicine.navigation.Destination
import org.medicine.navigation.navigate
import org.medicine.tools.time.toLocalDate
import org.medicine.tools.time.toLong
import org.medicine.ui.screen.therapyform.composable.TherapyForm
import org.medicine.ui.screen.therapyform.model.TherapyFormIntent
import org.medicine.ui.screen.therapyform.model.TherapyFormModel
import org.medicine.ui.screen.therapyform.model.TherapyFormViewState
import org.medicine.ui.stub.data.stubTherapyForm
import org.medicine.ui.theme.MedicalTherapyTheme
import java.time.LocalDate

/**
 * Created by Dmitriy Khalturin <dmitry.halturin.86@gmail.com>
 * for Medicine on 13.11.2021 6:03.
 */

@Composable
fun TherapyFormScreen(navController: NavController, viewModel: TherapyFormViewModel) {
  val uiState = viewModel.uiState

  rememberSystemUiController()
    .setSystemUiColors(
      MaterialTheme.colors.background,
      MaterialTheme.colors.background
    )

  val activity = LocalContext.current as AppCompatActivity

  TherapyFormView(
    navController,
    uiState,
    { date, callback -> showDatePickerDialog(activity.supportFragmentManager, date, callback) },
    { viewModel.obtainIntent(TherapyFormIntent.FillTherapy(it)) },
    { therapyId, therapyForm -> viewModel.obtainIntent(TherapyFormIntent.CreateOrSaveTherapy(therapyId, therapyForm)) },
    { therapyId -> viewModel.obtainIntent(TherapyFormIntent.DeleteTherapy(therapyId)) }
  )

  LaunchedEffect(key1 = viewModel) {
    viewModel.obtainIntent(TherapyFormIntent.EnterScreen)
  }
}

private fun showDatePickerDialog(fragmentManager: FragmentManager, date: LocalDate, callback: TherapyDateOnChange) {
  val builder = MaterialDatePicker.Builder.datePicker()
  val datePicker = builder.setSelection(date.toLong()).build()

  datePicker.addOnPositiveButtonClickListener {
    callback(it.toLocalDate())
  }
  datePicker.show(fragmentManager, null)
}

private typealias TherapyDateOnChange = (LocalDate) -> Unit

@Composable
private fun TherapyFormView(
  navController: NavController,
  uiState: TherapyFormViewState,
  therapyDateOnChange: (LocalDate, TherapyDateOnChange) -> Unit,
  therapyFormOnChange: (TherapyFormModel) -> Unit,
  createOrSaveTherapy: (Long?, TherapyFormModel) -> Unit,
  deleteTherapy: (Long) -> Unit,
) {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.TopCenter,
  ) {
    when (uiState) {
      is TherapyFormViewState.Initial -> Unit
      is TherapyFormViewState.Therapy -> uiState.run {
        TherapyForm(
          therapyId,
          therapy,
          { therapyFormOnChange(therapy.copy(name = it)) },
          { therapyFormOnChange(therapy.copy(description = it)) },
          { therapyDateOnChange(therapy.startDate) { therapyFormOnChange(therapy.copy(startDate = it)) } },
          { therapyDateOnChange(therapy.endDate) { therapyFormOnChange(therapy.copy(endDate = it)) } },
          { createOrSaveTherapy(therapyId, therapy) },
          { therapyId?.let(deleteTherapy) },
        )
      }
      // TODO: render (saving and deleting) successful UI.
      is TherapyFormViewState.SavingSuccessful ->
        navController.navigate(Destination.TherapySchedule(uiState.therapyId))
      is TherapyFormViewState.DeletingSuccessful ->
        navController.navigate(
          Destination.Overview,
          NavOptions.Builder()
            .setPopUpTo(route = null, inclusive = true)
            .build()
        )
    }
  }
}


@Preview(showBackground = true)
@Composable
fun TherapyFormViewPreview() {
  MedicalTherapyTheme {
    TherapyFormView(
      rememberNavController(),
      // TherapyFormViewState.Initial,
      TherapyFormViewState.Therapy(
        therapyId = null,
        therapy = stubTherapyForm(),
      ),
      // TherapyFormViewState.SavingSuccessful,
      // TherapyFormViewState.DeletingSuccessful,
      { _, _ -> }, {}, { _, _ -> }, {},
    )
  }
}
