package org.medicine.ui.screen.dealform

import dagger.hilt.android.lifecycle.HiltViewModel
import org.medicine.navigation.Destination
import org.medicine.navigation.viewmodel.NavigationViewModel
import javax.inject.Inject

/**
 * Created by Dmitriy Khalturin <dmitry.halturin.86@gmail.com>
 * for Medicine on 11.11.2021 0:40.
 */
@HiltViewModel
class DealFormViewModel @Inject constructor() : NavigationViewModel<Destination.DealForm>()
