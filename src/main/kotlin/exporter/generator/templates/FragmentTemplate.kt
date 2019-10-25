package exporter.generator.templates

class FragmentTemplate {

    fun getTemplate(packageName: String, classesPrefix: String): String {
        return "package com.synesis.gem.$packageName.presentation.view\n" +
                "\n" +
                "import android.os.Bundle\n" +
                "import com.arellomobile.mvp.presenter.InjectPresenter\n" +
                "import com.arellomobile.mvp.presenter.ProvidePresenter\n" +
                "import com.synesis.gem.core.di.IApp\n" +
                "import com.synesis.gem.core.ui.screens.base.fragment.BaseFragment\n" +
                "import com.synesis.gem.$packageName.di.components.${classesPrefix.capitalize()}Component\n" +
                "import com.synesis.gem.$packageName.presentation.presenter.${classesPrefix.capitalize()}Presenter\n" +
                "import com.synesis.gem.$packageName.presentation.presenter.${classesPrefix.capitalize()}View\n" +
                "import javax.inject.Inject\n" +
                "import javax.inject.Provider\n" +
                "\n" +
                "class ${classesPrefix.capitalize()}Fragment : BaseFragment<${classesPrefix.capitalize()}Presenter>(), ${classesPrefix.capitalize()}View {\n" +
                "\n" +
                "    @Inject\n" +
                "    lateinit var presenterProvider: Provider<${classesPrefix.capitalize()}Presenter>\n" +
                "\n" +
                "    @InjectPresenter\n" +
                "    lateinit var presenter: ${classesPrefix.capitalize()}Presenter\n" +
                "\n" +
                "    @ProvidePresenter\n" +
                "    fun createPresenter(): ${classesPrefix.capitalize()}Presenter {\n" +
                "        return presenterProvider.get()\n" +
                "    }\n" +
                "\n" +
                "    override fun providePresenter(): ${classesPrefix.capitalize()}Presenter = presenter\n" +
                "\n" +
                "    override fun getResIdLayout(): Int {\n" +
                "        TODO(\"not implemented\") //To change body of created functions use File | Settings | File Templates.\n" +
                "    }\n" +
                "\n" +
                "    override fun injectComponent(savedInstanceState: Bundle?) {\n" +
                "        ${classesPrefix.capitalize()}Component.init((requireContext().applicationContext as IApp).getAppComponent()).inject(this)\n" +
                "    }\n" +
                "\n" +
                "}"
    }
}