package ruf.view.locationmap.navigator.list

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_list.view.*
import ruf.view.locationmap.R
import ruf.view.locationmap.navigator.FragmentModule.Companion.injectScope
import ruf.view.locationmap.navigator.IView
import toothpick.ktp.delegate.inject


class ListFragment : Fragment(), IView {

    private val presenter: ListPresenter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectScope<ListModule>(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        view.open_detail.setOnClickListener { presenter.openDetail() }
        view.add_module.setOnClickListener { presenter.addModule(R.id.child_container) }
        view.remove_module.setOnClickListener { presenter.removeModule() }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.attachView(this)
    }

    override fun onDestroyView() {
        presenter.detachView()
        super.onDestroyView()
    }
}