package com.ardat.eventmanagement.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.Observer
import com.ardat.eventmanagement.Team.AddTeamActivity
import com.ardat.eventmanagement.R
import com.ardat.eventmanagement.Team.adapter.TeamAdapter
import com.ardat.eventmanagement.Team.model.TeamModel
import com.coding.smkcoding_project_2.viewmodel.TeamFragmentViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_team.*

class TeamFragment : Fragment() {

    lateinit var ref : DatabaseReference
    lateinit var auth: FirebaseAuth
    var dataTeam: MutableList<TeamModel> = ArrayList()
    private val viewModel by viewModels<TeamFragmentViewModel>()
    private var adapter: TeamAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        getData()

        viewModel.init(requireContext())
        viewModel.allTeams.observe(viewLifecycleOwner, Observer { x ->
            x?.let { adapter }
        })

        addTeamItem.setOnClickListener{
            val t = Intent(activity, AddTeamActivity::class.java)
            activity?.startActivity(t)
        }
    }

    private fun init() {
        rvTeamList.layoutManager = LinearLayoutManager(context)
        adapter = TeamAdapter(requireContext(), dataTeam)
        rvTeamList.adapter = adapter
        adapter
    }

    private fun getData() {
        Toast.makeText(context, "Loading...", Toast.LENGTH_LONG).show()
        auth = FirebaseAuth.getInstance()
        ref = FirebaseDatabase.getInstance().reference
        ref.child("Team").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Toast.makeText(context, "Database Error...", Toast.LENGTH_LONG).show()
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                dataTeam = ArrayList()
                for (snapshot in dataSnapshot.children) {
                    val team = snapshot.getValue(TeamModel::class.java)
                    team?.key = (snapshot.key!!.toString())
                    init()
                    dataTeam.add(team!!)
                }
                viewModel.insertAll(dataTeam)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        this.clearFindViewByIdCache()
    }
}