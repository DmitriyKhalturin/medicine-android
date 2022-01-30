package org.medicine.ui.screen.dayschedule.model

import org.medicine.ui.screen.therapyschedule.model.TherapyScheduleModel

/**
 * Created by Dmitriy Khalturin <dmitry.halturin.86@gmail.com>
 * for MedicalTherapy on 30.01.2022 3:33.
 */
sealed class DayScheduleViewState {
  object Initial : DayScheduleViewState()
  data class TherapySchedule(val therapyId: Long, val therapy: TherapyScheduleModel) : DayScheduleViewState()
}
