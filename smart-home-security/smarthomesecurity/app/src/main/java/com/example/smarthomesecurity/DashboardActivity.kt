package com.example.smarthomesecurity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthomesecurity.databinding.ActivityDashboardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var alertAdapter: AlertAdapter
    private val alerts = mutableListOf<Alert>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        alertAdapter = AlertAdapter(alerts)
        binding.alertRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@DashboardActivity)
            adapter = alertAdapter
        }

        loadAlerts()
        loadSystemStatus()

        binding.armButton.setOnClickListener {
            val newStatus = if (binding.armButton.text == "Arm System") "armed" else "disarmed"
            database.child("system").child("status").setValue(newStatus)
        }

        binding.helpButton.setOnClickListener {
            startActivity(Intent(this, HelpActivity::class.java))
        }

        binding.logoutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun loadAlerts() {
        database.child("alerts").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                alerts.clear()
                for (data in snapshot.children) {
                    val alert = data.getValue(Alert::class.java)
                    alert?.let { alerts.add(it) }
                }
                alertAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DashboardActivity, "Failed to load alerts", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun loadSystemStatus() {
        database.child("system").child("status").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status = snapshot.getValue(String::class.java)
                binding.armButton.text = if (status == "armed") "Disarm System" else "Arm System"
                binding.armButton.setBackgroundColor(
                    if (status == "armed") ContextCompat.getColor(this@DashboardActivity, R.color.red)
                    else ContextCompat.getColor(this@DashboardActivity, R.color.green)
                )
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DashboardActivity, "Failed to load status", Toast.LENGTH_SHORT).show()
            }
        })
    }
}