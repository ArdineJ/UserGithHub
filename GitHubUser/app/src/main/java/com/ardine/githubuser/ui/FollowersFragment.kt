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
import com.ardine.githubuser.databinding.FragmentFollowersBinding
import com.ardine.githubuser.model.FollowersViewModel

class FollowersFragment : Fragment() {

    private lateinit var followersViewModel: FollowersViewModel
    private var _binding: FragmentFollowersBinding? = null
    private val binding get() = _binding!!

    private var username: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        followersViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FollowersViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = _binding
        username = arguments?.getString(ARG_USERNAME)
        val rvUsersFollower = binding?.rvUsersFollower

        if (binding == null || rvUsersFollower == null) {
            return
        }
        val layoutManager = LinearLayoutManager(requireActivity())
        rvUsersFollower.layoutManager = layoutManager

        followersViewModel.getFollowerUser(username)

        followersViewModel.isLoadingFollower.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        followersViewModel.isfailed.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        followersViewModel.followerUser.observe(viewLifecycleOwner) { items ->
            items?.let {
                rvUsersFollower.adapter = showFragmentRecycler(it)
            }
        }
    }

    private fun showFragmentRecycler(items: List<User>?): UserListAdapter {
        val listUser = ArrayList<User>()

        items?.let{
            listUser.addAll(it)
        }
        return UserListAdapter(listUser)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBarFollower.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_USERNAME = "arg_username"
    }
}
