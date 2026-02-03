package com.example.examen_dsam

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

class EditarTascaFragment : Fragment() {

    private lateinit var tilNom: TextInputLayout
    private lateinit var etNom: TextInputEditText
    private lateinit var spinnerCategoria: Spinner
    private lateinit var spinnerEstat: Spinner
    private lateinit var etData: TextInputEditText
    private lateinit var btnTancar: MaterialButton

    private var tascaId: Int = -1
    private var tascaActual: Tasca? = null
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            tascaId = it.getInt("tasca_id", -1)
            if (tascaId != -1) {
                tascaActual = TasquesRepository.tasques.find { tasca -> tasca.id == tascaId }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_editar_tasca, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        setupSpinners()
        setupDatePicker()
        setupButtons()

        // Carregar dades de la tasca
        tascaActual?.let { carregarDadesTasca(it) }
    }

    private fun initViews(view: View) {
        tilNom = view.findViewById(R.id.tilNom)
        etNom = view.findViewById(R.id.etNom)
        spinnerCategoria = view.findViewById(R.id.spinnerCategoria)
        spinnerEstat = view.findViewById(R.id.spinnerEstat)
        etData = view.findViewById(R.id.etData)
        btnTancar = view.findViewById(R.id.btnTancar)
    }

    private fun setupSpinners() {
        // Spinner de categories
        val categories = listOf("Feina", "Família", "Personal")
        val adapterCategoria = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            categories
        )
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapterCategoria

        // Spinner d'estats
        val estats = listOf("No començada", "En curs", "Finalitzada")
        val adapterEstat = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            estats
        )
        adapterEstat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerEstat.adapter = adapterEstat
    }

    private fun setupDatePicker() {
        etData.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    etData.setText(dateFormat.format(calendar.time))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }
    }

    private fun setupButtons() {
        btnTancar.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun carregarDadesTasca(tasca: Tasca) {
        etNom.setText(tasca.nom)
        etData.setText(tasca.data)

        // Seleccionar categoria
        val posicioCategoria = when (tasca.categoria) {
            is Categoria.Feina -> 0
            is Categoria.Familia -> 1
            is Categoria.Personal -> 2
        }
        spinnerCategoria.setSelection(posicioCategoria)

        // Seleccionar estat
        val posicioEstat = when (tasca.estat) {
            is Estat.NoComencada -> 0
            is Estat.EnCurs -> 1
            is Estat.Finalitzada -> 2
        }
        spinnerEstat.setSelection(posicioEstat)
    }
}