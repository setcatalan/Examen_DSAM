
// EditarTascaFragment.kt
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



// Layout XML: fragment_editar_tasca.xml
/*
<?xml version="1.0" encoding="utf-8"?>
<ScrollView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fillViewport="true">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="16dp">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Detall de la Tasca"
            android:textSize="24sp"
            android:textStyle="bold"
            android:layout_marginBottom="24dp" />

        <com.google.android.material.textfield.TextInputLayout
            android:id="@+id/tilNom"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Nom de la tasca"
            android:layout_marginBottom="16dp">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/etNom"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:inputType="text"
                android:maxLines="1" />

        </com.google.android.material.textfield.TextInputLayout>

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Categoria"
            android:textSize="16sp"
            android:textStyle="bold"
            android:layout_marginBottom="8dp" />

        <Spinner
            android:id="@+id/spinnerCategoria"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:minHeight="48dp"
            android:layout_marginBottom="16dp"
            android:background="@android:drawable/btn_dropdown" />

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Estat"
            android:textSize="16sp"
            android:textStyle="bold"
            android:layout_marginBottom="8dp" />

        <Spinner
            android:id="@+id/spinnerEstat"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:minHeight="48dp"
            android:layout_marginBottom="16dp"
            android:background="@android:drawable/btn_dropdown" />

        <com.google.android.material.textfield.TextInputLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:hint="Data"
            android:layout_marginBottom="24dp">

            <com.google.android.material.textfield.TextInputEditText
                android:id="@+id/etData"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:focusable="false"
                android:clickable="true"
                android:inputType="none"
                android:drawableEnd="@android:drawable/ic_menu_my_calendar" />

        </com.google.android.material.textfield.TextInputLayout>

        <com.google.android.material.button.MaterialButton
            android:id="@+id/btnTancar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Tancar" />

    </LinearLayout>

</ScrollView>
*/

// Exemple de com navegar al fragment passant l'ID amb Bundle
/*

// A TasquesAdapter, afegir listener de click al constructor

private val onTascaClick: (Tasca) -> Unit

// Cridar al listener al lloc apropiat
holder.itemView.setOnClickListener { onTascaClick(tasca) }


// Des del TasquesFragment, quan creem l'adapter, caldrà passar aquest bloc de codi:
val bundle = Bundle().apply {
    putInt("tasca_id", tasca.id)
}

val fragment = EditarTascaFragment()
fragment.arguments = bundle

parentFragmentManager.beginTransaction()
    .replace(R.id.fragmentContainer, fragment)
    .addToBackStack(null)
    .commit()
    