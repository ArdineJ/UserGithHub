package com.ardine.githubuser.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ardine.githubuser.adapter.UserListAdapter
import com.ardine.githubuser.data.remote.response.User
import com.ardine.githubuser.databinding.FragmentFollowingBinding
import com.ardine.githubuser.model.FollowingsViewModel

class FollowingsFragment : Fragment() {

    private lateinit var followingsViewModel: FollowingsViewModel
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        followingsViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowingsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        username = arguments?.getString(ARG_USERNAME)
        val rvUsersFollowing = binding.rvUsersFollowing

        val layoutManager = LinearLayoutManager(requireActivity())
        rvUsersFollowing.layoutManager = layoutManager

        followingsViewModel.getFollowingUser(username)

        followingsViewModel.isLoadingFollowing.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        followingsViewModel.isfailed.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        followingsViewModel.followingUser.observe(viewLifecycleOwner) { items ->
            items?.let {
                rvUsersFollowing.adapter = showFragmentRecycler(it)
            }
        }

    }

    private fun showFragmentRecycler(items: List<User>?): UserListAdapter {
        val listUser = ArrayList<User>()

        items?.let {
            listUser.addAll(it)
        }
        return UserListAdapter(listUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollowing.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_USERNAME = "arg_username"
    }
}
