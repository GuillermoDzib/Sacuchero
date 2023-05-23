package com.bedu.sacuchero.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bedu.sacuchero.databinding.FragmentHomeBinding
import com.bedu.sacuchero.model.Food
import com.example.agenda.HomeAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.bedu.sacuchero.data.room.UsersViewModel
import com.bedu.sacuchero.ui.gallery.GalleryFragment
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var homeAdapter: HomeAdapter
    private val binding get() = _binding!!
//    private var stringBuilder = StringBuilder()
//    private var total = 0.0

    lateinit var usersViewModel: UsersViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        val intent = activity?.intent
        if (intent != null && intent.hasExtra("email")) {
            val email = intent.getStringExtra("email")
            // Realiza las operaciones necesarias con el valor del email recuperado
            lifecycleScope.launch {
                binding.textViewWelcome.text =  email?.let{"Bienvenido ${usersViewModel.getNameByEmail(it)}"  }
            }
        }



        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        homeAdapter = HomeAdapter { food -> onFoodClicked(food) }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = homeAdapter
        }

        homeViewModel.foodList.observe(viewLifecycleOwner) { foodItemList ->
            val foodList = foodItemList.map { foodItem ->
                Food(
                    foodItem.nam,
                    foodItem.pic,
                    foodItem.description,
                    foodItem.price,
                    foodItem.cant
                )
            }
            homeAdapter.setList(foodList)
            homeAdapter.notifyDataSetChanged()
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onFoodClicked(food: Food) {
        // Aquí puedes agregar la lógica para manejar el clic en un elemento de comida
        // Puedes abrir una nueva actividad o fragmento para mostrar los detalles del alimento seleccionado
        // Por ejemplo:
        /*
        val intent = Intent(requireContext(), FoodDetailsActivity::class.java)
        intent.putExtra("food", food)
        startActivity(intent)
        */
        usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)

        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Agregar al carrito")
        builder.setMessage("¿Deseas agregar al carrito?")

// Botón Aceptar
        builder.setPositiveButton("Aceptar") { dialog, which ->
            // Acción al hacer clic en Aceptar
            // Aquí puedes realizar las operaciones necesarias
            val intent = activity?.intent
            if (intent != null && intent.hasExtra("email")) {
                val email = intent.getStringExtra("email")
                // Realiza las operaciones necesarias con el valor del email recuperado
                lifecycleScope.launch {
                    var kart = email?.let { usersViewModel.getKartByEmail(it) }

                    var pattern = "nam=(\\w+),.*price=(\\d+(\\.\\d+)?)".toRegex()
                    var matchResult = pattern.find(food.toString())

                    if (matchResult != null) {
                        var name = matchResult.groupValues[1]
                        var price = matchResult.groupValues[2].toDouble()

                        var formattedPrice = String.format("%.2f", price)
                        var result = "$name -> $$formattedPrice"

                        kart = "$kart \n$result"
                        //println(result) // Output: "papadzules -> $80.00"
                    }
                        //Toast.makeText(requireContext(), "No se encontró", Toast.LENGTH_SHORT).show()
                    if (email != null) {
                        if (kart != null) {
                            usersViewModel.updateKartByEmail(email, kart)
                        }
                    }
                }
            }

        }

// Botón Cancelar
        builder.setNegativeButton("Cancelar") { dialog, which ->
            // Acción al hacer clic en Cancelar
            // Aquí puedes realizar las operaciones necesarias
            //Toast.makeText(requireContext(), "Has cancelado", Toast.LENGTH_SHORT).show()
        }

        val dialog = builder.create()
        dialog.show()


    }
}
