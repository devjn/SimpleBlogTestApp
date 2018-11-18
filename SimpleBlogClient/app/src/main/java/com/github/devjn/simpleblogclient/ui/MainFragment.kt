package com.github.devjn.simpleblogclient.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.view.LayoutInflater
import android.widget.*
import com.github.devjn.simpleblogclient.Provider
import com.github.devjn.simpleblogclient.R
import com.github.devjn.simpleblogclient.data.Post
import com.github.devjn.simpleblogclient.databinding.MainFragmentBinding
import java.util.*


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(
            MainViewModel::class.java
        )
    }

    private lateinit var itemsAdapter: PostAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.viewModel = viewModel

        itemsAdapter = PostAdapter(context!!, ArrayList())
        binding.list.adapter = itemsAdapter

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.posts.observe(this, Observer {
            if (it == null) return@Observer
            itemsAdapter.clear()
            itemsAdapter.addAll(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> viewModel.requestAllPosts()
            R.id.action_change_server -> showChangeServerDialog()
            R.id.action_new_post -> showNewPostDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showChangeServerDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Enter server address")

        val input = EditText(requireContext())
        val lp = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        input.layoutParams = lp
        alertDialog.setView(input)

        alertDialog.setPositiveButton("OK") { dialog, _ ->
            val address = input.text.toString()
            if (address.isEmpty()) return@setPositiveButton

            Provider.changeServiceAddress(address)
            dialog.dismiss()
        }

        alertDialog.setNeutralButton("Cancel") { dialog, _ -> dialog.cancel() }

        alertDialog.show()
    }

    private fun showNewPostDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("New post")

        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL

        val lp =
            LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)

        val inputTitle = EditText(requireContext())
        inputTitle.hint = "Title"
        layout.addView(inputTitle, lp)

        val inputDesc = EditText(requireContext())
        inputDesc.hint = "Description"
        layout.addView(inputDesc, lp)

        alertDialog.setView(layout)

        alertDialog.setPositiveButton("OK") { dialog, _ ->
            val title = inputTitle.text.toString()
            if (title.isEmpty()) return@setPositiveButton

            viewModel.createNewPost(title, inputDesc.text.toString())
            dialog.dismiss()
        }
        alertDialog.setNeutralButton("Cancel") { dialog, _ -> dialog.cancel() }
        alertDialog.show()
    }

    inner class PostAdapter(context: Context, list: ArrayList<Post>) : ArrayAdapter<Post>(context, 0, list) {

        override fun getItemId(position: Int) = getItem(position)!!.id

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val listItem: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
            val post = getItem(position)!!

            listItem.findViewById<TextView>(R.id.title).text = post.title
            listItem.findViewById<TextView>(R.id.date).text = DateFormat.format("dd.MM.yyy", post.creationDate)

            val btnDelete = listItem.findViewById<Button>(R.id.btn_delete)
            btnDelete.setOnClickListener {
                viewModel.deletePost(post.id)
                Toast.makeText(context, "Deleted post ${post.title}", Toast.LENGTH_SHORT).show()
            }

            listItem.setOnClickListener {
                Log.i("Ada", "itemClicked $position")
                requireFragmentManager().beginTransaction()
                    .replace(R.id.container, DetailFragment.newInstance(getItem(position)!!))
                    .addToBackStack(null)
                    .commit()
            }

            return listItem
        }
    }

}
