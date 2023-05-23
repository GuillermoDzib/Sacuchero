package com.bedu.sacuchero.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bedu.sacuchero.data.room.UsersViewModel
import com.bedu.sacuchero.databinding.FragmentGalleryBinding
import kotlinx.coroutines.launch

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textGallery
        galleryViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        val intent = activity?.intent
        if (intent != null && intent.hasExtra("email")) {
            val email = intent.getStringExtra("email")
            val usersViewModel = ViewModelProvider(this).get(UsersViewModel::class.java)
            // Realiza las operaciones necesarias con el valor del email recuperado
            lifecycleScope.launch {
                var foodItem = email?.let { usersViewModel.getKartByEmail(it) }
                textView.text = foodItem
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Pedido Realizado")
            builder.setMessage("Tiempo estimado de entrega: 30min")
            builder.setPositiveButton("Aceptar") { dialog, _ ->
                // Acción a realizar cuando se presiona el botón Aceptar
                dialog.dismiss()
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}