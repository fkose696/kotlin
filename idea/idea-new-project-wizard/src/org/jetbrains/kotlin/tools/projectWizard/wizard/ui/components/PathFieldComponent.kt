package org.jetbrains.kotlin.tools.projectWizard.wizard.ui.components

import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory
import com.intellij.openapi.ui.TextBrowseFolderListener
import com.intellij.openapi.ui.TextFieldWithBrowseButton
import org.jetbrains.kotlin.tools.projectWizard.core.context.ReadingContext
import org.jetbrains.kotlin.tools.projectWizard.core.entity.SettingValidator
import org.jetbrains.kotlin.tools.projectWizard.wizard.IdeContext
import org.jetbrains.kotlin.tools.projectWizard.wizard.ui.textField
import java.nio.file.Path
import java.nio.file.Paths
import javax.swing.JComponent

class PathFieldComponent(
    ideContext: IdeContext,
    labelText: String? = null,
    initialValue: Path? = null,
    validator: SettingValidator<Path>? = null,
    onValueUpdate: (Path) -> Unit = {}
) : UIComponent<Path>(
    ideContext,
    labelText,
    validator,
    onValueUpdate
) {
    override val uiComponent: TextFieldWithBrowseButton = TextFieldWithBrowseButton(
        textField(initialValue?.toString().orEmpty()) { path -> fireValueUpdated(Paths.get(path.trim())) }
    ).apply {
        addBrowseFolderListener(
            TextBrowseFolderListener(
                FileChooserDescriptorFactory.createSingleFolderDescriptor(),
                null
            )
        )
    }

    override fun getValidatorTarget(): JComponent = uiComponent.textField

    override fun updateUiValue(newValue: Path) = safeUpdateUi {
        uiComponent.text = newValue.toString()
    }

    override fun getUiValue(): Path = Paths.get(uiComponent.text.trim())

    override fun focusOn() {
        uiComponent.textField.requestFocus()
    }
}