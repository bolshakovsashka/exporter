package exporter.generator.module.templates

import exporter.generator.module.Config

class FragmentTemplate(val config: Config) : BaseTemplate(config) {

    override fun getTemplate(): String {
        return "package com.synesis.gem.${config.packageName}.${config.screenName}.presentation.view\n" +
                "\n" +
                "import android.os.Bundle\n" +
                "import android.view.View\n" +
                "import com.synesis.gem.core.di.IApp\n" +
                "import com.synesis.gem.core.ui.screens.base.fragment.BaseFragment\n" +
                "import com.synesis.gem.${config.packageName}.R\n" +
                "import ${config.getDIComponentImport()}\n" +
                "import ${config.getPresenterImport()}\n" +
                "import ${config.getMVPViewImport()}\n" +
                "import javax.inject.Inject\n" +
                "import javax.inject.Provider\n" +
                "\n" +
                "class ${config.getFragmentName()} : BaseFragment<${config.getPresenterName()}>(), ${config.getMVPViewName()} {\n" +
                "\n" +
                "    @Inject\n" +
                "    lateinit var presenterProvider: Provider<${config.getPresenterName()}>\n" +
                "\n" +
                "    private val presenter by moxyPresenter { presenterProvider.get() }\n" +
                "\n" +
                "    private lateinit var viewController: ${config.getViewControllerName()}\n" +
                "\n" +
                "    override fun providePresenter(): ${config.getPresenterName()} = presenter\n" +
                "\n" +
                "    override fun getResIdLayout(): Int = R.layout.${config.getLayoutName()}\n" +
                "\n" +
                "    override fun injectComponent(savedInstanceState: Bundle?) {\n" +
                "        ${config.getDIComponentName()}.init(getAppComponent()).inject(this)\n" +
                "    }\n" +
                "\n" +
                "    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {\n" +
                "        viewController = ${config.getViewControllerName()}(view)\n" +
                "        super.onViewCreated(view, savedInstanceState)\n" +
                "    }\n" +
                "}"
    }
}