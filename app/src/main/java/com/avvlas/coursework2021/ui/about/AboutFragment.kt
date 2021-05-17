package com.avvlas.coursework2021.ui.about

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.avvlas.coursework2021.R
import com.vansuita.materialabout.builder.AboutBuilder


class AboutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return AboutBuilder.with(requireContext())
            .setPhoto(R.drawable.photo)
            .setCover(R.mipmap.profile_cover)
            .setName(R.string.developer_fullname)
            .setSubTitle(R.string.developer_subtitle)
            .addEmailLink("vlas.s.341@gmail.com")
            .addGitHubLink("Lazydemon341")
            .addInstagramLink("avvlas_")
            .setAppIcon(R.mipmap.ic_launcher)
            .setAppName(R.string.app_name)
            .setVersionNameAsAppSubTitle()
            .addShareAction(R.string.app_name)
            .setWrapScrollView(true)
            .setLinksAnimated(true)
            .setShowAsCard(true)
            .build();
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = getString(R.string.action_about)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
    }

    companion object {
        fun getInstance() = AboutFragment()
    }
}